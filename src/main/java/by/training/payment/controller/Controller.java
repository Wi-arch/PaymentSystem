package by.training.payment.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import by.training.payment.command.PageEnum;
import by.training.payment.exception.NoJDBCDriverException;
import by.training.payment.exception.NoJDBCPropertiesFileException;
import by.training.payment.factory.CommandFactory;
import by.training.payment.pool.PoolConnection;

@SuppressWarnings("serial")
@WebServlet("/controller")
public class Controller extends HttpServlet {

	private static final Logger LOGGER = Logger.getLogger(Controller.class);
	private static final CommandFactory COMMAND_FACTORY = new CommandFactory();

	@Override
	public void init() throws ServletException {
		super.init();
		try {
			PoolConnection.INSTANCE.initializePool();
		} catch (NoJDBCDriverException | NoJDBCPropertiesFileException e) {
			LOGGER.fatal("Cannot initialize connection pool", e);
		}
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.getRequestDispatcher(PageEnum.HOME_PAGE.getValue()).forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String path = COMMAND_FACTORY.defineCommand(request).execute(request, response);
		request.getRequestDispatcher(path).forward(request, response);
	}

	@Override
	public void destroy() {
		super.destroy();
		PoolConnection.INSTANCE.closePool();
	}
}
