package by.training.payment.command.admin.request;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import by.training.payment.command.AbstractCommand;
import by.training.payment.command.RequestParameter;
import by.training.payment.entity.UserRequest;

public abstract class AbstractRequestCommand extends AbstractCommand {

	private static final String REQUESTS_NOT_FOUND = "label.requestsNotFound";

	protected void setUserRequestsInRequestAttribute(HttpServletRequest request, List<UserRequest> userRequestList) {
		if (userRequestList == null || userRequestList.isEmpty()) {
			request.setAttribute(RequestParameter.RESULT_MESSAGE.toString(), REQUESTS_NOT_FOUND);
		}
		request.setAttribute(RequestParameter.USER_REQUEST_LIST.toString(), userRequestList);
	}
}
