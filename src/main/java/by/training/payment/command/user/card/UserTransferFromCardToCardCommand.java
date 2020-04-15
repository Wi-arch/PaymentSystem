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

public class UserTransferFromCardToCardCommand extends AbstractCardCommand {

	private static final Logger LOGGER = Logger.getLogger(UserTransferFromCardToCardCommand.class);

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		Card cardFrom = new Card(request.getParameter(RequestParameter.SENDER_CARD_NUMBER.toString()));
		Card cardTo = new Card(request.getParameter(RequestParameter.RECEIVER_CARD_NUMBER.toString()));
		BigDecimal amount = getAmountFromHttpRequest(request);
		String paymentPurpose = getPaymentPurposeFromHttpRequest(request);
		String ccvCode = getCcvCodeFromHttpRequest(request);
		Currency currency = getCurrencyFromHttpRequest(request);
		try {
			cardService.makeTransferFromCardToCard(cardFrom, amount, currency, paymentPurpose, ccvCode, cardTo);
			setUserCardListInRequestAttribute(request);
		} catch (ServiceException e) {
			setErrorMessageInRequestAttribute(request, e);
			LOGGER.warn("Cannot make transfer from card to card", e);
		}
		return PageEnum.USER_CARDS_MENU.getValue();
	}
}
