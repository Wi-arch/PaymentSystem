package by.training.payment.factory;

import by.training.payment.dao.*;
import by.training.payment.dao.impl.*;

public class DAOFactory {

	private static final DAOFactory INSTANCE = new DAOFactory();
	private final BankAccountDAO bankAccountDAO = new BankAccountDAOImpl();
	private final CardDAO cardDAO = new CardDAOImpl();
	private final CardExpirationDateDAO cardExpirationDateDAO = new CardExpirationDateDAOImpl();
	private final CurrencyDAO currencyDAO = new CurrencyDAOImpl();
	private final ParameterHeaderDAO parameterHeaderDAO = new ParameterHeaderDAOImpl();
	private final PaymentSystemDAO paymentSystemDAO = new PaymentSystemDAOImpl();
	private final RequestParameterDAO requestParameterDAO = new RequestParameterDAOImpl();
	private final RequestStatusDAO requestStatusDAO = new RequestStatusDAOImpl();
	private final RequestTypeDAO requestTypeDAO = new RequestTypeDAOImpl();
	private final TransactionDAO transactionDAO = new TransactionDAOImpl();
	private final UserDAO userDAO = new UserDAOImpl();
	private final UserRequestDAO userRequestDAO = new UserRequestDAOImpl();
	private final UserRoleDAO userRoleDAO = new UserRoleDAOImpl();

	private DAOFactory() {
	}

	public static DAOFactory getInstance() {
		return INSTANCE;
	}

	public BankAccountDAO getBankAccountDAO() {
		return bankAccountDAO;
	}

	public CardDAO getCardDAO() {
		return cardDAO;
	}

	public CardExpirationDateDAO getCardExpirationDateDAO() {
		return cardExpirationDateDAO;
	}

	public CurrencyDAO getCurrencyDAO() {
		return currencyDAO;
	}

	public ParameterHeaderDAO getParameterHeaderDAO() {
		return parameterHeaderDAO;
	}

	public PaymentSystemDAO getPaymentSystemDAO() {
		return paymentSystemDAO;
	}

	public RequestParameterDAO getRequestParameterDAO() {
		return requestParameterDAO;
	}

	public RequestStatusDAO getRequestStatusDAO() {
		return requestStatusDAO;
	}

	public RequestTypeDAO getRequestTypeDAO() {
		return requestTypeDAO;
	}

	public TransactionDAO getTransactionDAO() {
		return transactionDAO;
	}

	public UserDAO getUserDAO() {
		return userDAO;
	}

	public UserRequestDAO getUserRequestDAO() {
		return userRequestDAO;
	}

	public UserRoleDAO getUserRoleDAO() {
		return userRoleDAO;
	}
}
