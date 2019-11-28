package client;

import java.sql.SQLException;

import bg.tu.varna.si.chat.model.request.LogoutRequest;
import bg.tu.varna.si.chat.model.request.UserRegisterRequest;
import bg.tu.varna.si.chat.model.response.Response;
import bg.tu.varna.si.chat.model.response.ResponseType;
import client.AlertHelper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.Window;

public class RegistrerUser {
	Client client;
	
	

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
		owner = registerButton.getScene().getWindow(); 
      
        
      /*  System.out.println(userNameField.getText());
        System.out.println(firstNameField.getText());
        System.out.println(lastNameField.getText());
        System.out.println(displayNameField.getText());
        System.out.println(passwordField.getText()); */
        
		String firstName = firstNameField.getText();
		String lastName = lastNameField.getText();
		String userName = userNameField.getText();
		String password = passwordField.getText();
		String displayName = displayNameField.getText();
		
	

		UserRegisterRequest registerRequest = new UserRegisterRequest(userName, firstName, lastName, displayName,
				password);
		client.sendRequest(registerRequest);

		Response response = (Response) client.readResponse();
		System.out.println(response);
		if (ResponseType.REGISTER == response.getResponseType()) {
			AlertHelper.showAlert(Alert.AlertType.CONFIRMATION, owner, "Registration Successful!",
					"Welcome " + firstNameField.getText());
			
		}
        if (userNameField.getText().isEmpty()) {
            AlertHelper.showAlert(Alert.AlertType.ERROR, owner, "Form Error!",
                "Please enter username");
            return;
        }

       if (firstNameField.getText().isEmpty()) {
           AlertHelper.showAlert(Alert.AlertType.ERROR, owner, "Form Error!",
                "Please enter your first name");
            return;
        }
        
        if (lastNameField.getText().isEmpty()) {
            AlertHelper.showAlert(Alert.AlertType.ERROR, owner, "Form Error!",
                "Please enter your last name");
            return;
        }

        if (displayNameField.getText().isEmpty()) {
           AlertHelper.showAlert(Alert.AlertType.ERROR, owner, "Form Error!",
                "Please enter your dysplay name");
            return;
        }
        
        if (passwordField.getText().isEmpty()) {
            AlertHelper.showAlert(Alert.AlertType.ERROR, owner, "Form Error!",
                "Please enter a password");
            return;
        }
    }

	/*public void registerUser() {
		String firstName = firstNameField.getText();
		String lastName = lastNameField.getText();
		String userName = userNameField.getText();
		String password = passwordField.getText();
		String displayName = displayNameField.getText();
		
		owner = registerButton.getScene().getWindow(); 

		UserRegisterRequest registerRequest = new UserRegisterRequest(userName, firstName, lastName, displayName,
				password);
		client.sendRequest(registerRequest);

		Response response = (Response) client.readResponse();
		System.out.println(response);
		if (ResponseType.ACKNOWLEGE == response.getResponseType()) {
			AlertHelper.showAlert(Alert.AlertType.CONFIRMATION, owner, "Registration Successful!",
					"Welcome " + firstNameField.getText());
			
		}
	}*/

	public void close(ActionEvent event) throws SQLException {
		Stage stage = (Stage) closeButton.getScene().getWindow();
		LogoutRequest logout = new LogoutRequest();
		client.sendRequest(logout);
		stage.close();
	}
}
