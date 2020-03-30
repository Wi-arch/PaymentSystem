package by.training.payment.entity;

import java.math.BigDecimal;
import java.util.Date;

public class Currency {

	private int id;
	private BigDecimal rate;
	private int scale;
	private String name;
	private Date updateDate;
	private boolean isBaseCurrency;

	public Currency() {

	}

	public Currency(int id) {
		this.id = id;
	}

	public Currency(int id, BigDecimal rate, int scale, String name, Date updateDate, boolean isBaseCurrency) {
		this.id = id;
		this.rate = rate;
		this.scale = scale;
		this.name = name;
		this.updateDate = updateDate;
		this.isBaseCurrency = isBaseCurrency;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public BigDecimal getRate() {
		return rate;
	}

	public void setRate(BigDecimal rate) {
		this.rate = rate;
	}

	public int getScale() {
		return scale;
	}

	public void setScale(int scale) {
		this.scale = scale;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}
	
	public boolean getIsBaseCurrency() {
		return isBaseCurrency;
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
		result = prime * result + id;
		result = prime * result + (isBaseCurrency ? 1231 : 1237);
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((rate == null) ? 0 : rate.hashCode());
		result = prime * result + scale;
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
		if (id != other.id)
			return false;
		if (isBaseCurrency != other.isBaseCurrency)
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
		if (scale != other.scale)
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
		return "Currency [id=" + id + ", rate=" + rate + ", scale=" + scale + ", name=" + name + ", updateDate="
				+ updateDate + ", isBaseCurrency=" + isBaseCurrency + "]";
	}
}
