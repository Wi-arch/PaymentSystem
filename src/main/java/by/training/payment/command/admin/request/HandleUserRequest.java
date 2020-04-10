package by.training.payment.command.admin.request;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import by.training.payment.command.PageEnum;
import by.training.payment.command.RequestParameter;
import by.training.payment.entity.UserRequest;
import by.training.payment.exception.ServiceException;
import by.training.payment.util.ExceptionParser;

public class HandleUserRequest extends AbstractRequestCommand {

	private static final Logger LOGGER = Logger.getLogger(HandleUserRequest.class);

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		UserRequest userRequest = getUserRequestFromHttpRequest(request);
		try {
			userRequestService.handleUserRequest(userRequest);
			request.setAttribute(RequestParameter.RESULT_MESSAGE.toString(), SUCCESSFULLY_COMPLETED_OPERATION_STATUS);
		} catch (ServiceException e) {
			request.setAttribute(RequestParameter.ERROR_MESSAGE.toString(), ExceptionParser.getExceptionStatus(e));
			LOGGER.warn("Cannot handle user request", e);
		}
		return PageEnum.ADMIN_REQUESTS_MENU.getValue();
	}
}
