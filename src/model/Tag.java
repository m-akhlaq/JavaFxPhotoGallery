package model;

import java.io.Serializable;

public class Tag implements Serializable {
	static final long serialVersionUID=1L;
	String key;
	String value;
	/**
	 * This class defines a tag as a pair of key value object where key is something like location and value is 
	 * something like Italy.
	 * @param key the tag key
	 * @param value the value the belongs to the key
	 */
	public Tag(String key,String value){
		this.key=key;
		this.value=value;
	}
	/**
	 * 
	 * @return key type String
	 */
	
	public String getKey(){
		return key;
	}
	/**
	 * 
	 * @return value of type string
	 */
	public String getValue(){
		return value;
	}
	public String toString(){
		return key+" "+value;
	}

}
