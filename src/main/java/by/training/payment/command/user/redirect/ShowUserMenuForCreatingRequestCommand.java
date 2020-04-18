package by.training.payment.command.user.redirect;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.training.payment.command.PageEnum;
import by.training.payment.command.user.request.AbstractCreateRequestCommand;

public class ShowUserMenuForCreatingRequestCommand extends AbstractCreateRequestCommand {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		setUserAccountListInRequestAttribute(request);
		setUserAvailableToUnlockCardListInRequestAttribute(request);
		return PageEnum.USER_CREATING_REQUEST_MENU.getValue();
	}
}
