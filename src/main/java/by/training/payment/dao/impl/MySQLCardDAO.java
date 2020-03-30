package by.training.payment.dao.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import by.training.payment.dao.CardDAO;
import by.training.payment.entity.Card;
import by.training.payment.exception.DAOException;
import by.training.payment.pool.PoolConnection;
import by.training.payment.pool.ProxyConnection;

public class MySQLCardDAO extends SQLUtil implements CardDAO {

	private static final String ADD_CARD = "INSERT INTO card (card_valid_until_date, card_number, card_number_mask, card_ccv_code, card_expiration_date_id, payment_system_id, bank_account_id, bank_user_id, card_is_blocked) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);";
	private static final String UPDATE_CARD = "UPDATE card SET card_opening_date=?, card_valid_until_date=?, card_number=?, card_number_mask=?, card_ccv_code=?, card_expiration_date_id=?, payment_system_id=?, bank_account_id=?, bank_user_id=?, card_is_blocked=? WHERE card_id = ?;";
	private static final String DELETE_CARD = "DELETE FROM card WHERE card_id = ?;";
	private static final String GET_CARD_BY_ID = "SELECT * FROM card INNER JOIN payment_system ON card.payment_system_id = payment_system.payment_system_id "
			+ "INNER JOIN card_expiration_date ON card_expiration_date.card_expiration_date_id = card.card_expiration_date_id "
			+ "INNER JOIN bank_account ON bank_account.bank_account_id = card.bank_account_id "
			+ "INNER JOIN currency ON bank_account.currency_id = currency.currency_id "
			+ "INNER JOIN bank_user ON bank_account.bank_user_id = bank_user.bank_user_id "
			+ "INNER JOIN user_role ON user_role.user_role_id = bank_user.user_role_id WHERE card.card_id = ?;";
	private static final String GET_ALL_CARDS = "SELECT * FROM card INNER JOIN payment_system ON card.payment_system_id = payment_system.payment_system_id "
			+ "INNER JOIN card_expiration_date ON card_expiration_date.card_expiration_date_id = card.card_expiration_date_id "
			+ "INNER JOIN bank_account ON bank_account.bank_account_id = card.bank_account_id "
			+ "INNER JOIN currency ON bank_account.currency_id = currency.currency_id "
			+ "INNER JOIN bank_user ON bank_account.bank_user_id = bank_user.bank_user_id "
			+ "INNER JOIN user_role ON user_role.user_role_id = bank_user.user_role_id;";
	private static final String GET_ALL_CARDS_BY_USER_ID = "SELECT * FROM card INNER JOIN payment_system ON card.payment_system_id = payment_system.payment_system_id "
			+ "INNER JOIN card_expiration_date ON card_expiration_date.card_expiration_date_id = card.card_expiration_date_id "
			+ "INNER JOIN bank_account ON bank_account.bank_account_id = card.bank_account_id "
			+ "INNER JOIN currency ON bank_account.currency_id = currency.currency_id "
			+ "INNER JOIN bank_user ON bank_account.bank_user_id = bank_user.bank_user_id "
			+ "INNER JOIN user_role ON user_role.user_role_id = bank_user.user_role_id WHERE bank_user.bank_user_id = ?;";
	private static final String GET_ALL_CARDS_BY_ACCOUNT_ID = "SELECT * FROM card INNER JOIN payment_system ON card.payment_system_id = payment_system.payment_system_id "
			+ "INNER JOIN card_expiration_date ON card_expiration_date.card_expiration_date_id = card.card_expiration_date_id "
			+ "INNER JOIN bank_account ON bank_account.bank_account_id = card.bank_account_id "
			+ "INNER JOIN currency ON bank_account.currency_id = currency.currency_id "
			+ "INNER JOIN bank_user ON bank_account.bank_user_id = bank_user.bank_user_id "
			+ "INNER JOIN user_role ON user_role.user_role_id = bank_user.user_role_id WHERE bank_account.bank_account_id = ?;";

	@Override
	public void addCard(Card card) throws DAOException {
		PreparedStatement statement = null;
		try (ProxyConnection connection = PoolConnection.INSTANCE.getConnection()) {
			if (connection != null) {
				statement = connection.prepareStatement(ADD_CARD);
				if (statement != null) {
					statement.setTimestamp(1, new Timestamp(card.getValidUntilDate().getTime()));
					statement.setString(2, card.getNumber());
					statement.setString(3, card.getNumberMask());
					statement.setString(4, card.getCcv());
					statement.setInt(5, card.getExpirationDate().getId());
					statement.setInt(6, card.getPaymentSystem().getId());
					statement.setInt(7, card.getBankAccount().getId());
					statement.setInt(8, card.getUser().getId());
					statement.setBoolean(9, card.isBlocked());
					statement.executeUpdate();
				}
			}
		} catch (SQLException e) {
			throw new DAOException("Cannot add card", e);
		} finally {
			closeStatement(statement);
		}
	}

