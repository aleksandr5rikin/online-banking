package ua.nure.petrikin.OnlineBanking.web.command.client;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import ua.nure.petrikin.OnlineBanking.db.AccountDao;
import ua.nure.petrikin.OnlineBanking.db.MySqlDaoFactory;
import ua.nure.petrikin.OnlineBanking.db.PaymentDao;
import ua.nure.petrikin.OnlineBanking.db.entity.Account;
import ua.nure.petrikin.OnlineBanking.db.entity.PayStatus;
import ua.nure.petrikin.OnlineBanking.db.entity.Payment;
import ua.nure.petrikin.OnlineBanking.exception.AppException;
import ua.nure.petrikin.OnlineBanking.exception.Messages;
import ua.nure.petrikin.OnlineBanking.web.Path;
import ua.nure.petrikin.OnlineBanking.web.command.Command;

public class AddPaymentCommand extends Command {

	private static final Logger LOG = Logger.getLogger(AddPaymentCommand.class);

	private static final long serialVersionUID = -7506166075358571214L;

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException, AppException {
		LOG.debug("START");

		String forward = Path.PAGE_ERROR_PAGE;

		LOG.debug(request.getParameter("accountId"));
		LOG.debug(request.getParameter("reciverId"));
		LOG.debug(request.getParameter("sum"));
		LOG.debug(request.getParameter("found"));
		LOG.debug(request.getParameter("comment"));

		String found = request.getParameter("found");
		int accId = Integer.parseInt(request.getParameter("accountId"));
		int sum = Integer.parseInt(request.getParameter("sum"));
		String comment = request.getParameter("comment");
		AccountDao accountDao = MySqlDaoFactory.getInstance().getDao(AccountDao.class);
		Account account = accountDao.get(accId);

		if (account.getAccStatusId() == 2) {
			throw new AppException(Messages.ERR_LOCK_ACCOUNT);
		}

		if (account.getLim() != 0) {
			Timestamp t = account.getDate();
			Calendar c = Calendar.getInstance();
			c.setTimeInMillis(t.getTime());
			c.add(Calendar.MONTH, 1);

			long limitTime = c.getTimeInMillis();
			long currentTime = new Date().getTime();

			if (currentTime > limitTime) {
				t = new Timestamp(limitTime);
				account.setDate(t);
				account.setSpent(0);
				accountDao.save(account);
			}

			if (account.getLim() - account.getSpent() <= 0) {
				throw new AppException(Messages.ERR_LIMIT);
			}
		}

		Payment payment = new Payment();
		payment.setAccId(accId);
		payment.setSum(sum);
		payment.setComment(comment);

		if (found.equals("found")) {
			payment.setReciverId(accId);
			payment.setPayStatusId(PayStatus.SUCEFULL.getId());
		} else {
			int reciverId = Integer.parseInt(request.getParameter("reciverId"));
			payment.setReciverId(reciverId);
			payment.setPayStatusId(PayStatus.PREPEARED.getId());
		}

		PaymentDao paymentDao = MySqlDaoFactory.getInstance().getDao(PaymentDao.class);
		paymentDao.save(payment);

		if (payment.getId() != null) {
			forward = Path.COMMAND_ACCOUNT_LIST;
		}

		LOG.trace("New Payment ID ====> " + payment.getId());
		LOG.debug("FINISH");
		return forward;
	}

}
