package bg.tu.varna.si.chat.client.controller;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.concurrent.ConcurrentHashMap;

import bg.tu.varna.si.chat.client.UsersRegistry;
import bg.tu.varna.si.chat.model.User;
import bg.tu.varna.si.chat.model.request.FileContentRequest;
import bg.tu.varna.si.chat.model.request.MessageRequest;
import bg.tu.varna.si.chat.model.request.StatusUpdateRequest;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.scene.text.TextBuilder;
import javafx.stage.FileChooser;

public class ChatController extends BaseController implements Initializable {
	
	private static final String CHAT_HISTORY_DIRECTORY = 
			System.getProperty("user.home") + 
			File.separator +
			"chatHistory" +
			File.separator;

	@FXML
	private Pane messagePane;

	@FXML
	private TextField userInputMessage;

	@FXML
	private Button sendMessageButton;

	@FXML
	private ListView<User> userList;

	@FXML
	private Label chatUserName;

	@FXML
	private Button sendFileButton;
	
	private Collection<MessageRequest> undeliveredMessageRequests;

	private Map<String, ListView<Text>> chats = new ConcurrentHashMap<String, ListView<Text>>();

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		Platform.runLater(() -> {
			ObservableList<User> users = FXCollections.observableList(UsersRegistry.getInstance().getUsers());
			userList.setItems(users);
			userList.setCellFactory(new CellUser());
			
			for (User user : userList.getItems()) {
				loadChat(user);
			}
			
			for (MessageRequest request : undeliveredMessageRequests) {
				receiveMessage(request);
			}
			
		});

