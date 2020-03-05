package by.training.payment.entity;

import java.math.BigDecimal;
import java.util.Date;

public class Currency {

	private String name;
	private BigDecimal rate;
	private int multiplier;
	private Date updateDate;
	private boolean isBaseCurrency;

	public Currency() {

	}

	public Currency(String name, BigDecimal rate, int multiplier, Date updateDate, boolean isBaseCurrency) {
		this.name = name;
		this.rate = rate;
		this.multiplier = multiplier;
		this.updateDate = updateDate;
		this.isBaseCurrency = isBaseCurrency;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public BigDecimal getRate() {
		return rate;
	}

	public void setRate(BigDecimal rate) {
		this.rate = rate;
	}

	public int getMultiplier() {
		return multiplier;
	}

	public void setMultiplier(int multiplier) {
		this.multiplier = multiplier;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public boolean isBaseCurrency() {
		return isBaseCurrency;
	}

	public void setBaseCurrency(boolean isBaseCurrency) {
		this.isBaseCurrency = isBaseCurrency;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (isBaseCurrency ? 1231 : 1237);
		result = prime * result + multiplier;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((rate == null) ? 0 : rate.hashCode());
		result = prime * result + ((updateDate == null) ? 0 : updateDate.hashCode());
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
		Currency other = (Currency) obj;
		if (isBaseCurrency != other.isBaseCurrency)
			return false;
		if (multiplier != other.multiplier)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (rate == null) {
			if (other.rate != null)
				return false;
		} else if (!rate.equals(other.rate))
			return false;
		if (updateDate == null) {
			if (other.updateDate != null)
				return false;
		} else if (!updateDate.equals(other.updateDate))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Currency [name=" + name + ", rate=" + rate + ", multiplier=" + multiplier + ", updateDate=" + updateDate
				+ ", isBaseCurrency=" + isBaseCurrency + "]";
	}

}
