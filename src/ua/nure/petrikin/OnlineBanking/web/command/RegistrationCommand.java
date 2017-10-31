package ua.nure.petrikin.OnlineBanking.web.command;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

public class RegistrationCommand extends Command{

	private static final long serialVersionUID = 4326976197519870765L;
	
	private static final Logger LOG = Logger.getLogger(RegistrationCommand.class);

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException, AppException {
		LOG.debug("START");

		User user = new User();
		
		String forward = Path.PAGE_ERROR_PAGE;
		
		String login = request.getParameter("login");
		String name = request.getParameter("name");
		String email = request.getParameter("email");
		String salt = SecureUtils.getNextSalt();
		String passwordTmp = request.getParameter("password");
		try {
			String password = SecureUtils.hash( passwordTmp + salt, "SHA-256");
			String token = SecureUtils.hash(email, "MD5");
			user.setLogin(login);
			user.setRoleId(Role.CLIENT.getId());
			user.setUserStatusId(UserStatus.UNCOMFIRMED.getId());
			user.setName(name);
			user.setEmail(email);
			user.setSalt(salt);
			user.setPassword(password);
			user.setToken(token);
			
			UserDao userDao = MySqlDaoFactory.getInstance().getDao(UserDao.class);
			
			user = userDao.save(user);
			
			if(user.getId() == 0){
				throw new DBException(Messages.ERR_SQL_SAVE_USER);
			}
			
			String text = "Confirm link: http://" 
					+ request.getServerName() + ":" 
					+ request.getServerPort() + "/" 
					+ request.getRequestURI() + "?command=confirmEmail&token=" 
					+ token + "&id="
					+ user.getId();
			
			SecureUtils.sendEmail(email, text);
			forward = Path.COMMAND_GET_LOGIN;
			
			LOG.trace("New unconfirm user ====> " + "['ID':" + user.getId() + ",'Login':" 
					+ login + ",'Role':" + Role.CLIENT.getName());
			
		} catch (DBException | NoSuchAlgorithmException ex) {
			LOG.error(ex.getMessage(), ex);
		}

		LOG.debug("FINISH");
		return forward;
	}
}
