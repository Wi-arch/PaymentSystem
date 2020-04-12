package by.training.payment.command.admin.card;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.training.payment.command.PageEnum;
import by.training.payment.command.user.card.AbstractCardCommand;

public class ShowAdminCardTransactions extends AbstractCardCommand {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		setTransactionListByCardNumberInRequestAttribute(request);
		return PageEnum.ADMIN_TRANSACTIONS_TABLE.getValue();
	}
}
