package application;
	
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import model.*;
import control.*;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			FXMLLoader loader = new FXMLLoader();
			
			loader.setLocation(
			getClass().getResource("/view/MainUserPage.fxml"));
			AnchorPane root =  (AnchorPane)loader.load();
			User u = new User("Muhammad Akh","msakhlaq");
			u.addAlbums(new Album("Al1"));
			u.addAlbums(new Album("Al2"));
			u.addAlbums(new Album("Al3"));
			MainUserPageController listController =
					 loader.getController();
					 listController.populateView(u);
					 listController.start(primaryStage); 
			Scene scene = new Scene(root,626,400);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
