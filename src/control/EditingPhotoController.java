package control;

import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import model.Album;
import model.Photo;
import model.Tag;
/**
 * 
 * @author Muhammad Akhlaq
 * @author John Brauner
 *
 */
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

	
	/**
	 * initlizes the current photo being edited
	 * @param p photo that is being edited
	 */
	public void initPhoto(Photo p){
		currentPhoto=p;
	}
	/**
	 * initilizes the fields and sets up the on close request
	 * @param s current stage
	 */
	
	public void start(Stage s){
		this.s=s;
		s.setResizable(false);
		captionField.setText(currentPhoto.getCaption());
		tagList =  FXCollections.observableArrayList(currentPhoto.getTags());
		s.setOnCloseRequest(new EventHandler<WindowEvent>() {
		    @Override
		    public void handle(WindowEvent event) {
		        // consume event
		        event.consume();
		        // show close dialog
		        Alert alert = new Alert(AlertType.CONFIRMATION);
		        alert.setTitle("Exit Application");
		        alert.setHeaderText("Are you sure you want to Exit?");
		        alert.setContentText("Any changes you have made will NOT be saved! Continue?");
		        ButtonType dontSaveAndExit = new ButtonType("OK");
		        ButtonType cancel = new ButtonType("Cancel", ButtonData.CANCEL_CLOSE);
		        alert.getButtonTypes().setAll(dontSaveAndExit,cancel);
		        Optional<ButtonType> result = alert.showAndWait();
		        if (result.get() == dontSaveAndExit){
		        	s.close();
		        }else{
		        	alert.close();
		        }
		        
		    }
		});

	}
	/**
	 * actaully edits the photo info
	 * @param e action event that is triggered as a result of clicking the button
	 */
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
	/**
	 * adds tags to the photo
	 * @param e action event that is triggered as a result of clicking the button
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
		    addingTagController.start(stage,fakePhoto,currentPhoto);
		    stage.showAndWait();
		    
		}catch(Exception ex){
			ex.printStackTrace();
		}
    	
    	
	}
    
    /**
     * makes the combobox and the delete button visible
     * @param e action event that is triggered as a result of clicking the button
     */
    @FXML public void showDeleteMenu(ActionEvent e){
    	showDeleteButton.setVisible(false);
    	cancelButton.setVisible(true);
    	deleteTagButton.setVisible(true);
    	tagComboBox.setVisible(true);
		tagComboBox.setItems(tagList);
		if (tagList.size()!=0)
			tagComboBox.getSelectionModel().select(0);
    }
    /**
     * resets the combobox and the delete button
     * @param e action event that is triggered as a result of clicking the button
     */
    @FXML public void resetMenu(ActionEvent e){
    	showDeleteButton.setVisible(true);
    	cancelButton.setVisible(false);
    	deleteTagButton.setVisible(false);
    	tagComboBox.setVisible(false);
    	
    }
    /**
     * removes the tag from the list of tags
     * @param e action event that is triggered as a result of clicking the button
     */
    @FXML public void deleteTag(ActionEvent e){
    	if (tagComboBox.getSelectionModel().getSelectedItem()!=null){
    		tagList.remove(tagComboBox.getSelectionModel().getSelectedIndex());
    	}
    }
	
	
	
}
