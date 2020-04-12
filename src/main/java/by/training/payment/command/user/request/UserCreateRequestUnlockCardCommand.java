package by.training.payment.command.user.request;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import static by.training.payment.factory.RequestTypeFactory.UNLOCK_CARD_REQUEST;
import org.apache.log4j.Logger;

import by.training.payment.command.PageEnum;
import by.training.payment.command.RequestParameter;
import by.training.payment.entity.Card;
import by.training.payment.entity.RequestType;
import by.training.payment.entity.UserRequest;
import by.training.payment.exception.ServiceException;
import by.training.payment.util.ExceptionParser;

public class UserCreateRequestUnlockCardCommand extends AbstractCreateRequestCommand {

	private static final Logger LOGGER = Logger.getLogger(UserCreateRequestUnlockCardCommand.class);
	private RequestType unlockCard = requestTypeFactory.getRequestType(UNLOCK_CARD_REQUEST);

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		Card card = new Card(request.getParameter(RequestParameter.CARD_NUMBER.toString()));
		UserRequest userRequest = createUserRequest(request, unlockCard);
		try {
			userRequestService.saveRequestToUnlockCard(userRequest, card);
			setUserAccountListInRequestAttribute(request);
			request.setAttribute(RequestParameter.RESULT_MESSAGE.toString(), SUCCESSFULLY_CREATED_REQUEST_STATUS);
		} catch (ServiceException e) {
			request.setAttribute(RequestParameter.ERROR_MESSAGE.toString(), ExceptionParser.getExceptionStatus(e));
			LOGGER.warn("Cannot save user request to unlock card", e);
		}
		return PageEnum.USER_CREATING_REQUEST_MENU.getValue();
	}

}
