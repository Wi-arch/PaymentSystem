package by.training.payment.dao;

import java.util.List;

import by.training.payment.entity.Transaction;
import by.training.payment.exception.DAOException;

public interface TransactionDAO extends DAO<Transaction, Integer> {

	List<Transaction> getAllTransactionsByAccountId(int accountId) throws DAOException;

	List<Transaction> getAllTransactionsByCardId(int cardId) throws DAOException;
}
