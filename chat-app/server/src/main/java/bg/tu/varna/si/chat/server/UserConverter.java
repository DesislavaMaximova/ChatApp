package bg.tu.varna.si.chat.server;

import bg.tu.varna.si.chat.model.User;
import bg.tu.varna.si.chat.model.request.UserRegisterRequest;


public class UserConverter {
	
	public static UserData createUserData(UserRegisterRequest request) {
		UserData data = new UserData();
		
		data.setDisplayName(request.getDisplayName());
		data.setFirstName(request.getFirstName());
		data.setLastName(request.getLastName());
		data.setUserName(request.getUserName());
		data.setPassword(request.getPassword());
		
		return data;
	}
	
	public static User createUser(UserData userData) {
		User user = new User();
		
		user.setUserName(userData.getUserName());
		user.setDisplayName(userData.getDisplayName());
		
		return user;
	}
}

