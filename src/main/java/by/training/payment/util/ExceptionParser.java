package by.training.payment.util;

public class ExceptionParser {

	private static final String DEFAULT_STATUS = "*Status999*";
	private static final String STATUS_REGEX = ".*(\\*Status\\d+\\*).*";

	private ExceptionParser() {
	}

	public static String getExceptionStatus(Throwable e) {
		if (e == null) {
			return DEFAULT_STATUS;
		}
		String status = getSourceCause(e).toString();
		return status.matches(STATUS_REGEX) ? status.replaceAll(STATUS_REGEX, "$1") : DEFAULT_STATUS;
	}

	private static Throwable getSourceCause(Throwable e) {
		Throwable cause = null;
		Throwable result = e;
		while ((cause = result.getCause()) != null && (result != cause)) {
			result = cause;
		}
		return result;
	}
}
