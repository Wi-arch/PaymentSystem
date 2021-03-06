package by.training.payment.command.user;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import by.training.payment.command.AbstractCommand;
import by.training.payment.command.PageEnum;
import by.training.payment.command.RequestParameter;
import by.training.payment.entity.User;
import by.training.payment.exception.ServiceException;

public class UserDeleteAccountCommand extends AbstractCommand {

	private static final Logger LOGGER = Logger.getLogger(UserDeleteAccountCommand.class);
	private static final String SUCCESSFULLY_DELETE_ACCOUNT_STATUS = "*Status1010*";

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		String page = PageEnum.HOME_PAGE.getValue();
		User user = (User) request.getSession().getAttribute(RequestParameter.USER.toString());
		try {
			userService.blockUser(user);
			request.getSession().invalidate();
			request.setAttribute(RequestParameter.RESULT_MESSAGE.toString(), SUCCESSFULLY_DELETE_ACCOUNT_STATUS);
		} catch (ServiceException e) {
			setErrorMessageInRequestAttribute(request, e);
			LOGGER.warn("Cannot delete user account", e);
			page = PageEnum.USER_PERSONAL_DATA_MENU.getValue();
		}
		return page;
	}
}
