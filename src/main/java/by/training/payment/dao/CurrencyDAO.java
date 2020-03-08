package by.training.payment.dao;

import java.util.List;

import by.training.payment.entity.Currency;
import by.training.payment.exception.DAOException;

public interface CurrencyDAO {

	void addCurrency(Currency currency) throws DAOException;

	void updateCurrency(Currency currency) throws DAOException;

	void deleteCurrency(Currency currency) throws DAOException;

	List<Currency> getAllCurrencies() throws DAOException;

	List<Currency> getCurrencyByName(String name) throws DAOException;
}
