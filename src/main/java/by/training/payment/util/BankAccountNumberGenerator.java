package by.training.payment.util;

import java.util.Random;

import by.training.payment.exception.ServiceException;
import by.training.payment.factory.ServiceFactory;
import by.training.payment.service.BankAccountService;

public enum BankAccountNumberGenerator {

	INSTANCE;

	private static final String ACCOUNT_NUMBER_PREFIX = "BY77PSBN3014";
	private static final int PERSONAL_NUMBER_LENGTH = 16;
	private static final int MINIMUM_TWO_DIGIT_NUMBER = 10;
	private static final Random RANDOM = new Random();
	private final BankAccountService bankAccountService = ServiceFactory.getInstance().getBankAccountService();

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

	private int getRandomDigit() {
		return RANDOM.nextInt(MINIMUM_TWO_DIGIT_NUMBER);
	}

	private boolean isBankAccountNumberFree(String accountNumber) throws ServiceException {
		return bankAccountService.getBankAccountByNumber(accountNumber) == null;
	}

}
