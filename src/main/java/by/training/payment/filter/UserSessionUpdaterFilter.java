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

import org.apache.log4j.Logger;

import by.training.payment.command.RequestParameter;
import by.training.payment.entity.User;
import by.training.payment.exception.ServiceException;
import by.training.payment.factory.ServiceFactory;
import by.training.payment.service.UserService;

/**
 * {@link UserSessionUpdaterFilter} filter is designed to update user data in
 * session. The filter only works when you try to access (using
 * {@link DispatcherType.REQUEST},
 * {@link DispatcherType.FORWARD},{@link DispatcherType.INCLUDE}) pages in
 * directory jsp/admin and jsp/user. If the {@link User} is not found in session
 * then data update will not happen.
 * 
 * @author Alexandr Borovets
 * @since JDK1.8
 */
@WebFilter(filterName = "UserSessionUpdaterFilter", dispatcherTypes = { DispatcherType.REQUEST, DispatcherType.FORWARD,
		DispatcherType.INCLUDE }, urlPatterns = { "/jsp/admin/*", "/jsp/user/*" })
public class UserSessionUpdaterFilter implements Filter {

	private static final UserService USER_SERVICE = ServiceFactory.getInstance().getUserService();
	private static final Logger LOGGER = Logger.getLogger(UserSessionUpdaterFilter.class);

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		User user = (User) httpRequest.getSession().getAttribute(RequestParameter.USER.toString());
		if (user != null) {
			try {
				User updatedUser = USER_SERVICE.getUserByLogin(user.getLogin());
				httpRequest.getSession().setAttribute(RequestParameter.USER.toString(), updatedUser);
			} catch (ServiceException e) {
				LOGGER.error("Cannot update user in session", e);
			}
		}
		chain.doFilter(request, response);
	}
}
