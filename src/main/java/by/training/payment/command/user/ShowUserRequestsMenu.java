package by.training.payment.command.user;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import by.training.payment.command.AbstractUserCommand;
import by.training.payment.command.PageEnum;
import by.training.payment.command.RequestParameter;
import by.training.payment.entity.User;
import by.training.payment.entity.UserRequest;
import by.training.payment.exception.ServiceException;
import by.training.payment.factory.ServiceFactory;
import by.training.payment.service.UserRequestService;
import by.training.payment.util.ExceptionParser;

public class ShowUserRequestsMenu extends AbstractUserCommand {

	private static final Logger LOGGER = Logger.getLogger(ShowUserRequestsMenu.class);
	private final UserRequestService userRequestService = ServiceFactory.getInstance().getUserRequestService();

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		User user = (User) request.getSession().getAttribute(RequestParameter.USER.toString());
		try {
			List<UserRequest> userRequests = userRequestService.getAllUserRequestsByUserId(user.getId());
			if (userRequests != null) {
				request.setAttribute(RequestParameter.USER_REQUEST_LIST.toString(), userRequests);
			}
		} catch (ServiceException e) {
			request.setAttribute(RequestParameter.ERROR_MESSAGE.toString(), ExceptionParser.getExceptionStatus(e));
			LOGGER.warn("Cannot load user requests", e);
		}
		return PageEnum.USER_REQUESTS_MENU.getValue();
	}

}
