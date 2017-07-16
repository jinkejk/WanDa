package com.wanda.dao.impl;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import com.wanda.beans.TrainingMaterial;
import com.wanda.beans.User;
import com.wanda.dao.UserDao;

@Repository("userDao")
public class UserDaoImpl implements UserDao {
	@Resource
	private SessionFactory sessionFactory;

	public User getUserById(Integer userId) {		
		return  (User) sessionFactory.getCurrentSession().get(User.class, userId);
	}

	public User getUserByLoginName(String loginName) {
		//hibernate4开始支持 ？+索引的方式
		return (User) sessionFactory.getCurrentSession()//
				.createQuery("from User u where u.loginName=?1")//
				.setString("1", loginName).uniqueResult();
	}

	@SuppressWarnings("unchecked")
	public Set<User> getUserByTrueName(String trueName) {
		//hibernate4开始支持 ？+索引的方式
		return new HashSet<User>(sessionFactory.getCurrentSession()//
				.createQuery("from User u where u.trueName=?1")//
				.setString("1", trueName).list());
	}

	public void addUser(User user) {
		sessionFactory.getCurrentSession().persist(user);
	}

	public void deleteUserById(Integer userId) {
		//load性能优于get，load没有数据封装的过程
		sessionFactory.getCurrentSession().delete(
				sessionFactory.getCurrentSession().load(User.class, userId));
	}

	@SuppressWarnings("unchecked")
	public List<User> getUsersByPage(int pageSize, int currentPage) {
		if(currentPage <= 0 || pageSize <= 0)
			return null;
		
		int startNum = pageSize * (currentPage-1);

		List<User> users = new ArrayList<User>();
		users = sessionFactory.getCurrentSession()//
				.createQuery("from User u order by u.role.roleId asc, u.lastLogin desc")//
				.setFirstResult(startNum)//
				.setMaxResults(pageSize)//
				.list();

		return users;
	}

	public Long getTotalUserNum() {
		return (Long) sessionFactory.getCurrentSession()//
				.createQuery("select count(*) from User")//
				.uniqueResult();
	}

	public void updateUser(User user) {
		sessionFactory.getCurrentSession().update(user);		
	}

	public Boolean getLogFlag(String userName) {
		return (Boolean) sessionFactory.getCurrentSession()//
				.createQuery("select u.logFlag from User u where u.loginName=?1")//
				.setString("1", userName)
				.uniqueResult();
	}

	@SuppressWarnings("unchecked")
	public List<User> getUsersByContentByPage(String searchContent, int pageSize, int currentPage) {
		if(currentPage <= 0 || pageSize <= 0)
			return null;

		int startNum = pageSize * (currentPage-1);

		return sessionFactory.getCurrentSession()//
				.createSQLQuery("select * from user u "
						+ "where u.loginName regexp replace(?1,' ','|') or u.trueName regexp replace(?1,' ','|') "
						+ "order by u.roleId asc limit ?2, ?3")//
				.addEntity(User.class)//
				.setCacheable(true)//
				.setParameter("1", searchContent)//
				.setParameter("2", startNum)//
				.setParameter("3", pageSize)//
				.list();
	}

	@Override
	public BigInteger getUserNumByContentByPage(String searchContent) {
		return (BigInteger) sessionFactory.getCurrentSession()//
				.createSQLQuery("select count(*) from user u "
						+ "where u.loginName regexp replace(?1,' ','|') or u.trueName regexp replace(?1,' ','|') ")//
				.setParameter("1", searchContent)//
				.uniqueResult();
	}

}
