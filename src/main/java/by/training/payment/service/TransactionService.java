package by.training.payment.service;

import java.util.List;

import by.training.payment.entity.Transaction;
import by.training.payment.exception.ServiceException;

/**
 * Interface describing the behavior of classes for working with
 * {@link Transaction} entity.
 * 
 * @author Alexandr Borovets
 * @since JDK1.8
 */
public interface TransactionService {

	/**
	 * Returns a list containing all transactions completed on the card specified as
	 * a parameter. If no transactions are found, returns an empty list.
	 * 
	 * @param cardNumber on the basis of which the transactions will be found
	 * @return list containing all transactions completed on the card specified as a
	 *         parameter. If no transactions are found, returns an empty list. Never
	 *         <code>null</code>.
	 * @throws ServiceException if an exception occurred while reading data from a
	 *                          data source
	 */
	List<Transaction> getAllTransactionsByCardNumber(String cardNumber) throws ServiceException;

	/**
	 * Returns a list containing all transactions completed on the bank account
	 * specified as a parameter. If no transactions are found, returns an empty
	 * list.
	 * 
	 * @param bank account number on the basis of which the transactions will be
	 *             found
	 * @return list containing all transactions completed on the bank account
	 *         specified as a parameter. If no transactions are found, returns an
	 *         empty list. Never <code>null</code>.
	 * @throws ServiceException if an exception occurred while reading data from a
	 *                          data source
	 */
	List<Transaction> getAllTransactionsByBankAccountNumber(String number) throws ServiceException;
}
