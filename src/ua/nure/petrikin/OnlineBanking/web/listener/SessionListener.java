package ua.nure.petrikin.OnlineBanking.web.listener;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.apache.log4j.Logger;

@WebListener
public class SessionListener implements HttpSessionListener{

	private static final Logger LOG = Logger.getLogger(SessionListener.class);
	
	@Override
	public void sessionCreated(HttpSessionEvent event) {
		HttpSession session = event.getSession();
		session.setMaxInactiveInterval(10*60);
		LOG.trace("Session created --> " + session.getId() + "; time: " + 10*60);
		
	}

	@Override
	public void sessionDestroyed(HttpSessionEvent event) {
		HttpSession session = event.getSession();
		LOG.trace("Session destroyed --> " + session.getId());
	}

}
