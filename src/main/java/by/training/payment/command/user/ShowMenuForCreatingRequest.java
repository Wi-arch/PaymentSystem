package by.training.payment.command.user;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import by.training.payment.command.AbstractCommand;
import by.training.payment.command.PageEnum;
import by.training.payment.command.RequestParameter;
import by.training.payment.entity.BankAccount;
import by.training.payment.entity.User;
import by.training.payment.exception.ServiceException;
import by.training.payment.util.ExceptionParser;

public class ShowMenuForCreatingRequest extends AbstractCommand {

	private static final Logger LOGGER = Logger.getLogger(ShowMenuForCreatingRequest.class);

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		User user = (User) request.getSession().getAttribute(RequestParameter.USER.toString());
		if (user != null) {
			try {
				List<BankAccount> bankAccounts = bankAccountService.getAllBankAccountsByUserLogin(user.getLogin());
				if (bankAccounts != null) {
					request.setAttribute(RequestParameter.BANK_ACCOUNT_LIST.toString(), bankAccounts);
				}
			} catch (ServiceException e) {
				request.setAttribute(RequestParameter.ERROR_MESSAGE.toString(), ExceptionParser.getExceptionStatus(e));
				LOGGER.warn("Cannot load user bank accounts", e);
			}
		}
		return PageEnum.USER_CREATING_REQUEST_MENU.getValue();
	}
}
