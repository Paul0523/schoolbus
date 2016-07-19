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

import com.schoolbus.dao.BusDao;
import com.schoolbus.dao.Page;
import com.schoolbus.entity.Bus;

@Repository("busDao")
public class BusDaoImpl implements BusDao{
	
	private Log logger = LogFactory.getLog(BusDaoImpl.class);
	@Resource
	private SessionFactory sessionFactory;

	@SuppressWarnings("rawtypes")
	@Override
	public Page<Bus> selectBusByPage(Bus bus,int start,int size) {
		ArrayList<Bus> buses = new ArrayList<Bus>();
		Session session = sessionFactory.openSession();
		Criteria criteria = session.createCriteria(Bus.class);
		if(bus != null){
			Example example = Example.create(bus);
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
			Bus resultUser = (Bus) it.next();
			buses.add(resultUser);
		}
		if (buses.size() == 0) {
			logger.info("获取到" + buses.size() + "个对象");
			return null;
		} else {
			return new Page<Bus>(buses,totalCount);
		}		
	}

	@Override
	public void saveBus(Bus bus) {
		sessionFactory.getCurrentSession().save(bus);		
	}

	@Override
	public void updateBus(Bus bus) {
		Session session = sessionFactory.getCurrentSession();
		session.update(bus);		
	}

	@Override
	public int deleteBus(int id) {
		String hql = "delete Bus b where b.id=?";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		query.setParameter(0, id);
		int i = query.executeUpdate();
		logger.info("删除了" + i + "条数据"+id);
		return i;		
	}

	@Override
	public Bus selectBusById(int id) {
		Session session = sessionFactory.openSession();
		Bus bus = (Bus) session.get(Bus.class, id);
		session.close();
		return bus;
	}

	@Override
	public ArrayList<Bus> selectBus(Bus bus) {
		ArrayList<Bus> buses = new ArrayList<Bus>();
		Session session = sessionFactory.openSession();
		Criteria criteria = session.createCriteria(Bus.class);
		if(bus != null){
			Example example = Example.create(bus);
			criteria.add(example);
		}
		List list = criteria.list();
		session.close();
		Iterator it = list.iterator();
		while(it.hasNext()) {
			Bus resultUser = (Bus) it.next();
			buses.add(resultUser);
		}
		return buses;	
	}

}
