package bg.tu.varna.si.chat.client.controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bg.tu.varna.si.chat.client.UsersRegistry;
import bg.tu.varna.si.chat.model.User;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.cell.CheckBoxListCell;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Callback;

public class GroupChatController extends BaseController {

	@FXML
	private Pane usersPane;

//	@FXML
//	private TextField groupName;

	@FXML
	private ListView<User> users;

	@FXML
	private Label chatUserName;

	@FXML
	private Button OK;

	@FXML
	private Button Cancel;

	public void start(Stage primaryStage) {
        try {
           
            List<User> list = new ArrayList<>();
          
            users.getItems().addAll(UsersRegistry.getInstance().getUsers());
            users.setCellFactory(CheckBoxListCell.forListView(new Callback<User, ObservableValue<Boolean>>() {
                @Override
                public ObservableValue<Boolean> call(User item) {
                    BooleanProperty observable = new SimpleBooleanProperty();
                    observable.addListener((obs, wasSelected, isNowSelected) -> {
                        if (isNowSelected) {
                            list.add(item);
                        } else {
                            list.remove(item);
                        }
                    });
                    return observable;
                }
            }));
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
	
	@FXML
	public void getInformation (ActionEvent event) throws SQLException {
		
	}
	

	
}
