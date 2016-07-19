package com.schoolbus.service.impl;

import java.util.ArrayList;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.google.gson.Gson;
import com.schoolbus.dao.BusDao;
import com.schoolbus.dao.LineDao;
import com.schoolbus.dao.Page;
import com.schoolbus.dao.UserDao;
import com.schoolbus.entity.Bus;
import com.schoolbus.entity.Line;
import com.schoolbus.entity.User;
import com.schoolbus.service.BusService;

@Service("busService")
public class BusServiceImpl implements BusService{
	private Log logger = LogFactory.getLog(BusServiceImpl.class);
	@Resource
	private BusDao busDao;
	@Resource
	private LineDao lineDao;
	@Resource
	private UserDao userDao;
	private String resultStr;
	private Gson gson = new Gson();

	@Override
	public String getBuses(Bus bus,int page,int rows){
		int start = (page - 1)*rows;
		int size = rows;
		Page<Bus> list = busDao.selectBusByPage(bus,start,size);
		if(list != null){
			if(list.getList() != null){
				ArrayList<Bus> buses = list.getList();
				logger.debug(buses.get(0).getName());
				String str = gson.toJson(buses);
				resultStr = "{\"total\":"+list.getTotalCount()+",\"rows\":"+str+"}";
			}
		}else{
			resultStr = "{\"total\":"+0+",\"rows\":[]}";
		}
		return resultStr;
	}

	@Override
	public String updateBus(Bus bus){
		Bus b = busDao.selectBusById(bus.getId());
		if(b == null){
			if(bus.getUserId() == null || bus.getLineId() == null){
				bus.setState(2);
			}else{
				bus.setState(0);
			}
			User user = null;
			if(bus.getUserId() != null){
				user = userDao.selectUserById(bus.getUserId());
				bus.setUserName(user.getUsername());
			}
			if(bus.getLineId() != null){
				Line line = lineDao.selectLineById(bus.getLineId());
				bus.setLineName(line.getName());
			}
			busDao.saveBus(bus);
			if(user != null){
				user.setBusId(bus.getId());
				user.setBusName(bus.getName());
				userDao.updateUser(user);
			}
		}else{	
			if(bus.getUserId() == null || bus.getLineId() == null){
				b.setState(2);
				b.setLongitude("");
				b.setLatitude("");
			}else{
				b.setState(0);
			}
			if(bus.getUserId() != null){
				User user = userDao.selectUserById(bus.getUserId());
				b.setUserId(bus.getUserId());
				b.setUserName(user.getUsername());
				user.setBusId(b.getId());
				user.setBusName(b.getName());
				userDao.updateUser(user);
			}else{
				logger.debug("1****");
				if(b.getUserId() != null){
					logger.debug("2*****"+b.getUserId());
					User user = userDao.selectUserById(b.getUserId());
					user.setBusId(null);
					user.setBusName("");
					userDao.updateUser(user);
				}
				b.setUserId(null);
				b.setUserName("");
			}
			if(bus.getLineId() != null){
				Line line = lineDao.selectLineById(bus.getLineId());
				b.setLineId(bus.getLineId());
				b.setLineName(line.getName());
			}else{
				b.setLineId(null);
				b.setLineName("");
			}
			busDao.updateBus(b);
		}
		
		return null;
	}

	@Override
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
	public String busById(Bus bus) {
		Bus resultBus = busDao.selectBusById(bus.getId());
		resultStr = gson.toJson(resultBus);
		return resultStr;
	}

}
