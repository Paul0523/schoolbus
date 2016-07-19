package com.schoolbus.service.impl;

import java.util.ArrayList;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.schoolbus.dao.BusDao;
import com.schoolbus.dao.LineDao;
import com.schoolbus.dao.LineStationsDao;
import com.schoolbus.dao.StationDao;
import com.schoolbus.dao.UserDao;
import com.schoolbus.entity.Bus;
import com.schoolbus.entity.BusState;
import com.schoolbus.entity.Customer;
import com.schoolbus.entity.Line;
import com.schoolbus.entity.LineStations;
import com.schoolbus.entity.SearchInfo;
import com.schoolbus.entity.Station;
import com.schoolbus.entity.User;
import com.schoolbus.service.CustomerService;
import com.schoolbus.service.ServiceUtil;

/**
 * Created by Paul on 2016/3/2.
 */
@Service("customerService")
public class CustomerServiceImpl implements CustomerService {
	private Log logger = LogFactory.getLog(CustomerServiceImpl.class);
	@Resource
	private BusDao busDao;
	@Resource
	private LineDao lineDao;
	@Resource
	private StationDao stationDao;
	@Resource
	private LineStationsDao lineStationsDao;
	@Resource
	UserDao userDao;
	private String resultStr;
	private Gson gson = new Gson();
   

	public String getStation(Customer customer) {
		int type = customer.getType();
		logger.debug(type+"***");
		Station resultStation = null;
		String errmsg = "{\"msg\":\"对不起，查询异常，请稍后重试\"}";
		if(type == 1){
			String strLongitude = customer.getLongitude();
			String strLatitude = customer.getLatitude();
			float longitude = Float.parseFloat(strLongitude);
			float latitude = Float.parseFloat(strLatitude);
			Station stationd = new Station();
			ArrayList<Station> list = stationDao.selectStations(stationd);
			float distance = -1;
			for(Station station:list){
				float tempDistance = (float) Math.sqrt(Math.pow(longitude-Float.parseFloat(station.getLongitude()), 2)+Math.pow(latitude-Float.parseFloat(station.getLatitude()),2));
				logger.info("计算得两地间距："+tempDistance);
				if(distance==-1){
					distance = tempDistance;
					resultStation = station;
				}else{
					if(tempDistance<distance){
						distance = tempDistance;
						resultStation = station;
					}
				}
			}
		}
		if(type == 2){
			int staId = customer.getStaId();
			resultStation = stationDao.selectStationById(staId);
		}
		return ServiceUtil.<Station>toJson(resultStation);
		/*if(resultStation != null){
//			resultStr = gson.toJson(resultStation);
		}else{
			resultStr = errmsg;
		}
        return resultStr;*/
    }

    public String getSearch(Customer customer) {
    	ArrayList<SearchInfo> list = new ArrayList<SearchInfo>();
    	SearchInfo searchInfo = new SearchInfo();
    	searchInfo.setId(1);
    	searchInfo.setName("2haoxian");
    	searchInfo.setType(1);
    	list.add(searchInfo);
    	searchInfo.setId(2);
    	searchInfo.setName("2haoxian");
    	searchInfo.setType(2);
    	list.add(searchInfo);
        return gson.toJson(list);
    }


    public String getLine(Customer customer) {
    	ArrayList<Line> list = lineDao.selectLinesByStationId(customer.getStaId());
        return ServiceUtil.<ArrayList<Line>>toJson(list);
    }

    public String getMapLine(Customer customer) {
        return null;
    }

	@Override
	public String getLineStations(Customer customer) {
		int lineId = customer.getLineId();
		int orientation = customer.getOrientation();
		ArrayList<Station> stations = lineStationsDao.selectLineStations(lineId,orientation);
		return ServiceUtil.<ArrayList<Station>>toJson(stations);
	}

