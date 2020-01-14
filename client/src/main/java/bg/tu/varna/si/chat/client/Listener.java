package bg.tu.varna.si.chat.client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.UncheckedIOException;
import java.net.Socket;

import bg.tu.varna.si.chat.client.controller.ChatController;
import bg.tu.varna.si.chat.model.request.FileContentRequest;
import bg.tu.varna.si.chat.model.request.MessageRequest;
import bg.tu.varna.si.chat.model.request.Request;
import bg.tu.varna.si.chat.model.request.StatusUpdateRequest;
import bg.tu.varna.si.chat.model.response.ErrorResponse;
import bg.tu.varna.si.chat.model.response.Response;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class Listener implements Runnable {

	private Socket socket;

	private ObjectInputStream inputStream;

	private ObjectOutputStream outputStream;

	private ChatController chatController;



	public Listener(String host, int port) {

		try {

			this.socket = new Socket(host, port);
			this.outputStream = new ObjectOutputStream(socket.getOutputStream());
			this.inputStream = new ObjectInputStream(socket.getInputStream());

		} catch (IOException e) {

			Platform.runLater(() -> {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Error");
				alert.setHeaderText(null);
				alert.setContentText("Error connecting to server");
				alert.showAndWait();

			});

		}
	}

	public void setChatController(ChatController controller) {
		this.chatController = controller;
	}
	
	public void send(Request request) {
		try {
			System.out.println("Passing by send");
			outputStream.writeObject(request);
		} catch (IOException e) {
			throw new UncheckedIOException(e);
		}
	}

	public Response sendAndReceive(Request request) {
		try {
			System.out.println("Passing by sendAndReceive");
			outputStream.writeObject(request);

			return (Response) inputStream.readObject();

		} catch (IOException e) {
			throw new UncheckedIOException(e);
		} catch (ClassNotFoundException e) {
			throw new IllegalStateException(e);
		}
	}

	//
	public void writeResponse(Response response) {
		try {

			outputStream.writeObject(response);

		} catch (IOException e) {
			System.err.println("Failed writing response: " + e.getLocalizedMessage());
		}
	}

	@Override
	public void run() {

		System.out.println("Client listening for incoming requests...");

		while (true) {

			try {
				Object object = inputStream.readObject();

				System.out.println("Received " + object.getClass());

				if (object instanceof ErrorResponse) {
					ErrorResponse error = (ErrorResponse) object;
					Platform.runLater(() -> {
						Alert alert = new Alert(AlertType.ERROR);
						alert.setTitle("Error");
						alert.setHeaderText(null);
						alert.setContentText(error.getErrorMessage());
						alert.showAndWait();
					});
				}

				if (object instanceof MessageRequest) {
					chatController.receiveMessage((MessageRequest) object);
				}

				if (object instanceof FileContentRequest) {
					chatController.acceptFileRequest((FileContentRequest) object);

				}

				if (object instanceof StatusUpdateRequest) {

					chatController.updateUserStatus((StatusUpdateRequest) object);

				}
			
			


			} catch (ClassNotFoundException | IOException e) {
				throw new IllegalStateException(e);
			}
		}
	}

	

}
