package ua.nure.petrikin.OnlineBanking.db;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import ua.nure.petrikin.OnlineBanking.db.pool.ConnectionPool;
import ua.nure.petrikin.OnlineBanking.exception.DBException;

public class MySqlDaoFactory implements DaoFactory {

	private static final Logger LOG = Logger.getLogger(MySqlDaoFactory.class);
	
	private static final String DB_DRIVER = "com.mysql.jdbc.Driver";
	private static final String DB_URL = "jdbc:mysql://localhost:3306/st4";
	private static final String DB_USER = "test";
	private static final String DB_PASSWORD = "test";
	private static final int MAX_CONNECTIONS = 10;
	private static final int INIT_CONNECTIONS = 5;
	
	private static MySqlDaoFactory instance;
	private static ConnectionPool connectionPool;
	private static Map<Class<? extends GenericDao<?, ?>>, GenericDao<?, ?>> creators;

	public static synchronized MySqlDaoFactory getInstance() throws DBException {
		LOG.debug("Start");
		if (instance == null) {
			instance = new MySqlDaoFactory();
		}
		LOG.debug("Finish");
		return instance;
	}

    private MySqlDaoFactory() throws DBException{
        try {
            Class.forName(DB_DRIVER);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        
        try {
			connectionPool = new ConnectionPool(DB_URL, DB_USER, DB_PASSWORD, MAX_CONNECTIONS, INIT_CONNECTIONS);
		} catch (SQLException e) {
			LOG.error(e.getMessage());
			throw new DBException(e);
		}

        creators = new HashMap<>();
        creators.put(UserDao.class, new UserDao(connectionPool));
        creators.put(AccountDao.class, new AccountDao(connectionPool));
        creators.put(PaymentDao.class, new PaymentDao(connectionPool));
        creators.put(NoticeDao.class, new NoticeDao(connectionPool));
    }

	@Override
	public <T extends GenericDao<?, ?>> T getDao(Class<T> clazz) throws DBException {
		T dao = clazz.cast(creators.get(clazz));
        if (dao == null) {
            throw new DBException("Dao object for " + clazz + " not found.");
        }
        return dao;
	}

}