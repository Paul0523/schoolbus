package com.schoolbus.dao;

import java.util.ArrayList;

import com.schoolbus.entity.Line;

public interface LineDao {
	public ArrayList<Line> selectLine(Line line);
	public ArrayList<Line> selectLinesByStationId(int stationId);
	public Line selectLineById(int id);
	public void saveLine(Line line);
	public void updateLine(Line line);
	public int deleteLine(int id);
}
