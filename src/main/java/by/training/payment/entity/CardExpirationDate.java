package by.training.payment.entity;

public class CardExpirationDate {

	private int id;
	private int value;

	public CardExpirationDate() {
	}

	public CardExpirationDate(int id) {
		this.id = id;
	}

	public CardExpirationDate(int id, int value) {
		this.id = id;
		this.value = value;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		result = prime * result + value;
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
		CardExpirationDate other = (CardExpirationDate) obj;
		if (id != other.id)
			return false;
		if (value != other.value)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "CardExpirationDate [id=" + id + ", value=" + value + "]";
	}

}
