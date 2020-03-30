package by.training.payment.dao.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import by.training.payment.dao.RequestStatusDAO;
import by.training.payment.entity.RequestStatus;
import by.training.payment.exception.DAOException;
import by.training.payment.pool.PoolConnection;
import by.training.payment.pool.ProxyConnection;

public class MySQLRequestStatusDAO extends SQLUtil implements RequestStatusDAO {

	private static final String GET_REQUEST_STATUS_BY_ID = "SELECT * FROM request_status WHERE request_status_id = ?";
	private static final String GET_REQUEST_STATUS_LIST = "SELECT * FROM request_status";

	@Override
	public RequestStatus getRequestStatusById(int id) throws DAOException {
		RequestStatus requestStatus = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try (ProxyConnection connection = PoolConnection.INSTANCE.getConnection()) {
			if (connection != null) {
				statement = connection.prepareStatement(GET_REQUEST_STATUS_BY_ID);
				if (statement != null) {
					statement.setInt(1, id);
					resultSet = statement.executeQuery();
					if (resultSet.next()) {
						requestStatus = buildRequestStatus(resultSet);
					}
				}
			}
		} catch (SQLException e) {
			throw new DAOException("Cannot load request status", e);
		} finally {
			closeStatement(statement);
			closeResultSet(resultSet);
		}
		return requestStatus;
	}

	@Override
	public List<RequestStatus> getRequestStatusList() throws DAOException {
		List<RequestStatus> requestStatus = new ArrayList<>();
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try (ProxyConnection connection = PoolConnection.INSTANCE.getConnection()) {
			if (connection != null) {
				statement = connection.prepareStatement(GET_REQUEST_STATUS_LIST);
				if (statement != null) {
					resultSet = statement.executeQuery();
					while (resultSet.next()) {
						requestStatus.add(buildRequestStatus(resultSet));
					}
				}
			}
		} catch (SQLException e) {
			throw new DAOException("Cannot load request status list", e);
		} finally {
			closeStatement(statement);
			closeResultSet(resultSet);
		}
		return requestStatus;
	}

}
