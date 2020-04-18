package by.training.payment.command.user.card;

import java.math.BigDecimal;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import by.training.payment.command.RequestParameter;
import by.training.payment.command.user.account.AbstractBankAccountCommand;
import by.training.payment.entity.Card;
import by.training.payment.entity.Currency;
import by.training.payment.entity.Transaction;
import by.training.payment.entity.User;
import by.training.payment.exception.ServiceException;

public abstract class AbstractCardCommand extends AbstractBankAccountCommand {

	private static final Logger LOGGER = Logger.getLogger(AbstractCardCommand.class);
	private static final String CARDS_NOT_FOUND = "label.cardsNotFound";

	protected void setUserCardListInRequestAttribute(HttpServletRequest request) {
		User user = (User) request.getSession().getAttribute(RequestParameter.USER.toString());
		if (user != null) {
			try {
				List<Card> cardList = cardService.getAllCardsByUserLogin(user.getLogin());
				request.setAttribute(RequestParameter.CARD_LIST.toString(), cardList);
			} catch (ServiceException e) {
				setErrorMessageInRequestAttribute(request, e);
				LOGGER.warn("Cannot load user card list by user login", e);
			}
		}
	}

	protected void setUserAvailableToUnlockCardListInRequestAttribute(HttpServletRequest request) {
		User user = (User) request.getSession().getAttribute(RequestParameter.USER.toString());
		if (user != null) {
			try {
				List<Card> cardList = cardService.getAvailableToUnlockCardListByUserLogin(user.getLogin());
				request.setAttribute(RequestParameter.CARD_LIST.toString(), cardList);
			} catch (ServiceException e) {
				setErrorMessageInRequestAttribute(request, e);
				LOGGER.warn("Cannot load valid to unlock card list by user login", e);
			}
		}
	}

	protected void makeSinglCardPayment(HttpServletRequest request, boolean writeOffPayment) throws ServiceException {
		BigDecimal amount = getAmountFromHttpRequest(request);
		String ccvCode = getCvcCodeFromHttpRequest(request);
		String paymentPurpose = getPaymentPurposeFromHttpRequest(request);
		Currency currency = getCurrencyFromHttpRequest(request);
		Card card = getCardFromHttpRequest(request);
		cardService.makeSinglCardPayment(card, amount, currency, paymentPurpose, ccvCode, writeOffPayment);
		request.setAttribute(RequestParameter.RESULT_MESSAGE.toString(), SUCCESSFULLY_COMPLETED_OPERATION_STATUS);
	}

	protected void setTransactionListByCardNumberInRequestAttribute(HttpServletRequest request) {
		String cardNumber = request.getParameter(RequestParameter.CARD_NUMBER.toString());
		try {
			List<Transaction> transactionList = transactionService.getAllTransactionsByCardNumber(cardNumber);
			request.setAttribute(RequestParameter.TRANSACTION_LIST.toString(), transactionList);
		} catch (ServiceException e) {
			setErrorMessageInRequestAttribute(request, e);
			LOGGER.warn("Cannot load transactions by card number", e);
		}
	}

	protected void setCardListInRequestAttribute(HttpServletRequest request, List<Card> cardList) {
		if (cardList == null || cardList.isEmpty()) {
			request.setAttribute(RequestParameter.RESULT_MESSAGE.toString(), CARDS_NOT_FOUND);
		}
		request.setAttribute(RequestParameter.CARD_LIST.toString(), cardList);
	}

	protected BigDecimal getAmountFromHttpRequest(HttpServletRequest request) {
		BigDecimal amount = null;
		String stringAmount = request.getParameter(RequestParameter.AMOUNT.toString());
		try {
			if (stringAmount != null) {
				amount = new BigDecimal(stringAmount);
			}
		} catch (NumberFormatException e) {
			LOGGER.warn("Invalid payment amount", e);
		}
		return amount;
	}

	protected String getCvcCodeFromHttpRequest(HttpServletRequest request) {
		return request.getParameter(RequestParameter.CVC_CODE.toString());
	}

	protected String getPaymentPurposeFromHttpRequest(HttpServletRequest request) {
		return request.getParameter(RequestParameter.PAYMENT_PURPOSE.toString());
	}
}
