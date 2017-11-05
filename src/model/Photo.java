package model;

import java.util.ArrayList;
import java.util.Date;

public class Photo {
	
	String caption;
	String location;
	ArrayList<Tag> tags = new ArrayList<Tag>();
	Date date;	
	
	public Photo(String caption, String location,ArrayList<Tag> tags,Date date)
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
	public Date getDate(){
		return date;
	}
	public ArrayList<Tag> getTags(){
		return tags;
	}
	public void setCaption(String c){
		caption=c;
	}
	public void setTags(ArrayList<Tag> t){
		tags=t;
	}
	
	
	public boolean isInRange(Date fromDate, Date toDate){
		int isCurrentDateBigger = getDate().compareTo(fromDate);
		if (isCurrentDateBigger>=0 && getDate().compareTo(toDate)<0){
			
			return true;
		}
		return false; 
	}
	
	public String printAttributes(){
		return caption+"\n"
				+"date created:"+date+"\n"
				+"Tags"+tags;
	}

	public String toString(){
		return caption;
	}
}
