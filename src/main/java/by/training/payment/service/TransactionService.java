package by.training.payment.service;

import java.util.List;

import by.training.payment.entity.Transaction;
import by.training.payment.exception.ServiceException;

public interface TransactionService {

	List<Transaction> getAllTransactionsByCardNumber(String cardNumber) throws ServiceException;

	List<Transaction> getAllTransactionsByBankAccountNumber(String number) throws ServiceException;
}
