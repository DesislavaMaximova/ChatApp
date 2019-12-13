package bg.tu.varna.si.chat.client.form;

import java.sql.SQLException;

import bg.tu.varna.si.chat.client.Client;
import bg.tu.varna.si.chat.model.request.UserRegisterRequest;
import bg.tu.varna.si.chat.model.response.ErrorResponse;
import bg.tu.varna.si.chat.model.response.Response;
import bg.tu.varna.si.chat.model.response.ResponseType;
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
import javafx.stage.Window;

public class RegistrerUser extends BaseForm {

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
	private Button closeButton;

	Window owner = null;

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
		
		Response response = Client.getInstance().send(registerRequest);

		if (ResponseType.LOGIN == response.getResponseType()) {
			
			try {
				Stage currentStage = (Stage) registerButton.getScene().getWindow();
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
		
		if (ResponseType.ERROR == response.getResponseType()) {
			ErrorResponse errorResponse = (ErrorResponse) response;
			
			alert(AlertType.ERROR, "Error", errorResponse.getErrorMessage());
		}
		
	}

	public void close(ActionEvent event) {
		Stage stage = (Stage) closeButton.getScene().getWindow();
		stage.close();
	}
}
