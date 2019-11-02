package bg.tu.varna.si.chat.server;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import bg.tu.varna.si.chat.model.User;

public class ClientRegistry {

	private Map<String, ClientHandler> clientHandlers = new HashMap<String, ClientHandler>();
	
	private UserDao userDao = new UserDao();

	private static ClientRegistry INSTANCE_HOLDER;
	
	private ClientRegistry() {

	}

	public static ClientRegistry getInstance() {
		if (INSTANCE_HOLDER == null) {
			INSTANCE_HOLDER = new ClientRegistry();
		}

		return INSTANCE_HOLDER;
	}

	public void logUserIn(String userName, ClientHandler clientHandler) {
		clientHandlers.put(userName, clientHandler);
	}

	public void logUserOut(String userName) {
		clientHandlers.remove(userName);
	}
	
	public Collection<User> getActiveUsers() {
		List<User> activeUsers = new LinkedList<User>();
		
		for (UserData data : userDao.getAllUsers()) {
			if (clientHandlers.containsKey(data.getUserName())) {
				activeUsers.add(new User(data.getUserName(), data.getDisplayName()));
			}
		}

		return activeUsers;
	}
	
	public Collection<User> getInactiveUsers() {
		List<User> inactiveUsers = new LinkedList<User>();
		
		for (UserData data : userDao.getAllUsers()) {
			if (!clientHandlers.containsKey(data.getUserName())) {
				inactiveUsers.add(new User(data.getUserName(), data.getDisplayName()));
			}
		}

		return inactiveUsers;

	}

}
