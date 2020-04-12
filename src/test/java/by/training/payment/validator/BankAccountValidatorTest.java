package by.training.payment.validator;

import org.junit.Test;

import by.training.payment.entity.BankAccount;
import by.training.payment.exception.ServiceException;

public class BankAccountValidatorTest {

	private static final String VALID_BANK_ACCOUNT_NUMBER = "BY77PSBN30141333921653306976";
	private static final BankAccountValidator BANK_ACCOUNT_VALIDATOR = new BankAccountValidator();
	private final BankAccount bankAccount = new BankAccount();

	@Test
	public void testCheckIsBankAccountBlockedPositive() throws ServiceException {
		bankAccount.setBlocked(false);
		BANK_ACCOUNT_VALIDATOR.checkIsBankAccountBlocked(bankAccount);
	}

	@Test(expected = ServiceException.class)
	public void testCheckIsBankAccountBlockedNegative() throws ServiceException {
		bankAccount.setBlocked(true);
		BANK_ACCOUNT_VALIDATOR.checkIsBankAccountBlocked(bankAccount);
	}

	@Test
	public void testCheckBankAccountNumberForNullPositive() throws ServiceException {
		bankAccount.setAccountNumber(VALID_BANK_ACCOUNT_NUMBER);
		BANK_ACCOUNT_VALIDATOR.checkBankAccountNumberForNull(bankAccount);
	}

	@Test(expected = ServiceException.class)
	public void testCheckBankAccountNumberForNullNegative() throws ServiceException {
		bankAccount.setAccountNumber(null);
		BANK_ACCOUNT_VALIDATOR.checkBankAccountNumberForNull(bankAccount);
	}
}
