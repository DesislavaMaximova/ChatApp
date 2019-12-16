package bg.tu.varna.si.chat.client;

import java.util.Collection;

import bg.tu.varna.si.chat.model.User;

public class UsersRegistry {

	private static UsersRegistry INSTANCE_HOLDER;

	private Collection<User> users;

	private User currentUser;

	private UsersRegistry() {

	}

	public static UsersRegistry getInstance() {
		if (INSTANCE_HOLDER == null) {
			INSTANCE_HOLDER = new UsersRegistry();
		}

		return INSTANCE_HOLDER;
	}

	public Collection<User> getUsers() {
		return users;
	}

	public void setUsers(Collection<User> users) {
		this.users = users;
	}

	public User getCurrentUser() {
		return currentUser;
	}

	public void setCurrentUser(User currentUser) {
		this.currentUser = currentUser;
	}

}
