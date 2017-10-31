package ua.nure.petrikin.OnlineBanking.db.entity;

import java.io.Serializable;

public abstract class Entity implements Serializable{

	private static final long serialVersionUID = 3463481987525731883L;
	
	private Integer id;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
}
