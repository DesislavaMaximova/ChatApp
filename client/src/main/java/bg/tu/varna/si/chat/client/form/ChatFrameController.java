package bg.tu.varna.si.chat.client.form;

import bg.tu.varna.si.chat.client.Client;
import bg.tu.varna.si.chat.model.request.MessageRequest;
import bg.tu.varna.si.chat.model.response.ErrorResponse;
import bg.tu.varna.si.chat.model.response.Response;
import bg.tu.varna.si.chat.model.response.ResponseType;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class ChatFrameController extends BaseForm{

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
	Stage window;
	
	@FXML
	TreeView<String> friendsList;
	
	
	
	public void start(Stage stage) {
		try {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/ChatFrame.fxml"));
		BorderPane root = (BorderPane) fxmlLoader.load();
		stage = new Stage();
		Scene scene = new Scene(root);
		window.setTitle("DNK Messenger");
		window.setScene(scene);
	
		// friends list
		TreeItem<String> rootItem, uOnline, uOffline;
		//Root of the TreeView
		rootItem = new TreeItem<>("RootItem");
		rootItem.setExpanded(true);
		
		//Online branch
		uOnline = makeBranch("Online", rootItem); //needs to be dynamic and get user list
		makeBranch("User1", uOnline);
		makeBranch("User2", uOnline);
		makeBranch("User3", uOnline);
		//makeBranch(user.getDisplayName(), online);
//I		//makeBranch(user.getDisplayName(), online);
		//makeBranch(user.getDisplayName(), online);
		
		//Offline branch
		uOffline = makeBranch("Offline", rootItem);
		makeBranch("OfflineUser1", uOffline);
		makeBranch("OfflineUser2", uOffline);
		makeBranch("OfflineUser3", uOffline);
		
		//Create tree
		friendsList.getRoot();
		friendsList.setShowRoot(false);
		//root.getChildren().addAll();
		
	/*	TreeItem<String> rootItem = new TreeItem<>("Root");
		TreeItem<String> uOnline = new TreeItem<>("Online");
		TreeItem<String> uOffline = new TreeItem<>("Offline");
		rootItem.getChildren().addAll(uOnline, uOffline);
II		TreeItem<String> user1 = new TreeItem<>("User1");
		TreeItem<String> user2 = new TreeItem<>("User2");
		uOnline.getChildren().addAll(user1, user2);
		TreeItem<String> user3 = new TreeItem<>("User3");
		TreeItem<String> user4 = new TreeItem<>("User4");
		uOffline.getChildren().addAll(user3, user4);
		friendsList.setRoot(rootItem);
	*/
		
		
		window.show();
	} catch (Exception e) {
		e.printStackTrace();
	}
	
	}
	//Create branches
	private TreeItem<String> makeBranch(String title, TreeItem<String> parent) {
		TreeItem<String> item = new TreeItem<>(title);
		item.setExpanded(true);
		parent.getChildren().add(item);
		return item;
	}
	
	@FXML
	protected void sendFileButtonClicked(ActionEvent event) throws Exception {
		//send file button clicked
	}
	
	@FXML
	protected void sendMessageButtonClicked(ActionEvent event) throws Exception {
		//send message button clicked
		MessageRequest message = new MessageRequest(userInputMessage.getText().toString(), "recipientName", "senderName");		
		messageArea.appendText(userInputMessage.getText().toString() + "\n");
		Response response = Client.getInstance().send(message);
		
		if (ResponseType.ERROR == response.getResponseType()) {
			ErrorResponse errorResponse = (ErrorResponse) response;
			alert(AlertType.ERROR, "Error", errorResponse.getErrorMessage());
			return;
		}
	}
}
