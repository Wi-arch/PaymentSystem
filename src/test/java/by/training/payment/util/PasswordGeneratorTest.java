package by.training.payment.util;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import by.training.payment.exception.ServiceException;
import by.training.payment.validator.UserValidator;

public class PasswordGeneratorTest {

	private static final UserValidator USER_VALIDATOR = new UserValidator();

	@Test
	public void testGenerateRandomValidPasswordPositive() throws ServiceException {
		String password = PasswordGenerator.generateRandomValidPassword();
		USER_VALIDATOR.checkIsPasswordValid(password);
	}

	@Test
	public void testGenerateRandomValidPasswordForNull() throws ServiceException {
		String password = PasswordGenerator.generateRandomValidPassword();
		assertNotNull(password);
	}
}
