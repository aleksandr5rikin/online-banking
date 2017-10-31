package ua.nure.petrikin.OnlineBanking.exception;

public final class Messages {

	private Messages() {
	}
	
	public static final String ERR_SQL_CONNECTION = "Cannot obtain a connection from the pool";
	public static final String ERR_SQL_SAVE_USER = "Cannot save new user";
	public static final String ERR_SQL_SAVE_ACCOUNT = "Cannot save new account";
	public static final String ERR_SQL_SAVE_PAYMENT = "Cannot save payment";
	public static final String ERR_SQL_FIND_USER = "Cannot find user";
	public static final String ERR_SQL_OBTAIN_USERS = "Cannot obtain user list";
	public static final String ERR_SQL_OBTAIN_ACCOUNTS = "Cannot obtain account list";
	public static final String ERR_SQL_OBTAIN_PAYMENTS = "Cannot obtain payments";
	public static final String ERR_SQL_LOCK_ACCOUNT = "Cannot lock account";
	public static final String ERR_LOCK_ACCOUNT = "Account is locked";
	public static final String ERR_LIMIT = "limit is exceeded";
	
	public static final String ERR_UNRESOLVED_PARAM = "Unreolved param!";	
	public static final String ERR_AUTH_PASS = "Passwords don't match";
	public static final String ERR_AUTH_UNCOMFIRMED = "User status ===> 'UNCOMFIRMED'";
	public static final String ERR_SECUR_HASH_ALG = "No such hash algorithem";
}
