package by.training.payment.validator;

import by.training.payment.entity.User;
import by.training.payment.exception.ServiceException;

public class UserValidator {

	private static final String LOGIN_REGEX = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[\\S]{6,}$";
	private static final String PASSWORD_REGEX = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*([^\\s\\w]|[_]))[\\S]{6,}$";
	private static final String EMAIL_REGEX = "[a-zA-Z0-9]{1}[a-zA-Z0-9_.-]{1,29}[a-zA-Z0-9]{1}@[a-zA-Z]{2,10}\\.[a-zA-Z]{2,}";
	private static final String NAME_REGEX = "\\p{L}+.*\\p{L}+";

	public void checkIsLoginValid(String login) throws ServiceException {
		if (login == null || !login.matches(LOGIN_REGEX)) {
			throw new ServiceException("Login not valid *Status1005*");
		}
	}

	public void checkIsPasswordValid(String password) throws ServiceException {
		if (password == null || !password.matches(PASSWORD_REGEX)) {
			throw new ServiceException("Password not valid *Status1006*");
		}
	}

	public void checkIsEmailValid(String email) throws ServiceException {
		if (email == null || !email.matches(EMAIL_REGEX)) {
			throw new ServiceException("Email not valid *Status1007*");
		}
	}

	public void checkUserIsBlocked(User user) throws ServiceException {
		checkUserForNull(user);
		if (user.getIsBlocked()) {
			throw new ServiceException("User blocked *Status1001*");
		}
	}

	public void checkUserLoginPasswordForNull(User user) throws ServiceException {
		checkUserLoginForNull(user);
		if (user.getPassword() == null) {
			throw new ServiceException("User fields null *Status1000*");
		}
	}

	public void comparePasswords(String password, String confirmPassword) throws ServiceException {
		if (password != null && !password.equals(confirmPassword)) {
			throw new ServiceException("Passwords not match *Status1002*");
		}
	}

	public void checkRequiredUserFieldsForNull(User user) throws ServiceException {
		checkUserLoginPasswordForNull(user);
		if (user.getEmail() == null || user.getUserRole() == null) {
			throw new ServiceException("User fields null *Status1000*");
		}
	}

	public void checkUserName(String name) throws ServiceException {
		if (name == null || !name.matches(NAME_REGEX)) {
			throw new ServiceException("Invalid user name *Status1012*");
		}
	}

	public void checkUserSurname(String surname) throws ServiceException {
		if (surname == null || !surname.matches(NAME_REGEX)) {
			throw new ServiceException("Invalid user name *Status1013*");
		}
	}

	public void compareOldPasswordAndNewPassword(String oldPassword, String newPassword) throws ServiceException {
		if (oldPassword != null && oldPassword.equals(newPassword)) {
			throw new ServiceException("New password should not match the old password *Status1015*");
		}
	}

	public void checkUserLoginForNull(User user) throws ServiceException {
		checkUserForNull(user);
		if (user.getLogin() == null) {
			throw new ServiceException("Null user login *Status1000*");
		}
	}

	public void checkUserForNull(User user) throws ServiceException {
		if (user == null) {
			throw new ServiceException("Null user");
		}
	}
}
