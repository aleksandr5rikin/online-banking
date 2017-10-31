package ua.nure.petrikin.OnlineBanking.db.entity;

import java.io.Serializable;

public enum PayStatus implements Serializable{
	PREPEARED, SUCEFULL;
	
	public static PayStatus getPayment(Payment payment) {
		int payStatusId = payment.getPayStatusId() - 1;
		return PayStatus.values()[payStatusId];
	}
	
	public String getName() {
		return name().toLowerCase();
	}
	
	public int getId() {
		return ordinal() + 1;
	}
}
