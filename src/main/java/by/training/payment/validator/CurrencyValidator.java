package by.training.payment.validator;

import by.training.payment.entity.Currency;
import by.training.payment.exception.ServiceException;

public class CurrencyValidator {

	public void checkCurrencyNameForNull(Currency currency) throws ServiceException {
		if (currency == null || currency.getName() == null) {
			throw new ServiceException("Null currency name");
		}
	}

	public void checkCurrencyFieldsForNull(Currency currency) throws ServiceException {
		if (currency == null || currency.getName() == null || currency.getRate() == null) {
			throw new ServiceException("Null currency fileds");
		}
	}
}
