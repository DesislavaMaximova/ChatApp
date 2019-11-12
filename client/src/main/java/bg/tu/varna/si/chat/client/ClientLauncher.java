package bg.tu.varna.si.chat.client;

import java.net.InetAddress;
import java.net.UnknownHostException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class ClientLauncher extends Application{

	public static void main(String[] args) {
		try {
			Client client = new Client(InetAddress.getLocalHost(), 1300);
			client.sendMessages();
			launch(args);
		} catch (UnknownHostException e) {
			System.out.println("Host ID not found!");
		}
	}

	@Override
	public void start(Stage primaryStage) {
		try {
			BorderPane root = (BorderPane)FXMLLoader.load(getClass().getResource("Login.fxml"));
			Scene scene = new Scene(root,600,400);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setTitle("User LogIn");
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

}
