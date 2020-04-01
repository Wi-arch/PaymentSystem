package by.training.payment.service.impl;

import java.util.Date;
import java.util.List;

import by.training.payment.dao.CurrencyDAO;
import by.training.payment.entity.Currency;
import by.training.payment.exception.DAOException;
import by.training.payment.exception.ServiceException;
import by.training.payment.factory.DAOFactory;
import by.training.payment.service.CurrencyService;

public class CurrencyServiceImpl implements CurrencyService {

	private final CurrencyDAO currencyDAO = DAOFactory.getInstance().getCurrencyDAO();

	@Override
	public void updateCurrency(Currency currency) throws ServiceException {
		checkCurrencyFieldsForNull(currency);
		try {
			currency.setUpdateDate(new Date());
			currencyDAO.updateCurrency(currency);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public List<Currency> getAllCurrencies() throws ServiceException {
		try {
			return currencyDAO.getAllCurrencies();
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public Currency getCurrencyByName(String name) throws ServiceException {
		Currency currency = null;
		if (name != null) {
			try {
				currency = currencyDAO.getCurrencyByCurrencyName(name);
			} catch (DAOException e) {
				throw new ServiceException(e);
			}
		}
		return currency;
	}

	private void checkCurrencyFieldsForNull(Currency currency) throws ServiceException {
		if (currency == null || currency.getName() == null || currency.getRate() == null) {
			throw new ServiceException("Null currency");
		}
	}

}
