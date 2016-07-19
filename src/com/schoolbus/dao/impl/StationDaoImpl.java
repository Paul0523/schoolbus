package com.schoolbus.dao.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.Projections;
import org.springframework.stereotype.Repository;

import com.schoolbus.dao.Page;
import com.schoolbus.dao.StationDao;
import com.schoolbus.entity.Station;

@Repository("stationDao")
public class StationDaoImpl implements StationDao{
	private Log logger = LogFactory.getLog(StationDaoImpl.class);
	@Resource
	private SessionFactory sessionFactory;

	
	@Override
	public Station selectStationById(int id) {
		Session session = sessionFactory.openSession();
		Station station = (Station) session.get(Station.class, id);
		return station;
	}

	@Override
	public void saveStation(Station station) {
		sessionFactory.getCurrentSession().save(station);		
	}

	@Override
	public void updateStation(Station station) {
		Session session = sessionFactory.getCurrentSession();
		session.update(station);		
	}

	@Override
	public int deleteStation(int id) {
		String hql = "delete Station b where b.id=?";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		query.setParameter(0, id);
		int i = query.executeUpdate();
		logger.info("删除了" + i + "条数据"+id);
		return i;		
	}

	@Override
	public Page<Station> selectStationByPage(Station station,int start,int size) {
		ArrayList<Station> stations = new ArrayList<Station>();
		Session session = sessionFactory.openSession();
		Criteria criteria = session.createCriteria(Station.class);
		if(station != null){
			Example example = Example.create(station);
			criteria.add(example);
		}
		int totalCount =  ((Long) criteria.setProjection(Projections.rowCount()).uniqueResult()).intValue();
		criteria.setProjection(null);
		if(start < totalCount){
			criteria.setFirstResult(start);
		}else{
			criteria.setFirstResult(start - size);
		}
		criteria.setMaxResults(size);
		List list = criteria.list();
		session.close();
		Iterator it = list.iterator();
		while(it.hasNext()) {
			Station resultStation = (Station) it.next();
			stations.add(resultStation);
		}
		if (stations.size() == 0) {
			logger.info("获取到" + stations.size() + "个对象");
			return null;
		} else {
			return new Page<Station>(stations,totalCount);
		}

	}

	@SuppressWarnings("rawtypes")
	@Override
	public ArrayList<Station> selectStations(Station station) {
		ArrayList<Station> stations = new ArrayList<Station>();
		Session session = sessionFactory.openSession();
		Criteria criteria = session.createCriteria(Station.class);
		if(station != null){
			Example example = Example.create(station);
			criteria.add(example);
		}
		List list = criteria.list();
		session.close();
		Iterator it = list.iterator();
		while(it.hasNext()) {
			Station resultStation = (Station) it.next();
			stations.add(resultStation);
		}
		if (stations.size() == 0) {
			logger.info("获取到" + stations.size() + "个对象");
			return null;
		} else {
			return stations;
		}
	}

}
