package bg.tu.varna.si.chat.model;

import java.io.Serializable;

public class Credentials implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private String userName;
	
	private String password;

	public Credentials(String userName, String password) {
		super();
		this.userName = userName;
		this.password = password;
	}

	public Credentials() {

	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Credentials [userName=");
		builder.append(userName);
		builder.append(", password=");
		builder.append(password);
		builder.append("]");
		return builder.toString();
	}
	
}
