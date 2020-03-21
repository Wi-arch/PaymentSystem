package by.training.payment.service.impl;

import java.util.List;

import static org.apache.commons.codec.digest.DigestUtils.sha1Hex;

import by.training.payment.dao.UserDAO;
import by.training.payment.entity.User;
import by.training.payment.exception.DAOException;
import by.training.payment.exception.ServiceException;
import by.training.payment.factory.DAOFactory;
import by.training.payment.service.UserService;
import by.training.payment.validator.UserValidator;

public class UserServiceImpl implements UserService {

	private final UserValidator userValidator = new UserValidator();
	private final UserDAO userDAO = DAOFactory.getInstance().getUserDAO();

	@Override
	public void registerUser(User user) throws ServiceException {
		checkUserFieldsForNull(user);
		isLoginPasswordEmailValid(user);
		try {
			if (userDAO.getUserByLogin(user.getLogin()) != null) {
				throw new ServiceException("Login is already exist");
			}
			if (userDAO.getUserByEmail(user.getEmail()) != null) {
				throw new ServiceException("Email is already exist");
			}
			user.setPassword(sha1Hex(user.getPassword()));
			userDAO.addUser(user);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public User login(User user) throws ServiceException {
		checkUserFieldsForNull(user);
		isLoginPasswordValid(user);
		user.setPassword(sha1Hex(user.getPassword()));
		try {
			return userDAO.getUserByLoginAndPassword(user.getLogin(), user.getPassword());
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public void updateUser(User user) throws ServiceException {
		checkUserFieldsForNull(user);
		try {
			userDAO.updateUser(user);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public User getUserById(int id) throws ServiceException {
		try {
			return userDAO.getUserById(id);
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

	private void isLoginPasswordValid(User user) throws ServiceException {
		userValidator.checkLogin(user.getLogin());
		userValidator.checkPassword(user.getPassword());
	}

	private void isLoginPasswordEmailValid(User user) throws ServiceException {
		isLoginPasswordValid(user);
		userValidator.checkEmail(user.getEmail());
	}

	private void checkUserFieldsForNull(User user) throws ServiceException {
		if (user == null || user.getEmail() == null || user.getLogin() == null || user.getPassword() == null
				|| user.getUserRole() == null) {
			throw new ServiceException("User fields null");
		}
	}

}
