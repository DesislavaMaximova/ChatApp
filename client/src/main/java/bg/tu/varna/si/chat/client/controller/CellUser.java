package bg.tu.varna.si.chat.client.controller;

import java.io.File;

import bg.tu.varna.si.chat.model.User;
import javafx.geometry.Pos;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.util.Callback;

public class CellUser implements Callback<ListView<User>,ListCell<User>>{
        @Override
    public ListCell<User> call(ListView<User> p) {

        ListCell<User> cell = new ListCell<User>(){

            @Override
            protected void updateItem(User user, boolean bln) {
                super.updateItem(user, bln);
                setGraphic(null);
                setText(null);
                if (user != null) {
                    HBox hBox = new HBox();

                    Text name = new Text(user.getDisplayName());
                    ImageView statusImageView = new ImageView();
                    if (user.isActive() == true) {
                    	File file = new File("src/main/resources/online.png");
                    Image statusImage = new Image(file.toURI().toString());
                    statusImageView.setImage(statusImage);
                    
                    }else {
                    	File file = new File ("src/main/resources/away.png");
                    	Image statusImage = new Image(file.toURI().toString());
                    	statusImageView.setImage(statusImage);
                    }
                    hBox.getChildren().addAll(statusImageView, name );
                    hBox.setAlignment(Pos.CENTER_LEFT);

                    setGraphic(hBox);
                }
            }
        };
        return cell;
    }
}


