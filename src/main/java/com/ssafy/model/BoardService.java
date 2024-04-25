package com.ssafy.model;

import java.sql.SQLException;
import java.util.List;

import com.ssafy.dto.BoardDto;

public interface BoardService {

	void writeArticle(BoardDto boardDto) throws Exception;
	List<BoardDto> listArticle(String page, String num) throws Exception;
	// List<BoardDto> listArticle(Map<String, Object> map) throws Exception;
	// PageNavigation makePageNavigation(Map<String, Object> map) throws Exception;
	BoardDto getArticle(int articleNo) throws Exception;
	void updateHit(int articleNo) throws Exception;
	
	void modifyArticle(BoardDto boardDto) throws Exception;
	void deleteArticle(int articleNo) throws Exception;
	int getEndPage(String num) throws SQLException;
	
}
