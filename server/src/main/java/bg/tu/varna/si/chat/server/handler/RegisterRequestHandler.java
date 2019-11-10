package bg.tu.varna.si.chat.server.handler;

import bg.tu.varna.si.chat.model.Credentials;
import bg.tu.varna.si.chat.model.request.LoginRequest;
import bg.tu.varna.si.chat.model.request.Request;
import bg.tu.varna.si.chat.model.request.UserRegisterRequest;
import bg.tu.varna.si.chat.model.response.ErrorType;
import bg.tu.varna.si.chat.model.response.Response;
import bg.tu.varna.si.chat.server.db.UserDAO;
import bg.tu.varna.si.chat.server.db.entity.UserEntity;
import bg.tu.varna.si.chat.server.exception.IllegalRequestException;

public class RegisterRequestHandler extends RequestHandler {

	//	private Response processRegistration(UserRegisterRequest request) {
	//
	//		// 1. check if user exists
	//		// 1.1. if user exists construct and return ErrorResponse
	//		UserEntity userData = UserDAO.getInstance().getUserEntity(request.getUserName());
	//		if (userData != null) {
	//			return new ErrorResponse(ErrorType.USERNAME_IS_UNAVAILABLE, "Username is alredy taken!");
	//
	//		}
	//
	//		// 2. Write new user to database
	//		userData = new UserEntity();
	//		userData.setDisplayName(request.getDisplayName());
	//		userData.setFirstName(request.getFirstName());
	//		userData.setLastName(request.getLastName());
	//		userData.setPassword(request.getPassword());
	//		userData.setUserName(request.getUserName());
	//
	//		UserDAO.getInstance().addUserEntity(userData);
	//
	//		return processLogin(new Login(new Credentials(request.getUserName(), request.getPassword())));
	//	}

	@Override
	public Response handle(Request request) {

		UserRegisterRequest registerRequest = (UserRegisterRequest) request;

		// 1. check if user exists
		// 1.1. if user exists construct and return ErrorResponse
		UserEntity userData = UserDAO.getInstance().getUserEntity(registerRequest.getUserName());
		if (userData != null) {
			throw new IllegalRequestException(ErrorType.USERNAME_IS_UNAVAILABLE, "Username is alredy taken!");
		}

		// 2. Write new user to database
		userData = new UserEntity();
		userData.setDisplayName(registerRequest.getDisplayName());
		userData.setFirstName(registerRequest.getFirstName());
		userData.setLastName(registerRequest.getLastName());
		userData.setPassword(registerRequest.getPassword());
		userData.setUserName(registerRequest.getUserName());

		UserDAO.getInstance().addUserEntity(userData);


		return new LoginHandler().handle(
				new LoginRequest(new Credentials(registerRequest.getUserName(), registerRequest.getPassword())));
	}

}
