package by.training.payment.command.admin.request;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import by.training.payment.command.PageEnum;
import by.training.payment.entity.UserRequest;
import by.training.payment.exception.ServiceException;

public class AdminFindAllUserRequestsCommand extends AbstractRequestCommand {

	private static final Logger LOGGER = Logger.getLogger(AdminFindAllUserRequestsCommand.class);

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		try {
			List<UserRequest> userRequestList = userRequestService.getAllUserRequests();
			setUserRequestsInRequestAttribute(request, userRequestList);
		} catch (ServiceException e) {
			setErrorMessageInRequestAttribute(request, e);
			LOGGER.warn("Cannot load all user requests", e);
		}
		return PageEnum.ADMIN_REQUESTS_MENU.getValue();
	}
}
