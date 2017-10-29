package control;

import java.io.File;
import java.net.MalformedURLException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.Album;
import model.Photo;

public class AddingPhotoController {
	@FXML TextField captionField;
	@FXML Button filePickerButton;
	@FXML Button addButton;
	Album currentAlbum;
	Stage s;
	String filePath;
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
			} catch (MalformedURLException e1) {
				e1.printStackTrace();
			}
        }
	}
	public void addPhoto(ActionEvent e){
		currentAlbum.addPhotos(new Photo(captionField.getText(),filePath));
		((Node)(e.getSource())).getScene().getWindow().hide();
	}

}
