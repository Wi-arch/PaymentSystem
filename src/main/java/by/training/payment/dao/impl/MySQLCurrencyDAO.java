package by.training.payment.dao.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import by.training.payment.dao.CurrencyDAO;
import by.training.payment.entity.Currency;
import by.training.payment.exception.DAOException;
import by.training.payment.pool.PoolConnection;
import by.training.payment.pool.ProxyConnection;

public class MySQLCurrencyDAO extends SQLUtil implements CurrencyDAO {

	private static final String ADD_CURRENCY = "INSERT INTO currency (currency_rate, currency_scale, currency_name, currency_update_date, currency_is_base_currency) VALUES (?, ?, ?, ?, ?);";
	private static final String UPDATE_CURRENCY = "UPDATE currency SET currency_rate=?, currency_scale=?, currency_name=?, currency_is_base_currency=? WHERE currency_id = ?;";
	private static final String DELETE_CURRENCY = "DELETE FROM currency WHERE currency_id = ?;";
	private static final String GET_CURRENCY_BY_ID = "SELECT * FROM currency WHERE currency_id = ?";
	private static final String GET_ALL_CURRENCIES = "SELECT * FROM currency";

	@Override
	public void addCurrency(Currency currency) throws DAOException {
		PreparedStatement statement = null;
		try (ProxyConnection connection = PoolConnection.INSTANCE.getConnection()) {
			if (connection != null) {
				statement = connection.prepareStatement(ADD_CURRENCY);
				if (statement != null) {
					statement.setBigDecimal(1, currency.getRate());
					statement.setInt(2, currency.getScale());
					statement.setString(3, currency.getName());
					statement.setTimestamp(4, new Timestamp(currency.getUpdateDate().getTime()));
					statement.setBoolean(5, currency.isBaseCurrency());
					statement.executeUpdate();
				}
			}
		} catch (SQLException e) {
			throw new DAOException("Cannot add currency", e);
		} finally {
			closeStatement(statement);
		}
	}

	@Override
	public void updateCurrency(Currency currency) throws DAOException {
		PreparedStatement statement = null;
		try (ProxyConnection connection = PoolConnection.INSTANCE.getConnection()) {
			if (connection != null) {
				statement = connection.prepareStatement(UPDATE_CURRENCY);
				if (statement != null) {
					statement.setBigDecimal(1, currency.getRate());
					statement.setInt(2, currency.getScale());
					statement.setString(3, currency.getName());
					statement.setBoolean(4, currency.isBaseCurrency());
					statement.setInt(5, currency.getId());
					statement.executeUpdate();
				}
			}
		} catch (SQLException e) {
			throw new DAOException("Cannot update currency", e);
		} finally {
			closeStatement(statement);
		}
	}

	@Override
	public void deleteCurrency(Currency currency) throws DAOException {
		PreparedStatement statement = null;
		try (ProxyConnection connection = PoolConnection.INSTANCE.getConnection()) {
			if (connection != null) {
				statement = connection.prepareStatement(DELETE_CURRENCY);
				if (statement != null) {
					statement.setInt(1, currency.getId());
					statement.executeUpdate();
				}
			}
		} catch (SQLException e) {
			throw new DAOException("Cannot delete currency", e);
		} finally {
			closeStatement(statement);
		}
	}

	@Override
	public Currency getCurrencyById(int id) throws DAOException {
		Currency currency = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try (ProxyConnection connection = PoolConnection.INSTANCE.getConnection()) {
			if (connection != null) {
				statement = connection.prepareStatement(GET_CURRENCY_BY_ID);
				if (statement != null) {
					statement.setInt(1, id);
					resultSet = statement.executeQuery();
					if (resultSet.next()) {
						currency = buildCurrency(resultSet);
					}
				}
			}
		} catch (SQLException e) {
			throw new DAOException("Cannot load currency", e);
		} finally {
			closeStatement(statement);
			closeResultSet(resultSet);
		}
		return currency;
	}

	@Override
	public List<Currency> getAllCurrencies() throws DAOException {
		List<Currency> currencies = new ArrayList<>();
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try (ProxyConnection connection = PoolConnection.INSTANCE.getConnection()) {
			if (connection != null) {
				statement = connection.prepareStatement(GET_ALL_CURRENCIES);
				if (statement != null) {
					resultSet = statement.executeQuery();
					while (resultSet.next()) {
						currencies.add(buildCurrency(resultSet));
					}
				}
			}
		} catch (SQLException e) {
			throw new DAOException("Cannot load currency list", e);
		} finally {
			closeStatement(statement);
			closeResultSet(resultSet);
		}
		return currencies;
	}

}
