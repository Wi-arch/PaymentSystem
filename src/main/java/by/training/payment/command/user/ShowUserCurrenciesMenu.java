package by.training.payment.command.user;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import by.training.payment.command.AbstractCommand;
import by.training.payment.command.PageEnum;
import by.training.payment.command.RequestParameter;
import by.training.payment.entity.Currency;
import by.training.payment.exception.ServiceException;
import by.training.payment.util.ExceptionParser;

public class ShowUserCurrenciesMenu extends AbstractCommand {

	private static final Logger LOGGER = Logger.getLogger(ShowUserCurrenciesMenu.class);

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		try {
			List<Currency> currencies = currencyService.getAllCurrencies();
			if (currencies != null) {
				request.setAttribute(RequestParameter.CURRENCIES_LIST.toString(), currencies);
				request.setAttribute(RequestParameter.CURRENT_DATE.toString(), new Date());
			}
		} catch (ServiceException e) {
			request.setAttribute(RequestParameter.ERROR_MESSAGE.toString(), ExceptionParser.getExceptionStatus(e));
			LOGGER.warn("Cannot load currencies", e);
		}
		return PageEnum.USER_CURRENCIES_MENU.getValue();
	}
}
