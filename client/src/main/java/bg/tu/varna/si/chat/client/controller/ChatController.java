package bg.tu.varna.si.chat.client.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.concurrent.ConcurrentHashMap;

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
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import bg.tu.varna.si.chat.client.UsersRegistry;
import bg.tu.varna.si.chat.model.User;
import bg.tu.varna.si.chat.model.request.FileTransferRequest;
import bg.tu.varna.si.chat.model.request.MessageRequest;
import bg.tu.varna.si.chat.model.response.FileTransferResponse;

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
	
	@FXML
	private Button sendFileButton;
	
	@FXML
	private ListView<String> messages;


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
	
	//redone 
		@FXML
		protected void sendMessageButtonPressed(ActionEvent event) throws Exception {

			sendMessage();
		}	
		
		//Created // Sending messages via pressing Enter Key
		@FXML
		protected void onEnterPressed(KeyEvent ke)
				throws Exception {
			if (ke.getCode().equals(KeyCode.ENTER)) {
				sendMessage();
			}
			if(ke.getCode().equals(KeyCode.F1))
			{
				sendFileButtonClicked(null);
			}
		}
	
	//Created new
		private void sendMessage()
		{
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
			SimpleDateFormat format = new SimpleDateFormat("HH:mm");
			String time = format.format(message.getTimeStamp());

			WriteToFile("[" + time + "] " + sender + ": " + userInputMessage.getText().toString(),
					UsersRegistry.getInstance().getCurrentUser().getUserName() + "_" + recipient + "_ChatHistory.txt");
				
			
			userInputMessage.clear();

		}

		File selectedFiles = null;
		@FXML
		protected void sendFileButtonClicked(ActionEvent event) throws Exception {
			
			String sender = UsersRegistry.getInstance().getCurrentUser().getUserName();
			String recipient = userList.getSelectionModel().getSelectedItem().getDisplayName();
			FileChooser fileChooser = new FileChooser();
			fileChooser.setTitle("Open File Dialog");
			// putting Extension Filters
			fileChooser.getExtensionFilters().addAll(new ExtensionFilter("Text Files", "*.txt"));
			 selectedFiles = fileChooser.showOpenDialog(null);

			// for debugging: showing selected item/s name
			if (selectedFiles != null)
			{

				System.out.println(selectedFiles.getName());
			 
						listener.send(new FileTransferRequest(recipient,sender,selectedFiles.getName(),(int) selectedFiles.length()));
					
		
			}

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
		}ReadFromFile(
				UsersRegistry.getInstance().getCurrentUser().getUserName() + "_" + selectedUser + "_ChatHistory.txt");

		
	}

	private void loadChat(User user) {
		chatUserName.setText(user.getDisplayName());

		ListView<String> messages = chats.get(user.getUserName());

		if (messages == null) {
			messages = new ListView<String>(); 

			messages.getItems().add("Conversation with " + user.getDisplayName());

			chats.put(user.getUserName(), messages);
		}
		messages.setPrefSize(1064, 632);
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
		
		SimpleDateFormat format = new SimpleDateFormat("HH:mm");
		String time = format.format(message.getTimeStamp());
		if (chatUserName.getText().compareTo(message.getSenderName()) == 0) {
			
		

			WriteToFile("[" + time + "]: " + message.getSenderName() + ": " + message.getMessageContent(),
					UsersRegistry.getInstance().getCurrentUser().getUserName() + "_" + message.getSenderName()
							+ "_ChatHistory.txt");

		} else {
			WriteToFile("[" + time + "]: " + message.getSenderName() + ": " + message.getMessageContent(),
					UsersRegistry.getInstance().getCurrentUser().getUserName() + "_"
							+  message.getSenderName() + "_ChatHistory.txt");

		}

	});
}


