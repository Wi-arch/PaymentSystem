package by.training.payment.validator;

import static org.apache.commons.codec.digest.DigestUtils.sha1Hex;

import java.math.BigDecimal;
import java.util.Date;

import by.training.payment.entity.Card;
import by.training.payment.exception.ServiceException;

/**
 * The {@link CardValidator} class validates {@link Card} entity.
 * 
 * @author Alexandr Borovets
 * @since JDK1.8
 */
public class CardValidator {

	/** Constant containing integer value 1 */
	private static final int ONE = 1;

	/**
	 * Constant containing integer value indicating the length of the payment card
	 * number
	 */
	private static final int VALID_CARD_NUMBER_LENGTH = 16;

	/**
	 * Check if {@link BigDecimal} value zero or less throws ServiceException
	 * 
	 * @param amount {@link BigDecimal} to be validated
	 * @throws ServiceException if {@link BigDecimal} value zero or less throws
	 *                          ServiceException
	 */
	public void checkIsAmountPositive(BigDecimal amount) throws ServiceException {
		if (amount == null || amount.compareTo(BigDecimal.ZERO) < ONE) {
			throw new ServiceException("Amount must be greater than zero *Status1019*");
		}
	}

	/**
	 * @param senderNumber   {@link String} to be validated
	 * @param receiverNumber {@link String} to be validated
	 * @throws ServiceException if senderNumber equals to receiverNumber
	 */
	public void compareSenderAndReceiverCardNumber(String senderNumber, String receiverNumber) throws ServiceException {
		if (senderNumber != null && senderNumber.equals(receiverNumber)) {
			throw new ServiceException("Same sender and receiver card numbers *Status1025*");
		}
	}

	/**
	 * @param card {@link Card} to be validated
	 * @throws ServiceException if {@link Card} null or {@link Card#getIsBlocked()}
	 *                          return true
	 */
	public void checkIsCardBlocked(Card card) throws ServiceException {
		if (card == null || card.getIsBlocked()) {
			throw new ServiceException("Card is blocked *Status1020*");
		}
	}

	/**
	 * @param card {@link Card} to be validated
	 * @throws ServiceException if {@link Card} null or {@link Card#getNumber()}
	 *                          return null value
	 */
	public void checkCardNumberForNull(Card card) throws ServiceException {
		if (card == null || card.getNumber() == null) {
			throw new ServiceException("Null card number *Status1024*");
		}
	}

	/**
	 * 
	 * @param paymentPurpose {@link String} to be validated
	 * @throws ServiceException if paymentPurpose null or empty
	 */
	public void checkIsPaymentPurposeEmpty(String paymentPurpose) throws ServiceException {
		if (paymentPurpose == null || paymentPurpose.isEmpty()) {
			throw new ServiceException("Null payment purpose");
		}
	}

	/**
	 * Compares the value userCcvCode converted to {@link DigestUtils.sha1Hex} with
	 * cardCcvCode, if the value does not match throws an ServiceException.
	 * 
	 * @param userCcvCode {@link String} to be validated
	 * @param cardCcvCode {@link String} to be validated
	 * @throws ServiceException if userCcvCode converted to
	 *                          {@link DigestUtils.sha1Hex} not equals to
	 *                          cardCcvCode
	 */
	public void compareCcvCodes(String userCcvCode, String cardCcvCode) throws ServiceException {
		if (userCcvCode == null || !sha1Hex(userCcvCode).equals(cardCcvCode)) {
			throw new ServiceException("Wrong ccv code *Status1022*");
		}
	}

	/**
	 * @param card {@link Card} to be validated
	 * @return <code>true</code> if {@link Card#getExpirationDate()} before current
	 *         date
	 */
	public boolean isExpiredDateCard(Card card) {
		return card != null && card.getValidUntilDate() != null && card.getValidUntilDate().before(new Date());
	}

	/**
	 * @param card {@link Card} to be validated
	 * @throws ServiceException if {@link Card#getExpirationDate()} before current
	 *                          date
	 */
	public void checkIsCardCanBeUnblocked(Card card) throws ServiceException {
		if (isExpiredDateCard(card)) {
			throw new ServiceException("Unable unlock expired card *Status1028*");
		}
	}

	/**
	 * @param cardNumber {@link String} to be validated
	 * @throws ServiceException if cardNumber null or cardNumber length not equals
	 *                          to {@link CardValidator#VALID_CARD_NUMBER_LENGTH}
	 */
	public void checkIsCardNumberLengthValid(String cardNumber) throws ServiceException {
		if (cardNumber == null || cardNumber.length() != VALID_CARD_NUMBER_LENGTH) {
			throw new ServiceException("Invalid card number length");
		}
	}
}
