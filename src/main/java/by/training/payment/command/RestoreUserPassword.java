package by.training.payment.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import by.training.payment.entity.User;
import by.training.payment.exception.ServiceException;
import by.training.payment.util.ExceptionParser;

public class RestoreUserPassword extends AbstractUserCommand {

	private static final Logger LOGGER = Logger.getLogger(RestoreUserPassword.class);
	private static final String SUCCESSFULLY_RESET_PASSWORD_STATUS = "*Status1017*";

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		User user = buildUser(request);
		try {
			USER_SERVICE.restoreUserPassword(user);
			request.setAttribute(RequestParameter.RESULT_MESSAGE.toString(), SUCCESSFULLY_RESET_PASSWORD_STATUS);
		} catch (ServiceException e) {
			request.setAttribute(RequestParameter.ERROR_MESSAGE.toString(), ExceptionParser.getExceptionStatus(e));
			LOGGER.warn("Cannot reset user password", e);
		}
		return PageEnum.HOME_PAGE.getValue();
	}
}
