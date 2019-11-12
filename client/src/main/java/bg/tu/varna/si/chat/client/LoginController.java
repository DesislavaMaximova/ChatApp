package bg.tu.varna.si.chat.client;

import javafx.event.ActionEvent;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.sql.SQLException;

//import bg.tu.varna.si.chat.client.JdbcLoginDao;
import bg.tu.varna.si.chat.server.db.UserDAO;

public class LoginController {

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
		        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Registration.fxml"));
		        BorderPane root = (BorderPane) fxmlLoader.load();
		        Stage stage = new Stage();
		        Scene scene = new Scene(root);
		        stage.setTitle("Register User");
		        stage.setScene(scene);  
		        stage.show();
		    } catch(Exception e) {
		        e.printStackTrace();
		    }
		}
	
	
	@FXML
	protected void handleLogInButtonAction(ActionEvent event) throws SQLException {
		Window owner = logIn.getScene().getWindow();
		if(userNameField.getText().isEmpty()) {
			AlertHelper.showAlert(Alert.AlertType.ERROR, owner, "Form Error!", "Please enter your name");
			return;
		}
		if(passwordField.getText().isEmpty()) {
			AlertHelper.showAlert(Alert.AlertType.ERROR, owner, "Form Error!", "Please enter your password");
			return;
		}
		//AlertHelper.showAlert(Alert.AlertType.CONFIRMATION, owner, "Login Successful!", "Welcome " + userNameField.getText());

        String username = userNameField.getText();
        String password = passwordField.getText();
		//JdbcLoginDao jdbcDao = new JdbcLoginDao();
        //=================================================================================
        UserDAO userDao = UserDAO.getInstance();
        boolean flag = userDao.validate(username, password);
        //=================================================================================
        if (!flag) {
            infoBox("Please enter correct Username and Password", null, "Failed");
        } else {
            infoBox("Login Successful!", null, "Message");
            try {
    	        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ChatFrame.fxml"));
    	        BorderPane root = (BorderPane) fxmlLoader.load();
    	        stage = new Stage();
    	        Scene scene = new Scene(root);
    	        stage.setTitle("DNK Messenger");
    	        stage.setScene(scene);
    	        stage.show();
    	    } catch(Exception e) {
    	        e.printStackTrace();
    	    }
    		;
        }
    


		/*try {
	        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ChatFrame.fxml"));
	        BorderPane root = (BorderPane) fxmlLoader.load();
	        stage = new Stage();
	        Scene scene = new Scene(root);
	        stage.setTitle("DNK Messenger");
	        stage.setScene(scene);
	        stage.show();
	    } catch(Exception e) {
	        e.printStackTrace();
	    }
		;
		*/
	}
    public static void infoBox(String infoMessage, String headerText, String title) {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setContentText(infoMessage);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.showAndWait();
    }
		
}
