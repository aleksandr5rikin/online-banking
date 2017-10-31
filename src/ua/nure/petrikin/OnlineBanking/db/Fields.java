package ua.nure.petrikin.OnlineBanking.db;

public final class Fields {
	private Fields(){
		
	}

	public static final String USER_ID = "id";
	public static final String USER_NAME = "name";
	public static final String USER_ROLE_ID = "role_id";
	public static final String USER_STATUS_ID = "user_status_id";
	public static final String USER_LOGIN = "login";
	public static final String USER_PASSWORD = "password";
	public static final String USER_EMAIL = "email";
	public static final String USER_TOKEN = "token";
	public static final String USER_SALT = "salt";
	
	public static final String ACCOUNT_ID = "id";
	public static final String ACCOUNT_NAME = "name";
	public static final String ACCOUNT_USER_ID = "user_id";
	public static final String ACCOUNT_STATUS_ID = "acc_status_id";
	public static final String ACCOUNT_BALANCE = "balance";
	public static final String ACCOUNT_REQUEST = "request";
	public static final String ACCOUNT_LIM = "lim";
	public static final String ACCOUNT_DATE = "date";
	public static final String ACCOUNT_SPENT = "spent";
	
	public static final String PAYMENT_ID = "id";
	public static final String PAYMENT_ACCOUNT_ID = "account_id";
	public static final String PAYMENT_RECIVER_ID = "reciver_id";
	public static final String PAYMENT_SUM = "sum";
	public static final String PAYMENT_STATUS_ID = "pay_status_id";
	public static final String PAYMENT_COMMENT = "comment";
	public static final String PAYMENT_DATE = "date";
	
	public static final String PAY_NOTICE_MSG = "message";
}
