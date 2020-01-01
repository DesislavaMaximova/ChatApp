package bg.tu.varna.si.chat.client;

import java.io.IOException;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;


public class ClientLauncher extends Application {

	public static final String SERVER_HOST = "localhost";
	public static final int SERVER_PORT = 1300;

	public static void main(String[] args) {

		launch(args);
	}

	@Override
	public void start(Stage primaryStage) {

		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/Login.fxml"));
			BorderPane root = (BorderPane) loader.load();
			Scene scene = new Scene(root, 800, 600);
			primaryStage.setTitle("User LogIn");
			primaryStage.setScene(scene);
			primaryStage.show();
			
			primaryStage.setOnCloseRequest(e -> {
				System.out.println("Shutting down client application.");
				Platform.exit();
				System.exit(0);
			});
			
		} catch (IOException e) {
			throw new IllegalStateException(e);
		}

	}

}