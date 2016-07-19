package com.schoolbus.service.impl;

import java.util.ArrayList;
import java.util.Date;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.google.gson.Gson;
import com.schoolbus.dao.BusDao;
import com.schoolbus.dao.LineDao;
import com.schoolbus.dao.LineStationsDao;
import com.schoolbus.dao.StationDao;
import com.schoolbus.dao.UserDao;
import com.schoolbus.entity.Bus;
import com.schoolbus.entity.Line;
import com.schoolbus.entity.LineStations;
import com.schoolbus.entity.Station;
import com.schoolbus.entity.TreeJson;
import com.schoolbus.entity.User;
import com.schoolbus.service.ManagerService;

/**
 * Created by Paul on 2016/3/2.
 */
@Service("managerService")
public class ManagerServiceImpl implements ManagerService {
	private Log logger = LogFactory.getLog(ManagerServiceImpl.class);
	private Gson gson = new Gson();
	private String resultStr = "{\"total\":"+0+",\"rows\":[]}";
	@Resource
	private UserDao userDao;
	@Resource
	private BusDao busDao;
	@Resource
	private LineDao lineDao;
	@Resource
	private StationDao stationDao;
	@Resource
	private LineStationsDao lineStationsDao;
	@Override
	public String sendGPS() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getNextStationNum() {
		// TODO Auto-generated method stub
		return null;
	}

	/*@Override
	public String getUsers(User user) {
		ArrayList<User> list = userDao.selectUser(user);
		if(list != null){
			logger.debug(list.get(0).getAccount());
			String str = gson.toJson(list);
			resultStr = "{\"total\":"+list.size()+",\"rows\":"+str+"}";
		}else{
			resultStr = "{\"total\":"+0+",\"rows\":[]}";
		}
		return resultStr;
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED)
	public String updateUser(User user) {
		User u = userDao.selectByAccount(user.getAccount());
		if(u == null){
			user.setUserType(1);
			user.setCreateTime(new Date());
			userDao.saveUser(user);
		}else{	
			u.setUsername(user.getUsername());
			u.setEmail(user.getEmail());
			u.setPhone(user.getPhone());
			if(!user.getPassword().equals("")){
				u.setPassword(user.getPassword());
			}
			userDao.updateUser(u);
		}
		
		return null;
	}

	@Override
	public String removeUser(User user) {
		int i = userDao.deleteUser(user.getId());
		if(i == 1){
			resultStr = "{\"success\":true}";
		}else{
			resultStr = "{\"success\":false}";
		}
		return resultStr;
	}
	
	public String getBuses(Bus bus){
		ArrayList<Bus> list = busDao.selectBus(bus);
		if(list != null){
			logger.debug(list.get(0).getName());
			String str = gson.toJson(list);
			resultStr = "{\"total\":"+list.size()+",\"rows\":"+str+"}";
		}else{
			resultStr = "{\"total\":"+0+",\"rows\":[]}";
		}
		return resultStr;
	}
	
	public String updateBus(Bus bus){
		Bus b = busDao.selectBusById(bus.getId());
		if(b == null){
			User user = userDao.selectUserById(bus.getUserId());
			Line line = lineDao.selectLineById(bus.getLineId());
			bus.setUserName(user.getUsername());
			bus.setLineName(line.getName());
			bus.setState(0);
			busDao.saveBus(bus);
			logger.debug(bus.getId()+"*******");
			user.setBusId(bus.getId());
			user.setBusName(bus.getName());
			userDao.updateUser(user);
		}else{	
			User user = userDao.selectUserById(bus.getUserId());
			Line line = lineDao.selectLineById(bus.getLineId());
			b.setLineId(bus.getLineId());
			b.setLineName(line.getName());
			b.setUserId(bus.getUserId());
			b.setUserName(user.getUsername());
			user.setBusId(bus.getId());
			user.setBusName(bus.getName());
			userDao.updateUser(user);
		}
		
		return null;
	}

	@Transactional(propagation=Propagation.REQUIRED)
	public String removeBus(Bus bus){
		int i = busDao.deleteBus(bus.getId());
		if(i == 1){
			resultStr = "{\"success\":true}";
		}else{
			resultStr = "{\"success\":false}";
		}
		return resultStr;
	}
	@Override
	public String getLines(Line line) {
		ArrayList<Line> list = lineDao.selectLine(line);
		ArrayList<TreeJson> treeList = new ArrayList<TreeJson>();
		if(list != null){
			logger.debug(list.get(0).getName());
			String str = gson.toJson(list);
			resultStr = "{\"total\":"+list.size()+",\"rows\":"+str+"}";
			for(Line resultLine : list){
				TreeJson tree = new TreeJson();
				tree.setId(resultLine.getId());
				tree.setText(resultLine.getName());
				treeList.add(tree);
			}
			resultStr = gson.toJson(treeList);
		}else{
			resultStr = "{\"total\":"+0+",\"rows\":[]}";
		}
		return resultStr;
	}

	@Override
	public String updateLines(Line line) {
		line.setState(0);
		lineDao.saveLine(line);
		return null;
	}

	@Override
	public String removeLine(Line line) {
		int i = lineDao.deleteLine(line.getId());
		if(i == 1){
			resultStr = "{\"success\":true}";
		}else{
			resultStr = "{\"success\":false}";
		}
		return resultStr;
	}

	@Override
	public String getStations(Station station) {
		ArrayList<Station> list = stationDao.selectStation(station);
		if(list != null){
			logger.debug(list.get(0).getName());
			String str = gson.toJson(list);
			resultStr = "{\"total\":"+list.size()+",\"rows\":"+str+"}";
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

	@Transactional(propagation=Propagation.REQUIRED)
	@Override
	public String getLineStations(Line line,int orientation) {
		ArrayList<Station> list = lineStationsDao.selectLineStations(line,orientation);
		if(list != null){
			String str = gson.toJson(list);
			resultStr = "{\"total\":"+list.size()+",\"rows\":"+str+"}";
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
				ls.setPriority(curPriority+1);
				lineStationsDao.updateLineStations(ls);
				curPriority++;
			}
		}
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
		lineStationsDao.removeLineStations(lineId,staId);
		return "{\"success\":true}";
	}
*/
}
