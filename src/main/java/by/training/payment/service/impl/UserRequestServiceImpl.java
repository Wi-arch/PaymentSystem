package by.training.payment.service.impl;

import static by.training.payment.factory.ParameterHeaderFactory.BANK_ACCOUNT_NUMBER_PARAMETER_HEADER;
import static by.training.payment.factory.ParameterHeaderFactory.CARD_EXPIRY_PARAMETER_HEADER;
import static by.training.payment.factory.ParameterHeaderFactory.CARD_NUMBER_PARAMETER_HEADER;
import static by.training.payment.factory.ParameterHeaderFactory.CURRENCY_NAME_PARAMETER_HEADER;
import static by.training.payment.factory.ParameterHeaderFactory.PAYMENT_SYSTEM_PARAMETER_HEADER;
import static by.training.payment.factory.RequestStatusFactory.REJECTED_STATUS;
import static by.training.payment.factory.RequestTypeFactory.OPEN_NEW_ACCOUNT_REQUEST;
import static by.training.payment.factory.RequestTypeFactory.OPEN_NEW_CARD_REQUEST;
import static by.training.payment.factory.RequestTypeFactory.OPEN_NEW_CARD_TO_EXISTING_ACCOUNT_REQUEST;
import static by.training.payment.factory.RequestTypeFactory.UNLOCK_CARD_REQUEST;
import static org.apache.commons.codec.digest.DigestUtils.sha1Hex;

import java.util.Date;
import java.util.List;

import by.training.payment.dao.CardDAO;
import by.training.payment.dao.RequestParameterDAO;
import by.training.payment.dao.UserRequestDAO;
import by.training.payment.entity.BankAccount;
import by.training.payment.entity.Card;
import by.training.payment.entity.CardExpiry;
import by.training.payment.entity.Currency;
import by.training.payment.entity.ParameterHeader;
import by.training.payment.entity.PaymentSystem;
import by.training.payment.entity.RequestParameter;
import by.training.payment.entity.RequestStatus;
import by.training.payment.entity.User;
import by.training.payment.entity.UserRequest;
import by.training.payment.exception.DAOException;
import by.training.payment.exception.ServiceException;
import by.training.payment.factory.DAOFactory;
import by.training.payment.factory.ParameterHeaderFactory;
import by.training.payment.factory.RequestParameterBuilder;
import by.training.payment.factory.RequestStatusFactory;
import by.training.payment.service.UserRequestService;
import by.training.payment.util.BankAccountNumberGenerator;
import by.training.payment.util.CardNumberGenerator;
import by.training.payment.util.CardUtil;
import by.training.payment.util.MailSender;
import by.training.payment.validator.CardValidator;
import by.training.payment.validator.RequestParameterValidator;
import by.training.payment.validator.UserRequestValidator;

public class UserRequestServiceImpl implements UserRequestService {

