package uk.co.monkeypower.android.straightrazordatabase.content;

import java.util.ArrayList;

import android.graphics.Bitmap;

public class RazorContent extends Content {
	
	private String manufacturer;
	private String model;
	private String region;
	private String date;
	private String grind;
	private String size;
	private String point;
	private String shoulder;
	private String spineDecoration;
	private String jimps;
	private String scales;
	private String steel;
	private String extraContent;
	
	private ArrayList<Bitmap> images;

	public String getManufacturer() {
		return manufacturer;
	}

	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getGrind() {
		return grind;
	}

	public void setGrind(String grind) {
		this.grind = grind;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public String getPoint() {
		return point;
	}

	public void setPoint(String point) {
		this.point = point;
	}

	public String getShoulder() {
		return shoulder;
	}

	public void setShoulder(String shoulder) {
		this.shoulder = shoulder;
	}

	public String getSpineDecoration() {
		return spineDecoration;
	}

	public void setSpineDecoration(String spineDecoration) {
		this.spineDecoration = spineDecoration;
	}

	public String getJimps() {
		return jimps;
	}

	public void setJimps(String jimps) {
		this.jimps = jimps;
	}

	public String getScales() {
		return scales;
	}

	public void setScales(String scales) {
		this.scales = scales;
	}

	public String getSteel() {
		return steel;
	}

	public void setSteel(String steel) {
		this.steel = steel;
	}

	public String getExtraContent() {
		return extraContent;
	}

	public void setExtraContent(String extraContent) {
		this.extraContent = extraContent;
	}

	public ArrayList<Bitmap> getImages() {
		return images;
	}

	public void setImages(ArrayList<Bitmap> images) {
		this.images = images;
	}
	
	
	
}
