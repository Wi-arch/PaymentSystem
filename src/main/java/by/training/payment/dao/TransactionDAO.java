package by.training.payment.dao;

import java.util.List;

import by.training.payment.entity.Transaction;
import by.training.payment.exception.DAOException;

public interface TransactionDAO {

	void addTransaction(Transaction transaction) throws DAOException;

	void updateTransaction(Transaction transaction) throws DAOException;

	void deleteTransaction(Transaction transaction) throws DAOException;

	Transaction getTransactionById(int id) throws DAOException;

	List<Transaction> getAllTransactions() throws DAOException;

	List<Transaction> getAllTransactionsByAccountId(int accountId) throws DAOException;

	List<Transaction> getAllTransactionsByCardId(int cardId) throws DAOException;
}
