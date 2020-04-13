package by.training.payment.util;

import java.util.Calendar;
import java.util.Date;
import java.util.Random;

import by.training.payment.entity.Card;
import by.training.payment.entity.CardExpiry;
import by.training.payment.exception.ServiceException;
import by.training.payment.validator.CardValidator;

/**
 * * The {@link CardUtil} used to perform auxiliary actions with the
 * {@link Card} entity.
 * 
 * @author Alexandr Borovets
 * @since JDK1.8
 */
public class CardUtil {

	/** Constant {@link String} containing card mask */
	private static final String CARD_MASK = "******";

	/** Constant index indicating the beginning of the card mask */
	private static final int START_CARD_MASK_INDEX = 6;

	/** Constant index indicating the end of the card mask */
	private static final int END_CARD_MASK_INDEX = 12;

	/** Constant containing minimum three-digit number */
	private static final int MINIMUM_THREE_DIGIT_NUMBER = 100;

	/** Constant containing nine hundred number */
	private static final int NINE_HUNDRED = 900;

	/** Constant containing {@link Random} */
	private static final Random RANDOM = new Random();

	/** Constant containing {@link CardValidator} instance */
	private static final CardValidator CARD_VALIDATOR = new CardValidator();

	/**
	 * Private constructor prohibiting the creation of class instances.
	 */
	private CardUtil() {
	}

	/**
	 * Calculates the expiration date of the card, depending on {@link CardExpiry}.
	 * If {@link CardExpiry} is null, throws ServiceException.
	 * 
	 * @param cardExpiry value, depending on which is calculated, the expiration
	 *                   date of the payment card
	 * @return {@link Date} containing the current date increased by
	 *         {@link CardExpiry#getValue()} value. Never <code>null</code>.
	 * @throws ServiceException if cardExpiry <code>null<code>
	 */
	public static Date getValidUntilDate(CardExpiry cardExpiry) throws ServiceException {
		if (cardExpiry == null) {
			throw new ServiceException("Null card expiry");
		}
		Calendar calendar = Calendar.getInstance();
		int currentYearNumber = calendar.get(Calendar.YEAR);
		calendar.set(Calendar.YEAR, currentYearNumber + cardExpiry.getValue());
		return calendar.getTime();
	}

	/**
	 * Generates a random three-digit ccv card number.
	 * 
	 * @return {@link String} containing random three-digit ccv card number. Never
	 *         <code>null</code>.
	 */
	public static String getRandomCcvNumber() {
		return String.valueOf(RANDOM.nextInt(NINE_HUNDRED) + MINIMUM_THREE_DIGIT_NUMBER);
	}

	/**
	 * Masks the card number from 6 to 12, replacing the digit with '*'. If the card
	 * number is null or does not match the card number length throws
	 * ServiceException.
	 * 
	 * @param cardNumber {@link String} value to be masked
	 * @return {@link String} value in which characters from 6 to 12 are replaced by
	 *         '*'. Never <code>null</code>.
	 * @throws ServiceException if cardNumber is null or does not match the card
	 *                          number length
	 */
	public static String getCardNumberMaskFromCardNumber(String cardNumber) throws ServiceException {
		CARD_VALIDATOR.checkIsCardNumberLengthValid(cardNumber);
		return new StringBuilder(cardNumber).replace(START_CARD_MASK_INDEX, END_CARD_MASK_INDEX, CARD_MASK).toString();
	}
}
