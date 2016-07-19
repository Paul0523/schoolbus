package com.schoolbus.dao;

import java.util.ArrayList;

import com.schoolbus.entity.Line;
import com.schoolbus.entity.LineStations;
import com.schoolbus.entity.Station;

public interface LineStationsDao {
	public Page<Station> selectLineStationsByPage(Line line,int orientation,int start,int size);
	public int selectMaxPriority(int lineId,int orientation);
	public void saveLineStation(LineStations lineStations);
	public LineStations selectLineStationsById(int lineId,int curId,int orientation);
	public LineStations selectLineStationsByPriority(int lineId,int priority,int orientation);
	public void updateLineStations(LineStations lineStations);
	public int removeLineStations(int lineId,int staId,int orientation);
	public ArrayList<Station> selectLineStations(int lineId,int orientation);
	public int selectStationNumByStaId(int stationId);
}
