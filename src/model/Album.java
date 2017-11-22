package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
/**
 * this class is the data class for Album
 * @author Muhammad AKhlaq
 * @author John Brauner
 *
 */
public class Album implements Serializable {

	String name;
	
	ArrayList<Photo> photos = new ArrayList<Photo>();
	/**
	 * constructs the album with just a name
	 * @param name name of the album
	 */
	public Album(String name){
		this.name=name;
	}
	
	/**
	 * returns the album name
	 * @return name of album
	 */
	public String getName(){
		return name;
	}
	/**
	 * 
	 * @return the list of photos in the album
	 */
	public ArrayList<Photo> getPhotos(){
		return photos;
	}
	/**
	 * 
	 * @param p the photo you want added
	 */
	public void addPhotos(Photo p){
		photos.add(p);
	}
	/**
	 * 
	 * @param n the new name of the album
	 */
	public void setName(String n){
		name=n;
	}
	/**
	 * 
	 * @return String which contains the attributes of the album 
	 */
	public String printAttributes(){
		ArrayList<Photo> sortedPhoto = photos;
		Date fromDate = new Date();
		Date toDate = new Date();
		Collections.sort(sortedPhoto,new PhotoComparator());
		if (sortedPhoto.size()==1){
			fromDate = sortedPhoto.get(0).getDate();
			toDate = sortedPhoto.get(0).getDate();
		}else if (sortedPhoto.size()>1){
			fromDate = sortedPhoto.get(0).getDate();
			toDate = sortedPhoto.get(sortedPhoto.size()-1).getDate();
		}
		int numOfPhotos = photos.size();
		if (numOfPhotos==0){
		return name+"\n"+"Contains: "+numOfPhotos+" photos \n";
		}else{
		return name+"\n"+"Contains: "+numOfPhotos+" photos \n"+"From: "+fromDate+" \n"+"To: "+toDate;
		}
	}
	 
	  
	public String toString(){
		return name;
	}
	
}
