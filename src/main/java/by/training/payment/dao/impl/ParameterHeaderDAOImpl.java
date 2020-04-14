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

public class ParameterHeaderDAOImpl extends SQLUtil implements ParameterHeaderDAO {

	private static final String GET_PARAMETER_HEADER_BY_NAME = "SELECT * FROM parameter_header WHERE parameter_header_name = ?";
	private static final String GET_ALL_PARAMETER_HEADERS = "SELECT * FROM parameter_header";

	@Override
	public List<ParameterHeader> getAllParameterHeaders() throws DAOException {
		List<ParameterHeader> parameterHeaders = new ArrayList<>();
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try (ProxyConnection connection = PoolConnection.INSTANCE.getConnection()) {
			statement = connection.prepareStatement(GET_ALL_PARAMETER_HEADERS);
			if (statement != null) {
				resultSet = statement.executeQuery();
				while (resultSet.next()) {
					parameterHeaders.add(buildParameterHeader(resultSet));
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

	@Override
	public ParameterHeader getParameterHeaderByValue(String value) throws DAOException {
		ParameterHeader parameterHeader = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try (ProxyConnection connection = PoolConnection.INSTANCE.getConnection()) {
			statement = connection.prepareStatement(GET_PARAMETER_HEADER_BY_NAME);
			if (statement != null) {
				statement.setString(1, value);
				resultSet = statement.executeQuery();
				if (resultSet.next()) {
					parameterHeader = buildParameterHeader(resultSet);
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
}
