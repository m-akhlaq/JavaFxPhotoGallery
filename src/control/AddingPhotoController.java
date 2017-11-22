package control;

import java.io.File;
import java.net.MalformedURLException;
import java.nio.file.attribute.FileTime;
import java.util.ArrayList;
import java.util.Date;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.Album;
import model.Photo;
import model.Tag;
/**
 * This class acts as the controller for the adding photo view
 * @author Muhammad AKhlaq
 * @author John Brauner
 *
 */
public class AddingPhotoController {
	@FXML TextField captionField;
	@FXML Button filePickerButton;
	@FXML Button addButton;
	@FXML Label filePickerLabel;
	@FXML TextField tagField;
	@FXML Button addTagButton;
	@FXML Label tagListLabel;
	
	Album currentAlbum;
	Stage s;
	String filePath;
	long fileTime;
	Photo photoToBeAdded=new Photo();
	/**
	 * This function initilizes the current album that we are working with and adding photos to
	 * @param a the album to which the photo will be added
	 */
	public void initAlbum(Album a){
		currentAlbum=a;
	}
	/**
	 * initilizes the stage and starts the window
	 * @param s the current stage
	 */
	public void start(Stage s){
		this.s=s;
		s.setResizable(false);
	}
	/**
	 * this function starts when the user presses the browse button. It opens the file chooser and allows the user to pick
	 * the picture they want to insert.
	 * @param e action event for 'browse' button
	 */
	public void openFilePicker(ActionEvent e){
         FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog(s);
        if (file != null) {
            try {
				filePath=file.toURI().toURL().toExternalForm();
				fileTime=file.lastModified();
				filePickerLabel.setVisible(true);
				filePickerLabel.setText("Selected!");
			} catch (MalformedURLException e1) {
				e1.printStackTrace();
			}
        } else {
        	filePickerLabel.setVisible(true);
        	filePickerLabel.setText("Please Select a picture");
        }
	}
	/**
	 * this methods actually adds that photo to a certain album
	 * @param e action event for 'add photo' button
	 */
	public void addPhoto(ActionEvent e){
		if (filePath==null){
			 Alert alert = new Alert(AlertType.ERROR);
			 alert.setTitle("ERROR");
			 alert.setHeaderText("ERROR HAS OCCURED");
			 alert.setContentText("Please select the location of the photo");
			 alert.showAndWait();
		}else{
		photoToBeAdded.setCaption(captionField.getText());
		photoToBeAdded.setDate(new Date(fileTime));
		photoToBeAdded.setLocation(filePath);
		currentAlbum.addPhotos(photoToBeAdded);
		s.close();
		}
		
	}	
	/**
	 * this method opens up the add tag view
	 * @param e action event for 'add tag' button
	 */
    @FXML public void addTag(ActionEvent e){
		try{
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(
			getClass().getResource("/view/AddingTagView.fxml"));
			AnchorPane root =  (AnchorPane)loader.load();
	        Stage stage = new Stage();
	        stage.setTitle("Add A tag");
	        stage.setScene(new Scene(root, 321, 249));
			AddingTagController addingTagController =
			loader.getController();
		    addingTagController.start(stage,photoToBeAdded,photoToBeAdded);
		    stage.showAndWait();
			if (!photoToBeAdded.getTags().isEmpty()){
				tagListLabel.setText("Picture has: "+ photoToBeAdded.getTags().size()+" tag!");
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
    	
    	
	}
		

}
