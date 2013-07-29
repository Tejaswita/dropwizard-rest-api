package exception;

public class InvalidCredentialsException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7984694666378621616L;

	public InvalidCredentialsException(String message, Exception exception) {
		super(message, exception);
	}

	public InvalidCredentialsException(String message) {
		super(message);
	}

}
