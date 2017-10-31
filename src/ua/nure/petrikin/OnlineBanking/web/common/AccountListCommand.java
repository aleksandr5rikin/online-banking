package ua.nure.petrikin.OnlineBanking.web.common;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import ua.nure.petrikin.OnlineBanking.db.AccountDao;
import ua.nure.petrikin.OnlineBanking.db.MySqlDaoFactory;
import ua.nure.petrikin.OnlineBanking.db.entity.Account;
import ua.nure.petrikin.OnlineBanking.db.entity.Role;
import ua.nure.petrikin.OnlineBanking.db.entity.User;
import ua.nure.petrikin.OnlineBanking.exception.AppException;
import ua.nure.petrikin.OnlineBanking.web.Path;
import ua.nure.petrikin.OnlineBanking.web.command.Command;

public class AccountListCommand extends Command{
	
	private static final long serialVersionUID = 1466243881286735402L;
	
	private static final Logger LOG = Logger.getLogger(AccountListCommand.class);

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException, AppException {
		LOG.debug("START");
		
		HttpSession session = request.getSession(false);
		Role role = (Role)session.getAttribute("role");
		
		AccountDao accountDao = MySqlDaoFactory.getInstance().getDao(AccountDao.class);
		List<Account> accounts = accountDao.getAll();
		
		int userId = 0;
		if(role == Role.CLIENT){
			User user = (User)session.getAttribute("user");
			LOG.trace("User ==== > " + user.getId() + " " +user.getName());
			userId = user.getId();
		} else if (role == Role.ADMIN){
			userId = Integer.parseInt(request.getParameter("userId"));
		}
		
		Iterator<Account> it = accounts.iterator();
		while( it.hasNext() ) {
		  Account a = it.next();
		  if( a.getUserId() != userId){
			  it.remove();
		  }
		}
		
		request.setAttribute("accounts", accounts);
		
		LOG.trace("List<Account> size ===> " + accounts.size());
		LOG.debug("FINISH");
		return Path.PAGE_COMMON_ACCOUNT_LIST;
	}

	
}
