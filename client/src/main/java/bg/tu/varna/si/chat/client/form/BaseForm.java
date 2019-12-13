package bg.tu.varna.si.chat.client.form;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public abstract class BaseForm {

	public void alert(AlertType alertType, String title, String content) {
		Alert alert = new Alert(alertType);
		alert.setTitle(title);
		alert.setHeaderText(null);
		alert.setContentText(content);
		alert.showAndWait();
	}
}
