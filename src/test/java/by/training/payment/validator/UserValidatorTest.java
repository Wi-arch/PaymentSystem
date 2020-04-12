package by.training.payment.validator;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import by.training.payment.entity.User;
import by.training.payment.entity.UserRole;
import by.training.payment.exception.ServiceException;

public class UserValidatorTest {

	private static final UserValidator USER_VALIDATOR = new UserValidator();
	private static final UserRole VALID_USER_ROLE = new UserRole("User");
	private static final String VALID_LOGIN = "Login5";
	private static final String INVALID_LOGIN = "login5";
	private static final String VALID_PASSWORD = "Password+999";
	private static final String INVALID_PASSWORD = "Password999";
	private static final String VALID_EMAIL = "test12@gmail.com";
	private static final String INVALID_EMAIL = "test12@gamil.";
	private static final String VALID_USER_NAME = "Tommy";
	private static final String INVALID_USER_NAME = " 2346+-";
	private User validUser = new User();

	@Before
	public void init() {
		validUser = new User();
		validUser.setBlocked(false);
		validUser.setEmail(VALID_EMAIL);
		validUser.setLogin(VALID_LOGIN);
		validUser.setPassword(VALID_PASSWORD);
		validUser.setName(VALID_USER_NAME);
		validUser.setUserRole(VALID_USER_ROLE);
	}

	@Test
	public void testCheckIsLoginValidPositive() throws ServiceException {
		USER_VALIDATOR.checkIsLoginValid(VALID_LOGIN);
	}

	@Test(expected = ServiceException.class)
	public void testCheckIsLoginValidNegative() throws ServiceException {
		USER_VALIDATOR.checkIsLoginValid(INVALID_LOGIN);
	}

	@Test
	public void testCheckIsPasswordValidPositive() throws ServiceException {
		USER_VALIDATOR.checkIsPasswordValid(VALID_PASSWORD);
	}

	@Test(expected = ServiceException.class)
	public void testCheckIsPasswordValidNegative() throws ServiceException {
		USER_VALIDATOR.checkIsPasswordValid(INVALID_PASSWORD);
	}

	@Test
	public void testCheckIsEmailValidPositive() throws ServiceException {
		USER_VALIDATOR.checkIsEmailValid(VALID_EMAIL);
	}

	@Test(expected = ServiceException.class)
	public void testCheckIsEmailValidNegative() throws ServiceException {
		USER_VALIDATOR.checkIsEmailValid(INVALID_EMAIL);
	}

	@Test
	public void testCheckUserIsBlockedPositive() throws ServiceException {
		validUser.setBlocked(false);
		USER_VALIDATOR.checkUserIsBlocked(validUser);
	}

	@Test(expected = ServiceException.class)
	public void testCheckUserIsBlockedNegative() throws ServiceException {
		validUser.setBlocked(true);
		USER_VALIDATOR.checkUserIsBlocked(validUser);
	}

	@Test
	public void testCheckUserLoginPasswordForNullPositive() throws ServiceException {
		USER_VALIDATOR.checkUserLoginPasswordForNull(validUser);
	}

	@Test(expected = ServiceException.class)
	public void testCheckUserLoginPasswordForNullNegative() throws ServiceException {
		validUser.setPassword(null);
		USER_VALIDATOR.checkUserLoginPasswordForNull(validUser);
	}

	@Test
	public void testComparePasswordsPositive() throws ServiceException {
		USER_VALIDATOR.comparePasswords(VALID_PASSWORD, VALID_PASSWORD);
		assertTrue(true);
	}

	@Test(expected = ServiceException.class)
	public void testComparePasswordsNegative() throws ServiceException {
		USER_VALIDATOR.comparePasswords(VALID_PASSWORD, INVALID_PASSWORD);
	}

	@Test
	public void testCheckRequiredUserFieldsForNullPositive() throws ServiceException {
		USER_VALIDATOR.checkRequiredUserFieldsForNull(validUser);
	}

	@Test(expected = ServiceException.class)
	public void testCheckRequiredUserFieldsForNullNegative() throws ServiceException {
		validUser.setUserRole(null);
		USER_VALIDATOR.checkRequiredUserFieldsForNull(validUser);
	}

	@Test
	public void testCheckUserNamePositive() throws ServiceException {
		USER_VALIDATOR.checkUserName(VALID_USER_NAME);
	}

	@Test(expected = ServiceException.class)
	public void testCheckUserNameNegative() throws ServiceException {
		USER_VALIDATOR.checkUserName(INVALID_USER_NAME);
	}

	@Test
	public void testCheckUserLoginForNullPositive() throws ServiceException {
		USER_VALIDATOR.checkUserLoginForNull(validUser);
	}

	@Test(expected = ServiceException.class)
	public void testCheckUserLoginForNullNegative() throws ServiceException {
		validUser.setLogin(null);
		USER_VALIDATOR.checkUserLoginForNull(validUser);
	}

	@Test
	public void testCheckUserForNullPositive() throws ServiceException {
		USER_VALIDATOR.checkUserForNull(validUser);
	}

	@Test(expected = ServiceException.class)
	public void testCheckUserForNullNegative() throws ServiceException {
		validUser = null;
		USER_VALIDATOR.checkUserForNull(validUser);
	}
}
