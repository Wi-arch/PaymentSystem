package by.training.payment.service.impl;

import java.util.List;

import by.training.payment.dao.BankAccountDAO;
import by.training.payment.entity.BankAccount;
import by.training.payment.exception.DAOException;
import by.training.payment.exception.ServiceException;
import by.training.payment.factory.DAOFactory;
import by.training.payment.service.BankAccountService;

public class BankAccountServiceImpl implements BankAccountService {

	private final BankAccountDAO bankAccountDAO = DAOFactory.getInstance().getBankAccountDAO();

	@Override
	public List<BankAccount> getAllBankAccountsByUserLogin(String login) throws ServiceException {
		List<BankAccount> bankAccountList = null;
		if (login != null) {
			try {
				bankAccountList = bankAccountDAO.getAllBankAccountsByUserLogin(login);
			} catch (DAOException e) {
				throw new ServiceException(e);
			}
		}
		return bankAccountList;
	}

}
