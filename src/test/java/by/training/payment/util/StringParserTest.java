package by.training.payment.util;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;

import org.junit.Test;

import by.training.payment.exception.ServiceException;

public class StringParserTest {

	private static final String VALID_CURRENCIES_RATES_FROM_NATIONAL_BANK = "[{\"Cur_ID\":170,\"Date\":\"2020-04-12T00:00:00\",\"Cur_Abbreviation\":\"AUD\",\"Cur_Scale\":1,\"Cur_Name\":\"Австралийский доллар\",\"Cur_OfficialRate\":1.5710},{\"Cur_ID\":191,\"Date\":\"2020-04-12T00:00:00\",\"Cur_Abbreviation\":\"BGN\",\"Cur_Scale\":1,\"Cur_Name\":\"Болгарский лев\",\"Cur_OfficialRate\":1.3852},{\"Cur_ID\":290,\"Date\":\"2020-04-12T00:00:00\",\"Cur_Abbreviation\":\"UAH\",\"Cur_Scale\":100,\"Cur_Name\":\"Гривен\",\"Cur_OfficialRate\":9.1067},{\"Cur_ID\":291,\"Date\":\"2020-04-12T00:00:00\",\"Cur_Abbreviation\":\"DKK\",\"Cur_Scale\":10,\"Cur_Name\":\"Датских крон\",\"Cur_OfficialRate\":3.6292},{\"Cur_ID\":145,\"Date\":\"2020-04-12T00:00:00\",\"Cur_Abbreviation\":\"USD\",\"Cur_Scale\":1,\"Cur_Name\":\"Доллар США\",\"Cur_OfficialRate\":2.4752},{\"Cur_ID\":292,\"Date\":\"2020-04-12T00:00:00\",\"Cur_Abbreviation\":\"EUR\",\"Cur_Scale\":1,\"Cur_Name\":\"Евро\",\"Cur_OfficialRate\":2.7060},{\"Cur_ID\":293,\"Date\":\"2020-04-12T00:00:00\",\"Cur_Abbreviation\":\"PLN\",\"Cur_Scale\":10,\"Cur_Name\":\"Злотых\",\"Cur_OfficialRate\":5.9595},{\"Cur_ID\":303,\"Date\":\"2020-04-12T00:00:00\",\"Cur_Abbreviation\":\"IRR\",\"Cur_Scale\":100000,\"Cur_Name\":\"Иранских риалов\",\"Cur_OfficialRate\":5.8933},{\"Cur_ID\":294,\"Date\":\"2020-04-12T00:00:00\",\"Cur_Abbreviation\":\"ISK\",\"Cur_Scale\":100,\"Cur_Name\":\"Исландских крон\",\"Cur_OfficialRate\":1.7378},{\"Cur_ID\":295,\"Date\":\"2020-04-12T00:00:00\",\"Cur_Abbreviation\":\"JPY\",\"Cur_Scale\":100,\"Cur_Name\":\"Йен\",\"Cur_OfficialRate\":2.2827},{\"Cur_ID\":23,\"Date\":\"2020-04-12T00:00:00\",\"Cur_Abbreviation\":\"CAD\",\"Cur_Scale\":1,\"Cur_Name\":\"Канадский доллар\",\"Cur_OfficialRate\":1.7743},{\"Cur_ID\":304,\"Date\":\"2020-04-12T00:00:00\",\"Cur_Abbreviation\":\"CNY\",\"Cur_Scale\":10,\"Cur_Name\":\"Китайских юаней\",\"Cur_OfficialRate\":3.5196},{\"Cur_ID\":72,\"Date\":\"2020-04-12T00:00:00\",\"Cur_Abbreviation\":\"KWD\",\"Cur_Scale\":1,\"Cur_Name\":\"Кувейтский динар\",\"Cur_OfficialRate\":7.9588},{\"Cur_ID\":296,\"Date\":\"2020-04-12T00:00:00\",\"Cur_Abbreviation\":\"MDL\",\"Cur_Scale\":10,\"Cur_Name\":\"Молдавских леев\",\"Cur_OfficialRate\":1.3789},{\"Cur_ID\":286,\"Date\":\"2020-04-12T00:00:00\",\"Cur_Abbreviation\":\"NZD\",\"Cur_Scale\":1,\"Cur_Name\":\"Новозеландский доллар\",\"Cur_OfficialRate\":1.5047},{\"Cur_ID\":297,\"Date\":\"2020-04-12T00:00:00\",\"Cur_Abbreviation\":\"NOK\",\"Cur_Scale\":10,\"Cur_Name\":\"Норвежских крон\",\"Cur_OfficialRate\":2.4244},{\"Cur_ID\":298,\"Date\":\"2020-04-12T00:00:00\",\"Cur_Abbreviation\":\"RUB\",\"Cur_Scale\":100,\"Cur_Name\":\"Российских рублей\",\"Cur_OfficialRate\":3.3571},{\"Cur_ID\":299,\"Date\":\"2020-04-12T00:00:00\",\"Cur_Abbreviation\":\"XDR\",\"Cur_Scale\":1,\"Cur_Name\":\"СДР (Специальные права заимствования)\",\"Cur_OfficialRate\":3.3727},{\"Cur_ID\":119,\"Date\":\"2020-04-12T00:00:00\",\"Cur_Abbreviation\":\"SGD\",\"Cur_Scale\":1,\"Cur_Name\":\"Сингапурcкий доллар\",\"Cur_OfficialRate\":1.7520},{\"Cur_ID\":300,\"Date\":\"2020-04-12T00:00:00\",\"Cur_Abbreviation\":\"KGS\",\"Cur_Scale\":100,\"Cur_Name\":\"Сомов\",\"Cur_OfficialRate\":3.1812},{\"Cur_ID\":301,\"Date\":\"2020-04-12T00:00:00\",\"Cur_Abbreviation\":\"KZT\",\"Cur_Scale\":1000,\"Cur_Name\":\"Тенге\",\"Cur_OfficialRate\":5.7519},{\"Cur_ID\":302,\"Date\":\"2020-04-12T00:00:00\",\"Cur_Abbreviation\":\"TRY\",\"Cur_Scale\":10,\"Cur_Name\":\"Турецких лир\",\"Cur_OfficialRate\":3.6903},{\"Cur_ID\":143,\"Date\":\"2020-04-12T00:00:00\",\"Cur_Abbreviation\":\"GBP\",\"Cur_Scale\":1,\"Cur_Name\":\"Фунт стерлингов\",\"Cur_OfficialRate\":3.0886},{\"Cur_ID\":305,\"Date\":\"2020-04-12T00:00:00\",\"Cur_Abbreviation\":\"CZK\",\"Cur_Scale\":100,\"Cur_Name\":\"Чешских крон\",\"Cur_OfficialRate\":10.0336},{\"Cur_ID\":306,\"Date\":\"2020-04-12T00:00:00\",\"Cur_Abbreviation\":\"SEK\",\"Cur_Scale\":10,\"Cur_Name\":\"Шведских крон\",\"Cur_OfficialRate\":2.4940},{\"Cur_ID\":130,\"Date\":\"2020-04-12T00:00:00\",\"Cur_Abbreviation\":\"CHF\",\"Cur_Scale\":1,\"Cur_Name\":\"Швейцарский франк\",\"Cur_OfficialRate\":2.5631}]";
	private static final String VALID_USD_CURRENCY_NAME = "USD";
	private static final String INVALID_CURRENCY_NAME = "Currency";
	private static final BigDecimal VALID_USD_RATE = new BigDecimal("2.4752");

