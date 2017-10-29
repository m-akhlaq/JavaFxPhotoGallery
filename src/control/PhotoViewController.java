package control;


import java.io.File;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

import application.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import javafx.stage.Stage;
import model.*;

public class PhotoViewController {
	
	
	@FXML ListView<Photo> photoView;
	@FXML Button addPhotoButton;
	
	private ObservableList<Photo> list = FXCollections.observableArrayList();
	Album currentAlbum;
	/**
	 * This function gets called before the start method to initialize the listview.
	 * @param u The user to whom these pictures belong
	 * @param a The album in which these photos reside
	 */
	public void populateView(User u,Album a){
		list.addAll(a.getPhotos());
		currentAlbum=a;
		photoView.setItems(list);
	}
	/**
	 * Gets called when the window is first opened. Creates a custom cell factory for the list view
	 * @param primaryStage The current Stage
	 */
	public void start(Stage primaryStage){
	    
	    photoView.setCellFactory(param -> new ListCell<Photo>(){
            private ImageView imageView = new ImageView();
            @Override
            public void updateItem(Photo name, boolean empty) {
            	super.updateItem(name, empty);
                if (empty) {
                    setText(null);
                    setGraphic(null);
                }else {
                    imageView.setImage(new Image(name.getLocation(),200,200,false,false));
                    setText(name.printAttributes());
                    setGraphic(imageView);
                }
            }
	    	
	    });
	}
	/**
	 * This function is the onClick Listener for the add button. It opens up a new window where the user can add
	 * a photo
	 * @param e the action event that has occured
	 */
	public void addPhoto(ActionEvent e){
		try{
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(
			getClass().getResource("/view/AddingPhotoView.fxml"));
			AnchorPane root =  (AnchorPane)loader.load();
	           Stage stage = new Stage();
	            stage.setTitle("My New Stage Title");
	            stage.setScene(new Scene(root, 350, 300));
			AddingPhotoController photoViewController =
					 loader.getController();
					 photoViewController.initAlbum(currentAlbum);
					 photoViewController.start(stage);
					 stage.showAndWait();
					 list.clear();
					 list.addAll(currentAlbum.getPhotos());
					Collections.sort(list,new PhotoComparator());
		}catch(Exception ex){
			ex.printStackTrace();
		}
		
	}
	/**
	 * This functions is used to handle clicks or double clicks done on individaul pictures
	 * @param click gets the event of the mouse click
	 */
	@FXML public void handleMouseClick(MouseEvent click) {
        if (click.getClickCount() == 2) {

        	try{
    			FXMLLoader loader = new FXMLLoader();
    			loader.setLocation(
    			getClass().getResource("/view/FullPhotoView.fxml"));
    			AnchorPane root =  (AnchorPane)loader.load();
    	        Stage stage = new Stage();
    	        stage.setTitle("Your Photo");
    	        stage.setScene(new Scene(root, 610, 450));
    	        FullPhotoViewController fullPhotoViewController = loader.getController();
    	        fullPhotoViewController.initPhoto(photoView.getSelectionModel().getSelectedItem());
    	        fullPhotoViewController.start(stage);
    	        stage.show();
        		
        	}catch(Exception e){
        		e.printStackTrace();
        	}

        }
	    
	}

		
	

}

