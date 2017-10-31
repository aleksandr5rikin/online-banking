package ua.nure.petrikin.OnlineBanking.web.filter;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;

import org.apache.log4j.Logger;

import ua.nure.petrikin.OnlineBanking.exception.AppException;
import ua.nure.petrikin.OnlineBanking.exception.Messages;
import ua.nure.petrikin.OnlineBanking.web.Path;
import ua.nure.petrikin.OnlineBanking.web.util.Param;
import ua.nure.petrikin.OnlineBanking.web.util.ParamContainer;

@WebFilter(servletNames = "Controller")
public class ValidationFilter implements Filter{

	private static final Logger LOG = Logger.getLogger(ValidationFilter.class);
	
	@Override
	public void destroy() {
		LOG.debug("DESTROY");
		
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
			throws IOException, ServletException {
		LOG.debug("START");

		boolean valid = true;
		
		String commandName = req.getParameter("command");
		
		LOG.trace("Request command ====> " + commandName);

		List<Param> requiredParams = ParamContainer.get(commandName);
		LOG.debug("requiredParams ====> " + requiredParams);
		
		try {
			for(Param param : requiredParams){
				String name = param.getName();
				LOG.debug("Param ====> " + name);
				String providetParam = req.getParameter(name);
				if(providetParam == null){
					LOG.warn("Providet param '" + name + "' == null");
					throw new AppException(Messages.ERR_UNRESOLVED_PARAM);
				}
				Map<String, String> tests = param.getTests();
				for(Map.Entry<String, String> entry : tests.entrySet()){
					Pattern p = Pattern.compile(entry.getValue());
					Matcher m = p.matcher(providetParam);
					if(!m.find()){
						String msg = entry.getKey() + ": " + providetParam;
						LOG.warn(msg);
						throw new AppException(msg);
					}
				}
				LOG.warn("Providet param ['" + name + "' : '" +providetParam + "'] is valid");
			}
		} catch (AppException ex) {
			valid = false;
			req.setAttribute("errorMessage", ex.getMessage());
			LOG.warn("Forward address ====> " + Path.PAGE_ERROR_PAGE);
			req.getRequestDispatcher(Path.PAGE_ERROR_PAGE).forward(req, resp);
		}
		
		if(valid){
			LOG.debug("FINISHED SUCEFULL");	
			chain.doFilter(req, resp);
		}
		
	}
	


	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		LOG.debug("INIT");
	}

}
