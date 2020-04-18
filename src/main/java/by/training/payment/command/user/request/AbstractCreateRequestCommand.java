package by.training.payment.command.user.request;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import by.training.payment.command.RequestParameter;
import by.training.payment.command.user.card.AbstractCardCommand;
import by.training.payment.entity.CardExpiry;
import by.training.payment.entity.PaymentSystem;
import by.training.payment.entity.RequestType;
import by.training.payment.entity.User;
import by.training.payment.entity.UserRequest;
import by.training.payment.exception.ServiceException;
import by.training.payment.factory.RequestTypeFactory;

public abstract class AbstractCreateRequestCommand extends AbstractCardCommand {

	private static final Logger LOGGER = Logger.getLogger(AbstractCreateRequestCommand.class);
	protected static final String SUCCESSFULLY_CREATED_REQUEST_STATUS = "*Status1018*";
	protected final RequestTypeFactory requestTypeFactory = RequestTypeFactory.getInstance();

	protected void setUserRequestsInRequestAttribute(HttpServletRequest request) {
		User user = (User) request.getSession().getAttribute(RequestParameter.USER.toString());
		if (user != null) {
			try {
				List<UserRequest> userRequests = userRequestService.getAllUserRequestsByUserLogin(user.getLogin());
				request.setAttribute(RequestParameter.USER_REQUEST_LIST.toString(), userRequests);
			} catch (ServiceException e) {
				setErrorMessageInRequestAttribute(request, e);
				LOGGER.warn("Cannot load user requests", e);
			}
		}
	}

	protected UserRequest createUserRequest(HttpServletRequest request, RequestType requestType) {
		User user = (User) request.getSession().getAttribute(RequestParameter.USER.toString());
		return new UserRequest(user, requestType);
	}

	protected PaymentSystem getPaymentSystemFromHttpRequest(HttpServletRequest request) {
		return new PaymentSystem(request.getParameter(RequestParameter.PAYMENT_SYSTEM.toString()));
	}

	protected CardExpiry getCardExpiryFromHttpRequest(HttpServletRequest request) {
		String cardExpiryValue = request.getParameter(RequestParameter.CARD_EXPIRATION_DATE.toString());
		CardExpiry cardExpiry = null;
		if (StringUtils.isNumeric(cardExpiryValue)) {
			cardExpiry = new CardExpiry(Integer.valueOf(cardExpiryValue));
		}
		return cardExpiry;
	}
}
