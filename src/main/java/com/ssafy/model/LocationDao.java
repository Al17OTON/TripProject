package com.ssafy.model;

import java.util.List;

import com.ssafy.dto.Location;
import com.ssafy.dto.MyPlace;

public interface LocationDao {
	public Location getLocationById(String id) throws Exception;
	public List<Location> getLocationList(String sido_code, String gugun_code, String content_type_id) throws Exception;
	public List<Location> getLocationList(String sido_code, String gugun_code) throws Exception;
	public List<Location> getLocationList(String sido_code) throws Exception;
	public List<Location> getLocationList() throws Exception;
	public void increaseRecommend(String id) throws Exception;
	
	public List<Location> getHotPlaceList(String sido_code, String gugun_code, int num) throws Exception;
	
	public void addMyPlace(MyPlace place) throws Exception;
	public void removeMyPlace(String contentId) throws Exception;
	public List<MyPlace> getMyPlaceList(String userId) throws Exception;
}
