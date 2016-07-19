package com.schoolbus.service;

import com.schoolbus.entity.Line;

public interface LineStationsService {
	public String getLineStations(Line line,int orientation,int page,int rows);
	public String updateLineStations(int lineId,int newStationId,int curStationId,int orientation);
	public String removeLineStations(int lineId,int staId,int orientation);
	public String getLineStationsForList(Line line,int orientation);
}
