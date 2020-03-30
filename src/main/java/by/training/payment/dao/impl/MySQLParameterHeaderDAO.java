package by.training.payment.dao.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import by.training.payment.dao.ParameterHeaderDAO;
import by.training.payment.entity.ParameterHeader;
import by.training.payment.exception.DAOException;
import by.training.payment.pool.PoolConnection;
import by.training.payment.pool.ProxyConnection;

public class MySQLParameterHeaderDAO extends SQLUtil implements ParameterHeaderDAO {

	private static final String GET_PARAMETER_HEADER_BY_ID = "SELECT * FROM parameter_header WHERE parameter_header_id = ?";
	private static final String GET_ALL_PARAMETER_HEADERS = "SELECT * FROM parameter_header";

	@Override
	public ParameterHeader getParameterHeaderById(int id) throws DAOException {
		ParameterHeader parameterHeader = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try (ProxyConnection connection = PoolConnection.INSTANCE.getConnection()) {
			if (connection != null) {
				statement = connection.prepareStatement(GET_PARAMETER_HEADER_BY_ID);
				if (statement != null) {
					statement.setInt(1, id);
					resultSet = statement.executeQuery();
					if (resultSet.next()) {
						parameterHeader = buildParameterHeader(resultSet);
					}
				}
			}
		} catch (SQLException e) {
			throw new DAOException("Cannot load parameter header", e);
		} finally {
			closeStatement(statement);
			closeResultSet(resultSet);
		}
		return parameterHeader;
	}

	@Override
	public List<ParameterHeader> getAllParameterHeaders() throws DAOException {
		List<ParameterHeader> parameterHeaders = new ArrayList<>();
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try (ProxyConnection connection = PoolConnection.INSTANCE.getConnection()) {
			if (connection != null) {
				statement = connection.prepareStatement(GET_ALL_PARAMETER_HEADERS);
				if (statement != null) {
					resultSet = statement.executeQuery();
					while (resultSet.next()) {
						parameterHeaders.add(buildParameterHeader(resultSet));
					}
				}
			}
		} catch (SQLException e) {
			throw new DAOException("Cannot load parameter header list", e);
		} finally {
			closeStatement(statement);
			closeResultSet(resultSet);
		}
		return parameterHeaders;
	}
}
