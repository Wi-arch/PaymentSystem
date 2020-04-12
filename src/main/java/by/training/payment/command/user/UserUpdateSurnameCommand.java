package by.training.payment.command.user;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import by.training.payment.command.AbstractCommand;
import by.training.payment.command.PageEnum;
import by.training.payment.command.RequestParameter;
import by.training.payment.entity.User;
import by.training.payment.exception.ServiceException;
import by.training.payment.util.ExceptionParser;

public class UserUpdateSurnameCommand extends AbstractCommand {

	private static final Logger LOGGER = Logger.getLogger(UserUpdateSurnameCommand.class);

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		User user = (User) request.getSession().getAttribute(RequestParameter.USER.toString());
		String surname = request.getParameter(RequestParameter.USER_SURNAME.toString());
		try {
			userService.updateUserSurname(user, surname);
			request.setAttribute(RequestParameter.USER.toString(), user);
		} catch (ServiceException e) {
			LOGGER.warn("Cannot update user surname", e);
			request.setAttribute(RequestParameter.ERROR_MESSAGE.toString(), ExceptionParser.getExceptionStatus(e));
		}
		return PageEnum.USER_PERSONAL_DATA_MENU.getValue();
	}
}
