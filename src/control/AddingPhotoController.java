package control;

import java.io.File;
import java.net.MalformedURLException;
import java.nio.file.attribute.FileTime;
import java.util.ArrayList;

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
	public void initAlbum(Album a){
		currentAlbum=a;
	}
	public void start(Stage s){
		this.s=s;
	}
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
		if (caption.length()==0 || tags.length()==0 || filePath==null){
			 Alert alert = new Alert(AlertType.ERROR);
			 alert.setTitle("ERROR");
			 alert.setHeaderText("ERROR HAS OCCURED");
			 alert.setContentText("Please fill out all fields to continue");
			 alert.showAndWait();
		}else{
		currentAlbum.addPhotos(new Photo(captionField.getText(),filePath,new ArrayList<Tag>(),fileTime));
		((Node)(e.getSource())).getScene().getWindow().hide();
		}
	}

}
