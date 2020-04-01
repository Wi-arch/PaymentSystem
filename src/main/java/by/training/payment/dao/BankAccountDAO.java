package by.training.payment.dao;

import java.util.List;

import by.training.payment.entity.BankAccount;
import by.training.payment.exception.DAOException;

public interface BankAccountDAO {

	void addBankAccount(BankAccount bankAccount) throws DAOException;

	void updateBankAccount(BankAccount bankAccount) throws DAOException;

	void deleteBankAccount(BankAccount bankAccount) throws DAOException;

	BankAccount getBankAccountByAccountNumber(String number) throws DAOException;

	List<BankAccount> getAllBankAccounts() throws DAOException;
}
