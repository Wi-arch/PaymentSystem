package by.training.payment.factory;

import by.training.payment.dao.impl.MySQLBankAccountDAO;
import by.training.payment.dao.impl.MySQLCardDAO;
import by.training.payment.dao.impl.MySQLCardExpirationDateDAO;
import by.training.payment.dao.impl.MySQLCurrencyDAO;
import by.training.payment.dao.impl.MySQLParameterHeaderDAO;
import by.training.payment.dao.impl.MySQLPaymentSystemDAO;
import by.training.payment.dao.impl.MySQLRequestParameterDAO;
import by.training.payment.dao.impl.MySQLRequestStatusDAO;
import by.training.payment.dao.impl.MySQLRequestTypeDAO;
import by.training.payment.dao.impl.MySQLTransactionDAO;
import by.training.payment.dao.impl.MySQLUserDAO;
import by.training.payment.dao.impl.MySQLUserRequestDAO;
import by.training.payment.dao.impl.MySQLUserRoleDAO;

public class DAOFactory {

	private final static DAOFactory INSTANCE = new DAOFactory();
	private final MySQLBankAccountDAO bankAccountDAO = new MySQLBankAccountDAO();
	private final MySQLCardDAO cardDAO = new MySQLCardDAO();
	private final MySQLCardExpirationDateDAO cardExpirationDateDAO = new MySQLCardExpirationDateDAO();
	private final MySQLCurrencyDAO currencyDAO = new MySQLCurrencyDAO();
	private final MySQLParameterHeaderDAO parameterHeaderDAO = new MySQLParameterHeaderDAO();
	private final MySQLPaymentSystemDAO paymentSystemDAO = new MySQLPaymentSystemDAO();
	private final MySQLRequestParameterDAO requestParameterDAO = new MySQLRequestParameterDAO();
	private final MySQLRequestStatusDAO requestStatusDAO = new MySQLRequestStatusDAO();
	private final MySQLRequestTypeDAO requestTypeDAO = new MySQLRequestTypeDAO();
	private final MySQLTransactionDAO transactionDAO = new MySQLTransactionDAO();
	private final MySQLUserDAO userDAO = new MySQLUserDAO();
	private final MySQLUserRequestDAO userRequestDAO = new MySQLUserRequestDAO();
	private final MySQLUserRoleDAO userRoleDAO = new MySQLUserRoleDAO();

	private DAOFactory() {
	}

	public static DAOFactory getInstance() {
		return INSTANCE;
	}

	public MySQLBankAccountDAO getBankAccountDAO() {
		return bankAccountDAO;
	}

	public MySQLCardDAO getCardDAO() {
		return cardDAO;
	}

	public MySQLCardExpirationDateDAO getCardExpirationDateDAO() {
		return cardExpirationDateDAO;
	}

	public MySQLCurrencyDAO getCurrencyDAO() {
		return currencyDAO;
	}

	public MySQLParameterHeaderDAO getParameterHeaderDAO() {
		return parameterHeaderDAO;
	}

	public MySQLPaymentSystemDAO getPaymentSystemDAO() {
		return paymentSystemDAO;
	}

	public MySQLRequestParameterDAO getRequestParameterDAO() {
		return requestParameterDAO;
	}

	public MySQLRequestStatusDAO getRequestStatusDAO() {
		return requestStatusDAO;
	}

	public MySQLRequestTypeDAO getRequestTypeDAO() {
		return requestTypeDAO;
	}

	public MySQLTransactionDAO getTransactionDAO() {
		return transactionDAO;
	}

	public MySQLUserDAO getUserDAO() {
		return userDAO;
	}

	public MySQLUserRequestDAO getUserRequestDAO() {
		return userRequestDAO;
	}

	public MySQLUserRoleDAO getUserRoleDAO() {
		return userRoleDAO;
	}

}
