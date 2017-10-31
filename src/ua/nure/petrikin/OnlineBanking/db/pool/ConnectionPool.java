package ua.nure.petrikin.OnlineBanking.db.pool;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

import org.apache.log4j.Logger;

import ua.nure.petrikin.OnlineBanking.exception.ConnectionNotAvailableException;

public class ConnectionPool {
    
	public static final Logger LOG = Logger.getLogger(ConnectionPool.class);
	
	private AtomicBoolean closed = new AtomicBoolean(false);
	
	private int maxNbConnections;
    private int initNbConnections;
    
    private String dbUrl;
    private String username;
    private String password;
    
    private ConnectionReleaser releaser;
    private Thread releaserThread;
    
    private LinkedBlockingQueue<PooledConnection> availableConnection;
    private LinkedBlockingQueue<PooledConnection> beingUsedConnection;

    public ConnectionPool(String dbUrl, String user, String password, int max, int init) throws SQLException {
        this.availableConnection = new LinkedBlockingQueue<PooledConnection>(max);
        this.beingUsedConnection = new LinkedBlockingQueue<PooledConnection>(max);
        this.maxNbConnections = max;
        this.initNbConnections = init;
        this.dbUrl = dbUrl;
        this.username = user;
        this.password = password;
        if (this.initNbConnections > this.maxNbConnections) {
            throw new IllegalArgumentException("The number of initial connection '" + this.initNbConnections + "' is greater than the maximum connection we can have (" + this.maxNbConnections + ")");
        }
        for (int i = 0; i < this.initNbConnections; ++i) {
            Connection newCon = DriverManager.getConnection(this.dbUrl, this.username, this.password);
            this.availableConnection.add(new PooledConnection(newCon, this));
        }
        releaser = new ConnectionReleaser(this, this.beingUsedConnection);
        releaserThread = new Thread(releaser);
        releaserThread.start();
    }

    public void returnConnection(Connection pooledConnection) throws SQLException {
        if (pooledConnection instanceof PooledConnection && pooledConnection.isValid(1000)) {
            if (!pooledConnection.getAutoCommit()) {
                pooledConnection.rollback();
            }
            pooledConnection.clearWarnings();
        } else {
            throw new SQLException("The Connection is invalid and cannot be pooled back.");
        }
        this.beingUsedConnection.remove(pooledConnection);
        this.availableConnection.add((PooledConnection)pooledConnection);
    }

    public PooledConnection getConnection() throws SQLException, InterruptedException, ConnectionNotAvailableException {
        if (!this.availableConnection.isEmpty()) {
            PooledConnection con = this.availableConnection.poll();
            this.beingUsedConnection.add(con);
            return con;
        }
        if (this.beingUsedConnection.size() < this.maxNbConnections) {
            Connection newCon = DriverManager.getConnection(this.dbUrl, this.username, this.password);
            PooledConnection newPooledCon = new PooledConnection(newCon, this);
            this.beingUsedConnection.add(newPooledCon);
            return newPooledCon;
        }
        if (this.availableConnection.peek() == null) {
            throw new ConnectionNotAvailableException();
        }
        return this.availableConnection.poll(0, TimeUnit.SECONDS);
    }
    
    public void close() throws SQLException {
        if (this.isClosed()) {
            return;
        }
        this.closed.set(true);

        if (this.releaserThread != null && this.releaserThread.isAlive()) {
            LOG.debug("Waiting for Releaser to join");
            try {
                this.releaserThread.join();
            } catch (InterruptedException e) {
                LOG.error(e);
            }
            LOG.debug("Releaser joined");
        }

        LinkedBlockingQueue<PooledConnection> pooledConnections = this.availableConnection;
        if (pooledConnections.size() == 0) {
            pooledConnections = this.beingUsedConnection;
        }
        while (pooledConnections.size() > 0) {
            PooledConnection conn = null;
            try {
                conn = pooledConnections.poll(1, TimeUnit.SECONDS);
            } catch (InterruptedException e) {
                if (LOG.isDebugEnabled()) {
                    LOG.debug("Cannot close connection pool. Interrupted. Exception:\n" + e.getMessage());
                }
                throw new SQLException("Cannot close Connection Pool. Interrupted.", e);
            }
            if (pooledConnections == this.availableConnection) {
                this.returnConnection(conn);
                if (pooledConnections.size() == 0) {
                    pooledConnections = this.beingUsedConnection;
                }
            }
        }
    }
    
    public boolean isClosed() {
        return this.closed.get();
    }
}
