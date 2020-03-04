package by.training.payment.entity;

import java.math.BigDecimal;
import java.util.Date;

public class Transaction {

	private int transactionId;
	private boolean isCompleted;
	private Date date;
	private boolean isWriteOff;
	private BigDecimal amount;
	private String currency;
	private String paymentPurpose;
	private int accountId;

	public Transaction() {

	}

	public Transaction(int transactionId, boolean isCompleted, Date date, boolean isWriteOff, BigDecimal amount,
			String currency, String paymentPurpose, int accountId) {
		this.transactionId = transactionId;
		this.isCompleted = isCompleted;
		this.date = date;
		this.isWriteOff = isWriteOff;
		this.amount = amount;
		this.currency = currency;
		this.paymentPurpose = paymentPurpose;
		this.accountId = accountId;
	}

	public int getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(int transactionId) {
		this.transactionId = transactionId;
	}

	public boolean isCompleted() {
		return isCompleted;
	}

	public void setCompleted(boolean isCompleted) {
		this.isCompleted = isCompleted;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public boolean isWriteOff() {
		return isWriteOff;
	}

	public void setWriteOff(boolean isWriteOff) {
		this.isWriteOff = isWriteOff;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getPaymentPurpose() {
		return paymentPurpose;
	}

	public void setPaymentPurpose(String paymentPurpose) {
		this.paymentPurpose = paymentPurpose;
	}

	public int getAccountId() {
		return accountId;
	}

	public void setAccountId(int accountId) {
		this.accountId = accountId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + accountId;
		result = prime * result + ((amount == null) ? 0 : amount.hashCode());
		result = prime * result + ((currency == null) ? 0 : currency.hashCode());
		result = prime * result + ((date == null) ? 0 : date.hashCode());
		result = prime * result + (isCompleted ? 1231 : 1237);
		result = prime * result + (isWriteOff ? 1231 : 1237);
		result = prime * result + ((paymentPurpose == null) ? 0 : paymentPurpose.hashCode());
		result = prime * result + transactionId;
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
		Transaction other = (Transaction) obj;
		if (accountId != other.accountId)
			return false;
		if (amount == null) {
			if (other.amount != null)
				return false;
		} else if (!amount.equals(other.amount))
			return false;
		if (currency == null) {
			if (other.currency != null)
				return false;
		} else if (!currency.equals(other.currency))
			return false;
		if (date == null) {
			if (other.date != null)
				return false;
		} else if (!date.equals(other.date))
			return false;
		if (isCompleted != other.isCompleted)
			return false;
		if (isWriteOff != other.isWriteOff)
			return false;
		if (paymentPurpose == null) {
			if (other.paymentPurpose != null)
				return false;
		} else if (!paymentPurpose.equals(other.paymentPurpose))
			return false;
		if (transactionId != other.transactionId)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Transaction [transactionId=" + transactionId + ", isCompleted=" + isCompleted + ", date=" + date
				+ ", isWriteOff=" + isWriteOff + ", amount=" + amount + ", currency=" + currency + ", paymentPurpose="
				+ paymentPurpose + ", accountId=" + accountId + "]";
	}
}
