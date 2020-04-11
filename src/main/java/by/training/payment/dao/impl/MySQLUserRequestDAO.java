package by.training.payment.dao.impl;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import by.training.payment.dao.UserRequestDAO;
import by.training.payment.entity.BankAccount;
import by.training.payment.entity.Card;
import by.training.payment.entity.RequestParameter;
import by.training.payment.entity.UserRequest;
import by.training.payment.exception.DAOException;
import by.training.payment.pool.PoolConnection;
import by.training.payment.pool.ProxyConnection;

public class MySQLUserRequestDAO extends SQLUtil implements UserRequestDAO {

	private static final String ADD_USER_REQUEST = "INSERT INTO user_request (bank_user_login, request_type_value) VALUES (?, ?);";
	private static final String UPDATE_USER_REQUEST = "UPDATE user_request SET bank_user_login=?, request_type_value=?, request_status_value=?, request_creation_date=?, request_handle_date=? WHERE user_request_id=?;";
	private static final String DELETE_USER_REQUEST = "DELETE FROM user_request WHERE user_request_id = ?;";
	private static final String GET_USER_REQUESTS_BY_ID = "SELECT * FROM user_request INNER JOIN bank_user ON user_request.bank_user_login = bank_user.bank_user_login INNER JOIN user_role ON bank_user.user_role_value = user_role.user_role_value INNER JOIN request_type ON request_type.request_type_value = user_request.request_type_value INNER JOIN request_status ON request_status.request_status_value = user_request.request_status_value WHERE user_request.user_request_id = ?;";
	private static final String GET_ALL_USER_REQUESTS = "SELECT * FROM user_request INNER JOIN bank_user ON user_request.bank_user_login = bank_user.bank_user_login INNER JOIN user_role ON bank_user.user_role_value = user_role.user_role_value INNER JOIN request_type ON request_type.request_type_value = user_request.request_type_value INNER JOIN request_status ON request_status.request_status_value = user_request.request_status_value;";
	private static final String GET_ALL_USER_REQUESTS_IN_PROCESSING = "SELECT * FROM user_request INNER JOIN bank_user ON user_request.bank_user_login = bank_user.bank_user_login INNER JOIN user_role ON bank_user.user_role_value = user_role.user_role_value INNER JOIN request_type ON request_type.request_type_value = user_request.request_type_value INNER JOIN request_status ON request_status.request_status_value = user_request.request_status_value WHERE request_status.request_status_value = 'In processing';";
	private static final String SAVE_REQUEST_PARAMETER = "{call PS_SaveRequestParameter(?,?,?)}";
	private static final String GET_ALL_USER_REQUESTS_BY_USER_LOGIN = "SELECT * FROM user_request INNER JOIN bank_user ON user_request.bank_user_login = bank_user.bank_user_login INNER JOIN user_role ON bank_user.user_role_value = user_role.user_role_value INNER JOIN request_type ON request_type.request_type_value = user_request.request_type_value INNER JOIN request_status ON request_status.request_status_value = user_request.request_status_value WHERE bank_user.bank_user_login = ?;";
	private static final String HANDLE_USER_REQUEST_TO_UNLOCK_CARD = "{call PS_HandleUserRequestToUnlockCard(?,?)}";
	private static final String HANDLE_USER_REQUEST_TO_OPEN_ACCOUNT = "{call PS_HandleUserRequestToOpenAccount(?,?,?,?)}";
	private static final String HANDLE_USER_REQUEST_TO_OPEN_CARD = "{call PS_HandleUserRequestToOpenCard(?,?,?,?,?,?,?,?,?,?)}";
	private static final String HANDLE_USER_REQUEST_TO_OPEN_CARD_TO_EXISTING_ACCOUNT = "{call PS_HandleUserRequestToOpenCardToExistingAccount(?,?,?,?,?,?,?,?,?)}";

	@Override
	public void addUserRequest(UserRequest userRequest) throws DAOException {
		PreparedStatement statement = null;
		try (ProxyConnection connection = PoolConnection.INSTANCE.getConnection()) {
			statement = connection.prepareStatement(ADD_USER_REQUEST);
			if (statement != null) {
				statement.setString(1, userRequest.getUser().getLogin());
				statement.setString(2, userRequest.getRequestType().getValue());
				statement.executeUpdate();
			}
		} catch (SQLException e) {
			throw new DAOException("Cannot add UserRequest", e);
		} finally {
			closeStatement(statement);
		}
	}

