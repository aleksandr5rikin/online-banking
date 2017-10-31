package ua.nure.petrikin.OnlineBanking.exception;

public class DBException extends AppException {

	private static final long serialVersionUID = 5148107885744381178L;

	public DBException() {
		super();
	}
	
	public DBException(Throwable cause) {
		super(cause);
	}

	public DBException(String message, Throwable cause) {
		super(message, cause);
	}

	public DBException(String message) {
		super(message);
	}
}
