package by.training.payment.command.admin.user;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import by.training.payment.command.AbstractCommand;
import by.training.payment.command.PageEnum;
import by.training.payment.command.RequestParameter;
import by.training.payment.entity.User;
import by.training.payment.exception.ServiceException;
import by.training.payment.util.ExceptionParser;

public class FindUserByLogin extends AbstractCommand {

	private static final Logger LOGGER = Logger.getLogger(FindUserByLogin.class);
	private static final String USER_NOT_FOUND = "label.userNotFound";

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		String userLogin = request.getParameter(RequestParameter.USER_LOGIN.toString());

		try {
			User user = userService.getUserByLogin(userLogin);
			if (user == null) {
				request.setAttribute(RequestParameter.RESULT_MESSAGE.toString(), USER_NOT_FOUND);
			}
			request.setAttribute(RequestParameter.USER_BY_LOGIN.toString(), user);
		} catch (ServiceException e) {
			request.setAttribute(RequestParameter.ERROR_MESSAGE.toString(), ExceptionParser.getExceptionStatus(e));
			LOGGER.warn("Cannot load user by login", e);
		}
		return PageEnum.ADMIN_USERS_MENU.getValue();
	}

}
