package com.ssafy.model;


import java.util.ArrayList;
import java.util.List;
import com.ssafy.dto.Location;
import com.ssafy.dto.MyPlace;

public class LocationServiceImpl implements LocationService {
	
	LocationDaoImpl locationdao = new LocationDaoImpl();
	@Override
	public List<Location> getLocationList(String sido_code, String gugun_code, String content_type_id) throws Exception {
		if(content_type_id != null && content_type_id.equals("100")) return locationdao.getHotPlaceList(sido_code, gugun_code, 10);
		else if(!sido_code.equals("0") && !gugun_code.equals("0") && !content_type_id.equals("0")) return locationdao.getLocationList(sido_code, gugun_code, content_type_id);
		else if(!sido_code.equals("0") && !gugun_code.equals("0") && content_type_id.equals("0")) return locationdao.getLocationList(sido_code, gugun_code); 
		else if(!sido_code.equals("0") && gugun_code.equals("0") && content_type_id.equals("0")) return locationdao.getLocationList(sido_code);
		else return locationdao.getLocationList();
	}
	@Override
	public Location getLocationById(String id) throws Exception {
		return locationdao.getLocationById(id);
	}
	@Override
	public void recommendLocation(String content_id) throws Exception {
		locationdao.increaseRecommend(content_id);
	}

	@Override
	public void addMyPlace(Location place) throws Exception {
		locationdao.addMyPlace(location2MyPlace(place));
	}
	@Override
	public void removeMyPlace(String contentId) throws Exception {
		locationdao.removeMyPlace(contentId);
	}
	@Override
	public List<Location> getMyPlaceList(String userId) throws Exception {
		return myPlace2Location(locationdao.getMyPlaceList(userId));
	}
	@Override
	public List<Location> myPlace2Location(List<MyPlace> m) {
		List<Location> list = new ArrayList<>();
		
		for(MyPlace my : m) {
			Location l = new Location();
			l.setTitle(my.getTitle());
			l.setAddr1(my.getInfo());
			l.setTel(my.getContent_type());
			l.setLatitude(my.getMapy());
			l.setLongitude(my.getMapx());
			l.setContent_id(my.getContent_id());
			l.setAddr2(my.getUser_id());
			list.add(l);
		}
		
		return list;
	}
	@Override
	public MyPlace location2MyPlace(Location l) {
		MyPlace my = new MyPlace();
		my.setTitle(l.getTitle());
		my.setInfo(l.getAddr1());
		my.setContent_type(l.getTel());
		my.setMapy(l.getLatitude());
		my.setMapx(l.getLongitude());
		my.setContent_id(l.getContent_id());
		my.setUser_id(l.getAddr2());
		
		return my;
	}


}
