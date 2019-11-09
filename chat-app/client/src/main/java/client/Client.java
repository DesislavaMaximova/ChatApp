package client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;

import bg.tu.varna.si.chat.model.Credentials;
import bg.tu.varna.si.chat.model.request.Login;
import bg.tu.varna.si.chat.model.request.Logout;
import bg.tu.varna.si.chat.model.request.Message;
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
			Login login = new Login(new Credentials("user1", "111"));
			objectOutputStream.writeObject(login);
			LoginResponse loginResponse = (LoginResponse) objectInputStream.readObject();
			System.out.println(loginResponse);
			
			// send message to user2
			Message message = new Message("Hi!", "user2", loginResponse.getCurrentUser().getUserName());
			objectOutputStream.writeObject(message);
			Response messageResponse = (Response) objectInputStream.readObject();
			System.out.println(messageResponse);
			
			// send logout
			Logout logout = new Logout();
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
			Login login = new Login(new Credentials("user2", "222"));
			objectOutputStream.writeObject(login);
			LoginResponse loginResponse = (LoginResponse) objectInputStream.readObject();
			System.out.println(loginResponse);
			
			// send logout
			Logout logout = new Logout();
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
