package by.training.payment.validator;

import java.math.BigDecimal;

import org.junit.Before;
import org.junit.Test;

import by.training.payment.entity.Currency;
import by.training.payment.exception.ServiceException;

public class CurrencyValidatorTest {

	private static final CurrencyValidator CURRENCY_VALIDATOR = new CurrencyValidator();
	private final Currency invalidCurrency = new Currency(null);
	private final Currency validCurrency = new Currency();
	private final BigDecimal validRate = new BigDecimal("2.0000");
	private final String validCurrencyName = "USD";
	private final int validScale = 1;

	@Before
	public void init() {
		validCurrency.setName(validCurrencyName);
		validCurrency.setRate(validRate);
		validCurrency.setScale(validScale);
	}

	@Test
	public void testCheckCurrencyNameForNullPositive() throws ServiceException {
		CURRENCY_VALIDATOR.checkCurrencyNameForNull(validCurrency);
	}

	@Test(expected = ServiceException.class)
	public void testCheckCurrencyNameForNullNegative() throws ServiceException {
		CURRENCY_VALIDATOR.checkCurrencyNameForNull(invalidCurrency);
	}

	@Test
	public void testCheckCurrencyFieldsForNullPositive() throws ServiceException {
		CURRENCY_VALIDATOR.checkCurrencyFieldsForNull(validCurrency);
	}

	@Test(expected = ServiceException.class)
	public void testCheckCurrencyFieldsForNullNegative() throws ServiceException {
		CURRENCY_VALIDATOR.checkCurrencyFieldsForNull(invalidCurrency);
	}
}
