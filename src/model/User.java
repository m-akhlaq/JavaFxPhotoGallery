package model;

import java.io.Serializable;
import java.util.ArrayList;
/**
 * 
 * @author Muhammad Akhlaq
 * @author John Brauner
 *
 */
public class User implements Serializable  {
	static final long serialVersionUID=1L;
	
	String fullName;
	String userName;
	ArrayList<Album> albums = new ArrayList<Album>();
	/**
	 * constructs the object using name of person
	 * @param fullName not used, the full name of the person
	 * @param userName the user name of the person
	 */
	public User(String fullName,String userName){
		this.fullName=fullName;
		this.userName=userName;
	}
	
	/**
	 * 
	 * @param name new album that is to be addded
	 */
	public void addAlbums(Album name){
		albums.add(name);
	}
	/**
	 * 
	 * @return list of albums
	 */
	public ArrayList<Album> getAlbum(){
		return albums;
	}
	/**
	 * check weahter the name of the album already exist
	 * @param name the name of the album
	 * @return true if name exists, false if it does not
	 */
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
