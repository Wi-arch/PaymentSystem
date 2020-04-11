package by.training.payment.command.admin.account;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import by.training.payment.command.PageEnum;
import by.training.payment.command.RequestParameter;
import by.training.payment.command.user.account.AbstractBankAccountCommand;
import by.training.payment.entity.BankAccount;
import by.training.payment.exception.ServiceException;
import by.training.payment.util.ExceptionParser;

public class ShowAllBankAccounts extends AbstractBankAccountCommand {

	private static final Logger LOGGER = Logger.getLogger(ShowAllBankAccounts.class);

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		try {
			List<BankAccount> bankAccountList = bankAccountService.getAllBankAccounts();
			setBankAccountListInRequestAttribute(request, bankAccountList);
		} catch (ServiceException e) {
			request.setAttribute(RequestParameter.ERROR_MESSAGE.toString(), ExceptionParser.getExceptionStatus(e));
			LOGGER.warn("Cannot load all bank accounts", e);
		}
		return PageEnum.ADMIN_ACCOUNTS_MENU.getValue();
	}
}
