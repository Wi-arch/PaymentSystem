package by.training.payment.service;

import java.util.List;

import by.training.payment.entity.Currency;
import by.training.payment.exception.ServiceException;

public interface CurrencyService {

	void updateCurrency(Currency currency) throws ServiceException;

	Currency getCurrencyByName(String name) throws ServiceException;

	List<Currency> getAllCurrencies() throws ServiceException;
}
