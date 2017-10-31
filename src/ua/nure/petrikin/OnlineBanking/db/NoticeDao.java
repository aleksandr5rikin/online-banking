package ua.nure.petrikin.OnlineBanking.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import ua.nure.petrikin.OnlineBanking.db.entity.Notice;
import ua.nure.petrikin.OnlineBanking.db.pool.ConnectionPool;
import ua.nure.petrikin.OnlineBanking.exception.DBException;

public class NoticeDao extends AbstractDao<Notice, Integer>{

	private static final String COUNT = "SELECT * FROM notice";
	
	public NoticeDao(ConnectionPool connection) {
		super(connection);
	}

	@Override
	protected String getSelectQuery() {
		return "SELECT * FROM notice";
	}

	@Override
	protected String getUpdateQuery() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected String getCreateQuery() {
		return "INSERT INTO notice VALUES (DEFAULT, ?, ?)";
	}

	@Override
	protected String getDeleteQuery() {
		return "DELETE FROL notice WHERE user_id = ?";
	}

	@Override
	protected Notice parseResultSet(ResultSet rs) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void prepareUpdateStatement(Notice entity, PreparedStatement ps) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void prepareInsertStatement(Notice entity, PreparedStatement ps) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	public int getCount(int userId) throws DBException{
		Connection con = getConnectionFromPool();
		int count = 0;
		try {
			PreparedStatement ps = con.prepareStatement(COUNT);
			ps.setInt(1, userId);
			ResultSet rs = ps.executeQuery();
			if(rs.next()){
				count = rs.getInt(1);
			}
		} catch (SQLException ex) {
			throw new DBException(ex.getMessage());
		}
		return count;
	}
}
