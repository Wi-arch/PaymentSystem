package by.training.payment.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import by.training.payment.exception.ServiceException;

public class UserRegistrationCommand extends AbstractCommand {

	private static final Logger LOGGER = Logger.getLogger(UserRegistrationCommand.class);
	private static final String SUCCESSFULLY_REGISTRATION_STATUS = "*Status1009*";

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		String confirmPassword = request.getParameter(RequestParameter.USER_PASSWORD_CONFIRM.toString());
		try {
			userService.registerUser(buildUser(request), confirmPassword);
			request.setAttribute(RequestParameter.RESULT_MESSAGE.toString(), SUCCESSFULLY_REGISTRATION_STATUS);
		} catch (ServiceException e) {
			setErrorMessageInRequestAttribute(request, e);
			LOGGER.warn("Registration exception", e);
		}
		return PageEnum.REGISTRATION_PAGE.getValue();
	}
}
