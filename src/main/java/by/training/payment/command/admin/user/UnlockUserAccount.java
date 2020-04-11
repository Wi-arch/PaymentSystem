package by.training.payment.command.admin.user;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import by.training.payment.command.AbstractCommand;
import by.training.payment.command.PageEnum;
import by.training.payment.command.RequestParameter;
import by.training.payment.entity.User;
import by.training.payment.exception.ServiceException;
import by.training.payment.util.ExceptionParser;

public class UnlockUserAccount extends AbstractCommand {

	private static final Logger LOGGER = Logger.getLogger(UnlockUserAccount.class);

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		User user = buildUser(request);
		try {
			userService.unlockUser(user);
			request.setAttribute(RequestParameter.RESULT_MESSAGE.toString(), SUCCESSFULLY_COMPLETED_OPERATION_STATUS);
		} catch (ServiceException e) {
			request.setAttribute(RequestParameter.ERROR_MESSAGE.toString(), ExceptionParser.getExceptionStatus(e));
			LOGGER.warn("Cannot unlock user account", e);
		}
		return PageEnum.ADMIN_USERS_MENU.getValue();
	}
}
