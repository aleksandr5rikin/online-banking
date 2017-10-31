package ua.nure.petrikin.OnlineBanking.web.websocket;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.websocket.EncodeException;
import javax.websocket.OnClose;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import ua.nure.petrikin.OnlineBanking.db.MySqlDaoFactory;
import ua.nure.petrikin.OnlineBanking.db.NoticeDao;
import ua.nure.petrikin.OnlineBanking.exception.AppException;
import ua.nure.petrikin.OnlineBanking.exception.DBException;

@ServerEndpoint("/notifications/{userId}")
public class ClientWebsocket {

	private Integer id;
	private static final Map<Integer, SessionWarper> sessions = Collections.synchronizedMap(new HashMap<>());

	public static void notifySend(Integer userId) throws AppException {	
		SessionWarper sessionWarper = sessions.get(userId);
		if(sessionWarper != null){
			try {
				Session s = sessionWarper.getSession();
				Integer count = sessionWarper.getCount();
				s.getBasicRemote().sendObject(++count);
			} catch (IOException | EncodeException ex) {
				sessions.remove(userId);
				throw new AppException(ex.getMessage());
			}
		}
	}
	
	@OnOpen
	public void onOpen(@PathParam("userId") Integer userId, Session session) throws IOException, EncodeException, DBException {
		id = userId;
		SessionWarper sessionWarper = new SessionWarper();
		
		NoticeDao noticeDao = MySqlDaoFactory.getInstance().getDao(NoticeDao.class);
		int count = noticeDao.getCount(userId);
		
		sessionWarper.setCount(count);
		sessionWarper.setSession(session);
		session.getBasicRemote().sendObject(++count);
		
		sessions.put(userId, sessionWarper);
		
	}

	@OnClose
	public void onClose() throws IOException, EncodeException {
		sessions.remove(id);
	}
}
