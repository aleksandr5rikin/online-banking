package ua.nure.petrikin.OnlineBanking.web.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.log4j.Logger;

public class ParamContainer {
private static final Logger LOG = Logger.getLogger(ParamContainer.class);
	
	private static Map<String, List<Param>> params = new TreeMap<>();
	
	static {
		Param id = new Param();
		id.setName("id");
		id.put("Id is not correct", "^\\d+$");
		
		Param login = new Param();
		login.setName("login");
		login.put("Login must be more then 5 sumbols", "^.{4,}$");
		login.put("Login is not correct", "^\\w+$");
		
		Param password = new Param();
		password.setName("password");
		password.put("Password is not correct", "^[\\w\\p{Punct}]{8,}$");
		
		Param name = new Param();
		name.setName("name");
		name.put("Name is not correct", "(?U)^\\w+$");
		
		Param email = new Param();
		email.setName("email");
		email.put("Email is not correct", "^[\\w\\._-]+@[\\w]+\\.[A-Za-z]{2,6}$");
		
		params.put("empty", new ArrayList<>());
		
		List<Param> tmp = new ArrayList<>();	
		tmp = new ArrayList<>();
		tmp.add(login);
		tmp.add(password);
		params.put("login", tmp);
		
		tmp = new ArrayList<>();
		tmp.add(login);
		tmp.add(password);
		tmp.add(name);
		tmp.add(email);
		params.put("registration", tmp);
		
		tmp = new ArrayList<>();
		tmp.add(name);
		params.put("createAccount", tmp);
		
		
		LOG.debug("Validation params container was successfully initialized");
		LOG.trace("Number of validation request params --> " + params.size());
	}

	public static List<Param> get(String commandName) {
		if (commandName == null || !params.containsKey(commandName)) {
			LOG.trace("Validation dont need");
			return params.get("empty"); 
		}
		
		return params.get(commandName);
	}
}