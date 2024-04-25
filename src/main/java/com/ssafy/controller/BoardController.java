package com.ssafy.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import com.ssafy.dto.BoardDto;
import com.ssafy.dto.MemoDto;
import com.ssafy.model.BoardService;
import com.ssafy.model.BoardServiceImpl;
import com.ssafy.model.MemoService;
import com.ssafy.model.MemoServiceImpl;
import com.ssafy.dto.User;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/article")
public class BoardController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private BoardService boardService;
	private MemoService memoService;

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		boardService = BoardServiceImpl.getBoardService();
		memoService = MemoServiceImpl.getMemoService();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String action = request.getParameter("action");

		String path = "";
		if ("list".equals(action)) {
			path = list(request, response);
			forward(request, response, path);
		} else if ("view".equals(action)) {
			path = view(request, response);
			forward(request, response, path);
			//redirect(request, response, path);
		} else if ("mvwrite".equals(action)) {
			path = "/page/board/write.jsp";
			redirect(request, response, path);
		} else if ("write".equals(action)) {
			path = write(request, response);
			redirect(request, response, path);
		} else if ("mvmodify".equals(action)) {
			path = mvModify(request, response);
			forward(request, response, path);
		} else if ("modify".equals(action)) {
			path = modify(request, response);
			forward(request, response, path);
		} else if ("delete".equals(action)) {
			path = delete(request, response);
			redirect(request, response, path);
		} else if ("addcomment".equals(action)) {
			path = addComment(request, response);
			redirect(request, response, path);
		} else if ("deletecomment".equals(action)) {
			path = deleteComment(request, response);
			redirect(request, response, path);
		} else if ("updatecomment".equals(action)) {
			path = updateComment(request, response);
			redirect(request, response, path);
		} else {
			redirect(request, response, path);
		}
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		doGet(request, response);
	}

	private void forward(HttpServletRequest request, HttpServletResponse response, String path)
			throws ServletException, IOException {
		RequestDispatcher dispatcher = request.getRequestDispatcher(path);
		dispatcher.forward(request, response);
	}

	private void redirect(HttpServletRequest request, HttpServletResponse response, String path) throws IOException {
		response.sendRedirect(request.getContextPath() + path);
	}

	private String list(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		User memberDto = (User) session.getAttribute("userinfo");
		
		String num = "10";
		
		if (memberDto != null) {
			try {
				String pageNum = request.getParameter("page");
				//System.out.println(pageNum);
				if(pageNum == null || "null".equals(pageNum)) pageNum = "1";
				
				int endPage = boardService.getEndPage(num);
				List<BoardDto> list = boardService.listArticle(pageNum, num);
				request.setAttribute("articles", list);
				request.setAttribute("page", Integer.parseInt(pageNum));
				request.setAttribute("endpage", endPage + 1);
				return "/page/board/list.jsp";
			} catch (Exception e) {
				e.printStackTrace();
				return "/index.jsp";
			}
		} else
			return "/user/login.jsp";
	}

	private String view(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		User memberDto = (User) session.getAttribute("userinfo");
		System.out.println("view");
		if (memberDto != null) {
			int articleNo = Integer.parseInt(request.getParameter("articleno"));
			try {
				BoardDto boardDto = boardService.getArticle(articleNo);
				boardService.updateHit(articleNo);
				request.setAttribute("article", boardDto);
				request.setAttribute("id", memberDto.getId());
				request.setAttribute("memolist", memoService.getMemoList(articleNo));
				
				return "/page/board/view.jsp";
			} catch (Exception e) {
				e.printStackTrace();
				return "/index.jsp";
			}
		} else
			return "/user/login.jsp";
	}

	private String write(HttpServletRequest request, HttpServletResponse response) {
//		HttpSession session = request.getSession();
//		User memberDto = (User) session.getAttribute("userinfo");
//		if (memberDto != null) {
			BoardDto boardDto = new BoardDto();
//			boardDto.setUserId(memberDto.getUserId());
			boardDto.setUserId(request.getParameter("id"));
			boardDto.setSubject(request.getParameter("subject"));
			boardDto.setContent(request.getParameter("content"));
			try {
				boardService.writeArticle(boardDto);
				return "/article?action=list";
			} catch (Exception e) {
				e.printStackTrace();
				return "/index.jsp";
			}
//		} else
//			return "/user/login.jsp";
	}

	private String mvModify(HttpServletRequest request, HttpServletResponse response) {
		String articleno = request.getParameter("articleno");
		BoardDto board = null;
		try {
			board = boardService.getArticle(Integer.parseInt(articleno));
		} catch (Exception e) {
			e.printStackTrace();
			return "/index.jsp";
		}
		
		request.setAttribute("article", board);
		
		return "/page/board/modify.jsp";
	}

	private String modify(HttpServletRequest request, HttpServletResponse response) {
		System.out.println("modifyCall");
		String no = request.getParameter("articleno");
		String subject = request.getParameter("subject");
		String content = request.getParameter("content");
		
		BoardDto board = new BoardDto();
		board.setArticleNo(Integer.parseInt(no));
		board.setSubject(subject);
		board.setContent(content);
		
		try {
			boardService.modifyArticle(board);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return "/article?action=list";
	}

	private String delete(HttpServletRequest request, HttpServletResponse response) {
		String articleno = request.getParameter("articleno");
		
		try {
			boardService.deleteArticle(Integer.parseInt(articleno));
			//memoService.deleteArticleMemo(Integer.parseInt(articleno));	cascade로 sql에서 알아서 삭제하므로 필요없음
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return "/article?action=list";
	}
	
	private String addComment(HttpServletRequest request, HttpServletResponse response) {
		int articleNo = Integer.parseInt(request.getParameter("num"));
		int parentId = Integer.parseInt(request.getParameter("id"));
		String comment = request.getParameter("comment");
		String user_id = request.getParameter("user_id");
				
		MemoDto m = new MemoDto();
		m.setComment(comment);
		m.setUser_id(user_id);
		
		try {
			memoService.addMemo(articleNo, parentId, m);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return "/article?action=view&articleno=" + articleNo;
	}
	
	private String deleteComment(HttpServletRequest request, HttpServletResponse response) {
		String articleNo = request.getParameter("articleno");
		String id = request.getParameter("id");
		
		try {
			memoService.deleteMemo(Integer.parseInt(id));
		} catch (NumberFormatException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return "/article?action=view&articleno=" + articleNo;
	}

	private String updateComment(HttpServletRequest request, HttpServletResponse response) {
		String articleNo = request.getParameter("num");
		String memoId = request.getParameter("id");
		String comment = request.getParameter("comment");
		
		try {
			memoService.updateMemo(Integer.parseInt(memoId), comment);
		} catch (NumberFormatException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return "/article?action=view&articleno=" + articleNo;
	}
}
