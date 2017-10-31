package ua.nure.petrikin.OnlineBanking.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import ua.nure.petrikin.OnlineBanking.db.entity.Payment;
import ua.nure.petrikin.OnlineBanking.db.pool.ConnectionPool;
import ua.nure.petrikin.OnlineBanking.exception.DBException;

public class PaymentDao extends AbstractDao<Payment, Integer>{
	
	private static final String SELECT_USER_PAY = "SELECT payments.* FROM payments, accounts "
			+ "WHERE (payments.account_id = accounts.id "
			+ "OR payments.reciver_id = accounts.id) AND accounts.user_id = ?";
	
	public PaymentDao(ConnectionPool connection) {
		super(connection);
	}

	@Override
	protected String getSelectQuery() {
		return "SELECT * FROM payments";
	}

	@Override
	protected String getUpdateQuery() {
		return "UPDATE payments SET pay_status_id = ? WHERE id = ?";
	}

	@Override
	protected String getCreateQuery() {
		return "INSERT INTO payments VALUE (DEFAULT, ?, ?, ?, ?, ?, DEFAULT)";
	}

	@Override
	protected String getDeleteQuery() {
		return "DELETE FROM payments WHERE id = ?";
	}

	@Override
	protected Payment parseResultSet(ResultSet rs) throws SQLException {
		Payment payment = new Payment();
		payment.setId(rs.getInt(Fields.PAYMENT_ID));
		payment.setAccId(rs.getInt(Fields.PAYMENT_ACCOUNT_ID));
		payment.setReciverId(rs.getInt(Fields.PAYMENT_RECIVER_ID));
		payment.setSum(rs.getInt(Fields.PAYMENT_SUM));
		payment.setPayStatusId(rs.getInt(Fields.PAYMENT_STATUS_ID));
		payment.setComment(rs.getString(Fields.PAYMENT_COMMENT));
		payment.setDate(rs.getTimestamp(Fields.PAYMENT_DATE));
		return payment;
	}

	@Override
	protected void prepareUpdateStatement(Payment entity, PreparedStatement ps) throws SQLException {
		ps.setInt(1, entity.getPayStatusId());
		ps.setInt(2, entity.getId());
	}

	@Override
	protected void prepareInsertStatement(Payment entity, PreparedStatement ps) throws SQLException {
		ps.setInt(1, entity.getAccId());
		ps.setInt(2, entity.getReciverId());
		ps.setInt(3, entity.getSum());
		ps.setInt(4, entity.getPayStatusId());
		ps.setString(5, entity.getComment());
	}
	
	public List<Payment> findUserPayment(int userId) throws DBException{
		Connection con = getConnectionFromPool();
		List<Payment> payments = new ArrayList<>();
		try {
			PreparedStatement ps = con.prepareStatement(SELECT_USER_PAY);
			ps.setInt(1, userId);
			ResultSet rs = ps.executeQuery();
			while(rs.next()){
				payments.add(parseResultSet(rs));
			}
		} catch (SQLException ex) {
			throw new DBException(ex.getMessage());
		}finally {
			try {
				con.close();
			} catch (SQLException e) {
				throw new DBException(e.getMessage());
			}
		}
		return payments;
	}
}