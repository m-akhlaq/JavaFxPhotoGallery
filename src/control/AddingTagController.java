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

public class AddingTagController {

	@FXML TextField keyField;
	@FXML TextField valueField;
	@FXML Button addButton;
	Photo currentPhoto;
	Stage mainStage;
	
	public void start(Stage primaryStage, Photo p){
		mainStage=primaryStage;
		currentPhoto=p;
	}
	
	@FXML public void addTag(ActionEvent e){
		String key=keyField.getText().trim();
		String value=valueField.getText().trim();
		if (!key.isEmpty()&&!value.isEmpty()){
			Tag t = new Tag(key,value);
			currentPhoto.addTag(t);
			mainStage.close();
		}else{
			 Alert alert = new Alert(AlertType.ERROR);
			 alert.setTitle("ERROR");
			 alert.setHeaderText("ERROR HAS OCCURED");
			 alert.setContentText("Please fill out both key and value");
			 alert.showAndWait();
		}
		
		
	}
	
	
	
}
