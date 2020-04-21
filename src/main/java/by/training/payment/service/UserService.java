package by.training.payment.service;

import java.util.List;

import by.training.payment.entity.User;
import by.training.payment.exception.ServiceException;

/**
 * Interface describing the behavior of classes for working with {@link User}
 * entity.
 * 
 * @author Alexandr Borovets
 * @since JDK1.8
 */
public interface UserService {

	/**
	 * Saves user information to a data source. After successful registration, user
	 * receives a message to the email address. If the password and password
	 * confirmation do not match throws ServiceException. If the login password or
	 * email address is not valid throws ServiceException. If a login or email
	 * address is already in use throws ServiceException.
	 * 
	 * @param user            whose you want to save
	 * @param confirmPassword must match the password specified in user entity
	 * @throws ServiceException if user or confirmPassword null. If the password and
	 *                          password confirmation do not match. If the login
	 *                          password or email address is not valid throws
	 *                          ServiceException. If a login or email address is
	 *                          already in use throws ServiceException.
	 */
	void registerUser(User user, String confirmPassword) throws ServiceException;

	/**
	 * Authorizes the user specified as a parameter. If the user is not found or the
	 * wrong password is specified, throws ServiceException.
	 * 
	 * @param user whose you need to authorize
	 * @return user that matches the login and password passed in user entity as a
	 *         parameter
	 * @throws ServiceException if user or required fields null. If user is not
	 *                          found or the wrong password is specified.
	 */
	User login(User user) throws ServiceException;

	/**
	 * Blocks the user specified as a parameter.
	 * 
	 * @param user whose you need to block
	 * @throws ServiceException if user or required fields null. If the user not
	 *                          found or is already blocked.
	 */
	void blockUser(User user) throws ServiceException;

	/**
	 * Returns the user whose login matches the login passed as a parameter.
	 * 
	 * @param login based on which the user will be searched
	 * @return user whose login matches the login passed as a parameter. If the user
	 *         is not found returns null.
	 * @throws ServiceException an exception occurred while reading data from a data
	 *                          source
	 */
	User getUserByLogin(String login) throws ServiceException;

	/**
	 * Returns a list of all users.
	 * 
	 * @return list of all users, if no users are found returns an empty list. Never
	 *         <code>null</code>.
	 * @throws ServiceException an exception occurred while reading data from a data
	 *                          source
	 */
	List<User> getAllUsers() throws ServiceException;

	/**
	 * Updates the password for the user passed as a parameter. If the new password
	 * and confirmation of the new password do not match, throws ServiceException
	 * 
	 * @param user            who needs to update password
	 * @param oldPassword     old user account password
	 * @param newPassword     new user account password
	 * @param confirmPassword confirmation of a new password from a user account
	 * @throws ServiceException if user or required fields null. If the user not
	 *                          found. If the new password and confirmation of the
	 *                          new password do not match. If the new password
	 *                          matches the old password.
	 */
	void updateUserPassword(User user, String oldPassword, String newPassword, String confirmPassword)
			throws ServiceException;

	/**
	 * Restores the user's password, generates a new random, valid password and
	 * sends it to the user's email address.
	 * 
	 * @param user who needs to recover password
	 * @throws ServiceException if user or required fields null. If the user not
	 *                          found.
	 */
	void restoreUserPassword(User user) throws ServiceException;

	/**
	 * Updates the user name specified as a parameter.
	 * 
	 * @param user who needs to update name
	 * @param name value
	 * @throws ServiceException if user or required fields null. If the user not
	 *                          found. If name is not valid.
	 */
	void updateUserName(User user, String name) throws ServiceException;

	/**
	 * Updates the user surname specified as a parameter.
	 * 
	 * @param user    who needs to update surname
	 * @param surname value
	 * @throws ServiceException if user or required fields null. If the user not
	 *                          found. If surname is not valid.
	 */
	void updateUserSurname(User user, String surname) throws ServiceException;

	/**
	 * Unlocks the user account specified as a parameter.
	 * 
	 * @param user whose you need to unlock
	 * @throws ServiceException if user or required fields null. If the user not
	 *                          found.
	 */
	void unlockUser(User user) throws ServiceException;

	/**
	 * Sends a user message to the system email address.Additionally saves email
	 * address and sender name if they are not valid throws ServiceException.
	 * 
	 * @param name    sender name
	 * @param email   sender email
	 * @param message message text
	 * @throws ServiceException if name, email or message not valid.
	 */
	void handleUserContactUsMessage(String name, String email, String message) throws ServiceException;
}
