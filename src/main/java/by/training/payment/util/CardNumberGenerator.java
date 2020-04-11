package by.training.payment.util;

import java.util.Random;

import by.training.payment.entity.PaymentSystem;
import by.training.payment.exception.ServiceException;
import by.training.payment.factory.ServiceFactory;
import by.training.payment.service.CardService;

public enum CardNumberGenerator {

	INSTANCE;

	private static final int PERSONAL_NUMBER_LENGTH = 15;
	private static final int AMERICAN_EXPRESS_FIRST_CARD_NUMBER_DIGIT = 3;
	private static final int VISA_FIRST_CARD_NUMBER_DIGIT = 4;
	private static final int MASTER_CARD_FIRST_CARD_NUMBER_DIGIT = 5;
	private static final int UNION_PAY_FIRST_CARD_NUMBER_DIGIT = 6;
	private static final int BELCARD_FIRST_CARD_NUMBER_DIGIT = 6;
	private static final int DEFAULT_FIRST_CARD_NUMBER_DIGIT = 7;
	private static final String AMERICAN_EXPRESS = "American Express";
	private static final String MASTER_CARD = "MasterCard";
	private static final String UNION_PAY = "UnionPay";
	private static final String VISA = "Visa";
	private static final String BELCARD = "БелКарт";
	private static final int MINIMUM_TWO_DIGIT_NUMBER = 10;
	private static final Random RANDOM = new Random();
	private final CardService cardService = ServiceFactory.getInstance().getCardService();

	public String generateFreeCardNumberByPaymentSystem(PaymentSystem paymentSystem) throws ServiceException {
		StringBuilder number = null;
		do {
			number = new StringBuilder();
			number.append(defineFirsCardNumberDigit(paymentSystem));
			for (int i = 0; i < PERSONAL_NUMBER_LENGTH; i++) {
				number.append(getRandomDigit());
			}
		} while (!isCardNumberFree(number.toString()));
		return number.toString();
	}

	private int getRandomDigit() {
		return RANDOM.nextInt(MINIMUM_TWO_DIGIT_NUMBER);
	}

	private boolean isCardNumberFree(String number) throws ServiceException {
		return cardService.getCardByNumber(number) == null;
	}

	private int defineFirsCardNumberDigit(PaymentSystem paymentSystem) {
		switch (paymentSystem.getName()) {
		case AMERICAN_EXPRESS:
			return AMERICAN_EXPRESS_FIRST_CARD_NUMBER_DIGIT;
		case VISA:
			return VISA_FIRST_CARD_NUMBER_DIGIT;
		case MASTER_CARD:
			return MASTER_CARD_FIRST_CARD_NUMBER_DIGIT;
		case UNION_PAY:
			return UNION_PAY_FIRST_CARD_NUMBER_DIGIT;
		case BELCARD:
			return BELCARD_FIRST_CARD_NUMBER_DIGIT;
		default:
			return DEFAULT_FIRST_CARD_NUMBER_DIGIT;
		}
	}

}
