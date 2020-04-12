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

public class UserUpdatePasswordCommand extends AbstractCommand {

	private static final Logger LOGGER = Logger.getLogger(UserUpdatePasswordCommand.class);
	private static final String SUCCESSFULLY_UPDATE_PASSWORD_STATUS = "*Status1014*";

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		User user = (User) request.getSession().getAttribute(RequestParameter.USER.toString());
		String oldPassword = request.getParameter(RequestParameter.OLD_PASSWORD.toString());
		String newPassword = request.getParameter(RequestParameter.NEW_PASSWORD.toString());
		String confirmPassword = request.getParameter(RequestParameter.CONFIRM_NEW_PASSWORD.toString());
		try {
			userService.updateUserPassword(user, oldPassword, newPassword, confirmPassword);
			request.setAttribute(RequestParameter.RESULT_MESSAGE.toString(), SUCCESSFULLY_UPDATE_PASSWORD_STATUS);
			request.getSession().setAttribute(RequestParameter.USER.toString(), user);
		} catch (ServiceException e) {
			request.setAttribute(RequestParameter.ERROR_MESSAGE.toString(), ExceptionParser.getExceptionStatus(e));
			LOGGER.warn("Cannot update user password", e);
		}
		return PageEnum.USER_PERSONAL_DATA_MENU.getValue();
	}
}
