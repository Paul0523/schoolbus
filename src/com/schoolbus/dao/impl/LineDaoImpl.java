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
import org.springframework.stereotype.Repository;

import com.schoolbus.dao.LineDao;
import com.schoolbus.entity.Line;
import com.schoolbus.entity.Station;

@Repository("lineDao")
public class LineDaoImpl implements LineDao{
	private Log logger = LogFactory.getLog(LineDaoImpl.class);
	@Resource
	private SessionFactory sessionFactory;
	
	@SuppressWarnings("rawtypes")
	@Override
	public ArrayList<Line> selectLine(Line line) {
		ArrayList<Line> lines = new ArrayList<Line>();
		Session session = sessionFactory.openSession();
		Criteria criteria = session.createCriteria(Line.class);
		if(line != null){
			Example example = Example.create(line);
			criteria.add(example);
		}
		List list = criteria.list();
		session.close();
		Iterator it = list.iterator();
		while(it.hasNext()) {
			Line resultUser = (Line) it.next();
			lines.add(resultUser);
		}
		if (lines.size() == 0) {
			logger.info("获取到" + lines.size() + "个对象");
			return null;
		} else {
			return lines;
		}	
	}

	@Override
	public void saveLine(Line line) {
		sessionFactory.getCurrentSession().save(line);		
	}

	@Override
	public void updateLine(Line line) {
		Session session = sessionFactory.getCurrentSession();
		session.update(line);		
	}

	@Override
	public int deleteLine(int id) {
		String hql = "delete Line lin where lin.id=?";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		query.setParameter(0, id);
		int i = query.executeUpdate();
		logger.info("删除了" + i + "条数据");
		return i;
	}

	@Override
	public Line selectLineById(int id) {
		Session session = sessionFactory.openSession();
		Line line = (Line) session.get(Line.class, id);
		session.close();
		return line;
	}

	@SuppressWarnings("unchecked")
	@Override
	public ArrayList<Line> selectLinesByStationId(int stationId) {
		ArrayList<Line> resultLines = new ArrayList<Line>();
		String hql = "select lin.id,lin.name,lin.state,ls.orientation from Line lin,LineStations ls where lin.id = ls.lineId and ls.stationId = ?";
		Session session = sessionFactory.openSession();
		Query query = session.createQuery(hql);
		query.setParameter(0, stationId);
		List list = query.list();
		session.close();
		Iterator iter = list.iterator();  
        while(iter.hasNext()){  
            Object[] obj = (Object[])iter.next();  
            Line line = new Line();
            line.setId((Integer)obj[0]);
            line.setName((String)obj[1]);
            line.setState((Integer)obj[2]);
            line.setOrientation((Integer)obj[3]);
            resultLines.add(line);
        }  
		logger.debug(resultLines.get(0).getName());
		return resultLines;
	}

}
