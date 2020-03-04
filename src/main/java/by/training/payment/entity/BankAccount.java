package by.training.payment.entity;

import java.math.BigDecimal;
import java.util.Date;

public class BankAccount {

	private int bankAccountId;
	private String accountNumber;
	private Date openingDate;
	private BigDecimal balance;
	private String currency;
	private int holderId;
	private boolean isBlocked;

	public BankAccount() {

	}

	public BankAccount(int bankAccountId, String accountNumber, Date openingDate, BigDecimal balance, String currency,
			int holderId, boolean isBlocked) {
		this.bankAccountId = bankAccountId;
		this.accountNumber = accountNumber;
		this.openingDate = openingDate;
		this.balance = balance;
		this.currency = currency;
		this.holderId = holderId;
		this.isBlocked = isBlocked;
	}

	public int getBankAccountId() {
		return bankAccountId;
	}

	public void setBankAccountId(int bankAccountId) {
		this.bankAccountId = bankAccountId;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public Date getOpeningDate() {
		return openingDate;
	}

	public void setOpeningDate(Date openingDate) {
		this.openingDate = openingDate;
	}

	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
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
		result = prime * result + ((accountNumber == null) ? 0 : accountNumber.hashCode());
		result = prime * result + ((balance == null) ? 0 : balance.hashCode());
		result = prime * result + bankAccountId;
		result = prime * result + ((currency == null) ? 0 : currency.hashCode());
		result = prime * result + holderId;
		result = prime * result + (isBlocked ? 1231 : 1237);
		result = prime * result + ((openingDate == null) ? 0 : openingDate.hashCode());
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
		BankAccount other = (BankAccount) obj;
		if (accountNumber == null) {
			if (other.accountNumber != null)
				return false;
		} else if (!accountNumber.equals(other.accountNumber))
			return false;
		if (balance == null) {
			if (other.balance != null)
				return false;
		} else if (!balance.equals(other.balance))
			return false;
		if (bankAccountId != other.bankAccountId)
			return false;
		if (currency == null) {
			if (other.currency != null)
				return false;
		} else if (!currency.equals(other.currency))
			return false;
		if (holderId != other.holderId)
			return false;
		if (isBlocked != other.isBlocked)
			return false;
		if (openingDate == null) {
			if (other.openingDate != null)
				return false;
		} else if (!openingDate.equals(other.openingDate))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "BankAccount [bankAccountId=" + bankAccountId + ", accountNumber=" + accountNumber + ", openingDate="
				+ openingDate + ", balance=" + balance + ", currency=" + currency + ", holderId=" + holderId
				+ ", isBlocked=" + isBlocked + "]";
	}

}
