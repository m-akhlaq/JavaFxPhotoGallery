package control;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.*;

public class MainUserPageController {
	
	@FXML ListView<Album> albumList;
	@FXML Label welcomeMessage;
	User user;
	private ObservableList<Album> list = FXCollections.observableArrayList();
	public void populateView(User u){
		user=u;
		list =  FXCollections.observableArrayList(u.getAlbum());
		welcomeMessage.setText("Welcome "+u.getName());
	}
	
	public void start(Stage mainStage){
		albumList.setItems(list);
		
		
		albumList.getSelectionModel().selectedIndexProperty().addListener(
				 (obs, oldVal, newVal) ->{
					 try {
							FXMLLoader loader = new FXMLLoader();
							loader.setLocation(
							getClass().getResource("/view/PhotoView.fxml"));
							AnchorPane root =  (AnchorPane)loader.load();
					           Stage stage = new Stage();
					            stage.setTitle("My New Stage Title");
					            stage.setScene(new Scene(root, 600, 400));
							PhotoViewController photoViewController =
									 loader.getController();
									 photoViewController.populateView(user,albumList.getSelectionModel().getSelectedItem());
									 photoViewController.start(stage);
									 stage.showAndWait();

					 }catch(Exception e) {
							e.printStackTrace();
						}
					 
					 
				 });
		
	}

}
