package by.training.payment.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import by.training.payment.entity.User;
import by.training.payment.exception.ServiceException;

public class RestoreUserPasswordCommand extends AbstractCommand {

	private static final Logger LOGGER = Logger.getLogger(RestoreUserPasswordCommand.class);
	private static final String SUCCESSFULLY_RESET_PASSWORD_STATUS = "*Status1017*";

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		User user = buildUser(request);
		try {
			userService.restoreUserPassword(user);
			request.setAttribute(RequestParameter.RESULT_MESSAGE.toString(), SUCCESSFULLY_RESET_PASSWORD_STATUS);
		} catch (ServiceException e) {
			setErrorMessageInRequestAttribute(request, e);
			LOGGER.warn("Cannot reset user password", e);
		}
		return PageEnum.RESTORE_PASSWORD_PAGE.getValue();
	}
}
