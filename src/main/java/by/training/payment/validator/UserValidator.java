package by.training.payment.validator;

import by.training.payment.exception.ServiceException;

public class UserValidator {

	private final static String LOGIN_REGEX = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{6,}$";
	private final static String PASSWORD_REGEX = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[^\\s\\w])[\\S]{6,}$";
	private final static String EMAIL_REGEX = "[a-zA-Z0-9]{1}[a-zA-Z0-9_.-]{1,29}[a-zA-Z0-9]{1}@[a-zA-Z]{3,10}\\.[a-zA-Z]{2,}";

	public void checkLogin(String login) throws ServiceException {
		if (login == null || !login.matches(LOGIN_REGEX)) {
			throw new ServiceException("Login not valid");
		}
	}

	public void checkPassword(String password) throws ServiceException {
		if (password == null || !password.matches(PASSWORD_REGEX)) {
			throw new ServiceException("Password not valid");
		}
	}

	public void checkEmail(String email) throws ServiceException {
		if (email == null || !email.matches(EMAIL_REGEX)) {
			throw new ServiceException("Email not valid");
		}
	}
}
