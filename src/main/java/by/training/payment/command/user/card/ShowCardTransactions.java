package by.training.payment.command.user.card;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import by.training.payment.command.AbstractCommand;
import by.training.payment.command.PageEnum;
import by.training.payment.command.RequestParameter;
import by.training.payment.entity.Transaction;
import by.training.payment.exception.ServiceException;
import by.training.payment.util.ExceptionParser;

public class ShowCardTransactions extends AbstractCommand {

	private static final Logger LOGGER = Logger.getLogger(ShowCardTransactions.class);

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		String cardNumber = request.getParameter(RequestParameter.CARD_NUMBER.toString());
		try {
			List<Transaction> transactionList = transactionService.getAllTransactionsByCardNumber(cardNumber);
			request.setAttribute(RequestParameter.TRANSACTION_LIST.toString(), transactionList);
		} catch (ServiceException e) {
			request.setAttribute(RequestParameter.ERROR_MESSAGE.toString(), ExceptionParser.getExceptionStatus(e));
			LOGGER.warn("Cannot load card transactions", e);
		}
		return PageEnum.USER_TRANSACTION_TABLE.getValue();
	}
}
