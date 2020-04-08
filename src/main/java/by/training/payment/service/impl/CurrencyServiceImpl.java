package by.training.payment.service.impl;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import by.training.payment.dao.CurrencyDAO;
import by.training.payment.entity.Currency;
import by.training.payment.exception.DAOException;
import by.training.payment.exception.ServiceException;
import by.training.payment.factory.DAOFactory;
import by.training.payment.service.CurrencyService;
import by.training.payment.util.RequestSender;
import by.training.payment.util.StringParser;
import by.training.payment.validator.CurrencyValidator;

public class CurrencyServiceImpl implements CurrencyService {

	private final CurrencyDAO currencyDAO = DAOFactory.getInstance().getCurrencyDAO();
	private final CurrencyValidator currencyValidator = new CurrencyValidator();

	@Override
	public void updateCurrency(Currency currency) throws ServiceException {
		currencyValidator.checkCurrencyFieldsForNull(currency);
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
		try {
			return currencyDAO.getCurrencyByCurrencyName(name);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public void updateAllCurrenciesFromNationalBank() throws ServiceException {
		try {
			List<Currency> currencies = getAllCurrencies();
			String currencyRates = RequestSender.getCurrencyRatesStringFromNationalBank();
			if (currencyRates == null) {
				throw new ServiceException("Null currency rates from national bank");
			}
			for (Currency currency : currencies) {
				updateCurrencyFromCurrencyRatesString(currency, currencyRates);
			}
			for (Currency currency : currencies) {
				updateCurrency(currency);
			}
		} catch (IOException e) {
			throw new ServiceException(e);
		}
	}

	private void updateCurrencyFromCurrencyRatesString(Currency currency, String currencyRates) {
		if (!currency.getIsBaseCurrency()) {
			String currencyName = currency.getName();
			BigDecimal rate = StringParser.getRateFromCurrencyRatesStringByCurrencyName(currencyRates, currencyName);
			int scale = StringParser.getScaleFromCurrencyRatesStringByCurrencyName(currencyRates, currencyName);
			currency.setRate(rate);
			currency.setScale(scale);
			currency.setUpdateDate(new Date());
		}
	}

}
