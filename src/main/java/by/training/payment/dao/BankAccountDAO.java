package by.training.payment.dao;

import java.util.List;

import by.training.payment.entity.BankAccount;
import by.training.payment.exception.DAOException;

public interface BankAccountDAO {

	void addBankAccount(BankAccount account) throws DAOException;

	void updateBankAccount(BankAccount account) throws DAOException;

	void deleteBankAccount(BankAccount account) throws DAOException;

	List<BankAccount> getAllBankAccounts() throws DAOException;

	BankAccount getBankAccountById(int id) throws DAOException;
	
	List<BankAccount> getAllBankAccountsByUserId (int userId) throws DAOException;	
}
