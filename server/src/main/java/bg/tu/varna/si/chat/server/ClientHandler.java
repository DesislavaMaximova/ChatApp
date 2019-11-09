package bg.tu.varna.si.chat.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import bg.tu.varna.si.chat.model.Credentials;
import bg.tu.varna.si.chat.model.User;
import bg.tu.varna.si.chat.model.request.Login;
import bg.tu.varna.si.chat.model.request.Request;
import bg.tu.varna.si.chat.model.request.RequestType;
import bg.tu.varna.si.chat.model.request.UserRegisterRequest;
import bg.tu.varna.si.chat.model.response.ErrorResponse;
import bg.tu.varna.si.chat.model.response.ErrorType;
import bg.tu.varna.si.chat.model.response.LoginResponse;
import bg.tu.varna.si.chat.model.response.Response;
import bg.tu.varna.si.chat.model.response.ResponseType;

public class ClientHandler implements Runnable {

	private Socket socket;

	private ObjectInputStream inputStream;

	private ObjectOutputStream outputStream;

	private String userName;

	private UserDao userDao = new UserDao();

	public ClientHandler(Socket socket) {
		super();
		this.socket = socket;

		try {
			this.inputStream = new ObjectInputStream(socket.getInputStream());
			this.outputStream = new ObjectOutputStream(socket.getOutputStream());
		} catch (IOException e) {
			throw new IllegalStateException("Failed getting socket IO streams.", e);
		}
	}

	@Override
	public void run() {

		// 1. process LoginRequest or RegisterRequest
		Response response = init();
		sendReponse(response);

		if (ResponseType.ERROR == response.getResponseType()) {
			shutdown();
		}

		// 2. process any other request until LogoutRequest is received
		processClientRequests();

	}

	private Response init() {

		Request request = readRequest();

		if (RequestType.LOGIN == request.getRequestType()) {
			return processLogin((Login) request);
		}

		if (RequestType.REGISTER == request.getRequestType()) {
			return processRegistration((UserRegisterRequest) request);
		}

		return new ErrorResponse(ErrorType.UNEXPECTED_REQUEST,
				"Expecting login or register request, but received " + request.getRequestType());
	}

	private Response processLogin(Login request) {

		Response response = null;

		// 1. get user from UserDao by username
		UserData userData = userDao.getUserData(request.getCredentials().getUserName());

		// 1.1. if user == null return ErrorResponse WRONG_USERNAME_OR_PASSWORD
		if (userData == null) {
			return new ErrorResponse(ErrorType.WRONG_USERNAME_OR_PASSWORD, "Wrong username or password!");
		}

		// 1.2. if user.getPassword != request.getPassword return ErrorReponse
		if (!userData.getPassword().equals(request.getCredentials().getPassword())) {
			return new ErrorResponse(ErrorType.WRONG_USERNAME_OR_PASSWORD, "Wrong username or password!");
		}

		// 2. Register this ClientHandler in ClientRegistry
		User loggedUser = UserConverter.createUser(userData);
		this.userName = loggedUser.getUserName();

		ClientRegistry.getInstance().logUserIn(userName, this);

		// 3. return LoginResponse (currentUser, activeUsers, inactiveUsers)
		response = new LoginResponse(loggedUser, ClientRegistry.getInstance().getActiveUsers(),
				ClientRegistry.getInstance().getInactiveUsers());

		// TODO :
		// 4. Send message to all activeUsers

		return response;
	}



	private Response processRegistration(UserRegisterRequest request) {

		// 1. check if user exists
		// 1.1. if user exists construct and return ErrorResponse
		UserData userData = userDao.getUserData(request.getUserName());
		if (userData != null) {
			return new ErrorResponse(ErrorType.USER_NAME_IS_NOT_AVAILABLE, "Username is alredy taken!");

		}

		// 2. Write new user to database
		userData = new UserData();
		userData.setDisplayName(request.getDisplayName());
		userData.setFirstName(request.getFirstName());
		userData.setLastName(request.getLastName());
		userData.setPassword(request.getPassword());
		userData.setUserName(request.getUserName());

		userDao.addUserData(userData);

		return processLogin(new Login(new Credentials(request.getUserName(), request.getPassword())));
	}

	private void sendReponse(Response response) {
		try {
			outputStream.writeObject(response);
		} catch (IOException e) {
			throw new IllegalStateException("Failed writing object in outputstream", e);
		}
	}

	private void processClientRequests() {
		Request request = null;

		do {
			request = readRequest();

			// TODO : handle client request

		} while (RequestType.LOGOUT != request.getRequestType());

		ClientRegistry.getInstance().logUserOut(userName);
		shutdown();
	}

	private Request readRequest() {
		try {
			Request request = (Request) inputStream.readObject();
			System.out.println("Received: " + request);

			return request;
		} catch (ClassNotFoundException | IOException e) {
			throw new IllegalStateException("Failed reading request.");
		}
	}

	private void shutdown() {
		try {
			if (socket != null) {
				System.out.println("Closing down connection!");
				socket.close();
			}
		} catch (IOException e) {
			System.out.println("Unable to close down connection!");
		}
	}
}
