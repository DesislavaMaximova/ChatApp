package bg.tu.varna.si.chat.client.controller;

import bg.tu.varna.si.chat.client.Listener;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public abstract class BaseController {
	
	protected static Listener listener;

	public void alert(AlertType alertType, String title, String content) {
		Alert alert = new Alert(alertType);
		alert.setTitle(title);
		alert.setHeaderText(null);
		alert.setContentText(content);
		alert.showAndWait();
	}
}
