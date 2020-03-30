package by.training.payment.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.training.payment.entity.User;

public class UserLogin extends AbstractUserCommand {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		User user = (User) request.getSession().getAttribute(RequestParameter.USER.toString());
		return user != null ? defineUserPage(user) : PageEnum.LOGIN_PAGE.getValue();
	}
}
