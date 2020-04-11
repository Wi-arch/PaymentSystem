package by.training.payment.service;

import java.util.List;

import by.training.payment.entity.User;
import by.training.payment.exception.ServiceException;

public interface UserService {

	void registerUser(User user, String confirmPassword) throws ServiceException;

	User login(User user) throws ServiceException;

	void blockUser(User user) throws ServiceException;

	User getUserByLogin(String login) throws ServiceException;

	List<User> getAllUsers() throws ServiceException;

	void updateUserPassword(User user, String oldPassword, String newPassword, String confirmPassword)
			throws ServiceException;

	void restoreUserPassword(User user) throws ServiceException;

	void updateUserName(User user, String name) throws ServiceException;

	void updateUserSurname(User user, String surname) throws ServiceException;

	void unlockUser(User user) throws ServiceException;
}
