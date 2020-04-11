package by.training.payment.dao.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import by.training.payment.dao.TransactionDAO;
import by.training.payment.entity.Transaction;
import by.training.payment.exception.DAOException;
import by.training.payment.pool.PoolConnection;
import by.training.payment.pool.ProxyConnection;

public class MySQLTransactionDAO extends SQLUtil implements TransactionDAO {

	private static final String ADD_TRANSACTION = "INSERT INTO payment_transaction (transaction_completed, transaction_is_write_off, transaction_amount, currency_name, transaction_payment_purpose, bank_account_number) VALUES (?, ?, ?, ?, ?, ?);";
	private static final String UPDATE_TRANSACTION = "UPDATE payment_transaction SET transaction_completed=?, transaction_date=?, transaction_is_write_off=?, transaction_amount=?, currency_name=?, transaction_payment_purpose=?, bank_account_number=? WHERE transaction_id = ?;";
	private static final String DELETE_TRANSACTION = "DELETE FROM payment_transaction WHERE transaction_id = ?;";
	private static final String GET_TRANSACTION_BY_ID = "SELECT * FROM payment_transaction INNER JOIN currency "
			+ "ON payment_transaction.currency_name = currency.currency_name "
			+ "INNER JOIN bank_account ON payment_transaction.bank_account_number = bank_account.bank_account_number "
			+ "INNER JOIN bank_user ON bank_account.bank_user_login = bank_user.bank_user_login "
			+ "INNER JOIN user_role ON user_role.user_role_value = bank_user.user_role_value "
			+ "WHERE payment_transaction.transaction_id = ?;";
	private static final String GET_ALL_TRANSACTIONS = "SELECT * FROM payment_transaction INNER JOIN currency "
			+ "ON payment_transaction.currency_name = currency.currency_name "
			+ "INNER JOIN bank_account ON payment_transaction.bank_account_number = bank_account.bank_account_number "
			+ "INNER JOIN bank_user ON bank_account.bank_user_login = bank_user.bank_user_login "
			+ "INNER JOIN user_role ON user_role.user_role_value = bank_user.user_role_value;";
	private static final String GET_ALL_TRANSACTIONS_BY_ACCOUNT_NUMBER = "SELECT * FROM payment_transaction INNER JOIN currency "
			+ "ON payment_transaction.currency_name = currency.currency_name "
			+ "INNER JOIN bank_account ON payment_transaction.bank_account_number = bank_account.bank_account_number "
			+ "INNER JOIN bank_user ON bank_account.bank_user_login = bank_user.bank_user_login "
			+ "INNER JOIN user_role ON user_role.user_role_value = bank_user.user_role_value "
			+ "WHERE bank_account.bank_account_number = ?;";
	private static final String GET_ALL_TRANSACTIONS_BY_CARD_NUMBER = "SELECT * FROM payment_transaction INNER JOIN currency "
			+ "ON payment_transaction.currency_name = currency.currency_name "
			+ "INNER JOIN bank_account ON payment_transaction.bank_account_number = bank_account.bank_account_number "
			+ "INNER JOIN bank_user ON bank_account.bank_user_login = bank_user.bank_user_login "
			+ "INNER JOIN user_role ON user_role.user_role_value = bank_user.user_role_value "
			+ "INNER JOIN card ON card.bank_account_number = bank_account.bank_account_number WHERE card.card_number = ?;";

	@Override
	public void addTransaction(Transaction transaction) throws DAOException {
		PreparedStatement statement = null;
		try (ProxyConnection connection = PoolConnection.INSTANCE.getConnection()) {
			statement = connection.prepareStatement(ADD_TRANSACTION);
			if (statement != null) {
				statement.setBoolean(1, transaction.getIsCompleted());
				statement.setBoolean(2, transaction.getIsWriteOff());
				statement.setBigDecimal(3, transaction.getAmount());
				statement.setString(4, transaction.getCurrency().getName());
				statement.setString(5, transaction.getPaymentPurpose());
				statement.setString(6, transaction.getAccount().getAccountNumber());
				statement.executeUpdate();
			}
		} catch (SQLException e) {
			throw new DAOException("Cannot add transaction", e);
		} finally {
			closeStatement(statement);
		}
	}

