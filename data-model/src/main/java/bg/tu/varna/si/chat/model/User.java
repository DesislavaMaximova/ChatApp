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

	public static long getSerialversionuid() {
		return serialVersionUID;
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

	@Override
	public String toString() {
		return this.displayName;
	}
	
}
