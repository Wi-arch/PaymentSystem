package by.training.payment.service.impl;

import java.util.List;

import by.training.payment.dao.UserRequestDAO;
import by.training.payment.entity.BankAccount;
import by.training.payment.entity.Card;
import by.training.payment.entity.CardExpirationDate;
import by.training.payment.entity.Currency;
import by.training.payment.entity.PaymentSystem;
import by.training.payment.entity.RequestParameter;
import by.training.payment.entity.UserRequest;
import by.training.payment.exception.DAOException;
import by.training.payment.exception.ServiceException;
import by.training.payment.factory.DAOFactory;
import by.training.payment.factory.RequestParameterBuilder;
import by.training.payment.service.UserRequestService;

public class UserRequestServiceImpl implements UserRequestService {

	private final UserRequestDAO userRequestDAO = DAOFactory.getInstance().getUserRequestDAO();
	private final RequestParameterBuilder builder = new RequestParameterBuilder();

	@Override
	public List<UserRequest> getAllUserRequestsByUserLogin(String login) throws ServiceException {
		try {
			return userRequestDAO.getAllUserRequestsByUserLogin(login);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public void saveRequestToUnlockCard(UserRequest userRequest, Card card) throws ServiceException {
		RequestParameter cardParameter = builder.buildCardRequestParameter(card);
		List<RequestParameter> requestParameterList = builder.buildRequestParameterList(cardParameter);
		saveUserRequestWithParameterList(userRequest, requestParameterList);
	}

	@Override
	public void saveRequestToOpenNewCard(UserRequest userRequest, Currency currency, PaymentSystem paymentSystem,
			CardExpirationDate cardExpirationDate) throws ServiceException {
		RequestParameter currencyParameter = builder.buildCurrencyRequestParameter(currency);
		RequestParameter paymentSystemParameter = builder.buildPaymentSystemRequestParameter(paymentSystem);
		RequestParameter cardExpirationDateParameter = builder
				.buildCardExpirationDateRequestParameter(cardExpirationDate);
		List<RequestParameter> requestParameterList = builder.buildRequestParameterList(currencyParameter,
				paymentSystemParameter, cardExpirationDateParameter);
		saveUserRequestWithParameterList(userRequest, requestParameterList);
	}

	@Override
	public void saveRequestToOpenNewAccount(UserRequest userRequest, Currency currency) throws ServiceException {
		RequestParameter currencyParameter = builder.buildCurrencyRequestParameter(currency);
		List<RequestParameter> requestParameterList = builder.buildRequestParameterList(currencyParameter);
		saveUserRequestWithParameterList(userRequest, requestParameterList);
	}

	@Override
	public void saveRequestToOpenNewCardToExistingAccount(UserRequest userRequest, BankAccount bankAccount,
			PaymentSystem paymentSystem, CardExpirationDate cardExpirationDate) throws ServiceException {
		RequestParameter bankAccountParameter = builder.buildBankAccountRequestParameter(bankAccount);
		RequestParameter paymentSystemParameter = builder.buildPaymentSystemRequestParameter(paymentSystem);
		RequestParameter cardExpirationDateParameter = builder
				.buildCardExpirationDateRequestParameter(cardExpirationDate);
		List<RequestParameter> requestParameterList = builder.buildRequestParameterList(bankAccountParameter,
				paymentSystemParameter, cardExpirationDateParameter);
		saveUserRequestWithParameterList(userRequest, requestParameterList);
	}

	@Override
	public List<UserRequest> getAllUserRequests() throws ServiceException {
		try {
			return userRequestDAO.getAllUserRequests();
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public List<UserRequest> getAllUserRequestsInProcessing() throws ServiceException {
		try {
			return userRequestDAO.getAllUserRequestsInProcessing();
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}

	private void saveUserRequestWithParameterList(UserRequest userRequest, List<RequestParameter> requestParameterList)
			throws ServiceException {
		checkRequiredUserRequestFieldsForNull(userRequest);
		checkRequestParameterListForNull(requestParameterList);
		try {
			userRequestDAO.saveUserRequestWithParameterList(userRequest, requestParameterList);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}

	private void checkRequiredUserRequestFieldsForNull(UserRequest userRequest) throws ServiceException {
		if (userRequest == null || userRequest.getUser() == null || userRequest.getUser().getLogin() == null
				|| userRequest.getRequestType() == null || userRequest.getRequestType().getValue() == null) {
			throw new ServiceException("Null user request fields");
		}
	}

	private void checkRequestParameterListForNull(List<RequestParameter> requestParameterList) throws ServiceException {
		if (requestParameterList == null) {
			throw new ServiceException("Null request parameter list");
		}
		for (RequestParameter requestParameter : requestParameterList) {
			if (requestParameter == null) {
				throw new ServiceException("Null request parameter");
			}
		}
	}

}
