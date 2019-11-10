package bg.tu.varna.si.chat.server;

import bg.tu.varna.si.chat.model.User;
import bg.tu.varna.si.chat.model.request.UserRegisterRequest;
import bg.tu.varna.si.chat.server.db.entity.UserEntity;


public class UserConverter {
	
	public static UserEntity createUserData(UserRegisterRequest request) {
		UserEntity data = new UserEntity();
		
		data.setDisplayName(request.getDisplayName());
		data.setFirstName(request.getFirstName());
		data.setLastName(request.getLastName());
		data.setUserName(request.getUserName());
		data.setPassword(request.getPassword());
		
		return data;
	}
	
	public static User createUser(UserEntity userData) {
		User user = new User();
		
		user.setUserName(userData.getUserName());
		user.setDisplayName(userData.getDisplayName());
		
		return user;
	}
}

