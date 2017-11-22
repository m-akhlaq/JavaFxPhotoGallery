package control;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import model.Photo;
import model.Tag;
/**
 * 
 * @author Muhammad Akhlaq
 * @author John Brauner
 *
 */
public class AddingTagController {

	@FXML TextField keyField;
	@FXML TextField valueField;
	@FXML Button addButton;
	Photo currentPhoto;
	Photo secondPhoto;
	Stage mainStage;
	/**
	 * initilzes the fields.
	 * @param primaryStage the current stage
	 * @param p the photo to whom that tag should be added.
	 */
	public void start(Stage primaryStage, Photo p, Photo p2){
		secondPhoto=p2;
		mainStage=primaryStage;
		mainStage.setResizable(false);
		currentPhoto=p;
	}
	/**
	 * actually adds the tag
	 * @param e action event for 'add tag' button
	 */
	@FXML public void addTag(ActionEvent e){
		String key=keyField.getText().trim();
		String value=valueField.getText().trim();
		if (!key.isEmpty()&&!value.isEmpty()){
			Tag t = new Tag(key,value);
			if (currentPhoto.matchTag(t)==false && secondPhoto.matchTag(t)==false){
			currentPhoto.addTag(t);
			Alert alert = new Alert(AlertType.INFORMATION);
			 alert.setTitle("Success!");
			 alert.setHeaderText("Tag added");
			 alert.setContentText("The tag was added succesfully");
			 alert.showAndWait();
			mainStage.close();
			}else{
				 Alert alert = new Alert(AlertType.ERROR);
				 alert.setTitle("ERROR");
				 alert.setHeaderText("ERROR HAS OCCURED");
				 alert.setContentText("Tag already exists");
				 alert.showAndWait();
			}
		}else{
			 Alert alert = new Alert(AlertType.ERROR);
			 alert.setTitle("ERROR");
			 alert.setHeaderText("ERROR HAS OCCURED");
			 alert.setContentText("Please fill out both key and value");
			 alert.showAndWait();
		}
		
		
	}
	
	
	
}
