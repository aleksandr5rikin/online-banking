package ua.nure.petrikin.OnlineBanking.db.entity;

import java.io.Serializable;

public enum Role implements Serializable{
	CLIENT, ADMIN;
	
	public static Role getRole(User user) {
		int roleId = user.getRoleId() - 1;
		return Role.values()[roleId];
	}
	
	public String getName() {
		return name().toLowerCase();
	}
	
	public int getId() {
		return ordinal() + 1;
	}
}
