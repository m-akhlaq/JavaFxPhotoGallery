package control;

import java.util.Optional;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.*;

public class MainUserPageController {
	
	@FXML ListView<Album> albumList;
	@FXML Label welcomeMessage;
	User user;
	Stage mainStage;
	private ObservableList<Album> list = FXCollections.observableArrayList();
	public void populateView(User u){
		user=u;
		list =  FXCollections.observableArrayList(u.getAlbum());
		welcomeMessage.setText("Welcome "+u.getName());
	}
	
	public void start(Stage mainStage){
		albumList.setItems(list);
		this.mainStage=mainStage;
		albumList.getSelectionModel().selectedIndexProperty().addListener(
				 (obs, oldVal, newVal) ->{
					 try {
							FXMLLoader loader = new FXMLLoader();
							loader.setLocation(
							getClass().getResource("/view/PhotoView.fxml"));
							AnchorPane root =  (AnchorPane)loader.load();
					        Stage stage = new Stage();
					        stage.setTitle("My New Stage Title");
					        stage.setScene(new Scene(root, 626, 400));
							PhotoViewController photoViewController =
							loader.getController();
							photoViewController.populateView(user,albumList.getSelectionModel().getSelectedItem());
							photoViewController.start(stage);
							mainStage.close();
							stage.showAndWait();
					 }catch(Exception e) {
							e.printStackTrace();
					 }
					 
					 
				 });
		
	}
	
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

}
