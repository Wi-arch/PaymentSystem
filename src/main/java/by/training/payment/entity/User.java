package by.training.payment.entity;

import java.util.Arrays;

public class User {

	private int userId;
	private String login;
	private char[] password;
	private String email;
	private int userRole;
	private String name;
	private String surName;
	private boolean isBlocked;

	public User() {

	}

	public User(int userId, String login, char[] password, String email, int userRole, String name, String surName,
			boolean isBlocked) {
		this.userId = userId;
		this.login = login;
		this.password = password;
		this.email = email;
		this.userRole = userRole;
		this.name = name;
		this.surName = surName;
		this.isBlocked = isBlocked;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public char[] getPassword() {
		return password;
	}

	public void setPassword(char[] password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getUserRole() {
		return userRole;
	}

	public void setUserRole(int userRole) {
		this.userRole = userRole;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurName() {
		return surName;
	}

	public void setSurName(String surName) {
		this.surName = surName;
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
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + (isBlocked ? 1231 : 1237);
		result = prime * result + ((login == null) ? 0 : login.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + Arrays.hashCode(password);
		result = prime * result + ((surName == null) ? 0 : surName.hashCode());
		result = prime * result + userId;
		result = prime * result + userRole;
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
		User other = (User) obj;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (isBlocked != other.isBlocked)
			return false;
		if (login == null) {
			if (other.login != null)
				return false;
		} else if (!login.equals(other.login))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (!Arrays.equals(password, other.password))
			return false;
		if (surName == null) {
			if (other.surName != null)
				return false;
		} else if (!surName.equals(other.surName))
			return false;
		if (userId != other.userId)
			return false;
		if (userRole != other.userRole)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "User [userId=" + userId + ", login=" + login + ", password=" + Arrays.toString(password) + ", email="
				+ email + ", userRole=" + userRole + ", name=" + name + ", surName=" + surName + ", isBlocked="
				+ isBlocked + "]";
	}
}