	@Test
	public void testGetScaleFromCurrencyRatesStringByCurrencyNamePositive() throws ServiceException {
		int actual = StringParser.getScaleFromCurrencyRatesStringByCurrencyName(
				VALID_CURRENCIES_RATES_FROM_NATIONAL_BANK, VALID_USD_CURRENCY_NAME);
		int expected = 1;
		assertEquals(expected, actual);
	}

	@Test(expected = ServiceException.class)
	public void testGetScaleFromCurrencyRatesStringByCurrencyNameNegative() throws ServiceException {
		StringParser.getScaleFromCurrencyRatesStringByCurrencyName(VALID_CURRENCIES_RATES_FROM_NATIONAL_BANK,
				INVALID_CURRENCY_NAME);
	}

	@Test(expected = ServiceException.class)
	public void testGetScaleFromCurrencyRatesStringByCurrencyNameForNull() throws ServiceException {
		StringParser.getScaleFromCurrencyRatesStringByCurrencyName(null, VALID_USD_CURRENCY_NAME);
	}

	@Test
	public void testGetRateFromCurrencyRatesStringByCurrencyNamePositive() throws ServiceException {
		BigDecimal actual = StringParser.getRateFromCurrencyRatesStringByCurrencyName(
				VALID_CURRENCIES_RATES_FROM_NATIONAL_BANK, VALID_USD_CURRENCY_NAME);
		assertEquals(VALID_USD_RATE, actual);
	}

	@Test(expected = ServiceException.class)
	public void testGetRateFromCurrencyRatesStringByCurrencyNameNegative() throws ServiceException {
		StringParser.getRateFromCurrencyRatesStringByCurrencyName(VALID_CURRENCIES_RATES_FROM_NATIONAL_BANK,
				INVALID_CURRENCY_NAME);
	}

	@Test(expected = ServiceException.class)
	public void testGetRateFromCurrencyRatesStringByCurrencyNameForNull() throws ServiceException {
		StringParser.getRateFromCurrencyRatesStringByCurrencyName(null, VALID_USD_CURRENCY_NAME);
	}
}
