package control;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.Album;
import model.Photo;
import model.User;
/**
 * 
 * @author Muhammad Akhlaq
 * @author John Brauner
 *
 */
public class LoginController {
	@FXML TextField usernameField;
	@FXML TextField passwordField;
	ArrayList<String> usernames = new ArrayList<String>();
	Stage mainStage;
	Scanner scanner;
	/**
	 * initlizes the stage and reads in the list of existing users
	 * @param mainStage stage of the current scene
	 */
	public void start(Stage mainStage){
		this.mainStage=mainStage;
		mainStage.setResizable(false);
		File usernameFile = new File("resources/usernames.txt");
		try {
			scanner = new Scanner(usernameFile);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		while (scanner.hasNextLine()){
			usernames.add(scanner.nextLine());
		}
	}
	/**
	 * this takes in a username and checks if that username exist in our file
	 * @param username the username we want to check
	 * @return true-user is registered. false-user is not registered
	 */
	public boolean findUsername(String username){
		for(String u:usernames){
			if (u.equals(username)){
				return true;
			}
		}
		return false;
	}
	/**
	 * this method logs the user in if they exist in our records or if the user is an admin
	 * @param e actionEvent that triggers when a button is pressed
	 */
	@FXML public void login(ActionEvent e){
		String username = usernameField.getText().toLowerCase();
		if (findUsername(username)){
			if (!username.equals("admin")){
				loadMainUserPage(username);
			}else{
				loadAdminPage();
			}
		}else{
			Alert alert = new Alert(AlertType.ERROR);
			 alert.setTitle("ERROR");
			 alert.setHeaderText("ERROR HAS OCCURED");
			 alert.setContentText("This user does not exist!");
			 alert.showAndWait();
		}
		
	}
	/**
	 * this loads the main user page for a specified user
	 * @param username the username whose albums we want loaded
	 */
	public void loadMainUserPage(String username){

		try {
			User u = readUser(username);
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(
			getClass().getResource("/view/MainUserPage.fxml"));
			AnchorPane root =  (AnchorPane)loader.load();
	        Stage stage = new Stage();
	        stage.setTitle("My Albums");
	        stage.setScene(new Scene(root, 626, 400));
			MainUserPageController mainUserPageController =
			loader.getController();
			mainUserPageController.populateView(u);
			mainUserPageController.start(stage);
			mainStage.close();
			stage.show();
	 }catch(Exception e) {
			e.printStackTrace();
    }
	}
	
	/**
	 * deserilizes a specified users data
	 * @param username the name of the serialized file we want to read
	 * @return User whose object we have reconsturcted 
	 * @throws IOException in case there is an error reading the file
	 * @throws ClassNotFoundException thorwn when the file is not found
	 */
	public User readUser(String username) throws IOException, ClassNotFoundException{
		String dir = "database/";
		String fileName = username+".dat";
		ObjectInputStream ois = new ObjectInputStream(new FileInputStream("resources/"+dir+fileName));
				User u = (User)ois.readObject();
				ois.close();
				return u; 
				
	}
	/**
	 * loads the admin subsystem
	 */
	public void loadAdminPage(){
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(
			getClass().getResource("/view/AdminView.fxml"));
			AnchorPane root =  (AnchorPane)loader.load();
	        Stage stage = new Stage();
	        stage.setTitle("Admin SubSystem");
	        stage.setScene(new Scene(root, 502, 388));
			AdminController adminController =
			loader.getController();
			adminController.start(stage);
			mainStage.close();
			stage.show();
	 }catch(Exception e) {
			e.printStackTrace();
    }
	}
	/**
	 * close the program
	 * @param e actionEvent triggered when a button is pressed
	 */
	@FXML public void cancel(ActionEvent e){
		mainStage.close();
	}

}


