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

	private static final String ADD_BANK_ACCOUNT = "INSERT INTO bank_account (bank_account_number, bank_account_balance, currency_id, bank_user_id) VALUES (?, ?, ?, ?);";
	private static final String UPDATE_BANK_ACCOUNT = "UPDATE bank_account SET bank_account_number=?, bank_account_opening_date=?, bank_account_balance=?, currency_id=?, bank_user_id=?, bank_account_is_blocked=? WHERE bank_account_id = ?;";
	private static final String DELETE_BANK_ACCOUNT = "DELETE FROM bank_account WHERE bank_account_id = ?;";
	private static final String GET_BANK_ACCOUNT_BY_ID = "SELECT * FROM bank_account INNER JOIN currency "
			+ "ON bank_account.currency_id = currency.currency_id "
			+ "INNER JOIN bank_user ON bank_account.bank_user_id = bank_user.bank_user_id "
			+ "INNER JOIN user_role ON user_role.user_role_id = bank_user.user_role_id "
			+ "WHERE bank_account.bank_account_id = ?;";
	private static final String GET_ALL_BANK_ACCOUNTS = "SELECT * FROM bank_account INNER JOIN currency "
			+ "ON bank_account.currency_id = currency.currency_id "
			+ "INNER JOIN bank_user ON bank_account.bank_user_id = bank_user.bank_user_id "
			+ "INNER JOIN user_role ON user_role.user_role_id = bank_user.user_role_id;";

	@Override
	public void addBankAccount(BankAccount bankAccount) throws DAOException {
		PreparedStatement statement = null;
		try (ProxyConnection connection = PoolConnection.INSTANCE.getConnection()) {
			if (connection != null) {
				statement = connection.prepareStatement(ADD_BANK_ACCOUNT);
				if (statement != null) {
					statement.setString(1, bankAccount.getAccountNumber());
					statement.setBigDecimal(2, bankAccount.getBalance());
					statement.setInt(3, bankAccount.getCurrency().getId());
					statement.setInt(4, bankAccount.getUser().getId());
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
					statement.setString(1, bankAccount.getAccountNumber());
					statement.setTimestamp(2, new Timestamp(bankAccount.getOpeningDate().getTime()));
					statement.setBigDecimal(3, bankAccount.getBalance());
					statement.setInt(4, bankAccount.getCurrency().getId());
					statement.setInt(5, bankAccount.getUser().getId());
					statement.setBoolean(6, bankAccount.isBlocked());
					statement.setInt(7, bankAccount.getId());
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
					statement.setInt(1, bankAccount.getId());
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
	public BankAccount getBankAccountById(int id) throws DAOException {
		BankAccount bankAccount = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try (ProxyConnection connection = PoolConnection.INSTANCE.getConnection()) {
			if (connection != null) {
				statement = connection.prepareStatement(GET_BANK_ACCOUNT_BY_ID);
				if (statement != null) {
					statement.setInt(1, id);
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
}
