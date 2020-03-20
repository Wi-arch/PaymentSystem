package by.training.payment.dao.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import by.training.payment.dao.UserRoleDAO;
import by.training.payment.entity.UserRole;
import by.training.payment.exception.DAOException;
import by.training.payment.pool.PoolConnection;
import by.training.payment.pool.ProxyConnection;

public class MySQLUserRoleDAO extends SQLUtil implements UserRoleDAO {

	private static final String GET_ALL_USER_ROLES = "SELECT * FROM user_role";
	private static final String GET_USER_ROLE_BY_ID = "SELECT * FROM user_role WHERE user_role_id = ?";

	@Override
	public UserRole getUserRoleById(int id) throws DAOException {
		UserRole userRole = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try (ProxyConnection connection = PoolConnection.INSTANCE.getConnection()) {
			if (connection != null) {
				statement = connection.prepareStatement(GET_USER_ROLE_BY_ID);
				if (statement != null) {
					statement.setInt(1, id);
					resultSet = statement.executeQuery();
					if (resultSet != null && resultSet.next()) {
						userRole = buildUserRole(resultSet);
					}
				}
			}
		} catch (SQLException e) {
			throw new DAOException("Cannot load user role", e);
		} finally {
			closeStatement(statement);
			closeResultSet(resultSet);
		}
		return userRole;
	}

	@Override
	public List<UserRole> getAllUserRoles() throws DAOException {
		List<UserRole> userRoleList = new ArrayList<>();
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try (ProxyConnection connection = PoolConnection.INSTANCE.getConnection()) {
			if (connection != null) {
				statement = connection.prepareStatement(GET_ALL_USER_ROLES);
				if (statement != null) {
					resultSet = statement.executeQuery();
					if (resultSet != null) {
						while (resultSet.next()) {
							userRoleList.add(buildUserRole(resultSet));
						}
					}
				}
			}
		} catch (SQLException e) {
			throw new DAOException("Cannot load user role list", e);
		} finally {
			closeStatement(statement);
			closeResultSet(resultSet);
		}
		return userRoleList;
	}

}