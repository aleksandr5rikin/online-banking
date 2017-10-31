package ua.nure.petrikin.OnlineBanking.web.command;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import ua.nure.petrikin.OnlineBanking.db.MySqlDaoFactory;
import ua.nure.petrikin.OnlineBanking.db.UserDao;
import ua.nure.petrikin.OnlineBanking.db.entity.Role;
import ua.nure.petrikin.OnlineBanking.db.entity.User;
import ua.nure.petrikin.OnlineBanking.db.entity.UserStatus;
import ua.nure.petrikin.OnlineBanking.exception.AppException;
import ua.nure.petrikin.OnlineBanking.exception.DBException;
import ua.nure.petrikin.OnlineBanking.exception.Messages;
import ua.nure.petrikin.OnlineBanking.web.Path;
import ua.nure.petrikin.OnlineBanking.web.util.SecureUtils;

public class LoginCommand extends Command {

	private static final long serialVersionUID = -3071536593627692473L;

	private static final Logger LOG = Logger.getLogger(LoginCommand.class);

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) 
			throws IOException, ServletException, AppException {
		LOG.debug("START");
		
		String login = request.getParameter("login");
		String password = request.getParameter("password");
		
		UserDao userDao = MySqlDaoFactory.getInstance().getDao(UserDao.class);
		User user = userDao.findByLogin(login);
		
		if(user == null){
			throw new AppException(Messages.ERR_SQL_FIND_USER);
		}
		
		if(UserStatus.getUserStatus(user) == UserStatus.UNCOMFIRMED){
			LOG.debug(Messages.ERR_AUTH_UNCOMFIRMED);
			throw new AppException(Messages.ERR_AUTH_UNCOMFIRMED);
		}
		
		String hashPassword = null;
		
		try {
			hashPassword = SecureUtils.hash(password + user.getSalt(), "SHA-256");	
		} catch (NoSuchAlgorithmException ex) {
			LOG.error(Messages.ERR_SECUR_HASH_ALG, ex);
			throw new DBException(Messages.ERR_SECUR_HASH_ALG, ex);
		}
		
		if(!hashPassword.equals(user.getPassword())){
			throw new AppException(Messages.ERR_AUTH_PASS);
		}
		
		Role role = Role.getRole(user);
		LOG.trace("userRole --> " + role.getName());
		
		String forward = Path.PAGE_ERROR_PAGE;

		if (role == Role.ADMIN) {
			forward = "";
		}

		if (role == Role.CLIENT) {
			forward = Path.COMMAND_ACCOUNT_LIST;
		}

		HttpSession session = request.getSession();
		session.setAttribute("user", user);
		LOG.trace("Set the session attribute: user --> " + user.getLogin());

		session.setAttribute("role", role);
		LOG.trace("Set the session attribute: role --> " + role.getName());

		LOG.debug("User " + user.getLogin() + " ID " + user.getId() + " logged as " + role.getName());

		LOG.debug("FINISH");
		return forward;
	}

}
