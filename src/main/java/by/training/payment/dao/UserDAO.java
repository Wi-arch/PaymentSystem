package by.training.payment.dao;

import java.util.List;

import by.training.payment.entity.User;
import by.training.payment.exception.DAOException;

public interface UserDAO {

	void addUser(User user) throws DAOException;

	void updateUser(User user) throws DAOException;

	void deleteUser(User user) throws DAOException;

	List<User> getAllUsers() throws DAOException;

	User getUserById(int id) throws DAOException;

	User getUserByLogin(String login) throws DAOException;

	User getUserByEmail(String email) throws DAOException;

	User getUserByLoginAndPassword(String login, String password) throws DAOException;

	User getUserByLoginAndEmail(String login, String email) throws DAOException;

}
