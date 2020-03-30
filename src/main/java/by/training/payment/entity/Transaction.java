package by.training.payment.entity;

import java.math.BigDecimal;
import java.util.Date;

public class Transaction {

	private int id;
	private boolean isCompleted;
	private Date date;
	private boolean isWriteOff;
	private BigDecimal amount;
	private Currency currency;
	private String paymentPurpose;
	private BankAccount account;

	public Transaction() {

	}

	public Transaction(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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

	public Currency getCurrency() {
		return currency;
	}

	public void setCurrency(Currency currency) {
		this.currency = currency;
	}

	public String getPaymentPurpose() {
		return paymentPurpose;
	}

	public void setPaymentPurpose(String paymentPurpose) {
		this.paymentPurpose = paymentPurpose;
	}

	public BankAccount getAccount() {
		return account;
	}

	public void setAccount(BankAccount account) {
		this.account = account;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((account == null) ? 0 : account.hashCode());
		result = prime * result + ((amount == null) ? 0 : amount.hashCode());
		result = prime * result + ((currency == null) ? 0 : currency.hashCode());
		result = prime * result + ((date == null) ? 0 : date.hashCode());
		result = prime * result + id;
		result = prime * result + (isCompleted ? 1231 : 1237);
		result = prime * result + (isWriteOff ? 1231 : 1237);
		result = prime * result + ((paymentPurpose == null) ? 0 : paymentPurpose.hashCode());
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
		if (account == null) {
			if (other.account != null)
				return false;
		} else if (!account.equals(other.account))
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
		if (id != other.id)
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
		return true;
	}

	@Override
	public String toString() {
		return "Transaction [id=" + id + ", isCompleted=" + isCompleted + ", date=" + date + ", isWriteOff="
				+ isWriteOff + ", amount=" + amount + ", currency=" + currency + ", paymentPurpose=" + paymentPurpose
				+ ", account=" + account + "]";
	}

}
