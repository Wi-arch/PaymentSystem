package by.training.payment.command.admin.request;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;

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

	protected UserRequest getUserRequestFromHttpRequest(HttpServletRequest request) {
		UserRequest userRequest = null;
		String userRequestId = request.getParameter(RequestParameter.USER_REQUEST_ID.toString());
		if (StringUtils.isNumeric(userRequestId)) {
			userRequest = new UserRequest(Integer.valueOf(userRequestId));
		}
		return userRequest;
	}
}
