package ua.nure.petrikin.OnlineBanking.db;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import ua.nure.petrikin.OnlineBanking.db.entity.Account;
import ua.nure.petrikin.OnlineBanking.db.pool.ConnectionPool;

public class AccountDao extends AbstractDao<Account, Integer>{

	public AccountDao(ConnectionPool connection) {
		super(connection);
	}

	@Override
	protected String getSelectQuery() {
		return "SELECT * FROM accounts";
	}

	@Override
	protected String getUpdateQuery() {
		return "UPDATE accounts SET user_id = ?, acc_status_id = ?, name = ?, "
			 + "balance = ?, request = ?, lim = ?, date ?, spent = ? WHERE id = ?";
	}

	@Override
	protected String getCreateQuery() {
		return "INSERT INTO accounts VALUE(DEFAULT, ?, ?, ?, DEFAULT, DEFAULT, DEFAULT, DEFAULT, DEFAULT)";
	}

	@Override
	protected String getDeleteQuery() {
		return "DELETE FROM accounts WHERE id = ?";
	}

	@Override
	protected Account parseResultSet(ResultSet rs) throws SQLException {
		Account account = new Account();
		account.setId(rs.getInt(Fields.ACCOUNT_ID));
		account.setUserId(rs.getInt(Fields.ACCOUNT_USER_ID));
		account.setAccStatusId(rs.getInt(Fields.ACCOUNT_STATUS_ID));
		account.setBalance(rs.getInt(Fields.ACCOUNT_BALANCE));
		account.setName(rs.getString(Fields.ACCOUNT_NAME));
		return account;
	}

	@Override
	protected void prepareUpdateStatement(Account entity, PreparedStatement ps) throws SQLException {
		ps.setInt(1, entity.getUserId());
		ps.setInt(2, entity.getAccStatusId());
		ps.setString(3, entity.getName());
		ps.setInt(4, entity.getBalance());
		ps.setBoolean(5, entity.isRequest());
		ps.setInt(6, entity.getLim());
		ps.setTimestamp(7, entity.getDate());
		ps.setInt(8, entity.getSpent());
		ps.setInt(9, entity.getId());
	}

	@Override
	protected void prepareInsertStatement(Account entity, PreparedStatement ps) throws SQLException {
		ps.setInt(1, entity.getUserId());
		ps.setInt(2, entity.getAccStatusId());
		ps.setString(3, entity.getName());
	}

}