package by.training.payment.util;

import java.math.BigDecimal;

import by.training.payment.exception.ServiceException;

public class StringParser {

	private static final String SCALE_REGEX = ".*\\{.*\"Cur_Abbreviation\":\"CURRENCY_NAME\",\"Cur_Scale\":(\\d+).*?\\}.*";
	private static final String RATE_REGEX = ".*\\{.*\"Cur_Abbreviation\":\"CURRENCY_NAME\".*?\"Cur_OfficialRate\":(\\d{1}\\.\\d{4})\\}.*";
	private static final String CURRENCY_NAME = "CURRENCY_NAME";
	private static final String CURRENCY_NAME_REGEX = "[A-Z] {3}";
	private static final String NATIONAL_BANK_CURRENCY_RATE_REGEX = "^\\[(?:\\{.*\"Cur_Abbreviation\":\"[A-Z]{3}\",\"Cur_Scale\":\\d+,.*\"Cur_OfficialRate\":\\d+\\.\\d+\\},?)*\\]$";

	private StringParser() {

	}

	public static int getScaleFromCurrencyRatesStringByCurrencyName(String currencyRate, String currencyName)
			throws ServiceException {
		checkIsCurrencyNameValid(currencyName);
		checkIsCurrencyRateValid(currencyRate);
		String currentScaleRegex = SCALE_REGEX.replace(CURRENCY_NAME, currencyName);
		String scale = currencyRate.replaceAll(currentScaleRegex, "$1");
		return Integer.valueOf(scale);
	}

	public static BigDecimal getRateFromCurrencyRatesStringByCurrencyName(String currencyRate, String currencyName)
			throws ServiceException {
		checkIsCurrencyNameValid(currencyName);
		checkIsCurrencyRateValid(currencyRate);
		String currenctRateRegex = RATE_REGEX.replace(CURRENCY_NAME, currencyName);
		String rate = currencyRate.replaceAll(currenctRateRegex, "$1");
		return new BigDecimal(rate);
	}

	private static void checkIsCurrencyNameValid(String currencyName) throws ServiceException {
		if (currencyName == null || !currencyName.matches(CURRENCY_NAME_REGEX)) {
			throw new ServiceException("Invalid currency name");
		}
	}

	private static void checkIsCurrencyRateValid(String currencyRate) throws ServiceException {
		if (currencyRate == null || !currencyRate.matches(NATIONAL_BANK_CURRENCY_RATE_REGEX)) {
			throw new ServiceException("Currency rate string not mathces to national bank rates string");
		}
	}
}
