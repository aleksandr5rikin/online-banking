package ua.nure.petrikin.OnlineBanking.web.command.admin;

import java.io.IOException;
import java.util.List;

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

public class UserListCommand extends Command{

	private static final Logger LOG = Logger.getLogger(UserListCommand.class);
	
	private static final long serialVersionUID = -8085905949545550655L;

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException, AppException {
		LOG.debug("START");
		
		UserDao userDao = MySqlDaoFactory.getInstance().getDao(UserDao.class);
		List<User> users = userDao.getAll();
		
		request.setAttribute("users", users);
		
		LOG.trace("List<User> size ===> " + users.size());
		LOG.debug("FINISH");
		return Path.PAGE_ADMIN_USER_LIST;
	}

}
