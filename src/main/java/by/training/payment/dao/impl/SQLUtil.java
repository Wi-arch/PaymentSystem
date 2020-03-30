package by.training.payment.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.log4j.Logger;

import by.training.payment.entity.BankAccount;
import by.training.payment.entity.Card;
import by.training.payment.entity.CardExpirationDate;
import by.training.payment.entity.Currency;
import by.training.payment.entity.ParameterHeader;
import by.training.payment.entity.PaymentSystem;
import by.training.payment.entity.RequestParameter;
import by.training.payment.entity.RequestStatus;
import by.training.payment.entity.RequestType;
import by.training.payment.entity.Transaction;
import by.training.payment.entity.User;
import by.training.payment.entity.UserRequest;
import by.training.payment.entity.UserRole;
import by.training.payment.pool.ProxyConnection;

public class SQLUtil {

	private static final Logger LOGGER = Logger.getLogger(SQLUtil.class);

	private static final String USER_ROLE_ID = "user_role.user_role_id";
	private static final String USER_ROLE_VALUE = "user_role.user_role_value";

	private static final String USER_ID = "bank_user.bank_user_id";
	private static final String USER_LOGIN = "bank_user.bank_user_login";
	private static final String USER_PASSWORD = "bank_user.bank_user_password";
	private static final String USER_EMAIL = "bank_user.bank_user_email";
	private static final String USER_NAME = "bank_user.bank_user_name";
	private static final String USER_SURNAME = "bank_user.bank_user_surname";
	private static final String USER_IS_BLOCKED = "bank_user.bank_user_is_blocked";

	private static final String USER_REQUEST_ID = "user_request.user_request_id";
	private static final String REQUEST_CREATION_DATE = "user_request.request_creation_date";
	private static final String REQUEST_HANDLE_DATE = "user_request.request_handle_date";

	private static final String REQUEST_TYPE_ID = "request_type.request_type_id";
	private static final String REQUEST_TYPE_VALUE = "request_type.request_type_value";

	private static final String REQUEST_STATUS_ID = "request_status.request_status_id";
	private static final String REQUEST_STATUS_VALUE = "request_status.request_status_value";

	private static final String PARAMETER_HEADER_ID = "parameter_header.parameter_header_id";
	private static final String PARAMETER_HEADER_NAME = "parameter_header.header_name";

	private static final String REQUEST_PARAMETER_ID = "request_parameter.request_parameter_id";
	private static final String REQUEST_PARAMETER_VALUE = "request_parameter.value";

	private static final String PAYMENT_SYSTEM_ID = "payment_system.payment_system_id";
	private static final String PAYMENT_SYSTEM_NAME = "payment_system.payment_system_name";

	private static final String CARD_EXPIRATION_DATE_ID = "card_expiration_date.card_expiration_date_id";
	private static final String CARD_EXPIRATION_DATE_VALUE = "card_expiration_date.card_expiration_date_value";

	private static final String CURRENCY_ID = "currency.currency_id";
	private static final String CURRENCY_RATE = "currency.currency_rate";
	private static final String CURRENCY_SCALE = "currency.currency_scale";
	private static final String CURRENCY_NAME = "currency.currency_name";
	private static final String CURRENCY_UPDATE_DATE = "currency.currency_update_date";
	private static final String CURRENCY_IS_BASE_CURRENCY = "currency.currency_is_base_currency";

	private static final String BANK_ACCOUNT_ID = "bank_account.bank_account_id";
	private static final String BANK_ACCOUNT_NUMBER = "bank_account.bank_account_number";
	private static final String BANK_ACCOUNT_BALANCE = "bank_account.bank_account_balance";
	private static final String BANK_ACCOUNT_IS_BLOCKED = "bank_account.bank_account_is_blocked";
	private static final String BANK_ACCOUNT_OPENING_DATE = "bank_account.bank_account_opening_date";

	private static final String TRANSACTION_ID = "payment_transaction.transaction_id";
	private static final String TRANSACTION_COMPLETED = "payment_transaction.transaction_completed";
	private static final String TRANSACTION_DATE = "payment_transaction.transaction_date";
	private static final String TRANSACTION_IS_WRITE_OFF = "payment_transaction.transaction_is_write_off";
	private static final String TRANSACTION_AMOUNT = "payment_transaction.transaction_amount";
	private static final String TRANSACTION_PAYMENT_PURPOSE = "payment_transaction.transaction_payment_purpose";

