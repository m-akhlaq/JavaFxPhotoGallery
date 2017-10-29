package control;


import java.io.File;

import application.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import model.*;

public class PhotoViewController {
	
	
	@FXML ListView<Photo> photoView;
	@FXML Button addPhotoButton;
	
	private ObservableList<Photo> list = FXCollections.observableArrayList();
	Album currentAlbum;
	public void populateView(User u,Album a){
		list.addAll(a.getPhotos());
		currentAlbum=a;
	}

	public void start(Stage primaryStage){
	    photoView.setItems(list);
	    photoView.setCellFactory(param -> new ListCell<Photo>(){
            private ImageView imageView = new ImageView();
            @Override
            public void updateItem(Photo name, boolean empty) {
            	super.updateItem(name, empty);
                if (empty) {
                    setText(null);
                    setGraphic(null);
                }else {
                    imageView.setImage(new Image(name.getLocation()));
                    setText(name.getCaption());
                    setGraphic(imageView);
                }
            }
	    	
	    });
	}
	
	public void addPhoto(ActionEvent e){
		try{
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(
			getClass().getResource("/view/AddingPhotoView.fxml"));
			AnchorPane root =  (AnchorPane)loader.load();
	           Stage stage = new Stage();
	            stage.setTitle("My New Stage Title");
	            stage.setScene(new Scene(root, 600, 400));
			AddingPhotoController photoViewController =
					 loader.getController();
					 photoViewController.initAlbum(currentAlbum);
					 photoViewController.start(stage);
					 stage.showAndWait();
					 list.clear();
					 list.addAll(currentAlbum.getPhotos());
		}catch(Exception ex){
			ex.printStackTrace();
		}
		
	}

		
	

}

