package bg.tu.varna.si.chat.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import bg.tu.varna.si.chat.server.db.UserDAO;

public class Server {

	private static final int PORT = 1300;
	
	public void startServer() throws IOException {
		
		try (ServerSocket server = new ServerSocket(PORT)) {
			
			UserDAO.getInstance();
			
			System.out.println("Server started. Listening on port " + PORT);
			
			do {
				Socket clientSocket = server.accept();

				ClientHandler handler = new ClientHandler(clientSocket);
				Thread treat = new Thread(handler);
				treat.start();

			} while (true);
			
		} catch (IOException e) {
			throw new IllegalStateException("Unable to set up port!", e);
		} 

	}
	
}
