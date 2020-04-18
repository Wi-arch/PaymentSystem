package by.training.payment.util;

import java.util.Random;

import by.training.payment.entity.PaymentSystem;
import by.training.payment.exception.ServiceException;
import by.training.payment.factory.ServiceFactory;
import by.training.payment.service.CardService;

/**
 * The {@link CardNumberGenerator} used to generate random, not used payment
 * card numbers.
 * 
 * @author Alexandr Borovets
 * @since JDK1.8
 */
public enum CardNumberGenerator {

	INSTANCE;

	/** Length of string to be randomly generated */
	private static final int PERSONAL_NUMBER_LENGTH = 15;

	/**
	 * Constant containing the value of the first digit of the card number of the
	 * payment system American Express
	 */
	private static final int AMERICAN_EXPRESS_FIRST_CARD_NUMBER_DIGIT = 3;

	/**
	 * Constant containing the value of the first digit of the card number of the
	 * payment system Visa
	 */
	private static final int VISA_FIRST_CARD_NUMBER_DIGIT = 4;

	/**
	 * Constant containing the value of the first digit of the card number of the
	 * payment system MasterCard
	 */
	private static final int MASTER_CARD_FIRST_CARD_NUMBER_DIGIT = 5;

	/**
	 * Constant containing the value of the first digit of the card number of the
	 * payment system UnionPay
	 */
	private static final int UNION_PAY_FIRST_CARD_NUMBER_DIGIT = 6;

	/**
	 * Constant containing the value of the first digit of the card number of the
	 * payment system Белкарт
	 */
	private static final int BELCARD_FIRST_CARD_NUMBER_DIGIT = 6;

	/**
	 * Constant containing the default value of the first digit of the card number
	 */
	private static final int DEFAULT_FIRST_CARD_NUMBER_DIGIT = 7;

	private static final String AMERICAN_EXPRESS = "American Express";
	private static final String MASTER_CARD = "MasterCard";
	private static final String UNION_PAY = "UnionPay";
	private static final String VISA = "Visa";
	private static final String BELCARD = "БелКарт";

	/** Constant containing minimum two-digit number */
	private static final int MINIMUM_TWO_DIGIT_NUMBER = 10;

	/** Constant containing {@link Random} */
	private static final Random RANDOM = new Random();

	/** Constant containing {@link CardService} instance */
	private final CardService cardService = ServiceFactory.getInstance().getCardService();

	/**
	 * Generates a random {@link String} card number depending on the specified
	 * payment system. If the card number is already in use, the action is repeated.
	 * If the payment system is empty, then the default value is assigned.
	 * 
	 * @param paymentSystem value depending on which the first card number digit is
	 *                      generated
	 * @return {@link String} value containing a randomly generated, free payment
	 *         card number. Never <code>null</code>.
	 * @throws ServiceException if an exception occurred when calling a method
	 *                          {@link CardNumberGenerator#isCardNumberFree(String)}
	 */
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

	/**
	 * Generates a random digit from 0 to 9
	 * 
	 * @return random digit
	 */
	private int getRandomDigit() {
		return RANDOM.nextInt(MINIMUM_TWO_DIGIT_NUMBER);
	}

	/**
	 * @param number {@link String} to be checked
	 * @return <code>true<code> if and only if card number not used
	 * @throws ServiceException if an exception occurred when calling a method
	 *                          {@link CardService#getCardByNumber(String)}
	 */
	private boolean isCardNumberFree(String number) throws ServiceException {
		return cardService.getCardByNumber(number) == null;
	}

	/**
	 * Creates the first digit of the card number, depending on the specified
	 * payment system.If the payment system is empty, then the default value is
	 * assigned.
	 * 
	 * @param paymentSystem value depending on which the first card number digit is
	 *                      generated
	 * @return first digit of card number
	 */
	private int defineFirsCardNumberDigit(PaymentSystem paymentSystem) {
		if (paymentSystem == null || paymentSystem.getName() == null) {
			return DEFAULT_FIRST_CARD_NUMBER_DIGIT;
		}
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
