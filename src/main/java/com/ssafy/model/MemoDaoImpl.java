package com.ssafy.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.ssafy.dto.BoardDto;
import com.ssafy.dto.MemoDto;
import com.ssafy.util.DBUtil;

public class MemoDaoImpl implements MemoDao {
	
	private static MemoDao instance = null;
	private DBUtil dbUtil = null;
	
	private MemoDaoImpl() {
		dbUtil = DBUtil.getInstance();
	}
	
	public static MemoDao getMemoDao() {
		if(instance == null) instance = new MemoDaoImpl();
		return instance;
	}
	
	@Override
	public List<MemoDto> getMemoList(int articleNo) throws SQLException {
		List<MemoDto> list = new ArrayList<>();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = dbUtil.getConnection();
			StringBuilder sql = new StringBuilder();
			sql.append("select * from memo where article_no=?");
			pstmt = conn.prepareStatement(sql.toString());
			
			pstmt.setInt(1, articleNo);
			
			rs = pstmt.executeQuery();
			while(rs.next()) {
				MemoDto memo = new MemoDto();
				memo.setMemo_no(rs.getInt("memo_no"));
				memo.setArticle_no(rs.getInt("article_no"));
				memo.setUser_id(rs.getString("user_id"));
				memo.setComment(rs.getString("comment"));
				memo.setRef(rs.getInt("ref"));
				memo.setStep(rs.getInt("step"));
				memo.setDepth(rs.getInt("depth"));
				memo.setMemo_time(rs.getString("memo_time"));
				list.add(memo);
			}
		} finally {
			dbUtil.close(rs, pstmt, conn);
		}
		
		list.sort((o1, o2) -> Integer.compare(o1.getStep(), o2.getStep()));
		
		return list;
	}

	@Override
	public void addMemo(int articleNo, int parentNo, MemoDto memo) throws SQLException {
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		MemoDto parent = null;
		System.out.println(parentNo);
		if(parentNo != -1 ) parent = getMemo(parentNo);
		if(parent == null) {
			parent = new MemoDto();
			parent.setStep(-1);
			parent.setDepth(-1);
		}
		
		//만약 대댓글이 너무 깊어진다면 예외처리
		if(parent.getDepth() >= 6) throw new SQLException();
			
		try {
			
			updateOrder(articleNo, parent);
			
			conn = dbUtil.getConnection();
			StringBuilder sql = new StringBuilder();
			sql.append("insert into memo (article_no, user_id, comment, ref, step, depth) \n");
			sql.append("values (?, ?, ?, ?, ?, ?) ;");
	
			pstmt = conn.prepareStatement(sql.toString());
			
			pstmt.setInt(1, articleNo);
			pstmt.setString(2, memo.getUser_id());
			pstmt.setString(3, memo.getComment());
			pstmt.setInt(4, articleNo);
			pstmt.setInt(5, parent.getStep() + 1);
			pstmt.setInt(6, parent.getDepth() + 1);
			
			pstmt.executeUpdate();
			
		} finally {
			dbUtil.close(pstmt, conn);
		}
		
	}

	@Override
	public void deleteMemo(int memoId) throws SQLException {
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = dbUtil.getConnection();
			StringBuilder sql = new StringBuilder();
			sql.append("update memo set comment=?, user_id=? where memo_no=?");
			pstmt = conn.prepareStatement(sql.toString());
			
			pstmt.setString(1, "삭제된 댓글입니다.");
			pstmt.setString(2, "Unknown");
			pstmt.setInt(3, memoId);
			pstmt.executeUpdate();
			
		} finally {
			dbUtil.close(pstmt, conn);
		}
	}

	@Override
	public MemoDto getMemo(int memoId) throws SQLException {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		MemoDto memo = null;
		try {
			conn = dbUtil.getConnection();
			StringBuilder sql = new StringBuilder();
			sql.append("select * from memo where memo_no=?");
			pstmt = conn.prepareStatement(sql.toString());
			
			pstmt.setInt(1, memoId);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				memo = new MemoDto();
				memo.setArticle_no(rs.getInt("article_no"));
				memo.setUser_id(rs.getString("user_id"));
				memo.setComment(rs.getString("comment"));
				memo.setRef(rs.getInt("ref"));
				memo.setStep(rs.getInt("step"));
				memo.setDepth(rs.getInt("depth"));
				memo.setMemo_time(rs.getString("memo_time"));
			}
		} finally {
			dbUtil.close(rs, pstmt, conn);
		}
		
		return memo;
	}

	@Override
	public void updateOrder(int articleNo, MemoDto parent) throws SQLException {
		Connection conn = null;
		PreparedStatement pstmt = null;
			
		try {
			conn = dbUtil.getConnection();
			StringBuilder sql = new StringBuilder();
			sql.append("update memo set step = step + 1 where step >= ? and ref = ? ; ");
			pstmt = conn.prepareStatement(sql.toString());
			
			pstmt.setInt(1, parent.getStep() + 1);
			pstmt.setInt(2, articleNo);
			
			pstmt.executeUpdate();
			
		} finally {
			dbUtil.close(pstmt, conn);
		}
		
	}

	@Override
	public void deleteArticleMemo(int articleNo) throws SQLException {
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = dbUtil.getConnection();
			StringBuilder sql = new StringBuilder();
			sql.append("delete from memo where ref=?");
			pstmt = conn.prepareStatement(sql.toString());
			
			pstmt.setInt(1, articleNo);
			
			pstmt.executeUpdate();
			
		} finally {
			dbUtil.close(pstmt, conn);
		}
	}

	@Override
	public void setUnknown(String userId) throws SQLException {
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = dbUtil.getConnection();
			StringBuilder sql = new StringBuilder();
			sql.append("update memo set user_id=? where user_id=?");
			pstmt = conn.prepareStatement(sql.toString());
			
			pstmt.setString(1, "Unknown");
			pstmt.setString(2, userId);
			pstmt.executeUpdate();
			
		} finally {
			dbUtil.close(pstmt, conn);
		}
	}

	@Override
	public void updateMemo(int memoId, String comment) throws SQLException {
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = dbUtil.getConnection();
			StringBuilder sql = new StringBuilder();
			sql.append("update memo set comment=? where memo_no=?");
			pstmt = conn.prepareStatement(sql.toString());
			
			pstmt.setString(1, comment);
			pstmt.setInt(2, memoId);
			pstmt.executeUpdate();
			
		} finally {
			dbUtil.close(pstmt, conn);
		}
	}

}
