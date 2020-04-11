package by.training.payment.dao.impl;

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import by.training.payment.dao.BankAccountDAO;
import by.training.payment.dao.TransactionDAO;
import by.training.payment.entity.BankAccount;
import by.training.payment.entity.Card;
import by.training.payment.entity.Transaction;
import by.training.payment.exception.DAOException;
import by.training.payment.factory.DAOFactory;
import by.training.payment.pool.PoolConnection;
import by.training.payment.pool.ProxyConnection;

public class MySQLBankAccountDAO extends SQLUtil implements BankAccountDAO {

	private static final String ADD_BANK_ACCOUNT = "INSERT INTO bank_account (bank_account_number, bank_account_balance, currency_name, bank_user_login) VALUES (?, ?, ?, ?);";
	private static final String UPDATE_BANK_ACCOUNT = "UPDATE bank_account SET bank_account_opening_date=?, bank_account_balance=?, currency_name=?, bank_user_login=?, bank_account_is_blocked=? WHERE bank_account_number = ?;";
	private static final String DELETE_BANK_ACCOUNT = "DELETE FROM bank_account WHERE bank_account_number = ?;";
	private static final String UPDATE_BANK_ACCOUNT_IS_BLOCKED = "UPDATE bank_account SET bank_account_is_blocked=? WHERE bank_account_number = ?;";
	private static final String GET_BANK_ACCOUNT_BY_ACCOUNT_NUMBER = "SELECT * FROM bank_account INNER JOIN currency "
			+ "ON bank_account.currency_name = currency.currency_name "
			+ "INNER JOIN bank_user ON bank_account.bank_user_login = bank_user.bank_user_login "
			+ "INNER JOIN user_role ON user_role.user_role_value = bank_user.user_role_value "
			+ "WHERE bank_account.bank_account_number = ?;";
	private static final String GET_ALL_BANK_ACCOUNTS = "SELECT * FROM bank_account INNER JOIN currency "
			+ "ON bank_account.currency_name = currency.currency_name "
			+ "INNER JOIN bank_user ON bank_account.bank_user_login = bank_user.bank_user_login "
			+ "INNER JOIN user_role ON user_role.user_role_value = bank_user.user_role_value;";
	private static final String GET_ALL_BANK_ACCOUNTS_BY_USER_LOGIN = "SELECT * FROM bank_account INNER JOIN currency "
			+ "ON bank_account.currency_name = currency.currency_name "
			+ "INNER JOIN bank_user ON bank_account.bank_user_login = bank_user.bank_user_login "
			+ "INNER JOIN user_role ON user_role.user_role_value = bank_user.user_role_value "
			+ "WHERE bank_user.bank_user_login = ?;";
	private static final String UPDATE_BANK_ACCOUNT_BALANCE = "UPDATE bank_account SET bank_account_balance=? WHERE bank_account_number = ?;";
	private static final String UPDATE_CARD_IS_BLOCKED = "{call PS_UpdateCardIsBlocked(?,?)}";
	private static final String SAVE_BANK_ACCOUNT_TRANSFER = "{call PS_SaveBankAccountTransfer(?,?,?,?)}";

	@Override
	public void addBankAccount(BankAccount bankAccount) throws DAOException {
		PreparedStatement statement = null;
		try (ProxyConnection connection = PoolConnection.INSTANCE.getConnection()) {
			statement = connection.prepareStatement(ADD_BANK_ACCOUNT);
			if (statement != null) {
				statement.setString(1, bankAccount.getAccountNumber());
				statement.setBigDecimal(2, bankAccount.getBalance());
				statement.setString(3, bankAccount.getCurrency().getName());
				statement.setString(4, bankAccount.getUser().getLogin());
				statement.executeUpdate();
			}
		} catch (SQLException e) {
			throw new DAOException("Cannot add bank account", e);
		} finally {
			closeStatement(statement);
		}
	}

