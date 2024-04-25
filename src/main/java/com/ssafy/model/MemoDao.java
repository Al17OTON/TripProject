package com.ssafy.model;

import java.sql.SQLException;
import java.util.List;

import com.ssafy.dto.MemoDto;

public interface MemoDao {
	public List<MemoDto> getMemoList(int articleNo) throws SQLException;
	public void addMemo(int articleNo, int parentNo, MemoDto memo) throws SQLException;
	public void deleteMemo(int memoId) throws SQLException;
	public MemoDto getMemo(int memoId) throws SQLException;
	public void updateOrder(int articleNo, MemoDto parent) throws SQLException;
	public void deleteArticleMemo(int articleNo) throws SQLException;
	public void setUnknown(String userId) throws SQLException;
	public void updateMemo(int memoId, String comment) throws SQLException;
}
