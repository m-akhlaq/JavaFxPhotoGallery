package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
/**
 * This is the data class for photo
 * @author Muhammad Akhlaq
 * @author John Brauner
 *
 */
public class Photo implements Serializable {
	static final long serialVersionUID=1L;
	String caption;
	String location;
	ArrayList<Tag> tags = new ArrayList<Tag>();
	Date date;	
	/**
	 * 
	 * @param caption the caption of the photo
	 * @param location filepath of the photo
	 * @param date date of the photo
	 */
	public Photo(String caption, String location,Date date)
	{
		this.location=location;
		this.caption=caption; 
		this.date=date;
		this.tags=tags;
	}	
	/**
	 * Empty constructor used in editing tags on photos
	 */
	public Photo(){
		
	}
	/**
	 * 
	 * @return filepath of photo
	 */
	public String getLocation(){
		return location;
	}
	
	/**
	 * 
	 * @return caption of photo
	 */
	public String getCaption(){
		return caption;
	}
	/**
	 * 
	 * @return date of photo
	 */
	public Date getDate(){
		return date;
	}
	/**
	 * 
	 * @return the tags on the photo
	 */
	public ArrayList<Tag> getTags(){
		return tags;
	}
	/**
	 * 
	 * @param c the new caption
	 */
	public void setCaption(String c){
		caption=c;
	}
	/**
	 * 
	 * @param t a new list of tags
	 */
	public void setTags(ArrayList<Tag> t){
		tags=t;
	}
	/**
	 * 
	 * @param d new date of the photo
	 */
	public void setDate(Date d){
		date=d;
	}
	/**
	 * 
	 * @param l the new filepath of the photo
	 */
	public void setLocation(String l){
		location=l;
	}
	/**
	 * 
	 * @param t a new tag to be added
	 */
	public void addTag(Tag t){
		tags.add(t);
	}
	
	/**
	 * this checks if the photo was taken in a certain data range
	 * @param fromDate the starting date
	 * @param toDate the ending date
	 * @return true-the photo is taken between the dates specified above false if not
	 */
	public boolean isInRange(Date fromDate, Date toDate){
		int isCurrentDateBigger = getDate().compareTo(fromDate);
		if (isCurrentDateBigger>=0 && getDate().compareTo(toDate)<0){
			
			return true;
		}
		return false; 
	}
	/**
	 * this checks if the photo has all the tags that are required for a search to classify this as a result
	 * @param paramTag the tags we want to check
	 * @return if photo contains all such tags
	 */
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
	/**
	 * this checks if the tag is in a photo
	 * @param tag weather a certain tag is in the photo
	 * @return weather the tag is in the photo
	 */
	public boolean matchTag(Tag tag){
		for (Tag t:tags){
			if (t.getKey().toLowerCase().equals(tag.getKey().toLowerCase())&& t.getValue().toLowerCase().equals(tag.getValue().toLowerCase())){
				return true;
			}
		}
		return false;
	}
	/**
	 * 
	 * @return the attributes of the photo
	 */
	public String printAttributes(){
		return caption+"\n"
				+"date created:"+date+"\n"
				+"Tags"+tags;
	}

	public String toString(){
		return caption;
	}
}
