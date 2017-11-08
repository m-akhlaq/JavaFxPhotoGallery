package model;

import java.util.ArrayList;
import java.util.Date;

public class Photo {
	
	String caption;
	String location;
	ArrayList<Tag> tags = new ArrayList<Tag>();
	Date date;	
	
	public Photo(String caption, String location,Date date)
	{
		this.location=location;
		this.caption=caption; 
		this.date=date;
		this.tags=tags;
	}	
	public Photo(){
		
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
	public void setDate(Date d){
		date=d;
	}
	public void setLocation(String l){
		location=l;
	}
	public void addTag(Tag t){
		tags.add(t);
	}
	
	
	public boolean isInRange(Date fromDate, Date toDate){
		int isCurrentDateBigger = getDate().compareTo(fromDate);
		if (isCurrentDateBigger>=0 && getDate().compareTo(toDate)<0){
			
			return true;
		}
		return false; 
	}
	public boolean hasTags(ArrayList<Tag> paramTag){
		if (paramTag.isEmpty())
			return true;
		for (Tag t:paramTag){
			if (matchTag(t)==false){
				return false;
			}
		}
		return true;
		
	}
	private boolean matchTag(Tag tag){
		for (Tag t:tags){
			if (t.getKey().equals(tag.getKey())&& t.getValue().equals(tag.getValue())){
				return true;
			}
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
