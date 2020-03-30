package by.training.payment.command.user;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import by.training.payment.command.AbstractUserCommand;
import by.training.payment.command.PageEnum;
import by.training.payment.command.RequestParameter;
import by.training.payment.entity.Currency;
import by.training.payment.exception.ServiceException;
import by.training.payment.factory.ServiceFactory;
import by.training.payment.util.ExceptionParser;

public class ShowUserCurrenciesMenu extends AbstractUserCommand {

	private static final Logger LOGGER = Logger.getLogger(ShowUserCurrenciesMenu.class);
	private static final String NO_CURRENCIES_STATUS = "*Status2000*";

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		try {
			List<Currency> currencies = ServiceFactory.getInstance().getCurrencyService().getAllCurrencies();
			if (currencies != null) {
				request.setAttribute(RequestParameter.CURRENCIES_LIST.toString(), currencies);
				request.setAttribute(RequestParameter.CURRENT_DATE.toString(), new Date());
			} else {
				request.setAttribute(RequestParameter.ERROR_MESSAGE.toString(), NO_CURRENCIES_STATUS);
			}
		} catch (ServiceException e) {
			request.setAttribute(RequestParameter.ERROR_MESSAGE.toString(), ExceptionParser.getExceptionStatus(e));
			LOGGER.warn("Cannot load currencies", e);
		}
		return PageEnum.USER_CURRENCIES_MENU.getValue();
	}
}
