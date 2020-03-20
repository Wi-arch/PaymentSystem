package by.training.payment.dao.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import by.training.payment.dao.CardExpirationDateDAO;
import by.training.payment.entity.CardExpirationDate;
import by.training.payment.exception.DAOException;
import by.training.payment.pool.PoolConnection;
import by.training.payment.pool.ProxyConnection;

public class MySQLCardExpirationDateDAO extends SQLUtil implements CardExpirationDateDAO {

	private static final String GET_CARD_EXPIRATION_DATE_BY_ID = "SELECT * FROM card_expiration_date WHERE card_expiration_date_id = ?";
	private static final String GET_ALL_CARD_EXPIRATION_DATES = "SELECT * FROM card_expiration_date";

	@Override
	public CardExpirationDate getCardExpirationDateById(int id) throws DAOException {
		CardExpirationDate cardExpirationDate = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try (ProxyConnection connection = PoolConnection.INSTANCE.getConnection()) {
			if (connection != null) {
				statement = connection.prepareStatement(GET_CARD_EXPIRATION_DATE_BY_ID);
				if (statement != null) {
					statement.setInt(1, id);
					resultSet = statement.executeQuery();
					if (resultSet != null && resultSet.next()) {
						cardExpirationDate = buildCardExpirationDate(resultSet);
					}
				}
			}
		} catch (SQLException e) {
			throw new DAOException("Cannot load card expiration date", e);
		} finally {
			closeStatement(statement);
			closeResultSet(resultSet);
		}
		return cardExpirationDate;
	}

	@Override
	public List<CardExpirationDate> getAllCardExpirationDates() throws DAOException {
		List<CardExpirationDate> cardExpirationDates = new ArrayList<>();
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try (ProxyConnection connection = PoolConnection.INSTANCE.getConnection()) {
			if (connection != null) {
				statement = connection.prepareStatement(GET_ALL_CARD_EXPIRATION_DATES);
				if (statement != null) {
					resultSet = statement.executeQuery();
					if (resultSet != null) {
						while (resultSet.next()) {
							cardExpirationDates.add(buildCardExpirationDate(resultSet));
						}
					}
				}
			}
		} catch (SQLException e) {
			throw new DAOException("Cannot load card expiration date list", e);
		} finally {
			closeStatement(statement);
			closeResultSet(resultSet);
		}
		return cardExpirationDates;
	}
}
