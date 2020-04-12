package by.training.payment.command.admin.card;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import by.training.payment.command.PageEnum;
import by.training.payment.command.RequestParameter;
import by.training.payment.command.user.card.AbstractCardCommand;
import by.training.payment.entity.Card;
import by.training.payment.exception.ServiceException;
import by.training.payment.util.ExceptionParser;

public class AdminShowAllCardsByUserLoginCommand extends AbstractCardCommand {

	private static final Logger LOGGER = Logger.getLogger(AdminShowAllCardsByUserLoginCommand.class);

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		String login = request.getParameter(RequestParameter.USER_LOGIN.toString());
		try {
			List<Card> cardList = cardService.getAllCardsByUserLogin(login);
			setCardListInRequestAttribute(request, cardList);
		} catch (ServiceException e) {
			request.setAttribute(RequestParameter.ERROR_MESSAGE.toString(), ExceptionParser.getExceptionStatus(e));
			LOGGER.warn("Cannot load card list", e);
		}
		return PageEnum.ADMIN_CARDS_MENU.getValue();
	}
}
