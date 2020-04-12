package by.training.payment.validator;

import static org.apache.commons.codec.digest.DigestUtils.sha1Hex;

import java.math.BigDecimal;
import java.util.Date;

import by.training.payment.entity.Card;
import by.training.payment.exception.ServiceException;

public class CardValidator {

	private static final int ONE = 1;
	private static final int VALID_CARD_NUMBER_LENGTH = 16;

	public void checkIsAmountPositive(BigDecimal amount) throws ServiceException {
		if (amount == null || amount.compareTo(BigDecimal.ZERO) < ONE) {
			throw new ServiceException("Amount must be greater than zero *Status1019*");
		}
	}

	public void compareSenderAndReceiverCardNumber(String senderNumber, String receiverNumber) throws ServiceException {
		if (senderNumber != null && senderNumber.equals(receiverNumber)) {
			throw new ServiceException("Same sender and receiver card numbers *Status1025*");
		}
	}

	public void checkIsCardBlocked(Card card) throws ServiceException {
		if (card == null || card.getIsBlocked()) {
			throw new ServiceException("Card is blocked *Status1020*");
		}
	}

	public void checkCardNumberForNull(Card card) throws ServiceException {
		if (card == null || card.getNumber() == null) {
			throw new ServiceException("Null card number *Status1024*");
		}
	}

	public void checkIsPaymentPurposeEmpty(String paymentPurpose) throws ServiceException {
		if (paymentPurpose == null || paymentPurpose.isEmpty()) {
			throw new ServiceException("Null payment purpose");
		}
	}

	public void compareCcvCodes(String userCcvCode, String cardCcvCode) throws ServiceException {
		if (userCcvCode == null || !sha1Hex(userCcvCode).equals(cardCcvCode)) {
			throw new ServiceException("Wrong ccv code *Status1022*");
		}
	}

	public boolean isExpiredDateCard(Card card) {
		return card != null && card.getValidUntilDate() != null && card.getValidUntilDate().before(new Date());
	}

	public void checkIsCardCanBeUnblocked(Card card) throws ServiceException {
		if (isExpiredDateCard(card)) {
			throw new ServiceException("Unable unlock expired card *Status1028*");
		}
	}

	public void checkIsCardNumberLengthValid(String cardNumber) throws ServiceException {
		if (cardNumber == null || cardNumber.length() != VALID_CARD_NUMBER_LENGTH) {
			throw new ServiceException("Invalid card number length");
		}
	}
}
