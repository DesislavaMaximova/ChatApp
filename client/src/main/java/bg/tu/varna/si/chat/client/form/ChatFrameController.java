package bg.tu.varna.si.chat.client.form;

import bg.tu.varna.si.chat.model.User;
//import bg.tu.varna.si.chat.client.Client;
//import bg.tu.varna.si.chat.model.Credentials;
//import bg.tu.varna.si.chat.model.request.LoginRequest;
//import bg.tu.varna.si.chat.model.response.ErrorResponse;
//import bg.tu.varna.si.chat.model.response.Response;
//import bg.tu.varna.si.chat.model.response.ResponseType;
//import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class ChatFrameController extends BaseForm {

	//@FXML
	//TreeView<String>friendsList;
	
	@FXML
	private TextArea messageArea;
	
	@FXML
	private TextField userInputMessage;
	
	@FXML
	private Button sendFileButton;
	
	@FXML
	private Button sendMessageButton;
	
	@FXML
	Stage stage;
	
	@FXML
	private TreeView<String> friendsList;
	User user = new User();
	public void start(Stage primaryStage) {
		try {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/ChatFrame.fxml"));
		BorderPane root = (BorderPane) fxmlLoader.load();
		stage = new Stage();
		Scene scene = new Scene(root);
		stage.setTitle("DNK Messenger");
		stage.setScene(scene);
		TreeItem<String> rootItem, online, offline;
		rootItem = new TreeItem<>();
		//Root of the TreeView
		rootItem.setExpanded(true);
		
		//Online branch
		online = makeBranch("Online", rootItem); //make it add dynamically
		makeBranch("Test User", online);
		makeBranch(user.getDisplayName(), online);
		makeBranch(user.getDisplayName(), online);
		makeBranch(user.getDisplayName(), online);
		
		//Offline branch
		offline = makeBranch(user.getDisplayName(), rootItem);
		makeBranch("OfflineUser", offline);
		
		//Create tree
		friendsList = new TreeView<>(rootItem);
		friendsList.setShowRoot(false);
		root.getChildren().add(friendsList);
		
		stage.show();
	} catch (Exception e) {
		e.printStackTrace();
	}
	

	}
	//Create branches
	private TreeItem<String> makeBranch(String title, TreeItem<String> parent) {
		//Credentials credentials = new Credentials();
		//LoginRequest login = new LoginRequest(credentials);
		//User user = new User();
		
		//Response response = Client.getInstance().send(login);
		TreeItem<String> item = new TreeItem<>(user.getDisplayName());
		item.setExpanded(true);
		parent.getChildren().add(item);
			return item;
	}
	
	
	
}
