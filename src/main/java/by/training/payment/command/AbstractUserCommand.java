package by.training.payment.command;

import javax.servlet.http.HttpServletRequest;

import by.training.payment.entity.User;
import by.training.payment.entity.UserRole;
import by.training.payment.factory.ServiceFactory;
import by.training.payment.service.UserService;

public abstract class AbstractUserCommand implements Command {

	protected static final UserService USER_SERVICE = ServiceFactory.getInstance().getUserService();
	private static final String USER_ROLE = "user";
	private static final String ADMIN_ROLE = "admin";

	protected User buildUser(HttpServletRequest request) {
		User user = new User();
		user.setLogin(request.getParameter(RequestParameter.USER_LOGIN.toString()));
		user.setPassword(request.getParameter(RequestParameter.USER_PASSWORD.toString()));
		user.setEmail(request.getParameter(RequestParameter.USER_EMAIL.toString()));
		String userRoleId = request.getParameter(RequestParameter.USER_ROLE_ID.toString());
		if (userRoleId != null) {
			user.setUserRole(new UserRole(Integer.valueOf(userRoleId)));
		}
		return user;
	}

	protected String defineUserPage(User user) {
		switch (user.getUserRole().getValue()) {
		case USER_ROLE:
			return PageEnum.USER_MAIN_MENU.getValue();
		case ADMIN_ROLE:
			return PageEnum.ADMIN_MAIN_MENU.getValue();
		default:
			return PageEnum.HOME_PAGE.getValue();
		}
	}

}
