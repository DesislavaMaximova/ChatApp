package bg.tu.varna.si.chat.model;

import bg.tu.varna.si.chat.model.request.RecipientType;

public class User extends Recipient {

	private static final long serialVersionUID = 1L;

	private String userName;
	
	private boolean isActive;

	private String displayName;

	public User(String userName, boolean isActive, String displayName) {
		super(RecipientType.USER);
		this.userName = userName;
		this.isActive = isActive;
		this.displayName = displayName;
	}

	public String getUserName() {
		return userName;
	}

	public boolean isActive() {
		return isActive;
	}

	public String getDisplayName() {
		return displayName;
	}
	
	public void setUserName(String userName) {
		this.userName = userName;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	@Override
	public String toString() {
		return this.displayName;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((userName == null) ? 0 : userName.hashCode());
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
		if (userName == null) {
			if (other.userName != null)
				return false;
		} else if (!userName.equals(other.userName))
			return false;
		return true;
	}
	
	
}
