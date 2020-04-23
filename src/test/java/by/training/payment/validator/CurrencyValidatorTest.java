package by.training.payment.validator;

import java.math.BigDecimal;

import org.junit.Before;
import org.junit.Test;

import by.training.payment.entity.Currency;
import by.training.payment.exception.ServiceException;

public class CurrencyValidatorTest {

	private static final CurrencyValidator CURRENCY_VALIDATOR = new CurrencyValidator();
	private static final Currency INVALID_CURRENCY = new Currency(null);
	private static final Currency VALID_CURRENCY = new Currency();
	private static final BigDecimal VALID_RATE = new BigDecimal("2.0000");
	private static final String VALID_CURRENCY_NAME = "USD";
	private static final int VALID_SCALE = 1;

	@Before
	public void init() {
		VALID_CURRENCY.setName(VALID_CURRENCY_NAME);
		VALID_CURRENCY.setRate(VALID_RATE);
		VALID_CURRENCY.setScale(VALID_SCALE);
	}

	@Test
	public void testCheckCurrencyNameForNullPositive() throws ServiceException {
		CURRENCY_VALIDATOR.checkCurrencyNameForNull(VALID_CURRENCY);
	}

	@Test(expected = ServiceException.class)
	public void testCheckCurrencyNameForNullNegative() throws ServiceException {
		CURRENCY_VALIDATOR.checkCurrencyNameForNull(INVALID_CURRENCY);
	}

	@Test
	public void testCheckCurrencyFieldsForNullPositive() throws ServiceException {
		CURRENCY_VALIDATOR.checkCurrencyFieldsForNull(VALID_CURRENCY);
	}

	@Test(expected = ServiceException.class)
	public void testCheckCurrencyFieldsForNullNegative() throws ServiceException {
		CURRENCY_VALIDATOR.checkCurrencyFieldsForNull(INVALID_CURRENCY);
	}
}
