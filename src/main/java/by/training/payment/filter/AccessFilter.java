package by.training.payment.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.training.payment.command.PageEnum;

/**
 * The filter is designed to prevent direct access to pages in the directory
 * jsp/user and jsp/admin. When you try to access these pages, you are
 * redirected to the home page {@link PageEnum#HOME_PAGE}.
 * 
 * @author Alexandr Borovets
 * @since JDK1.8
 */
@WebFilter(filterName = "AccessFilter", urlPatterns = { "/jsp/user/*", "/jsp/admin/*" })
public class AccessFilter implements Filter {

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpsResponse = (HttpServletResponse) response;
		httpsResponse.sendRedirect(httpRequest.getContextPath() + PageEnum.HOME_PAGE.getValue());
	}
}
