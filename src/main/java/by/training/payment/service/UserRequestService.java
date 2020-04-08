package by.training.payment.service;

import java.util.List;

import by.training.payment.entity.BankAccount;
import by.training.payment.entity.Card;
import by.training.payment.entity.CardExpirationDate;
import by.training.payment.entity.Currency;
import by.training.payment.entity.PaymentSystem;
import by.training.payment.entity.UserRequest;
import by.training.payment.exception.ServiceException;

public interface UserRequestService {

	void saveRequestToUnlockCard(UserRequest userRequest, Card card) throws ServiceException;

	void saveRequestToOpenNewAccount(UserRequest userRequest, Currency currency) throws ServiceException;

	void saveRequestToOpenNewCard(UserRequest userRequest, Currency currency, PaymentSystem paymentSystem,
			CardExpirationDate cardExpirationDate) throws ServiceException;

	void saveRequestToOpenNewCardToExistingAccount(UserRequest userRequest, BankAccount bankAccount,
			PaymentSystem paymentSystem, CardExpirationDate cardExpirationDate) throws ServiceException;

	List<UserRequest> getAllUserRequestsByUserLogin(String login) throws ServiceException;

	List<UserRequest> getAllUserRequests() throws ServiceException;

	List<UserRequest> getAllUserRequestsInProcessing() throws ServiceException;
}
