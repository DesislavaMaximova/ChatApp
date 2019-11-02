package client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;

import bg.tu.varna.si.chat.model.Credentials;
import bg.tu.varna.si.chat.model.request.Login;
import bg.tu.varna.si.chat.model.request.UserRegisterRequest;
import bg.tu.varna.si.chat.model.response.Response;

public class Client {

	private InetAddress serverHost;

	private final int serverPort;

	public Client(InetAddress host, int port) {

		this.serverHost = host;
		this.serverPort = port;
	}

	public void sendMessages() {

		try (Socket socket = new Socket(serverHost, serverPort);
				ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
				ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream())) {

			//Login login = new Login(new Credentials("Popi", "111"));

			//objectOutputStream.writeObject(login);
			//Response response = (Response) objectInputStream.readObject();
			//System.out.println(response);
			UserRegisterRequest register = new UserRegisterRequest("Bobo", "Boris", "Tishev", "Bobo", "123");
			objectOutputStream.writeObject(register);
			Response response = (Response) objectInputStream.readObject();
			System.out.println(response);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// try {
//			socket = new Socket(serverHost, serverPort);
//			
//			OutputStream output = socket.getOutputStream();
//			ObjectOutputStream objectOutputStream = new ObjectOutputStream(output);
//			Scanner userEntity = new Scanner(System.in);
//			String userInput = "";
//			String response = "";
//			do {
//				System.out.println("Enter message ('" + Constants.END_SESSION_STRING + "' to exit): ");
//				userInput = userEntity.nextLine();
//				Request request = new Message(userInput);
//				
//				objectOutputStream.writeObject(request);
//				
//				
//				response = input.nextLine();
//				System.out.println("Server> " + response);
//
//			} while (!Constants.END_SESSION_STRING.equalsIgnoreCase(message));
//			input.close();
//			userEntity.close();
//
//		} catch (IOException e) {
//			e.printStackTrace();
//		} finally {
//			try {
//				System.out.println("Closing connection");
//				socket.close();
//
//			} catch (IOException e) {
//				System.out.println("Unable to disconect!");
//				System.exit(1);
//			}
//
//		}
	}
}
