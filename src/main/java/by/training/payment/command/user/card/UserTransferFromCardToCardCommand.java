package by.training.payment.command.user.card;

import java.math.BigDecimal;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import by.training.payment.command.PageEnum;
import by.training.payment.command.RequestParameter;
import by.training.payment.entity.Card;
import by.training.payment.entity.Currency;
import by.training.payment.exception.ServiceException;
import by.training.payment.util.ExceptionParser;

public class UserTransferFromCardToCardCommand extends AbstractCardCommand {

	private static final Logger LOGGER = Logger.getLogger(UserTransferFromCardToCardCommand.class);

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		Card cardFrom = new Card(request.getParameter(RequestParameter.SENDER_CARD_NUMBER.toString()));
		Card cardTo = new Card(request.getParameter(RequestParameter.RECEIVER_CARD_NUMBER.toString()));
		BigDecimal amount = new BigDecimal(request.getParameter(RequestParameter.AMOUNT.toString()));
		Currency currency = new Currency(request.getParameter(RequestParameter.CURRENCY.toString()));
		String paymentPurpose = request.getParameter(RequestParameter.PAYMENT_PURPOSE.toString());
		String ccvCode = request.getParameter(RequestParameter.CCV_CODE.toString());
		try {
			cardService.makeTransferFromCardToCard(cardFrom, amount, currency, paymentPurpose, ccvCode, cardTo);
			setUserCardListInRequestAttribute(request);
		} catch (ServiceException e) {
			request.setAttribute(RequestParameter.ERROR_MESSAGE.toString(), ExceptionParser.getExceptionStatus(e));
			LOGGER.warn("Cannot make transfer from card to card", e);
		}
		return PageEnum.USER_CARDS_MENU.getValue();
	}
}
