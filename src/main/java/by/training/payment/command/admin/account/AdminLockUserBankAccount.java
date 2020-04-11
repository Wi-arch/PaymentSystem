package by.training.payment.command.admin.account;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import by.training.payment.command.PageEnum;
import by.training.payment.command.RequestParameter;
import by.training.payment.command.user.account.AbstractBankAccountCommand;
import by.training.payment.entity.BankAccount;
import by.training.payment.exception.ServiceException;
import by.training.payment.util.ExceptionParser;

public class AdminLockUserBankAccount extends AbstractBankAccountCommand {

	private static final Logger LOGGER = Logger.getLogger(AdminLockUserBankAccount.class);

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		BankAccount account = new BankAccount(request.getParameter(RequestParameter.BANK_ACCOUNT_NUMBER.toString()));
		try {
			bankAccountService.lockBankAccount(account);
			request.setAttribute(RequestParameter.RESULT_MESSAGE.toString(), SUCCESSFULLY_COMPLETED_OPERATION_STATUS);
		} catch (ServiceException e) {
			request.setAttribute(RequestParameter.ERROR_MESSAGE.toString(), ExceptionParser.getExceptionStatus(e));
			LOGGER.warn("Cannot lock user bank account", e);
		}
		return PageEnum.ADMIN_ACCOUNTS_MENU.getValue();
	}
}
