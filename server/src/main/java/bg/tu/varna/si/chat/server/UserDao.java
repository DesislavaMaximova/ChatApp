package bg.tu.varna.si.chat.server;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class UserDao {

	private static Map<String, UserData> users = createUsers();

	public Collection<UserData> getAllUsers() {
		return users.values();
	}
	
	public UserData getUserData(String username) {
		return users.get(username);
	}

	public void addUserData(UserData user) {
		users.put(user.getUserName(), user);
	}

	public static Map<String, UserData> createUsers() {
		UserData user1 = new UserData("Popi", "Loo", "Smith", "Popi", "111");
		UserData user2 = new UserData("PPP", "Doo", "Boo", "PPP", "222");
		UserData user3 = new UserData("EEE", "Dee", "Fee", "Fee", "333");

		Map<String, UserData> users = new HashMap<String, UserData>();

		users.put(user1.getUserName(), user1);
		users.put(user2.getUserName(), user2);
		users.put(user3.getUserName(), user3);

		return users;
	}

}
