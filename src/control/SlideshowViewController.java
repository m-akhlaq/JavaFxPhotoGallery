package control;

import java.util.ArrayList;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import model.Album;
import model.Photo;

public class SlideshowViewController {
	
	Album album;
	@FXML ImageView photoImageView;
	@FXML Button infoHoverButton;
	Stage stage;
	ArrayList<Photo> listOfPhotos = new ArrayList<Photo>();
	int photoNumber=0;
	public void initSlideshow(Album a){
		album=a;
		listOfPhotos = a.getPhotos();
		Photo photo = listOfPhotos.get(photoNumber);
		Image photoImage = new Image(photo.getLocation(),610,450,false,false);
		photoImageView.setImage(photoImage);
		Image infoImage = new Image("file:/C:/Users/shahe/Git/Photos15/src/pictures/info.png",35,34,false,false);
        infoHoverButton.setGraphic(new ImageView(infoImage));
        Tooltip tt = new Tooltip();
        tt.setText(photo.printAttributes());
        tt.setStyle("-fx-font: normal bold 20 Langdon; "
            + "-fx-base: #AE3522; "
            + "-fx-text-fill: orange;");
        infoHoverButton.setTooltip(tt);
	}
	
	public void start(Stage s){
		stage=s;
	}
	
	@FXML public void goForward(ActionEvent e){
		photoNumber++;
		if (photoNumber==listOfPhotos.size()){
			photoNumber=0;
		}
		Photo photo = listOfPhotos.get(photoNumber);
		Image photoImage = new Image(photo.getLocation(),610,450,false,false);
		photoImageView.setImage(photoImage);
        Tooltip tt = new Tooltip();
        tt.setText(photo.printAttributes());
        tt.setStyle("-fx-font: normal bold 20 Langdon; "
            + "-fx-base: #AE3522; "
            + "-fx-text-fill: orange;");
        infoHoverButton.setTooltip(tt);
	}
	
	@FXML public void goBackward(ActionEvent e){
		photoNumber--;
		if (photoNumber<0){
			photoNumber=listOfPhotos.size()-1;
		}
		Photo photo = listOfPhotos.get(photoNumber);
		Image photoImage = new Image(photo.getLocation(),610,450,false,false);
		photoImageView.setImage(photoImage);
        Tooltip tt = new Tooltip();
        tt.setText(photo.printAttributes());
        tt.setStyle("-fx-font: normal bold 20 Langdon; "
            + "-fx-base: #AE3522; "
            + "-fx-text-fill: orange;");
        infoHoverButton.setTooltip(tt);
	}

}
