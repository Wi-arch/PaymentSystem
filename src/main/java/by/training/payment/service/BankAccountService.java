package by.training.payment.service;

import java.util.List;

import by.training.payment.entity.BankAccount;
import by.training.payment.exception.ServiceException;

public interface BankAccountService {

	List<BankAccount> getAllBankAccountsByUserLogin(String login) throws ServiceException;

	void lockBankAccount(BankAccount bankAccount) throws ServiceException;

	void unlockBankAccount(BankAccount bankAccount) throws ServiceException;

	BankAccount getBankAccountByNumber(String number) throws ServiceException;

	List<BankAccount> getAllBankAccounts() throws ServiceException;

}
