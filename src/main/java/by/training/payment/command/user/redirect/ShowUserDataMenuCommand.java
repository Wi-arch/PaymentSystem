package by.training.payment.command.user.redirect;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.training.payment.command.AbstractCommand;
import by.training.payment.command.PageEnum;

public class ShowUserDataMenuCommand extends AbstractCommand {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		return PageEnum.USER_PERSONAL_DATA_MENU.getValue();
	}
}
