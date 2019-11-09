package bg.tu.varna.si.chat.server;

import java.io.IOException;

public class ServerLauncher {

	public static void main(String[] args) {

		try {
			
			Server server = new Server();
			server.startServer();

		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
	}

}
