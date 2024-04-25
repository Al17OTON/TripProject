package com.ssafy.model;

import java.sql.SQLException;
import java.util.ArrayList;
import com.ssafy.dto.Planning;

public class PlanningServiceImpl implements PlanningService{

	static PlanningDaoImpl planningDao = new PlanningDaoImpl();
	
	public boolean addPlan(String pName, String distance, String[] lat, String[] lng, String id) throws SQLException {
		
		return planningDao.addPlan(pName, distance, lat, lng, id);
	}

	@Override
	public ArrayList<Planning> getplanlist(String id) throws SQLException {
		
		return planningDao.getplanlist(id);
	}



}
