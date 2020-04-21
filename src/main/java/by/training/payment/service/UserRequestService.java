package by.training.payment.service;

import java.util.List;

import by.training.payment.entity.BankAccount;
import by.training.payment.entity.Card;
import by.training.payment.entity.CardExpiry;
import by.training.payment.entity.Currency;
import by.training.payment.entity.PaymentSystem;
import by.training.payment.entity.UserRequest;
import by.training.payment.exception.ServiceException;

/**
 * Interface describing the behavior of classes for working with
 * {@link UserRequest} entity.
 * 
 * @author Alexandr Borovets
 * @since JDK1.8
 */
public interface UserRequestService {

	/**
	 * Saves user request to unlock the card passed as a parameter.
	 * 
	 * @param userRequest to be saved
	 * @param card        to be unlocked
	 * @throws ServiceException if userRequest required fields or card number null
	 */
	void saveRequestToUnlockCard(UserRequest userRequest, Card card) throws ServiceException;

	/**
	 * Saves user request to open bank account with the currency specified as a
	 * parameter.
	 * 
	 * @param userRequest to be saved
	 * @param currency    in which a bank account is to be opened
	 * @throws ServiceException if userRequest required fields or currency null
	 */
	void saveRequestToOpenNewAccount(UserRequest userRequest, Currency currency) throws ServiceException;

	/**
	 * Saves user request for opening a payment card with the following parameters:
	 * currency, payment system and expiration date.
	 * 
	 * @param userRequest        to be saved
	 * @param currency           in which a payment card is to be opened
	 * @param paymentSystem      payment system in which a payment card must be open
	 * @param cardExpirationDate payment card validity
	 * @throws ServiceException if userRequest required fields null or currency,
	 *                          paymentSystem or cardExpirationDate null
	 */
	void saveRequestToOpenNewCard(UserRequest userRequest, Currency currency, PaymentSystem paymentSystem,
			CardExpiry cardExpirationDate) throws ServiceException;

	/**
	 * Saves user request to open a payment card to an existing bank account with
	 * the following parameters: payment system and expiration date.
	 * 
	 * @param userRequest        to be saved
	 * @param bankAccount        bank account to which a payment card should be
	 *                           opened
	 * @param paymentSystem      payment system in which a payment card must be open
	 * @param cardExpirationDate payment card validity
	 * @throws ServiceException if userRequest required fields null or bankAccount,
	 *                          paymentSystem or cardExpirationDate null
	 */
	void saveRequestToOpenNewCardToExistingAccount(UserRequest userRequest, BankAccount bankAccount,
			PaymentSystem paymentSystem, CardExpiry cardExpirationDate) throws ServiceException;

	/**
	 * Returns a list containing user requests belonging to the user login specified
	 * as a parameter. If no user requests are found, returns an empty list.
	 * 
	 * @param login
	 * @return list containing user requests belonging to the user login specified
	 *         as a parameter. If no user requests are found, returns an empty list.
	 *         Never <code>null</code>.
	 * @throws ServiceException if an exception occurred while reading data from a
	 *                          data source
	 */
	List<UserRequest> getAllUserRequestsByUserLogin(String login) throws ServiceException;

	/**
	 * Returns a list containing all user requests. If no user requests are found,
	 * returns an empty list.
	 * 
	 * @return list containing all user requests. If no user requests are found,
	 *         returns an empty list. Never <code>null</code>.
	 * @throws ServiceException if an exception occurred while reading data from a
	 *                          data source
	 */
	List<UserRequest> getAllUserRequests() throws ServiceException;

	/**
	 * Returns a list of user requests that are in process. If no user requests are
	 * found, returns an empty list.
	 * 
	 * @return list of user requests that are in process. If no user requests are
	 *         found, returns an empty list.
	 * @throws ServiceException if an exception occurred while reading data from a
	 *                          data source
	 */
	List<UserRequest> getAllUserRequestsInProcessing() throws ServiceException;

	/**
	 * Processes the user request, setting the request status to rejected.
	 * 
	 * @param userRequest to be processed
	 * @throws ServiceException if userRequest null or if the request has already
	 *                          been processed
	 */
	void rejectUserRequest(UserRequest userRequest) throws ServiceException;

	/**
	 * Processes the user request and then sets the value processed to
	 * {@link UserRequest#getRequestStatus()}.
	 * 
	 * @param userRequest to be processed
	 * @throws ServiceException if userRequest null or if the request has already
	 *                          been processed
	 */
	void handleUserRequest(UserRequest userRequest) throws ServiceException;
}
