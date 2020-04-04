package by.training.payment.entity;

import java.util.Date;

public class UserRequest {

	private int id;
	private User user;
	private RequestType requestType;
	private RequestStatus requestStatus;
	private Date creationDate;
	private Date handlingDate;

	public UserRequest() {
	}

	public UserRequest(int id) {
		this.id = id;
	}

	public UserRequest(RequestType requestType) {
		this.requestType = requestType;
	}

	public UserRequest(User user, RequestType requestType) {
		this.user = user;
		this.requestType = requestType;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public RequestType getRequestType() {
		return requestType;
	}

	public void setRequestType(RequestType requestType) {
		this.requestType = requestType;
	}

	public RequestStatus getRequestStatus() {
		return requestStatus;
	}

	public void setRequestStatus(RequestStatus requestStatus) {
		this.requestStatus = requestStatus;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public Date getHandlingDate() {
		return handlingDate;
	}

	public void setHandlingDate(Date handlingDate) {
		this.handlingDate = handlingDate;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((creationDate == null) ? 0 : creationDate.hashCode());
		result = prime * result + ((handlingDate == null) ? 0 : handlingDate.hashCode());
		result = prime * result + id;
		result = prime * result + ((requestStatus == null) ? 0 : requestStatus.hashCode());
		result = prime * result + ((requestType == null) ? 0 : requestType.hashCode());
		result = prime * result + ((user == null) ? 0 : user.hashCode());
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
		UserRequest other = (UserRequest) obj;
		if (creationDate == null) {
			if (other.creationDate != null)
				return false;
		} else if (!creationDate.equals(other.creationDate))
			return false;
		if (handlingDate == null) {
			if (other.handlingDate != null)
				return false;
		} else if (!handlingDate.equals(other.handlingDate))
			return false;
		if (id != other.id)
			return false;
		if (requestStatus == null) {
			if (other.requestStatus != null)
				return false;
		} else if (!requestStatus.equals(other.requestStatus))
			return false;
		if (requestType == null) {
			if (other.requestType != null)
				return false;
		} else if (!requestType.equals(other.requestType))
			return false;
		if (user == null) {
			if (other.user != null)
				return false;
		} else if (!user.equals(other.user))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "UserRequest [id=" + id + ", user=" + user + ", requestType=" + requestType + ", requestStatus="
				+ requestStatus + ", creationDate=" + creationDate + ", handlingDate=" + handlingDate + "]";
	}

}
