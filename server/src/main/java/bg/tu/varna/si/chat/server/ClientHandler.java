package bg.tu.varna.si.chat.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import bg.tu.varna.si.chat.model.request.Request;
import bg.tu.varna.si.chat.model.request.RequestType;
import bg.tu.varna.si.chat.model.response.ErrorResponse;
import bg.tu.varna.si.chat.model.response.ErrorType;
import bg.tu.varna.si.chat.model.response.LoginResponse;
import bg.tu.varna.si.chat.model.response.Response;
import bg.tu.varna.si.chat.server.exception.IllegalRequestException;
import bg.tu.varna.si.chat.server.exception.UnsupportedRequestException;
import bg.tu.varna.si.chat.server.handler.RequestHandlerFactory;

public class ClientHandler implements Runnable {

	private Socket socket;

	private ObjectInputStream inputStream;

	private ObjectOutputStream outputStream;

	private String userName;

	public ClientHandler(Socket socket) {

		this.socket = socket;

		try {
			this.inputStream = new ObjectInputStream(socket.getInputStream());
			this.outputStream = new ObjectOutputStream(socket.getOutputStream());
		} catch (IOException e) {
			throw new IllegalStateException("Failed getting socket IO streams.", e);
		}
	}



	public void sendRequest(Request request) {
		try {
			outputStream.writeObject(request);
		} catch (IOException e) {
			throw new IllegalStateException("Failed writing object in outputstream", e);
		}
	}



	@Override
	public void run() {
		Request request = null;
		Response response = null;

		do {
			request = readRequest();

			try {
				response = RequestHandlerFactory.buildRequestHandler(request).handle(request);

				if (response instanceof LoginResponse) {
					this.userName = ((LoginResponse) response).getCurrentUser().getUserName();
					ClientRegistry.getInstance().logUserIn(userName, this);
				}

				if (userName == null ) {
					throw new IllegalRequestException(ErrorType.UNEXPECTED_REQUEST, 
							"Expecting login or register request, but received [" + request.getRequestType() + "]");
				}

			} catch (UnsupportedRequestException e) {
				response = new ErrorResponse(e.getErrorType(), e.getErrorMessage());
			} catch (IllegalRequestException e) {
				response = new ErrorResponse(e.getErrorType(), e.getErrorMessage());
			}

			writeResponse(response);

		} while (RequestType.LOGOUT_REQUEST != request.getRequestType());

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

	private void writeResponse(Response response) {
		try {
			outputStream.writeObject(response);
		} catch (IOException e) {
			System.out.println("Failed writing response..." + e.getLocalizedMessage());
		}
	}

	private void shutdown() {
		try {
			if (socket != null) {
				System.out.println("Closing down connection!");
				socket.close();
			}
		} catch (IOException e) {
			System.out.println("Failed closing connection!");
		}
	}
}
