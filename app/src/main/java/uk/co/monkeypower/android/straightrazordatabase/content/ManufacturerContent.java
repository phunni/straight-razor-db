package uk.co.monkeypower.android.straightrazordatabase.content;

import java.util.ArrayList;

import android.graphics.Bitmap;

public class ManufacturerContent extends Content {
	
	private String Location;
	private String datesOfOperation;
	private ArrayList<String> externalLinks;
	private String owners;
	private String extraContent;
	private ArrayList<String> references; 
	
	private ArrayList<Bitmap> uploads;
	private Bitmap logo;
	public String getLocation() {
		return Location;
	}
	public void setLocation(String location) {
		Location = location;
	}
	public String getDatesOfOperation() {
		return datesOfOperation;
	}
	public void setDatesOfOperation(String datesOfOperation) {
		this.datesOfOperation = datesOfOperation;
	}
	public ArrayList<String> getExternalLinks() {
		return externalLinks;
	}
	public void setExternalLinks(ArrayList<String> externalLinks) {
		this.externalLinks = externalLinks;
	}
	public String getOwners() {
		return owners;
	}
	public void setOwners(String owners) {
		this.owners = owners;
	}
	public String getExtraContent() {
		return extraContent;
	}
	public void setExtraContent(String extraContent) {
		this.extraContent = extraContent;
	}
	public ArrayList<Bitmap> getUploads() {
		return uploads;
	}
	public void setUploads(ArrayList<Bitmap> uploads) {
		this.uploads = uploads;
	}
	public Bitmap getLogo() {
		return logo;
	}
	public void setLogo(Bitmap logo) {
		this.logo = logo;
	}
	public ArrayList<String> getReferences() {
		return references;
	}
	public void setReferences(ArrayList<String> references) {
		this.references = references;
	}

}
