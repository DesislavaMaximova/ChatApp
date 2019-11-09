package client;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class ClientLauncher {

	public static void main(String[] args) {
		try {
			Client client = new Client(InetAddress.getLocalHost(), 1300);
			client.sendMessages();
		} catch (UnknownHostException e) {
			System.out.println("Host ID not found!");
		}
	}

}
