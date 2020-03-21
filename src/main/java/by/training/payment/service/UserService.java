package by.training.payment.service;

import java.util.List;

import by.training.payment.entity.User;
import by.training.payment.exception.ServiceException;

public interface UserService {

	void registerUser(User user) throws ServiceException;

	User login(User user) throws ServiceException;

	void updateUser(User user) throws ServiceException;

	User getUserById(int id) throws ServiceException;

	List<User> getAllUsers() throws ServiceException;
}
