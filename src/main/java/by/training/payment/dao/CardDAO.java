package by.training.payment.dao;

import java.util.List;

import by.training.payment.entity.Card;
import by.training.payment.exception.DAOException;

public interface CardDAO {

	void addCard(Card card) throws DAOException;

	void updateCard(Card card) throws DAOException;

	void deleteCard(Card card) throws DAOException;

	List<Card> getAllCards() throws DAOException;

	Card getCardById(int id) throws DAOException;

	List<Card> getAllCardsByUserId(int userId) throws DAOException;

	List<Card> getAllCardsByAccountId(int accountId) throws DAOException;
}
