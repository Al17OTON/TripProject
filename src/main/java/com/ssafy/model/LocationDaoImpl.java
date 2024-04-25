package com.ssafy.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.ssafy.dto.Location;
import com.ssafy.dto.MyPlace;
import com.ssafy.util.DBUtil;

public class LocationDaoImpl implements LocationDao {
	
	@Override
	public Location getLocationById(String id) throws Exception {
		Location location = null;
		Connection conn = DBUtil.getConnection();
		
		String sql = "select attraction_description.content_id, overview, content_type_id, title, addr1, addr2, first_image, tel, sido_code, gugun_code, recommend, latitude, longitude, mlevel from attraction_description, attraction_info where attraction_description.content_id = attraction_info.content_id and attraction_info.content_id=?";
		PreparedStatement stmt = conn.prepareStatement(sql);
		stmt.setString(1, id);
		ResultSet infoRes = stmt.executeQuery();
		
		if(infoRes.next()) {
			location = new Location(
					infoRes.getString("content_id"),
					infoRes.getString("overview"),			
					infoRes.getString("content_type_id"),
					infoRes.getString("title"),
					infoRes.getString("addr1"),
					infoRes.getString("addr2"),
					infoRes.getString("first_image"),
					infoRes.getString("tel"),
					infoRes.getString("sido_code"),
					infoRes.getString("gugun_code"),
					infoRes.getString("recommend"),
					infoRes.getString("latitude"),
					infoRes.getString("longitude"),
					infoRes.getString("mlevel")
					);
		}
		
		
		conn.close();
		return location;
	}

	@Override
	public List<Location> getLocationList(String sido_code, String gugun_code, String content_type_id) throws Exception {
		List<Location> list = new ArrayList<>();
		Connection conn = DBUtil.getConnection();
		
		String sql = "select attraction_description.content_id, overview, content_type_id, title, addr1, addr2, first_image, tel, sido_code, gugun_code, recommend, latitude, longitude, mlevel from attraction_description, attraction_info where attraction_description.content_id = attraction_info.content_id and sido_code=? and gugun_code=? and content_type_id=?";
		PreparedStatement stmt = conn.prepareStatement(sql);
		stmt.setString(1, sido_code);
		stmt.setString(2, gugun_code);
		stmt.setString(3, content_type_id);
		ResultSet infoRes = stmt.executeQuery();
		
		while(infoRes.next()) {
			list.add(new Location(
					infoRes.getString("content_id"),
					infoRes.getString("overview"),			
					infoRes.getString("content_type_id"),
					infoRes.getString("title"),
					infoRes.getString("addr1"),
					infoRes.getString("addr2"),
					infoRes.getString("first_image"),
					infoRes.getString("tel"),
					infoRes.getString("sido_code"),
					infoRes.getString("gugun_code"),
					infoRes.getString("recommend"),
					infoRes.getString("latitude"),
					infoRes.getString("longitude"),
					infoRes.getString("mlevel")
					));
		}
		
		
		conn.close();
		return list;
	}

