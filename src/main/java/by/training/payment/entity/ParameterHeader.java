package by.training.payment.entity;

public class ParameterHeader {

	private String name;

	public ParameterHeader() {

	}

	public ParameterHeader(String name) {
		this.name = name;
	}

	public ParameterHeader(ParameterHeader parameterHeader) {
		this.name = parameterHeader.name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		ParameterHeader other = (ParameterHeader) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ParameterHeader [name=" + name + "]";
	}

}
