package by.training.payment.dao;

import java.util.List;

import by.training.payment.entity.CardExpirationDate;
import by.training.payment.exception.DAOException;

public interface CardExpirationDateDAO {

	void addCardExpirationDate(CardExpirationDate cardExpirationDate) throws DAOException;

	void updateCardExpirationDate(CardExpirationDate cardExpirationDate) throws DAOException;

	void deleteCardExpirationDate(CardExpirationDate cardExpirationDate) throws DAOException;

	List<CardExpirationDate> getAllCardExpirationDates() throws DAOException;

	CardExpirationDate getCardExpirationDateById(int id) throws DAOException;
}
