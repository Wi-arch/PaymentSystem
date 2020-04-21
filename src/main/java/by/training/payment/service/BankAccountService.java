package by.training.payment.service;

import java.util.List;

import by.training.payment.entity.BankAccount;
import by.training.payment.exception.ServiceException;

/**
 * Interface describing the behavior of classes for working with
 * {@link BankAccount}
 * 
 * @author Alexandr Borovets
 * @since JDK1.8
 */
public interface BankAccountService {

	/**
	 * Returns a list of bank accounts in which the owner matches the login passed
	 * as a parameter. Returns an empty list if no accounts were found.
	 * 
	 * @param login based on which the selection of bank accounts will be made
	 * @return a list containing bank accounts, if no accounts are found returns an
	 *         empty list. Never <code>null</code>.
	 * @throws ServiceException an exception occurred while reading data from a data
	 *                          source
	 */
	List<BankAccount> getAllBankAccountsByUserLogin(String login) throws ServiceException;

	/**
	 * Locks bank account that was passed as a parameter. It also blocks all cards
	 * that are tied to a specified bank account.
	 * 
	 * @param bankAccount to be blocked
	 * @throws ServiceException if bankAccount null or an exception occurred while
	 *                          updating data in a data source
	 */
	void lockBankAccount(BankAccount bankAccount) throws ServiceException;

	/**
	 * Unlocks bank account that was passed as a parameter.
	 * 
	 * @param bankAccount bankAccount to be unblocked
	 * @throws ServiceException if bankAccount null or an exception occurred while
	 *                          updating data in a data source
	 */
	void unlockBankAccount(BankAccount bankAccount) throws ServiceException;

	/**
	 * Return an bank account in which the number matches the number passed as a
	 * parameter. Return null if no matching bank account is found.
	 * 
	 * @param number account number on the basis of which the bank account will be
	 *               found
	 * @return {@link BankAccount} in which the number matches the number passed as
	 *         a parameter
	 * @throws ServiceException an exception occurred while reading data from a data
	 *                          source
	 */
	BankAccount getBankAccountByNumber(String number) throws ServiceException;

	/**
	 * Returns a list of all bank accounts. If no bank accounts are found, returns
	 * an empty list.
	 * 
	 * @return a list containing bank accounts, if no accounts are found returns an
	 *         empty list. Never <code>null</code>.
	 * @throws ServiceException if an exception occurred while reading data from a
	 *                          data source
	 */
	List<BankAccount> getAllBankAccounts() throws ServiceException;

}
