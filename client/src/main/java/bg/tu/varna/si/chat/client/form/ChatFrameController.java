package bg.tu.varna.si.chat.client.form;

import java.net.URL;
import java.util.Collection;
import java.util.ResourceBundle;

import bg.tu.varna.si.chat.client.Client;
import bg.tu.varna.si.chat.client.UsersRegistry;
import bg.tu.varna.si.chat.model.User;
import bg.tu.varna.si.chat.model.request.MessageRequest;
import bg.tu.varna.si.chat.model.response.ErrorResponse;
import bg.tu.varna.si.chat.model.response.Response;
import bg.tu.varna.si.chat.model.response.ResponseType;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class ChatFrameController extends BaseForm implements Initializable {

	@FXML
	private TextArea messageArea;

	@FXML
	private TextField userInputMessage;

	@FXML
	private Button sendFileButton;

	@FXML
	private ImageView sendFile;

	@FXML
	private Button sendMessageButton;

	@FXML
	private ImageView sendMessage;

	@FXML
	private TreeView<User> friendsList;

	@FXML
	Stage stage;

	@FXML
	Label chatUserName;

	String recipient;

	String sender;

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		TreeItem<User> rootItem = new TreeItem<>();
		rootItem.setExpanded(true);

		// online branch and its children
		TreeItem<User> onlineUsersTree = new TreeItem<User>(new User("", false, "Online users"));

		Collection<User> allUsers = UsersRegistry.getInstance().getUsers();
		for (User user : allUsers) {
			if (user.isActive()) {
				addUser(user, onlineUsersTree);
			}
		}

		// offline users branch and its children
		TreeItem<User> offlineUsersTree = new TreeItem<User>(new User("", false, "Offline users"));
		for (User user : allUsers) {
			if (!user.isActive()) {
				addUser(user, offlineUsersTree);
			}
		}

		rootItem.getChildren().add(onlineUsersTree);
		rootItem.getChildren().add(offlineUsersTree);

		onlineUsersTree.setExpanded(true);
		offlineUsersTree.setExpanded(true);

		// create tree and hide root item
		friendsList.setRoot(rootItem);
		friendsList.setShowRoot(false);
	}

	// Create branches
	private void addUser(User user, TreeItem<User> parent) {
		// skip currently logged user
		if (!UsersRegistry.getInstance().getCurrentUser().getUserName().equals(user.getUserName())) {
			TreeItem<User> item = new TreeItem<>(user);
			parent.getChildren().add(item);
		}
	}

	@FXML
	protected void sendFileButtonClicked(ActionEvent event) throws Exception {
		// send file button clicked
	}

	@FXML
	protected void sendMessageButtonClicked(ActionEvent event) throws Exception {
		// send message button clicked
		sender = UsersRegistry.getInstance().getCurrentUser().getUserName();
		MessageRequest message = new MessageRequest(userInputMessage.getText().toString(), recipient, sender);
		messageArea.appendText(userInputMessage.getText().toString() + sender + "\n");
		Response response = Client.getInstance().send(message);
		userInputMessage.clear();

		if (ResponseType.ERROR == response.getResponseType()) {
			ErrorResponse errorResponse = (ErrorResponse) response;
			alert(AlertType.ERROR, "Error", errorResponse.getErrorMessage());
			return;
		}
	}

	@FXML
	protected void selectedItem(MouseEvent click) {
		// selected item from TreeView
		TreeItem<User> selectedItem = friendsList.getSelectionModel().getSelectedItem();
		if (selectedItem.getValue().getUserName() != null) {
			// TODO : open a chat dialog with selected user

			chatUserName.setText(selectedItem.getValue().getDisplayName());
//			stage.setTitle("DNK Messenger: " + UsersRegistry.getInstance().getCurrentUser().getDisplayName());

			recipient = selectedItem.getValue().getUserName();
			
			System.out.println("Selected username: " + selectedItem.getValue().getUserName());
		}

	}
}
