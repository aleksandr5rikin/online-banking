package ua.nure.petrikin.OnlineBanking.web.command;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ua.nure.petrikin.OnlineBanking.exception.AppException;
import ua.nure.petrikin.OnlineBanking.web.Path;

public class GetLoginCommand extends Command{

	private static final long serialVersionUID = -7072093818445430277L;

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException, AppException {
		return Path.PAGE_LOGIN;
	}

}
