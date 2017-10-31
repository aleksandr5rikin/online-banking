package ua.nure.petrikin.OnlineBanking.exception;

public class ConnectionNotAvailableException extends AppException {

	private static final long serialVersionUID = 530988069619320888L;

	public ConnectionNotAvailableException(){
		super();
	}
	
	public ConnectionNotAvailableException(Throwable cause){
		super(cause);
	}
	
	public ConnectionNotAvailableException(String message){
		super(message);
	}
	
	public ConnectionNotAvailableException(String message, Throwable cause){
		super(message, cause);
	}
}
