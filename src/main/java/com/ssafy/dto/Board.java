package com.ssafy.dto;

import java.sql.ResultSet;
import java.sql.SQLException;

//import jakarta.security.auth.message.callback.PrivateKeyCallback.Request;
import jakarta.servlet.http.HttpServletRequest;

public class Board {
	private String id; //userId 외래키
	private String articleNum; //게시물 번호
	private String title;
	private String content;
	private String viewcnt;
	
	
	public Board() {};
	
	public Board(HttpServletRequest rq) {
		this.id = rq.getParameter("id");
		this.articleNum = rq.getParameter("articleNum");
		this.title = rq.getParameter("title");
		this.content = rq.getParameter("content");
		this.viewcnt = rq.getParameter("viewcnt");
	}

	public Board(ResultSet rs) throws SQLException {
		this.id = rs.getString("id");
		this.articleNum = rs.getString("articleNum");
		this.title = rs.getString("title");
		this.content = rs.getString("content");
		this.viewcnt = rs.getString("viewcnt");
	}
	
	public Board(String id, String articleNum, String title, String content, String viewcnt) {
		super();
		this.id = id;
		this.articleNum = articleNum;
		this.title = title;
		this.content = content;
		this.viewcnt = viewcnt;
	}

	public String getArticleNum() {
		return articleNum;
	}

	public void setArticleNum(String articleNum) {
		this.articleNum = articleNum;
	}


	public String getViewcnt() {
		return viewcnt;
	}

	public void setViewcnt(String viewcnt) {
		this.viewcnt = viewcnt;
	}


	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	

}
