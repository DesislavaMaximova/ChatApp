package bg.tu.varna.si.chat.client.controller;

import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.concurrent.ConcurrentHashMap;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import bg.tu.varna.si.chat.client.UsersRegistry;
import bg.tu.varna.si.chat.model.User;
import bg.tu.varna.si.chat.model.request.MessageRequest;

public class ChatController extends BaseController implements Initializable {

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


	private Map<String, ListView<String>> chats = 
			new ConcurrentHashMap<String, ListView<String>>();

	@Override
	public void initialize(URL location, ResourceBundle resources) {


		Platform.runLater(() -> {
			ObservableList<User> users = FXCollections.observableList(
					UsersRegistry.getInstance().getUsers());
			userList.setItems(users);
		});

		sendMessageButton.setDisable(true);
	}

	@FXML
	protected void sendMessage(ActionEvent event) throws Exception {

		if (userInputMessage.getText().isEmpty()) {
			return;
		}

		// send message button clicked
		String sender = UsersRegistry.getInstance().getCurrentUser().getUserName();
		String recipient = userList.getSelectionModel().getSelectedItem().getUserName();

		MessageRequest message = new MessageRequest(userInputMessage.getText().toString(), recipient, sender);

		listener.send(message);
		
		chats.get(recipient).getItems().add(
				UsersRegistry.getInstance().getCurrentUser().getDisplayName()
				+ ": " +
				userInputMessage.getText()
				);
		
		userInputMessage.clear();

	}

	@FXML
	protected void selectedItem(MouseEvent click) {
		// selected item from TreeView
		User selectedUser = userList.getSelectionModel().getSelectedItem();

		if (selectedUser != null && selectedUser.getUserName() != null) {
			loadChat(selectedUser);
			sendMessageButton.setDisable(false);
		} else {
			sendMessageButton.setDisable(true);
		}
	}

	private void loadChat(User user) {
		chatUserName.setText(user.getDisplayName());

		ListView<String> messages = chats.get(user.getUserName());

		if (messages == null) {
			messages = new ListView<String>(); 

			messages.getItems().add("Conversation with " + user.getDisplayName());

			chats.put(user.getUserName(), messages);
		}

		messagePane.getChildren().clear();
		messagePane.getChildren().add(messages);
	}
	
	public void receiveMessage(MessageRequest message) {
		
		Platform.runLater(() -> {
			
			User sender = UsersRegistry.getInstance().getUser(message.getSenderName());
			
			loadChat(sender);
			
			userList.getSelectionModel().select(sender);
			
			chats.get(sender.getUserName()).getItems().add(
					sender.getDisplayName() +  ": " +
					message.getMessageContent()
					);
		});
	}

}
