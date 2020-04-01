package by.training.payment.dao;

import java.util.List;

import by.training.payment.entity.Card;
import by.training.payment.exception.DAOException;

public interface CardDAO {

	void addCard(Card card) throws DAOException;

	void updateCard(Card card) throws DAOException;

	void deleteCard(Card card) throws DAOException;

	Card getCardByCardNumber(String number) throws DAOException;

	List<Card> getAllCards() throws DAOException;

	List<Card> getAllCardsByUserLogin(String login) throws DAOException;

	List<Card> getAllCardsByAccountNumber(String number) throws DAOException;
}
