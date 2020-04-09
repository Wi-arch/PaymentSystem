package by.training.payment.command.admin.account;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.training.payment.command.PageEnum;
import by.training.payment.command.user.account.AbstractBankAccountCommand;

public class ShowAdminBankAccountTransactions extends AbstractBankAccountCommand {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		setBankAccountTransactionListInRequestAttribute(request);
		return PageEnum.ADMIN_TRANSACTIONS_TABLE.getValue();
	}
}
