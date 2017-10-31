package ua.nure.petrikin.OnlineBanking.web.command.client;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import ua.nure.petrikin.OnlineBanking.db.AccountDao;
import ua.nure.petrikin.OnlineBanking.db.MySqlDaoFactory;
import ua.nure.petrikin.OnlineBanking.db.NoticeDao;
import ua.nure.petrikin.OnlineBanking.db.PaymentDao;
import ua.nure.petrikin.OnlineBanking.db.entity.Account;
import ua.nure.petrikin.OnlineBanking.db.entity.Notice;
import ua.nure.petrikin.OnlineBanking.db.entity.PayStatus;
import ua.nure.petrikin.OnlineBanking.db.entity.Payment;
import ua.nure.petrikin.OnlineBanking.db.entity.User;
import ua.nure.petrikin.OnlineBanking.exception.AppException;
import ua.nure.petrikin.OnlineBanking.web.Path;
import ua.nure.petrikin.OnlineBanking.web.command.Command;
import ua.nure.petrikin.OnlineBanking.web.websocket.ClientWebsocket;

public class ConfirmPayCommand extends Command {

	private static final Logger LOG = Logger.getLogger(ConfirmPayCommand.class);

	private static final long serialVersionUID = -3224495433136774938L;

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException, AppException {
		LOG.debug("START");

		String forward = Path.COMMAND_PAYMENT_LIST;

		HttpSession session = request.getSession(false);
		User user = (User) session.getAttribute("user");

		int recAccId = Integer.parseInt(request.getParameter("recAccId"));
		int payId = Integer.parseInt(request.getParameter("paymentId"));

		AccountDao accountDao = MySqlDaoFactory.getInstance().getDao(AccountDao.class);
		PaymentDao paymentDao = MySqlDaoFactory.getInstance().getDao(PaymentDao.class);
		NoticeDao noticeDao = MySqlDaoFactory.getInstance().getDao(NoticeDao.class);

		Account account = accountDao.get(recAccId);

		Payment p = paymentDao.get(payId);
		p.setPayStatusId(PayStatus.SUCEFULL.getId());
		p = paymentDao.save(p);

		if (p.getId() == null) {
			forward = Path.PAGE_ERROR_PAGE;
		} else {
			Notice notice = new Notice();
			notice.setUserId(account.getUserId());
			notice.setMessage("TEST");
			noticeDao.save(notice);
			ClientWebsocket.notifySend(account.getUserId());

			notice = new Notice();
			notice.setUserId(user.getId());
			notice.setMessage("TEST");
			noticeDao.save(notice);
			ClientWebsocket.notifySend(user.getId());
		}

		LOG.trace("Confirmed pay ID ====> " + payId);
		LOG.debug("FINISH");
		return forward;
	}

}
