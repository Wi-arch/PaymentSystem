package by.training.payment.entity;

import java.util.Arrays;
import java.util.Date;

public class Card {

	private int cardId;
	private Date openingDate;
	private Date validUntilDate;
	private char[] number;
	private String numberMask;
	private char[] ccv;
	private int expirationDate;
	private int paymentSystem;
	private int accountId;
	private int holderId;
	private boolean isBlocked;

	public Card() {
		
	}

	public Card(int cardId, Date openingDate, Date validUntilDate, char[] number, String numberMask, char[] ccv,
			int expirationDate, int paymentSystem, int accountId, int holderId, boolean isBlocked) {
		this.cardId = cardId;
		this.openingDate = openingDate;
		this.validUntilDate = validUntilDate;
		this.number = number;
		this.numberMask = numberMask;
		this.ccv = ccv;
		this.expirationDate = expirationDate;
		this.paymentSystem = paymentSystem;
		this.accountId = accountId;
		this.holderId = holderId;
		this.isBlocked = isBlocked;
	}

	public int getCardId() {
		return cardId;
	}

	public void setCardId(int cardId) {
		this.cardId = cardId;
	}

	public Date getOpeningDate() {
		return openingDate;
	}

	public void setOpeningDate(Date openingDate) {
		this.openingDate = openingDate;
	}

	public Date getValidUntilDate() {
		return validUntilDate;
	}

	public void setValidUntilDate(Date validUntilDate) {
		this.validUntilDate = validUntilDate;
	}

	public char[] getNumber() {
		return number;
	}

	public void setNumber(char[] number) {
		this.number = number;
	}

	public String getNumberMask() {
		return numberMask;
	}

	public void setNumberMask(String numberMask) {
		this.numberMask = numberMask;
	}

	public char[] getCcv() {
		return ccv;
	}

	public void setCcv(char[] ccv) {
		this.ccv = ccv;
	}

	public int getExpirationDate() {
		return expirationDate;
	}

	public void setExpirationDate(int expirationDate) {
		this.expirationDate = expirationDate;
	}

	public int getPaymentSystem() {
		return paymentSystem;
	}

	public void setPaymentSystem(int paymentSystem) {
		this.paymentSystem = paymentSystem;
	}

	public int getAccountId() {
		return accountId;
	}

	public void setAccountId(int accountId) {
		this.accountId = accountId;
	}

	public int getHolderId() {
		return holderId;
	}

	public void setHolderId(int holderId) {
		this.holderId = holderId;
	}

	public boolean isBlocked() {
		return isBlocked;
	}

	public void setBlocked(boolean isBlocked) {
		this.isBlocked = isBlocked;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + accountId;
		result = prime * result + cardId;
		result = prime * result + Arrays.hashCode(ccv);
		result = prime * result + expirationDate;
		result = prime * result + holderId;
		result = prime * result + (isBlocked ? 1231 : 1237);
		result = prime * result + Arrays.hashCode(number);
		result = prime * result + ((numberMask == null) ? 0 : numberMask.hashCode());
		result = prime * result + ((openingDate == null) ? 0 : openingDate.hashCode());
		result = prime * result + paymentSystem;
		result = prime * result + ((validUntilDate == null) ? 0 : validUntilDate.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Card other = (Card) obj;
		if (accountId != other.accountId)
			return false;
		if (cardId != other.cardId)
			return false;
		if (!Arrays.equals(ccv, other.ccv))
			return false;
		if (expirationDate != other.expirationDate)
			return false;
		if (holderId != other.holderId)
			return false;
		if (isBlocked != other.isBlocked)
			return false;
		if (!Arrays.equals(number, other.number))
			return false;
		if (numberMask == null) {
			if (other.numberMask != null)
				return false;
		} else if (!numberMask.equals(other.numberMask))
			return false;
		if (openingDate == null) {
			if (other.openingDate != null)
				return false;
		} else if (!openingDate.equals(other.openingDate))
			return false;
		if (paymentSystem != other.paymentSystem)
			return false;
		if (validUntilDate == null) {
			if (other.validUntilDate != null)
				return false;
		} else if (!validUntilDate.equals(other.validUntilDate))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Card [cardId=" + cardId + ", openingDate=" + openingDate + ", validUntilDate=" + validUntilDate
				+ ", number=" + Arrays.toString(number) + ", numberMask=" + numberMask + ", ccv=" + Arrays.toString(ccv)
				+ ", expirationDate=" + expirationDate + ", paymentSystem=" + paymentSystem + ", accountId=" + accountId
				+ ", holderId=" + holderId + ", isBlocked=" + isBlocked + "]";
	}

}