	@Override
	public void updateUserRequest(UserRequest userRequest) throws DAOException {
		PreparedStatement statement = null;
		try (ProxyConnection connection = PoolConnection.INSTANCE.getConnection()) {
			statement = connection.prepareStatement(UPDATE_USER_REQUEST);
			if (statement != null) {
				statement.setString(1, userRequest.getUser().getLogin());
				statement.setString(2, userRequest.getRequestType().getValue());
				statement.setString(3, userRequest.getRequestStatus().getValue());
				statement.setTimestamp(4, new Timestamp(userRequest.getCreationDate().getTime()));
				statement.setTimestamp(5,
						userRequest.getHandlingDate() != null ? new Timestamp(userRequest.getHandlingDate().getTime())
								: null);
				statement.setInt(6, userRequest.getId());
				statement.executeUpdate();
			}
		} catch (SQLException e) {
			throw new DAOException("Cannot update UserRequest", e);
		} finally {
			closeStatement(statement);
		}
	}

	@Override
	public void deleteUserRequest(UserRequest userRequest) throws DAOException {
		PreparedStatement statement = null;
		try (ProxyConnection connection = PoolConnection.INSTANCE.getConnection()) {
			statement = connection.prepareStatement(DELETE_USER_REQUEST);
			if (statement != null) {
				statement.setInt(1, userRequest.getId());
				statement.executeUpdate();
			}
		} catch (SQLException e) {
			throw new DAOException("Cannot update UserRequest", e);
		} finally {
			closeStatement(statement);
		}
	}

	@Override
	public List<UserRequest> getAllUserRequests() throws DAOException {
		List<UserRequest> result = new ArrayList<>();
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try (ProxyConnection connection = PoolConnection.INSTANCE.getConnection()) {
			statement = connection.prepareStatement(GET_ALL_USER_REQUESTS);
			if (statement != null) {
				resultSet = statement.executeQuery();
				while (resultSet.next()) {
					result.add(buildUserRequest(resultSet));
				}
			}
		} catch (SQLException e) {
			throw new DAOException("Cannot load UserRequests", e);
		} finally {
			closeResultSet(resultSet);
			closeStatement(statement);
		}
		return result;
	}

	@Override
	public List<UserRequest> getAllUserRequestsInProcessing() throws DAOException {
		List<UserRequest> result = new ArrayList<>();
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try (ProxyConnection connection = PoolConnection.INSTANCE.getConnection()) {
			statement = connection.prepareStatement(GET_ALL_USER_REQUESTS_IN_PROCESSING);
			if (statement != null) {
				resultSet = statement.executeQuery();
				while (resultSet.next()) {
					result.add(buildUserRequest(resultSet));
				}
			}
		} catch (SQLException e) {
			throw new DAOException("Cannot load User requests in processing", e);
		} finally {
			closeResultSet(resultSet);
			closeStatement(statement);
		}
		return result;
	}

	@Override
	public UserRequest getUserRequestById(int id) throws DAOException {
		UserRequest userRequest = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try (ProxyConnection connection = PoolConnection.INSTANCE.getConnection()) {
			statement = connection.prepareStatement(GET_USER_REQUESTS_BY_ID);
			if (statement != null) {
				statement.setInt(1, id);
				resultSet = statement.executeQuery();
				if (resultSet.next()) {
					userRequest = buildUserRequest(resultSet);
				}
			}
		} catch (SQLException e) {
			throw new DAOException("Cannot load UserRequest", e);
		} finally {
			closeResultSet(resultSet);
			closeStatement(statement);
		}
		return userRequest;
	}

	@Override
	public void saveUserRequestWithParameterList(UserRequest userRequest, List<RequestParameter> requestParameters)
			throws DAOException {
		ProxyConnection connection = PoolConnection.INSTANCE.getConnection();
		PreparedStatement statement = null;
		CallableStatement callableStatement = null;
		ResultSet resultSet = null;
		int currentId = 0;
		try {
			connection.setAutoCommit(false);
			statement = connection.prepareStatement(ADD_USER_REQUEST, Statement.RETURN_GENERATED_KEYS);
			if (statement != null) {
				statement.setString(1, userRequest.getUser().getLogin());
				statement.setString(2, userRequest.getRequestType().getValue());
				statement.executeUpdate();
				resultSet = statement.getGeneratedKeys();
				if (resultSet.next()) {
					currentId = resultSet.getInt(1);
				}
				callableStatement = connection.prepareCall(SAVE_REQUEST_PARAMETER);
				for (RequestParameter parameter : requestParameters) {
					callableStatement.setString(1, parameter.getParameterHeader().getName());
					callableStatement.setInt(2, currentId);
					callableStatement.setString(3, parameter.getValue());
					callableStatement.addBatch();
				}
				callableStatement.executeBatch();
			}
			connection.commit();
		} catch (SQLException e) {
			rollbackTransaction(connection);
			throw new DAOException("Cannot save user request", e);
		} finally {
			closeResultSet(resultSet);
			closeStatement(statement);
			closeStatement(callableStatement);
			closeConnection(connection);
		}
	}

