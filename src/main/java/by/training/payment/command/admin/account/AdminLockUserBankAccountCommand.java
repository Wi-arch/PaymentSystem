package by.training.payment.command.admin.account;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import by.training.payment.command.PageEnum;
import by.training.payment.command.RequestParameter;
import by.training.payment.command.user.account.AbstractBankAccountCommand;
import by.training.payment.entity.BankAccount;
import by.training.payment.exception.ServiceException;

public class AdminLockUserBankAccountCommand extends AbstractBankAccountCommand {

	private static final Logger LOGGER = Logger.getLogger(AdminLockUserBankAccountCommand.class);

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		BankAccount account = getBankAccountFromHttpRequest(request);
		try {
			bankAccountService.lockBankAccount(account);
			request.setAttribute(RequestParameter.RESULT_MESSAGE.toString(), SUCCESSFULLY_COMPLETED_OPERATION_STATUS);
		} catch (ServiceException e) {
			setErrorMessageInRequestAttribute(request, e);
			LOGGER.warn("Cannot lock user bank account", e);
		}
		return PageEnum.ADMIN_ACCOUNTS_MENU.getValue();
	}
}
