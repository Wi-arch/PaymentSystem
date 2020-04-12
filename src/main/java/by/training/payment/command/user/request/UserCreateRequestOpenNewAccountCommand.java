package by.training.payment.command.user.request;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import static by.training.payment.factory.RequestTypeFactory.OPEN_NEW_ACCOUNT_REQUEST;
import by.training.payment.command.PageEnum;
import by.training.payment.command.RequestParameter;
import by.training.payment.entity.Currency;
import by.training.payment.entity.RequestType;
import by.training.payment.entity.UserRequest;
import by.training.payment.exception.ServiceException;
import by.training.payment.util.ExceptionParser;

public class UserCreateRequestOpenNewAccountCommand extends AbstractCreateRequestCommand {

	private static final Logger LOGGER = Logger.getLogger(UserCreateRequestOpenNewAccountCommand.class);
	private RequestType openNewAccount = requestTypeFactory.getRequestType(OPEN_NEW_ACCOUNT_REQUEST);

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		Currency currency = new Currency(request.getParameter(RequestParameter.CURRENCY.toString()));
		UserRequest userRequest = createUserRequest(request, openNewAccount);
		try {
			userRequestService.saveRequestToOpenNewAccount(userRequest, currency);
			setUserAccountListInRequestAttribute(request);
			request.setAttribute(RequestParameter.RESULT_MESSAGE.toString(), SUCCESSFULLY_CREATED_REQUEST_STATUS);
		} catch (ServiceException e) {
			request.setAttribute(RequestParameter.ERROR_MESSAGE.toString(), ExceptionParser.getExceptionStatus(e));
			LOGGER.warn("Cannot save request to open new account", e);
		}
		return PageEnum.USER_CREATING_REQUEST_MENU.getValue();
	}

}
