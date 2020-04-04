package by.training.payment.dao.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import by.training.payment.dao.BankAccountDAO;
import by.training.payment.entity.BankAccount;
import by.training.payment.exception.DAOException;
import by.training.payment.pool.PoolConnection;
import by.training.payment.pool.ProxyConnection;

public class MySQLBankAccountDAO extends SQLUtil implements BankAccountDAO {

	private static final String ADD_BANK_ACCOUNT = "INSERT INTO bank_account (bank_account_number, bank_account_balance, currency_name, bank_user_login) VALUES (?, ?, ?, ?);";
	private static final String UPDATE_BANK_ACCOUNT = "UPDATE bank_account SET bank_account_opening_date=?, bank_account_balance=?, currency_name=?, bank_user_login=?, bank_account_is_blocked=? WHERE bank_account_number = ?;";
	private static final String DELETE_BANK_ACCOUNT = "DELETE FROM bank_account WHERE bank_account_number = ?;";
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

	@Override	
	public void addBankAccount(BankAccount bankAccount) throws DAOException {
		PreparedStatement statement = null;
		try (ProxyConnection connection = PoolConnection.INSTANCE.getConnection()) {
			if (connection != null) {
				statement = connection.prepareStatement(ADD_BANK_ACCOUNT);
				if (statement != null) {
					statement.setString(1, bankAccount.getAccountNumber());
					statement.setBigDecimal(2, bankAccount.getBalance());
					statement.setString(3, bankAccount.getCurrency().getName());
					statement.setString(4, bankAccount.getUser().getLogin());
					statement.executeUpdate();
				}
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
			if (connection != null) {
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
			if (connection != null) {
				statement = connection.prepareStatement(DELETE_BANK_ACCOUNT);
				if (statement != null) {
					statement.setString(1, bankAccount.getAccountNumber());
					statement.executeUpdate();
				}
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
			if (connection != null) {
				statement = connection.prepareStatement(GET_BANK_ACCOUNT_BY_ACCOUNT_NUMBER);
				if (statement != null) {
					statement.setString(1, accountNumber);
					resultSet = statement.executeQuery();
					if (resultSet.next()) {
						bankAccount = buildBankAccount(resultSet);
					}
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
			if (connection != null) {
				statement = connection.prepareStatement(GET_ALL_BANK_ACCOUNTS);
				if (statement != null) {
					resultSet = statement.executeQuery();
					while (resultSet.next()) {
						bankAccounts.add(buildBankAccount(resultSet));
					}
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
			if (connection != null) {
				statement = connection.prepareStatement(GET_ALL_BANK_ACCOUNTS_BY_USER_LOGIN);
				if (statement != null) {
					statement.setString(1, login);
					resultSet = statement.executeQuery();
					while (resultSet.next()) {
						bankAccounts.add(buildBankAccount(resultSet));
					}
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


}
