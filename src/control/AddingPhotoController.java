package control;

import java.io.File;
import java.net.MalformedURLException;
import java.nio.file.attribute.FileTime;
import java.util.ArrayList;
import java.util.Date;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.Album;
import model.Photo;
import model.Tag;

public class AddingPhotoController {
	@FXML TextField captionField;
	@FXML Button filePickerButton;
	@FXML Button addButton;
	@FXML Label filePickerLabel;
	@FXML TextField tagField;
	
	Album currentAlbum;
	Stage s;
	String filePath;
	long fileTime;
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
	public void addPhoto(ActionEvent e){
		String caption = captionField.getText();
		String tags = tagField.getText();
		ArrayList<Tag> tagList = getTags(tags);
		if (caption.length()==0 || tags.length()==0 || filePath==null){
			 Alert alert = new Alert(AlertType.ERROR);
			 alert.setTitle("ERROR");
			 alert.setHeaderText("ERROR HAS OCCURED");
			 alert.setContentText("Please fill out all fields to continue");
			 alert.showAndWait();
		}else{
		currentAlbum.addPhotos(new Photo(captionField.getText(),filePath,tagList,new Date(fileTime)));
		((Node)(e.getSource())).getScene().getWindow().hide();
		}
	}
	
	/**
	 * This function takes in the raw input from the tags textfield and converts it into an arraylist of tags that
	 * can be passed to the photo object.
	 * @param tags gets the tags the user types in from the tag field
	 * @return An ArrayList<Tag>
	 */
	
	public ArrayList<Tag> getTags(String tags){
		tags.trim();
		String [] individualTags;
		ArrayList<Tag> tagList = new ArrayList<Tag>();
		if (tags.contains(","))
		  individualTags = tags.split(",");
		else {
			individualTags = new String[1];
			individualTags[0]=tags;
		}
		for(String s:individualTags){
			String[] keyValuePair = s.split("=");
			tagList.add(new Tag(keyValuePair[0],keyValuePair[1]));
		}
		return tagList;
	}

}
