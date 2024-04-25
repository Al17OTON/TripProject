package com.ssafy.model;

import java.sql.SQLException;
import java.util.List;

import com.ssafy.dto.MemoDto;

public class MemoServiceImpl implements MemoService {
	
	private static MemoService instance = null;
	private MemoDao memoDao = null;
	
	private MemoServiceImpl() {
		memoDao = MemoDaoImpl.getMemoDao();
	}
	
	public static MemoService getMemoService() {
		if(instance == null) instance = new MemoServiceImpl();
		return instance;
	}
	
	
	@Override
	public List<MemoDto> getMemoList(int articleNo) throws SQLException {
		return memoDao.getMemoList(articleNo);
	}

	@Override
	public void addMemo(int articleNo, int parentNo, MemoDto memo) throws SQLException {
		memoDao.addMemo(articleNo, parentNo, memo);
	}

	@Override
	public void deleteMemo(int memoId) throws SQLException {
		memoDao.deleteMemo(memoId);
	}

	@Override
	public MemoDto getMemo(int memoId) throws SQLException {
		return memoDao.getMemo(memoId);
	}

	@Override
	public void deleteArticleMemo(int articleNo) throws SQLException {
		memoDao.deleteArticleMemo(articleNo);
	}

	@Override
	public void setUnknown(String userId) throws SQLException {
		memoDao.setUnknown(userId);
	}

	@Override
	public void updateMemo(int memoId, String comment) throws SQLException {
		memoDao.updateMemo(memoId, comment);
	}

}