	private static final String CARD_ID = "card.card_id";
	private static final String CARD_OPENING_DATE = "card.card_opening_date";
	private static final String CARD_VALID_UNTIL_DATE = "card.card_valid_until_date";
	private static final String CARD_NUMBER = "card.card_number";
	private static final String CARD_NUMBER_MASK = "card.card_number_mask";
	private static final String CARD_CCV_CODE = "card.card_ccv_code";
	private static final String CARD_IS_BLOCKED = "card.card_is_blocked";

	protected Card buildCard(ResultSet resultSet) throws SQLException {
		Card card = new Card();
		card.setId(resultSet.getInt(CARD_ID));
		card.setBankAccount(buildBankAccount(resultSet));
		card.setBlocked(resultSet.getBoolean(CARD_IS_BLOCKED));
		card.setCcv(resultSet.getString(CARD_CCV_CODE));
		card.setExpirationDate(buildCardExpirationDate(resultSet));
		card.setNumber(resultSet.getString(CARD_NUMBER));
		card.setNumberMask(resultSet.getString(CARD_NUMBER_MASK));
		card.setOpeningDate(resultSet.getTimestamp(CARD_OPENING_DATE));
		card.setPaymentSystem(buildPaymentSystem(resultSet));
		card.setUser(buildUser(resultSet));
		card.setValidUntilDate(resultSet.getTimestamp(CARD_VALID_UNTIL_DATE));
		return card;
	}

	protected Transaction buildTransaction(ResultSet resultSet) throws SQLException {
		Transaction transaction = new Transaction();
		transaction.setId(resultSet.getInt(TRANSACTION_ID));
		transaction.setAccount(buildBankAccount(resultSet));
		transaction.setAmount(resultSet.getBigDecimal(TRANSACTION_AMOUNT));
		transaction.setCompleted(resultSet.getBoolean(TRANSACTION_COMPLETED));
		transaction.setCurrency(buildCurrency(resultSet));
		transaction.setDate(resultSet.getTimestamp(TRANSACTION_DATE));
		transaction.setPaymentPurpose(resultSet.getString(TRANSACTION_PAYMENT_PURPOSE));
		transaction.setWriteOff(resultSet.getBoolean(TRANSACTION_IS_WRITE_OFF));
		return transaction;
	}

	protected BankAccount buildBankAccount(ResultSet resultSet) throws SQLException {
		BankAccount bankAccount = new BankAccount();
		bankAccount.setId(resultSet.getInt(BANK_ACCOUNT_ID));
		bankAccount.setAccountNumber(resultSet.getString(BANK_ACCOUNT_NUMBER));
		bankAccount.setBalance(resultSet.getBigDecimal(BANK_ACCOUNT_BALANCE));
		bankAccount.setBlocked(resultSet.getBoolean(BANK_ACCOUNT_IS_BLOCKED));
		bankAccount.setCurrency(buildCurrency(resultSet));
		bankAccount.setOpeningDate(resultSet.getTimestamp(BANK_ACCOUNT_OPENING_DATE));
		bankAccount.setUser(buildUser(resultSet));
		return bankAccount;
	}

	protected Currency buildCurrency(ResultSet resultSet) throws SQLException {
		Currency currency = new Currency();
		currency.setId(resultSet.getInt(CURRENCY_ID));
		currency.setBaseCurrency(resultSet.getBoolean(CURRENCY_IS_BASE_CURRENCY));
		currency.setName(resultSet.getString(CURRENCY_NAME));
		currency.setRate(resultSet.getBigDecimal(CURRENCY_RATE));
		currency.setScale(resultSet.getInt(CURRENCY_SCALE));
		currency.setUpdateDate(resultSet.getTimestamp(CURRENCY_UPDATE_DATE));
		return currency;
	}

	protected CardExpirationDate buildCardExpirationDate(ResultSet resultSet) throws SQLException {
		CardExpirationDate cardExpirationDate = new CardExpirationDate();
		cardExpirationDate.setId(resultSet.getInt(CARD_EXPIRATION_DATE_ID));
		cardExpirationDate.setValue(resultSet.getInt(CARD_EXPIRATION_DATE_VALUE));
		return cardExpirationDate;
	}

