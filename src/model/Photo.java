package model;

import java.nio.file.attribute.FileTime;
import java.util.ArrayList;
import java.util.Date;

public class Photo {
	
	String caption;
	String location;
	ArrayList<Tag> tags = new ArrayList<Tag>();
	long date;
	public Photo(String caption, String location)
	{
		this.location=location;
		this.caption=caption; 
	}	
	public Photo(String caption, String location,ArrayList<Tag> tags,long date)
	{
		this.location=location;
		this.caption=caption; 
		this.date=date;
		this.tags=tags;
	}	
	
	public String getLocation(){
		return location;
	}
	
	public String getCaption(){
		return caption;
	}
	public long getDate(){
		return date;
	}
	public ArrayList<Tag> getTags(){
		return tags;
	}
	public String printAttributes(){
		return caption+"\n"
				+"date created:"+new Date(date)+"\n"
				+"Tags"+tags;
	}

	public String toString(){
		return caption;
	}
}
