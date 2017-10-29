package control;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import model.Photo;

public class FullPhotoViewController {

	Photo photo;
	@FXML ImageView photoImageView;
	@FXML Button infoHoverButton;
	public void initPhoto(Photo p){
		photo=p;
		Image img = new Image("file:/C:/Users/shahe/Git/Photos15/src/info.png",35,34,false,false);
        infoHoverButton.setGraphic(new ImageView(img));
        Tooltip tt = new Tooltip();
        tt.setText(photo.printAttributes());
        tt.setStyle("-fx-font: normal bold 20 Langdon; "
            + "-fx-base: #AE3522; "
            + "-fx-text-fill: orange;");
        infoHoverButton.setTooltip(tt);
	}
	
	public void start(Stage primaryStage){
		Image img = new Image(photo.getLocation(),610,450,false,false);
		photoImageView.setImage(img);
	}
	
	
}
