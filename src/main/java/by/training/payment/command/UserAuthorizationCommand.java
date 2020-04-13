package by.training.payment.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import by.training.payment.entity.User;
import by.training.payment.exception.ServiceException;
import by.training.payment.util.ExceptionParser;

public class UserAuthorizationCommand extends AbstractCommand {

	private static final Logger LOGGER = Logger.getLogger(UserAuthorizationCommand.class);

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		String page = PageEnum.LOGIN_PAGE.getValue();
		try {
			User existingUser = userService.login(buildUser(request));
			page = defineUserPage(existingUser);
			request.getSession().setAttribute(RequestParameter.USER.toString(), existingUser);
		} catch (ServiceException e) {
			LOGGER.warn("User login exception", e);
			request.setAttribute(RequestParameter.ERROR_MESSAGE.toString(), ExceptionParser.getExceptionStatus(e));
		}
		return page;
	}
}
