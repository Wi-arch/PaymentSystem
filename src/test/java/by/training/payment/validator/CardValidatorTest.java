package by.training.payment.validator;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;

import static org.apache.commons.codec.digest.DigestUtils.sha1Hex;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

import by.training.payment.entity.Card;
import by.training.payment.exception.ServiceException;

public class CardValidatorTest {

	private static final CardValidator CARD_VALIDATOR = new CardValidator();
	private static final Card CARD = new Card();
	private static final BigDecimal POSITIVE_AMOUNT = new BigDecimal(100);
	private static final BigDecimal NEGATIV_AMOUNT = new BigDecimal(-100);
	private static final String VALID_SENDER_CARD_NUMBER = "4233884915342222";
	private static final String VALID_RECEIVER_CARD_NUMBER = "4833889912342222";
	private static final String VALID_USER_CVC_CODE = "369";
	private static final String VALID_CARD_CVC_CODE = sha1Hex("369");
	private static final String VALID_PAYMENT_PURPOSE = "Cash deposit";
	private static final String EMPTY_PAYMENT_PURPOSE = "";

	@Test
	public void testCheckIsCardNumberLengthValidPositive() throws ServiceException {
		CARD_VALIDATOR.checkIsCardNumberLengthValid(VALID_SENDER_CARD_NUMBER);
	}

	@Test(expected = ServiceException.class)
	public void testCheckIsCardNumberLengthValidNegative() throws ServiceException {
		CARD_VALIDATOR.checkIsCardNumberLengthValid(VALID_USER_CVC_CODE);
	}

	@Test
	public void testCheckIsAmountPositivePositive() throws ServiceException {
		CARD_VALIDATOR.checkIsAmountPositive(POSITIVE_AMOUNT);
	}

	@Test(expected = ServiceException.class)
	public void testCheckIsAmountPositiveNegative() throws ServiceException {
		CARD_VALIDATOR.checkIsAmountPositive(NEGATIV_AMOUNT);
	}

	@Test
	public void testCheckIsCardBlockedPositive() throws ServiceException {
		CARD.setBlocked(false);
		CARD_VALIDATOR.checkIsCardBlocked(CARD);
	}

	@Test(expected = ServiceException.class)
	public void testCheckIsCardBlockedNegative() throws ServiceException {
		CARD.setBlocked(true);
		CARD_VALIDATOR.checkIsCardBlocked(CARD);
	}

	@Test
	public void testCompareSenderAndReceiverCardNumberPositive() throws ServiceException {
		CARD_VALIDATOR.compareSenderAndReceiverCardNumber(VALID_SENDER_CARD_NUMBER, VALID_RECEIVER_CARD_NUMBER);
	}

	@Test(expected = ServiceException.class)
	public void testCompareSenderAndReceiverCardNumberNegative() throws ServiceException {
		CARD_VALIDATOR.compareSenderAndReceiverCardNumber(VALID_SENDER_CARD_NUMBER, VALID_SENDER_CARD_NUMBER);
	}

	@Test
	public void testCheckCardNumberForNullPositive() throws ServiceException {
		CARD.setNumber(VALID_SENDER_CARD_NUMBER);
		CARD_VALIDATOR.checkCardNumberForNull(CARD);
	}

	@Test(expected = ServiceException.class)
	public void testCheckCardNumberForNullNegative() throws ServiceException {
		CARD.setNumber(null);
		CARD_VALIDATOR.checkCardNumberForNull(CARD);
	}

	@Test
	public void testCheckIsPaymentPurposeEmptyPositive() throws ServiceException {
		CARD_VALIDATOR.checkIsPaymentPurposeEmpty(VALID_PAYMENT_PURPOSE);
	}

	@Test(expected = ServiceException.class)
	public void testCheckIsPaymentPurposeEmptyNegative() throws ServiceException {
		CARD_VALIDATOR.checkIsPaymentPurposeEmpty(EMPTY_PAYMENT_PURPOSE);
	}

	@Test
	public void testCompareCcvCodesPositive() throws ServiceException {
		CARD_VALIDATOR.compareCcvCodes(VALID_USER_CVC_CODE, VALID_CARD_CVC_CODE);
	}

	@Test(expected = ServiceException.class)
	public void testCompareCcvCodesNegative() throws ServiceException {
		CARD_VALIDATOR.compareCcvCodes(VALID_USER_CVC_CODE, null);
	}

	@Test
	public void testIsExpiredDateCardPositive() {
		CARD.setValidUntilDate(getPastDate());
		boolean actual = CARD_VALIDATOR.isExpiredDateCard(CARD);
		boolean expected = true;
		assertEquals(expected, actual);
	}

	@Test
	public void testIsExpiredDateCardNegative() {
		CARD.setValidUntilDate(getFutureDate());
		boolean actual = CARD_VALIDATOR.isExpiredDateCard(CARD);
		boolean expected = false;
		assertEquals(expected, actual);
	}

	private Date getFutureDate() {
		Calendar futureDate = Calendar.getInstance();
		int currentYear = futureDate.get(Calendar.YEAR);
		futureDate.set(Calendar.YEAR, ++currentYear);
		return futureDate.getTime();
	}

	private Date getPastDate() {
		Calendar pastDate = Calendar.getInstance();
		int currentYear = pastDate.get(Calendar.YEAR);
		pastDate.set(Calendar.YEAR, --currentYear);
		return pastDate.getTime();
	}
}
