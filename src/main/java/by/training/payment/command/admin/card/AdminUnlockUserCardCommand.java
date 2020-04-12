package by.training.payment.command.admin.card;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import by.training.payment.command.AbstractCommand;
import by.training.payment.command.PageEnum;
import by.training.payment.command.RequestParameter;
import by.training.payment.entity.Card;
import by.training.payment.exception.ServiceException;
import by.training.payment.util.ExceptionParser;

public class AdminUnlockUserCardCommand extends AbstractCommand {

	private static final Logger LOGGER = Logger.getLogger(AdminUnlockUserCardCommand.class);

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		Card card = new Card(request.getParameter(RequestParameter.CARD_NUMBER.toString()));
		try {
			cardService.unlockCard(card);
			request.setAttribute(RequestParameter.RESULT_MESSAGE.toString(), SUCCESSFULLY_COMPLETED_OPERATION_STATUS);
		} catch (ServiceException e) {
			request.setAttribute(RequestParameter.ERROR_MESSAGE.toString(), ExceptionParser.getExceptionStatus(e));
			LOGGER.warn("Cannot unlock user card", e);
		}
		return PageEnum.ADMIN_CARDS_MENU.getValue();
	}
}
