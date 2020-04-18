package by.training.payment.command.user.card;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import by.training.payment.command.PageEnum;
import by.training.payment.exception.ServiceException;

public class UserCardWriteOffOperationCommand extends AbstractCardCommand {

	private static final Logger LOGGER = Logger.getLogger(UserCardWriteOffOperationCommand.class);
	private static final boolean IS_WRITE_OFF_OPERATION = true;

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		try {
			makeSinglCardPayment(request, IS_WRITE_OFF_OPERATION);
		} catch (ServiceException e) {
			setErrorMessageInRequestAttribute(request, e);
			LOGGER.warn("Cannot make write-off user operation", e);
		}
		setUserCardListInRequestAttribute(request);
		return PageEnum.USER_CARDS_MENU.getValue();
	}
}
