package com.ssafy.model;

import java.sql.SQLException;
import java.util.ArrayList;
import com.ssafy.dto.Planning;

public interface PlanningService {
	public boolean addPlan(String pName, String distance, String lat[], String lng[], String id) throws SQLException;
	public ArrayList<Planning> getplanlist(String id)  throws SQLException;
}
