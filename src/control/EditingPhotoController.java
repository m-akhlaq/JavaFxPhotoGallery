package control;

import java.util.ArrayList;
import java.util.Date;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import model.Album;
import model.Photo;
import model.Tag;

public class EditingPhotoController {

	@FXML TextField captionField;
	@FXML Button editButton;
	@FXML TextField tagField;	
	Album currentAlbum;
	Stage s;
	Photo currentPhoto;
	
	
	public void initPhoto(Photo p){
		currentPhoto=p;
	}
	
	public void start(Stage s){
		this.s=s;
		captionField.setText(currentPhoto.getCaption());
		ArrayList<Tag> tagList = currentPhoto.getTags();
		String tags="";
		if (tagList.size()!=0){
			tags=tagList.get(0).getKey()+"="+tagList.get(0).getValue();
			tagList.remove(0);
		}
		for(Tag t:currentPhoto.getTags()){
			tags+=","+t.getKey()+"="+t.getValue();
		}
		tagField.setText(tags);
	}
	
	public void editPhoto(ActionEvent e){
		String caption = captionField.getText();
		String tags = tagField.getText();
		ArrayList<Tag> tagList = getTags(tags);
		if (tagList!=null){
			currentPhoto.setCaption(caption);
			currentPhoto.setTags(tagList);
			s.close();
		}
	}
	
	public ArrayList<Tag> getTags(String tags){
		tags.trim();
		ArrayList<Tag> tagList = new ArrayList<Tag>();
		try{
		String [] individualTags;
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
		}catch(Exception e){
			 if (tags.length()!=0){
			 Alert alert = new Alert(AlertType.ERROR);
			 alert.setTitle("ERROR");
			 alert.setHeaderText("ERROR HAS OCCURED");
			 alert.setContentText("Please fill out the tags properly");
			 alert.showAndWait();
			 return null;
			 
		}
		
	}
		return tagList;
	}
	
	
	
}
