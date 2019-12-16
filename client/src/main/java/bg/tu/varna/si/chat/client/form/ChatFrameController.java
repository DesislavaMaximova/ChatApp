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
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
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
	private TreeView<String> friendsList;
	
	@FXML
	Stage stage;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// root of the tree

		TreeItem<String> rootItem = new TreeItem<>("Root");
		rootItem.setExpanded(true);

		// online branch and its children
		TreeItem<String> onlineUsersTree = new TreeItem<String>("Online users");

		Collection<User> allUsers = UsersRegistry.getInstance().getUsers();
		for (User user : allUsers) {
			if (user.isActive()) {
				addUser(user, onlineUsersTree);
			}
		}

		// offline users branch and its children
		TreeItem<String> offlineUsersTree = new TreeItem<String>("Offline users");
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
	private void addUser(User user, TreeItem<String> parent) {
		// skip currently logged user
		if (!UsersRegistry.getInstance().getCurrentUser().getUserName().equals(user.getUserName())) {
			TreeItem<String> item = new TreeItem<>(user.getDisplayName());
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
		MessageRequest message = new MessageRequest(userInputMessage.getText().toString(), "recipientName",
				"senderName");
		messageArea.appendText(userInputMessage.getText().toString() + "\n");
		Response response = Client.getInstance().send(message);

		if (ResponseType.ERROR == response.getResponseType()) {
			ErrorResponse errorResponse = (ErrorResponse) response;
			alert(AlertType.ERROR, "Error", errorResponse.getErrorMessage());
			return;
		}
	}
	@FXML
	protected void selectedItem(MouseEvent event) {
		//selected item from TreeView
		TreeItem<String> item = friendsList.getSelectionModel().getSelectedItem();
		try {
			

			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/ChatFrame.fxml"));
			BorderPane root = (BorderPane) fxmlLoader.load();
			stage = new Stage();
			Scene scene = new Scene(root);
			stage.setTitle("DNK Messenger: " + UsersRegistry.getInstance().getCurrentUser().getDisplayName());
			stage.setScene(scene);
			stage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
		//logic here
		messageArea.appendText(item.getValue().toString() + "\n");//this is only an example to see if it even works
	}
}
