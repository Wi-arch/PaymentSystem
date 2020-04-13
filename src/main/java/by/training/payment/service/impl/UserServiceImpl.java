package by.training.payment.service.impl;

import java.util.List;

import static org.apache.commons.codec.digest.DigestUtils.sha1Hex;

import by.training.payment.dao.UserDAO;
import by.training.payment.entity.User;
import by.training.payment.exception.DAOException;
import by.training.payment.exception.ServiceException;
import by.training.payment.factory.DAOFactory;
import by.training.payment.service.UserService;
import by.training.payment.util.MailSender;
import by.training.payment.util.PasswordGenerator;
import by.training.payment.validator.UserValidator;

public class UserServiceImpl implements UserService {

	private final UserValidator userValidator = new UserValidator();
	private final UserDAO userDAO = DAOFactory.getInstance().getUserDAO();
	private final MailSender mailSender = MailSender.getInstance();
	private static final String REGISTRATION_SUBJECT = "Регистрация завершена успешно";
	private static final String RESET_PASSWORD_SUBJECT = "Восстановление пароля";
	private static final String REGISTRATION_TEXT = "Спасибо за регистрацию на PaymentSystem. Ваш логин ";
	private static final String RESET_PASSWORD_TEXT = "Пароль успешно изменён. Новый пароль ";

	@Override
	public void registerUser(User user, String confirmPassword) throws ServiceException {
		userValidator.checkRequiredUserFieldsForNull(user);
		isLoginPasswordEmailValid(user);
		userValidator.comparePasswords(user.getPassword(), confirmPassword);
		try {
			if (userDAO.getUserByLogin(user.getLogin()) != null) {
				throw new ServiceException("Login is already exist *Status1003*");
			}
			if (userDAO.getUserByEmail(user.getEmail()) != null) {
				throw new ServiceException("Email is already exist *Status1004*");
			}
			user.setPassword(sha1Hex(user.getPassword()));
			userDAO.addUser(user);
			mailSender.sendMessage(user.getEmail(), REGISTRATION_SUBJECT, REGISTRATION_TEXT + user.getLogin());
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public User login(User user) throws ServiceException {
		userValidator.checkUserLoginPasswordForNull(user);
		user.setPassword(sha1Hex(user.getPassword()));
		User existingUser = null;
		try {
			existingUser = userDAO.getUserByLoginAndPassword(user.getLogin(), user.getPassword());
			userValidator.checkUserCanLogin(existingUser);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
		return existingUser;
	}

	@Override
	public User getUserByLogin(String login) throws ServiceException {
		try {
			return userDAO.getUserByLogin(login);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public List<User> getAllUsers() throws ServiceException {
		try {
			return userDAO.getAllUsers();
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public void blockUser(User user) throws ServiceException {
		userValidator.checkUserLoginForNull(user);
		try {
			User existingUser = userDAO.getUserByLogin(user.getLogin());
			userValidator.checkUserLoginForNull(existingUser);
			userValidator.checkUserIsBlocked(existingUser);
			existingUser.setBlocked(true);
			updateUser(existingUser);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public void updateUserPassword(User user, String oldPassword, String newPassword, String confirmPassword)
			throws ServiceException {
		userValidator.checkRequiredUserFieldsForNull(user);
		if (oldPassword == null || !user.getPassword().equals(sha1Hex(oldPassword))) {
			throw new ServiceException("Wrong user password *Status1011*");
		}
		userValidator.comparePasswords(newPassword, confirmPassword);
		userValidator.checkIsPasswordValid(newPassword);
		userValidator.compareOldPasswordAndNewPassword(oldPassword, newPassword);
		user.setPassword(sha1Hex(newPassword));
		updateUser(user);
	}

	@Override
	public void restoreUserPassword(User user) throws ServiceException {
		isLoginEmailValid(user);
		try {
			User existingUser = userDAO.getUserByLoginAndEmail(user.getLogin(), user.getEmail());
			if (existingUser == null) {
				throw new ServiceException("User with specified login and email does not exist *Status1016*");
			}
			String newPassowrd = PasswordGenerator.generateRandomValidPassword();
			existingUser.setPassword(sha1Hex(newPassowrd));
			updateUser(existingUser);
			mailSender.sendMessage(user.getEmail(), RESET_PASSWORD_SUBJECT, RESET_PASSWORD_TEXT + newPassowrd);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public void updateUserName(User user, String name) throws ServiceException {
		userValidator.checkRequiredUserFieldsForNull(user);
		if (name != null && !name.isEmpty()) {
			userValidator.checkUserName(name);
		}
		user.setName(name);
		updateUser(user);
	}

	@Override
	public void updateUserSurname(User user, String surname) throws ServiceException {
		userValidator.checkRequiredUserFieldsForNull(user);
		if (surname != null && !surname.isEmpty()) {
			userValidator.checkUserSurname(surname);
		}
		user.setSurname(surname);
		updateUser(user);
	}

	@Override
	public void unlockUser(User user) throws ServiceException {
		userValidator.checkUserLoginForNull(user);
		try {
			User existingUser = userDAO.getUserByLogin(user.getLogin());
			existingUser.setBlocked(false);
			updateUser(existingUser);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}

	private void isLoginPasswordEmailValid(User user) throws ServiceException {
		isLoginEmailValid(user);
		userValidator.checkIsPasswordValid(user.getPassword());
	}

	private void isLoginEmailValid(User user) throws ServiceException {
		userValidator.checkIsLoginValid(user.getLogin());
		userValidator.checkIsEmailValid(user.getEmail());
	}

	private void updateUser(User user) throws ServiceException {
		userValidator.checkRequiredUserFieldsForNull(user);
		try {
			userDAO.updateUser(user);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}
}
