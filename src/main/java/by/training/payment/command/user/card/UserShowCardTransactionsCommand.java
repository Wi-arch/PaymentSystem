package by.training.payment.command.user.card;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.training.payment.command.PageEnum;

public class UserShowCardTransactionsCommand extends AbstractCardCommand {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		setTransactionListByCardNumberInRequestAttribute(request);
		return PageEnum.USER_TRANSACTION_TABLE.getValue();
	}
}
