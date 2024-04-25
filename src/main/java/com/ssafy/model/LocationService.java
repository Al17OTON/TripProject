package com.ssafy.model;

import java.util.ArrayList;
import java.util.List;

import com.ssafy.dto.Location;
import com.ssafy.dto.MyPlace;

public interface LocationService {
	public List<Location> getLocationList(String sido_code, String gugun_code, String content_type_id) throws Exception;
	public Location getLocationById(String id) throws Exception;
	public void recommendLocation(String content_id) throws Exception;
	
	public void addMyPlace(Location place) throws Exception;
	public void removeMyPlace(String contentId) throws Exception;
	public List<Location> getMyPlaceList(String userId) throws Exception;
	
	public List<Location> myPlace2Location(List<MyPlace> m);
	public MyPlace location2MyPlace(Location l);
	
}
