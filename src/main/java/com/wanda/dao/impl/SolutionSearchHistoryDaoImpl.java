package com.wanda.dao.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import com.wanda.beans.SolutionSearchHistory;
import com.wanda.dao.SolutionSearchHistoryDao;

@Repository("solutionSearchHistoryDao")
public class SolutionSearchHistoryDaoImpl implements SolutionSearchHistoryDao {	
	@Resource
	private SessionFactory sessionFactory;

	@SuppressWarnings("unchecked")
	public List<SolutionSearchHistory> getHistoryByUserId(Integer userId) {
		return new ArrayList<SolutionSearchHistory>(sessionFactory.getCurrentSession()//
				.createQuery("from SolutionSearchHistory s where s.user.userId=?1")//
				.setInteger("1", userId)//
				.list());
	}

	public SolutionSearchHistory getHistoryById(Integer solutionSearchHistoryId) {
		return (SolutionSearchHistory) sessionFactory.getCurrentSession()//
				.get(SolutionSearchHistory.class, solutionSearchHistoryId);
	}

	public void addSolutionSearchHistory(SolutionSearchHistory solutionSearchHistory) {
		sessionFactory.getCurrentSession().persist(solutionSearchHistory);
	}

	public void deleteSolutionSearchHistory(Integer solutionSearchHistoryId) {
		sessionFactory.getCurrentSession().delete(
				sessionFactory.getCurrentSession()//
				.get(SolutionSearchHistory.class, solutionSearchHistoryId));
	}

	public void updateSolutionSearchHistory(SolutionSearchHistory solutionSearchHistory) {
		sessionFactory.getCurrentSession().update(solutionSearchHistory);
	}

	@SuppressWarnings("unchecked")
	public List<String> getLastSearchContent(int num) {
		return sessionFactory.getCurrentSession()//
				.createQuery("select s.keyword from SolutionSearchHistory s order by s.searchDate desc")//
				.setFirstResult(0)//
				.setMaxResults(num)//
				.list();
	}

}
