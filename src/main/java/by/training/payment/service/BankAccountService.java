package by.training.payment.service;

import java.util.List;

import by.training.payment.entity.BankAccount;
import by.training.payment.exception.ServiceException;

public interface BankAccountService {

	List<BankAccount> getAllBankAccountsByUserLogin(String login) throws ServiceException;
}
