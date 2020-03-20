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

	private static final String ADD_TRANSACTION = "INSERT INTO payment_transaction (transaction_completed, transaction_is_write_off, transaction_amount, transaction_currency_id, transaction_payment_purpose, bank_account_id) VALUES (?, ?, ?, ?, ?, ?);";
	private static final String UPDATE_TRANSACTION = "UPDATE payment_transaction SET transaction_completed=?, transaction_date=?, transaction_is_write_off=?, transaction_amount=?, transaction_currency_id=?, transaction_payment_purpose=?, bank_account_id=? WHERE transaction_id = ?;";
	private static final String DELETE_TRANSACTION = "DELETE FROM payment_transaction WHERE transaction_id = ?;";
	private static final String GET_TRANSACTION_BY_ID = "SELECT * FROM payment_transaction INNER JOIN currency "
			+ "ON payment_transaction.transaction_currency_id = currency.currency_id "
			+ "INNER JOIN bank_account ON payment_transaction.bank_account_id = bank_account.bank_account_id "
			+ "INNER JOIN bank_user ON bank_account.bank_user_id = bank_user.bank_user_id "
			+ "INNER JOIN user_role ON user_role.user_role_id = bank_user.user_role_id "
			+ "WHERE payment_transaction.transaction_id = ?;";
	private static final String GET_ALL_TRANSACTIONS = "SELECT * FROM payment_transaction INNER JOIN currency "
			+ "ON payment_transaction.transaction_currency_id = currency.currency_id "
			+ "INNER JOIN bank_account ON payment_transaction.bank_account_id = bank_account.bank_account_id "
			+ "INNER JOIN bank_user ON bank_account.bank_user_id = bank_user.bank_user_id "
			+ "INNER JOIN user_role ON user_role.user_role_id = bank_user.user_role_id;";
	private static final String GET_ALL_TRANSACTIONS_BY_ACCOUNT_ID = "SELECT * FROM payment_transaction INNER JOIN currency "
			+ "ON payment_transaction.transaction_currency_id = currency.currency_id "
			+ "INNER JOIN bank_account ON payment_transaction.bank_account_id = bank_account.bank_account_id "
			+ "INNER JOIN bank_user ON bank_account.bank_user_id = bank_user.bank_user_id "
			+ "INNER JOIN user_role ON user_role.user_role_id = bank_user.user_role_id "
			+ "WHERE bank_account.bank_account_id = ?;";
	private static final String GET_ALL_TRANSACTIONS_BY_CARD_ID = "SELECT * FROM payment_transaction INNER JOIN currency "
			+ "ON payment_transaction.transaction_currency_id = currency.currency_id "
			+ "INNER JOIN bank_account ON payment_transaction.bank_account_id = bank_account.bank_account_id "
			+ "INNER JOIN bank_user ON bank_account.bank_user_id = bank_user.bank_user_id "
			+ "INNER JOIN user_role ON user_role.user_role_id = bank_user.user_role_id "
			+ "INNER JOIN card ON card.bank_account_id = bank_account.bank_account_id WHERE card.card_id = ?;";

	@Override
	public void addTransaction(Transaction transaction) throws DAOException {
		PreparedStatement statement = null;
		try (ProxyConnection connection = PoolConnection.INSTANCE.getConnection()) {
			if (connection != null) {
				statement = connection.prepareStatement(ADD_TRANSACTION);
				if (statement != null) {
					statement.setBoolean(1, transaction.isCompleted());
					statement.setBoolean(2, transaction.isWriteOff());
					statement.setBigDecimal(3, transaction.getAmount());
					statement.setInt(4, transaction.getCurrency().getId());
					statement.setString(5, transaction.getPaymentPurpose());
					statement.setInt(6, transaction.getAccount().getId());
					statement.executeUpdate();
				}
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
			if (connection != null) {
				statement = connection.prepareStatement(UPDATE_TRANSACTION);
				if (statement != null) {
					statement.setBoolean(1, transaction.isCompleted());
					statement.setTimestamp(2, new Timestamp(transaction.getDate().getTime()));
					statement.setBoolean(3, transaction.isWriteOff());
					statement.setBigDecimal(4, transaction.getAmount());
					statement.setInt(5, transaction.getCurrency().getId());
					statement.setString(6, transaction.getPaymentPurpose());
					statement.setInt(7, transaction.getAccount().getId());
					statement.setInt(8, transaction.getId());
					statement.executeUpdate();
				}
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
			if (connection != null) {
				statement = connection.prepareStatement(DELETE_TRANSACTION);
				if (statement != null) {
					statement.setInt(1, transaction.getId());
					statement.executeUpdate();
				}
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
			if (connection != null) {
				statement = connection.prepareStatement(GET_TRANSACTION_BY_ID);
				if (statement != null) {
					statement.setInt(1, id);
					resultSet = statement.executeQuery();
					if (resultSet != null && resultSet.next()) {
						transaction = buildTransaction(resultSet);
					}
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
			if (connection != null) {
				statement = connection.prepareStatement(GET_ALL_TRANSACTIONS);
				if (statement != null) {
					resultSet = statement.executeQuery();
					if (resultSet != null) {
						while (resultSet.next()) {
							transactions.add(buildTransaction(resultSet));
						}
					}
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
	public List<Transaction> getAllTransactionsByAccountId(int accountId) throws DAOException {
		List<Transaction> transactions = new ArrayList<>();
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try (ProxyConnection connection = PoolConnection.INSTANCE.getConnection()) {
			if (connection != null) {
				statement = connection.prepareStatement(GET_ALL_TRANSACTIONS_BY_ACCOUNT_ID);
				if (statement != null) {
					statement.setInt(1, accountId);
					resultSet = statement.executeQuery();
					if (resultSet != null) {
						while (resultSet.next()) {
							transactions.add(buildTransaction(resultSet));
						}
					}
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
	public List<Transaction> getAllTransactionsByCardId(int cardId) throws DAOException {
		List<Transaction> transactions = new ArrayList<>();
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try (ProxyConnection connection = PoolConnection.INSTANCE.getConnection()) {
			if (connection != null) {
				statement = connection.prepareStatement(GET_ALL_TRANSACTIONS_BY_CARD_ID);
				if (statement != null) {
					statement.setInt(1, cardId);
					resultSet = statement.executeQuery();
					if (resultSet != null) {
						while (resultSet.next()) {
							transactions.add(buildTransaction(resultSet));
						}
					}
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

}
