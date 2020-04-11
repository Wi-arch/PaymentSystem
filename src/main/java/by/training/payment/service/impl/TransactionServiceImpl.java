package by.training.payment.service.impl;

import java.util.List;

import by.training.payment.dao.TransactionDAO;
import by.training.payment.entity.Transaction;
import by.training.payment.exception.DAOException;
import by.training.payment.exception.ServiceException;
import by.training.payment.factory.DAOFactory;
import by.training.payment.service.TransactionService;

public class TransactionServiceImpl implements TransactionService {

	private final TransactionDAO transactionDAO = DAOFactory.getInstance().getTransactionDAO();

	@Override
	public List<Transaction> getAllTransactionsByCardNumber(String cardNumber) throws ServiceException {
		try {
			return transactionDAO.getAllTransactionsByCardNumber(cardNumber);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public List<Transaction> getAllTransactionsByBankAccountNumber(String number) throws ServiceException {
		try {
			return transactionDAO.getAllTransactionsByAccountNumber(number);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}
}
