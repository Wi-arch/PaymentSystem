package by.training.payment.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import by.training.payment.entity.User;
import by.training.payment.exception.ServiceException;
import by.training.payment.util.ExceptionParser;

public class UserAuthorization extends AbstractCommand {

	private static final Logger LOGGER = Logger.getLogger(UserAuthorization.class);
	private static final String WRONG_LOGIN_OR_PASSWORD_STATUS = "*Status1008*";

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		String page = PageEnum.LOGIN_PAGE.getValue();
		try {
			User existingUser = userService.login(buildUser(request));
			if (existingUser != null) {
				page = defineUserPage(existingUser);
				request.getSession().setAttribute(RequestParameter.USER.toString(), existingUser);
			} else {
				request.setAttribute(RequestParameter.ERROR_MESSAGE.toString(), WRONG_LOGIN_OR_PASSWORD_STATUS);
			}
		} catch (ServiceException e) {
			LOGGER.warn("User login exception", e);
			request.setAttribute(RequestParameter.ERROR_MESSAGE.toString(), ExceptionParser.getExceptionStatus(e));
		}
		return page;
	}
}
