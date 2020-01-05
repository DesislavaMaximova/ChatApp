package bg.tu.varna.si.chat.client.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.LinkedList;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import bg.tu.varna.si.chat.client.Listener;
import bg.tu.varna.si.chat.client.UsersRegistry;
import bg.tu.varna.si.chat.model.Credentials;
import bg.tu.varna.si.chat.model.request.LoginRequest;
import bg.tu.varna.si.chat.model.response.ErrorResponse;
import bg.tu.varna.si.chat.model.response.LoginResponse;
import bg.tu.varna.si.chat.model.response.Response;
import bg.tu.varna.si.chat.model.response.ResponseType;

public class LoginController extends BaseController {

	@FXML
	private TextField userNameField;

	@FXML
	private PasswordField passwordField;

	@FXML
	private Button signUp;

	@FXML
	private Button logIn;

	private static LoginController INSTANCE_HOLDER;

	public static ChatController chatController;


	public LoginController() {
		INSTANCE_HOLDER = this;
	}

	public static LoginController getInstance() {
		return INSTANCE_HOLDER;
	}


	@FXML
	protected void handleSignUpButtonAction(ActionEvent event) throws Exception {

		try {
			Stage currentStage = (Stage) logIn.getScene().getWindow();
			currentStage.close();

			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/RegisterUser.fxml"));
			Parent root = (Parent) fxmlLoader.load();
			Stage stage = new Stage();
			stage.setTitle("Register User");
			stage.setScene(new Scene(root));
			stage.show();
		} catch (IOException e) {
			throw new IllegalStateException(e);
		}
	}

	@FXML
	protected void handleLogInButtonAction(ActionEvent event) throws SQLException {
		
		if (userNameField.getText().isEmpty()) {
			alert(AlertType.ERROR, "Error", "Please enter your username.");
			return;
		}
		
		if (passwordField.getText().isEmpty()) {
			alert(AlertType.ERROR, "Error", "Please enter your password.");
			return;
		}
		
		listener = new Listener("localhost", 1300);


		Credentials credentials = new Credentials();
		credentials.setUserName(userNameField.getText());
		credentials.setPassword(passwordField.getText());
		LoginRequest login = new LoginRequest(credentials);

		Response response = listener.sendAndReceive(login);
		
		LoginResponse loginResponse = null;

		if (ResponseType.LOGIN == response.getResponseType()) {
			loginResponse = (LoginResponse) response;
			UsersRegistry.getInstance().setUsers(new LinkedList<>(loginResponse.getUsers()));
			UsersRegistry.getInstance().setCurrentUser(loginResponse.getCurrentUser());
			
			System.out.println("\n\n\n Undelivered messages \n");
			System.out.println(loginResponse.getUndeliveredMessages());
		}

		if (ResponseType.ERROR == response.getResponseType()) {
			ErrorResponse errorResponse = (ErrorResponse) response;
			alert(AlertType.ERROR, "Error", errorResponse.getErrorMessage());
			return;
		}

		try {
			Stage currentStage = (Stage) logIn.getScene().getWindow();
			currentStage.close();

			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Chat.fxml"));
			BorderPane root = (BorderPane) fxmlLoader.load();
			
			ChatController chatController = fxmlLoader.<ChatController>getController();
			chatController.setUndeliveredMessages(loginResponse.getUndeliveredMessages());
			
			Stage stage = new Stage();
			Scene scene = new Scene(root);
			stage.setTitle("DNK Messenger: " + UsersRegistry.getInstance().getCurrentUser().getDisplayName());
			stage.setScene(scene);
			stage.show();
			
			listener.setChatController(chatController);
			
			stage.setOnCloseRequest(e -> {
				System.out.println("Shutting down client application.");
				Platform.exit();
				System.exit(0);
			});
			
		} catch (IOException e) {
			throw new IllegalStateException(e);
		}

		new Thread(listener).start();

	}


}
