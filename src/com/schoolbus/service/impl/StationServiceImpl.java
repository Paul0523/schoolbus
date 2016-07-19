package com.schoolbus.service.impl;

import java.util.ArrayList;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.schoolbus.dao.Page;
import com.schoolbus.dao.StationDao;
import com.schoolbus.entity.Station;
import com.schoolbus.service.StationService;

@Service("stationService")
public class StationServiceImpl implements StationService{
	private Log logger = LogFactory.getLog(StationServiceImpl.class);
	@Resource
	private StationDao stationDao;
	private String resultStr;
	private Gson gson = new Gson();
	@Override
	public String getStations(Station station,int page,int rows) {
		int start = (page-1)*rows;
		int size = rows;
		Page<Station> list = stationDao.selectStationByPage(station,start,size);
		if(list != null){
			if(list.getList() != null){
				ArrayList<Station> stations = list.getList();
				logger.debug(stations.get(0).getName());
				String str = gson.toJson(stations);
				resultStr = "{\"total\":"+list.getTotalCount()+",\"rows\":"+str+"}";
			}
		}else{
			resultStr = "{\"total\":"+0+",\"rows\":[]}";
		}
		return resultStr;
	}

	@Override
	public String updateStation(Station station) {
		Station sta = stationDao.selectStationById(station.getId());
		if(sta == null){
			station.setState(0);
			stationDao.saveStation(station);
		}else{	
			sta.setLongitude(station.getLongitude());
			sta.setLatitude(station.getLatitude());
			stationDao.updateStation(sta);
		}
		return null;
	}

	@Override
	public String removeStation(Station station) {
		int i = stationDao.deleteStation(station.getId());
		if(i == 1){
			resultStr = "{\"success\":true}";
		}else{
			resultStr = "{\"success\":false}";
		}
		return resultStr;
	}

}
