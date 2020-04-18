package by.training.payment.dao.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import by.training.payment.dao.RequestTypeDAO;
import by.training.payment.entity.RequestType;
import by.training.payment.exception.DAOException;
import by.training.payment.pool.PoolConnection;
import by.training.payment.pool.ProxyConnection;

public class RequestTypeDAOImpl extends SQLUtil implements RequestTypeDAO {

	private static final String GET_REQUEST_TYPE_BY_VALUE = "SELECT * FROM request_type WHERE request_type_value = ?";
	private static final String GET_ALL_REQUEST_TYPES = "SELECT * FROM request_type";

	@Override
	public List<RequestType> getAllRequestTypes() throws DAOException {
		List<RequestType> requestTypes = new ArrayList<>();
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try (ProxyConnection connection = PoolConnection.INSTANCE.getConnection()) {
			statement = connection.prepareStatement(GET_ALL_REQUEST_TYPES);
			if (statement != null) {
				resultSet = statement.executeQuery();
				while (resultSet.next()) {
					requestTypes.add(buildRequestType(resultSet));
				}
			}
		} catch (SQLException e) {
			throw new DAOException("Cannot load request type list", e);
		} finally {
			closeStatement(statement);
			closeResultSet(resultSet);
		}
		return requestTypes;
	}

	@Override
	public RequestType getRequestTypeByValue(String value) throws DAOException {
		RequestType requestType = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try (ProxyConnection connection = PoolConnection.INSTANCE.getConnection()) {
			statement = connection.prepareStatement(GET_REQUEST_TYPE_BY_VALUE);
			if (statement != null) {
				statement.setString(1, value);
				resultSet = statement.executeQuery();
				if (resultSet.next()) {
					requestType = buildRequestType(resultSet);
				}
			}
		} catch (SQLException e) {
			throw new DAOException("Cannot load request type", e);
		} finally {
			closeStatement(statement);
			closeResultSet(resultSet);
		}
		return requestType;
	}
}
