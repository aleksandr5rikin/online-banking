package ua.nure.petrikin.OnlineBanking.web.common;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import ua.nure.petrikin.OnlineBanking.web.Path;
import ua.nure.petrikin.OnlineBanking.web.command.Command;

public class LogoutCommand extends Command{

	private static final long serialVersionUID = 3658358534929790935L;

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		
		HttpSession session = request.getSession(false);
		if (session != null) {
			session.invalidate();
		}

		return Path.COMMAND_GET_LOGIN;
	}
}
