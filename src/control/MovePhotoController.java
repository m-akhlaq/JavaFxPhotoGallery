package control;

import java.util.Optional;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import model.Album;
import model.Photo;
import model.User;

public class MovePhotoController {

	@FXML ToggleGroup group;
	@FXML ComboBox<Album> albumComboBox;
	Stage mainStage;
	Photo currentPhoto;
	Album currentAlbum;
	User currentUser;
	@FXML RadioButton cutRadioButton;
	@FXML RadioButton copyRadioButton;
	int indexOfPhoto; 
	private ObservableList<Album> albumList = FXCollections.observableArrayList();

	
	public void start(Stage s, Photo p, Album a, User u, int currentIndex){
		mainStage = s;
		currentPhoto = p;
		currentAlbum = a;
		currentUser = u;
		indexOfPhoto = currentIndex;
		albumList =  FXCollections.observableArrayList(currentUser.getAlbum());
		for(int x=0;x<albumList.size();x++){
			if (albumList.get(x).equals(currentAlbum)){
				albumList.remove(x);
			}
		}
		albumComboBox.setItems(albumList);

	}
	
	public void movePhoto(ActionEvent e){
		if (albumComboBox.getSelectionModel().getSelectedItem()!=null){
		if (copyRadioButton.isSelected()){
			albumComboBox.getSelectionModel().getSelectedItem().addPhotos(currentPhoto);
			Alert alert = new Alert(AlertType.INFORMATION);
			 alert.setTitle("Success!");
			 alert.setHeaderText("Photo Successfully Moved");
			 alert.setContentText("The Photo was Moved");
			 alert.showAndWait();
			 mainStage.close();
		}else if (cutRadioButton.isSelected()){
			Alert alert = new Alert(AlertType.CONFIRMATION);
			 alert.setTitle("Confirm your Action");
			 alert.setHeaderText("Please confirm");
			 alert.setContentText("Doing this will delete the photo from the original album and move it to the selected one! Continue?");
			 Optional<ButtonType> result = alert.showAndWait();
			 //ensures that the user presses OK before any changes are finalized.
			 if (result.get() == ButtonType.OK){
				albumComboBox.getSelectionModel().getSelectedItem().addPhotos(currentPhoto);
				currentAlbum.getPhotos().remove(indexOfPhoto);
				 alert.setTitle("Success!");
				 alert.setHeaderText("Photo Successfully Moved");
				 alert.setContentText("The Photo was Moved");
				 alert.showAndWait();
				 mainStage.close();
			 }
			
		}
		}else{
			Alert alert = new Alert(AlertType.ERROR);
			 alert.setTitle("ERROR");
			 alert.setHeaderText("ERROR HAS OCCURED");
			 alert.setContentText("Please select an album!");
			 alert.showAndWait();
		}
		
	}
	
}
