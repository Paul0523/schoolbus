package com.schoolbus.dao.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import com.schoolbus.dao.LineStationsDao;
import com.schoolbus.dao.Page;
import com.schoolbus.entity.Line;
import com.schoolbus.entity.LineStations;
import com.schoolbus.entity.Station;

@Repository("lineStationsDao")
public class LineStationsDaoImpl implements LineStationsDao{
	private Log logger = LogFactory.getLog(LineStationsDaoImpl.class);
	@Resource
	private SessionFactory sessionFactory;

	@Override
	public Page<Station> selectLineStationsByPage(Line line,int orientation,int start,int size) {
		Session session = sessionFactory.openSession();
		String total = "select count(*) from Station sta,LineStations ls where sta.id = ls.stationId and ls.lineId = ? and ls.orientation = ? order by ls.priority asc"; 
		int totalCount = ((Long)session.createQuery(total).setParameter(0, line.getId()).setParameter(1, orientation).uniqueResult()).intValue();
		logger.debug(totalCount+"******"+start+"*****"+size);
		String hql = "select sta.id,sta.name,sta.longitude,sta.latitude,sta.state from Station sta,LineStations ls where sta.id = ls.stationId and ls.lineId = ? and ls.orientation = ? order by ls.priority asc";
		Query query = session.createQuery(hql);
		query.setParameter(0, line.getId());
		query.setParameter(1, orientation);
		if(start < totalCount){
			query.setFirstResult(start);
		}else{
			query.setFirstResult(start - size);
		}
		query.setMaxResults(size);
		ArrayList<Station> resultList = new ArrayList<Station>();
		List list = query.list(); 
		session.close();
		Iterator iter = list.iterator();  
        while(iter.hasNext()){  
            Object[] obj = (Object[])iter.next();  
            Station resultStation = new Station();
            resultStation.setId((Integer)obj[0]);
            resultStation.setName((String)obj[1]);
            resultStation.setLongitude((String)obj[2]);
            resultStation.setLatitude((String)obj[3]);
            resultStation.setState((Integer)obj[4]);
            resultList.add(resultStation);
        }  
		logger.info("查询到了" + resultList.size() + "个站点");
		return new Page<Station>(resultList,totalCount);
	}


	@Override
	public int selectMaxPriority(int lineId,int orientation) {
		int maxPriority;
		String hql = "select max(ls.priority) from LineStations ls where ls.lineId = ? and ls.orientation = ?";
		Query query = sessionFactory.openSession().createQuery(hql);
		query.setInteger(0, lineId);
		query.setInteger(1, orientation);
		List list = query.list(); 
		if(list.get(0) == null){
			maxPriority = 0;
		}else{
			maxPriority = (int) list.get(0);
		}
		return maxPriority;
	}

	@Override
	public void saveLineStation(LineStations lineStations) {
		sessionFactory.getCurrentSession().save(lineStations);
		
	}

	@Override
	public LineStations selectLineStationsByPriority(int lineId,int priority,int orientation) {
		String hql = "from LineStations ls where ls.lineId = ? and ls.priority = ? and ls.orientation = ?";
		Query query = sessionFactory.openSession().createQuery(hql);
		query.setInteger(0, lineId);
		query.setInteger(1, priority);
		query.setInteger(2, orientation);
		List list = query.list(); 
		LineStations lineStations = (LineStations) list.get(0);
		return lineStations;
	}

	@Override
	public void updateLineStations(LineStations lineStations) {
		sessionFactory.getCurrentSession().update(lineStations);
	}


	@Override
	public LineStations selectLineStationsById(int lineId, int curId,int orientation) {
		String hql = "from LineStations ls where ls.lineId = ? and ls.stationId = ? and ls.orientation = ?";
		Query query = sessionFactory.openSession().createQuery(hql);
		query.setInteger(0, lineId);
		query.setInteger(1, curId);
		query.setInteger(2, orientation);
		List list = query.list(); 
		if(list.size() == 0){
			return null;
		}
		LineStations lineStations = (LineStations) list.get(0);
		return lineStations;
	}


	@Override
	public int removeLineStations(int lineId,int staId,int orientation) {
		String hql = "delete LineStations ls where ls.stationId=? and ls.lineId=? and ls.orientation=?";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		query.setParameter(0, staId);
		query.setParameter(1, lineId);
		query.setParameter(2, orientation);
		int i = query.executeUpdate();
		logger.info("删除了" + i + "条数据");
		return i;		
	}


	@Override
	public ArrayList<Station> selectLineStations(int lineId, int orientation) {
		String hql = "select sta.id,sta.name,sta.longitude,sta.latitude from Station sta,LineStations ls where sta.id = ls.stationId and ls.lineId = ? and ls.orientation = ? order by ls.priority asc";
		Session session = sessionFactory.openSession();
		Query query = session.createQuery(hql);
		query.setParameter(0, lineId);
		query.setParameter(1, orientation);
		ArrayList<Station> resultList = new ArrayList<Station>();
		List list = query.list(); 
		session.close();
		Iterator iter = list.iterator();  
        while(iter.hasNext()){  
            Object[] obj = (Object[])iter.next();  
            Station resultStation = new Station();
            resultStation.setId((Integer)obj[0]);
            resultStation.setName((String)obj[1]);
            resultStation.setLongitude((String)obj[2]);
            resultStation.setLatitude((String)obj[3]);
            logger.debug(resultStation.getName()+"*****");
            resultList.add(resultStation);
        }  
		logger.info("查询到了" + resultList.size() + "个站点");
		return resultList;
	}


	@Override
	public int selectStationNumByStaId(int stationId) {
		String hql = "select count(*) from LineStations ls where ls.stationId = ?";
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery(hql);
		query.setParameter(0, stationId);
		int num = ((Long)query.uniqueResult()).intValue();
		return num;
	}

}
