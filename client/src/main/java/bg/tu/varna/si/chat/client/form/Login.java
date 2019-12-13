package bg.tu.varna.si.chat.client.form;

import java.sql.SQLException;

import bg.tu.varna.si.chat.client.Client;
import bg.tu.varna.si.chat.model.Credentials;
import bg.tu.varna.si.chat.model.request.LoginRequest;
import bg.tu.varna.si.chat.model.response.ErrorResponse;
import bg.tu.varna.si.chat.model.response.Response;
import bg.tu.varna.si.chat.model.response.ResponseType;
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

public class Login extends BaseForm {

	@FXML
	private TextField userNameField;

	@FXML
	private PasswordField passwordField;

	@FXML
	private Button signUp;

	@FXML
	private Button logIn;

	@FXML
	Stage stage;

	@FXML
	protected void handleSignUpButtonAction(ActionEvent event) throws Exception {

		try {
			Stage currentStage = (Stage) logIn.getScene().getWindow();
			currentStage.close();

			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Registration.fxml"));
			Parent root = (Parent) fxmlLoader.load();
			Stage stage = new Stage();
			stage.setTitle("Register User");
			stage.setScene(new Scene(root));
			stage.show();
		} catch (Exception e) {
			e.printStackTrace();
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

		Credentials credentials = new Credentials();
		credentials.setUserName(userNameField.getText());
		credentials.setPassword(passwordField.getText());
		LoginRequest login = new LoginRequest(credentials);
		Response response = Client.getInstance().send(login);

		if (ResponseType.ERROR == response.getResponseType()) {
			ErrorResponse errorResponse = (ErrorResponse) response;
			alert(AlertType.ERROR, "Error", errorResponse.getErrorMessage());
			return;
		}

		try {
			Stage currentStage = (Stage) logIn.getScene().getWindow();
			currentStage.close();
			
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/ChatFrame.fxml"));
			BorderPane root = (BorderPane) fxmlLoader.load();
			stage = new Stage();
			Scene scene = new Scene(root);
			stage.setTitle("DNK Messenger");
			stage.setScene(scene);
			stage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
