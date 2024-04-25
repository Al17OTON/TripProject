package com.ssafy.dto;

public class Location {
	//attraction_description
	private String content_id;
	private String overview;
	
	//attraction_info
	private String content_type_id;
	private String title;
	private String addr1;
	private String addr2;
	private String img;
	private String tel;
	private String sido_code;
	private String gugun_code;
	private String recommend;
	private String latitude;
	private String longitude;
	private String mlevel;
	
	public Location() {}

	public Location(String content_id, String overview, String content_type_id, String title, String addr1,
			String addr2, String img, String tel, String sido_code, String gugun_code, String recommend,
			String latitude, String longitude, String mlevel) {
		super();
		this.content_id = content_id;
		this.overview = overview;
		this.content_type_id = content_type_id;
		this.title = title;
		this.addr1 = addr1;
		this.addr2 = addr2;
		this.img = img;
		this.tel = tel;
		this.sido_code = sido_code;
		this.gugun_code = gugun_code;
		this.recommend = recommend;
		this.latitude = latitude;
		this.longitude = longitude;
		this.mlevel = mlevel;
	}


	public String getAddr2() {
		return addr2;
	}

	public void setAddr2(String addr2) {
		this.addr2 = addr2;
	}

	public String getContent_id() {
		return content_id;
	}
	public void setContent_id(String content_id) {
		this.content_id = content_id;
	}
	public String getOverview() {
		return overview;
	}
	public void setOverview(String overview) {
		this.overview = overview;
	}
	public String getContent_type_id() {
		return content_type_id;
	}
	public void setContent_type_id(String content_type_id) {
		this.content_type_id = content_type_id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getAddr1() {
		return addr1;
	}
	public void setAddr1(String addr1) {
		this.addr1 = addr1;
	}
	public String getImg() {
		return img;
	}
	public void setImg(String img) {
		this.img = img;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getSido_code() {
		return sido_code;
	}
	public void setSido_code(String sido_code) {
		this.sido_code = sido_code;
	}
	public String getGugun_code() {
		return gugun_code;
	}
	public void setGugun_code(String gugun_code) {
		this.gugun_code = gugun_code;
	}
	public String getRecommend() {
		return recommend;
	}
	public void setRecommend(String recommend) {
		this.recommend = recommend;
	}
	public String getLatitude() {
		return latitude;
	}
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
	public String getLongitude() {
		return longitude;
	}
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	public String getMlevel() {
		return mlevel;
	}
	public void setMlevel(String mlevel) {
		this.mlevel = mlevel;
	}


	@Override
	public String toString() {
		return "Location [content_id=" + content_id + ", overview=" + overview + ", content_type_id=" + content_type_id
				+ ", title=" + title + ", addr1=" + addr1 + ", img=" + img + ", tel=" + tel + ", sido_code=" + sido_code
				+ ", gugun_code=" + gugun_code + ", recommend=" + recommend + ", latitude=" + latitude + ", longitude="
				+ longitude + ", mlevel=" + mlevel + "]";
	}
	
	
	
}