	@Override
	public String getLineBuses(Customer customer) {
		int lineId = customer.getLineId();
		int orientation = customer.getOrientation();
		Bus bus = new Bus();
		bus.setLineId(lineId);
		bus.setState(1);
		bus.setLineOrientation(orientation);
		ArrayList<Bus> buses = busDao.selectBus(bus);
		ArrayList<BusState> busesState = new ArrayList<BusState>();
		for(Bus i : buses){
			BusState busState = null;
			Station curStation = stationDao.selectStationById(i.getCurStationId());
			String curStationLongitude = curStation.getLongitude();
			String curStationLatitude = curStation.getLatitude();
			String busLongitude = i.getLongitude();
			String busLatitude = i.getLatitude();
			logger.debug(i.getLineOrientation()+"**88**"+i.getName()+"******"+i.getCurStationId()+"****"+curStation.getName()+"****"+curStationLongitude+"******"+curStationLatitude+"*****"+busLongitude+"******"+busLatitude);
			float tempDistance = (float) Math.sqrt(Math.pow(Float.parseFloat(curStationLongitude)-Float.parseFloat(busLongitude), 2)+Math.pow(Float.parseFloat(curStationLatitude)-Float.parseFloat(busLatitude),2));
			double testDistance = ServiceUtil.Distance( Double.parseDouble(curStationLongitude), Double.parseDouble(curStationLatitude), Double.parseDouble(busLongitude), Double.parseDouble(busLatitude));
			logger.debug("计算两点之间的距离"+ testDistance);
			boolean tag = testDistance < 30;
			for(BusState j : busesState){
				if(tag){
					if(j.getCurStationId() == i.getCurStationId() && j.getNextStationId() == i.getCurStationId()){
						busState = j;
					}
				}else{
					if(j.getCurStationId() == i.getCurStationId() && j.getNextStationId() == i.getNextStationId()){
						busState = j;
					}
				}
			}
			if(busState == null){
				busState = new BusState();
				if(tag){
					busState.setCurStationId(i.getCurStationId());
					busState.setNextStationId(i.getCurStationId());
				}else{
					busState.setCurStationId(i.getCurStationId());
					busState.setNextStationId(i.getNextStationId());
				}
				busesState.add(busState);
			}
			busState.setNum(busState.getNum() + 1);
		}
		
		return ServiceUtil.<ArrayList<BusState>>toJson(busesState);
	}

	@Override
	public String callBus(Customer customer) {
		String account = customer.getAccount();
		User resultUser = userDao.selectByAccount(account);
		if(resultUser.getState() == 1){
			if(resultUser.getAppointmentLine() == 0){
				int lineId = customer.getLineId();
				int curId = customer.getStaId();
				int orientation = customer.getOrientation();
				logger.debug(lineId+"****"+curId+"*****"+orientation);
				LineStations ls = lineStationsDao.selectLineStationsById(lineId, curId, orientation);
				int maxPriority = lineStationsDao.selectMaxPriority(lineId, orientation);
				logger.debug(ls.getPriority() + "********" + maxPriority);
				if(ls.getPriority() == 1){
					return "{\"code\":3,\"msg\":\"暂不提供预约首发站功能\"}";
				}else if(ls.getPriority() == maxPriority){
					return "{\"code\":3,\"msg\":\"不能预约终点站\"}";
				}
				if(ls.getNum() == null){
					ls.setNum(0);
				}
				ls.setNum(ls.getNum() + 1);
				lineStationsDao.updateLineStations(ls);
				resultUser.setAppointmentLine(lineId);
				resultUser.setAppointmentOrientation(orientation);
				resultUser.setAppointmentStationId(curId);
				userDao.updateUser(resultUser);
				return "{\"code\":1,\"msg\":\"预约成功\"}";
			}else{
				return "{\"code\":0,\"msg\":\"您已经预约，请稍后再试\"}";
			}
		}else{
			return "{\"code\":0,\"msg\":\"请登录后操作\"}";
		}
	}

}
