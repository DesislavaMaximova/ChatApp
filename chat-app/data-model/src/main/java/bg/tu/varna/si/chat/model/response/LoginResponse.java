package bg.tu.varna.si.chat.model.response;

import java.util.Collection;

import bg.tu.varna.si.chat.model.User;

public class LoginResponse extends Response {

	private static final long serialVersionUID = 5336694067920377728L;

	private User currentUser;
	
	private Collection<User> activeUsers;
	
	private Collection<User> inactiveUsers;
	
	public LoginResponse(User currentUser, Collection<User> activeUsers, Collection<User> inactiveUsers) {
		super(ResponseType.LOGIN);
		this.currentUser = currentUser;
		this.activeUsers = activeUsers;
		this.inactiveUsers = inactiveUsers;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public User getCurrentUser() {
		return currentUser;
	}

	public Collection<User> getActiveUsers() {
		return activeUsers;
	}

	public Collection<User> getInactiveUsers() {
		return inactiveUsers;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("LoginResponse [currentUser=");
		builder.append(currentUser);
		builder.append(", activeUsers=");
		builder.append(activeUsers);
		builder.append(", inactiveUsers=");
		builder.append(inactiveUsers);
		builder.append("]");
		return builder.toString();
	}

}