	@Override
	public void updateBankAccount(BankAccount bankAccount) throws DAOException {
		PreparedStatement statement = null;
		try (ProxyConnection connection = PoolConnection.INSTANCE.getConnection()) {
			statement = connection.prepareStatement(UPDATE_BANK_ACCOUNT);
			if (statement != null) {
				statement.setTimestamp(1, new Timestamp(bankAccount.getOpeningDate().getTime()));
				statement.setBigDecimal(2, bankAccount.getBalance());
				statement.setString(3, bankAccount.getCurrency().getName());
				statement.setString(4, bankAccount.getUser().getLogin());
				statement.setBoolean(5, bankAccount.getIsBlocked());
				statement.setString(6, bankAccount.getAccountNumber());
				statement.executeUpdate();
			}
		} catch (SQLException e) {
			throw new DAOException("Cannot update bank account", e);
		} finally {
			closeStatement(statement);
		}
	}

	@Override
	public void deleteBankAccount(BankAccount bankAccount) throws DAOException {
		PreparedStatement statement = null;
		try (ProxyConnection connection = PoolConnection.INSTANCE.getConnection()) {
			statement = connection.prepareStatement(DELETE_BANK_ACCOUNT);
			if (statement != null) {
				statement.setString(1, bankAccount.getAccountNumber());
				statement.executeUpdate();
			}
		} catch (SQLException e) {
			throw new DAOException("Cannot delete bank account", e);
		} finally {
			closeStatement(statement);
		}
	}

	@Override
	public BankAccount getBankAccountByAccountNumber(String accountNumber) throws DAOException {
		BankAccount bankAccount = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try (ProxyConnection connection = PoolConnection.INSTANCE.getConnection()) {
			statement = connection.prepareStatement(GET_BANK_ACCOUNT_BY_ACCOUNT_NUMBER);
			if (statement != null) {
				statement.setString(1, accountNumber);
				resultSet = statement.executeQuery();
				if (resultSet.next()) {
					bankAccount = buildBankAccount(resultSet);
				}
			}
		} catch (SQLException e) {
			throw new DAOException("Cannot load bank account", e);
		} finally {
			closeStatement(statement);
			closeResultSet(resultSet);
		}
		return bankAccount;
	}

	@Override
	public List<BankAccount> getAllBankAccounts() throws DAOException {
		List<BankAccount> bankAccounts = new ArrayList<>();
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try (ProxyConnection connection = PoolConnection.INSTANCE.getConnection()) {
			statement = connection.prepareStatement(GET_ALL_BANK_ACCOUNTS);
			if (statement != null) {
				resultSet = statement.executeQuery();
				while (resultSet.next()) {
					bankAccounts.add(buildBankAccount(resultSet));
				}
			}
		} catch (SQLException e) {
			throw new DAOException("Cannot load bank account list", e);
		} finally {
			closeStatement(statement);
			closeResultSet(resultSet);
		}
		return bankAccounts;
	}

	@Override
	public List<BankAccount> getAllBankAccountsByUserLogin(String login) throws DAOException {
		List<BankAccount> bankAccounts = new ArrayList<>();
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try (ProxyConnection connection = PoolConnection.INSTANCE.getConnection()) {
			statement = connection.prepareStatement(GET_ALL_BANK_ACCOUNTS_BY_USER_LOGIN);
			if (statement != null) {
				statement.setString(1, login);
				resultSet = statement.executeQuery();
				while (resultSet.next()) {
					bankAccounts.add(buildBankAccount(resultSet));
				}
			}
		} catch (SQLException e) {
			throw new DAOException("Cannot load bank account list by user login", e);
		} finally {
			closeStatement(statement);
			closeResultSet(resultSet);
		}
		return bankAccounts;
	}

	@Override
	public void saveSinglBankAccountOperationWithTransaction(BankAccount bankAccount, Transaction transaction)
			throws DAOException {
		TransactionDAO transactionDAO = DAOFactory.getInstance().getTransactionDAO();
		ProxyConnection connection = PoolConnection.INSTANCE.getConnection();
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try {
			connection.setAutoCommit(false);
			connection.setTransactionIsolation(Connection.TRANSACTION_REPEATABLE_READ);
			statement = connection.prepareStatement(UPDATE_BANK_ACCOUNT_BALANCE);
			statement.setBigDecimal(1, bankAccount.getBalance());
			statement.setString(2, bankAccount.getAccountNumber());
			statement.executeUpdate();
			checkIsBalanceGreaterThanZero(bankAccount, connection);
			connection.commit();
			transaction.setCompleted(true);
		} catch (SQLException e) {
			rollbackTransaction(connection);
			throw new DAOException("Cannot save bank account operation with transaction", e);
		} finally {
			closeResultSet(resultSet);
			closeStatement(statement);
			closeConnection(connection);
			transactionDAO.addTransaction(transaction);
		}
	}

