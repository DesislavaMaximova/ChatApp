package bg.tu.varna.si.chat.client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.UncheckedIOException;
import java.net.ConnectException;
import java.net.Socket;
import java.net.UnknownHostException;

import bg.tu.varna.si.chat.model.request.Request;
import bg.tu.varna.si.chat.model.response.ErrorResponse;
import bg.tu.varna.si.chat.model.response.ErrorType;
import bg.tu.varna.si.chat.model.response.Response;

public class Client {

	private static final Client INSTANCE_HOLDER = new Client();

	private String host;
	private int port;

	private ObjectInputStream inputStream;
	private ObjectOutputStream outputStream;

	private Socket socket;

	public Client() {

	}

	public static Client getInstance() {
		return INSTANCE_HOLDER;
	}

	private void initializeIOStreams() throws UnknownHostException, IOException {
		this.socket = new Socket(host, port);
		this.outputStream = new ObjectOutputStream(socket.getOutputStream());
		this.inputStream = new ObjectInputStream(socket.getInputStream());
	}

	public void configure(String host, int port) {
		this.host = host;
		this.port = port;
	}

	public Response send(Request request) {
		try {

			if (outputStream == null || inputStream == null) {
				initializeIOStreams();
			}

			outputStream.writeObject(request);

			Response response = (Response) inputStream.readObject();

			return response;
		} catch (IOException e) {
			if (e instanceof ConnectException) {
				return new ErrorResponse(ErrorType.CONNECTION_REFUSED, "Failed connecting to " + host + ":" + port);
			}

			throw new UncheckedIOException(e);

		} catch (ClassNotFoundException e) {
			throw new IllegalStateException(e);
		}
	}

	public void shutDown() {
		try {
			inputStream.close();
			outputStream.close();
			socket.close();
		} catch (IOException ignored) {

		}
	}

}
