package model;

import java.io.Serializable;
import java.util.ArrayList;

public class User implements Serializable  {
	static final long serialVersionUID=1L;
	
	String fullName;
	String userName;
	ArrayList<Album> albums = new ArrayList<Album>();
	
	public User(String fullName,String userName){
		this.fullName=fullName;
		this.userName=userName;
	}
	
	
	public void addAlbums(Album name){
		albums.add(name);
	}
	
	public ArrayList<Album> getAlbum(){
		return albums;
	}
	public boolean nameExist(String name){
		name.trim();
		for (Album a:albums){
			if (a.getName().toLowerCase().equals(name.toLowerCase())){
				return true;
			}
		}
		return false;
	}
	
	public String getName(){
		return fullName;
	}

}
