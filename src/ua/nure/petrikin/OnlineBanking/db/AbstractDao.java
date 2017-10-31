package ua.nure.petrikin.OnlineBanking.db;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import ua.nure.petrikin.OnlineBanking.db.entity.Entity;
import ua.nure.petrikin.OnlineBanking.db.pool.ConnectionPool;
import ua.nure.petrikin.OnlineBanking.exception.ConnectionNotAvailableException;
import ua.nure.petrikin.OnlineBanking.exception.DBException;
import ua.nure.petrikin.OnlineBanking.exception.Messages;


public abstract class AbstractDao<E extends Entity, PK extends Serializable> implements GenericDao<E, PK>{

	private ConnectionPool connectionPool;
	
	protected Connection getConnectionFromPool() throws DBException{
		Connection con = null;
		try {
			con = connectionPool.getConnection();
		} catch (InterruptedException | ConnectionNotAvailableException | SQLException ex) {
			throw new DBException(Messages.ERR_SQL_CONNECTION, ex);
		}
		return con;
	}
	
    public E get(PK id) throws DBException{
    	Connection connection = getConnectionFromPool(); 
    	List<E> list = new ArrayList<>();
         String sql = getSelectQuery();
         sql += " WHERE id = ?";
         try (PreparedStatement statement = connection.prepareStatement(sql)){
        	 statement.setObject(1, id);
             ResultSet rs = statement.executeQuery();
             while(rs.next()){
            	 list.add(parseResultSet(rs));
             }
         } catch (SQLException e) {
             throw new DBException(e.getMessage());
         } finally {
			try {
				connection.close();
			} catch (SQLException e) {
				throw new DBException(e.getMessage());
			}
		}
         
         if (list == null || list.size() == 0) {
             return null;
         }
         if (list.size() > 1) {
             throw new DBException("Received more than one record.");
         }
         return list.iterator().next();
    }
    
    public E save(E entity) throws DBException{
    	Connection connection = getConnectionFromPool();
    	String sql;
    	
    	if (entity.getId() != null) {
    		sql = getUpdateQuery();
            try {
            	PreparedStatement statement = connection.prepareStatement(sql);
                prepareUpdateStatement(entity, statement);
                int count = statement.executeUpdate();
                if (count != 1) {
                    throw new SQLException("On update modify more then 1 record: " + count);
                }
            } catch (SQLException e) {
            	entity.setId(null);
                throw new DBException(e.getMessage());
            } finally {
				try {
					connection.close();
				} catch (SQLException e) {
					throw new DBException(e.getMessage());
				}
			}
            
            return entity;
        } else {
	        sql = getCreateQuery();
	        try {
	        	PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
	            prepareInsertStatement(entity, statement);
	            int affectedRow = statement.executeUpdate();
	            if (affectedRow == 0) {
	                throw new DBException("User save error");
	            }
	            entity.setId(affectedRow);
	        } catch (SQLException e) {
	            throw new DBException(e.getMessage());
	        } finally {
				try {
					connection.close();
				} catch (SQLException e) {
					throw new DBException(e.getMessage());
				}
			}
        }
        return entity;
    }
    
    public List<E> getAll() throws DBException{
    	Connection connection = getConnectionFromPool();
    	List<E> lst = new ArrayList<>();
    	String sql = getSelectQuery();
        try {
        	PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) { 
                lst.add(parseResultSet(rs));
            }
        } catch (SQLException e) {
        	throw new DBException(e.getMessage());
        } finally {
			try {
				connection.close();
			} catch (SQLException e) {
				throw new DBException(e.getMessage());
			}
		}
        
        return lst;
    }
    
    @Override
    public void delete(E object) throws DBException {
    	Connection con = getConnectionFromPool();
    	String sql = getDeleteQuery();
        try {
        	PreparedStatement stmt = con.prepareStatement(sql);
            try {
            	stmt.setObject(1, object.getId());
            } catch (SQLException e) {
                throw new DBException(e.getMessage());
            }
            int count = stmt.executeUpdate();
            if (count != 1) {
                throw new DBException("On delete modify more then 1 record: " + count);
            }
            stmt.close();
        } catch (SQLException e) {
            throw new DBException(e.getMessage());
        }finally {
			try {
				con.close();
			} catch (SQLException e) {
				throw new DBException(e.getMessage());
			}
		}
    }

    
    public AbstractDao(ConnectionPool connection) {
        this.connectionPool = connection;
    }
    
    protected abstract String getSelectQuery();
    protected abstract String getUpdateQuery();
    protected abstract String getCreateQuery();
    protected abstract String getDeleteQuery();
    
    protected abstract E parseResultSet(ResultSet rs) throws SQLException;
    protected abstract void prepareUpdateStatement(E entity, PreparedStatement ps)throws SQLException;
    protected abstract void prepareInsertStatement(E entity, PreparedStatement ps)throws SQLException;
}