package com.schoolbus.service.impl;

import java.util.ArrayList;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import com.schoolbus.dao.BusDao;
import com.schoolbus.dao.LineDao;
import com.schoolbus.dao.LineStationsDao;
import com.schoolbus.dao.StationDao;
import com.schoolbus.dao.UserDao;
import com.schoolbus.entity.Bus;
import com.schoolbus.entity.Driver;
import com.schoolbus.entity.Line;
import com.schoolbus.entity.LineStations;
import com.schoolbus.entity.Station;
import com.schoolbus.entity.User;
import com.schoolbus.service.DriverService;
import com.schoolbus.service.ServiceUtil;

/**
 * Created by Paul on 2016/3/2.
 */
@Service("driverService")
public class DriverServiceImpl implements DriverService {
	private Log logger = LogFactory.getLog(DriverServiceImpl.class);
	@Resource
	private LineDao lineDao;
	@Resource
	private BusDao busDao;
	@Resource
	private LineStationsDao lineStationsDao;
	@Resource
	private StationDao stationDao;
	@Resource
	private UserDao userDao;

	@Override
	public String getLine(Driver driver) {
		Bus bus = busDao.selectBusById(driver.getBusId());
		Line line = lineDao.selectLineById(bus.getLineId());
		return ServiceUtil.<Line>toJson(line);
	}

	@Override
	public String start(Driver driver) {
		String longitude = driver.getLongitude();
		String latitude = driver.getLatitude();
		int busId = driver.getBusId();
		Bus bus = busDao.selectBusById(busId);
		int lineId = bus.getLineId();
		ArrayList<Station>  stations = lineStationsDao.selectLineStations(lineId, 0);
		Station firstStation = stations.get(0);
		Station lastStation = stations.get(stations.size()-1);
		float firstDistance = (float) Math.sqrt(Math.pow(Float.parseFloat(longitude)-Float.parseFloat(firstStation.getLongitude()), 2)+Math.pow(Float.parseFloat(latitude)-Float.parseFloat(firstStation.getLatitude()),2));
		float lastDistance = (float) Math.sqrt(Math.pow(Float.parseFloat(longitude)-Float.parseFloat(lastStation.getLongitude()), 2)+Math.pow(Float.parseFloat(latitude)-Float.parseFloat(lastStation.getLatitude()),2));
		boolean tag = firstDistance < lastDistance;
		if(tag){
			bus.setCurStationId(firstStation.getId());
			bus.setNextStationId(stations.get(1).getId());
			bus.setState(1);
			bus.setLineOrientation(0);
			bus.setLongitude(longitude);
			bus.setLatitude(latitude);
			busDao.updateBus(bus);
		}else{
			stations = lineStationsDao.selectLineStations(lineId, 1);
			bus.setCurStationId(stations.get(0).getId());
			bus.setNextStationId(stations.get(1).getId());
			bus.setState(1);
			bus.setLineOrientation(1);
			bus.setLongitude(longitude);
			bus.setLatitude(latitude);
			busDao.updateBus(bus);
		}
		return "{\"code\":1,\"msg\":\"发车成功，请安全驾驶\"}";
	}

	@Override
	public String stop(Driver driver) {
		logger.debug("******");
		int busId = driver.getBusId();
		Bus bus = busDao.selectBusById(busId);
		bus.setState(0);
		bus.setLongitude("");
		bus.setLatitude("");
		busDao.updateBus(bus);
		return "{\"code\":1,\"msg\":\"停车成功\"}";
	}
	
	@Override
	public String updateGPS(Driver driver) {
		String longitude = driver.getLongitude();
		String latitude = driver.getLatitude();
		int busId = driver.getBusId();
		int num = 0;
		Bus bus = busDao.selectBusById(busId);
		bus.setLongitude(longitude);
		bus.setLatitude(latitude);
		int lineId = bus.getLineId();
		int nextStationId = bus.getNextStationId();
		if(nextStationId != -1){
			Station station = stationDao.selectStationById(bus.getNextStationId());
			int curId = bus.getNextStationId();
			int orientation = bus.getLineOrientation();
			String nextStationLongitude = station.getLongitude();
			String nextStationLatitude = station.getLatitude();
			float distance = (float) Math.sqrt(Math.pow(Float.parseFloat(longitude)-Float.parseFloat(nextStationLongitude), 2)+Math.pow(Float.parseFloat(latitude)-Float.parseFloat(nextStationLatitude),2));
			double testDistance = ServiceUtil.Distance( Double.parseDouble(longitude), Double.parseDouble(latitude), Double.parseDouble(nextStationLongitude), Double.parseDouble(nextStationLatitude));
			LineStations ls = lineStationsDao.selectLineStationsById(lineId, curId, orientation);
			logger.debug(curId+"*****"+longitude+"******"+latitude+"*******"+busId+"******"+distance+"******"+testDistance);
			if(ls.getNum() == null){
				ls.setNum(0);
			}
			num = ls.getNum();
			if(testDistance < 30){
				User user = new User();
				user.setAppointmentLine(lineId);
				user.setAppointmentOrientation(orientation);
				user.setAppointmentStationId(curId);
				ArrayList<User> users = userDao.selectUser(user);
				for(User u:users){
					u.setAppointmentLine(0);
					userDao.updateUser(u);
				}
				ls.setNum(0);
				lineStationsDao.updateLineStations(ls);
				bus.setCurStationId(bus.getNextStationId());
				int priority = ls.getPriority();
				int maxPriority = lineStationsDao.selectMaxPriority(lineId, orientation);
				if(priority == maxPriority){
					bus.setNextStationId(-1);
					bus.setState(0);
					bus.setLongitude("");
					bus.setLatitude("");
				}else{
					LineStations nextLs = lineStationsDao.selectLineStationsByPriority(lineId, priority+1, orientation);
					bus.setNextStationId(nextLs.getStationId());
					if(nextLs.getNum() == null){
						nextLs.setNum(0);
					}
					num = nextLs.getNum();
				}
				busDao.updateBus(bus);
				return "{\"code\":1,\"msg\":\"车俩进站，下一站点有"+num+"人乘坐车辆\"}";
			}
			busDao.updateBus(bus);
			return "{\"code\":1,\"msg\":\"下一站点有"+num+"人乘坐车辆\"}";
		}
		busDao.updateBus(bus);
		return "{\"code\":1,\"msg\":\"下一站点有"+num+"人乘坐车辆\"}";
	}
	
}
