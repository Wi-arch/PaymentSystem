package by.training.payment.validator;

import by.training.payment.entity.BankAccount;
import by.training.payment.exception.ServiceException;

/**
 * The {@link BankAccountValidator} class validates {@link BankAccount} entity.
 * 
 * @author Alexandr Borovets
 * @since JDK1.8
 */
public class BankAccountValidator {

	/**
	 * @param bankAccount {@link BankAccount} to be validated
	 * @throws ServiceException if {@link BankAccount} null or
	 *                          {@link BankAccount#getIsBlocked()} return true
	 */
	public void checkIsBankAccountBlocked(BankAccount bankAccount) throws ServiceException {
		if (bankAccount == null || bankAccount.getIsBlocked()) {
			throw new ServiceException("Bank account is blocked *Status1021*");
		}
	}

	/**
	 * @param bankAccount {@link BankAccount} to be validated
	 * @throws ServiceException if {@link BankAccount} null or
	 *                          {@link BankAccount#getAccountNumber()} return null
	 *                          value
	 */
	public void checkBankAccountNumberForNull(BankAccount bankAccount) throws ServiceException {
		if (bankAccount == null || bankAccount.getAccountNumber() == null) {
			throw new ServiceException("Null bank account number");
		}
	}
}
