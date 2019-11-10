package bg.tu.varna.si.chat.server.db.entity;

public class UserEntity {

	private int ID;

	private String userName;

	private String firstName;

	private String lastName;

	private String displayName;

	private String password;

	public UserEntity(
			String userName, 
			String firstName, 
			String lastName, 
			String displayName, 
			String password) {
		this.userName = userName;
		this.firstName = firstName;
		this.lastName = lastName;
		this.displayName = displayName;
		this.password = password;
	}

	public UserEntity() { 

	}

	public int getID() {
		return ID;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
