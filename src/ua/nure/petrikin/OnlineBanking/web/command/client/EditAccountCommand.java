package ua.nure.petrikin.OnlineBanking.web.command.client;

import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDate;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ua.nure.petrikin.OnlineBanking.db.AccountDao;
import ua.nure.petrikin.OnlineBanking.db.MySqlDaoFactory;
import ua.nure.petrikin.OnlineBanking.db.entity.Account;
import ua.nure.petrikin.OnlineBanking.exception.AppException;
import ua.nure.petrikin.OnlineBanking.web.Path;
import ua.nure.petrikin.OnlineBanking.web.command.Command;

public class EditAccountCommand extends Command {

	private static final long serialVersionUID = 600590137166044490L;

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException, AppException {
		int accId = Integer.parseInt(request.getParameter("accountId"));
		int lim = Integer.parseInt(request.getParameter("lim"));

		AccountDao accountDao = MySqlDaoFactory.getInstance().getDao(AccountDao.class);
		Account account = accountDao.get(accId);
		account.setLim(lim);

		LocalDate localDate = LocalDate.now();
		Timestamp timestamp = Timestamp.valueOf(localDate.atStartOfDay());

		account.setDate(timestamp);
		accountDao.save(account);

		return Path.COMMAND_CLIENT_EDIT + "&accountId=" + accId;
	}

}
