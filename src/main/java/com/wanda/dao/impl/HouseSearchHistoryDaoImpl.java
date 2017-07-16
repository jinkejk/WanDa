package com.wanda.dao.impl;

import javax.annotation.Resource;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import com.wanda.beans.HouseSearchHistory;
import com.wanda.dao.HouseSearchHistoryDao;

@Repository("houseSearchHistoryDao")
public class HouseSearchHistoryDaoImpl implements HouseSearchHistoryDao {

	@Resource
	private SessionFactory sessionFactory;
	
	@Override
	public HouseSearchHistory getHouseSearchHistoryById(Integer searchId) {
		return (HouseSearchHistory) sessionFactory.getCurrentSession().get(HouseSearchHistory.class, searchId);
	}

	@Override
	public void addHouseSearchHistory(HouseSearchHistory houseSearchHistory) {
		sessionFactory.getCurrentSession().persist(houseSearchHistory);
	}

	@Override
	public void deleteHouseSearchHistory(HouseSearchHistory houseSearchHistory) {
		sessionFactory.getCurrentSession().delete(houseSearchHistory);
	}

}
