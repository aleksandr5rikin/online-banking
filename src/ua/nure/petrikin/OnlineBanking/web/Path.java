package ua.nure.petrikin.OnlineBanking.web;

public final class Path {
	
	private Path(){
		
	}
	
	public static final String PAGE_LOGIN = "/WEB-INF/jsp/login.jsp";
	public static final String PAGE_REGISTRATION = "/WEB-INF/jsp/registration.jsp";
	public static final String PAGE_ERROR_PAGE = "/WEB-INF/jsp/error_page.jsp";
	public static final String PAGE_COMMON_ACCOUNT_LIST = "/WEB-INF/jsp/common/account_list.jsp";
	public static final String PAGE_CLIENT_PAYMENT_LIST = "/WEB-INF/jsp/client/payment_list.jsp";
	public static final String PAGE_CLIENT_EDIT = "/WEB-INF/jsp/client/account_edit.jsp";
	public static final String PAGE_ADMIN_USER_LIST = "/WEB-INF/jsp/admin/user_list.jsp";
	
	public static final String COMMAND_CLIENT_EDIT = "/controller?command=getEdit";
	public static final String COMMAND_PAYMENT_LIST = "/controller?command=paymentList$status=prepeared";
	public static final String COMMAND_GET_LOGIN = "/controller?command=getLogin";
	public static final String COMMAND_ACCOUNT_LIST = "/controller?command=accountList";
	public static final String COMMAND_USER_LIST = "/controller?command=userList";
}
