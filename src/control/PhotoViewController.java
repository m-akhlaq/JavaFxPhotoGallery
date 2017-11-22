package control;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Optional;

import application.Photos;
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
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import model.*;
/**
 * This class acts as the controller for the main photos page within an album.
 * @author Muhammad Akhlaq
 * @author John Brauner
 *
 */
public class PhotoViewController {
	
	
	@FXML ListView<Photo> photoView;
	@FXML Button addPhotoButton;
	@FXML Button searchPhotoButton;
	@FXML Button backButton;
	@FXML Button deleteButton;
	@FXML Button editPhotoButton;
	@FXML Button slideshowButton;
	
	private ObservableList<Photo> list = FXCollections.observableArrayList();
	Album currentAlbum;
	User user;
	Stage stage;
	/**
	 * This function gets called before the start method to initialize the listview.
	 * @param u The user to whom these pictures belong
	 * @param a The album in which these photos reside
	 */
	public void populateView(User u,Album a){
		list.addAll(a.getPhotos());
		currentAlbum=a;
		user=u;
		photoView.setItems(list);
		if (list.size()>0){
			photoView.getSelectionModel().select(0);
		}
	}
	/**
	 * Gets called when the window is first opened. Creates a custom cell factory for the list view
	 * @param primaryStage The current Stage
	 */
	public void start(Stage primaryStage){
	    stage=primaryStage;
	    stage.setResizable(false);
		stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
		    @Override
		    public void handle(WindowEvent event) {
		        // consume event
		        event.consume();
		        // show close dialog
		        Alert alert = new Alert(AlertType.CONFIRMATION);
		        alert.setTitle("Exit Application");
		        alert.setHeaderText("Are you sure you want to Exit?");
		        alert.setContentText("Do you want the changes you have made during this sesssion to be saved?");
		        ButtonType saveAndExit = new ButtonType("Save changes and Exit");
		        ButtonType dontSaveAndExit = new ButtonType("Don't save changes and Exit");
		        ButtonType cancel = new ButtonType("Cancel", ButtonData.CANCEL_CLOSE);
		        alert.getButtonTypes().setAll(saveAndExit,dontSaveAndExit,cancel);
		        Optional<ButtonType> result = alert.showAndWait();
		        if (result.get() == saveAndExit){
		        	saveData();
		        	stage.close();
		        }else if (result.get() == dontSaveAndExit){
		        	stage.close();
		        }else{
		        	alert.close();
		        }
		        
		    }
		});
	    
	    
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
                    setText(name.getCaption());
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
	        stage.setTitle("Add Photo");
	        stage.setScene(new Scene(root, 279, 300));
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
	/**
	 * This functions is used to handle clicks or double clicks done on individaul pictures
	 * @param click gets the event of the mouse click
	 */
	@FXML public void doubleClickPhoto(MouseEvent click) {
        if (click.getClickCount() == 2 && photoView.getSelectionModel().getSelectedItem()!=null) {
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
	/**
	 * this opens the search view to allow users to search for photos
	 * @param e action event that occurs when the search button is pressed
	 */
	@FXML public void openSearch(ActionEvent e){
		try{
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(
			getClass().getResource("/view/SearchView.fxml"));
			AnchorPane root =  (AnchorPane)loader.load();
	        Stage stage = new Stage();
	        stage.setTitle("Search Photos");
	        stage.setScene(new Scene(root, 545, 423));
	        SearchViewController searchViewController = loader.getController();
	        searchViewController.initUser(user);
	        searchViewController.start(stage);
	        stage.show();
			
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	/**
	 * allows users to return to their main album page
	 * @param e  action event that occurs when the back button is pressed
	 */
	@FXML public void goBack(ActionEvent e){
		
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(
			getClass().getResource("/view/MainUserPage.fxml"));
			AnchorPane root =  (AnchorPane)loader.load();
	         Stage newStage = new Stage();
	         newStage.setTitle("Album Page");
	         newStage.setScene(new Scene(root, 626, 400));
			MainUserPageController MainUserPageController =
			loader.getController();
			MainUserPageController.populateView(user);
			MainUserPageController.start(newStage); 
			stage.close();
			newStage.show();
			
		} catch(Exception ex) {
			ex.printStackTrace();
		}
	}
	/**
	 * this button deletes the selected photo 
	 * @param e  action event that occurs when the delete button is pressed
	 */
	@FXML public void deletePhoto(ActionEvent e){
		if (photoView.getSelectionModel().getSelectedItem()!=null){
			int removeIndex = photoView.getSelectionModel().getSelectedIndex();
			Photo removePhoto = photoView.getSelectionModel().getSelectedItem();
			list.remove(removeIndex);
			for (int x=0;x<currentAlbum.getPhotos().size();x++){
				if (currentAlbum.getPhotos().get(x).equals(removePhoto)){
					currentAlbum.getPhotos().remove(x);
					break;
				}
				
			}
			photoView.refresh();
		}else{
	 		 Alert alert = new Alert(AlertType.ERROR);
			 alert.setTitle("ERROR");
			 alert.setHeaderText("ERROR HAS OCCURED");
			 alert.setContentText("Please click to select an item in the list!");
			 alert.showAndWait();
		}
	}
	
	/**
	 * this allows the user to edit the information of a selected photo
	 * @param e  action event that occurs when the search button is pressed
	 */
	@FXML public void editPhoto(ActionEvent e){
		if (photoView.getSelectionModel().getSelectedItem()!=null){
		try{
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(
			getClass().getResource("/view/EditingPhotoView.fxml"));
			AnchorPane root =  (AnchorPane)loader.load();
	        Stage stage = new Stage();
	        stage.setTitle("Edit Photo");
	        stage.setScene(new Scene(root, 300, 240));
			EditingPhotoController editingPhotoViewController =
			loader.getController();
			editingPhotoViewController.initPhoto(photoView.getSelectionModel().getSelectedItem());
			editingPhotoViewController.start(stage);
		    stage.showAndWait();
			photoView.refresh();
		}catch(Exception ex){
			ex.printStackTrace();
		}
	  }else{
	 		 Alert alert = new Alert(AlertType.ERROR);
			 alert.setTitle("ERROR");
			 alert.setHeaderText("ERROR HAS OCCURED");
			 alert.setContentText("Please click to select an item in the list!");
			 alert.showAndWait();
	  }
	}
	/**
	 * this starts the slideshow of all photos within the current album
	 * @param e  action event that occurs when the slideshow button is pressed
	 */
	@FXML public void startSlideshow(ActionEvent e){
		if (list.size()!=0){
			try{
				FXMLLoader loader = new FXMLLoader();
				loader.setLocation(
				getClass().getResource("/view/SlideshowView.fxml"));
				AnchorPane root =  (AnchorPane)loader.load();
		        Stage stage = new Stage();
		        stage.setTitle("SlideShow");
		        stage.setScene(new Scene(root, 610, 450));
				SlideshowViewController slideshowViewController =
				loader.getController();
				slideshowViewController.initSlideshow(currentAlbum);
				slideshowViewController.start(stage);
			    stage.showAndWait();
			}catch(Exception ex){
				ex.printStackTrace();
			}
		}else{
	 		 Alert alert = new Alert(AlertType.ERROR);
			 alert.setTitle("ERROR");
			 alert.setHeaderText("ERROR HAS OCCURED");
			 alert.setContentText("Please add a photo to the album before starting the slideshow!");
			 alert.showAndWait();
		}
		
	}
	/**
	 * This event handler is responsible for opening the moving photo view!
	 * @param e ActionEvent the occurs as a result of the button press!
	 */
	
	@FXML public void movePhoto(ActionEvent e){
		if (photoView.getSelectionModel().getSelectedItem()!=null){
			try{
				FXMLLoader loader = new FXMLLoader();
				loader.setLocation(
				getClass().getResource("/view/MovePhotoView.fxml"));
				AnchorPane root =  (AnchorPane)loader.load();
		        Stage stage = new Stage();
		        stage.setTitle("Move Photo");
		        stage.setScene(new Scene(root, 306, 213));
				MovePhotoController movePhotoController =
				loader.getController();
				movePhotoController.start(stage, photoView.getSelectionModel().getSelectedItem(), currentAlbum, user,photoView.getSelectionModel().getSelectedIndex());
			    stage.showAndWait();
				list.clear();
				list.addAll(currentAlbum.getPhotos());
				photoView.refresh();
			}catch(Exception ex){
				ex.printStackTrace();
			}
			
			
		}else{
	 		 Alert alert = new Alert(AlertType.ERROR);
			 alert.setTitle("ERROR");
			 alert.setHeaderText("ERROR HAS OCCURED");
			 alert.setContentText("Please click to select an item in the list!");
			 alert.showAndWait();
		}
	}
	/**
	 * this event handler is responsible for opening a photo in a seperate viewing area. (Same as double clicking a photo)
	 * @param e ActionEvent the occurs as a result of the button press!
	 */
	
	@FXML public void openPhoto(ActionEvent e){
		if (photoView.getSelectionModel().getSelectedItem()!=null){
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
        		
        	}catch(Exception ex){
        		ex.printStackTrace();
        	}
		}else{
	 		 Alert alert = new Alert(AlertType.ERROR);
			 alert.setTitle("ERROR");
			 alert.setHeaderText("ERROR HAS OCCURED");
			 alert.setContentText("Please click to select an item in the list!");
			 alert.showAndWait();
		}
	}

	public void saveData(){
		try{
			String dir = "database/";
			String fileName = user.getName()+".dat";
			ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("resources/"+dir+fileName));
			oos.writeObject(user); 
			oos.close();
			}catch(IOException ex){
				ex.printStackTrace();
			}
	}
		
	

}

