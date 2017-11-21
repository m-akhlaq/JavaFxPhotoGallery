package control;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Optional;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextInputDialog;
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
 * This class acts as the controller for the main user page (album view)
 * @author Muhammad Akhlaq
 * @author John Brauner
 *
 */
public class MainUserPageController {
	
	@FXML ListView<Album> albumList;
	@FXML Label welcomeMessage;
	User user;
	Stage mainStage;
	int num=0;
    static final long serialVersionUID=1L;
	private ObservableList<Album> list = FXCollections.observableArrayList();
	/**
	 * this method populates the list view full of Albums
	 * @param u User whose albums are loaded up (currently logged in user)
	 */
	public void populateView(User u){
		user=u;
		list =  FXCollections.observableArrayList(u.getAlbum());
		albumList.refresh();
		welcomeMessage.setText("Welcome "+u.getName());
	}
	/**
	 * this methods sets the cellFactory for the list view containing the albums and defines the event handling when the
	 * close button is pressed.
	 * @param mainStage the mainStage of the view
	 */
	public void start(Stage mainStage){
		albumList.setItems(list);
		albumList.refresh();
		albumList.setCellFactory(param -> new ListCell<Album>(){
            private ImageView imageView = new ImageView();
            @Override
            public void updateItem(Album name, boolean empty) {
            	super.updateItem(name, empty);
                if (empty) {
                    setText(null);
                    setGraphic(null);
                }else {
                    imageView.setImage(new Image("file:resources/albumImage.png",100,100,false,false));
                    setText(name.printAttributes());
                    setGraphic(imageView);
                }
            }
	    	
	    });
		this.mainStage=mainStage;
		mainStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
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
		        	mainStage.close();
		        }else if (result.get() == dontSaveAndExit){
		        	mainStage.close();
		        }else{
		        	alert.close();
		        }
		        
		    }
		});
	}
	/**
	 * This method handles when the user click (specifically double clicks) an album, this opens up the album
	 * @param click The mouse event that triggers when an item on the list is clicked
	 */

	@FXML public void doubleClickPhoto(MouseEvent click) {
        if (click.getClickCount() == 2 && albumList.getSelectionModel().getSelectedItem()!=null) {
        	try {
        		num++;
				FXMLLoader loader = new FXMLLoader();
				loader.setLocation(
				getClass().getResource("/view/PhotoView.fxml"));
				AnchorPane root =  (AnchorPane)loader.load();
		        Stage stage = new Stage();
		        stage.setTitle("My Photos");
		        stage.setScene(new Scene(root, 626, 400));
				PhotoViewController photoViewController =
				loader.getController();
				photoViewController.populateView(user,albumList.getSelectionModel().getSelectedItem());
				photoViewController.start(stage);
				mainStage.close();
				stage.show();
		 }catch(Exception e) {
				e.printStackTrace();
        }
       } 
	}
	/**
	 * this opens a dialouge box which allows the user to add a new album
	 * @param e action event as a result of pressing of 'add album button'
	 */
	@FXML public void addAlbum(ActionEvent e){
		TextInputDialog dialog = new TextInputDialog();
		dialog.setTitle("Album Name");
		dialog.setHeaderText("Album Name");
		dialog.setContentText("Please enter Album Name:");
		Optional<String> result = dialog.showAndWait();
		if (result.isPresent()){
			String albumName = result.get();
			if (user.nameExist(albumName)==false){
				Album newAlbum = new Album(albumName);
				user.addAlbums(newAlbum);
				list.clear();
				list =  FXCollections.observableArrayList(user.getAlbum());
				albumList.setItems(list);
				albumList.refresh();
			}else{
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Error ");
				alert.setHeaderText("ERROR");
				alert.setContentText("Album name already exists!");
				alert.showAndWait();
			}
		}
	}
	
	/**
	 * this method handles the deletion of an album
	 * @param e action event as a result of pressing of 'delete album button'
	 */
	public void deleteAlbum(ActionEvent e){
		if (albumList.getSelectionModel().getSelectedItem()!=null){
			Alert alert = new Alert(AlertType.CONFIRMATION);
			 alert.setTitle("Confirm your Action");
			 alert.setHeaderText("Please confirm");
			 alert.setContentText("Doing this will delete the album and all photos in it! Continue?");
			 Optional<ButtonType> result = alert.showAndWait();
			 //ensures that the user presses OK before any changes are finalized.
			 if (result.get() == ButtonType.OK){
			 int index = albumList.getSelectionModel().getSelectedIndex();
			 list.remove(index);
			 user.getAlbum().remove(index);
			 albumList.refresh();
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
	 * this opens the searchView which allows users to search their photos.
	 * @param e action event as a result of pressing of 'open serch utton'
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
	        stage.showAndWait();
			list.clear();
			list =  FXCollections.observableArrayList(user.getAlbum());
			albumList.setItems(list);
			albumList.refresh();
			
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	/**
	 * does the exact same thing as double clicking a photo, opens the album
	 * @param e action event as a result of pressing of 'move to album button'
	 */
	@FXML public void openAlbum(ActionEvent e){
      	if (albumList.getSelectionModel().getSelectedItem()!=null){
		try {
				num++;
				FXMLLoader loader = new FXMLLoader();
				loader.setLocation(
				getClass().getResource("/view/PhotoView.fxml"));
				AnchorPane root =  (AnchorPane)loader.load();
		        Stage stage = new Stage();
		        stage.setTitle("My Photos");
		        stage.setScene(new Scene(root, 626, 400));
				PhotoViewController photoViewController =
				loader.getController();
				photoViewController.populateView(user,albumList.getSelectionModel().getSelectedItem());
				photoViewController.start(stage);
				mainStage.close();
				stage.show();
		 }catch(Exception ex) {
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
	 * opens a dialouge box and allows user to edit the album name
	 * @param e action event as a result of pressing of 'edit album button'
	 */
	@FXML public void editAlbum(ActionEvent e){
		if (albumList.getSelectionModel().getSelectedItem()!=null){
			Album albumToBeEdited = albumList.getSelectionModel().getSelectedItem();
			TextInputDialog dialog = new TextInputDialog(albumToBeEdited.getName());
			dialog.setTitle("Album Name");
			dialog.setHeaderText("Album Name");
			dialog.setContentText("Please enter Album Name:");
			Optional<String> result = dialog.showAndWait();
			if (result.isPresent()){
				String albumName = result.get();
				if (user.nameExist(albumName)==false){
					albumToBeEdited.setName(albumName);
				}else{
					 Alert alert = new Alert(AlertType.ERROR);
					 alert.setTitle("ERROR");
					 alert.setHeaderText("ERROR HAS OCCURED");
					 alert.setContentText("Album Name Already exist! Try again");
					 alert.showAndWait();
				}
				
			}
			albumList.refresh();
		}else{
	 		 Alert alert = new Alert(AlertType.ERROR);
			 alert.setTitle("ERROR");
			 alert.setHeaderText("ERROR HAS OCCURED");
			 alert.setContentText("Please click to select an item in the list!");
			 alert.showAndWait();
		}
	}
	/**
	 * allows the user to safely exit the application and save their changes
	 * @param e action event as a result of pressing of 'exit button'
	 */
	@FXML public void exit(ActionEvent e){
		Alert alert = new Alert(AlertType.CONFIRMATION);
		 alert.setTitle("Confirm your Action");
		 alert.setHeaderText("Please confirm");
		 alert.setContentText("This will save your progress and close the program! Continue?");
		 Optional<ButtonType> result = alert.showAndWait();
		 //ensures that the user presses OK before any changes are finalized.
		 if (result.get() == ButtonType.OK){
			saveData();
		    mainStage.close();
	 }
	}
	/**
	 * allows the user to return to the login page and save their changes
	 * @param e action event as a result of pressing of 'logout button'
	 */
	@FXML public void logout(ActionEvent e){
		Alert alert = new Alert(AlertType.CONFIRMATION);
		 alert.setTitle("Confirm your Action");
		 alert.setHeaderText("Please confirm");
		 alert.setContentText("This will save your progress and return to the login page! Continue?");
		 Optional<ButtonType> result = alert.showAndWait();
		 //ensures that the user presses OK before any changes are finalized.
		if (result.get() == ButtonType.OK){
		try{
		saveData();
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(
		getClass().getResource("/view/LoginView.fxml"));
		AnchorPane root =  (AnchorPane)loader.load();
	    Stage stage = new Stage();
	    stage.setTitle("Login");
	    stage.setScene(new Scene(root, 325, 242));
	    LoginController loginController =
		loader.getController();
		loginController.start(stage);
		mainStage.close();
		stage.show();
		}catch(IOException ex){
			ex.printStackTrace();
		}
	}
	}
	/**
	 * serializes the state of the object and saves data 
	 */
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