	@Override
	public void increaseRecommend(String id) throws Exception {
		Connection conn = DBUtil.getConnection();
		String sql = "update attraction_info set recommend = recommend + 1 where content_id=?";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, id);
		pstmt.executeUpdate();
		conn.close();
	}

	@Override
	public List<Location> getLocationList(String sido_code, String gugun_code) throws Exception {
		List<Location> list = new ArrayList<>();
		Connection conn = DBUtil.getConnection();
		
		String sql = "select attraction_description.content_id, overview, content_type_id, title, addr1, addr2, first_image, tel, sido_code, gugun_code, recommend, latitude, longitude, mlevel from attraction_description, attraction_info where attraction_description.content_id = attraction_info.content_id and sido_code=? and gugun_code=?";
		PreparedStatement stmt = conn.prepareStatement(sql);
		stmt.setString(1, sido_code);
		stmt.setString(2, gugun_code);
		ResultSet infoRes = stmt.executeQuery();
		
		while(infoRes.next()) {
			list.add(new Location(
					infoRes.getString("content_id"),
					infoRes.getString("overview"),			
					infoRes.getString("content_type_id"),
					infoRes.getString("title"),
					infoRes.getString("addr1"),
					infoRes.getString("addr2"),
					infoRes.getString("first_image"),
					infoRes.getString("tel"),
					infoRes.getString("sido_code"),
					infoRes.getString("gugun_code"),
					infoRes.getString("recommend"),
					infoRes.getString("latitude"),
					infoRes.getString("longitude"),
					infoRes.getString("mlevel")
					));
		}
		
		
		conn.close();
		return list;
	}

	@Override
	public List<Location> getLocationList(String sido_code) throws Exception {
		List<Location> list = new ArrayList<>();
		Connection conn = DBUtil.getConnection();
		
		String sql = "select attraction_description.content_id, overview, content_type_id, title, addr1, addr2, first_image, tel, sido_code, gugun_code, recommend, latitude, longitude, mlevel from attraction_description, attraction_info where attraction_description.content_id = attraction_info.content_id and sido_code=?";
		PreparedStatement stmt = conn.prepareStatement(sql);
		stmt.setString(1, sido_code);
		ResultSet infoRes = stmt.executeQuery();
		
		while(infoRes.next()) {
			list.add(new Location(
					infoRes.getString("content_id"),
					infoRes.getString("overview"),			
					infoRes.getString("content_type_id"),
					infoRes.getString("title"),
					infoRes.getString("addr1"),
					infoRes.getString("addr2"),
					infoRes.getString("first_image"),
					infoRes.getString("tel"),
					infoRes.getString("sido_code"),
					infoRes.getString("gugun_code"),
					infoRes.getString("recommend"),
					infoRes.getString("latitude"),
					infoRes.getString("longitude"),
					infoRes.getString("mlevel")
					));
		}
		
		
		conn.close();
		return list;
	}

	@Override
	public List<Location> getLocationList() throws Exception {
		List<Location> list = new ArrayList<>();
		Connection conn = DBUtil.getConnection();
		
		String sql = "select attraction_description.content_id, overview, content_type_id, title, addr1, addr2, first_image, tel, sido_code, gugun_code, recommend, latitude, longitude, mlevel from attraction_description, attraction_info where attraction_description.content_id = attraction_info.content_id";
		PreparedStatement stmt = conn.prepareStatement(sql);
		ResultSet infoRes = stmt.executeQuery();
		
		while(infoRes.next()) {
			list.add(new Location(
					infoRes.getString("content_id"),
					infoRes.getString("overview"),			
					infoRes.getString("content_type_id"),
					infoRes.getString("title"),
					infoRes.getString("addr1"),
					infoRes.getString("addr2"),
					infoRes.getString("first_image"),
					infoRes.getString("tel"),
					infoRes.getString("sido_code"),
					infoRes.getString("gugun_code"),
					infoRes.getString("recommend"),
					infoRes.getString("latitude"),
					infoRes.getString("longitude"),
					infoRes.getString("mlevel")
					));
		}
		
		
		conn.close();
		return list;
	}

	@Override
	public List<Location> getHotPlaceList(String sido_code, String gugun_code, int num) throws Exception {
		List<Location> list = new ArrayList<>();
		Connection conn = DBUtil.getConnection();
		
		if(sido_code == null) sido_code = "*";
		if(gugun_code == null) gugun_code = "*";
		
		String sql = "select attraction_description.content_id, overview, content_type_id, title, addr1, addr2, first_image, tel, sido_code, gugun_code, recommend, latitude, longitude, mlevel from attraction_description, attraction_info where attraction_description.content_id = attraction_info.content_id and sido_code=? and gugun_code=? order by recommend DESC limit ?";
		PreparedStatement stmt = conn.prepareStatement(sql);
		stmt.setString(1, sido_code);
		stmt.setString(2, gugun_code);
		stmt.setInt(3, num);
		ResultSet infoRes = stmt.executeQuery();
		
		while(infoRes.next()) {
			list.add(new Location(
					infoRes.getString("content_id"),
					infoRes.getString("overview"),			
					infoRes.getString("content_type_id"),
					infoRes.getString("title"),
					infoRes.getString("addr1"),
					infoRes.getString("addr2"),
					infoRes.getString("first_image"),
					infoRes.getString("tel"),
					infoRes.getString("sido_code"),
					infoRes.getString("gugun_code"),
					infoRes.getString("recommend"),
					infoRes.getString("latitude"),
					infoRes.getString("longitude"),
					infoRes.getString("mlevel")
					));
		}
		
		
		conn.close();
		return list;
	}

	@Override
	public void addMyPlace(MyPlace place) throws Exception {
		Connection conn = DBUtil.getConnection();
		
		String sql = "insert into myplace (user_id, title, content_type, info, mapx, mapy) value (?,?,?,?,?,?)";
		PreparedStatement stmt = conn.prepareStatement(sql);
		
		stmt.setString(1, place.getUser_id());
		stmt.setString(2, place.getTitle());
		stmt.setString(3, place.getContent_type());
		stmt.setString(4, place.getInfo());
		stmt.setString(5, place.getMapx());
		stmt.setString(6, place.getMapy());
		
		stmt.executeUpdate();
		conn.close();
		
	}

	@Override
	public void removeMyPlace(String contentId) throws Exception {
		Connection conn = DBUtil.getConnection();
		
		String sql = "delete from myplace where content_id=?";
		PreparedStatement stmt = conn.prepareStatement(sql);
		
		stmt.setString(1, contentId);
		
		stmt.executeUpdate();
		conn.close();
	}

	@Override
	public List<MyPlace> getMyPlaceList(String userId) throws Exception {
		List<MyPlace> list = new ArrayList<>();
		Connection conn = DBUtil.getConnection();
		
		String sql = "select content_id, user_id, title, content_type, info, mapx, mapy from myplace where user_id=?";
		PreparedStatement stmt = conn.prepareStatement(sql);
		stmt.setString(1, userId);
		ResultSet infoRes = stmt.executeQuery();
		
		while(infoRes.next()) {
			list.add(new MyPlace(
					infoRes.getString("content_id"),
					infoRes.getString("user_id"),
					infoRes.getString("title"),
					infoRes.getString("content_type"),
					infoRes.getString("info"),
					infoRes.getString("mapx"),
					infoRes.getString("mapy")
					));
		}
		
		
		conn.close();
		return list;
		
	}

}
