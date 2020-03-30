package by.training.payment.util;

public class PasswordGenerator {

	private static final int MINIMUM_PASSWORD_LENGTH = 6;
	private static final int MAXIMUM_PASSWORD_LENGTH = 16;

	public static String generateRandomValidPassword() {
		StringBuilder password = new StringBuilder();
		int passwordLength = (int) (Math.random() * MAXIMUM_PASSWORD_LENGTH + MINIMUM_PASSWORD_LENGTH);
		for (int i = 0; i < passwordLength; i += 4) {
			password.append(getRandomDigit());
			password.append(getRandomUpperCaseLetter());
			password.append(getRandomSpecialSymbol());
			password.append(getRandomLowerCaseLetter());
		}
		return password.toString();
	}

	private static int getRandomDigit() {
		return (int) (Math.random() * 10);
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
