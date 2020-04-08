package by.training.payment.command.user.card;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import by.training.payment.command.PageEnum;
import by.training.payment.command.RequestParameter;
import by.training.payment.exception.ServiceException;
import by.training.payment.util.ExceptionParser;

public class CardWriteOffOperation extends AbstractCardCommand {

	private static final Logger LOGGER = Logger.getLogger(CardWriteOffOperation.class);
	private static final boolean IS_WRITE_OFF_OPERATION = true;

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		try {
			makeSinglCardPayment(request, IS_WRITE_OFF_OPERATION);
			setUserCardListInRequestAttribute(request);
		} catch (ServiceException e) {
			request.setAttribute(RequestParameter.ERROR_MESSAGE.toString(), ExceptionParser.getExceptionStatus(e));
			LOGGER.warn("Cannot recharge user card", e);
		}
		return PageEnum.USER_CARDS_MENU.getValue();
	}
}
