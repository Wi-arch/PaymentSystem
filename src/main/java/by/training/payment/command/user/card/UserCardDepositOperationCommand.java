package by.training.payment.command.user.card;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import by.training.payment.command.PageEnum;
import by.training.payment.exception.ServiceException;

public class UserCardDepositOperationCommand extends AbstractCardCommand {

	private static final Logger LOGGER = Logger.getLogger(UserCardDepositOperationCommand.class);
	private static final boolean IS_WRITE_OFF_OPERATION = false;

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		try {
			makeSinglCardPayment(request, IS_WRITE_OFF_OPERATION);
			setUserCardListInRequestAttribute(request);
		} catch (ServiceException e) {
			setErrorMessageInRequestAttribute(request, e);
			LOGGER.warn("Cannot recharge user card", e);
		}
		return PageEnum.USER_CARDS_MENU.getValue();
	}
}
