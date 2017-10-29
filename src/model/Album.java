package model;

import java.util.ArrayList;

public class Album {

	String name;
	ArrayList<Photo> photos = new ArrayList<Photo>();
	public Album(String name){
		this.name=name;
	}
	
	public String toString(){
		return name;
	}
	
	public ArrayList<Photo> getPhotos(){
		return photos;
	}
	public void addPhotos(Photo p){
		photos.add(p);
	}
	
}
