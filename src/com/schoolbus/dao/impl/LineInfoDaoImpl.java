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
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.Projections;
import org.springframework.stereotype.Repository;

import com.schoolbus.dao.LineInfoDao;
import com.schoolbus.entity.LineInfo;

@Repository("lineInfoDao")
public class LineInfoDaoImpl implements LineInfoDao{
	private Log logger = LogFactory.getLog(LineInfoDaoImpl.class);
	@Resource
	private SessionFactory sessionFactory;

	@Override
	public ArrayList<LineInfo> selectLineInfo(LineInfo lineInfo) {
		ArrayList<LineInfo> lineInfos = new ArrayList<LineInfo>();
		Session session = sessionFactory.openSession();
		Criteria criteria = session.createCriteria(LineInfo.class);
		if(lineInfo != null){
			Example example = Example.create(lineInfo);
			criteria.add(example);
			criteria.addOrder(Order.asc("priority"));
		}
		List list = criteria.list();
		session.close();
		Iterator it = list.iterator();
		while(it.hasNext()) {
			LineInfo resultLineInfo = (LineInfo) it.next();
			lineInfos.add(resultLineInfo);
		}
		
		return lineInfos;
	}

	@Override
	public void updateLineInfo(LineInfo lineInfo) {
		sessionFactory.getCurrentSession().update(lineInfo);
	}

	@Override
	public void removeLineInfo(int id) {
		String hql = "delete LineInfo lin where lin.id=?";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		query.setParameter(0, id);
		int i = query.executeUpdate();
		logger.info("删除了" + i + "条数据");
	}

	@Override
	public LineInfo selectLineInfoById(int id) {
		Session session = sessionFactory.openSession();
		LineInfo lineInfo = (LineInfo) session.get(LineInfo.class, id);
		session.close();
		return lineInfo;
	}

	@Override
	public void saveLineInfo(LineInfo lineInfo) {
		sessionFactory.getCurrentSession().save(lineInfo);
	}

	@Override
	public int selectTotalCount(LineInfo lineInfo) {
		Session session = sessionFactory.openSession();
		Criteria criteria = session.createCriteria(LineInfo.class);
		if(lineInfo != null){
			Example example = Example.create(lineInfo);
			criteria.add(example);
		}
		Projection pro = Projections.rowCount();
		int totalCount =  ((Long) criteria.setProjection(pro).uniqueResult()).intValue();
		return totalCount;
	}

	@Override
	public int selectMaxPriority(int lineId, int orientation) {
		String hql = "select max(priority) from LineInfo ln where ln.lineId = ? and ln.orientation = ?";
		Session session = sessionFactory.openSession();
		Query query = session.createQuery(hql);
		query.setParameter(0, lineId);
		query.setParameter(1, orientation);
		int maxPriority = (int) query.uniqueResult();
		return maxPriority;
	}

	@Override
	public LineInfo selectByPriority(int lineId, int orientation, int priority) {
		String hql = "from LineInfo ln where ln.lineId = ? and ln.orientation = ? and ln.priority = ?";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		query.setParameter(0, lineId);
		query.setParameter(1, orientation);
		query.setParameter(2, priority);
		List list = query.list();
		LineInfo ln = (LineInfo) list.get(0);
		return ln;
	}
	
	
}
