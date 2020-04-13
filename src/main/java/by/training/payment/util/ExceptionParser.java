package by.training.payment.util;

/**
 * The {@link ExceptionParser} used to extract exception status from
 * {@link Throwable}.
 * 
 * @author Alexandr Borovets
 * @since JDK1.8
 */
public class ExceptionParser {

	/**
	 * Default exception status. Used in case if status is not found in
	 * {@link Throwable}
	 */
	private static final String DEFAULT_STATUS = "*Status999*";

	/**
	 * Regular expression to used to extract exception status from {@link Throwable}
	 */
	private static final String STATUS_REGEX = ".*(\\*Status\\d+\\*).*";

	/**
	 * Private constructor prohibiting the creation of class instances.
	 */
	private ExceptionParser() {
	}

	/**
	 * Used to extract exception status from {@link Throwable}. If {@link Throwable}
	 * is <code>null<code> or {@link Throwable} does not contain exception status
	 * return {@link ExceptionParser#DEFAULT_STATUS}.
	 * 
	 * @param e {@link Throwable} value from which the exception status will be
	 *          extracted.
	 * @return {@link String} value containing exception status extracted from {@link
	 *         Throwable}. If {@link Throwable} is <code>null<code> or
	 *         {@link Throwable} does not contain exception status return
	 *         {@link ExceptionParser#DEFAULT_STATUS}. Never <code>null</code>.
	 */
	public static String getExceptionStatus(Throwable e) {
		if (e == null) {
			return DEFAULT_STATUS;
		}
		String status = getSourceCause(e).toString();
		return status.matches(STATUS_REGEX) ? status.replaceAll(STATUS_REGEX, "$1") : DEFAULT_STATUS;
	}

	/**
	 * @param e value from which the source of the exception is extracted
	 * @return {@link Throwable} value containing the source of the exception passed
	 *         as a parameter to the method
	 */
	private static Throwable getSourceCause(Throwable e) {
		Throwable cause = null;
		Throwable result = e;
		while ((cause = result.getCause()) != null && (result != cause)) {
			result = cause;
		}
		return result;
	}
}
