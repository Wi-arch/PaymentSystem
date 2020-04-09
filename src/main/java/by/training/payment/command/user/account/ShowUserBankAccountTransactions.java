package by.training.payment.command.user.account;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.training.payment.command.PageEnum;

public class ShowUserBankAccountTransactions extends AbstractBankAccountCommand {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		setBankAccountTransactionListInRequestAttribute(request);
		return PageEnum.USER_TRANSACTION_TABLE.getValue();
	}
}
