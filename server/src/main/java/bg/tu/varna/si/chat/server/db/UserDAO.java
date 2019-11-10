package bg.tu.varna.si.chat.server.db;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import bg.tu.varna.si.chat.server.db.entity.UserEntity;

public class UserDAO {

	private static Map<String, UserEntity> users = createUsers();
	
	private static UserDAO INSTANCE_HOLDER;
	
	private UserDAO() {
		
	}
	
	public static UserDAO getInstance() {
		if (INSTANCE_HOLDER == null) {
			INSTANCE_HOLDER = new UserDAO();
		}
		
		return INSTANCE_HOLDER;
	}

	public Collection<UserEntity> getAllUsers() {
		return users.values();
	}
	
	public UserEntity getUserEntity(String username) {
		return users.get(username);
	}

	public void addUserEntity(UserEntity userEntity) {
		users.put(userEntity.getUserName(), userEntity);
	}

	public static Map<String, UserEntity> createUsers() {
		UserEntity user1 = new UserEntity("user1", "Loo", "Smith", "Popi", "111");
		UserEntity user2 = new UserEntity("user2", "Doo", "Boo", "PPP", "222");
		UserEntity user3 = new UserEntity("user3", "Dee", "Fee", "Fee", "333");

		Map<String, UserEntity> users = new HashMap<String, UserEntity>();

		users.put(user1.getUserName(), user1);
		users.put(user2.getUserName(), user2);
		users.put(user3.getUserName(), user3);

		return users;
	}

}
