package bg.tu.varna.si.chat.client;

import java.util.List;

import bg.tu.varna.si.chat.model.User;

public class UsersRegistry {

	private static UsersRegistry INSTANCE_HOLDER;

	private List<User> users;

	private User currentUser;
	
	private UsersRegistry() {

	}

	public static UsersRegistry getInstance() {
		if (INSTANCE_HOLDER == null) {
			INSTANCE_HOLDER = new UsersRegistry();
		}

		return INSTANCE_HOLDER;
	}

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

	public User getCurrentUser() {
		return currentUser;
	}

	public void setCurrentUser(User currentUser) {
		this.currentUser = currentUser;
	}
	
	public User getUser(String userName) {
		for (User user : users) {
			if (user.getUserName().equals(userName)) {
				return user;
			}
		}
		
		return null;
	}

}
