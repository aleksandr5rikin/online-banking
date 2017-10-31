package ua.nure.petrikin.OnlineBanking.web.command.client;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ua.nure.petrikin.OnlineBanking.db.AccountDao;
import ua.nure.petrikin.OnlineBanking.db.MySqlDaoFactory;
import ua.nure.petrikin.OnlineBanking.db.entity.Account;
import ua.nure.petrikin.OnlineBanking.exception.AppException;
import ua.nure.petrikin.OnlineBanking.web.Path;
import ua.nure.petrikin.OnlineBanking.web.command.Command;

public class GetEditCommand extends Command {

	private static final long serialVersionUID = 6385501825519240047L;

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException, AppException {
		int accountId = Integer.parseInt(request.getParameter("accountId"));
		AccountDao accountDao = MySqlDaoFactory.getInstance().getDao(AccountDao.class);
		Account account = accountDao.get(accountId);
		request.setAttribute("account", account);
		return Path.PAGE_CLIENT_EDIT;
	}

}
