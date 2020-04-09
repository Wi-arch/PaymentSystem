package by.training.payment.command.user.account;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import by.training.payment.command.AbstractCommand;
import by.training.payment.command.RequestParameter;
import by.training.payment.entity.BankAccount;
import by.training.payment.entity.Transaction;
import by.training.payment.entity.User;
import by.training.payment.exception.ServiceException;
import by.training.payment.util.ExceptionParser;

public abstract class AbstractBankAccountCommand extends AbstractCommand {

	private static final Logger LOGGER = Logger.getLogger(AbstractBankAccountCommand.class);

	protected void setUserAccountListInRequestAttribute(HttpServletRequest request) {
		User user = (User) request.getSession().getAttribute(RequestParameter.USER.toString());
		if (user != null) {
			try {
				List<BankAccount> bankAccounts = bankAccountService.getAllBankAccountsByUserLogin(user.getLogin());
				request.setAttribute(RequestParameter.BANK_ACCOUNT_LIST.toString(), bankAccounts);
			} catch (ServiceException e) {
				request.setAttribute(RequestParameter.ERROR_MESSAGE.toString(), ExceptionParser.getExceptionStatus(e));
				LOGGER.warn("Cannot load user card list by user login", e);
			}
		}
	}
	
	protected void setBankAccountTransactionListInRequestAttribute(HttpServletRequest request) {
		String accountNumber = request.getParameter(RequestParameter.BANK_ACCOUNT_NUMBER.toString());
		try {
			List<Transaction> transactionList = transactionService.getAllTransactionsByBankAccountNumber(accountNumber);
			request.setAttribute(RequestParameter.TRANSACTION_LIST.toString(), transactionList);
		} catch (ServiceException e) {
			request.setAttribute(RequestParameter.ERROR_MESSAGE.toString(), ExceptionParser.getExceptionStatus(e));
			LOGGER.warn("Cannot load transactions by user account number", e);
		}
	}
}
