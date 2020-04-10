package by.training.payment.util;

import java.util.Random;

public class PasswordGenerator {

	private static final int MINIMUM_PASSWORD_LENGTH = 6;
	private static final int MAXIMUM_PASSWORD_LENGTH = 16;
	private static final int MINIMUM_TWO_DIGIT_NUMBER = 10;
	private static final Random RANDOM = new Random();

	private PasswordGenerator() {
	}

	public static String generateRandomValidPassword() {
		StringBuilder password = new StringBuilder();
		int passwordLength = RANDOM.nextInt(MAXIMUM_PASSWORD_LENGTH) + MINIMUM_PASSWORD_LENGTH;
		for (int i = 0; i < passwordLength; i += 4) {
			password.append(getRandomDigit());
			password.append(getRandomUpperCaseLetter());
			password.append(getRandomSpecialSymbol());
			password.append(getRandomLowerCaseLetter());
		}
		return password.toString();
	}

	private static int getRandomDigit() {
		return  RANDOM.nextInt(MINIMUM_TWO_DIGIT_NUMBER);
	}

	private static char getRandomLowerCaseLetter() {
		return (char) ('a' + Math.random() * ('z' - 'a'));
	}

	private static char getRandomUpperCaseLetter() {
		return (char) ('A' + Math.random() * ('Z' - 'A'));
	}

	private static char getRandomSpecialSymbol() {
		return (char) ('!' + Math.random() * ('/' - '!'));
	}
}
