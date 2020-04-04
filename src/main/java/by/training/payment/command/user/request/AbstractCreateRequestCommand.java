package by.training.payment.command.user.request;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import by.training.payment.command.AbstractCommand;
import by.training.payment.command.RequestParameter;
import by.training.payment.entity.BankAccount;
import by.training.payment.entity.User;
import by.training.payment.exception.ServiceException;
import by.training.payment.factory.RequestTypeFactory;

public abstract class AbstractCreateRequestCommand extends AbstractCommand {

	protected static final String SUCCESSFULLY_CREATED_REQUEST_STATUS = "*Status1018*";
	protected final RequestTypeFactory requestTypeFactory = RequestTypeFactory.getInstance();

	protected void setUserAccountListInRequestAttribute(HttpServletRequest request, User user) throws ServiceException {
		if (user != null) {
			List<BankAccount> bankAccounts = bankAccountService.getAllBankAccountsByUserLogin(user.getLogin());
			if (bankAccounts != null) {
				request.setAttribute(RequestParameter.BANK_ACCOUNT_LIST.toString(), bankAccounts);
			}
		}
	}
}
