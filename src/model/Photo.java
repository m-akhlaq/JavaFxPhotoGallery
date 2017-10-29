package model;

public class Photo {
	
	String caption;
	String location;
	public Photo(String caption, String location)
	{
		this.location=location;
		this.caption=caption; 
	}	
	
	public String getLocation(){
		return location;
	}
	
	public String toString(){
		return caption;
	}
	public String getCaption(){
		return caption;
	}
	
	
	
}
