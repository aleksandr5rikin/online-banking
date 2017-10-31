package ua.nure.petrikin.OnlineBanking.db.entity;

import java.io.Serializable;

public enum AccStatus implements Serializable{
	UNLOCKED, LOCKED;
	
	public static AccStatus getAccStatus(Account account) {
		int accStatusId = account.getAccStatusId() - 1;
		return AccStatus.values()[accStatusId];
	}
	
	public String getName() {
		return name().toLowerCase();
	}
	
	public int getId() {
		return ordinal() + 1;
	}
}
