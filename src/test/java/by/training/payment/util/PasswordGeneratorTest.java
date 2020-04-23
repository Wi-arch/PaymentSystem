package by.training.payment.util;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class PasswordGeneratorTest {

	/** Regular expression to validate user password */
	private static final String PASSWORD_REGEX = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*([^\\s\\w]|[_]))[\\S]{6,}$";

	@Test
	public void testGenerateRandomValidPasswordPositive() {
		String password = PasswordGenerator.generateRandomValidPassword();
		assertTrue(checkIsPasswordValid(password));
	}

	@Test
	public void testGenerateRandomValidPasswordForNull() {
		String password = PasswordGenerator.generateRandomValidPassword();
		assertNotNull(password);
	}

	private boolean checkIsPasswordValid(String password) {
		return password != null && password.matches(PASSWORD_REGEX);
	}
}