		sendMessageButton.setDisable(true);
		sendFileButton.setDisable(true);
	}

	// redone
	@FXML
	protected void sendMessageButtonPressed(ActionEvent event) throws Exception {

		sendMessage();
	}

	// Created // Sending messages via pressing Enter Key
	@FXML
	protected void onEnterPressed(KeyEvent ke) throws Exception {
		if (ke.getCode().equals(KeyCode.ENTER)) {
			sendMessage();
		}
		if (ke.getCode().equals(KeyCode.F1)) {
			sendFileButtonClicked(null);
		}
	}

	// Created new
	private void sendMessage() {
		if (userInputMessage.getText().isEmpty()) {
			return;
		}

		// send message button clicked
		User sender = UsersRegistry.getInstance().getCurrentUser();
		User recipient = userList.getSelectionModel().getSelectedItem();

		MessageRequest message = new MessageRequest(userInputMessage.getText().toString(), recipient.getUserName(), sender.getUserName());

		listener.send(message);
		SimpleDateFormat format = new SimpleDateFormat("HH:mm");
		String time = format.format(message.getTimeStamp());

		chats.get(recipient.getUserName()).getItems().add(formatText("", "[", time, "] ",
				sender.getDisplayName(), ": ", userInputMessage.getText()));

		writeToFile("[" + time + "] " + sender.getDisplayName() + ": " + userInputMessage.getText().toString(),
				sender.getUserName() + "_" + recipient.getUserName() + "_ChatHistory.txt");

		userInputMessage.clear();

	}

	@FXML
	protected void selectedItem(MouseEvent click) {

		User selectedUser = userList.getSelectionModel().getSelectedItem();

		if (selectedUser != null && selectedUser.getUserName() != null) {
			loadChat(selectedUser);
			sendMessageButton.setDisable(false);
			sendFileButton.setDisable(false);
		} else {
			sendMessageButton.setDisable(true);
			sendFileButton.setDisable(true);
		}

	}

	private void loadChat(User user) {

		chatUserName.setText(user.getDisplayName());

		ListView<Text> messages = chats.get(user.getUserName());
		if (messages == null) {
			messages = new ListView<Text>();

			messages.getItems().add(formatText("-fx-font-weight:bold;", "Conversation with ", user.getDisplayName()));

			chats.put(user.getUserName(), messages);

			readFromFile(user.getUserName(), 
					UsersRegistry.getInstance().getCurrentUser().getUserName() + "_" + user.getUserName() + "_ChatHistory.txt");

		}
		messages.setPrefSize(440, 480);
		messagePane.getChildren().clear();
		messagePane.getChildren().add(messages);
	}

	public void receiveMessage(MessageRequest message) {

		Platform.runLater(() -> {

			User sender = UsersRegistry.getInstance().getUser(message.getSenderName());
			userList.getSelectionModel().select(sender);
			loadChat(sender);


			SimpleDateFormat format = new SimpleDateFormat("HH:mm");
			String time = format.format(message.getTimeStamp());

			chats.get(sender.getUserName()).getItems()
			.add(formatText("", "[", time, "]: ", sender.getDisplayName(), ": ", message.getMessageContent()));

		

				writeToFile("[" + time + "]: " + sender.getDisplayName() + ": " + message.getMessageContent(),
						UsersRegistry.getInstance().getCurrentUser().getUserName() + "_" + message.getSenderName()
						+ "_ChatHistory.txt");		

		});
	}

	public void updateUserStatus(StatusUpdateRequest statusUpdateRequest) {

		Platform.runLater(() -> {
			UsersRegistry.getInstance().updateUser(statusUpdateRequest.getUser());

			userList.getItems().remove(statusUpdateRequest.getUser());
			userList.getItems().add(statusUpdateRequest.getUser());
			userList.refresh();
		});

	}

	@FXML
	protected void sendFileButtonClicked(ActionEvent event) throws Exception {

		String sender = UsersRegistry.getInstance().getCurrentUser().getUserName();
		String recipient = userList.getSelectionModel().getSelectedItem().getDisplayName();
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Open File Dialog");
		File selectedFiles = fileChooser.showOpenDialog(null);

		if (selectedFiles != null) {

			try {
				File fileToBeTransfered = new File(selectedFiles.getAbsolutePath());
				FileContentRequest fileRequest = new FileContentRequest(selectedFiles.getName(), recipient, sender,
						Files.readAllBytes(fileToBeTransfered.toPath()));
				System.out.println("Sending file request");
				listener.send(fileRequest);
				System.out.println(selectedFiles.getName());

				chats.get(recipient).getItems()
				.add(formatText("-fx-font-weight:bold;", "You have send file " + fileToBeTransfered.getName()
				+ " to:  " + userList.getSelectionModel().getSelectedItem().getDisplayName()));

			} catch (IOException e) {
				System.out.println("Can't read file with name: " + selectedFiles.getName());
				e.printStackTrace();
			}

		}

	}
	
	public void setUndeliveredMessages(Collection<MessageRequest> undeliveredMessageRequests) {
		this.undeliveredMessageRequests = undeliveredMessageRequests;
	}

	public void acceptFileRequest(FileContentRequest request) {

		Platform.runLater(() -> {
			System.out.println("Got a File Transfer Request");
			User sender = UsersRegistry.getInstance().getUser(request.getSender());

			Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
			alert.setTitle("Incoming file request from " + sender);
			alert.setContentText("Do you want to save it?");
			ButtonType okButton = new ButtonType("Yes", ButtonBar.ButtonData.YES);
			ButtonType noButton = new ButtonType("No", ButtonBar.ButtonData.NO);

			alert.getButtonTypes().setAll(okButton, noButton);
			alert.showAndWait().ifPresent(type -> {

				if (type == okButton) {

					userList.getSelectionModel().select(sender);
					loadChat(sender);

					FileChooser fileChooser = new FileChooser();
					fileChooser.setTitle("Save File");
					fileChooser.setInitialFileName(request.getFileName());
					File file = fileChooser.showSaveDialog(null);

					if (file != null) {
						byte[] content = request.getContent();
						try (FileOutputStream fos = new FileOutputStream(file)) {
							fos.write(content);

						} catch (IOException ioe) {

						}
					}

					chats.get(sender.getUserName()).getItems().add(formatText("-fx-font-weight:bold;",
							"You have recieved file with name  ", file.getName(), " from ", sender.getDisplayName()));

				} else {
					System.out.println("File Transfer Request Not Accepted");

				}

			});

		});

	}

	public void receiveFile(FileContentRequest file) {

		BufferedWriter writer = null;


		System.out.println(file.getFileName() + " Send By:" + file.getSender() + " With size of:" + file.getContent());
		String s = new String(file.getContent());
		System.out.println(s);
		System.out.println(file.getFileName());
		File newTextFile = new File(System.getProperty("user.home") + File.separatorChar + "My Documents"
				+ File.separatorChar + file.getFileName());
		try {
			writer = new BufferedWriter(new FileWriter(newTextFile));
			writer.write(s);

		} catch (IOException e) {
		} finally {
			try {
				if (writer != null)
					writer.close();
			} catch (IOException e) {
			}
		}

		System.out.println("File saved to: " + System.getProperty("user.home") + File.separatorChar + "My Documents"
				+ File.separatorChar + file.getFileName());

	}

	private void writeToFile(String content, String FileName) {
		try (FileWriter fileWriter = new FileWriter(CHAT_HISTORY_DIRECTORY + FileName, true)) {
			fileWriter.write(content + System.lineSeparator());

		} catch (IOException e) {
			System.out.println("Error Writing to file!");
		}
	}

	private void readFromFile(String collocutor, String FileName) {

		try {

			BufferedReader br = new BufferedReader(
					new InputStreamReader(new FileInputStream(CHAT_HISTORY_DIRECTORY + FileName), "Cp1252"), 100);

			String line;

			while ((line = br.readLine()) != null) {

				chats.get(collocutor).getItems().add(formatText("", line));
			}

			br.close();

		} catch (FileNotFoundException e) {
			System.out.println("File with name " + FileName + "not Found!");
		} catch (IOException e) {
			System.out.println("Error read to file!");
		}
	}

	private Text formatText(String style, String... parts) {
		StringBuilder builder = new StringBuilder();
		for (String part : parts) {
			builder.append(part);
		}

		return TextBuilder.create().text(builder.toString()).style(style).build();
	}

}
