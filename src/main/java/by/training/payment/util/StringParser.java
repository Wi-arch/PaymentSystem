package by.training.payment.util;

import java.math.BigDecimal;

public class StringParser {

	private static final String SCALE_REGEX = ".*\\{.*\"Cur_Abbreviation\":\"CURRENCY_NAME\",\"Cur_Scale\":(\\d+).*?\\}.*";
	private static final String RATE_REGEX = ".*\\{.*\"Cur_Abbreviation\":\"CURRENCY_NAME\".*?\"Cur_OfficialRate\":(\\d{1}\\.\\d{4})\\}.*";
	private static final String CURRENCY_NAME = "CURRENCY_NAME";

	private StringParser() {

	}

	public static int getScaleFromCurrencyRatesStringByCurrencyName(String currencyRate, String currencyName) {
		String currentScaleRegex = SCALE_REGEX.replace(CURRENCY_NAME, currencyName);
		String scale = currencyRate.replaceAll(currentScaleRegex, "$1");
		return Integer.valueOf(scale);
	}

	public static BigDecimal getRateFromCurrencyRatesStringByCurrencyName(String currencyRate, String currencyName) {
		String currenctRateRegex = RATE_REGEX.replace(CURRENCY_NAME, currencyName);
		String rate = currencyRate.replaceAll(currenctRateRegex, "$1");
		return new BigDecimal(rate);
	}

}
