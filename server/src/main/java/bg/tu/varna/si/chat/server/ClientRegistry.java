package bg.tu.varna.si.chat.server;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import bg.tu.varna.si.chat.model.User;
import bg.tu.varna.si.chat.server.db.UserDAO;
import bg.tu.varna.si.chat.server.db.entity.UserEntity;

public class ClientRegistry {

	private Map<String, ClientHandler> clientHandlers = new ConcurrentHashMap<String, ClientHandler>();
	
	private static ClientRegistry INSTANCE_HOLDER;
	
	private ClientRegistry() {

	}

	public static ClientRegistry getInstance() {
		if (INSTANCE_HOLDER == null) {
			INSTANCE_HOLDER = new ClientRegistry();
		}

		return INSTANCE_HOLDER;
	}
	
	public ClientHandler getClientHandler(String username) {
		return clientHandlers.get(username);
	}

	public void logUserIn(String username, ClientHandler clientHandler) {
		clientHandlers.put(username, clientHandler);
	}

	public void logUserOut(String userName) {
		clientHandlers.remove(userName);
	}
	
	public Collection<User> getActiveUsers() {
		List<User> activeUsers = new LinkedList<User>();
		
		for (UserEntity data : UserDAO.getInstance().getAllUsers()) {
			if (clientHandlers.containsKey(data.getUserName())) {
				activeUsers.add(new User(data.getUserName(), data.getDisplayName()));
			}
		}

		return activeUsers;
	}
	
	public Collection<User> getInactiveUsers() {
		List<User> inactiveUsers = new LinkedList<User>();
		
		for (UserEntity data : UserDAO.getInstance().getAllUsers()) {
			if (!clientHandlers.containsKey(data.getUserName())) {
				inactiveUsers.add(new User(data.getUserName(), data.getDisplayName()));
			}
		}

		return inactiveUsers;
	}

}
