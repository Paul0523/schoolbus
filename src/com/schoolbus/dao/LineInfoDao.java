package com.schoolbus.dao;

import java.util.ArrayList;

import com.schoolbus.entity.LineInfo;

public interface LineInfoDao {
	public ArrayList<LineInfo> selectLineInfo(LineInfo lineInfo);
	public void updateLineInfo(LineInfo lineInfo);
	public void removeLineInfo(int id);
	public LineInfo selectLineInfoById(int id);
	public void saveLineInfo(LineInfo lineInfo);
	public int selectTotalCount(LineInfo lineInfo);
	public int selectMaxPriority(int lineId,int orientation);
	public LineInfo selectByPriority(int lineId,int orientation,int priority);
}
