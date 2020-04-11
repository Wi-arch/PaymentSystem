package by.training.payment.service.impl;

import java.util.List;

import by.training.payment.dao.BankAccountDAO;
import by.training.payment.dao.CardDAO;
import by.training.payment.entity.BankAccount;
import by.training.payment.entity.Card;
import by.training.payment.exception.DAOException;
import by.training.payment.exception.ServiceException;
import by.training.payment.factory.DAOFactory;
import by.training.payment.service.BankAccountService;
import by.training.payment.validator.BankAccountValidator;

public class BankAccountServiceImpl implements BankAccountService {

	private final DAOFactory daoFactory = DAOFactory.getInstance();
	private final BankAccountDAO bankAccountDAO = daoFactory.getBankAccountDAO();
	private final CardDAO cardDAO = daoFactory.getCardDAO();
	private final BankAccountValidator bankAccountValidator = new BankAccountValidator();

	@Override
	public List<BankAccount> getAllBankAccountsByUserLogin(String login) throws ServiceException {
		try {
			return bankAccountDAO.getAllBankAccountsByUserLogin(login);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public void lockBankAccount(BankAccount bankAccount) throws ServiceException {
		bankAccountValidator.checkBankAccountNumberForNull(bankAccount);
		String bankAccountNumber = bankAccount.getAccountNumber();
		try {
			BankAccount existingBankAccount = bankAccountDAO.getBankAccountByAccountNumber(bankAccountNumber);
			bankAccountValidator.checkIsBankAccountBlocked(existingBankAccount);
			List<Card> cardList = cardDAO.getAllCardsByAccountNumber(bankAccountNumber);
			existingBankAccount.setBlocked(true);
			lockAllCards(cardList);
			bankAccountDAO.lockBankAccountWithCardList(existingBankAccount, cardList);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public void unlockBankAccount(BankAccount bankAccount) throws ServiceException {
		bankAccountValidator.checkBankAccountNumberForNull(bankAccount);
		String bankAccountNumber = bankAccount.getAccountNumber();
		try {
			BankAccount existingBankAccount = bankAccountDAO.getBankAccountByAccountNumber(bankAccountNumber);
			bankAccountValidator.checkBankAccountNumberForNull(existingBankAccount);
			existingBankAccount.setBlocked(false);
			bankAccountDAO.updateBankAccount(existingBankAccount);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}

	}

	@Override
	public BankAccount getBankAccountByNumber(String number) throws ServiceException {
		try {
			return bankAccountDAO.getBankAccountByAccountNumber(number);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public List<BankAccount> getAllBankAccounts() throws ServiceException {
		try {
			return bankAccountDAO.getAllBankAccounts();
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}

	private void lockAllCards(List<Card> cardList) {
		if (cardList != null) {
			for (Card card : cardList) {
				if (card != null) {
					card.setBlocked(true);
				}
			}
		}
	}

}
