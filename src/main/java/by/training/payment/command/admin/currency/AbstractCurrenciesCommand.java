package by.training.payment.command.admin.currency;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import by.training.payment.command.AbstractCommand;
import by.training.payment.command.RequestParameter;
import by.training.payment.entity.Currency;
import by.training.payment.exception.ServiceException;
import by.training.payment.util.ExceptionParser;

public abstract class AbstractCurrenciesCommand extends AbstractCommand {

	private static final Logger LOGGER = Logger.getLogger(AbstractCurrenciesCommand.class);

	protected void setCurrenciesListInRequestAttribute(HttpServletRequest request) {
		try {
			List<Currency> currencies = currencyService.getAllCurrencies();
			request.setAttribute(RequestParameter.CURRENCIES_LIST.toString(), currencies);
			request.setAttribute(RequestParameter.CURRENT_DATE.toString(), new Date());
		} catch (ServiceException e) {
			request.setAttribute(RequestParameter.ERROR_MESSAGE.toString(), ExceptionParser.getExceptionStatus(e));
			LOGGER.warn("Cannot load currencies", e);
		}
	}
}
