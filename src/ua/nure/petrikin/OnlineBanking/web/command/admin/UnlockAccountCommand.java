package ua.nure.petrikin.OnlineBanking.web.command.admin;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import ua.nure.petrikin.OnlineBanking.db.AccountDao;
import ua.nure.petrikin.OnlineBanking.db.MySqlDaoFactory;
import ua.nure.petrikin.OnlineBanking.db.entity.Account;
import ua.nure.petrikin.OnlineBanking.db.entity.Role;
import ua.nure.petrikin.OnlineBanking.exception.AppException;
import ua.nure.petrikin.OnlineBanking.web.Path;
import ua.nure.petrikin.OnlineBanking.web.command.Command;

public class UnlockAccountCommand extends Command{

	private static final Logger LOG = Logger.getLogger(UnlockAccountCommand.class);
	
	private static final long serialVersionUID = 4328421026354385306L;

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException, AppException {
		LOG.debug("START");
		
		String forward = Path.COMMAND_ACCOUNT_LIST;
		
		HttpSession session = request.getSession(false);
		Role role = (Role) session.getAttribute("role");
		
		int accId = Integer.parseInt(request.getParameter("accountId"));
		int statusId = Integer.parseInt(request.getParameter("statusId"));
		
		AccountDao accountDao = MySqlDaoFactory.getInstance().getDao(AccountDao.class);
		Account acc = accountDao.get(accId);
		
		if(role == Role.ADMIN || statusId == 2){
			acc.setAccStatusId(statusId);
		} else if (statusId == 1){
			acc.setRequest(true);
		}
		acc = accountDao.save(acc);
		
		if(acc.getId() == null){
			forward = Path.PAGE_ERROR_PAGE;
		}
		
		LOG.trace("Account ID ====> " + acc.getId());
		LOG.debug("FINISH");
 		return forward;
	}
}
