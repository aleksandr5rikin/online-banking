package ua.nure.petrikin.OnlineBanking.db.pool;

import java.sql.SQLException;
import java.util.concurrent.LinkedBlockingQueue;

import org.apache.log4j.Logger;

public class ConnectionReleaser implements Runnable
{
	public static final Logger LOG = Logger.getLogger(ConnectionReleaser.class);
	public static final int TIMEOUT = 30000;
    private ConnectionPool pool;
    private LinkedBlockingQueue<PooledConnection> busyConnections;
    private long lastRun;   
    public ConnectionReleaser(ConnectionPool pool, LinkedBlockingQueue<PooledConnection> busyConnections) {
        this.pool = pool;
        this.busyConnections =  busyConnections;
    }

    @Override
    public void run() {
        this.lastRun = System.currentTimeMillis();
        LOG.debug("Time between runs: " + TIMEOUT);
        while (true) {
            if (this.pool.isClosed()) {
               return;
            }
            if(((System.currentTimeMillis() - this.lastRun) > TIMEOUT)) {
                if (this.busyConnections != null && this.busyConnections.size() > 0) {
                    for (int i = 0; i < this.busyConnections.size(); i++) {
                        PooledConnection conn = this.busyConnections.peek();
                        try {
                            if (conn != null && conn.isClosed()) {
                                this.pool.returnConnection(conn);
                                LOG.debug("Connection Released by ConnectionReleaser: " + conn);
                            }
                        } catch (SQLException e) {
                            LOG.error("Connection could not be released to the pool", e);
                        }
                    }
                    this.lastRun = System.currentTimeMillis();
                }
            }
        }
    }
}
