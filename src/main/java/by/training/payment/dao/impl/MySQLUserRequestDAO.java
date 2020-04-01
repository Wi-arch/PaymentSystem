package by.training.payment.dao.impl;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import by.training.payment.dao.UserRequestDAO;
import by.training.payment.entity.RequestParameter;
import by.training.payment.entity.UserRequest;
import by.training.payment.exception.DAOException;
import by.training.payment.pool.PoolConnection;
import by.training.payment.pool.ProxyConnection;

public class MySQLUserRequestDAO extends SQLUtil implements UserRequestDAO {

	private static final Logger LOGGER = Logger.getLogger(MySQLUserRequestDAO.class);
	private static final String ADD_USER_REQUEST = "INSERT INTO user_request (bank_user_login, request_type_value) VALUES (?, ?);";
	private static final String UPDATE_USER_REQUEST = "UPDATE user_request SET bank_user_login=?, request_type_value=?, request_status_value=?, request_creation_date=?, request_handle_date=? WHERE user_request_id=?;";
	private static final String DELETE_USER_REQUEST = "DELETE FROM user_request WHERE user_request_id = ?;";
	private static final String GET_USER_REQUESTS_BY_ID = "SELECT * FROM user_request INNER JOIN bank_user ON user_request.bank_user_login = bank_user.bank_user_login INNER JOIN user_role ON bank_user.user_role_value = user_role.user_role_value INNER JOIN request_type ON request_type.request_type_value = user_request.request_type_value INNER JOIN request_status ON request_status.request_status_value = user_request.request_status_value WHERE user_request.user_request_id = ?;";
	private static final String GET_ALL_USER_REQUESTS = "SELECT * FROM user_request INNER JOIN bank_user ON user_request.bank_user_login = bank_user.bank_user_login INNER JOIN user_role ON bank_user.user_role_value = user_role.user_role_value INNER JOIN request_type ON request_type.request_type_value = user_request.request_type_value INNER JOIN request_status ON request_status.request_status_value = user_request.request_status_value;";
	private static final String SAVE_REQUEST_PARAMETER = "{call PS_SaveRequestParameter(?,?,?)}";
	private static final String GET_ALL_USER_REQUESTS_BY_USER_LOGIN = "SELECT * FROM user_request INNER JOIN bank_user ON user_request.bank_user_login = bank_user.bank_user_login INNER JOIN user_role ON bank_user.user_role_value = user_role.user_role_value INNER JOIN request_type ON request_type.request_type_value = user_request.request_type_value INNER JOIN request_status ON request_status.request_status_value = user_request.request_status_value WHERE bank_user.bank_user_login = ?;";

	@Override
	public void addUserRequest(UserRequest userRequest) throws DAOException {
		PreparedStatement statement = null;
		try (ProxyConnection connection = PoolConnection.INSTANCE.getConnection()) {
			if (connection != null) {
				statement = connection.prepareStatement(ADD_USER_REQUEST);
				if (statement != null) {
					statement.setString(1, userRequest.getUser().getLogin());
					statement.setString(2, userRequest.getRequestType().getValue());
					statement.executeUpdate();
				}
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
			if (connection != null) {
				statement = connection.prepareStatement(UPDATE_USER_REQUEST);
				if (statement != null) {
					statement.setString(1, userRequest.getUser().getLogin());
					statement.setString(2, userRequest.getRequestType().getValue());
					statement.setString(3, userRequest.getRequestStatus().getValue());
					statement.setTimestamp(4, new Timestamp(userRequest.getCreationDate().getTime()));
					statement.setTimestamp(5, userRequest.getHandlingDate() != null ? new Timestamp(userRequest.getHandlingDate().getTime()) : null);
					statement.setInt(6, userRequest.getId());
					statement.executeUpdate();
				}
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
			if (connection != null) {
				statement = connection.prepareStatement(DELETE_USER_REQUEST);
				if (statement != null) {
					statement.setInt(1, userRequest.getId());
					statement.executeUpdate();
				}
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
			if (connection != null) {
				statement = connection.prepareStatement(GET_ALL_USER_REQUESTS);
				if (statement != null) {
					resultSet = statement.executeQuery();
					while (resultSet.next()) {
						result.add(buildUserRequest(resultSet));
					}
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
	public UserRequest getUserRequestById(int id) throws DAOException {
		UserRequest userRequest = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try (ProxyConnection connection = PoolConnection.INSTANCE.getConnection()) {
			if (connection != null) {
				statement = connection.prepareStatement(GET_USER_REQUESTS_BY_ID);
				if (statement != null) {
					statement.setInt(1, id);
					resultSet = statement.executeQuery();
					if (resultSet.next()) {
						userRequest = buildUserRequest(resultSet);
					}
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
		try {
			if (connection != null) {
				connection.setAutoCommit(false);
				connection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
				statement = connection.prepareStatement(ADD_USER_REQUEST, Statement.RETURN_GENERATED_KEYS);
				if (statement != null) {
					statement.setString(1, userRequest.getUser().getLogin());
					statement.setString(2, userRequest.getRequestType().getValue());
					statement.executeUpdate();
					resultSet = statement.getGeneratedKeys();
					int currentId = resultSet.next() ? resultSet.getInt(1) : 0;
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
			}
		} catch (SQLException e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				LOGGER.error("Cannot rollback transaction", e1);
			}
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

}
