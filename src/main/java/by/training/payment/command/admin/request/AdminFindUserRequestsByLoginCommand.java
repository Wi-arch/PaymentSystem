package by.training.payment.command.admin.request;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import by.training.payment.command.PageEnum;
import by.training.payment.command.RequestParameter;
import by.training.payment.entity.UserRequest;
import by.training.payment.exception.ServiceException;
import by.training.payment.util.ExceptionParser;

public class AdminFindUserRequestsByLoginCommand extends AbstractRequestCommand {

	private static final Logger LOGGER = Logger.getLogger(AdminFindUserRequestsByLoginCommand.class);

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		String userLogin = request.getParameter(RequestParameter.USER_LOGIN.toString());

		try {
			List<UserRequest> userRequestList = userRequestService.getAllUserRequestsByUserLogin(userLogin);
			setUserRequestsInRequestAttribute(request, userRequestList);
		} catch (ServiceException e) {
			request.setAttribute(RequestParameter.ERROR_MESSAGE.toString(), ExceptionParser.getExceptionStatus(e));
			LOGGER.warn("Cannot load user request by login", e);
		}
		return PageEnum.ADMIN_REQUESTS_MENU.getValue();
	}

}
