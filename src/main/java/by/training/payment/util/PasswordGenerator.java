package by.training.payment.util;

import java.util.Random;

/**
 * The {@link PasswordGenerator} used to generate a random valid user password.
 * 
 * @author Alexandr Borovets
 * @since JDK1.8
 */
public class PasswordGenerator {

	/** Constant containing minimum password length */
	private static final int MINIMUM_PASSWORD_LENGTH = 6;

	/** Constant containing maximum password length */
	private static final int MAXIMUM_PASSWORD_LENGTH = 16;

	/** Constant containing minimum two-digit number */
	private static final int MINIMUM_TWO_DIGIT_NUMBER = 10;

	/** Constant containing {@link Random} */
	private static final Random RANDOM = new Random();

	/**
	 * Private constructor prohibiting the creation of class instances.
	 */
	private PasswordGenerator() {
	}

	/**
	 * Generates a random valid user password.
	 * 
	 * @return {@link String} containing random valid user password. Never
	 *         <code>null</code>.
	 */
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

	/**
	 * Generates a random digit from 0 to 9
	 * 
	 * @return random digit
	 */
	private static int getRandomDigit() {
		return RANDOM.nextInt(MINIMUM_TWO_DIGIT_NUMBER);
	}

	/**
	 * Generates a random lower case letter
	 * 
	 * @return random lower case letter
	 */
	private static char getRandomLowerCaseLetter() {
		return (char) ('a' + Math.random() * ('z' - 'a'));
	}

	/**
	 * Generates a random upper case letter
	 * 
	 * @return random upper case letter
	 */
	private static char getRandomUpperCaseLetter() {
		return (char) ('A' + Math.random() * ('Z' - 'A'));
	}

	/**
	 * Generates a random special symbol
	 * 
	 * @return random special symbol
	 */
	private static char getRandomSpecialSymbol() {
		return (char) ('!' + Math.random() * ('/' - '!'));
	}
}
