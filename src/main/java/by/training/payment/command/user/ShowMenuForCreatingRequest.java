package by.training.payment.command.user;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.training.payment.command.AbstractUserCommand;
import by.training.payment.command.PageEnum;

public class ShowMenuForCreatingRequest extends AbstractUserCommand {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		return PageEnum.USER_CREATING_REQUEST_MENU.getValue();
	}
}
