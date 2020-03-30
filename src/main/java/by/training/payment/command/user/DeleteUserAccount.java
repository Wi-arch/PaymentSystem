package by.training.payment.command.user;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.training.payment.command.AbstractUserCommand;
import by.training.payment.command.PageEnum;
import by.training.payment.command.RequestParameter;
import by.training.payment.entity.User;
import by.training.payment.exception.ServiceException;
import by.training.payment.util.ExceptionParser;

public class DeleteUserAccount extends AbstractUserCommand {

	private static final String SUCCESSFULLY_DELETE_ACCOUNT_STATUS = "*Status1010*";

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		String page = null;
		User user = (User) request.getSession().getAttribute(RequestParameter.USER.toString());
		try {
			USER_SERVICE.blockUser(user);
			request.getSession().invalidate();
			page = PageEnum.HOME_PAGE.getValue();
			request.setAttribute(RequestParameter.RESULT_MESSAGE.toString(), SUCCESSFULLY_DELETE_ACCOUNT_STATUS);
		} catch (ServiceException e) {
			request.setAttribute(RequestParameter.ERROR_MESSAGE.toString(), ExceptionParser.getExceptionStatus(e));
			page = PageEnum.USER_PERSONAL_DATA_MENU.toString();
		}
		return page;
	}
}