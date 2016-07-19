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
import com.schoolbus.dao.UserDao;
import com.schoolbus.entity.User;

@Repository("userDao")
public class UserDaoImpl implements UserDao {
	private Log logger = LogFactory.getLog(UserDaoImpl.class);
	@Resource
	private SessionFactory sessionFactory;

	@SuppressWarnings("rawtypes")
	@Override
	public Page<User> selectUserByPage(User user,int start,int size) {
		ArrayList<User> users = new ArrayList<User>();
		Session session = sessionFactory.openSession();
		Criteria criteria = session.createCriteria(User.class);
		if(user != null){
			Example example = Example.create(user);
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
			User resultUser = (User) it.next();
			users.add(resultUser);
		}
		if (users.size() == 0) {
			logger.info("获取到" + users.size() + "个对象");
			return null;
		} else {
			return new Page<User>(users,totalCount);
		}

	}

	@Override
	public void saveUser(User user) {
		sessionFactory.getCurrentSession().save(user);
	}

	@Override
	public void updateUser(User user) {
		Session session = sessionFactory.getCurrentSession();
		session.update(user);
	}

	@Override
	public int deleteUser(int id) {
		String hql = "delete User u where u.id=?";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		query.setParameter(0, id);
		int i = query.executeUpdate();
		logger.info("删除了" + i + "条数据");
		return i;
	}

	@Override
	public User selectByAccount(String account) {
		String hql = "from User u where u.account=?";
		logger.debug("入参"+account);
		Session session = sessionFactory.openSession();
		Query query = session.createQuery(hql);
		query.setString(0, account);
		ArrayList<User> list = (ArrayList<User>) query.list();
		session.close();
		if(list.size() == 1){
			logger.debug(list.get(0).getAccount());
			return list.get(0);
		}else{
			return null;
		}
	}

	@Override
	public User selectUserById(int id) {
		Session session = sessionFactory.getCurrentSession();
		User user = (User) session.get(User.class, id);
		return user;
	}

	@Override
	public ArrayList<User> selectUser(User user) {
		ArrayList<User> users = new ArrayList<User>();
		Session session = sessionFactory.openSession();
		Criteria criteria = session.createCriteria(User.class);
		if(user != null){
			Example example = Example.create(user);
			criteria.add(example);
		}
		
		List list = criteria.list();
		session.close();
		Iterator it = list.iterator();
		while(it.hasNext()) {
			User resultUser = (User) it.next();
			users.add(resultUser);
		}
		return users;
	}
}
