package control;

import java.util.ArrayList;
import java.util.Date;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.Album;
import model.Photo;
import model.Tag;

public class EditingPhotoController {

	@FXML TextField captionField;
	@FXML Button editButton;
	@FXML TextField tagField;
	@FXML Button addTagButton;
	@FXML Button showDeleteButton;
	@FXML Button deleteTagButton;
	@FXML Button cancelButton;
	@FXML ComboBox<Tag> tagComboBox;
	Album currentAlbum;
	Stage s;
	Photo currentPhoto;
	Photo fakePhoto = new Photo();
	private ObservableList<Tag> tagList = FXCollections.observableArrayList();

	
	
	public void initPhoto(Photo p){
		currentPhoto=p;
	}
	
	public void start(Stage s){
		this.s=s;
		captionField.setText(currentPhoto.getCaption());
		tagList =  FXCollections.observableArrayList(currentPhoto.getTags());

	}
	
	public void editPhoto(ActionEvent e){
		String caption = captionField.getText();
		currentPhoto.getTags().clear();
		currentPhoto.getTags().addAll(tagList);
		if (!fakePhoto.getTags().isEmpty()){
			currentPhoto.getTags().addAll(fakePhoto.getTags());
		}		
			currentPhoto.setCaption(caption);
			s.close();
	}
    @FXML public void addTag(ActionEvent e){
		try{
			System.out.println(currentPhoto.getTags());
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(
			getClass().getResource("/view/AddingTagView.fxml"));
			AnchorPane root =  (AnchorPane)loader.load();
	        Stage stage = new Stage();
	        stage.setTitle("Add A tag");
	        stage.setScene(new Scene(root, 321, 249));
			AddingTagController addingTagController =
			loader.getController();
		    addingTagController.start(stage,fakePhoto);
		    stage.showAndWait();
		    
		}catch(Exception ex){
			ex.printStackTrace();
		}
    	
    	
	}
    
    @FXML public void showDeleteMenu(ActionEvent e){
    	showDeleteButton.setVisible(false);
    	cancelButton.setVisible(true);
    	deleteTagButton.setVisible(true);
    	tagComboBox.setVisible(true);
		tagComboBox.setItems(tagList);
    }
    @FXML public void resetMenu(ActionEvent e){
    	showDeleteButton.setVisible(true);
    	cancelButton.setVisible(false);
    	deleteTagButton.setVisible(false);
    	tagComboBox.setVisible(false);
    	
    }
    @FXML public void deleteTag(ActionEvent e){
    	if (tagComboBox.getSelectionModel().getSelectedItem()!=null){
    		tagList.remove(tagComboBox.getSelectionModel().getSelectedIndex());
    	}
    }
	
	
	
}
