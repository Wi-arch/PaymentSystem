package by.training.payment.entity;

import java.util.Date;

public class Card {

	private String number;
	private Date openingDate;
	private Date validUntilDate;
	private String numberMask;
	private String ccv;
	private CardExpiry expirationDate;
	private PaymentSystem paymentSystem;
	private BankAccount bankAccount;
	private User user;
	private boolean isBlocked;

	public Card() {
		
	}

	public Card(String number) {
		this.number = number;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
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

	public String getNumberMask() {
		return numberMask;
	}

	public void setNumberMask(String numberMask) {
		this.numberMask = numberMask;
	}

	public String getCcv() {
		return ccv;
	}

	public void setCcv(String ccv) {
		this.ccv = ccv;
	}

	public CardExpiry getExpirationDate() {
		return expirationDate;
	}

	public void setExpirationDate(CardExpiry expirationDate) {
		this.expirationDate = expirationDate;
	}

	public PaymentSystem getPaymentSystem() {
		return paymentSystem;
	}

	public void setPaymentSystem(PaymentSystem paymentSystem) {
		this.paymentSystem = paymentSystem;
	}

	public BankAccount getBankAccount() {
		return bankAccount;
	}

	public void setBankAccount(BankAccount bankAccount) {
		this.bankAccount = bankAccount;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public boolean getIsBlocked() {
		return isBlocked;
	}

	public void setBlocked(boolean isBlocked) {
		this.isBlocked = isBlocked;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((bankAccount == null) ? 0 : bankAccount.hashCode());
		result = prime * result + ((ccv == null) ? 0 : ccv.hashCode());
		result = prime * result + ((expirationDate == null) ? 0 : expirationDate.hashCode());
		result = prime * result + (isBlocked ? 1231 : 1237);
		result = prime * result + ((number == null) ? 0 : number.hashCode());
		result = prime * result + ((numberMask == null) ? 0 : numberMask.hashCode());
		result = prime * result + ((openingDate == null) ? 0 : openingDate.hashCode());
		result = prime * result + ((paymentSystem == null) ? 0 : paymentSystem.hashCode());
		result = prime * result + ((user == null) ? 0 : user.hashCode());
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
		if (bankAccount == null) {
			if (other.bankAccount != null)
				return false;
		} else if (!bankAccount.equals(other.bankAccount))
			return false;
		if (ccv == null) {
			if (other.ccv != null)
				return false;
		} else if (!ccv.equals(other.ccv))
			return false;
		if (expirationDate == null) {
			if (other.expirationDate != null)
				return false;
		} else if (!expirationDate.equals(other.expirationDate))
			return false;
		if (isBlocked != other.isBlocked)
			return false;
		if (number == null) {
			if (other.number != null)
				return false;
		} else if (!number.equals(other.number))
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
		if (paymentSystem == null) {
			if (other.paymentSystem != null)
				return false;
		} else if (!paymentSystem.equals(other.paymentSystem))
			return false;
		if (user == null) {
			if (other.user != null)
				return false;
		} else if (!user.equals(other.user))
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
		return "Card [number=" + number + ", openingDate=" + openingDate + ", validUntilDate=" + validUntilDate
				+ ", numberMask=" + numberMask + ", ccv=" + ccv + ", expirationDate=" + expirationDate
				+ ", paymentSystem=" + paymentSystem + ", bankAccount=" + bankAccount + ", user=" + user
				+ ", isBlocked=" + isBlocked + "]";
	}

}
