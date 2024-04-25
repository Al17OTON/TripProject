package com.ssafy.dto;

import java.util.ArrayList;
import java.util.Arrays;

public class Planning {
	private String pName;
	private String distance;
	private ArrayList<String> lat;
	private ArrayList<String> lon;
	
	public Planning() {
		this.lat = new ArrayList<>();
		this.lon = new ArrayList<>(); 
	}
	
	public Planning(String pName, String distance, ArrayList<String> lat, ArrayList<String> lon) {
		super();
		this.pName = pName;
		this.distance = distance;
		this.lat = lat;
		this.lon = lon;
	}
	public String getpName() {
		return pName;
	}
	public void setpName(String pName) {
		this.pName = pName;
	}
	public String getDistance() {
		return distance;
	}
	public void setDistance(String distance) {
		this.distance = distance;
	}
	public ArrayList<String> getLat() {
		return lat;
	}
	public void setLat(ArrayList<String> lat) {
		this.lat = lat;
	}
	public ArrayList<String> getLon() {
		return lon;
	}
	public void setLon(ArrayList<String> lon) {
		this.lon = lon;
	}
	
	public void addLat(String lat) {
		this.lat.add(lat);
	}
	public void addLon(String lon) {
		this.lon.add(lon);
	}
}
