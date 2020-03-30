package by.training.payment.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.training.payment.command.PageEnum;

@WebFilter(urlPatterns = { "/jsp/user/*", "/jsp/admin/*" })
public class AccessFilter implements Filter {

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpsResponse = (HttpServletResponse) response;
		httpsResponse.sendRedirect(httpRequest.getContextPath() + PageEnum.HOME_PAGE.getValue());
	}
}
