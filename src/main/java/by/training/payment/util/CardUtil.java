package by.training.payment.util;

import java.util.Calendar;
import java.util.Date;
import java.util.Random;

import by.training.payment.entity.CardExpiry;
import by.training.payment.exception.ServiceException;
import by.training.payment.validator.CardValidator;

public class CardUtil {

	private static final String CARD_MASK = "******";
	private static final int START_CARD_MASK_INDEX = 6;
	private static final int END_CARD_MASK_INDEX = 12;
	private static final int MINIMUM_THREE_DIGIT_NUMBER = 100;
	private static final int NINE_HUNDRED = 900;
	private static final Random RANDOM = new Random();
	private static final CardValidator CARD_VALIDATOR = new CardValidator();

	private CardUtil() {
	}

	public static Date getValidUntilDate(CardExpiry cardExpiry) throws ServiceException {
		if (cardExpiry == null) {
			throw new ServiceException("Null card expiry");
		}
		Calendar calendar = Calendar.getInstance();
		int currentYearNumber = calendar.get(Calendar.YEAR);
		calendar.set(Calendar.YEAR, currentYearNumber + cardExpiry.getValue());
		return calendar.getTime();
	}

	public static String getRandomCcvNumber() {
		return String.valueOf(RANDOM.nextInt(NINE_HUNDRED) + MINIMUM_THREE_DIGIT_NUMBER);
	}

	public static String getCardNumberMaskFromCardNumber(String cardNumber) throws ServiceException {
		CARD_VALIDATOR.checkIsCardNumberLengthValid(cardNumber);
		return new StringBuilder(cardNumber).replace(START_CARD_MASK_INDEX, END_CARD_MASK_INDEX, CARD_MASK).toString();
	}
}
