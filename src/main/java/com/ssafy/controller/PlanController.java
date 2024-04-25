package com.ssafy.controller;

import jakarta.servlet.ServletException;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;

import com.ssafy.dto.Planning;
import com.ssafy.model.LocationDaoImpl;
import com.ssafy.model.PlanningServiceImpl;


@WebServlet(urlPatterns = {"/plan"})
public class PlanController extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public PlanController() {super();}
    public PlanningServiceImpl planImpl = new PlanningServiceImpl();
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		process(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		process(request, response);
	}
	
	private void process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter("action");
		String url = "";
		try {
			if(action != null) {
				if(action.equals("addplan")) {
					addPlan(request,response);
					return;
				}else if(action.equals("listplan")) {
					listplan(request,response);
					return;
				}
			}

		}catch(Exception e) {
			request.setAttribute("msg", e.toString());
			url = "error/error.jsp";
		}		
		
		
		
	}

	private void listplan(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
		 String id = (String) request.getSession().getAttribute("login");
		 ArrayList<Planning> planList = planImpl.getplanlist(id);
		    
		    // JSON 형식의 문자열 직접 생성
		 StringBuilder jsonBuilder = new StringBuilder();
		 jsonBuilder.append("[");
		 for (int i = 0; i < planList.size(); i++) {
		     Planning plan = planList.get(i);
		     jsonBuilder.append("{");
		     jsonBuilder.append("\"pName\": \"" + plan.getpName() + "\",");
		     jsonBuilder.append("\"distance\": \"" + plan.getDistance() + "\",");
		     jsonBuilder.append("\"lat\": [" + String.join(",", plan.getLat()) + "],");
		     jsonBuilder.append("\"lon\": [" + String.join(",", plan.getLon()) + "]");
		     jsonBuilder.append("}");
		     if (i < planList.size() - 1) {
		         jsonBuilder.append(",");
		     }
		 }
		 jsonBuilder.append("]");
		 
		 // Set response content type
		 response.setContentType("application/json");
		 
		 // Write JSON data to the response
		 PrintWriter out = response.getWriter();
		 out.print(jsonBuilder.toString());
		 out.flush();
		
		
	}

	private void addPlan(HttpServletRequest request, HttpServletResponse response) {
		String pName= request.getParameter("pName");
		String distance =request.getParameter("distance");
		String lat[] = request.getParameterValues("lat");
		String lng[] = request.getParameterValues("lng");
		String id = (String) request.getSession().getAttribute("login");
		System.out.println("데이터왓어요 " + pName + " "+ distance + " "+ lat + " "+ lng + " "+ id + " ");
		
		lat = lat[0].replaceAll("[\\[\\]]", "").split(","); // 문자열에서 대괄호를 제거하고 쉼표로 분할
		lng = lng[0].replaceAll("[\\[\\]]", "").split(","); // 문자열에서 대괄호를 제거하고 쉼표로 분할


		
		try {
			planImpl.addPlan(pName, distance, lat, lng, id);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		System.out.println("플랜넣기 성공!!");
		
	}
	

}
