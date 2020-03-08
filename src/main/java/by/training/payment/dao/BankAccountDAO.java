package by.training.payment.dao;

import java.util.List;

import by.training.payment.entity.BankAccount;
import by.training.payment.exception.DAOException;

public interface BankAccountDAO extends DAO<BankAccount, Integer> {

	List<BankAccount> getAllBankAccountsByUserId(int userId) throws DAOException;
}
