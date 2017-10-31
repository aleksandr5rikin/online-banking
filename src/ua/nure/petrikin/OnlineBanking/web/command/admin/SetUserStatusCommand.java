package ua.nure.petrikin.OnlineBanking.web.command.admin;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import ua.nure.petrikin.OnlineBanking.db.MySqlDaoFactory;
import ua.nure.petrikin.OnlineBanking.db.UserDao;
import ua.nure.petrikin.OnlineBanking.db.entity.User;
import ua.nure.petrikin.OnlineBanking.exception.AppException;
import ua.nure.petrikin.OnlineBanking.web.Path;
import ua.nure.petrikin.OnlineBanking.web.command.Command;

public class SetUserStatusCommand extends Command{

	private static final Logger LOG = Logger.getLogger(SetUserStatusCommand.class);
	
	private static final long serialVersionUID = 1363877118077508439L;

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException, AppException {
		LOG.debug("START");
		
		String forward = Path.COMMAND_USER_LIST;
		
		int userId = Integer.parseInt(request.getParameter("userId"));
		int statusId = Integer.parseInt(request.getParameter("statusId"));
		
		UserDao userDao = MySqlDaoFactory.getInstance().getDao(UserDao.class);
		User user = userDao.get(userId);
		user.setUserStatusId(statusId);
		userDao.save(user);
		
		if(user.getId() == null){
			forward = Path.PAGE_ERROR_PAGE;
		}
		
		LOG.trace("User ID ====> " + user.getId());
		LOG.debug("FINISH");
 		return forward;
	}

}
