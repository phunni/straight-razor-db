package uk.co.redfruit.libraries.srpDB.data;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * User: paul
 * Date: 01/07/13
 * Time: 19:06
 */
public class Manufacturer implements Parcelable, Comparable<Manufacturer> {

    private int pageID;
    private String title;
    
    public Manufacturer() {}

    private Manufacturer(Parcel source) {
		pageID = source.readInt();
		title = source.readString();
	}

	public int getPageID() {
        return pageID;
    }

    public void setPageID(int pageID) {
        this.pageID = pageID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(pageID);
		dest.writeString(title);
		
	}
	
	public static final Parcelable.Creator<Manufacturer> CREATOR = new Creator<Manufacturer>() {
		
		@Override
		public Manufacturer[] newArray(int size) {
			return new Manufacturer[size];
		}
		
		@Override
		public Manufacturer createFromParcel(Parcel source) {
			Manufacturer manufacturer = new Manufacturer(source);
			return manufacturer;
		}
	};

	@Override
	public int compareTo(Manufacturer another) {
		if (this.equals(another)) {
			return 0;
		} 
		String title = another.getTitle();
		return this.title.compareToIgnoreCase(title);
	}
}
