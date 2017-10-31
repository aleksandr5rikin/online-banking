package ua.nure.petrikin.OnlineBanking.web.command.client;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import ua.nure.petrikin.OnlineBanking.db.AccountDao;
import ua.nure.petrikin.OnlineBanking.db.MySqlDaoFactory;
import ua.nure.petrikin.OnlineBanking.db.entity.AccStatus;
import ua.nure.petrikin.OnlineBanking.db.entity.Account;
import ua.nure.petrikin.OnlineBanking.db.entity.User;
import ua.nure.petrikin.OnlineBanking.exception.AppException;
import ua.nure.petrikin.OnlineBanking.web.Path;
import ua.nure.petrikin.OnlineBanking.web.command.Command;

public class AddAccountCommand extends Command {

	private static final Logger LOG = Logger.getLogger(AddAccountCommand.class);

	private static final long serialVersionUID = -4060341422635798377L;

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException, AppException {
		LOG.debug("START");

		String forward = Path.PAGE_ERROR_PAGE;
		HttpSession session = request.getSession(false);

		String name = request.getParameter("name");
		User user = (User) session.getAttribute("user");

		Account account = new Account();
		account.setName(name);
		account.setUserId(user.getId());
		account.setAccStatusId(AccStatus.UNLOCKED.getId());

		AccountDao accountDao = MySqlDaoFactory.getInstance().getDao(AccountDao.class);
		accountDao.save(account);

		if (account.getId() == null) {
			forward = Path.COMMAND_ACCOUNT_LIST;
		}

		LOG.trace("New Account ID ====> " + account.getId());
		LOG.debug("FINISH");
		return forward;
	}

}