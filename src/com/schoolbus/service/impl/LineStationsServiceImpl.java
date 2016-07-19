package com.schoolbus.service.impl;

import java.util.ArrayList;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.google.gson.Gson;
import com.schoolbus.dao.LineStationsDao;
import com.schoolbus.dao.Page;
import com.schoolbus.dao.StationDao;
import com.schoolbus.entity.Line;
import com.schoolbus.entity.LineStations;
import com.schoolbus.entity.Station;
import com.schoolbus.service.LineStationsService;

@Service("lineStationsService")
public class LineStationsServiceImpl implements LineStationsService{
	private Log logger = LogFactory.getLog(LineStationsServiceImpl.class);
	@Resource
	private LineStationsDao lineStationsDao;
	@Resource
	private StationDao stationDao;
	private String resultStr;
	private Gson gson = new Gson();
	
	@Override
	public String getLineStations(Line line,int orientation,int page,int rows) {
		int start = (page-1)*rows;
		int size = rows;
		Page<Station> list = lineStationsDao.selectLineStationsByPage(line,orientation,start,size);
		if(list != null){
			if(list.getList() != null){
				ArrayList<Station> stations = list.getList();
				String str = gson.toJson(stations);
				resultStr = "{\"total\":"+list.getTotalCount()+",\"rows\":"+str+"}";
			}
		}else{
			resultStr = "{\"total\":"+0+",\"rows\":[]}";
		}
		return resultStr;
	}

	@Override
	public String updateLineStations(int lineId,int newStationId, int curStationId,int orientation) {
		LineStations checkLs = lineStationsDao.selectLineStationsById(lineId, newStationId,orientation);
		if(checkLs != null){
			return "{'success':false,'msg':'已有相同站点，禁止添加！'}";
		}
		int maxPriority = lineStationsDao.selectMaxPriority(lineId,orientation);
		int newPriority = maxPriority+1;
		if(curStationId != -1){
			LineStations curLs = lineStationsDao.selectLineStationsById(lineId, curStationId,orientation);
			int curPriority = curLs.getPriority();
			newPriority = curPriority;
			while(curPriority <= maxPriority){
				LineStations ls = lineStationsDao.selectLineStationsByPriority(lineId,curPriority,orientation);
				logger.debug(ls.getId()+"*******");
				ls.setPriority(curPriority+1);
				lineStationsDao.updateLineStations(ls);
				curPriority++;
			}
		}
		Station station = stationDao.selectStationById(newStationId);
		station.setState(1);
		stationDao.updateStation(station);
		LineStations lineStations = new LineStations();
		lineStations.setLineId(lineId);
		lineStations.setStationId(newStationId);
		lineStations.setPriority(newPriority);
		lineStations.setOrientation(orientation);
		lineStationsDao.saveLineStation(lineStations);
		return "{'success':true,'msg':'添加成功'}";
	}

	@Override
	public String removeLineStations(int lineId,int staId,int orientation) {
		logger.debug(lineId+"******"+staId+"*****"+orientation+"******");
		LineStations lineStations = lineStationsDao.selectLineStationsById(lineId, staId,orientation);
		int priority = lineStations.getPriority();
		int maxPriority = lineStationsDao.selectMaxPriority(lineId,orientation);
		logger.debug("priority:"+priority+"maxPriority:"+maxPriority);
		while(priority < maxPriority){
			LineStations nextLs = lineStationsDao.selectLineStationsByPriority(lineId, priority+1,orientation);
			nextLs.setPriority(priority);
			lineStationsDao.updateLineStations(nextLs);
			priority++;
			logger.debug("循环："+priority);
		}
		int num = lineStationsDao.selectStationNumByStaId(staId);
		if(num == 1){
			Station station = stationDao.selectStationById(staId);
			station.setState(0);
			stationDao.updateStation(station);
		}
		lineStationsDao.removeLineStations(lineId,staId,orientation);
		return "{\"success\":true}";
	}

	@Override
	public String getLineStationsForList(Line line, int orientation) {
		int lineId = line.getId();
		ArrayList<Station> stations = lineStationsDao.selectLineStations(lineId, orientation);
		resultStr = gson.toJson(stations);
		return resultStr;
	}

}
