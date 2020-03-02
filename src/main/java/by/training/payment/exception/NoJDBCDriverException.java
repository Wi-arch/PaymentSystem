package by.training.payment.exception;

@SuppressWarnings("serial")
public class NoJDBCDriverException extends Exception {

	public NoJDBCDriverException() {
	}

	public NoJDBCDriverException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public NoJDBCDriverException(String message, Throwable cause) {
		super(message, cause);
	}

	public NoJDBCDriverException(String message) {
		super(message);
	}

	public NoJDBCDriverException(Throwable cause) {
		super(cause);
	}

}
