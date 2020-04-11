package by.training.payment.command.user.request;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import by.training.payment.command.RequestParameter;
import by.training.payment.command.user.account.AbstractBankAccountCommand;
import by.training.payment.entity.RequestType;
import by.training.payment.entity.User;
import by.training.payment.entity.UserRequest;
import by.training.payment.exception.ServiceException;
import by.training.payment.factory.RequestTypeFactory;
import by.training.payment.util.ExceptionParser;

public abstract class AbstractCreateRequestCommand extends AbstractBankAccountCommand {

	private static final Logger LOGGER = Logger.getLogger(AbstractCreateRequestCommand.class);
	protected static final String SUCCESSFULLY_CREATED_REQUEST_STATUS = "*Status1018*";
	protected final RequestTypeFactory requestTypeFactory = RequestTypeFactory.getInstance();

	protected void setUserRequestsInRequestAttribute(HttpServletRequest request) {
		User user = (User) request.getSession().getAttribute(RequestParameter.USER.toString());
		if (user != null) {
			try {
				List<UserRequest> userRequests = userRequestService.getAllUserRequestsByUserLogin(user.getLogin());
				request.setAttribute(RequestParameter.USER_REQUEST_LIST.toString(), userRequests);
			} catch (ServiceException e) {
				request.setAttribute(RequestParameter.ERROR_MESSAGE.toString(), ExceptionParser.getExceptionStatus(e));
				LOGGER.warn("Cannot load user requests", e);
			}
		}
	}

	protected UserRequest createUserRequest(HttpServletRequest request, RequestType requestType) {
		User user = (User) request.getSession().getAttribute(RequestParameter.USER.toString());
		return new UserRequest(user, requestType);
	}
}
