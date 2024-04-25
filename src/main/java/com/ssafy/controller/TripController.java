package com.ssafy.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.dto.Location;
import com.ssafy.dto.User;
import com.ssafy.model.LocationService;
import com.ssafy.model.LocationServiceImpl;
import com.ssafy.model.MemoServiceImpl;
import com.ssafy.model.UserService;
import com.ssafy.model.UserServiceImpl;



@WebServlet(urlPatterns = {"/trip"})
public class TripController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	//장소관련 dao => locDao
	private LocationService locDao; 
	private UserService userDao;
	
    public TripController() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		process(request,response);
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		process(request,response);
	}
	
	
	@Override
	public void init() throws ServletException {
		super.init();
		locDao = new LocationServiceImpl();
		userDao = new UserServiceImpl();
	}
	
	
	private void process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String url = "";
		response.setContentType("text/html; charset=UTF-8");
		String action = request.getParameter("action");
		System.out.println("action :" + action);
		HttpSession session = request.getSession();
		
		try {
			if(action != null) {
				if(action.equals("selectcity")) {
					selectcity(request,response);
					return;
				}else if(action.equals("login")) {
					url = login(request,response);
				}else if(action.equals("signup")) {
					url = signup(request, response);
				}else if(action.equals("logout")) {
					url = logout(request, response);
				}else if(action.equals("init")) {
					url = "/index.jsp";
				}else if(action.equals("recommend")) {
					String contentId = request.getParameter("id");
					System.out.println(contentId);
					recommend(contentId);
					return;
				}else if(action.equals("showmyplace")) {
//					String id = request.getParameter("id");
					
					String id = (String)session.getAttribute("login");
					System.out.println(id);
					if(id == null) {
						System.out.println("Null id");
						alert(response, "로그인 후 이용해 주세요.");
						return;
					}
					getMyPlace(id, request, response);
					return;
				}else if(action.equals("addmyplace")) {
					addMyPlace(request, response);
					return;
				}else if(action.equals("removemyplace")) {
					
				}else if(action.equals("profileform")) {
					url = profileForm(request, response);
				}else if(action.equals("modify")) {
					url = modify(request, response);
				}else if(action.contains("deleteuser")) {
					url = deleteUser(request, response);
					System.out.println(url);
				}else if(action.contains("findpass")) {
					findPass(request, response);
					return;
				}
					
			}

		}catch(Exception e) {
			System.out.println(e);
			request.setAttribute("msg", e.toString());
			
			//error창
			url = "error/error.jsp";
		}
		if(url == null) return ;
		
		if(url.startsWith("redirect")) {
			url = url.substring(url.indexOf(":")+1);
			response.sendRedirect(request.getContextPath()+ url);  //리다이렉트 할때는 브라우저에서 실행하기 때문에 컨텍스트 패스를 추가해주어야 한다.
			System.out.println(url);
		}else{
			System.out.println(url);
			request.getRequestDispatcher(url).forward(request, response);
		}
		
		
	}
	private void findPass(HttpServletRequest request, HttpServletResponse response) {
		String id = request.getParameter("id");
		String email = request.getParameter("email");
		System.out.println("findpass pram : " + id + email);
	    try {
	        String pass = userDao.findPass(id, email);
	        System.out.println("findpass password: " + pass);
	        
	        // 응답으로 비밀번호를 JSON 형식으로 전송
	        response.setContentType("application/json");
	        response.setCharacterEncoding("UTF-8");
	        PrintWriter out = response.getWriter();
//	        if(pass == null) {
//	        	out.print("일치하는 정보가 없습니다!!!!");
//	        	out.flush();
//	        }else {
		        out.print("{\"password\": \"" + pass + "\"}");
		        out.flush();
//	        }

	    } catch (Exception e) {
	        e.printStackTrace();
	    }
		return;
		
		
	}

	private String deleteUser(HttpServletRequest request, HttpServletResponse response) {
		String id = request.getParameter("id");
		String pw = request.getParameter("pw");
		System.out.println("delete : " + id + pw);
		try {
			userDao.deleteUser(id, pw);
			MemoServiceImpl.getMemoService().setUnknown(id);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		HttpSession session = request.getSession();
		session.invalidate();
		
		return "redirect:/trip?action=init";
	}

	private String modify(HttpServletRequest request, HttpServletResponse response) {
		String id = request.getParameter("id");
		String pw = request.getParameter("pw");
		String email = request.getParameter("email");
		System.out.println(id+pw+email);
		try {
			userDao.modifyUser(id, pw, email);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "redirect:/trip?action=init";
	}

	private String profileForm(HttpServletRequest request, HttpServletResponse response) throws Exception {
		if(request.getSession().getAttribute("login") == null)return "redirect:/product/login.jsp";
		String id = (String) request.getSession().getAttribute("login");
		
		System.out.println("id :" +id);
		User user = userDao.userById(id);
		request.setAttribute("u", user);
		
		
		return "/page/profile.jsp";
	}
	
	private String signup(HttpServletRequest request, HttpServletResponse response) throws Exception{
		//1 파라미터처리
		String id = request.getParameter("id");
		//User user = userDao.userById(id);
		//아이디 가진 유저가 있으면?
		if("Unknown".equals(id) || userDao.userExist(id)) {
			response.getWriter().write("IdDupl");
			return null;
		}
		String pw = request.getParameter("password");
		String email = request.getParameter("email");
		String name = request.getParameter("name");
		System.out.println(id+pw+email);
		try {
			userDao.addUser(id, pw, email, name);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("!!!");
			e.printStackTrace();
		}
		return "redirect:/trip?action=init";
	}

	private String logout(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		session.invalidate();
			
		return "redirect:/trip?action=init";
	}
	private String login(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String id = request.getParameter("id");
		String pw = request.getParameter("pw");
		System.out.println(id +":"+ pw);
		boolean login = false;
		User user = null;
		
		try {
			login = userDao.verifyUser(id, pw);
			if(login) user = userDao.userById(id);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(login);
		if(login && user != null) {
			HttpSession session = request.getSession();
			session.setAttribute("login", id);
			session.setAttribute("userinfo", user);
			return "redirect:/trip?action=init";
		}else {
			response.getWriter().write("false");;
			return null;
		}
		
		
	}

	private void selectcity(HttpServletRequest request, HttpServletResponse response) throws Exception {
	    // 클라이언트에서 보낸 JSON 데이터를 파싱
	    BufferedReader reader = request.getReader();
	    StringBuilder stringBuilder = new StringBuilder();
	    String line;
	    while ((line = reader.readLine()) != null) {
	        stringBuilder.append(line);
	    }
	    String jsonStr = stringBuilder.toString();

	    // 파싱한 JSON 데이터를 Jackson ObjectMapper를 사용하여 자바 객체로 변환
	    ObjectMapper mapper = new ObjectMapper();
	    JsonNode jsonData = mapper.readTree(jsonStr);

	    // 자바 객체에서 필요한 데이터를 추출
	    String cities = jsonData.get("cityId").asText();
	    String subcity = jsonData.get("subCityId").asText();
	    String keywords = jsonData.get("keywordId").asText();

		System.out.println(cities + " " +subcity + " "+keywords);
		List<Location> list = locDao.getLocationList(cities, subcity, keywords);
		
	    // JSON으로 변환
	    mapper = new ObjectMapper();
	    String jsonList = mapper.writeValueAsString(list);

	    // 클라이언트에게 JSON 응답 전송
	    response.setContentType("application/json");
	    response.setCharacterEncoding("UTF-8");
	    response.getWriter().write(jsonList);
	}
	
	private void getMyPlace(String id, HttpServletRequest request, HttpServletResponse response) throws Exception {
		List<Location> list = locDao.getMyPlaceList(id);
		
		ObjectMapper mapper = new ObjectMapper();
		String json = mapper.writeValueAsString(list);
		
		response.setContentType("application/json");
	    response.setCharacterEncoding("UTF-8");
	    response.getWriter().write(json);
	}
	
	private void addMyPlace(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// 클라이언트에서 보낸 JSON 데이터를 파싱
	    BufferedReader reader = request.getReader();
	    StringBuilder stringBuilder = new StringBuilder();
	    String line;
	    while ((line = reader.readLine()) != null) {
	        stringBuilder.append(line);
	    }
	    String jsonStr = stringBuilder.toString();

	    // 파싱한 JSON 데이터를 Jackson ObjectMapper를 사용하여 자바 객체로 변환
	    ObjectMapper mapper = new ObjectMapper();
	    JsonNode jsonData = mapper.readTree(jsonStr);

	    // 자바 객체에서 필요한 데이터를 추출
	    Location loc = new Location();
	    loc.setTitle(jsonData.get("title").asText());
	    loc.setAddr1(jsonData.get("addr1").asText());
	    loc.setTel(jsonData.get("tel").asText());
	    loc.setLongitude(jsonData.get("longitude").asText());
	    loc.setLatitude(jsonData.get("latitude").asText());
	    loc.setAddr2(jsonData.get("addr2").asText());
//	    System.out.println(id + " " + title);
	    
	    locDao.addMyPlace(loc);
	    
	}
	
	
	private void recommend(String id) {
		//uuid형식이라면 탈출
		if(id.contains("-")) return;
		try {
			locDao.recommendLocation(id);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//알림창만 띄우기
	public static void alert(HttpServletResponse response, String msg) {
	    try {
			response.setContentType("text/html; charset=utf-8");
			PrintWriter w = response.getWriter();
			w.write("<script>alert('"+msg+"');</script>");
			w.flush();
			w.close();
	    } catch(Exception e) {
			e.printStackTrace();
	    }
	}
}
