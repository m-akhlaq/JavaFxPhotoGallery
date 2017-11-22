package control;

import java.util.ArrayList;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import model.Album;
import model.Photo;
/**
 * 
 * @author Muhammad Akhlaq
 * @author John Brauner
 * This class acts as the controller for the slideshow view
 */
public class SlideshowViewController {
	
	Album album;
	@FXML ImageView photoImageView;
	@FXML Button infoHoverButton;
	Stage stage;
	ArrayList<Photo> listOfPhotos = new ArrayList<Photo>();
	int photoNumber=0;
	/**
	 * initilizes the slideshow and displays the first image to the user
	 * @param a the album though which you want to run your slideshow through
	 */
	public void initSlideshow(Album a){
		album=a;
		listOfPhotos = album.getPhotos();
		Photo photo = listOfPhotos.get(photoNumber);
		Image photoImage = new Image(photo.getLocation(),610,450,false,false);
		photoImageView.setImage(photoImage);
		Image img = new Image("file:resources/info.png",35,34,false,false);
        infoHoverButton.setGraphic(new ImageView(img));
        Tooltip tt = new Tooltip();
        tt.setText(photo.printAttributes());
        tt.setStyle("-fx-font: normal bold 20 Langdon; "
            + "-fx-base: #AE3522; "
            + "-fx-text-fill: orange;");
        infoHoverButton.setTooltip(tt);
	}
	/**
	 * initilizes the stage
	 * @param s the current stage
	 */
	public void start(Stage s){
		stage=s;
		stage.setResizable(false);
	}
	/**
	 * changes the photo and goes forward in the slideshow
	 * @param e the event that occurs when the forward button is pressed
	 */
	
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
	/**
	 * changes the photo and moves to the previous photo in the slideshow
	 * @param e the event that occurs when the go back button is pressed
	 */
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
	/**
	 * this method shows the information of the photo is a dialogue box
	 * @param e actionEvent that is triggered
	 */
	@FXML public void showInfo(ActionEvent e){
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Picture Information");
		alert.setHeaderText("Current Photo Information");
		alert.setContentText(listOfPhotos.get(photoNumber).printAttributes());

		alert.showAndWait();
	}

}
