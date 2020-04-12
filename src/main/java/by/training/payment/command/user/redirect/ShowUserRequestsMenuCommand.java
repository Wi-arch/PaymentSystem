package by.training.payment.command.user.redirect;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.training.payment.command.PageEnum;
import by.training.payment.command.user.request.AbstractCreateRequestCommand;

public class ShowUserRequestsMenuCommand extends AbstractCreateRequestCommand {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		setUserRequestsInRequestAttribute(request);
		return PageEnum.USER_REQUESTS_MENU.getValue();
	}
}
