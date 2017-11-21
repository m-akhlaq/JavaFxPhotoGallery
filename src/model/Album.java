package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

public class Album implements Serializable {

	String name;
	ArrayList<Photo> photos = new ArrayList<Photo>();
	public Album(String name){
		this.name=name;
	}
	

	public String getName(){
		return name;
	}
	public ArrayList<Photo> getPhotos(){
		return photos;
	}
	public void addPhotos(Photo p){
		photos.add(p);
	}
	public void setName(String n){
		name=n;
	}
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