	protected PaymentSystem buildPaymentSystem(ResultSet resultSet) throws SQLException {
		PaymentSystem paymentSystem = new PaymentSystem();
		paymentSystem.setId(resultSet.getInt(PAYMENT_SYSTEM_ID));
		paymentSystem.setName(resultSet.getString(PAYMENT_SYSTEM_NAME));
		return paymentSystem;
	}

	protected RequestParameter buildRequestParameter(ResultSet resultSet) throws SQLException {
		RequestParameter requestParameter = new RequestParameter();
		requestParameter.setId(resultSet.getInt(REQUEST_PARAMETER_ID));
		requestParameter.setParameterHeader(buildParameterHeader(resultSet));
		requestParameter.setUserRequest(buildUserRequest(resultSet));
		requestParameter.setValue(resultSet.getString(REQUEST_PARAMETER_VALUE));
		return requestParameter;
	}

	protected ParameterHeader buildParameterHeader(ResultSet resultSet) throws SQLException {
		ParameterHeader parameterHeader = new ParameterHeader();
		parameterHeader.setId(resultSet.getInt(PARAMETER_HEADER_ID));
		parameterHeader.setName(resultSet.getString(PARAMETER_HEADER_NAME));
		return parameterHeader;
	}

	protected UserRequest buildUserRequest(ResultSet resultSet) throws SQLException {
		UserRequest userRequest = new UserRequest();
		userRequest.setCreationDate(resultSet.getTimestamp(REQUEST_CREATION_DATE));
		userRequest.setHandlingDate(resultSet.getTimestamp(REQUEST_HANDLE_DATE));
		userRequest.setId(resultSet.getInt(USER_REQUEST_ID));
		userRequest.setRequestStatus(buildRequestStatus(resultSet));
		userRequest.setRequestType(buildRequestType(resultSet));
		userRequest.setUser(buildUser(resultSet));
		return userRequest;
	}

	protected User buildUser(ResultSet resultSet) throws SQLException {
		User user = new User();
		user.setBlocked(resultSet.getBoolean(USER_IS_BLOCKED));
		user.setEmail(resultSet.getString(USER_EMAIL));
		user.setId(resultSet.getInt(USER_ID));
		user.setLogin(resultSet.getString(USER_LOGIN));
		user.setName(resultSet.getString(USER_NAME));
		user.setPassword(resultSet.getString(USER_PASSWORD));
		user.setSurname(resultSet.getString(USER_SURNAME));
		user.setUserRole(buildUserRole(resultSet));
		return user;
	}

	protected RequestStatus buildRequestStatus(ResultSet resultSet) throws SQLException {
		RequestStatus requestStatus = new RequestStatus();
		requestStatus.setId(resultSet.getInt(REQUEST_STATUS_ID));
		requestStatus.setValue(resultSet.getString(REQUEST_STATUS_VALUE));
		return requestStatus;
	}

	protected RequestType buildRequestType(ResultSet resultSet) throws SQLException {
		RequestType requestType = new RequestType();
		requestType.setId(resultSet.getInt(REQUEST_TYPE_ID));
		requestType.setValue(resultSet.getString(REQUEST_TYPE_VALUE));
		return requestType;
	}

	protected UserRole buildUserRole(ResultSet resultSet) throws SQLException {
		UserRole userRole = new UserRole();
		userRole.setId(resultSet.getInt(USER_ROLE_ID));
		userRole.setValue(resultSet.getString(USER_ROLE_VALUE));
		return userRole;
	}

	protected void closeResultSet(ResultSet resultSet) {
		if (resultSet != null) {
			try {
				resultSet.close();
			} catch (SQLException e) {
				LOGGER.error("Cannot close resultSet", e);
			}
		}
	}

	protected void closeStatement(Statement statement) {
		if (statement != null) {
			try {
				statement.close();
			} catch (SQLException e) {
				LOGGER.error("Cannot close PreparedStatement", e);
			}
		}
	}

	protected void closeConnection(ProxyConnection proxyConnection) {
		if (proxyConnection != null) {
			try {
				proxyConnection.close();
			} catch (SQLException e) {
				LOGGER.error("Cannot close ProxyConnection", e);
			}
		}
	}
}
