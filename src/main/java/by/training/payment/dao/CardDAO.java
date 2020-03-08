package by.training.payment.dao;

import java.util.List;

import by.training.payment.entity.Card;
import by.training.payment.exception.DAOException;

public interface CardDAO extends DAO<Card, Integer> {

	List<Card> getAllCardsByUserId(int userId) throws DAOException;

	List<Card> getAllCardsByAccountId(int accountId) throws DAOException;
}
