package ua.nure.petrikin.OnlineBanking.web.command.client;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import ua.nure.petrikin.OnlineBanking.db.MySqlDaoFactory;
import ua.nure.petrikin.OnlineBanking.db.PaymentDao;
import ua.nure.petrikin.OnlineBanking.db.entity.Payment;
import ua.nure.petrikin.OnlineBanking.db.entity.User;
import ua.nure.petrikin.OnlineBanking.exception.AppException;
import ua.nure.petrikin.OnlineBanking.web.Path;
import ua.nure.petrikin.OnlineBanking.web.command.Command;

public class PaymentListCommand extends Command {

	private static final Logger LOG = Logger.getLogger(PaymentListCommand.class);

	private static final long serialVersionUID = 4036718055099290958L;

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException, AppException {
		LOG.debug("START");

		HttpSession session = request.getSession(false);
		User user = (User) session.getAttribute("user");
		int accId = Integer.parseInt(request.getParameter("accountId"));

		PaymentDao paymentDao = MySqlDaoFactory.getInstance().getDao(PaymentDao.class);
		List<Payment> payments = paymentDao.findUserPayment(user.getId());

		Iterator<Payment> it = payments.iterator();
		while (it.hasNext()) {
			Payment p = it.next();
			if (p.getAccId() != accId && p.getPayStatusId() == 1) {
				it.remove();
			}
		}

		request.setAttribute("payments", payments);

		LOG.trace("List<Payment> size ====> " + payments.size());
		LOG.debug("FINISH");
		return Path.PAGE_CLIENT_PAYMENT_LIST;
	}

}
