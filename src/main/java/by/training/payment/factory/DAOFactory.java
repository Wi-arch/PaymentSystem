package by.training.payment.factory;

import by.training.payment.dao.*;
import by.training.payment.dao.impl.*;

public class DAOFactory {

	private final static DAOFactory INSTANCE = new DAOFactory();
	private final BankAccountDAO bankAccountDAO = new MySQLBankAccountDAO();
	private final CardDAO cardDAO = new MySQLCardDAO();
	private final CardExpirationDateDAO cardExpirationDateDAO = new MySQLCardExpirationDateDAO();
	private final CurrencyDAO currencyDAO = new MySQLCurrencyDAO();
	private final ParameterHeaderDAO parameterHeaderDAO = new MySQLParameterHeaderDAO();
	private final PaymentSystemDAO paymentSystemDAO = new MySQLPaymentSystemDAO();
	private final RequestParameterDAO requestParameterDAO = new MySQLRequestParameterDAO();
	private final RequestStatusDAO requestStatusDAO = new MySQLRequestStatusDAO();
	private final RequestTypeDAO requestTypeDAO = new MySQLRequestTypeDAO();
	private final TransactionDAO transactionDAO = new MySQLTransactionDAO();
	private final UserDAO userDAO = new MySQLUserDAO();
	private final UserRequestDAO userRequestDAO = new MySQLUserRequestDAO();
	private final UserRoleDAO userRoleDAO = new MySQLUserRoleDAO();

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
