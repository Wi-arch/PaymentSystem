package by.training.payment.command.user.redirect;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.training.payment.command.PageEnum;
import by.training.payment.command.admin.currency.AbstractCurrenciesCommand;

public class ShowUserCurrenciesMenu extends AbstractCurrenciesCommand {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		setCurrenciesListInRequestAttribute(request);
		return PageEnum.USER_CURRENCIES_MENU.getValue();
	}
}
