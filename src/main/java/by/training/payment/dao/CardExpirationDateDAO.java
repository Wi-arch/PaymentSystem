package by.training.payment.dao;

import java.util.List;

import by.training.payment.entity.CardExpiry;
import by.training.payment.exception.DAOException;

public interface CardExpirationDateDAO {

	CardExpiry getCardExpirationDateByValue(String value) throws DAOException;

	List<CardExpiry> getAllCardExpirationDates() throws DAOException;

}
