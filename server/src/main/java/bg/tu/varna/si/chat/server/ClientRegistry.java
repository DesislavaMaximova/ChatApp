package bg.tu.varna.si.chat.server;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import bg.tu.varna.si.chat.model.User;
import bg.tu.varna.si.chat.model.request.StatusUpdateRequest;
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
		sendStatusUpdateNotification(username, true);
	}

	public void logUserOut(String userName) {
		if (userName != null) {
			clientHandlers.remove(userName);
			sendStatusUpdateNotification(userName, false);
		}
	}

	private void sendStatusUpdateNotification(String username, boolean online) {
		UserEntity userEntity = UserDAO.getInstance().getUserEntity(username);

		User updatedUser = new User(username, online, userEntity.getDisplayName());

		for (Map.Entry<String, ClientHandler> entry : clientHandlers.entrySet()) {
			String name = entry.getKey();
			if (name != username) {
				try {
					entry.getValue().sendRequest(new StatusUpdateRequest(updatedUser));
				} catch (Exception e) {
					System.err.println("Failed sending status update request");
				}
			}
		}
	}

	public Collection<User> getUsers(String username) {
		List<User> users = new LinkedList<User>();

		for (UserEntity data : UserDAO.getInstance().getAllOtherUsers(username)) {
			if (clientHandlers.containsKey(data.getUserName())) {
				users.add(new User(data.getUserName(), true, data.getDisplayName()));
			} else {
				users.add(new User(data.getUserName(), false, data.getDisplayName()));
			}
		}

		return users;
	}

}