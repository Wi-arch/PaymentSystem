package by.training.payment.command.admin.redirect;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.training.payment.command.AbstractCommand;
import by.training.payment.command.PageEnum;

public class ShowAdminAccountsMenuCommand extends AbstractCommand {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		return PageEnum.ADMIN_ACCOUNTS_MENU.getValue();
	}

}
