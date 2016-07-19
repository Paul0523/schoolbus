package com.schoolbus.service.impl;

import java.util.ArrayList;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.schoolbus.dao.LineInfoDao;
import com.schoolbus.dao.LineStationsDao;
import com.schoolbus.entity.LineInfo;
import com.schoolbus.entity.Station;
import com.schoolbus.service.LineInfoService;

@Service("lineInfoService")
public class LineInfoServiceImpl implements LineInfoService{
	private Log logger = LogFactory.getLog(LineInfoServiceImpl.class);
	@Resource
	private LineStationsDao lineStationsDao;
	@Resource
	private LineInfoDao lineInfoDao;
	private String resultStr;
	private Gson gson = new Gson();

	@Override
	public String getLineInfo(LineInfo lineInfo) {
		int lineId = lineInfo.getLineId();
		int orientation = lineInfo.getOrientation();
		int totalCount = lineInfoDao.selectTotalCount(lineInfo);
		ArrayList<LineInfo> lineInfos;
		if(totalCount == 0){
			lineInfos = new ArrayList<LineInfo>();
			ArrayList<Station> stations = lineStationsDao.selectLineStations(lineId, orientation);
			int priority = 1;
			for(Station station : stations){
				LineInfo ln = new LineInfo();
				ln.setLineId(lineId);
				ln.setOrientation(orientation);
				ln.setLongitude(station.getLongitude());
				ln.setLatitude(station.getLatitude());
				ln.setPriority(priority);
				lineInfoDao.saveLineInfo(ln);
				lineInfos.add(ln);
				priority++;
			}
		}else{
			lineInfos = lineInfoDao.selectLineInfo(lineInfo);
		}
		resultStr = gson.toJson(lineInfos);
		return resultStr;
	}

	@Override
	public String saveLineInfo(LineInfo lineInfo) {
		int lineId = lineInfo.getLineId();
		int orientation = lineInfo.getOrientation();
		int priority = lineInfo.getPriority();
		int maxPriority = lineInfoDao.selectMaxPriority(lineId,orientation);
		logger.debug(maxPriority+"********"+priority+"********");
		while(priority <= maxPriority){
			LineInfo ln = lineInfoDao.selectByPriority(lineId, orientation, maxPriority);
			logger.debug(ln.getLongitude()+"*******"+priority+"*******"+ln.getId());
			ln.setPriority(maxPriority+1);
			lineInfoDao.updateLineInfo(ln);
			maxPriority--;
		}
		lineInfoDao.saveLineInfo(lineInfo);
		return "{\"code\":1,\"msg\":\"修改成功\"}";
	}

	@Override
	public String updateLineInfoGPSById(LineInfo lineInfo) {
		int id = lineInfo.getId();
		String longitude = lineInfo.getLongitude();
		String latitude = lineInfo.getLatitude();
		LineInfo ln = lineInfoDao.selectLineInfoById(id);
		ln.setLongitude(longitude);
		ln.setLatitude(latitude);
		lineInfoDao.updateLineInfo(ln);
		return "{\"code\":1,\"msg\":\"操作成功\"}";
	}

	@Override
	public String removeLineInfo(LineInfo lineInfo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String removeLineInfoById(LineInfo lineInfo) {
		int id = lineInfo.getId();
		LineInfo reLn = lineInfoDao.selectLineInfoById(id);
		int lineId = reLn.getLineId();
		int orientation = reLn.getOrientation();
		int priority = reLn.getPriority();
		int maxPriority = lineInfoDao.selectMaxPriority(lineId, orientation);
		lineInfoDao.removeLineInfo(id);
		while(priority < maxPriority){
			LineInfo ln = lineInfoDao.selectByPriority(lineId, orientation, priority+1);
			ln.setPriority(priority);
			lineInfoDao.updateLineInfo(ln);
			priority++;
		}
		return "{\"code\":1,\"msg\":\"操作成功\"}";
	}

}
