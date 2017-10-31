package ua.nure.petrikin.OnlineBanking.web.websocket;

import javax.websocket.Session;

public class SessionWarper {
	private Integer count;
	private Session session;
	
	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}
	public Session getSession() {
		return session;
	}
	public void setSession(Session session) {
		this.session = session;
	}
	
}
