package ua.nure.petrikin.OnlineBanking.web.command;

import java.util.Map;
import java.util.TreeMap;

import org.apache.log4j.Logger;

import ua.nure.petrikin.OnlineBanking.web.command.admin.UnlockAccountCommand;
import ua.nure.petrikin.OnlineBanking.web.command.client.AddAccountCommand;
import ua.nure.petrikin.OnlineBanking.web.command.client.AddPaymentCommand;
import ua.nure.petrikin.OnlineBanking.web.command.client.CartCommand;
import ua.nure.petrikin.OnlineBanking.web.command.client.ConfirmEmailCommand;
import ua.nure.petrikin.OnlineBanking.web.command.client.ConfirmPayCommand;
import ua.nure.petrikin.OnlineBanking.web.command.client.EditAccountCommand;
import ua.nure.petrikin.OnlineBanking.web.command.client.GetEditCommand;
import ua.nure.petrikin.OnlineBanking.web.command.client.LockAccountCommand;
import ua.nure.petrikin.OnlineBanking.web.command.client.PaymentListCommand;
import ua.nure.petrikin.OnlineBanking.web.command.client.RequestAccountCommand;
import ua.nure.petrikin.OnlineBanking.web.common.AccountListCommand;
import ua.nure.petrikin.OnlineBanking.web.common.LogoutCommand;

public class CommandContainer {
	
	private static final Logger LOG = Logger.getLogger(CommandContainer.class);
	
	private static Map<String, Command> commands = new TreeMap<String, Command>();
	
	static {
		LOG.info("Start");
		
		commands.put("login", new LoginCommand());
		commands.put("logout", new LogoutCommand());
		commands.put("registration", new RegistrationCommand());
		commands.put("unlockStatus", new UnlockAccountCommand());
		commands.put("lockAccount", new LockAccountCommand());
		commands.put("requestAccount", new RequestAccountCommand());
		commands.put("confirmPay", new ConfirmPayCommand());
		commands.put("confirmEmail", new ConfirmEmailCommand());
		commands.put("getLogin", new GetLoginCommand());
		commands.put("getRegistration", new GetRegistrationCommand());
		commands.put("accountList", new AccountListCommand());
		commands.put("paymentList", new PaymentListCommand());
		commands.put("addAccount", new AddAccountCommand());
		commands.put("addPayment", new AddPaymentCommand());
		commands.put("cart", new CartCommand());
		commands.put("editAccount", new EditAccountCommand());
		commands.put("getEdit", new GetEditCommand());
		
		LOG.trace("Number of commands --> " + commands.size());
		LOG.info("Container was successfully initialized");
	}

	public static Command get(String commandName) {
		if (commandName == null || !commands.containsKey(commandName)) {
			LOG.trace("Command not found, name --> " + commandName);
			return commands.get("getLogin"); 
		}
		
		return commands.get(commandName);
	}
	
}
