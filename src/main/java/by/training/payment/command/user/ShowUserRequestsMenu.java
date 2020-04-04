package by.training.payment.command.user;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import by.training.payment.command.AbstractCommand;
import by.training.payment.command.PageEnum;
import by.training.payment.command.RequestParameter;
import by.training.payment.entity.User;
import by.training.payment.entity.UserRequest;
import by.training.payment.exception.ServiceException;
import by.training.payment.util.ExceptionParser;

public class ShowUserRequestsMenu extends AbstractCommand {

	private static final Logger LOGGER = Logger.getLogger(ShowUserRequestsMenu.class);

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		User user = (User) request.getSession().getAttribute(RequestParameter.USER.toString());
		if (user != null) {
			try {
				List<UserRequest> userRequests = userRequestService.getAllUserRequestsByUserLogin(user.getLogin());
				if (userRequests != null) {
					request.setAttribute(RequestParameter.USER_REQUEST_LIST.toString(), userRequests);
				}
			} catch (ServiceException e) {
				request.setAttribute(RequestParameter.ERROR_MESSAGE.toString(), ExceptionParser.getExceptionStatus(e));
				LOGGER.warn("Cannot load user requests", e);
			}
		}
		return PageEnum.USER_REQUESTS_MENU.getValue();
	}

}
