package by.training.payment.pool;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Enumeration;
import java.util.Properties;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import com.mysql.cj.jdbc.Driver;
import org.apache.log4j.Logger;

import by.training.payment.exception.DAOException;
import by.training.payment.exception.NoJDBCDriverException;
import by.training.payment.exception.NoJDBCPropertiesFileException;

public enum PoolConnection {

	INSTANCE;

	private static final Logger LOGGER = Logger.getLogger(PoolConnection.class);
	private static final String DATABASE_PROPERTIES_FILE = "database.properties";
	private static final String DATABASE_URL_KEY = "url";
	private static final int MAXIMUM_SECONDS_TIMEOUT = 30;
	private static final int POOL_SIZE = 30;
	private final BlockingQueue<ProxyConnection> availableConnection = new LinkedBlockingQueue<>(POOL_SIZE);
	private final Deque<ProxyConnection> unavailableConnection = new ArrayDeque<>(POOL_SIZE);
	private final AtomicBoolean isCreated = new AtomicBoolean(false);
	private final Properties databaseProperties = new Properties();
	private String url;

	public AtomicBoolean isCreated() {
		return isCreated;
	}

	public void initializePool() throws NoJDBCDriverException, NoJDBCPropertiesFileException {
		if (isCreated.compareAndSet(false, true)) {
			try {
				initializeDatabaseConfigurations();
				registerDriver();
				for (int i = 0; i < POOL_SIZE; i++) {
					availableConnection.add(new ProxyConnection(DriverManager.getConnection(url, databaseProperties)));
				}
			} catch (SQLException e) {
				LOGGER.fatal("Cannot initialize pool", e);
				throw new NoJDBCDriverException("Cannot initialize pool", e);
			}
		}
	}

	public ProxyConnection getConnection() throws DAOException {
		ProxyConnection connection = null;
		try {
			connection = availableConnection.poll(MAXIMUM_SECONDS_TIMEOUT, TimeUnit.SECONDS);
			if (connection == null) {
				throw new DAOException("Connection has been timed out *Status998*");
			}
			unavailableConnection.push(connection);
		} catch (InterruptedException e) {
			LOGGER.warn("Cannot get connection", e);
			Thread.currentThread().interrupt();
		}
		return connection;
	}

	public void returnConnection(ProxyConnection connection) {
		try {
			if (connection != null && !connection.isClosed()) {
				if (!connection.getAutoCommit()) {
					connection.setAutoCommit(true);
				}
				if (connection.getTransactionIsolation() != Connection.TRANSACTION_REPEATABLE_READ) {
					connection.setTransactionIsolation(Connection.TRANSACTION_REPEATABLE_READ);
				}
				unavailableConnection.remove(connection);
				availableConnection.put(connection);
			}
		} catch (SQLException e) {
			LOGGER.warn("Cannot reset autocommit", e);
		} catch (InterruptedException e) {
			LOGGER.warn("Cannot return connection", e);
			Thread.currentThread().interrupt();
		}
	}

	public void closePool() {
		if (isCreated.get()) {
			for (int i = 0; i < POOL_SIZE; i++) {
				closeConnection(availableConnection.poll());
				closeConnection(unavailableConnection.poll());
			}
			deregisterDriver();
		}
	}

	private void closeConnection(ProxyConnection connection) {
		if (connection != null) {
			try {
				connection.closeInPool();
			} catch (SQLException e) {
				LOGGER.error("Cannot close connection", e);
			}
		}
	}

	private void registerDriver() throws NoJDBCDriverException {
		try {
			DriverManager.registerDriver(new Driver());
		} catch (SQLException e) {
			LOGGER.fatal("Cannot register driver", e);
			throw new NoJDBCDriverException("Cannot register driver", e);
		}
	}

	private void deregisterDriver() {
		Enumeration<java.sql.Driver> driverEnumeration = DriverManager.getDrivers();
		if (driverEnumeration != null) {
			while (driverEnumeration.hasMoreElements()) {
				try {
					DriverManager.deregisterDriver(driverEnumeration.nextElement());
				} catch (SQLException e) {
					LOGGER.error("Cannot deregister driver", e);
				}
			}
		}
	}

	private void initializeDatabaseConfigurations() throws NoJDBCPropertiesFileException {
		InputStream inputStream = getClass().getClassLoader().getResourceAsStream(DATABASE_PROPERTIES_FILE);
		if (inputStream == null) {
			LOGGER.fatal("Cannot found database properties");
			throw new NoJDBCPropertiesFileException("Cannot found database properties");
		}
		try {
			databaseProperties.load(inputStream);
			url = databaseProperties.getProperty(DATABASE_URL_KEY);
		} catch (IOException e) {
			LOGGER.fatal("Cannot load database properties", e);
			throw new NoJDBCPropertiesFileException("Cannot load database properties", e);
		}
	}
}
