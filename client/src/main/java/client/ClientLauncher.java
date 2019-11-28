package client;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class ClientLauncher extends Application {

	public static void main(String[] args) {
		try {
			launch (args);
			Client client = new Client(InetAddress.getLocalHost(), 1300);
			client.sendMessages();
		
			
		} catch (UnknownHostException e) {
			System.out.println("Host ID not found!");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//@Override
	public void start(Stage primaryStage) {
		try {
		
			
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/Registration.fxml"));
			BorderPane root = (BorderPane)loader.load();
			Scene scene = new Scene(root,600,400);
			primaryStage.setTitle("User LogIn");
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

}