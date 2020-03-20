package by.training.payment.dao.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import by.training.payment.dao.UserDAO;
import by.training.payment.entity.User;
import by.training.payment.exception.DAOException;
import by.training.payment.pool.PoolConnection;
import by.training.payment.pool.ProxyConnection;

public class MySQLUserDAO extends SQLUtil implements UserDAO {

	private static final String ADD_USER = "INSERT INTO bank_user (bank_user_login, bank_user_password, bank_user_email, user_role_id, bank_user_name, bank_user_surname) VALUES (?, ?, ?, ?, ?, ?);";
	private static final String UPDATE_USER = "UPDATE bank_user SET bank_user_login = ?, bank_user_password = ?, bank_user_email = ?, user_role_id = ?, bank_user_name = ? ,bank_user_surname = ?, bank_user_is_blocked = ? WHERE (bank_user_id = ?);";
	private static final String DELETE_USER = "DELETE FROM bank_user WHERE bank_user_id = ?;";
	private static final String GET_USER_BY_ID = "SELECT * FROM bank_user INNER JOIN user_role ON bank_user.user_role_id = user_role.user_role_id WHERE bank_user_id = ?;";
	private static final String GET_ALL_USERS = "SELECT * FROM bank_user INNER JOIN user_role ON bank_user.user_role_id = user_role.user_role_id;";

	@Override
	public void addUser(User user) throws DAOException {
		PreparedStatement statement = null;
		try (ProxyConnection connection = PoolConnection.INSTANCE.getConnection()) {
			if (connection != null) {
				statement = connection.prepareStatement(ADD_USER);
				if (statement != null) {
					statement.setString(1, user.getLogin());
					statement.setString(2, user.getPassword());
					statement.setString(3, user.getEmail());
					statement.setInt(4, user.getUserRole().getId());
					statement.setString(5, user.getName());
					statement.setString(6, user.getSurName());
					statement.executeUpdate();
				}
			}
		} catch (SQLException e) {
			throw new DAOException("Cannot add user", e);
		} finally {
			closeStatement(statement);
		}
	}

	@Override
	public void updateUser(User user) throws DAOException {
		PreparedStatement statement = null;
		try (ProxyConnection connection = PoolConnection.INSTANCE.getConnection()) {
			if (connection != null) {
				statement = connection.prepareStatement(UPDATE_USER);
				if (statement != null) {
					statement.setString(1, user.getLogin());
					statement.setString(2, user.getPassword());
					statement.setString(3, user.getEmail());
					statement.setInt(4, user.getUserRole().getId());
					statement.setString(5, user.getName());
					statement.setString(6, user.getSurName());
					statement.setBoolean(7, user.isBlocked());
					statement.setInt(8, user.getId());
					statement.executeUpdate();
				}
			}
		} catch (SQLException e) {
			throw new DAOException("Cannot update user", e);
		} finally {
			closeStatement(statement);
		}
	}

	@Override
	public void deleteUser(User user) throws DAOException {
		PreparedStatement statement = null;
		try (ProxyConnection connection = PoolConnection.INSTANCE.getConnection()) {
			if (connection != null) {
				statement = connection.prepareStatement(DELETE_USER);
				if (statement != null) {
					statement.setInt(1, user.getId());
					statement.executeUpdate();
				}
			}
		} catch (SQLException e) {
			throw new DAOException("Cannot delete user", e);
		} finally {
			closeStatement(statement);
		}
	}

	@Override
	public List<User> getAllUsers() throws DAOException {
		List<User> userList = new ArrayList<>();
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try (ProxyConnection connection = PoolConnection.INSTANCE.getConnection()) {
			if (connection != null) {
				statement = connection.prepareStatement(GET_ALL_USERS);
				if (statement != null) {
					resultSet = statement.executeQuery();
					if (resultSet != null) {
						while (resultSet.next()) {
							userList.add(buildUser(resultSet));
						}
					}
				}
			}
		} catch (SQLException e) {
			throw new DAOException("Cannot load user list", e);
		} finally {
			closeStatement(statement);
			closeResultSet(resultSet);
		}
		return userList;
	}

	@Override
	public User getUserById(int id) throws DAOException {
		User user = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try (ProxyConnection connection = PoolConnection.INSTANCE.getConnection()) {
			if (connection != null) {
				statement = connection.prepareStatement(GET_USER_BY_ID);
				if (statement != null) {
					statement.setInt(1, id);
					resultSet = statement.executeQuery();
					if (resultSet != null && resultSet.next()) {
						user = buildUser(resultSet);
					}
				}
			}
		} catch (SQLException e) {
			throw new DAOException("Cannot load user", e);
		} finally {
			closeStatement(statement);
			closeResultSet(resultSet);
		}
		return user;
	}
}
