package by.training.payment.util;

import java.util.Calendar;
import java.util.Date;
import java.util.Random;

import by.training.payment.entity.CardExpiry;

public class CardUtil {

	private static final String CARD_MASK = "******";
	private static final int START_CARD_MASK_INDEX = 6;
	private static final int END_CARD_MASK_INDEX = 12;
	private static final int MINIMUM_THREE_DIGIT_NUMBER = 100;
	private static final int NINE_HUNDRED = 900;
	private static final Random RANDOM = new Random();

	private CardUtil() {
	}

	public static Date getValidUntilDate(CardExpiry cardExpiry) {
		Calendar calendar = Calendar.getInstance();
		int currentYearNumber = calendar.get(Calendar.YEAR);
		calendar.set(Calendar.YEAR, currentYearNumber + cardExpiry.getValue());
		return calendar.getTime();
	}

	public static String getRandomCcvNumber() {
		return String.valueOf(RANDOM.nextInt(NINE_HUNDRED) + MINIMUM_THREE_DIGIT_NUMBER);
	}

	public static String getCardNumberMaskFromCardNumber(String cardNumber) {
		return new StringBuilder(cardNumber).replace(START_CARD_MASK_INDEX, END_CARD_MASK_INDEX, CARD_MASK).toString();
	}
}
