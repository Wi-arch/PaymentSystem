package by.training.payment.util;

import java.util.Random;

import by.training.payment.entity.BankAccount;
import by.training.payment.exception.ServiceException;
import by.training.payment.factory.ServiceFactory;
import by.training.payment.service.BankAccountService;

public enum BankAccountNumberGenerator {

	INSTANCE;

	/** Constant containing bank account number prefix */
	private static final String ACCOUNT_NUMBER_PREFIX = "BY77PSBN3014";

	/**
	 * The length of the bank account number whose values should be filled randomly
	 */
	private static final int PERSONAL_NUMBER_LENGTH = 16;

	/** Constant containing minimum two-digit number */
	private static final int MINIMUM_TWO_DIGIT_NUMBER = 10;

	/** Constant containing {@link Random} */
	private static final Random RANDOM = new Random();

	/** Constant containing {@link BankAccountService} instance */
	private final BankAccountService bankAccountService = ServiceFactory.getInstance().getBankAccountService();

	/**
	 * Generates a random string and then checks whether this string is used as an
	 * bank account number. Repeats this action if the bank account number is
	 * already in use.
	 * 
	 * @return {@link String} value containing a randomly generated, free bank
	 *         account number
	 * @throws ServiceException if an exception occurred while receiving
	 *                          {@link BankAccount} by account number when calling a
	 *                          method
	 *                          {@BankAccountNumberGenerator#isBankAccountNumberFree}
	 */
	public String generateRandomFreeBankAccountNumber() throws ServiceException {
		StringBuilder number = null;
		do {
			number = new StringBuilder(ACCOUNT_NUMBER_PREFIX);
			for (int i = 0; i < PERSONAL_NUMBER_LENGTH; i++) {
				number.append(getRandomDigit());
			}
		} while (!isBankAccountNumberFree(number.toString()));
		return number.toString();
	}

	/**
	 * Generates a random digit from 0 to 9
	 * 
	 * @return random digit
	 */
	private int getRandomDigit() {
		return RANDOM.nextInt(MINIMUM_TWO_DIGIT_NUMBER);
	}

	/**
	 * 
	 * @param accountNumber {@link String} to be checked
	 * @return <code>true<code> if and only if accountNumber not used
	 * @throws ServiceException if an exception occurred when calling a method
	 *                          {@BankAccountService#getBankAccountByNumber}
	 */
	private boolean isBankAccountNumberFree(String accountNumber) throws ServiceException {
		return bankAccountService.getBankAccountByNumber(accountNumber) == null;
	}

}