FileTransferResponse response; 
public void AcceptFileRequest(FileTransferRequest request)
{
	
	
	Platform.runLater(() -> {
		System.out.println("Got a File Transfer Request");
		User sender = UsersRegistry.getInstance().getUser(request.getSender());
		 
		Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
		alert.setTitle("Incomming File from "+ sender);
		alert.setContentText("Save?");
		ButtonType okButton = new ButtonType("Yes", ButtonBar.ButtonData.YES);
		ButtonType noButton = new ButtonType("No", ButtonBar.ButtonData.NO);
	
		alert.getButtonTypes().setAll(okButton, noButton);
		alert.showAndWait().ifPresent(type -> {
		        if (type == okButton) {
		        	System.out.println("File Transfer Request Accepted");
		        	response = new FileTransferResponse();
					response.setApproved(true);
					response.setMessage(request.getFileName());
					System.out.println("Recieved"+response);
					System.out.println("Sending response: "+ response);
				 	listener.writeResponse(response);
					
		        }
		        else {
		         	System.out.println("File Transfer Request Not Accepted");
		         	response = new FileTransferResponse();
		         	response.setApproved(false);
		         	response.setMessage("Not Accepted");
		         	System.out.println("Recieved"+response);
		         	System.out.println("Sending response");
		         	listener.writeResponse(response);
		        } 
		});
	
	});
	
	
	    
}

/*public void SendFileContent() throws FileNotFoundException
{
	String recipient = userList.getSelectionModel().getSelectedItem().getUserName();
	 Scanner scanner =
		      new Scanner(selectedFiles);
	scanner.useDelimiter("\\Z");
		    String contents = scanner.next();
		    System.out.println(contents);
		    scanner.close();
	 FileContentRequest fileRequest = new FileContentRequest(selectedFiles.getAbsolutePath(),recipient,UsersRegistry.getInstance().getCurrentUser().getUserName());
	
	System.out.println("Sending file request");
		listener.send(fileRequest);
}
	

public void receiveFile(FileContentRequest file)
{
	
	 BufferedWriter writer = null;
	
	SimpleDateFormat format = new SimpleDateFormat("HH:mm");
	String time = format.format(file.getTimeStamp());
	 System.out.println(file.getFileName()+" Send By:"+file.getSender()+" With size of:"+file.getContent());
	   String s = new String(file.getContent());
	   System.out.println(s);
	System.out.println(file.getFileName());
	    File newTextFile = new File(System.getProperty("user.home")  + File.separatorChar + "My Documents"+ File.separatorChar+"["+time+"]"+".txt");
	   try
	   {
	       writer = new BufferedWriter( new FileWriter(newTextFile));
	       writer.write(s);

	   }
	   catch ( IOException e)
	   {
	   }
	   finally
	   {
	       try
	       {
	           if ( writer != null)
	           writer.close();
	       }
	       catch ( IOException e)
	       {
	       }
	   }
	
        
        System.out.println("File saved to: "+System.getProperty("user.home") + File.separatorChar + "My Documents"+ File.separatorChar +"["+time+"]"+".txt" );
	

}
*/
public void WriteToFile(String content, String FileName) {
	String directory = System.getProperty("user.home");
	try (FileWriter fileWriter = new FileWriter(directory + File.separator + FileName, true)) {
		String fileContent = content;
		fileWriter.write(fileContent + System.lineSeparator());

	} catch (IOException e) {
		System.out.println("Error Writing to file!");
	}
}

void ReadFromFile(String FileName) {
	
	try{
		
		String directory = System.getProperty("user.home");
		
		String recipient = userList.getSelectionModel().getSelectedItem().getUserName();
		
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(directory + File.separator + FileName), "Cp1252"), 100);
		
		String line;
		    
          while ((line = br.readLine()) != null) {  
        	  
			chats.get(recipient).getItems().add(line);
          }
          
          br.close();	

	} catch (FileNotFoundException e) {
		System.out.println("File with name " + FileName + "not Found!");
	} catch (IOException e) {
		System.out.println("Error read to file!");
	}
}
}
