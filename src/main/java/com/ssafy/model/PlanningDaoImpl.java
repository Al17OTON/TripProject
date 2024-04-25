package com.ssafy.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import com.ssafy.dto.Planning;
import com.ssafy.util.DBUtil;

public class PlanningDaoImpl implements PlanningDao{

	@Override
	public boolean addPlan(String pName, String distance, String[] lat, String[] lng, String id) throws SQLException {
		Connection conn = DBUtil.getConnection();
		String sql = "insert into t_plan(user_id, plan_name, distance) values (?,?,?)";
		PreparedStatement pstmt = conn.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
		pstmt.setString(1, id);
		pstmt.setString(2, pName);
		pstmt.setString(3, distance);
		pstmt.executeUpdate();
		
		int plan_id = 0;
		ResultSet rs = pstmt.getGeneratedKeys(); //자동생성된 인덱스 번호 가져오기
		if(rs.next())plan_id = rs.getInt(1);
		System.out.println("plan_id :" +plan_id);
		
		
		String sql2 = "insert into t_route(plan_id, route_seq, lat, lon, plan_name) values(?,?,?,?,?)";
		for (int i = 1; i < lng.length; i++) {
			sql2 += ",(?,?,?,?,?)";
		}
		int t = 0;
		pstmt = conn.prepareStatement(sql2);
		for (int i = 0; i < lng.length; i++) {
			pstmt.setString(++t, plan_id+"");
			pstmt.setString(++t, i+"");
			pstmt.setString(++t,lat[i]);
			pstmt.setString(++t,lng[i]);
			System.out.println(lat[i] + " " + lng[i]);
			pstmt.setString(++t, pName);
		}
		pstmt.executeUpdate();
		conn.close();
		return false;
	}

	@Override
	public ArrayList<Planning> getplanlist(String id) throws SQLException {
		ArrayList<Planning> planlist = new ArrayList<>();
		Connection conn = DBUtil.getConnection();
		String sql = "SELECT t_plan.plan_name,t_route.plan_id ,t_plan.distance, t_route.lat, t_route.lon "
				+ "FROM t_plan "
				+ "JOIN t_route ON t_plan.plan_id = t_route.plan_id "
				+ "WHERE t_plan.user_id = ?";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, id);
		ResultSet rs = pstmt.executeQuery();
		int idx =-1;
		HashSet<String> isId = new HashSet<>();
		while(rs.next()) {
			String planName = rs.getString("plan_name");
			String planId = rs.getString("plan_id");
	        String distance = rs.getString("distance");
	        String latitude = rs.getString("lat");
	        String longitude = rs.getString("lon");
	        if(isId.contains(planId)) {
	        	planlist.get(idx).addLat(latitude);
	        	planlist.get(idx).addLon(longitude);	        	
	        }else {
	        	planlist.add(new Planning());
	        	idx++;
	        	planlist.get(idx).setpName(planName);
	        	planlist.get(idx).setDistance(distance);
	        	planlist.get(idx).addLat(latitude);
	        	planlist.get(idx).addLon(longitude);
	        	isId.add(planId);
	        }
			
		}
		conn.close();
		return planlist;
	}

}