	@Override
	public void updateTransaction(Transaction transaction) throws DAOException {
		PreparedStatement statement = null;
		try (ProxyConnection connection = PoolConnection.INSTANCE.getConnection()) {
			statement = connection.prepareStatement(UPDATE_TRANSACTION);
			if (statement != null) {
				statement.setBoolean(1, transaction.getIsCompleted());
				statement.setTimestamp(2, new Timestamp(transaction.getDate().getTime()));
				statement.setBoolean(3, transaction.getIsWriteOff());
				statement.setBigDecimal(4, transaction.getAmount());
				statement.setString(5, transaction.getCurrency().getName());
				statement.setString(6, transaction.getPaymentPurpose());
				statement.setString(7, transaction.getAccount().getAccountNumber());
				statement.setInt(8, transaction.getId());
				statement.executeUpdate();
			}
		} catch (SQLException e) {
			throw new DAOException("Cannot update transaction", e);
		} finally {
			closeStatement(statement);
		}
	}

	@Override
	public void deleteTransaction(Transaction transaction) throws DAOException {
		PreparedStatement statement = null;
		try (ProxyConnection connection = PoolConnection.INSTANCE.getConnection()) {
			statement = connection.prepareStatement(DELETE_TRANSACTION);
			if (statement != null) {
				statement.setInt(1, transaction.getId());
				statement.executeUpdate();
			}
		} catch (SQLException e) {
			throw new DAOException("Cannot delete transaction", e);
		} finally {
			closeStatement(statement);
		}
	}

	@Override
	public Transaction getTransactionById(int id) throws DAOException {
		Transaction transaction = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try (ProxyConnection connection = PoolConnection.INSTANCE.getConnection()) {
			statement = connection.prepareStatement(GET_TRANSACTION_BY_ID);
			if (statement != null) {
				statement.setInt(1, id);
				resultSet = statement.executeQuery();
				if (resultSet.next()) {
					transaction = buildTransaction(resultSet);
				}
			}
		} catch (SQLException e) {
			throw new DAOException("Cannot load transaction", e);
		} finally {
			closeStatement(statement);
			closeResultSet(resultSet);
		}
		return transaction;
	}

	@Override
	public List<Transaction> getAllTransactions() throws DAOException {
		List<Transaction> transactions = new ArrayList<>();
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try (ProxyConnection connection = PoolConnection.INSTANCE.getConnection()) {
			statement = connection.prepareStatement(GET_ALL_TRANSACTIONS);
			if (statement != null) {
				resultSet = statement.executeQuery();
				while (resultSet.next()) {
					transactions.add(buildTransaction(resultSet));
				}
			}
		} catch (SQLException e) {
			throw new DAOException("Cannot load transactions", e);
		} finally {
			closeStatement(statement);
			closeResultSet(resultSet);
		}
		return transactions;
	}

	@Override
	public List<Transaction> getAllTransactionsByAccountNumber(String number) throws DAOException {
		List<Transaction> transactions = new ArrayList<>();
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try (ProxyConnection connection = PoolConnection.INSTANCE.getConnection()) {
			statement = connection.prepareStatement(GET_ALL_TRANSACTIONS_BY_ACCOUNT_NUMBER);
			if (statement != null) {
				statement.setString(1, number);
				resultSet = statement.executeQuery();
				while (resultSet.next()) {
					transactions.add(buildTransaction(resultSet));
				}
			}
		} catch (SQLException e) {
			throw new DAOException("Cannot load transactions by account number", e);
		} finally {
			closeStatement(statement);
			closeResultSet(resultSet);
		}
		return transactions;
	}

	@Override
	public List<Transaction> getAllTransactionsByCardNumber(String number) throws DAOException {
		List<Transaction> transactions = new ArrayList<>();
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try (ProxyConnection connection = PoolConnection.INSTANCE.getConnection()) {
			statement = connection.prepareStatement(GET_ALL_TRANSACTIONS_BY_CARD_NUMBER);
			if (statement != null) {
				statement.setString(1, number);
				resultSet = statement.executeQuery();
				while (resultSet.next()) {
					transactions.add(buildTransaction(resultSet));
				}
			}
		} catch (SQLException e) {
			throw new DAOException("Cannot load transactions by card number", e);
		} finally {
			closeStatement(statement);
			closeResultSet(resultSet);
		}
		return transactions;
	}

}
