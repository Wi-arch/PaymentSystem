package by.training.payment.command.user.redirect;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.training.payment.command.PageEnum;
import by.training.payment.command.user.account.AbstractBankAccountCommand;

public class ShowUserBankAccountMenu extends AbstractBankAccountCommand {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		setUserAccountListInRequestAttribute(request);
		return PageEnum.USER_ACCOUNTS_MENU.getValue();
	}

}
