package by.training.payment.service.impl;

import java.util.List;

import by.training.payment.dao.CurrencyDAO;
import by.training.payment.entity.Currency;
import by.training.payment.exception.DAOException;
import by.training.payment.exception.ServiceException;
import by.training.payment.factory.DAOFactory;
import by.training.payment.service.CurrencyService;

public class CurrencyServiceImpl implements CurrencyService {

	private final CurrencyDAO currencyDao = DAOFactory.getInstance().getCurrencyDAO();

	@Override
	public void updateCurrency(Currency currency) throws ServiceException {
		checkCurrencyFieldsForNull(currency);
		try {
			currencyDao.updateCurrency(currency);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public Currency getCurrencyById(int id) throws ServiceException {
		try {
			return currencyDao.getCurrencyById(id);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public List<Currency> getAllCurrencies() throws ServiceException {
		try {
			return currencyDao.getAllCurrencies();
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}

	private void checkCurrencyFieldsForNull(Currency currency) throws ServiceException {
		if (currency == null || currency.getName() == null || currency.getRate() == null) {
			throw new ServiceException("Null currency");
		}
	}

}
