package by.training.payment.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.training.payment.exception.NoJDBCDriverException;
import by.training.payment.exception.NoJDBCPropertiesFileException;
import by.training.payment.pool.PoolConnection;

@SuppressWarnings("serial")
@WebServlet("/controller")
public class Controller extends HttpServlet {

	@Override
	public void init() throws ServletException {
		super.init();
		try {
			PoolConnection.INSTANCE.initializePool();
		} catch (NoJDBCDriverException | NoJDBCPropertiesFileException e) {
			throw new RuntimeException("Cannot initialize connection pool");
		}
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO
	}

	@Override
	public void destroy() {
		super.destroy();
		PoolConnection.INSTANCE.closePool();
	}

}
