package com.wanda.dao.impl;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import com.wanda.beans.Drawing;
import com.wanda.beans.House;
import com.wanda.dao.HouseDao;

@Repository("houseDao")
public class HouseDaoImpl implements HouseDao {
	
	@Resource
	private SessionFactory sessionFactory;

	@Override
	public House getHouseById(Integer houseId) {
		return (House) sessionFactory.getCurrentSession().get(House.class, houseId);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<House> getHousesByPage(int pageSize, int currentPage) {
		if(currentPage <= 0 || pageSize <= 0)
			return null;
		
		int startNum = pageSize * (currentPage-1);

		List<House> houses = new ArrayList<House>();
		houses = sessionFactory.getCurrentSession()//
				.createQuery("from House h order by h.createDate desc")//
				.setFirstResult(startNum)//
				.setCacheable(true)//
				.setMaxResults(pageSize)//
				.list();

		return houses;
	}

	@Override
	public void deleteHouseById(Integer houseId) {
		sessionFactory.getCurrentSession().delete(this.getHouseById(houseId));

	}

	@Override
	public void addHouse(House house) {
		sessionFactory.getCurrentSession().persist(house);

	}

	@Override
	public void updateHouse(House house) {
		sessionFactory.getCurrentSession().update(house);
	}

	@Override
	public Long getTotalHouseNum() {
		return (Long) sessionFactory.getCurrentSession()//
				.createQuery("select count(*) from House")//
				.uniqueResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<House> getHousesByContentByPage(String searchContent, int pageSize, int currentPage) {
		if(currentPage <= 0 || pageSize <= 0)
			return null;
		
		int startNum = pageSize * (currentPage-1);

		List<House> houses = new ArrayList<House>();
		houses = sessionFactory.getCurrentSession()//
				.createSQLQuery("select * from house h "
						+ "where h.HXName regexp replace(?1,' ','|') or h.LPName regexp replace(?1,' ','|') "
						+ "order by h.createDate desc limit ?2, ?3")//
				.addEntity(House.class)//
				.setParameter("1", searchContent)//
				.setParameter("2", startNum)//
				.setParameter("3", pageSize)//
				.setCacheable(true)//
				.list();

		return houses;
	}

	@Override
	public BigInteger getTotalHouseNumByContent(String searchContent) {
		return (BigInteger) sessionFactory.getCurrentSession()//
				.createSQLQuery("select count(*) from house h "
						+ "where h.HXName regexp replace(?1,' ','|') or h.LPName regexp replace(?1,' ','|') ")//
				.setParameter("1", searchContent)//
				.uniqueResult();
	}

	@SuppressWarnings("unchecked")
	public List<String> getAllLPName() {
		return sessionFactory.getCurrentSession()//
				.createQuery("select distinct LPName from House")//
				.list();
	}

	@SuppressWarnings("unchecked")
	public List<String> getHXNameByLPName(String LPName) {
		return sessionFactory.getCurrentSession()//
				.createQuery("select distinct HXName from House h where h.LPName=?1")//
				.setParameter("1", LPName)//
				.list();
	}

	@Override
	public House getHouseByLPAndHXName(String LPName, String HXName) {
		return (House) sessionFactory.getCurrentSession()//
				.createQuery("from House h where h.LPName=?1 and h.HXName=?2")//
				.setParameter("1", LPName)//
				.setParameter("2", HXName)//
				.uniqueResult();
	}

}
