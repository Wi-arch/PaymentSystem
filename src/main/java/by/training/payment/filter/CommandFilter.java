package by.training.payment.filter;

import java.io.IOException;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.training.payment.command.CommandName;
import by.training.payment.command.PageEnum;
import by.training.payment.command.RequestParameter;

@WebFilter(urlPatterns = { "/controller" }, servletNames = { "Controller" })
public class CommandFilter implements Filter {

	private static final Set<String> COMMAND_NAME_SET = Arrays.asList(CommandName.values()).stream()
			.map(CommandName::name).collect(Collectors.toSet());

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		String command = request.getParameter(RequestParameter.COMMAND.toString());
		if (command == null || !COMMAND_NAME_SET.contains(command)) {
			httpResponse.sendRedirect(httpRequest.getContextPath() + PageEnum.HOME_PAGE.getValue());
			return;
		}
		chain.doFilter(request, response);
	}

}
