package application;
	
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import model.*;
import control.*;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
/**
 * This is the class that runs the whole programs and contains the main method
 * @author Muhammad Akhlaq
 * @author John Brauner
 *
 */

public class Photos extends Application {
	/**
	 * The overriden start method that launches the login page
	 */
	@Override
	public void start(Stage primaryStage) {
		try {
			FXMLLoader loader = new FXMLLoader();
			
			loader.setLocation(
			getClass().getResource("/view/LoginView.fxml"));
			AnchorPane root =  (AnchorPane)loader.load();
			LoginController listController =
					 loader.getController();
					// listController.populateView(u);
					 listController.start(primaryStage); 
			Scene scene = new Scene(root,325,242);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * launches the application
	 * @param args String args
	 */
	public static void main(String[] args) {
		launch(args);
	}
}
