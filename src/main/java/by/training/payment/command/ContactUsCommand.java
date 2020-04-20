package by.training.payment.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import by.training.payment.exception.ServiceException;

public class ContactUsCommand extends AbstractCommand {

	private static final Logger LOGGER = Logger.getLogger(ContactUsCommand.class);
	private static final String SUCCESSFULLY_SENT_MESSAGE_STATUS = "text.messageSentSuccessfully";

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		String name = request.getParameter(RequestParameter.USER_NAME.name());
		String email = request.getParameter(RequestParameter.USER_EMAIL.name());
		String message = request.getParameter(RequestParameter.USER_TEXT.name());
		try {
			userService.handleUserContactUsMessage(name, email, message);
			request.setAttribute(RequestParameter.RESULT_MESSAGE.name(), SUCCESSFULLY_SENT_MESSAGE_STATUS);
		} catch (ServiceException e) {
			setErrorMessageInRequestAttribute(request, e);
			LOGGER.warn("Cannot handle user contact us message", e);
		}
		return PageEnum.CONTACT_US_PAGE.getValue();
	}
}
