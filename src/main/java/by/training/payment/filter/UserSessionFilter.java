package by.training.payment.filter;

import java.io.IOException;

import javax.servlet.DispatcherType;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.training.payment.command.PageEnum;
import by.training.payment.command.RequestParameter;
import by.training.payment.entity.User;

/**
 * The filter is designed to prevent access to pages in the jsp/user directory
 * to users who are not authorized with the 'User' role or blocked users. When
 * you try to access these pages, you are redirected to the login page
 * {@link PageEnum#LOGIN_PAGE}.
 * 
 * @author Alexandr Borovets
 * @since JDK1.8
 */
@WebFilter(filterName = "UserSessionFilter", dispatcherTypes = { DispatcherType.REQUEST, DispatcherType.FORWARD,
		DispatcherType.INCLUDE }, urlPatterns = { "/jsp/user/*" })
public class UserSessionFilter implements Filter {

	private static final String USER_ROLE = "User";

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpsResponse = (HttpServletResponse) response;
		User user = (User) httpRequest.getSession().getAttribute(RequestParameter.USER.toString());
		if (user == null || user.getIsBlocked() || !user.getUserRole().getRole().equals(USER_ROLE)) {
			httpsResponse.sendRedirect(httpRequest.getContextPath() + PageEnum.LOGIN_PAGE.getValue());
			return;
		}
		chain.doFilter(request, response);
	}
}
