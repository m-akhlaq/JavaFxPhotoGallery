package control;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Scanner;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.Photo;
import model.User;
/**
 * 
 * @author Muhammad Akhlaq
 * @author John Brauner
 *
 */
public class AdminController {

	@FXML ListView<String> userList;
	ArrayList<String> usernames = new ArrayList<String>();
	private ObservableList<String> usernameList = FXCollections.observableArrayList();
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
			String s = scanner.nextLine();
			if (!s.equals("admin"))
				usernames.add(s);
		}
		
		usernameList=FXCollections.observableArrayList(usernames);
		userList.setItems(usernameList);
		
	}
	/**
	 * this takes in a username and checks if that username exist in our file
	 * @param username the username we want to check
	 * @return true-user is registered. false-user is not registered
	 */
	public boolean usernameExists(String username){
		if (username.equals("admin"))
			return true;
		for (String s:usernameList){
			if (username.equals(s))
				return true;
		}
		return false;
		
	}
	/**
	 * this method actaully adds the new user into the system
	 * @param e action event triggered when a button is pressed
	 */
	@FXML public void addUser(ActionEvent e){
		TextInputDialog dialog = new TextInputDialog();
		dialog.setTitle("Add user");
		dialog.setHeaderText("Username");
		dialog.setContentText("Please enter the new Users username:");
		Optional<String> result = dialog.showAndWait();
		if (result.isPresent()){
			String username = result.get();
			username = username.toLowerCase().trim();
			if (usernameExists(username)==false){
				usernameList.add(username);
				userList.refresh();
				User newUser = new User(username,username);
				try{
					createUserData(newUser);
				}catch(IOException ex){
					ex.printStackTrace();
				}
			}else{
				Alert alert = new Alert(AlertType.ERROR);
				 alert.setTitle("ERROR");
				 alert.setHeaderText("ERROR HAS OCCURED");
				 alert.setContentText("username already exists");
				 alert.showAndWait();
			}
		}
		
		
	}
	/**
	 * this creates the .dat file with the same name as the username
	 * @param u User whose data we want serialized
	 * @throws IOException thrown incase there is an IO problem
	 */
	private void createUserData(User u) throws IOException{
		String dir = "database/";
		String fileName = u.getName()+".dat";
		ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("resources/"+dir+fileName));
		oos.writeObject(u); 
		oos.flush();
		oos.close();
	}
	/**
	 * this method deletes the user and their corresponding .dat file
	 * @param e Action event triggered by clicking delete
	 */
	@FXML public void deleteUser(ActionEvent e){
		if(userList.getSelectionModel().getSelectedItem()!=null){
			Alert alert = new Alert(AlertType.CONFIRMATION);
			 alert.setTitle("Confirm your Action");
			 alert.setHeaderText("Please confirm");
			 alert.setContentText("Doing this will delete the user and all their albums and photos! Continue?");
			 Optional<ButtonType> result = alert.showAndWait();
			 //ensures that the user presses OK before any changes are finalized.
			 if (result.get() == ButtonType.OK){
			int index = userList.getSelectionModel().getSelectedIndex();
			String dir = "database/";
			String fileName = userList.getSelectionModel().getSelectedItem()+".dat";
			File file = new File("resources/"+dir+fileName);
			file.delete();
			usernameList.remove(index);
			userList.refresh();
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
	 * saves the data and exits the application
	 * @param e Action event triggered by clicking delete
	 */
	@FXML public void exit(ActionEvent e){
		Alert alert = new Alert(AlertType.CONFIRMATION);
		 alert.setTitle("Confirm your Action");
		 alert.setHeaderText("Please confirm");
		 alert.setContentText("This will save your progress and close the program! Continue?");
		 Optional<ButtonType> result = alert.showAndWait();
		 //ensures that the user presses OK before any changes are finalized.
		 if (result.get() == ButtonType.OK){
				File text = new File("resources/usernames.txt");
				text.setExecutable(true);
				text.setReadable(true);
				text.setWritable(true);
				try {
					BufferedWriter bw=null;
					bw= new BufferedWriter(new FileWriter(text));
					for(String s:usernameList){
						bw.write(s);
						bw.newLine();
					}
				bw.write("admin");
				bw.flush();
				bw.close();
				mainStage.close();
		 }catch(IOException ex){
				ex.printStackTrace();
		}
	}
	}
	/**
	 * saves data and logs out
	 * @param e Action event triggered by clicking delete
	 */
	@FXML public void logout(ActionEvent e){
		File text = new File("resources/usernames.txt");
		text.setExecutable(true);
		text.setReadable(true);
		text.setWritable(true);
		try {
			BufferedWriter bw=null;
			bw= new BufferedWriter(new FileWriter(text));
			for(String s:usernameList){
				bw.write(s);
				bw.newLine();
			}
		bw.write("admin");
		bw.flush();
		bw.close();
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
