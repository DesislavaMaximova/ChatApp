package bg.tu.varna.si.chat.client.form;

import java.net.URL;
import java.util.ResourceBundle;

import bg.tu.varna.si.chat.client.Client;
import bg.tu.varna.si.chat.model.request.MessageRequest;
import bg.tu.varna.si.chat.model.response.ErrorResponse;
import bg.tu.varna.si.chat.model.response.Response;
import bg.tu.varna.si.chat.model.response.ResponseType;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
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

public class ChatFrameController extends BaseForm implements Initializable{

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
	private TreeView<String> friendsList;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		//root of the tree
		TreeItem<String> rootItem = new TreeItem<>("Root");
		rootItem.setExpanded(true);
		//online branch and its children
		TreeItem<String> online = makeBranch("Online", rootItem);
		makeBranch("user1", online);
		makeBranch("user2", online);
		makeBranch("user2", online);
		//offline branch and its children
		TreeItem<String> offline = makeBranch("Offline", rootItem);
		makeBranch("user3", offline);
		makeBranch("user4", offline);


		online.setExpanded(true);
		offline.setExpanded(true);

		//create tree and hide root item
		friendsList.setRoot(rootItem);
		friendsList.setShowRoot(false);
		
		
	}
	//Create branches
			private TreeItem<String> makeBranch(String title, TreeItem<String> parent) {
				TreeItem<String> item = new TreeItem<>(title);
				item.setExpanded(true);
				parent.getChildren().add(item);
				return item;
			}
	
	
	public void start(Stage stage) {
		try {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/ChatFrame.fxml"));
		BorderPane root = (BorderPane) fxmlLoader.load();
		stage = new Stage();
		Scene scene = new Scene(root);
		window.setTitle("DNK Messenger");
		window.setScene(scene);	
		window.show();
		
	} catch (Exception e) {
		e.printStackTrace();
	}
	
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
