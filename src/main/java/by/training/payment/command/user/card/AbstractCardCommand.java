package by.training.payment.command.user.card;

import java.math.BigDecimal;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import by.training.payment.command.AbstractCommand;
import by.training.payment.command.RequestParameter;
import by.training.payment.entity.Card;
import by.training.payment.entity.Currency;
import by.training.payment.entity.Transaction;
import by.training.payment.entity.User;
import by.training.payment.exception.ServiceException;
import by.training.payment.util.ExceptionParser;

public abstract class AbstractCardCommand extends AbstractCommand {

	private static final Logger LOGGER = Logger.getLogger(AbstractCardCommand.class);
	private static final String CARDS_NOT_FOUND = "label.cardsNotFound";

	protected void setUserCardListInRequestAttribute(HttpServletRequest request) {
		User user = (User) request.getSession().getAttribute(RequestParameter.USER.toString());
		if (user != null) {
			try {
				List<Card> cardList = cardService.getAllCardsByUserLogin(user.getLogin());
				request.setAttribute(RequestParameter.CARD_LIST.toString(), cardList);
			} catch (ServiceException e) {
				request.setAttribute(RequestParameter.ERROR_MESSAGE.toString(), ExceptionParser.getExceptionStatus(e));
				LOGGER.warn("Cannot load user card list by user login", e);
			}
		}
	}

	protected void makeSinglCardPayment(HttpServletRequest request, boolean writeOffPayment) throws ServiceException {
		Card card = new Card(request.getParameter(RequestParameter.CARD_NUMBER.toString()));
		BigDecimal amount = new BigDecimal(request.getParameter(RequestParameter.AMOUNT.toString()));
		Currency currency = new Currency(request.getParameter(RequestParameter.CURRENCY.toString()));
		String paymentPurpose = request.getParameter(RequestParameter.PAYMENT_PURPOSE.toString());
		String ccvCode = request.getParameter(RequestParameter.CCV_CODE.toString());
		cardService.makeSinglCardPayment(card, amount, currency, paymentPurpose, ccvCode, writeOffPayment);
		request.setAttribute(RequestParameter.RESULT_MESSAGE.toString(), SUCCESSFULLY_COMPLETED_OPERATION_STATUS);
	}

	protected void setTransactionListByCardNumberInRequestAttribute(HttpServletRequest request) {
		String cardNumber = request.getParameter(RequestParameter.CARD_NUMBER.toString());
		try {
			List<Transaction> transactionList = transactionService.getAllTransactionsByCardNumber(cardNumber);
			request.setAttribute(RequestParameter.TRANSACTION_LIST.toString(), transactionList);
		} catch (ServiceException e) {
			request.setAttribute(RequestParameter.ERROR_MESSAGE.toString(), ExceptionParser.getExceptionStatus(e));
			LOGGER.warn("Cannot load transactions by card number", e);
		}
	}

	protected void setCardListInRequestAttribute(HttpServletRequest request, List<Card> cardList) {
		if (cardList == null || cardList.isEmpty()) {
			request.setAttribute(RequestParameter.RESULT_MESSAGE.toString(), CARDS_NOT_FOUND);
		}
		request.setAttribute(RequestParameter.CARD_LIST.toString(), cardList);
	}
}