	private final DAOFactory daoFactory = DAOFactory.getInstance();
	private final RequestParameterDAO requestParameterDAO = daoFactory.getRequestParameterDAO();
	private final UserRequestDAO userRequestDAO = daoFactory.getUserRequestDAO();
	private final CardDAO cardDAO = daoFactory.getCardDAO();
	private final ParameterHeaderFactory headerFactory = ParameterHeaderFactory.getInstance();
	private final RequestParameterBuilder builder = RequestParameterBuilder.getInstance();
	private final RequestStatusFactory requestStatusFactory = RequestStatusFactory.getInstance();
	private final RequestStatus rejectedStatus = requestStatusFactory.getRequestStatus(REJECTED_STATUS);
	private final ParameterHeader accountNumberHeader = headerFactory.getParameterHeader(BANK_ACCOUNT_NUMBER_PARAMETER_HEADER);
	private final ParameterHeader cardExpiryHeader = headerFactory.getParameterHeader(CARD_EXPIRY_PARAMETER_HEADER);
	private final ParameterHeader cardNumberHeader = headerFactory.getParameterHeader(CARD_NUMBER_PARAMETER_HEADER);
	private final ParameterHeader currencyHeader = headerFactory.getParameterHeader(CURRENCY_NAME_PARAMETER_HEADER);
	private final ParameterHeader paymentSystemHeader = headerFactory.getParameterHeader(PAYMENT_SYSTEM_PARAMETER_HEADER);
	private final CardValidator cardValidator = new CardValidator();
	private final UserRequestValidator userRequestValidator = new UserRequestValidator();
	private final RequestParameterValidator requestParameterValidator = new RequestParameterValidator();
	private final MailSender mailSender = MailSender.getInstance();

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
			CardExpiry cardExpiry) throws ServiceException {
		RequestParameter currencyParameter = builder.buildCurrencyRequestParameter(currency);
		RequestParameter paymentSystemParameter = builder.buildPaymentSystemRequestParameter(paymentSystem);
		RequestParameter cardExpiryParameter = builder.buildCardExpirationDateRequestParameter(cardExpiry);
		List<RequestParameter> requestParameterList = builder.buildRequestParameterList(currencyParameter,
				paymentSystemParameter, cardExpiryParameter);
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
			PaymentSystem paymentSystem, CardExpiry cardExpiration) throws ServiceException {
		RequestParameter bankAccountParameter = builder.buildBankAccountRequestParameter(bankAccount);
		RequestParameter paymentSystemParameter = builder.buildPaymentSystemRequestParameter(paymentSystem);
		RequestParameter cardExpirationParameter = builder.buildCardExpirationDateRequestParameter(cardExpiration);
		List<RequestParameter> requestParameterList = builder.buildRequestParameterList(bankAccountParameter,
				paymentSystemParameter, cardExpirationParameter);
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

	@Override
	public void rejectUserRequest(UserRequest userRequest) throws ServiceException {
		userRequestValidator.checkUserRequestForNull(userRequest);
		try {
			UserRequest exisingUserRequest = userRequestDAO.getUserRequestById(userRequest.getId());
			userRequestValidator.checkRequestStatusIsInProcessing(exisingUserRequest);
			exisingUserRequest.setRequestStatus(rejectedStatus);
			exisingUserRequest.setHandlingDate(new Date());
			userRequestDAO.updateUserRequest(exisingUserRequest);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public void handleUserRequest(UserRequest userRequest) throws ServiceException {
		userRequestValidator.checkUserRequestForNull(userRequest);
		int requestId = userRequest.getId();
		try {
			UserRequest exisingUserRequest = userRequestDAO.getUserRequestById(requestId);
			userRequestValidator.checkRequestStatusIsInProcessing(exisingUserRequest);
			List<RequestParameter> parameters = requestParameterDAO.getAllRequestParametersByUserRequestId(requestId);
			handleUserRequest(exisingUserRequest, parameters);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}

	private void handleUserRequest(UserRequest userRequest, List<RequestParameter> parameters) throws ServiceException {
		switch (userRequest.getRequestType().getValue()) {
		case OPEN_NEW_ACCOUNT_REQUEST:
			openNewAccount(userRequest, parameters);
			break;
		case OPEN_NEW_CARD_REQUEST:
			openNewCard(userRequest, parameters);
			break;
		case OPEN_NEW_CARD_TO_EXISTING_ACCOUNT_REQUEST:
			openNewCardToExisingAccount(userRequest, parameters);
			break;
		case UNLOCK_CARD_REQUEST:
			unlockCard(userRequest, parameters);
			break;
		default:
			throw new ServiceException("Unknown request type");
		}
	}

	private void openNewAccount(UserRequest userRequest, List<RequestParameter> parameters) throws ServiceException {
		RequestParameter currencyParameter = getRequestParameterByHeader(parameters, currencyHeader);
		Currency currency = getCurrencyFromRequestParameter(currencyParameter);
		String accountNumber = BankAccountNumberGenerator.INSTANCE.generateRandomFreeBankAccountNumber();
		BankAccount bankAccount = buildBankAccount(accountNumber, currency, userRequest.getUser());
		try {
			userRequestDAO.handleUserRequestToOpenAccount(userRequest, bankAccount);
			mailSender.sendMessageAccountOpenRequestComplete(userRequest.getUser().getEmail(), accountNumber);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}

	private void openNewCardToExisingAccount(UserRequest userRequest, List<RequestParameter> parameters)
			throws ServiceException {
		RequestParameter paymentSystemParameter = getRequestParameterByHeader(parameters, paymentSystemHeader);
		RequestParameter cardExpiryParameter = getRequestParameterByHeader(parameters, cardExpiryHeader);
		RequestParameter accountNumberParameter = getRequestParameterByHeader(parameters, accountNumberHeader);
		PaymentSystem paymentSystem = getPaymentSystemFromRequestParameter(paymentSystemParameter);
		CardExpiry cardExpiry = getCardExpiryFromRequestParameter(cardExpiryParameter);
		String accountNumber = accountNumberParameter.getValue();
		String cardNumber = CardNumberGenerator.INSTANCE.generateFreeCardNumberByPaymentSystem(paymentSystem);
		String ccv = CardUtil.getRandomCcvNumber();
		Card card = buildCard(cardNumber, cardExpiry, ccv, paymentSystem, accountNumber, userRequest.getUser());
		try {
			userRequestDAO.handleUserRequestToOpenCardToExistingAccount(userRequest, card);
			mailSender.sendMessageOpenCardToExistingAccountRequestComplete(userRequest.getUser().getEmail(),accountNumber, cardNumber, ccv);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}

	private void openNewCard(UserRequest userRequest, List<RequestParameter> parameters) throws ServiceException {
		RequestParameter paymentSystemParameter = getRequestParameterByHeader(parameters, paymentSystemHeader);
		RequestParameter currencyParameter = getRequestParameterByHeader(parameters, currencyHeader);
		RequestParameter cardExpiryParameter = getRequestParameterByHeader(parameters, cardExpiryHeader);
		PaymentSystem paymentSystem = getPaymentSystemFromRequestParameter(paymentSystemParameter);
		CardExpiry cardExpiry = getCardExpiryFromRequestParameter(cardExpiryParameter);
		Currency currency = getCurrencyFromRequestParameter(currencyParameter);
		String accountNumber = BankAccountNumberGenerator.INSTANCE.generateRandomFreeBankAccountNumber();
		String cardNumber = CardNumberGenerator.INSTANCE.generateFreeCardNumberByPaymentSystem(paymentSystem);
		String ccv = CardUtil.getRandomCcvNumber();
		BankAccount bankAccount = buildBankAccount(accountNumber, currency, userRequest.getUser());
		Card card = buildCard(cardNumber, cardExpiry, ccv, paymentSystem, accountNumber, userRequest.getUser());
		try {
			userRequestDAO.handleUserRequestToOpenCard(userRequest, card, bankAccount);
			mailSender.sendMessageOpenCardRequestComplete(userRequest.getUser().getEmail(), cardNumber, ccv);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}

	private void unlockCard(UserRequest userRequest, List<RequestParameter> parameters) throws ServiceException {
		RequestParameter cardNumberParameter = getRequestParameterByHeader(parameters, cardNumberHeader);
		try {
			Card card = cardDAO.getCardByCardNumber(cardNumberParameter.getValue());
			cardValidator.checkIsCardCanBeUnblocked(card);
			userRequestDAO.handleUserRequestToUnlockCard(userRequest, card);
			mailSender.sendMessageUnlockCardRequestComplete(userRequest.getUser().getEmail(), card.getNumberMask());
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}

	private CardExpiry getCardExpiryFromRequestParameter(RequestParameter parameter) throws ServiceException {
		requestParameterValidator.checkIsCardExpiryRequestParameter(parameter);
		return new CardExpiry(Integer.valueOf(parameter.getValue()));
	}

	private PaymentSystem getPaymentSystemFromRequestParameter(RequestParameter parameter) throws ServiceException {
		requestParameterValidator.checkIsPaymentSystemRequestParameter(parameter);
		return new PaymentSystem(parameter.getValue());
	}

	private Currency getCurrencyFromRequestParameter(RequestParameter parameter) throws ServiceException {
		requestParameterValidator.checkIsCurrencyRequestParameter(parameter);
		return new Currency(parameter.getValue());
	}

	private Card buildCard(String cardNumber, CardExpiry cardExpiry, String ccv, PaymentSystem paymentSystem,
			String accountNumber, User user) throws ServiceException {
		Card card = new Card(sha1Hex(cardNumber));
		card.setValidUntilDate(CardUtil.getValidUntilDate(cardExpiry));
		card.setNumberMask(CardUtil.getCardNumberMaskFromCardNumber(cardNumber));
		card.setCvc(sha1Hex(ccv));
		card.setExpirationDate(cardExpiry);
		card.setPaymentSystem(paymentSystem);
		card.setBankAccount(new BankAccount(accountNumber));
		card.setUser(user);
		return card;
	}

	private BankAccount buildBankAccount(String accountNumber, Currency currency, User user) {
		BankAccount bankAccount = new BankAccount(accountNumber);
		bankAccount.setCurrency(currency);
		bankAccount.setUser(user);
		return bankAccount;
	}

	private RequestParameter getRequestParameterByHeader(List<RequestParameter> requestParameters,
			ParameterHeader header) throws ServiceException {
		requestParameterValidator.checkRequestParameterListForNull(requestParameters);
		for (RequestParameter parameter : requestParameters) {
			if (parameter.getParameterHeader().equals(header)) {
				return parameter;
			}
		}
		throw new ServiceException("The request parameter list does not contain the specified header");
	}

	private void saveUserRequestWithParameterList(UserRequest userRequest, List<RequestParameter> requestParameterList)
			throws ServiceException {
		userRequestValidator.checkRequiredUserRequestFieldsForNull(userRequest);
		requestParameterValidator.checkRequestParameterListForNull(requestParameterList);
		try {
			userRequestDAO.saveUserRequestWithParameterList(userRequest, requestParameterList);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}

}
