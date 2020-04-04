package by.training.payment.command;

import javax.servlet.http.HttpServletRequest;

import by.training.payment.entity.User;
import by.training.payment.entity.UserRole;
import by.training.payment.factory.ServiceFactory;
import by.training.payment.service.BankAccountService;
import by.training.payment.service.CurrencyService;
import by.training.payment.service.UserRequestService;
import by.training.payment.service.UserService;

public abstract class AbstractCommand implements Command {

	protected final UserService userService = ServiceFactory.getInstance().getUserService();
	protected final UserRequestService userRequestService = ServiceFactory.getInstance().getUserRequestService();
	protected final CurrencyService currencyService = ServiceFactory.getInstance().getCurrencyService();
	protected final BankAccountService bankAccountService = ServiceFactory.getInstance().getBankAccountService();
	private static final String USER_ROLE = "User";
	private static final String ADMIN_ROLE = "Admin";

	protected User buildUser(HttpServletRequest request) {
		User user = new User();
		user.setLogin(request.getParameter(RequestParameter.USER_LOGIN.toString()));
		user.setPassword(request.getParameter(RequestParameter.USER_PASSWORD.toString()));
		user.setEmail(request.getParameter(RequestParameter.USER_EMAIL.toString()));
		user.setUserRole(new UserRole(request.getParameter(RequestParameter.USER_ROLE_VALUE.toString())));
		return user;
	}

	protected String defineUserPage(User user) {
		switch (user.getUserRole().getRole()) {
		case USER_ROLE:
			return PageEnum.USER_MAIN_MENU.getValue();
		case ADMIN_ROLE:
			return PageEnum.ADMIN_MAIN_MENU.getValue();
		default:
			return PageEnum.HOME_PAGE.getValue();
		}
	}

}
