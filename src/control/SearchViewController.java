package control;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Optional;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.Album;
import model.Photo;
import model.User;

public class SearchViewController {

	@FXML Button searchButton;
	@FXML DatePicker toDatePicker;
	@FXML DatePicker fromDatePicker;
	@FXML TextField tagsField;
	@FXML ListView<Photo> resultList;
	@FXML Button moveToAlbumButton;
	private ObservableList<Photo> searchResultList = FXCollections.observableArrayList();

	User user;
	ArrayList<Album> allAlbums = new ArrayList<Album>();
	Stage stage;
	
	
	public void initUser (User u){
		user=u;
		allAlbums=u.getAlbum();
	}
	public void start(Stage primaryStage){
		stage=primaryStage;
	    resultList.setCellFactory(param -> new ListCell<Photo>(){
            private ImageView imageView = new ImageView();
            @Override
            public void updateItem(Photo p, boolean empty) {
            	super.updateItem(p, empty);
                if (empty) {
                    setText(null);
                    setGraphic(null);
                }else {
                    imageView.setImage(new Image(p.getLocation(),200,200,false,false));
                    setText(p.printAttributes());
                    setGraphic(imageView);
                }
            }
	    	
	    });
	}
	
	@FXML public void search(ActionEvent e){
		searchResultList.clear();
		resultList.refresh();
		String tags = tagsField.getText().trim();
		
		Date fromDate,toDate;
		LocalDate localFromDate = fromDatePicker.getValue();
		LocalDate localToDate = toDatePicker.getValue();
		if (!tags.isEmpty() || localFromDate!=null || localToDate!=null){
			Calendar c =  Calendar.getInstance();
			if (localFromDate!=null){
			c.set(localFromDate.getYear(), localFromDate.getMonthValue()-1, localFromDate.getDayOfMonth(),0,0000,000);
			fromDate = c.getTime();

			}else fromDate = new Date(1);
			
			if (localToDate!=null){
			c.set(localToDate.getYear(), localToDate.getMonthValue()-1, localToDate.getDayOfMonth(),23,59,59);
			toDate = c.getTime();
			}else toDate=new Date(150967831358996L);
			
			for (Album a:allAlbums){
				ArrayList<Photo> allPhotos = a.getPhotos();
				for (Photo p:allPhotos){
					if (p.isInRange(fromDate, toDate)){
						searchResultList.add(p);
					}
				}
			}
			
			

		}
		resultList.setItems(searchResultList);
	}
	
	@FXML public void moveResultsToAlbum(){
		if (searchResultList.size()!=0){
			TextInputDialog dialog = new TextInputDialog("walter");
			dialog.setTitle("Album Name");
			dialog.setHeaderText("Album Name");
			dialog.setContentText("Please enter Album Name:");
			Optional<String> result = dialog.showAndWait();
			if (result.isPresent()){
				String albumName = result.get();
				if (user.nameExist(albumName)==false){
					Album newAlbum = new Album(albumName);
					for (Photo p:searchResultList){
						newAlbum.addPhotos(p);
					}
					user.addAlbums(newAlbum);
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
	
}
