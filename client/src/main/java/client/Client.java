package client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;

import bg.tu.varna.si.chat.model.request.Request;
import bg.tu.varna.si.chat.model.response.Response;

public class Client {

	private InetAddress serverHost;

	private final int serverPort;
	private ObjectInputStream inputStream;

	private ObjectOutputStream outputStream;
	private Request request = null;
	private Response response = null;

	public Client(InetAddress host, int port) throws IOException, ClassNotFoundException {

		this.serverHost = host;
		this.serverPort = port;
	}
	
		public void sendMessages() {
		try (Socket socket = new Socket(serverHost, serverPort);
				ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
				ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream())) {
			while (true) {
				
			}
			
			
	
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
	

	

	public void sendRequest(Request request) {
		try {
			outputStream.writeObject(request);
		} catch (IOException e) {
			throw new IllegalStateException("Failed writing object in outputstream", e);
		}
	}

	public Request readRequest() {
		try {
			request = (Request) inputStream.readObject();
			System.out.println("Received: " + request);

			return request;
		} catch (ClassNotFoundException | IOException e) {
			throw new IllegalStateException("Failed reading request.");
		}
	}

	public void writeResponse(Response response) {
		try {
			outputStream.writeObject(response);
		} catch (IOException e) {
			System.out.println("Failed writing response..." + e.getLocalizedMessage());
		}
	}

	public Response readResponse() {
		try {
			response = (Response) inputStream.readObject();
			System.out.println("Received: " + response);
			return response;

		} catch (ClassNotFoundException | IOException e) {
			throw new IllegalStateException("Failed reading request.");
		}

	}

	// public void sendMessages() {

	/**
	 * user1 sends message to offline user2
	 */

	/*
	 * // send message to user2 MessageRequest message = new MessageRequest("Hi!",
	 * "user2", loginResponse.getCurrentUser().getUserName());
	 * outputStream.writeObject(message); Response messageResponse = (Response)
	 * inputStream.readObject(); System.out.println(messageResponse);
	 * 
	 * // send logout LogoutRequest logout = new LogoutRequest();
	 * outputStream.writeObject(logout);
	 */

	/**
	 * user2 logs in and receives all offline messages
	 */
	/*
	 * try (Socket socket = new Socket(serverHost, serverPort); ObjectOutputStream
	 * objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
	 * ObjectInputStream objectInputStream = new
	 * ObjectInputStream(socket.getInputStream())) {
	 * 
	 * // login user2 LoginRequest login = new LoginRequest(new Credentials("user2",
	 * "222")); objectOutputStream.writeObject(login); LoginResponse loginResponse =
	 * (LoginResponse) objectInputStream.readObject();
	 * System.out.println(loginResponse);
	 * 
	 * // send logout LogoutRequest logout = new LogoutRequest();
	 * objectOutputStream.writeObject(logout);
	 * 
	 * } catch (IOException e) { // TODO Auto-generated catch block
	 * e.printStackTrace(); } catch (ClassNotFoundException e) { // TODO
	 * Auto-generated catch block e.printStackTrace(); }
	 * 
	 * }
	 */
}