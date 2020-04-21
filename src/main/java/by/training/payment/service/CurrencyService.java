package by.training.payment.service;

import java.util.List;

import by.training.payment.entity.Currency;
import by.training.payment.exception.ServiceException;

/**
 * Interface describing the behavior of classes for working with
 * {@link Currency} entity.
 * 
 * @author Alexandr Borovets
 * @since JDK1.8
 */
public interface CurrencyService {

	/**
	 * Updates the currency specified as a parameter, and then sets the current date
	 * to {@link Currency#setUpdateDate(java.util.Date)}. If one of the required
	 * fields null, then throws an ServiceException.
	 * 
	 * @param currency to be updated
	 * @throws ServiceException if one of the required fields null or an exception
	 *                          occurred while updating data in a data source
	 */
	void updateCurrency(Currency currency) throws ServiceException;

	/**
	 * Returns the currency in which the name matches the name passed in the
	 * parameter. If no matching currency is found returns null.
	 * 
	 * @param name based on which the selection of currency will be made
	 * @return currency whose name matches the name passed as a parameter or null if
	 *         no matching currency is found
	 * @throws ServiceException an exception occurred while reading data from a data
	 *                          source
	 */
	Currency getCurrencyByName(String name) throws ServiceException;

	/**
	 * Return list containing all currencies, if no currency is found returns an
	 * empty list.
	 * 
	 * @return list containing all currencies, if no currency is found returns an
	 *         empty list. Never <code>null</code>.
	 * @throws ServiceException an exception occurred while reading data from a data
	 *                          source
	 */
	List<Currency> getAllCurrencies() throws ServiceException;

	/**
	 * Updates all exchange rates using API of the national bank of the republic of
	 * Belarus.
	 * 
	 * @see http://www.nbrb.by/apihelp/exrates
	 * 
	 * @throws ServiceException an exception occurred while reading/updating data
	 *                          from/in data source.
	 */
	void updateAllCurrenciesFromNationalBank() throws ServiceException;
}
