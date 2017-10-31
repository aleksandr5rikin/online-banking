package ua.nure.petrikin.OnlineBanking.web.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import ua.nure.petrikin.OnlineBanking.db.entity.Role;
import ua.nure.petrikin.OnlineBanking.web.Path;

public class AccessFilter implements Filter{
	
	private static final Logger LOG = Logger.getLogger(AccessFilter.class);
	
	private Map<Role, List<String>> accessMap = new HashMap<Role, List<String>>();
	private List<String> commons = new ArrayList<String>();
	private List<String> guests = new ArrayList<String>();
	private List<String> outOfControl = new ArrayList<String>();
	private String forward = Path.COMMAND_GET_LOGIN;
	
	public void destroy() {
		LOG.debug("DESTROY");
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		LOG.debug("START");
		
		if (accessAllowed(request)) {
			LOG.debug("FINISH");
			chain.doFilter(request, response);
		} else {
			
			LOG.warn("You do not have permission to access the requested resource: ");
			LOG.warn("ForwardUrl: " + forward);
			request.getRequestDispatcher(forward).forward(request, response);
		}
	}
	
	private boolean accessAllowed(ServletRequest request) {
			HttpServletRequest httpRequest = (HttpServletRequest) request;

		LOG.debug("Required resourse: " + httpRequest.getRequestURI());
		String commandName = httpRequest.getParameter("command");
		LOG.trace("Command name: " + commandName);
		if (commandName == null || commandName.isEmpty()) {
			commandName = "getLogin";
		}
		
		if (outOfControl.contains(commandName)) {
			return true;
		}
		
		HttpSession session = httpRequest.getSession(false);
		if (session == null || session.getAttribute("role") == null) {
			LOG.trace("You are --> Guest");
			if (guests.contains(commandName)) {
				return true;
			}
			return false;
		}
		
		Role role = (Role)session.getAttribute("role");
		
		LOG.trace("You are --> " + role);
		
		if(role == Role.CLIENT){
			forward = "controller?command=accountList";
		}
		
		if(role == Role.ADMIN){
			forward = "controller?command=usersList";
		}
		
		return accessMap.get(role).contains(commandName)
				|| commons.contains(commandName);
	}

	public void init(FilterConfig fConfig) throws ServletException {
		LOG.debug("Filter initialization starts");
		
		for(Role role : Role.values()){
			accessMap.put(role, asList(fConfig.getInitParameter(role.getName())));
		}
		LOG.trace("Access map --> " + accessMap);

		commons = asList(fConfig.getInitParameter("common"));
		LOG.trace("Common commands --> " + commons);

		guests = asList(fConfig.getInitParameter("guest"));
		LOG.trace("Guest commands --> " + guests);
		
		outOfControl = asList(fConfig.getInitParameter("out-of-control"));
		LOG.trace("Out of control commands --> " + outOfControl);
		
		LOG.debug("Filter initialization finished");
	}
	
	/**
	 * Extracts parameter values from string.
	 * 
	 * @param str
	 *            parameter values string.
	 * @return list of parameter values.
	 */
	private List<String> asList(String str) {
		List<String> list = new ArrayList<String>();
		StringTokenizer st = new StringTokenizer(str);
		while (st.hasMoreTokens()) {
			list.add(st.nextToken());
		}
		return list;		
	}
}
