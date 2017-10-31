package ua.nure.petrikin.OnlineBanking.web.command.client;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import ua.nure.petrikin.OnlineBanking.db.AccountDao;
import ua.nure.petrikin.OnlineBanking.db.MySqlDaoFactory;
import ua.nure.petrikin.OnlineBanking.db.entity.Account;
import ua.nure.petrikin.OnlineBanking.exception.AppException;
import ua.nure.petrikin.OnlineBanking.web.Path;
import ua.nure.petrikin.OnlineBanking.web.command.Command;

public class RequestAccountCommand extends Command {

	private static final Logger LOG = Logger.getLogger(RequestAccountCommand.class);

	private static final long serialVersionUID = 7829732638125667803L;

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException, AppException {
		LOG.debug("START");

		int accId = Integer.parseInt(request.getParameter("accountId"));
		String forward = Path.COMMAND_CLIENT_EDIT + "&accountId=" + accId;

		AccountDao accountDao = MySqlDaoFactory.getInstance().getDao(AccountDao.class);
		Account acc = accountDao.get(accId);
		acc.setRequest(true);
		acc = accountDao.save(acc);

		if (acc.getId() == null) {
			forward = Path.PAGE_ERROR_PAGE;
		}

		LOG.trace("Account ID ====> " + acc.getId());
		LOG.debug("FINISH");
		return forward;
	}

}
