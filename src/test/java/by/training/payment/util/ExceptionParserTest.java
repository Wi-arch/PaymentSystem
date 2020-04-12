package by.training.payment.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

public class ExceptionParserTest {

	private static final String DEFAULT_STATUS = "*Status999*";
	private static final String VALID_EXCEPTION_STATUS = "*Status1000*";

	@Test
	public void testGetExceptionStatusForNull() {
		String exceptionStatus = ExceptionParser.getExceptionStatus(null);
		assertNotNull(exceptionStatus);
	}

	@Test
	public void testGetExceptionStatusPositive() {
		String actual = ExceptionParser.getExceptionStatus(getExceptionWithValidStatus());
		String expected = VALID_EXCEPTION_STATUS;
		assertEquals(expected, actual);
	}

	@Test
	public void testGetExceptionStatusEmpty() {
		String actual = ExceptionParser.getExceptionStatus(getExceptionWithEmptyStatus());
		String expected = DEFAULT_STATUS;
		assertEquals(expected, actual);
	}

	private Exception getExceptionWithValidStatus() {
		Exception validException = new Exception("Valid exception status " + VALID_EXCEPTION_STATUS);
		return validException;
	}

	private Exception getExceptionWithEmptyStatus() {
		Exception exceptionWithEmptyStatus = new Exception("Empty exception status");
		return exceptionWithEmptyStatus;
	}
}
