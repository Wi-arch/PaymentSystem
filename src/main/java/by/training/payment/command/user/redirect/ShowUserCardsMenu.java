package by.training.payment.command.user.redirect;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.training.payment.command.PageEnum;
import by.training.payment.command.user.card.AbstractCardCommand;

public class ShowUserCardsMenu extends AbstractCardCommand {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		setUserCardListInRequestAttribute(request);
		return PageEnum.USER_CARDS_MENU.getValue();
	}
}
