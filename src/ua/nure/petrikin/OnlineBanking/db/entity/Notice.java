package ua.nure.petrikin.OnlineBanking.db.entity;

public class Notice extends Entity{

	private static final long serialVersionUID = -972071152036116000L;
	
	private String message;
	private int userId;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}
}
