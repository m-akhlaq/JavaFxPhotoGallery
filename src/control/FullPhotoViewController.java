package control;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import model.Photo;
/**
 * this class acts as the controller for the fullPhotoView which displays the selected pciture in full screen
 * @author Muhammad Akhlaq
 * @author John Brauner
 *
 */
public class FullPhotoViewController {

	Photo photo;
	Stage mainStage;
	@FXML ImageView photoImageView;
	@FXML Button infoHoverButton;
	/**
	 * loads the photo and sets the tooltip for information
	 * @param p photo that is being displayed
	 */
	public void initPhoto(Photo p){
		photo=p;
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
	 * initilizes that stage and sets the image
	 * @param primaryStage the current stage
	 */
	public void start(Stage primaryStage){
		mainStage=primaryStage;
		mainStage.setResizable(false);
		Image img = new Image(photo.getLocation(),610,450,false,false);
		photoImageView.setImage(img);
	}
	/**
	 * this method shows the information of the photo is a dialogue box
	 * @param e actionEvent that is triggered
	 */
	@FXML public void showInfo(ActionEvent e){
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Picture Information");
		alert.setHeaderText("Current Photo Information");
		alert.setContentText(photo.printAttributes());

		alert.showAndWait();
	}
	

	
	
}
