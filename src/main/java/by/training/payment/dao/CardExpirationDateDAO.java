package by.training.payment.dao;

import java.util.List;

import by.training.payment.entity.CardExpirationDate;
import by.training.payment.exception.DAOException;

public interface CardExpirationDateDAO {

	CardExpirationDate getCardExpirationDateById(int id) throws DAOException;

	List<CardExpirationDate> getAllCardExpirationDates() throws DAOException;

}
