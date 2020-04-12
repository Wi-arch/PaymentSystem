package by.training.payment.command.admin.user;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import by.training.payment.command.AbstractCommand;
import by.training.payment.command.PageEnum;
import by.training.payment.command.RequestParameter;
import by.training.payment.entity.User;
import by.training.payment.exception.ServiceException;
import by.training.payment.util.ExceptionParser;

public class AdminShowAllUsersCommand extends AbstractCommand {

	private static final Logger LOGGER = Logger.getLogger(AdminShowAllUsersCommand.class);
	private static final String USERS_NOT_FOUND = "label.usersNotFound";

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		try {
			List<User> userList = userService.getAllUsers();
			if (userList == null || userList.isEmpty()) {
				request.setAttribute(RequestParameter.RESULT_MESSAGE.toString(), USERS_NOT_FOUND);
			}
			request.setAttribute(RequestParameter.USER_LIST.toString(), userList);
		} catch (ServiceException e) {
			request.setAttribute(RequestParameter.ERROR_MESSAGE.toString(), ExceptionParser.getExceptionStatus(e));
			LOGGER.warn("Cannot load user list", e);
		}
		return PageEnum.ADMIN_USERS_MENU.getValue();
	}

}
