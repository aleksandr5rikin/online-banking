package ua.nure.petrikin.OnlineBanking.db.entity;

import java.io.Serializable;

public enum UserStatus implements Serializable{
	UNCOMFIRMED, CONFIRMED, BLOCKED;
	
	public static UserStatus getUserStatus(User user) {
		int userStatusId = user.getUserStatusId() - 1;
		return UserStatus.values()[userStatusId];
	}
	
	public String getName() {
		return name().toLowerCase();
	}
	
	public int getId() {
		return ordinal() + 1;
	}
}
