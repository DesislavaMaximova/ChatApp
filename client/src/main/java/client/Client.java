package client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;

import bg.tu.varna.si.chat.model.Credentials;
import bg.tu.varna.si.chat.model.request.LoginRequest;
import bg.tu.varna.si.chat.model.request.LogoutRequest;
import bg.tu.varna.si.chat.model.request.MessageRequest;
import bg.tu.varna.si.chat.model.response.LoginResponse;
import bg.tu.varna.si.chat.model.response.Response;

public class Client {

	private InetAddress serverHost;

	private final int serverPort;

	public Client(InetAddress host, int port) {

		this.serverHost = host;
		this.serverPort = port;
	}

	public void sendMessages() {
		
		/**
		 * user1 sends message to offline user2
		 */
		try (Socket socket = new Socket(serverHost, serverPort);
				ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
				ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream())) {

			// login user1
			LoginRequest login = new LoginRequest(new Credentials("user1", "111"));
			objectOutputStream.writeObject(login);
			
		
			LoginResponse loginResponse = (LoginResponse) objectInputStream.readObject();
			System.out.println(loginResponse);
			
			// send message to user2
			MessageRequest message = new MessageRequest("Hi!", "user2", loginResponse.getCurrentUser().getUserName());
			objectOutputStream.writeObject(message);
			Response messageResponse = (Response) objectInputStream.readObject();
			System.out.println(messageResponse);
			
			// send logout
			LogoutRequest logout = new LogoutRequest();
			objectOutputStream.writeObject(logout);	
			
						
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		/**
		 * user2 logs in and receives all offline messages
		 */
		try (Socket socket = new Socket(serverHost, serverPort);
				ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
				ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream())) {

			// login user2
			LoginRequest login = new LoginRequest(new Credentials("user2", "222"));
			objectOutputStream.writeObject(login);
			LoginResponse loginResponse = (LoginResponse) objectInputStream.readObject();
			System.out.println(loginResponse);
			
			// send logout
			LogoutRequest logout = new LogoutRequest();
			objectOutputStream.writeObject(logout);		

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
