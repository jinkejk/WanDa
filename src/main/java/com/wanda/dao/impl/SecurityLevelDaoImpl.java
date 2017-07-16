package com.wanda.dao.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import com.wanda.beans.SecurityLevel;
import com.wanda.dao.SecurityLevelDao;

@Repository("securityLevelDao")
public class SecurityLevelDaoImpl implements SecurityLevelDao{

	@Resource
	private SessionFactory sessionFactory;

	public SecurityLevel getSecurityLevelById(int securityLevelId) {
		return (SecurityLevel) sessionFactory.getCurrentSession().get(SecurityLevel.class, securityLevelId);
	}

	public SecurityLevel getSecurityLevelByName(String securityLevelName) {
		return (SecurityLevel) sessionFactory.getCurrentSession()//
				.createQuery("from SecurityLevel s where s.securityLevelName=?1")//
				.setString("1", securityLevelName)//
				.uniqueResult();
	}

	@SuppressWarnings("unchecked")
	public List<SecurityLevel> getAllSecurityLevels() {
		List<SecurityLevel> securityLevels = new ArrayList<SecurityLevel>();
		securityLevels = sessionFactory.getCurrentSession()//
				.createQuery("from SecurityLevel")//
				.list();
		
		return securityLevels;
	}

}
