package by.training.payment.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UserLogoutCommand implements Command {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		request.getSession().invalidate();
		return PageEnum.HOME_PAGE.getValue();
	}
}
