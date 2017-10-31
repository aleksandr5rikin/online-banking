package ua.nure.petrikin.OnlineBanking.db.entity;

import java.sql.Timestamp;

public class Account extends Entity{

	private static final long serialVersionUID = 3918649988481599817L;
	
	private int userId;
	private int lim;
	private int accStatusId;
	private int balance;
	private int spent;
	private String name;
	private boolean request;
	private Timestamp date;
	
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public int getAccStatusId() {
		return accStatusId;
	}
	public void setAccStatusId(int accStatusId) {
		this.accStatusId = accStatusId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getBalance() {
		return balance;
	}
	public void setBalance(int balance) {
		this.balance = balance;
	}
	public boolean isRequest() {
		return request;
	}
	public void setRequest(boolean request) {
		this.request = request;
	}
	public int getLim() {
		return lim;
	}
	public void setLim(int lim) {
		this.lim = lim;
	}
	public int getSpent() {
		return spent;
	}
	public void setSpent(int spent) {
		this.spent = spent;
	}
	public Timestamp getDate() {
		return date;
	}
	public void setDate(Timestamp date) {
		this.date = date;
	}
	
}
