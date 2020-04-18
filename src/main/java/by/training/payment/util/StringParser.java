package by.training.payment.util;

import java.math.BigDecimal;

import by.training.payment.exception.ServiceException;

/**
 * The {@link StringParser} used to convert and extract certain data from
 * {@link String}
 * 
 * @author Alexandr Borovets
 * @since JDK1.8
 */
public class StringParser {

	/**
	 * Regular expression to get scale value from {@link String}
	 */
	private static final String SCALE_REGEX = ".*\\{.*\"Cur_Abbreviation\":\"%s\",\"Cur_Scale\":(\\d+).*?\\}.*";

	/**
	 * Regular expression to get rate value from {@link String}
	 */
	private static final String RATE_REGEX = ".*\\{.*\"Cur_Abbreviation\":\"%s\".*?\"Cur_OfficialRate\":(\\d{1}\\.\\d{4})\\}.*";

	/**
	 * Regular expression check {@link String} whether it contains 3 upper case
	 * latin letters
	 */
	private static final String CURRENCY_NAME_REGEX = "[A-Z]{3}";

	/**
	 * Regular expression for check whether {@link String} matches the line of
	 * objects containing the rates of the national bank of the Republic of Belarus
	 * 
	 * @see http://www.nbrb.by/apihelp/exrates
	 * @see RequestSender
	 */
	private static final String NATIONAL_BANK_CURRENCY_RATE_REGEX = "^\\[(?:\\{.*\"Cur_Abbreviation\":\"[A-Z]{3}\",\"Cur_Scale\":\\d+,.*\"Cur_OfficialRate\":\\d+\\.\\d+\\},?)*\\]$";

	/**
	 * Private constructor prohibiting the creation of class instances.
	 */
	private StringParser() {
	}

	/**
	 * Extracts currency scale from the <code>currencyRate<code> of objects
	 * containing the rates of the national bank of the Republic of Belarus in
	 * accordance with the specified currency name. If <code>currencyRate<code> does
	 * not match the exchange rates provided by the national bank of the Republic of
	 * Belarus then throws ServiceException.
	 * 
	 * @see http://www.nbrb.by/apihelp/exrates
	 * 
	 * @param currencyRate {@link String} from which the currency scale will be
	 *                     extracted
	 * @param currencyName {@link String} name of the currency whose scale you need
	 *                     to find
	 * @return currency scale according to the specified <code>currencyName<code>
	 * @throws ServiceException If <code>currencyRate<code> null or does not match
	 *                          the exchange rates provided by the national bank of
	 *                          the Republic of Belarus. If <code>currencyName<code>
	 *                          null or not matches
	 *                          {@link StringParser#CURRENCY_NAME_REGEX}
	 */
	public static int getScaleFromCurrencyRatesStringByCurrencyName(String currencyRate, String currencyName)
			throws ServiceException {
		checkIsCurrencyNameValid(currencyName);
		checkIsCurrencyRateValid(currencyRate);
		String scale = currencyRate.replaceAll(String.format(SCALE_REGEX, currencyName), "$1");
		return Integer.valueOf(scale);
	}

	/**
	 * Extracts currency scale from the <code>currencyRate<code> of objects
	 * containing the rates of the national bank of the Republic of Belarus in
	 * accordance with the specified currency name. If <code>currencyRate<code> does
	 * not match the exchange rates provided by the national bank of the Republic of
	 * Belarus then throws ServiceException.
	 * 
	 * @see http://www.nbrb.by/apihelp/exrates
	 * 
	 * @param currencyRate {@link String} from which the currency scale will be
	 *                     extracted
	 * @param currencyName {@link String} name of the currency whose scale you need
	 *                     to find
	 * @return currency rate according to the specified <code>currencyName<code>
	 * @throws ServiceException If <code>currencyRate<code> null or does not match
	 *                          the exchange rates provided by the national bank of
	 *                          the Republic of Belarus. If <code>currencyName<code>
	 *                          null or not matches
	 *                          {@link StringParser#CURRENCY_NAME_REGEX}
	 */
	public static BigDecimal getRateFromCurrencyRatesStringByCurrencyName(String currencyRate, String currencyName)
			throws ServiceException {
		checkIsCurrencyNameValid(currencyName);
		checkIsCurrencyRateValid(currencyRate);
		String rate = currencyRate.replaceAll(String.format(RATE_REGEX, currencyName), "$1");
		return new BigDecimal(rate);
	}

	/**
	 * @param currencyName {@link String} to be checked
	 * @throws ServiceException if <code>currencyName<code> null or not matches
	 *                          {@link StringParser#CURRENCY_NAME_REGEX}
	 */
	private static void checkIsCurrencyNameValid(String currencyName) throws ServiceException {
		if (currencyName == null || !currencyName.matches(CURRENCY_NAME_REGEX)) {
			throw new ServiceException("Invalid currency name");
		}
	}

	/**
	 * @param currencyRate {@link String} to be checked
	 * @throws ServiceException if <code>currencyRate<code> null or not matches
	 *                          {@link StringParser#NATIONAL_BANK_CURRENCY_RATE_REGEX}
	 */
	private static void checkIsCurrencyRateValid(String currencyRate) throws ServiceException {
		if (currencyRate == null || !currencyRate.matches(NATIONAL_BANK_CURRENCY_RATE_REGEX)) {
			throw new ServiceException("Currency rate string not mathces to national bank rates string");
		}
	}
}
