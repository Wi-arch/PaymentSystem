package by.training.payment.validator;

import by.training.payment.entity.BankAccount;
import by.training.payment.exception.ServiceException;

public class BankAccountValidator {

	public void checkIsBankAccountBlocked(BankAccount bankAccount) throws ServiceException {
		if (bankAccount == null || bankAccount.getIsBlocked()) {
			throw new ServiceException("Bank account is blocked *Status1021*");
		}
	}

	public void checkBankAccountNumberForNull(BankAccount bankAccount) throws ServiceException {
		if (bankAccount == null || bankAccount.getAccountNumber() == null) {
			throw new ServiceException("Null bank account number");
		}
	}
}
