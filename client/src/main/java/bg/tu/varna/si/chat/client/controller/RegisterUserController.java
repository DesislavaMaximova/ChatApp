package bg.tu.varna.si.chat.client.controller;

import java.sql.SQLException;
import java.util.LinkedList;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import bg.tu.varna.si.chat.client.ClientLauncher;
import bg.tu.varna.si.chat.client.Listener;
import bg.tu.varna.si.chat.client.UsersRegistry;
import bg.tu.varna.si.chat.model.request.UserRegisterRequest;
import bg.tu.varna.si.chat.model.response.ErrorResponse;
import bg.tu.varna.si.chat.model.response.LoginResponse;
import bg.tu.varna.si.chat.model.response.Response;
import bg.tu.varna.si.chat.model.response.ResponseType;

public class RegisterUserController extends BaseController {

	@FXML
	private TextField userNameField;

	@FXML
	private TextField firstNameField;

	@FXML
	private TextField lastNameField;

	@FXML
	private TextField displayNameField;

	@FXML
	private PasswordField passwordField;

	@FXML
	private Button registerButton;

	@FXML
	public void register(ActionEvent event) throws SQLException {

		Stage stage = (Stage) registerButton.getScene().getWindow();
		
		String firstName = firstNameField.getText();
		String lastName = lastNameField.getText();
		String userName = userNameField.getText();
		String password = passwordField.getText();
		String displayName = displayNameField.getText();
		
		if (userNameField.getText().isEmpty()) {
			alert(AlertType.ERROR, "Error", "Please enter desired username");
			return;
		}

		if (firstNameField.getText().isEmpty()) {
			alert(AlertType.ERROR, "Error", "Please enter your first name");
			return;
		}

		if (lastNameField.getText().isEmpty()) {
			alert(AlertType.ERROR, "Error", "Please enter your last name");
			return;
		}

		if (displayNameField.getText().isEmpty()) {
			alert(AlertType.ERROR, "Error", "Please enter your display name");
			return;
		}

		if (passwordField.getText().isEmpty()) {
			alert(AlertType.ERROR, "Error", "Please enter a password");
			return;
		}
		

		UserRegisterRequest registerRequest = new UserRegisterRequest(
				userName, firstName, lastName, displayName, password);
		
		listener = new Listener(ClientLauncher.SERVER_HOST, ClientLauncher.SERVER_PORT);
		
		Response response = listener.sendAndReceive(registerRequest);
		
		if (ResponseType.ERROR == response.getResponseType()) {
			ErrorResponse errorResponse = (ErrorResponse) response;
			
			alert(AlertType.ERROR, "Error", errorResponse.getErrorMessage());
			return;
		}
		
		if (ResponseType.LOGIN == response.getResponseType()) {
			
			LoginResponse loginResponse = (LoginResponse) response;
			UsersRegistry.getInstance().setCurrentUser(loginResponse.getCurrentUser());
			UsersRegistry.getInstance().setUsers(new LinkedList<>(loginResponse.getUsers()));
			
			try {
				Stage currentStage = (Stage) registerButton.getScene().getWindow();
				currentStage.close();
				
				FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Chat.fxml"));
				BorderPane root = (BorderPane) fxmlLoader.load();
				stage = new Stage();
				Scene scene = new Scene(root);
				stage.setTitle("DNK Messenger: " + UsersRegistry.getInstance().getCurrentUser().getDisplayName());
				stage.setScene(scene);
				stage.show();
				
				listener.setChatController(fxmlLoader.getController());
				
				stage.setOnCloseRequest(e -> {
					System.out.println("Shutting down client application.");
					Platform.exit();
					System.exit(0);
				});
				
			} catch (Exception e) {
				throw new IllegalStateException(e);
			}
		
			new Thread(listener).start();
		}
	}
	
}
