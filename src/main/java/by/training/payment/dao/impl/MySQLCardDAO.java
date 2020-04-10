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

	private static final String ADD_CARD = "INSERT INTO card (card_number, card_valid_until_date, card_number_mask, card_ccv_code, card_expiration_date_value, payment_system_name, bank_account_number, bank_user_login, card_is_blocked) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);";
	private static final String UPDATE_CARD = "UPDATE card SET card_opening_date=?, card_valid_until_date=?, card_number_mask=?, card_ccv_code=?, card_expiration_date_value=?, payment_system_name=?, bank_account_number=?, bank_user_login=?, card_is_blocked=? WHERE card_number = ?;";
	private static final String DELETE_CARD = "DELETE FROM card WHERE card_number = ?;";
	private static final String GET_CARD_BY_CARD_NUMBER = "SELECT * FROM card INNER JOIN payment_system ON card.payment_system_name = payment_system.payment_system_name "
			+ "INNER JOIN card_expiration_date ON card_expiration_date.card_expiration_date_value = card.card_expiration_date_value "
			+ "INNER JOIN bank_account ON bank_account.bank_account_number = card.bank_account_number "
			+ "INNER JOIN currency ON bank_account.currency_name = currency.currency_name "
			+ "INNER JOIN bank_user ON bank_account.bank_user_login = bank_user.bank_user_login "
			+ "INNER JOIN user_role ON user_role.user_role_value = bank_user.user_role_value WHERE card.card_number = ?;";
	private static final String GET_ALL_CARDS = "SELECT * FROM card INNER JOIN payment_system ON card.payment_system_name = payment_system.payment_system_name "
			+ "INNER JOIN card_expiration_date ON card_expiration_date.card_expiration_date_value = card.card_expiration_date_value "
			+ "INNER JOIN bank_account ON bank_account.bank_account_number = card.bank_account_number "
			+ "INNER JOIN currency ON bank_account.currency_name = currency.currency_name "
			+ "INNER JOIN bank_user ON bank_account.bank_user_login = bank_user.bank_user_login "
			+ "INNER JOIN user_role ON user_role.user_role_value = bank_user.user_role_value;";
	private static final String GET_ALL_UNBLOCKED_CARDS = "SELECT * FROM card INNER JOIN payment_system ON card.payment_system_name = payment_system.payment_system_name "
			+ "INNER JOIN card_expiration_date ON card_expiration_date.card_expiration_date_value = card.card_expiration_date_value "
			+ "INNER JOIN bank_account ON bank_account.bank_account_number = card.bank_account_number "
			+ "INNER JOIN currency ON bank_account.currency_name = currency.currency_name "
			+ "INNER JOIN bank_user ON bank_account.bank_user_login = bank_user.bank_user_login "
			+ "INNER JOIN user_role ON user_role.user_role_value = bank_user.user_role_value WHERE card.card_is_blocked='false';";
	private static final String GET_ALL_CARDS_BY_USER_LOGIN = "SELECT * FROM card INNER JOIN payment_system ON card.payment_system_name = payment_system.payment_system_name "
			+ "INNER JOIN card_expiration_date ON card_expiration_date.card_expiration_date_value = card.card_expiration_date_value "
			+ "INNER JOIN bank_account ON bank_account.bank_account_number = card.bank_account_number "
			+ "INNER JOIN currency ON bank_account.currency_name = currency.currency_name "
			+ "INNER JOIN bank_user ON bank_account.bank_user_login = bank_user.bank_user_login "
			+ "INNER JOIN user_role ON user_role.user_role_value = bank_user.user_role_value WHERE bank_user.bank_user_login = ?;";
	private static final String GET_ALL_CARDS_BY_ACCOUNT_NUMBER = "SELECT * FROM card INNER JOIN payment_system ON card.payment_system_name = payment_system.payment_system_name "
			+ "INNER JOIN card_expiration_date ON card_expiration_date.card_expiration_date_value = card.card_expiration_date_value "
			+ "INNER JOIN bank_account ON bank_account.bank_account_number = card.bank_account_number "
			+ "INNER JOIN currency ON bank_account.currency_name = currency.currency_name "
			+ "INNER JOIN bank_user ON bank_account.bank_user_login = bank_user.bank_user_login "
			+ "INNER JOIN user_role ON user_role.user_role_value = bank_user.user_role_value WHERE bank_account.bank_account_number = ?;";

	@Override
	public void addCard(Card card) throws DAOException {
		PreparedStatement statement = null;
		try (ProxyConnection connection = PoolConnection.INSTANCE.getConnection()) {
			statement = connection.prepareStatement(ADD_CARD);
			if (statement != null) {
				statement.setString(1, card.getNumber());
				statement.setTimestamp(2, new Timestamp(card.getValidUntilDate().getTime()));
				statement.setString(3, card.getNumberMask());
				statement.setString(4, card.getCcv());
				statement.setInt(5, card.getExpirationDate().getValue());
				statement.setString(6, card.getPaymentSystem().getName());
				statement.setString(7, card.getBankAccount().getAccountNumber());
				statement.setString(8, card.getUser().getLogin());
				statement.setBoolean(9, card.getIsBlocked());
				statement.executeUpdate();
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
			statement = connection.prepareStatement(UPDATE_CARD);
			if (statement != null) {
				statement.setTimestamp(1, new Timestamp(card.getOpeningDate().getTime()));
				statement.setTimestamp(2, new Timestamp(card.getValidUntilDate().getTime()));
				statement.setString(3, card.getNumberMask());
				statement.setString(4, card.getCcv());
				statement.setInt(5, card.getExpirationDate().getValue());
				statement.setString(6, card.getPaymentSystem().getName());
				statement.setString(7, card.getBankAccount().getAccountNumber());
				statement.setString(8, card.getUser().getLogin());
				statement.setBoolean(9, card.getIsBlocked());
				statement.setString(10, card.getNumber());
				statement.executeUpdate();
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
			statement = connection.prepareStatement(DELETE_CARD);
			if (statement != null) {
				statement.setString(1, card.getNumber());
				statement.executeUpdate();
			}
		} catch (SQLException e) {
			throw new DAOException("Cannot delete card", e);
		} finally {
			closeStatement(statement);
		}
	}

	@Override
	public List<Card> getAllCards() throws DAOException {
		List<Card> cards = new ArrayList<>();
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try (ProxyConnection connection = PoolConnection.INSTANCE.getConnection()) {
			statement = connection.prepareStatement(GET_ALL_CARDS);
			if (statement != null) {
				resultSet = statement.executeQuery();
				while (resultSet.next()) {
					cards.add(buildCard(resultSet));
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
	public Card getCardByCardNumber(String number) throws DAOException {
		Card card = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try (ProxyConnection connection = PoolConnection.INSTANCE.getConnection()) {
			statement = connection.prepareStatement(GET_CARD_BY_CARD_NUMBER);
			if (statement != null) {
				statement.setString(1, number);
				resultSet = statement.executeQuery();
				if (resultSet.next()) {
					card = buildCard(resultSet);
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
	public List<Card> getAllCardsByUserLogin(String login) throws DAOException {
		List<Card> cards = new ArrayList<>();
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try (ProxyConnection connection = PoolConnection.INSTANCE.getConnection()) {
			statement = connection.prepareStatement(GET_ALL_CARDS_BY_USER_LOGIN);
			if (statement != null) {
				statement.setString(1, login);
				resultSet = statement.executeQuery();
				while (resultSet.next()) {
					cards.add(buildCard(resultSet));
				}
			}
		} catch (SQLException e) {
			throw new DAOException("Cannot load card list by user login", e);
		} finally {
			closeStatement(statement);
			closeResultSet(resultSet);
		}
		return cards;
	}

	@Override
	public List<Card> getAllCardsByAccountNumber(String number) throws DAOException {
		List<Card> cards = new ArrayList<>();
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try (ProxyConnection connection = PoolConnection.INSTANCE.getConnection()) {
			statement = connection.prepareStatement(GET_ALL_CARDS_BY_ACCOUNT_NUMBER);
			if (statement != null) {
				statement.setString(1, number);
				resultSet = statement.executeQuery();
				while (resultSet.next()) {
					cards.add(buildCard(resultSet));
				}
			}
		} catch (SQLException e) {
			throw new DAOException("Cannot load card list by account number", e);
		} finally {
			closeStatement(statement);
			closeResultSet(resultSet);
		}
		return cards;
	}

	@Override
	public List<Card> getAllUnblockedCards() throws DAOException {
		List<Card> cards = new ArrayList<>();
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try (ProxyConnection connection = PoolConnection.INSTANCE.getConnection()) {
			statement = connection.prepareStatement(GET_ALL_UNBLOCKED_CARDS);
			if (statement != null) {
				resultSet = statement.executeQuery();
				while (resultSet.next()) {
					cards.add(buildCard(resultSet));
				}
			}
		} catch (SQLException e) {
			throw new DAOException("Cannot load unblocked card list", e);
		} finally {
			closeStatement(statement);
			closeResultSet(resultSet);
		}
		return cards;
	}
}
