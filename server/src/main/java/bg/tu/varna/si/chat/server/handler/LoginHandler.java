package bg.tu.varna.si.chat.server.handler;

import bg.tu.varna.si.chat.model.User;
import bg.tu.varna.si.chat.model.request.LoginRequest;
import bg.tu.varna.si.chat.model.request.Request;
import bg.tu.varna.si.chat.model.response.ErrorResponse;
import bg.tu.varna.si.chat.model.response.ErrorType;
import bg.tu.varna.si.chat.model.response.LoginResponse;
import bg.tu.varna.si.chat.model.response.Response;
import bg.tu.varna.si.chat.server.ClientRegistry;
import bg.tu.varna.si.chat.server.UserConverter;
import bg.tu.varna.si.chat.server.db.UserDAO;
import bg.tu.varna.si.chat.server.db.entity.UserEntity;

public class LoginHandler extends RequestHandler {
	
	//	private Response processLogin(Login request) {
	//
	//		Response response = null;
	//
	//		// 1. get user from UserDao by username
	//		UserEntity userData = UserDAO.getInstance().getUserEntity(request.getCredentials().getUserName());
	//
	//		// 1.1. if user == null return ErrorResponse WRONG_USERNAME_OR_PASSWORD
	//		if (userData == null) {
	//			return new ErrorResponse(ErrorType.WRONG_USERNAME_OR_PASSWORD, "Wrong username or password!");
	//		}
	//
	//		// 1.2. if user.getPassword != request.getPassword return ErrorReponse
	//		if (!userData.getPassword().equals(request.getCredentials().getPassword())) {
	//			return new ErrorResponse(ErrorType.WRONG_USERNAME_OR_PASSWORD, "Wrong username or password!");
	//		}
	//
	//		// 2. Register this ClientHandler in ClientRegistry
	//		User loggedUser = UserConverter.createUser(userData);
	//		this.userName = loggedUser.getUserName();
	//
	//		ClientRegistry.getInstance().logUserIn(userName, this);
	//
	//		// 3. return LoginResponse (currentUser, activeUsers, inactiveUsers)
	//		response = new LoginResponse(loggedUser, ClientRegistry.getInstance().getActiveUsers(),
	//				ClientRegistry.getInstance().getInactiveUsers());
	//
	//		// TODO :
	//		// 4. Send message to all activeUsers
	//
	//		return response;
	//	}
	
	@Override
	public Response handle(Request request) {
		
		LoginRequest loginRequest = (LoginRequest) request;
		
		// 1. get user from UserDao by username
		UserEntity userData = UserDAO.getInstance().getUserEntity(loginRequest.getCredentials().getUserName());

		// 1.1. if user == null return ErrorResponse WRONG_USERNAME_OR_PASSWORD
		if (userData == null) {
			return new ErrorResponse(ErrorType.WRONG_USERNAME_OR_PASSWORD, "Wrong username or password!");
		}

		// 1.2. if user.getPassword != request.getPassword return ErrorReponse
		if (!userData.getPassword().equals(loginRequest.getCredentials().getPassword())) {
			return new ErrorResponse(ErrorType.WRONG_USERNAME_OR_PASSWORD, "Wrong username or password!");
		}

		// 2. Register this ClientHandler in ClientRegistry
		User loggedUser = UserConverter.createUser(userData);

		// 3. return LoginResponse (currentUser, activeUsers, inactiveUsers)
		Response response = new LoginResponse(loggedUser, ClientRegistry.getInstance().getActiveUsers(),
				ClientRegistry.getInstance().getInactiveUsers());

		return response;
	}

}
