package by.training.payment.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import by.training.payment.entity.CardExpiry;
import by.training.payment.exception.ServiceException;

public class CardUtilTest {

	private static final String VALID_CARD_NUMBER = "4786123458678425";
	private static final String VALID_CARD_NUMBER_MASK = "478612******8425";
	private static final int VALID_CCV_CODE_LENGTH = 3;
	private static final int VALID_CARD_EXPIRY_VALUE = 5;
	private CardExpiry validCardExpiry;

	@Before
	public void init() {
		validCardExpiry = new CardExpiry(VALID_CARD_EXPIRY_VALUE);
	}

	@Test
	public void testGetCardNumberMaskFromCardNumberForNull() throws ServiceException {
		String cardNumberMask = CardUtil.getCardNumberMaskFromCardNumber(VALID_CARD_NUMBER);
		assertNotNull(cardNumberMask);
	}

	@Test
	public void testGetCardNumberMaskFromCardNumberPositive() throws ServiceException {
		String actual = CardUtil.getCardNumberMaskFromCardNumber(VALID_CARD_NUMBER);
		assertEquals(VALID_CARD_NUMBER_MASK, actual);
	}

	@Test
	public void testGetRandomCcvNumberForNull() throws ServiceException {
		String ccvNumber = CardUtil.getRandomCcvNumber();
		assertNotNull(ccvNumber);
	}

	@Test
	public void testGetRandomCcvNumberPositive() throws ServiceException {
		String ccvNumber = CardUtil.getRandomCcvNumber();
		int expected = VALID_CCV_CODE_LENGTH;
		int actual = ccvNumber.length();
		assertEquals(expected, actual);
	}

	@Test
	public void testGetValidUntilDateForNull() throws ServiceException {
		Date validUntilDate = CardUtil.getValidUntilDate(validCardExpiry);
		assertNotNull(validUntilDate);
	}

	@Test(expected = ServiceException.class)
	public void testGetValidUntilDateNegative() throws ServiceException {
		validCardExpiry = null;
		CardUtil.getValidUntilDate(validCardExpiry);
	}
}
