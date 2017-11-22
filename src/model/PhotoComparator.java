package model;

import java.util.Comparator;
import java.util.Date;
/**
 * this class is the comparator used to sort photos
 * @author Muhammad Akhlaq
 * @author John Brauner
 *
 */
public class PhotoComparator implements Comparator<Photo> {

	
	@Override
	public int compare(Photo o1, Photo o2) {
			Date p1Date = o1.getDate();
			Date p2Date = o2.getDate();
			return p1Date.compareTo(p2Date);
	}

}
