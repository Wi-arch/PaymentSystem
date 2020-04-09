package by.training.payment.command.user.account;

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

public class ShowBankAccountTransactions extends AbstractCommand {

	private static final Logger LOGGER = Logger.getLogger(ShowBankAccountTransactions.class);

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		String accountNumber = request.getParameter(RequestParameter.BANK_ACCOUNT_NUMBER.toString());
		try {
			List<Transaction> transactionList = transactionService.getAllTransactionsByBankAccountNumber(accountNumber);
			request.setAttribute(RequestParameter.TRANSACTION_LIST.toString(), transactionList);
		} catch (ServiceException e) {
			request.setAttribute(RequestParameter.ERROR_MESSAGE.toString(), ExceptionParser.getExceptionStatus(e));
			LOGGER.warn("Cannot load transactions by user account number", e);
		}
		return PageEnum.USER_TRANSACTION_TABLE.getValue();
	}

}
