package ua.nure.petrikin.OnlineBanking.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import ua.nure.petrikin.OnlineBanking.db.entity.User;
import ua.nure.petrikin.OnlineBanking.db.pool.ConnectionPool;
import ua.nure.petrikin.OnlineBanking.exception.DBException;

public class UserDao extends AbstractDao<User, Integer>{
	
	public UserDao(ConnectionPool connection) {
        super(connection);
	}

	@Override
	protected String getSelectQuery() {
		return "SELECT * FROM users";
	}

	@Override
	protected String getUpdateQuery() {
		return "UPDATE users SET user_status_id = ?, login = ?, "
			 + "password = ?, salt = ?, name = ?, email = ?, token = ? WHERE id= ?";
	}

	@Override
	protected String getCreateQuery() {
		return "INSERT INTO users VALUES (DEFAULT, ?, ?, ?, ?, ?, ?, ?, ?)";
	}
	
	@Override
	protected String getDeleteQuery() {
		return "DELETE FROM users WHERE id= ?";
	}
	
	@Override
	protected User parseResultSet(ResultSet rs) throws SQLException {
		User user = new User();
		user.setId(rs.getInt(Fields.USER_ID));
		user.setRoleId(rs.getInt(Fields.USER_ROLE_ID));
		user.setUserStatusId(rs.getInt(Fields.USER_STATUS_ID));
		user.setLogin(rs.getString(Fields.USER_LOGIN));
		user.setPassword(rs.getString(Fields.USER_PASSWORD));
		user.setName(rs.getString(Fields.USER_NAME));
		user.setEmail(rs.getString(Fields.USER_EMAIL));
		user.setToken(rs.getString(Fields.USER_TOKEN));
		user.setSalt(rs.getString(Fields.USER_SALT));
		return user;
	}

	@Override
	protected void prepareUpdateStatement(User entity, PreparedStatement ps) throws SQLException {
		ps.setInt(1, entity.getRoleId());
		ps.setInt(2, entity.getUserStatusId());
		ps.setString(3, entity.getLogin());
		ps.setString(4, entity.getPassword());
		ps.setString(5, entity.getSalt());
		ps.setString(6, entity.getName());
		ps.setString(7, entity.getEmail());
		ps.setString(8, entity.getToken());	
		ps.setInt(9, entity.getId());
	}
	
	@Override
	protected void prepareInsertStatement(User entity, PreparedStatement ps) throws SQLException {
		ps.setInt(1, entity.getRoleId());
		ps.setInt(2, entity.getUserStatusId());
		ps.setString(3, entity.getLogin());
		ps.setString(4, entity.getPassword());
		ps.setString(5, entity.getSalt());
		ps.setString(6, entity.getName());
		ps.setString(7, entity.getEmail());
		ps.setString(8, entity.getToken());	
	}
	
	public User findByLogin(String login) throws DBException{
    	Connection connection = getConnectionFromPool(); 
    	List<User> list = new ArrayList<>();
         String sql = getSelectQuery();
         sql += " WHERE login = ?";
         try (PreparedStatement statement = connection.prepareStatement(sql)){
        	 statement.setString(1, login);
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
}