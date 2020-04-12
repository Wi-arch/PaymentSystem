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
	private final Card card = new Card();
	private final BigDecimal positiveAmount = new BigDecimal(100);
	private final BigDecimal negativeAmount = new BigDecimal(-100);
	private final String validSenderCardNumber = "4233884915342222";
	private final String validReceiverCardNumber = "4833889912342222";
	private final String validUserCcvCode = "369";
	private final String validCardCcvCode = sha1Hex("369");
	private final String validPaymentPurpose = "Cash deposit";
	private final String emptyPaymentPurpose = "";

	@Test
	public void testCheckIsCardNumberLengthValidPositive() throws ServiceException {
		CARD_VALIDATOR.checkIsCardNumberLengthValid(validSenderCardNumber);
	}

	@Test(expected = ServiceException.class)
	public void testCheckIsCardNumberLengthValidNegative() throws ServiceException {
		CARD_VALIDATOR.checkIsCardNumberLengthValid(validUserCcvCode);
	}

	@Test
	public void testCheckIsAmountPositivePositive() throws ServiceException {
		CARD_VALIDATOR.checkIsAmountPositive(positiveAmount);
	}

	@Test(expected = ServiceException.class)
	public void testCheckIsAmountPositiveNegative() throws ServiceException {
		CARD_VALIDATOR.checkIsAmountPositive(negativeAmount);
	}

	@Test
	public void testCheckIsCardBlockedPositive() throws ServiceException {
		card.setBlocked(false);
		CARD_VALIDATOR.checkIsCardBlocked(card);
	}

	@Test(expected = ServiceException.class)
	public void testCheckIsCardBlockedNegative() throws ServiceException {
		card.setBlocked(true);
		CARD_VALIDATOR.checkIsCardBlocked(card);
	}

	@Test
	public void testCompareSenderAndReceiverCardNumberPositive() throws ServiceException {
		CARD_VALIDATOR.compareSenderAndReceiverCardNumber(validSenderCardNumber, validReceiverCardNumber);
	}

	@Test(expected = ServiceException.class)
	public void testCompareSenderAndReceiverCardNumberNegative() throws ServiceException {
		CARD_VALIDATOR.compareSenderAndReceiverCardNumber(validSenderCardNumber, validSenderCardNumber);
	}

	@Test
	public void testCheckCardNumberForNullPositive() throws ServiceException {
		card.setNumber(validSenderCardNumber);
		CARD_VALIDATOR.checkCardNumberForNull(card);
	}

	@Test(expected = ServiceException.class)
	public void testCheckCardNumberForNullNegative() throws ServiceException {
		card.setNumber(null);
		CARD_VALIDATOR.checkCardNumberForNull(card);
	}

	@Test
	public void testCheckIsPaymentPurposeEmptyPositive() throws ServiceException {
		CARD_VALIDATOR.checkIsPaymentPurposeEmpty(validPaymentPurpose);
	}

	@Test(expected = ServiceException.class)
	public void testCheckIsPaymentPurposeEmptyNegative() throws ServiceException {
		CARD_VALIDATOR.checkIsPaymentPurposeEmpty(emptyPaymentPurpose);
	}

	@Test
	public void testCompareCcvCodesPositive() throws ServiceException {
		CARD_VALIDATOR.compareCcvCodes(validUserCcvCode, validCardCcvCode);
	}

	@Test(expected = ServiceException.class)
	public void testCompareCcvCodesNegative() throws ServiceException {
		CARD_VALIDATOR.compareCcvCodes(validUserCcvCode, null);
	}

	@Test
	public void testIsExpiredDateCardPositive() {
		card.setValidUntilDate(getPastDate());
		boolean actual = CARD_VALIDATOR.isExpiredDateCard(card);
		boolean expected = true;
		assertEquals(expected, actual);
	}

	@Test
	public void testIsExpiredDateCardNegative() {
		card.setValidUntilDate(getFutureDate());
		boolean actual = CARD_VALIDATOR.isExpiredDateCard(card);
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
