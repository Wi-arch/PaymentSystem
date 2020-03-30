package by.training.payment.dao.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import by.training.payment.dao.RequestParameterDAO;
import by.training.payment.entity.RequestParameter;
import by.training.payment.exception.DAOException;
import by.training.payment.pool.PoolConnection;
import by.training.payment.pool.ProxyConnection;

public class MySQLRequestParameterDAO extends SQLUtil implements RequestParameterDAO {

	private static final String ADD_REQUEST_PARAMETER = "INSERT INTO request_parameter (parameter_header_id, user_request_id, value) VALUES (?,?,?);";
	private static final String UPDATE_REQUEST_PARAMETER = "UPDATE request_parameter SET parameter_header_id=?, user_request_id=?, value=? WHERE request_parameter_id = ?;";
	private static final String DELETE_REQUEST_PARAMETER = "DELETE FROM request_parameter WHERE request_parameter_id = ?;";
	private static final String GET_REQUEST_PARAMETER_BY_ID = "SELECT * FROM request_parameter INNER JOIN parameter_header "
			+ "ON request_parameter.parameter_header_id = parameter_header.parameter_header_id "
			+ "INNER JOIN user_request ON user_request.user_request_id = request_parameter.user_request_id "
			+ "INNER JOIN request_type ON request_type.request_type_id = user_request.request_type_id "
			+ "INNER JOIN request_status ON request_status.request_status_id = user_request.request_status_id "
			+ "INNER JOIN bank_user ON bank_user.bank_user_id = user_request.bank_user_id "
			+ "INNER JOIN user_role ON user_role.user_role_id = bank_user.user_role_id "
			+ "WHERE request_parameter.request_parameter_id = ?;";

	private static final String GET_ALL_REQUEST_PARAMETERS = "SELECT * FROM request_parameter INNER JOIN parameter_header "
			+ "ON request_parameter.parameter_header_id = parameter_header.parameter_header_id "
			+ "INNER JOIN user_request ON user_request.user_request_id = request_parameter.user_request_id "
			+ "INNER JOIN request_type ON request_type.request_type_id = user_request.request_type_id "
			+ "INNER JOIN request_status ON request_status.request_status_id = user_request.request_status_id "
			+ "INNER JOIN bank_user ON bank_user.bank_user_id = user_request.bank_user_id "
			+ "INNER JOIN user_role ON user_role.user_role_id = bank_user.user_role_id;";

	private static final String GET_ALL_REQUEST_PARAMETERS_BY_USER_REQUEST_ID = "SELECT * FROM request_parameter INNER JOIN parameter_header "
			+ "ON request_parameter.parameter_header_id = parameter_header.parameter_header_id "
			+ "INNER JOIN user_request ON user_request.user_request_id = request_parameter.user_request_id "
			+ "INNER JOIN request_type ON request_type.request_type_id = user_request.request_type_id "
			+ "INNER JOIN request_status ON request_status.request_status_id = user_request.request_status_id "
			+ "INNER JOIN bank_user ON bank_user.bank_user_id = user_request.bank_user_id "
			+ "INNER JOIN user_role ON user_role.user_role_id = bank_user.user_role_id "
			+ "WHERE request_parameter.user_request_id = ?;";

	@Override
	public void addRequestParameter(RequestParameter requestParameter) throws DAOException {
		PreparedStatement statement = null;
		try (ProxyConnection connection = PoolConnection.INSTANCE.getConnection()) {
			if (connection != null) {
				statement = connection.prepareStatement(ADD_REQUEST_PARAMETER);
				if (statement != null) {
					statement.setInt(1, requestParameter.getParameterHeader().getId());
					statement.setInt(2, requestParameter.getUserRequest().getId());
					statement.setString(3, requestParameter.getValue());
					statement.executeUpdate();
				}
			}
		} catch (SQLException e) {
			throw new DAOException("Cannot add request parameter", e);
		} finally {
			closeStatement(statement);
		}
	}

	@Override
	public void updateRequestParameter(RequestParameter requestParameter) throws DAOException {
		PreparedStatement statement = null;
		try (ProxyConnection connection = PoolConnection.INSTANCE.getConnection()) {
			if (connection != null) {
				statement = connection.prepareStatement(UPDATE_REQUEST_PARAMETER);
				if (statement != null) {
					statement.setInt(1, requestParameter.getParameterHeader().getId());
					statement.setInt(2, requestParameter.getUserRequest().getId());
					statement.setString(3, requestParameter.getValue());
					statement.setInt(4, requestParameter.getId());
					statement.executeUpdate();
				}
			}
		} catch (SQLException e) {
			throw new DAOException("Cannot update request parameter", e);
		} finally {
			closeStatement(statement);
		}
	}

	@Override
	public void deleteRequestParameter(RequestParameter requestParameter) throws DAOException {
		PreparedStatement statement = null;
		try (ProxyConnection connection = PoolConnection.INSTANCE.getConnection()) {
			if (connection != null) {
				statement = connection.prepareStatement(DELETE_REQUEST_PARAMETER);
				if (statement != null) {
					statement.setInt(1, requestParameter.getId());
					statement.executeUpdate();
				}
			}
		} catch (SQLException e) {
			throw new DAOException("Cannot delete request parameter", e);
		} finally {
			closeStatement(statement);
		}
	}

	@Override
	public RequestParameter getRequestParameterById(int id) throws DAOException {
		RequestParameter requestParameter = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try (ProxyConnection connection = PoolConnection.INSTANCE.getConnection()) {
			if (connection != null) {
				statement = connection.prepareStatement(GET_REQUEST_PARAMETER_BY_ID);
				if (statement != null) {
					statement.setInt(1, id);
					resultSet = statement.executeQuery();
					if (resultSet.next()) {
						requestParameter = buildRequestParameter(resultSet);
					}
				}
			}
		} catch (SQLException e) {
			throw new DAOException("Cannot load request parameter", e);
		} finally {
			closeStatement(statement);
			closeResultSet(resultSet);
		}
		return requestParameter;
	}

	@Override
	public List<RequestParameter> getAllRequestParameters() throws DAOException {
		List<RequestParameter> requestParameters = new ArrayList<>();
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try (ProxyConnection connection = PoolConnection.INSTANCE.getConnection()) {
			if (connection != null) {
				statement = connection.prepareStatement(GET_ALL_REQUEST_PARAMETERS);
				if (statement != null) {
					resultSet = statement.executeQuery();
					while (resultSet.next()) {
						requestParameters.add(buildRequestParameter(resultSet));
					}
				}
			}
		} catch (SQLException e) {
			throw new DAOException("Cannot load request parameter list", e);
		} finally {
			closeStatement(statement);
			closeResultSet(resultSet);
		}
		return requestParameters;
	}

	@Override
	public List<RequestParameter> getAllRequestParametersByUserRequestId(int userRequestId) throws DAOException {
		List<RequestParameter> requestParameters = new ArrayList<>();
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try (ProxyConnection connection = PoolConnection.INSTANCE.getConnection()) {
			if (connection != null) {
				statement = connection.prepareStatement(GET_ALL_REQUEST_PARAMETERS_BY_USER_REQUEST_ID);
				if (statement != null) {
					statement.setInt(1, userRequestId);
					resultSet = statement.executeQuery();
					while (resultSet.next()) {
						requestParameters.add(buildRequestParameter(resultSet));
					}
				}
			}
		} catch (SQLException e) {
			throw new DAOException("Cannot load request parameter list", e);
		} finally {
			closeStatement(statement);
			closeResultSet(resultSet);
		}
		return requestParameters;
	}

}
