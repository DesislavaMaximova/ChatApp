package bg.tu.varna.si.chat.client;

import java.sql.SQLException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.Window;

public class RegisterController {

		@FXML
	    private TextField firstNameField;

	    @FXML
	    private TextField lastNameField;

	    @FXML
	    private TextField userNameField;

	    @FXML
	    private PasswordField passwordField;

	    @FXML
	    private Button registerButton;

	    @FXML
	    private Button closeButton;
	    
	    @FXML
	    public void register(ActionEvent event) throws SQLException {

	        Window owner = registerButton.getScene().getWindow(); 
	        
	        System.out.println(firstNameField.getText());
	        System.out.println(lastNameField.getText());
	        System.out.println(userNameField.getText());
	        System.out.println(passwordField.getText());

	       if (firstNameField.getText().isEmpty()) {
	            showAlert(Alert.AlertType.ERROR, owner, "Form Error!",
	                "Please enter your firstname");
	            return;
	        }
	        
	        if (lastNameField.getText().isEmpty()) {
	            showAlert(Alert.AlertType.ERROR, owner, "Form Error!",
	                "Please enter your lastname");
	            return;
	        }

	        if (userNameField.getText().isEmpty()) {
	            showAlert(Alert.AlertType.ERROR, owner, "Form Error!",
	                "Please enter your username");
	            return;
	        }
	        
	        if (passwordField.getText().isEmpty()) {
	            showAlert(Alert.AlertType.ERROR, owner, "Form Error!",
	                "Please enter a password");
	            return;
	        }
	        
	        String firstName = firstNameField.getText();
	        String lastName = lastNameField.getText();
	        String userName = userNameField.getText();
	        String password = passwordField.getText();

	        JdbcRegisterDao jdbcRegisterDao = new JdbcRegisterDao();
	        jdbcRegisterDao.insertRecord(firstName,lastName, userName, password);

	          showAlert(Alert.AlertType.CONFIRMATION, owner, "Registration Successful!",
	            "Welcome " + firstNameField.getText());
	    }

	    private static void showAlert(Alert.AlertType alertType, Window owner, String title, String message) {
	        Alert alert = new Alert(alertType);
	        alert.setTitle(title);
	        alert.setHeaderText(null);
	        alert.setContentText(message);
	        alert.initOwner(owner);
	        alert.show();
	    }
	    public void close(ActionEvent event) throws SQLException {
	    	 Stage stage = (Stage) closeButton.getScene().getWindow();
	    	 stage.close();
	    }
	}