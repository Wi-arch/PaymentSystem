package by.training.payment.command.user.card;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import by.training.payment.command.PageEnum;
import by.training.payment.command.RequestParameter;
import by.training.payment.entity.Card;
import by.training.payment.exception.ServiceException;
import by.training.payment.util.ExceptionParser;

public class LockUserCard extends AbstractCardCommand {

	private static final Logger LOGGER = Logger.getLogger(LockUserCard.class);

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		Card card = new Card(request.getParameter(RequestParameter.CARD_NUMBER.toString()));
		try {
			cardService.lockCard(card);
			setUserCardListInRequestAttribute(request);
			request.setAttribute(RequestParameter.RESULT_MESSAGE.toString(), SUCCESSFULLY_COMPLETED_OPERATION_STATUS);
		} catch (ServiceException e) {
			request.setAttribute(RequestParameter.ERROR_MESSAGE.toString(), ExceptionParser.getExceptionStatus(e));
			LOGGER.warn("Cannot lock user card", e);
		}
		return PageEnum.USER_CARDS_MENU.getValue();
	}
}
