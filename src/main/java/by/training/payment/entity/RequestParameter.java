package by.training.payment.entity;

public class RequestParameter {

	private int id;
	private ParameterHeader parameterHeader;
	private UserRequest userRequest;
	private String value;

	public RequestParameter() {

	}

	public RequestParameter(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public ParameterHeader getParameterHeader() {
		return parameterHeader;
	}

	public void setParameterHeader(ParameterHeader parameterHeader) {
		this.parameterHeader = parameterHeader;
	}

	public UserRequest getUserRequest() {
		return userRequest;
	}

	public void setUserRequest(UserRequest userRequest) {
		this.userRequest = userRequest;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		result = prime * result + ((parameterHeader == null) ? 0 : parameterHeader.hashCode());
		result = prime * result + ((userRequest == null) ? 0 : userRequest.hashCode());
		result = prime * result + ((value == null) ? 0 : value.hashCode());
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
		RequestParameter other = (RequestParameter) obj;
		if (id != other.id)
			return false;
		if (parameterHeader == null) {
			if (other.parameterHeader != null)
				return false;
		} else if (!parameterHeader.equals(other.parameterHeader))
			return false;
		if (userRequest == null) {
			if (other.userRequest != null)
				return false;
		} else if (!userRequest.equals(other.userRequest))
			return false;
		if (value == null) {
			if (other.value != null)
				return false;
		} else if (!value.equals(other.value))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "RequestParameter [id=" + id + ", parameterHeader=" + parameterHeader + ", userRequest=" + userRequest
				+ ", value=" + value + "]";
	}
}
