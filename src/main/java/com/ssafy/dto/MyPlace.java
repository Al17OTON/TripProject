package com.ssafy.dto;

public class MyPlace {
	String content_id;
	String user_id;
	String title; 
	String content_type; 
	String info; 
	String mapx; 
	String mapy;
	
	public MyPlace() {}
	
	public MyPlace(String content_id, String user_id, String title, String content_type, String info, String mapx,
			String mapy) {
		super();
		this.content_id = content_id;
		this.user_id = user_id;
		this.title = title;
		this.content_type = content_type;
		this.info = info;
		this.mapx = mapx;
		this.mapy = mapy;
	}
	public String getContent_id() {
		return content_id;
	}
	public void setContent_id(String content_id) {
		this.content_id = content_id;
	}
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
	
	public String getContent_type() {
		return content_type;
	}

	public void setContent_type(String content_type) {
		this.content_type = content_type;
	}

	public String getInfo() {
		return info;
	}
	public void setInfo(String info) {
		this.info = info;
	}
	public String getMapx() {
		return mapx;
	}
	public void setMapx(String mapx) {
		this.mapx = mapx;
	}
	public String getMapy() {
		return mapy;
	}
	public void setMapy(String mapy) {
		this.mapy = mapy;
	}
	
	
}
