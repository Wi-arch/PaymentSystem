package by.training.payment.command.user.redirect;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import by.training.payment.command.AbstractCommand;
import by.training.payment.command.PageEnum;
import by.training.payment.command.RequestParameter;
import by.training.payment.entity.User;
import by.training.payment.exception.ServiceException;
import by.training.payment.util.ExceptionParser;

public class ShowUserDataMenuCommand extends AbstractCommand {

	private static final Logger LOGGER = Logger.getLogger(ShowUserDataMenuCommand.class);

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		User user = (User) request.getSession().getAttribute(RequestParameter.USER.toString());
		if (user != null) {
			try {
				user = userService.getUserByLogin(user.getLogin());
				request.getSession().setAttribute(RequestParameter.USER.toString(), user);
			} catch (ServiceException e) {
				request.setAttribute(RequestParameter.ERROR_MESSAGE.toString(), ExceptionParser.getExceptionStatus(e));
				LOGGER.warn("Cannot load current user information", e);
			}
		}
		return PageEnum.USER_PERSONAL_DATA_MENU.getValue();
	}
}
