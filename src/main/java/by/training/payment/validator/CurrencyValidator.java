package by.training.payment.validator;

import by.training.payment.entity.Currency;
import by.training.payment.exception.ServiceException;

/**
 * The {@link CurrencyValidator} class validates {@link Currency} entity.
 * 
 * @author Alexandr Borovets
 * @since JDK1.8
 */
public class CurrencyValidator {

	/**
	 * @param currency {@link Currency} to be validated
	 * @throws ServiceException if {@link Currency} null or
	 *                          {@link Currency#getName()} return null value
	 */
	public void checkCurrencyNameForNull(Currency currency) throws ServiceException {
		if (currency == null || currency.getName() == null) {
			throw new ServiceException("Null currency name");
		}
	}

	/**
	 * @param currency {@link Currency} to be validated
	 * @throws ServiceException if {@link Currency} null or one of required fields
	 *                          are null
	 */
	public void checkCurrencyFieldsForNull(Currency currency) throws ServiceException {
		if (currency == null || currency.getName() == null || currency.getRate() == null) {
			throw new ServiceException("Null currency fileds");
		}
	}
}
