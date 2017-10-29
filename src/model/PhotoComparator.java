package model;

import java.util.Comparator;
import java.util.Date;

public class PhotoComparator implements Comparator<Photo> {

	@Override
	public int compare(Photo o1, Photo o2) {
			Date p1Date = new Date(o1.getDate());
			Date p2Date = new Date(o2.getDate());
			return p1Date.compareTo(p2Date);
	}

}
