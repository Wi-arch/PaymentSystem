package by.training.payment.validator;

import org.apache.commons.lang3.StringUtils;

import by.training.payment.entity.User;
import by.training.payment.exception.ServiceException;

/**
 * The {@link UserValidator} class validates {@link User} entity.
 * 
 * @author Alexandr Borovets
 * @since JDK1.8
 *
 */
public class UserValidator {

	/** Regular expression to validate user login */
	private static final String LOGIN_REGEX = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[\\S]{6,}$";

	/** Regular expression to validate user password */
	private static final String PASSWORD_REGEX = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*([^\\s\\w]|[_]))[\\S]{6,}$";

	/** Regular expression to validate user email */
	private static final String EMAIL_REGEX = "[a-zA-Z0-9]{1}[a-zA-Z0-9_.-]{1,29}[a-zA-Z0-9]{1}@[a-zA-Z]{2,10}\\.[a-zA-Z]{2,}";

	/** Regular expression to validate user name */
	private static final String NAME_REGEX = "\\p{L}+.*\\p{L}+";

	/**
	 * 
	 * @param login {@link String} to be validated
	 * @throws ServiceException if the specified login is null or if login does not
	 *                          match {@link UserValidator#LOGIN_REGEX}
	 */
	public void checkIsLoginValid(String login) throws ServiceException {
		if (login == null || !login.matches(LOGIN_REGEX)) {
			throw new ServiceException("Login not valid *Status1005*");
		}
	}

	/**
	 * 
	 * @param password {@link String} to be validated
	 * @throws ServiceException if the specified password is null or if password
	 *                          does not match {@link UserValidator#PASSWORD_REGEX}
	 */
	public void checkIsPasswordValid(String password) throws ServiceException {
		if (password == null || !password.matches(PASSWORD_REGEX)) {
			throw new ServiceException("Password not valid *Status1006*");
		}
	}

	/**
	 * 
	 * @param email {@link String} to be validated
	 * @throws ServiceException if the specified email is null or if email does not
	 *                          match {@link UserValidator#EMAIL_REGEX}
	 */
	public void checkIsEmailValid(String email) throws ServiceException {
		if (email == null || !email.matches(EMAIL_REGEX)) {
			throw new ServiceException("Email not valid *Status1007*");
		}
	}

	/**
	 * 
	 * @param user {@link User} to be validated
	 * @throws ServiceException if the specified user is null or if
	 *                          {@link User#getIsBlocked()} return true
	 */
	public void checkUserIsBlocked(User user) throws ServiceException {
		checkUserForNull(user);
		if (user.getIsBlocked()) {
			throw new ServiceException("User blocked *Status1001*");
		}
	}

	/**
	 * 
	 * @param user {@link User} to be validated
	 * @throws ServiceException if the specified user is null or if
	 *                          {@link User#getLogin()} or
	 *                          {@link User#getPassword()} return null value
	 */
	public void checkUserLoginPasswordForNull(User user) throws ServiceException {
		checkUserLoginForNull(user);
		if (user.getPassword() == null) {
			throw new ServiceException("User fields null *Status1000*");
		}
	}

	/**
	 * Compares whether password and confirmPassword match, if not, throws an
	 * ServiceException.
	 * 
	 * @param password        {@link String} to be compared
	 * @param confirmPassword {@link String} to be compared
	 * @throws ServiceException if password or confirmPassword null, or password not
	 *                          equals to confirmPassword
	 *                          {@code !password.equals(confirmPassword)}
	 */
	public void comparePasswords(String password, String confirmPassword) throws ServiceException {
		if (password != null && !password.equals(confirmPassword)) {
			throw new ServiceException("Passwords not match *Status1002*");
		}
	}

	/**
	 * Checks for required fields in entity {@link User} for null.
	 * 
	 * @param user {@link User} to be validated
	 * @throws ServiceException if {@link User} or one of required fields are null
	 */
	public void checkRequiredUserFieldsForNull(User user) throws ServiceException {
		checkUserLoginPasswordForNull(user);
		if (user.getEmail() == null || user.getUserRole() == null) {
			throw new ServiceException("User fields null *Status1000*");
		}
	}

	/**
	 * 
	 * @param name {@link String} to be validated
	 * @throws ServiceException if the specified name is null or if name does not
	 *                          match {@link UserValidator#NAME_REGEX}
	 */
	public void checkUserName(String name) throws ServiceException {
		if (name == null || !name.matches(NAME_REGEX)) {
			throw new ServiceException("Invalid user name *Status1012*");
		}
	}

	/**
	 * 
	 * @param surname {@link String} to be validated
	 * @throws ServiceException if the specified surname is null or if surname does
	 *                          not match {@link UserValidator#NAME_REGEX}
	 */
	public void checkUserSurname(String surname) throws ServiceException {
		if (surname == null || !surname.matches(NAME_REGEX)) {
			throw new ServiceException("Invalid user name *Status1013*");
		}
	}

	/**
	 * If {@link User} null or blocked throws ServiceException
	 * 
	 * @param user {@link User} to be validated
	 * @throws ServiceException if {@link User} null or {@link User#getIsBlocked()}
	 *                          return true
	 */
	public void checkUserCanLogin(User user) throws ServiceException {
		if (user == null) {
			throw new ServiceException("Wrong user login or password *Status1008*");
		}
		checkUserIsBlocked(user);
	}

	/**
	 * Compares the old and new passwords if they match throws an ServiceException
	 * 
	 * @param oldPassword {@link String} to be compared
	 * @param newPassword {@link String} to be compared
	 * @throws ServiceException if oldPassword equal to newPassword
	 */
	public void compareOldPasswordAndNewPassword(String oldPassword, String newPassword) throws ServiceException {
		if (oldPassword != null && oldPassword.equals(newPassword)) {
			throw new ServiceException("New password should not match the old password *Status1015*");
		}
	}

	/**
	 * 
	 * @param user {@link User} to be validated
	 * @throws ServiceException if {@link User} null or {@link User#getLogin()}
	 *                          return null value
	 */
	public void checkUserLoginForNull(User user) throws ServiceException {
		checkUserForNull(user);
		if (user.getLogin() == null) {
			throw new ServiceException("Null user login *Status1000*");
		}
	}

	/**
	 * 
	 * @param user {@link User}
	 * @throws ServiceException if {@link User} null
	 */
	public void checkUserForNull(User user) throws ServiceException {
		if (user == null) {
			throw new ServiceException("Null user");
		}
	}

	/**
	 * @param message {@link String} to be validated
	 * @throws ServiceException if message null or blank
	 */
	public void checkIsMessageBlank(String message) throws ServiceException {
		if (StringUtils.isBlank(message)) {
			throw new ServiceException("Blank message *Status1029*");
		}
	}
}
