package by.training.payment.command.admin.currency;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import by.training.payment.command.PageEnum;
import by.training.payment.exception.ServiceException;

public class AdminUpdateCurrenciesFromNationalBankCommand extends AbstractCurrenciesCommand {

	private static final Logger LOGGER = Logger.getLogger(AdminUpdateCurrenciesFromNationalBankCommand.class);

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		try {
			currencyService.updateAllCurrenciesFromNationalBank();
		} catch (ServiceException e) {
			setErrorMessageInRequestAttribute(request, e);
			LOGGER.warn("Cannot update currencies from national bank", e);
		}
		setCurrenciesListInRequestAttribute(request);
		return PageEnum.ADMIN_CURRENCIES_MENU.getValue();
	}

}
