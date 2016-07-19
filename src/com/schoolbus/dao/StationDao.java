package com.schoolbus.dao;

import java.util.ArrayList;

import com.schoolbus.entity.Station;

public interface StationDao {
	public Station selectStationById(int id);
	public Page<Station> selectStationByPage(Station station,int start,int size);
	public ArrayList<Station> selectStations(Station station);
	public void saveStation(Station station);
	public void updateStation(Station station);
	public int deleteStation(int id);
}
