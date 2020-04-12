package by.training.payment.command.user.request;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import static by.training.payment.factory.RequestTypeFactory.OPEN_NEW_CARD_REQUEST;
import by.training.payment.command.PageEnum;
import by.training.payment.command.RequestParameter;
import by.training.payment.entity.CardExpiry;
import by.training.payment.entity.Currency;
import by.training.payment.entity.PaymentSystem;
import by.training.payment.entity.RequestType;
import by.training.payment.entity.UserRequest;
import by.training.payment.exception.ServiceException;
import by.training.payment.util.ExceptionParser;

public class UserCreateRequestOpenNewCardCommand extends AbstractCreateRequestCommand {

	private static final Logger LOGGER = Logger.getLogger(UserCreateRequestOpenNewCardCommand.class);
	private RequestType openNewCard = requestTypeFactory.getRequestType(OPEN_NEW_CARD_REQUEST);

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		Currency currency = new Currency(request.getParameter(RequestParameter.CURRENCY.toString()));
		PaymentSystem paymentSystem = new PaymentSystem(request.getParameter(RequestParameter.PAYMENT_SYSTEM.toString()));
		String cardExpirationDateValue = request.getParameter(RequestParameter.CARD_EXPIRATION_DATE.toString());
		CardExpiry cardExpirationDate = new CardExpiry(Integer.valueOf(cardExpirationDateValue));
		UserRequest userRequest = createUserRequest(request, openNewCard);
		try {
			userRequestService.saveRequestToOpenNewCard(userRequest, currency, paymentSystem, cardExpirationDate);
			setUserAccountListInRequestAttribute(request);
			request.setAttribute(RequestParameter.RESULT_MESSAGE.toString(), SUCCESSFULLY_CREATED_REQUEST_STATUS);
		} catch (ServiceException e) {
			request.setAttribute(RequestParameter.ERROR_MESSAGE.toString(), ExceptionParser.getExceptionStatus(e));
			LOGGER.warn("Cannot save request to open new card", e);
		}
		return PageEnum.USER_CREATING_REQUEST_MENU.getValue();
	}

}