	@Override
	public void saveTransfer(BankAccount bankAccountFrom, Transaction transactionFrom, BankAccount bankAccountTo,
			Transaction transactionTo) throws DAOException {
		TransactionDAO transactionDAO = DAOFactory.getInstance().getTransactionDAO();
		ProxyConnection connection = PoolConnection.INSTANCE.getConnection();
		CallableStatement callableStatement = null;
		ResultSet resultSet = null;
		try {
			connection.setAutoCommit(false);
			connection.setTransactionIsolation(Connection.TRANSACTION_REPEATABLE_READ);
			callableStatement = connection.prepareCall(SAVE_BANK_ACCOUNT_TRANSFER);
			callableStatement.setBigDecimal(1, bankAccountFrom.getBalance());
			callableStatement.setString(2, bankAccountFrom.getAccountNumber());
			callableStatement.setBigDecimal(3, bankAccountTo.getBalance());
			callableStatement.setString(4, bankAccountTo.getAccountNumber());
			callableStatement.executeUpdate();
			checkIsBalanceGreaterThanZero(bankAccountFrom, connection);
			connection.commit();
			transactionTo.setCompleted(true);
			transactionFrom.setCompleted(true);
			transactionDAO.addTransaction(transactionTo);
		} catch (SQLException e) {
			rollbackTransaction(connection);
			throw new DAOException("Cannot save transfer with transaction", e);
		} finally {
			closeResultSet(resultSet);
			closeStatement(callableStatement);
			closeConnection(connection);
			transactionDAO.addTransaction(transactionFrom);
		}
	}

	@Override
	public void lockBankAccountWithCardList(BankAccount bankAccount, List<Card> cardList) throws DAOException {
		ProxyConnection connection = PoolConnection.INSTANCE.getConnection();
		PreparedStatement statement = null;
		CallableStatement callableStatement = null;
		try {
			connection.setAutoCommit(false);
			statement = connection.prepareStatement(UPDATE_BANK_ACCOUNT_IS_BLOCKED);			
			statement.setBoolean(1, bankAccount.getIsBlocked());
			statement.setString(2, bankAccount.getAccountNumber());
			statement.executeUpdate();
			callableStatement = connection.prepareCall(UPDATE_CARD_IS_BLOCKED);
			for (Card card : cardList) {
				callableStatement.setString(1, card.getNumber());
				callableStatement.setBoolean(2, card.getIsBlocked());
				callableStatement.addBatch();
			}
			callableStatement.executeUpdate();
			connection.commit();
		} catch (SQLException e) {
			rollbackTransaction(connection);
			throw new DAOException("Cannot lock bank account with card list", e);
		} finally {
			closeStatement(callableStatement);
			closeStatement(statement);
			closeConnection(connection);
		}
	}

	private void checkIsBalanceGreaterThanZero(BankAccount bankAccount, ProxyConnection connection)
			throws SQLException {
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try {
			statement = connection.prepareStatement(GET_BANK_ACCOUNT_BY_ACCOUNT_NUMBER);
			statement.setString(1, bankAccount.getAccountNumber());
			resultSet = statement.executeQuery();
			if (resultSet.next()) {
				BigDecimal currentBalance = resultSet.getBigDecimal(BANK_ACCOUNT_BALANCE);
				if (currentBalance.compareTo(BigDecimal.ZERO) < ZERO) {
					throw new SQLException("Not enough money on the balance *Status1023*");
				}
			}
		} finally {
			closeResultSet(resultSet);
			closeStatement(statement);
		}
	}

}
