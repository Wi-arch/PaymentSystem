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

	private static final String ADD_USER = "INSERT INTO bank_user (bank_user_login, bank_user_password, bank_user_email, user_role_value, bank_user_name, bank_user_surname) VALUES (?, ?, ?, ?, ?, ?);";
	private static final String UPDATE_USER = "UPDATE bank_user SET bank_user_password = ?, bank_user_email = ?, user_role_value = ?, bank_user_name = ? ,bank_user_surname = ?, bank_user_is_blocked = ? WHERE bank_user_login = ?;";
	private static final String DELETE_USER = "DELETE FROM bank_user WHERE bank_user_login = ?;";
	private static final String GET_ALL_USERS = "SELECT * FROM bank_user INNER JOIN user_role ON bank_user.user_role_value = user_role.user_role_value;";
	private static final String GET_USER_BY_LOGIN = "SELECT * FROM bank_user INNER JOIN user_role ON bank_user.user_role_value = user_role.user_role_value WHERE bank_user_login = ?;";
	private static final String GET_USER_BY_EMAIL = "SELECT * FROM bank_user INNER JOIN user_role ON bank_user.user_role_value = user_role.user_role_value WHERE bank_user_email = ?;";
	private static final String GET_USER_BY_LOGIN_AND_PASSWORD = "SELECT * FROM bank_user INNER JOIN user_role ON bank_user.user_role_value = user_role.user_role_value WHERE bank_user_login = ? AND bank_user_password = ?;";
	private static final String GET_USER_BY_LOGIN_AND_EMAIL = "SELECT * FROM bank_user INNER JOIN user_role ON bank_user.user_role_value = user_role.user_role_value WHERE bank_user_login = ? AND bank_user_email = ?;";

	@Override
	public void addUser(User user) throws DAOException {
		PreparedStatement statement = null;
		try (ProxyConnection connection = PoolConnection.INSTANCE.getConnection()) {
			statement = connection.prepareStatement(ADD_USER);
			if (statement != null) {
				statement.setString(1, user.getLogin());
				statement.setString(2, user.getPassword());
				statement.setString(3, user.getEmail());
				statement.setString(4, user.getUserRole().getRole());
				statement.setString(5, user.getName());
				statement.setString(6, user.getSurname());
				statement.executeUpdate();
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
			statement = connection.prepareStatement(UPDATE_USER);
			if (statement != null) {
				statement.setString(1, user.getPassword());
				statement.setString(2, user.getEmail());
				statement.setString(3, user.getUserRole().getRole());
				statement.setString(4, user.getName());
				statement.setString(5, user.getSurname());
				statement.setBoolean(6, user.getIsBlocked());
				statement.setString(7, user.getLogin());
				statement.executeUpdate();
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
			statement = connection.prepareStatement(DELETE_USER);
			if (statement != null) {
				statement.setString(1, user.getLogin());
				statement.executeUpdate();
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
			statement = connection.prepareStatement(GET_ALL_USERS);
			if (statement != null) {
				resultSet = statement.executeQuery();
				while (resultSet.next()) {
					userList.add(buildUser(resultSet));
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
	public User getUserByLogin(String login) throws DAOException {
		User user = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try (ProxyConnection connection = PoolConnection.INSTANCE.getConnection()) {
			statement = connection.prepareStatement(GET_USER_BY_LOGIN);
			if (statement != null) {
				statement.setString(1, login);
				resultSet = statement.executeQuery();
				if (resultSet.next()) {
					user = buildUser(resultSet);
				}
			}
		} catch (SQLException e) {
			throw new DAOException("Cannot load user by login", e);
		} finally {
			closeStatement(statement);
			closeResultSet(resultSet);
		}
		return user;
	}

	@Override
	public User getUserByEmail(String email) throws DAOException {
		User user = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try (ProxyConnection connection = PoolConnection.INSTANCE.getConnection()) {
			statement = connection.prepareStatement(GET_USER_BY_EMAIL);
			if (statement != null) {
				statement.setString(1, email);
				resultSet = statement.executeQuery();
				if (resultSet.next()) {
					user = buildUser(resultSet);
				}
			}
		} catch (SQLException e) {
			throw new DAOException("Cannot load user by email", e);
		} finally {
			closeStatement(statement);
			closeResultSet(resultSet);
		}
		return user;
	}

	@Override
	public User getUserByLoginAndPassword(String login, String password) throws DAOException {
		User user = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try (ProxyConnection connection = PoolConnection.INSTANCE.getConnection()) {
			statement = connection.prepareStatement(GET_USER_BY_LOGIN_AND_PASSWORD);
			if (statement != null) {
				statement.setString(1, login);
				statement.setString(2, password);
				resultSet = statement.executeQuery();
				if (resultSet.next()) {
					user = buildUser(resultSet);
				}
			}
		} catch (SQLException e) {
			throw new DAOException("Cannot load user by login and password", e);
		} finally {
			closeStatement(statement);
			closeResultSet(resultSet);
		}
		return user;
	}

	@Override
	public User getUserByLoginAndEmail(String login, String email) throws DAOException {
		User user = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try (ProxyConnection connection = PoolConnection.INSTANCE.getConnection()) {
			statement = connection.prepareStatement(GET_USER_BY_LOGIN_AND_EMAIL);
			if (statement != null) {
				statement.setString(1, login);
				statement.setString(2, email);
				resultSet = statement.executeQuery();
				if (resultSet.next()) {
					user = buildUser(resultSet);
				}
			}
		} catch (SQLException e) {
			throw new DAOException("Cannot load user by login and email", e);
		} finally {
			closeStatement(statement);
			closeResultSet(resultSet);
		}
		return user;
	}
}