	@Override
	public List<UserRequest> getAllUserRequestsByUserLogin(String login) throws DAOException {
		List<UserRequest> result = new ArrayList<>();
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try (ProxyConnection connection = PoolConnection.INSTANCE.getConnection()) {
			if (connection != null) {
				statement = connection.prepareStatement(GET_ALL_USER_REQUESTS_BY_USER_LOGIN);
				if (statement != null) {
					statement.setString(1, login);
					resultSet = statement.executeQuery();
					while (resultSet.next()) {
						result.add(buildUserRequest(resultSet));
					}
				}
			}
		} catch (SQLException e) {
			throw new DAOException("Cannot load UserRequests by user login", e);
		} finally {
			closeResultSet(resultSet);
			closeStatement(statement);
		}
		return result;
	}

	@Override
	public void handleUserRequestToUnlockCard(UserRequest userRequest, Card card) throws DAOException {
		ProxyConnection connection = PoolConnection.INSTANCE.getConnection();
		CallableStatement statement = null;
		try {
			connection.setAutoCommit(false);
			statement = connection.prepareCall(HANDLE_USER_REQUEST_TO_UNLOCK_CARD);
			statement.setInt(1, userRequest.getId());
			statement.setString(2, card.getNumber());
			statement.executeUpdate();
			connection.commit();
		} catch (SQLException e) {
			rollbackTransaction(connection);
			throw new DAOException("Cannot handle user request to unlock card");
		} finally {
			closeStatement(statement);
			closeConnection(connection);
		}
	}

	@Override
	public void handleUserRequestToOpenAccount(UserRequest userRequest, BankAccount bankAccount) throws DAOException {
		ProxyConnection connection = PoolConnection.INSTANCE.getConnection();
		CallableStatement statement = null;
		try {
			connection.setAutoCommit(false);
			statement = connection.prepareCall(HANDLE_USER_REQUEST_TO_OPEN_ACCOUNT);
			statement.setInt(1, userRequest.getId());
			statement.setString(2, bankAccount.getAccountNumber());
			statement.setString(3, userRequest.getUser().getLogin());
			statement.setString(4, bankAccount.getCurrency().getName());
			statement.executeUpdate();
			connection.commit();
		} catch (SQLException e) {
			rollbackTransaction(connection);
			throw new DAOException("Cannot handle user request to open account");
		} finally {
			closeStatement(statement);
			closeConnection(connection);
		}
	}

	@Override
	public void handleUserRequestToOpenCard(UserRequest userRequest, Card card, BankAccount bankAccount)
			throws DAOException {
		ProxyConnection connection = PoolConnection.INSTANCE.getConnection();
		CallableStatement statement = null;
		try {
			connection.setAutoCommit(false);
			statement = connection.prepareCall(HANDLE_USER_REQUEST_TO_OPEN_CARD);
			statement.setInt(1, userRequest.getId());
			statement.setString(2, card.getNumber());
			statement.setTimestamp(3, new Timestamp(card.getValidUntilDate().getTime()));
			statement.setString(4, card.getNumberMask());
			statement.setString(5, card.getCcv());
			statement.setInt(6, card.getExpirationDate().getValue());
			statement.setString(7, card.getPaymentSystem().getName());
			statement.setString(8, bankAccount.getAccountNumber());
			statement.setString(9, card.getUser().getLogin());
			statement.setString(10, bankAccount.getCurrency().getName());
			statement.executeUpdate();
			connection.commit();
		} catch (SQLException e) {
			rollbackTransaction(connection);
			throw new DAOException("Cannot handle user request to open card");
		} finally {
			closeStatement(statement);
			closeConnection(connection);
		}
	}

	@Override
	public void handleUserRequestToOpenCardToExistingAccount(UserRequest userRequest, Card card) throws DAOException {
		ProxyConnection connection = PoolConnection.INSTANCE.getConnection();
		CallableStatement statement = null;
		try {
			connection.setAutoCommit(false);
			statement = connection.prepareCall(HANDLE_USER_REQUEST_TO_OPEN_CARD_TO_EXISTING_ACCOUNT);
			statement.setInt(1, userRequest.getId());
			statement.setString(2, card.getNumber());
			statement.setTimestamp(3, new Timestamp(card.getValidUntilDate().getTime()));
			statement.setString(4, card.getNumberMask());
			statement.setString(5, card.getCcv());
			statement.setInt(6, card.getExpirationDate().getValue());
			statement.setString(7, card.getPaymentSystem().getName());
			statement.setString(8, card.getBankAccount().getAccountNumber());
			statement.setString(9, card.getUser().getLogin());
			statement.executeUpdate();
			connection.commit();
		} catch (SQLException e) {
			rollbackTransaction(connection);
			throw new DAOException("Cannot handle user request to open card to existing account");
		} finally {
			closeStatement(statement);
			closeConnection(connection);
		}
	}

}
