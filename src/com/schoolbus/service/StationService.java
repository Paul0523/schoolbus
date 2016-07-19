package com.schoolbus.service;

import com.schoolbus.entity.Station;

public interface StationService {
	public String getStations(Station station,int page,int rows);
	public String updateStation(Station station);
	public String removeStation(Station station);
}
