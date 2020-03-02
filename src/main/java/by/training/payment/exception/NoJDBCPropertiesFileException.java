package by.training.payment.exception;

@SuppressWarnings("serial")
public class NoJDBCPropertiesFileException extends Exception {

	public NoJDBCPropertiesFileException() {
	}

	public NoJDBCPropertiesFileException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public NoJDBCPropertiesFileException(String message, Throwable cause) {
		super(message, cause);
	}

	public NoJDBCPropertiesFileException(String message) {
		super(message);
	}

	public NoJDBCPropertiesFileException(Throwable cause) {
		super(cause);
	}
}
