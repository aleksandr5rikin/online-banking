package ua.nure.petrikin.OnlineBanking.db.entity;

import java.sql.Timestamp;

public class Payment extends Entity{

	private static final long serialVersionUID = -3379870318476509289L;
	
	private int accId;
	private int reciverId;
	private int sum;
	private int payStatusId;
	private String comment;
	private Timestamp date;
	
	public int getAccId() {
		return accId;
	}
	public void setAccId(int accId) {
		this.accId = accId;
	}
	public int getReciverId() {
		return reciverId;
	}
	public void setReciverId(int reciverId) {
		this.reciverId = reciverId;
	}
	public int getSum() {
		return sum;
	}
	public void setSum(int sum) {
		this.sum = sum;
	}
	public int getPayStatusId() {
		return payStatusId;
	}
	public void setPayStatusId(int payStatusId) {
		this.payStatusId = payStatusId;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public Timestamp getDate() {
		return date;
	}
	public void setDate(Timestamp date) {
		this.date = date;
	}
	
}