	@Override
	public void updateCard(Card card) throws DAOException {
		PreparedStatement statement = null;
		try (ProxyConnection connection = PoolConnection.INSTANCE.getConnection()) {
			if (connection != null) {
				statement = connection.prepareStatement(UPDATE_CARD);
				if (statement != null) {
					statement.setTimestamp(1, new Timestamp(card.getOpeningDate().getTime()));
					statement.setTimestamp(2, new Timestamp(card.getValidUntilDate().getTime()));
					statement.setString(3, card.getNumber());
					statement.setString(4, card.getNumberMask());
					statement.setString(5, card.getCcv());
					statement.setInt(6, card.getExpirationDate().getId());
					statement.setInt(7, card.getPaymentSystem().getId());
					statement.setInt(8, card.getBankAccount().getId());
					statement.setInt(9, card.getUser().getId());
					statement.setBoolean(10, card.isBlocked());
					statement.setInt(11, card.getId());
					statement.executeUpdate();
				}
			}
		} catch (SQLException e) {
			throw new DAOException("Cannot update card", e);
		} finally {
			closeStatement(statement);
		}
	}

	@Override
	public void deleteCard(Card card) throws DAOException {
		PreparedStatement statement = null;
		try (ProxyConnection connection = PoolConnection.INSTANCE.getConnection()) {
			if (connection != null) {
				statement = connection.prepareStatement(DELETE_CARD);
				if (statement != null) {
					statement.setInt(1, card.getId());
					statement.executeUpdate();
				}
			}
		} catch (SQLException e) {
			throw new DAOException("Cannot delete card", e);
		} finally {
			closeStatement(statement);
		}
	}

	@Override
	public Card getCardById(int id) throws DAOException {
		Card card = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try (ProxyConnection connection = PoolConnection.INSTANCE.getConnection()) {
			if (connection != null) {
				statement = connection.prepareStatement(GET_CARD_BY_ID);
				if (statement != null) {
					statement.setInt(1, id);
					resultSet = statement.executeQuery();
					if (resultSet.next()) {
						card = buildCard(resultSet);
					}
				}
			}
		} catch (SQLException e) {
			throw new DAOException("Cannot load card", e);
		} finally {
			closeStatement(statement);
			closeResultSet(resultSet);
		}
		return card;
	}

	@Override
	public List<Card> getAllCards() throws DAOException {
		List<Card> cards = new ArrayList<>();
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try (ProxyConnection connection = PoolConnection.INSTANCE.getConnection()) {
			if (connection != null) {
				statement = connection.prepareStatement(GET_ALL_CARDS);
				if (statement != null) {
					resultSet = statement.executeQuery();
					while (resultSet.next()) {
						cards.add(buildCard(resultSet));
					}
				}
			}
		} catch (SQLException e) {
			throw new DAOException("Cannot load card list", e);
		} finally {
			closeStatement(statement);
			closeResultSet(resultSet);
		}
		return cards;
	}

	@Override
	public List<Card> getAllCardsByUserId(int userId) throws DAOException {
		List<Card> cards = new ArrayList<>();
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try (ProxyConnection connection = PoolConnection.INSTANCE.getConnection()) {
			if (connection != null) {
				statement = connection.prepareStatement(GET_ALL_CARDS_BY_USER_ID);
				if (statement != null) {
					statement.setInt(1, userId);
					resultSet = statement.executeQuery();
					while (resultSet.next()) {
						cards.add(buildCard(resultSet));
					}
				}
			}
		} catch (SQLException e) {
			throw new DAOException("Cannot load card list by user id", e);
		} finally {
			closeStatement(statement);
			closeResultSet(resultSet);
		}
		return cards;
	}

	@Override
	public List<Card> getAllCardsByAccountId(int accountId) throws DAOException {
		List<Card> cards = new ArrayList<>();
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try (ProxyConnection connection = PoolConnection.INSTANCE.getConnection()) {
			if (connection != null) {
				statement = connection.prepareStatement(GET_ALL_CARDS_BY_ACCOUNT_ID);
				if (statement != null) {
					statement.setInt(1, accountId);
					resultSet = statement.executeQuery();
					while (resultSet.next()) {
						cards.add(buildCard(resultSet));
					}
				}
			}
		} catch (SQLException e) {
			throw new DAOException("Cannot load card list by account id", e);
		} finally {
			closeStatement(statement);
			closeResultSet(resultSet);
		}
		return cards;
	}

}
