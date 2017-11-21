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
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.Album;
import model.Photo;
import model.User;

public class LoginController {
	@FXML TextField usernameField;
	@FXML TextField passwordField;
	ArrayList<String> usernames = new ArrayList<String>();
	Stage mainStage;
	Scanner scanner;
	public void start(Stage mainStage){
		this.mainStage=mainStage;
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
	
	public boolean findUsername(String username){
		for(String u:usernames){
			if (u.equals(username)){
				return true;
			}
		}
		return false;
	}
	
	@FXML public void login(ActionEvent e){
		String username = usernameField.getText().toLowerCase();
		if (findUsername(username)){
			if (!username.equals("admin")){
				loadMainUserPage(username);
			}else{
				loadAdminPage();
			}
		}
		
	}
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
	
	public User readUser(String username) throws IOException, ClassNotFoundException{
		String dir = "database/";
		String fileName = username+".dat";
		ObjectInputStream ois = new ObjectInputStream(new FileInputStream("resources/"+dir+fileName));
				User u = (User)ois.readObject();
				ois.close();
				return u; 
				
	}
	
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

}


