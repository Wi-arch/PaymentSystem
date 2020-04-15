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

public class AdminShowAllUserAccountsByLoginCommand extends AbstractBankAccountCommand {

	private static final Logger LOGGER = Logger.getLogger(AdminShowAllUserAccountsByLoginCommand.class);

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		String userLogin = request.getParameter(RequestParameter.USER_LOGIN.toString());
		try {
			List<BankAccount> bankAccountList = bankAccountService.getAllBankAccountsByUserLogin(userLogin);
			setBankAccountListInRequestAttribute(request, bankAccountList);
		} catch (ServiceException e) {
			setErrorMessageInRequestAttribute(request, e);
			LOGGER.warn("Cannot load user account list by user login", e);
		}
		return PageEnum.ADMIN_ACCOUNTS_MENU.getValue();
	}
}
