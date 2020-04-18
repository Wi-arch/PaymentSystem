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

@WebFilter(filterName = "AdminSessionFilter", dispatcherTypes = { DispatcherType.REQUEST, DispatcherType.FORWARD,
		DispatcherType.INCLUDE }, urlPatterns = { "/jsp/admin/*" })
public class AdminSessionFilter implements Filter {

	private static final String ADMIN_ROLE = "Admin";

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpsResponse = (HttpServletResponse) response;
		User user = (User) httpRequest.getSession().getAttribute(RequestParameter.USER.toString());
		if (user == null || user.getIsBlocked() || !user.getUserRole().getRole().equals(ADMIN_ROLE)) {
			httpsResponse.sendRedirect(httpRequest.getContextPath() + PageEnum.LOGIN_PAGE.getValue());
			return;
		}
		chain.doFilter(request, response);
	}
}