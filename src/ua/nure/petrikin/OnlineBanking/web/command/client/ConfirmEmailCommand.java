package ua.nure.petrikin.OnlineBanking.web.command.client;

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

public class ConfirmEmailCommand extends Command {

	private static final Logger LOG = Logger.getLogger(ConfirmEmailCommand.class);

	private static final long serialVersionUID = 9115229623238737213L;

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException, AppException {
		LOG.debug("START");

		String forward = Path.COMMAND_GET_LOGIN;

		String token = request.getParameter("token");
		int userId = Integer.parseInt(request.getParameter("id"));

		UserDao userDao = MySqlDaoFactory.getInstance().getDao(UserDao.class);
		User user = userDao.get(userId);
		user.setToken(token);
		userDao.save(user);

		if (user.getId() == null) {
			forward = Path.PAGE_ERROR_PAGE;
		}

		LOG.trace("[User ID ====> " + userId + "]");
		LOG.debug("FINISH");
		return forward;
	}

}
