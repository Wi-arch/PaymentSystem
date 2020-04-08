package by.training.payment.dao.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import by.training.payment.dao.PaymentSystemDAO;
import by.training.payment.entity.PaymentSystem;
import by.training.payment.exception.DAOException;
import by.training.payment.pool.PoolConnection;
import by.training.payment.pool.ProxyConnection;

public class MySQLPaymentSystemDAO extends SQLUtil implements PaymentSystemDAO {

	private static final String GET_PAYMENT_SYSTEM_BY_NAME = "SELECT * FROM payment_system WHERE payment_system_name = ?";
	private static final String GET_ALL_PAYMENT_SYSTEMS = "SELECT * FROM payment_system";

	@Override
	public List<PaymentSystem> getAllPaymentSystems() throws DAOException {
		List<PaymentSystem> paymentSystems = new ArrayList<>();
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try (ProxyConnection connection = PoolConnection.INSTANCE.getConnection()) {
			statement = connection.prepareStatement(GET_ALL_PAYMENT_SYSTEMS);
			if (statement != null) {
				resultSet = statement.executeQuery();
				while (resultSet.next()) {
					paymentSystems.add(buildPaymentSystem(resultSet));
				}
			}
		} catch (SQLException e) {
			throw new DAOException("Cannot load payment system list", e);
		} finally {
			closeStatement(statement);
			closeResultSet(resultSet);
		}
		return paymentSystems;
	}

	@Override
	public PaymentSystem getPaymentSystemByName(String name) throws DAOException {
		PaymentSystem paymentSystem = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try (ProxyConnection connection = PoolConnection.INSTANCE.getConnection()) {
			statement = connection.prepareStatement(GET_PAYMENT_SYSTEM_BY_NAME);
			if (statement != null) {
				statement.setString(1, name);
				resultSet = statement.executeQuery();
				if (resultSet.next()) {
					paymentSystem = buildPaymentSystem(resultSet);
				}
			}
		} catch (SQLException e) {
			throw new DAOException("Cannot load payment system", e);
		} finally {
			closeStatement(statement);
			closeResultSet(resultSet);
		}
		return paymentSystem;
	}
}
