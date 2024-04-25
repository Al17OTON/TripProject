package com.ssafy.model;

import java.sql.SQLException;
import java.util.List;

import com.ssafy.dto.BoardDto;

public interface BoardDao {

	void writeArticle(BoardDto boardDto) throws SQLException;
	List<BoardDto> listArticle(String page, String num) throws SQLException;
	// List<BoardDto> listArticle(Map<String, Object> param) throws SQLException;
	// int getTotalArticleCount(Map<String, Object> param) throws SQLException;
	BoardDto getArticle(int articleNo) throws SQLException;
	void updateHit(int articleNo) throws SQLException;
	
	void modifyArticle(BoardDto boardDto) throws SQLException;
	void deleteArticle(int articleNo) throws SQLException;
	int getEndPage(String num) throws SQLException;
	
}
