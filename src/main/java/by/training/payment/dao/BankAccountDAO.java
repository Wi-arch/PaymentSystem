package by.training.payment.dao;

import java.util.List;

import by.training.payment.entity.BankAccount;
import by.training.payment.entity.Card;
import by.training.payment.entity.Transaction;
import by.training.payment.exception.DAOException;

public interface BankAccountDAO {

	void addBankAccount(BankAccount bankAccount) throws DAOException;

	void updateBankAccount(BankAccount bankAccount) throws DAOException;

	void deleteBankAccount(BankAccount bankAccount) throws DAOException;

	BankAccount getBankAccountByAccountNumber(String number) throws DAOException;

	List<BankAccount> getAllBankAccounts() throws DAOException;

	List<BankAccount> getAllBankAccountsByUserLogin(String login) throws DAOException;

	void saveSinglBankAccountOperationWithTransaction(BankAccount bankAccount, Transaction transaction)
			throws DAOException;

	void saveTransfer(BankAccount bankAccountFrom, Transaction transactionFrom, BankAccount bankAccountTo,
			Transaction transactionTo) throws DAOException;

	void lockBankAccountWithCardList(BankAccount bankAccount, List<Card> cardList) throws DAOException;
}
