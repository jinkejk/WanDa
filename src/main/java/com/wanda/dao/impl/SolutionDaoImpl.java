package com.wanda.dao.impl;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import com.wanda.beans.Solution;
import com.wanda.beans.TrainingMaterialsCategory;
import com.wanda.dao.SolutionDao;

@Repository("solutionDao")
public class SolutionDaoImpl implements SolutionDao {

	@Resource
	private SessionFactory sessionFactory;

	public Solution getSolutionById(Integer solutionId) {
		return  (Solution) sessionFactory.getCurrentSession().get(Solution.class, solutionId);
	}

	public void addSolution(Solution solution) {
		sessionFactory.getCurrentSession().persist(solution);
	}

	public void deleteSolutionById(Integer solutionId) {
		//load性能优于get，load没有数据封装的过程
		sessionFactory.getCurrentSession().delete(
				sessionFactory.getCurrentSession().load(Solution.class, solutionId));	
	}

	public void addClickNumById(Integer solutionId) {
		Solution solution = (Solution) sessionFactory.getCurrentSession().load(Solution.class, solutionId);
		solution.setClickNum(solution.getClickNum() + 1);
		sessionFactory.getCurrentSession().update(solution);		
	}

	@SuppressWarnings("unchecked")
	public List<Solution> getSolutionByPage(int pageSize, int currentPage) {
		if(currentPage <= 0 || pageSize <= 0)
			return null;

		int startNum = pageSize * (currentPage-1);

		List<Solution> solutions = new ArrayList<Solution>();
		solutions = sessionFactory.getCurrentSession()//
				.createQuery("from Solution s order by s.createDate desc")//
				.setCacheable(true)//
				.setFirstResult(startNum)//
				.setMaxResults(pageSize)//
				.list();

		return solutions;
	}

	public Long getTotalSolutionNum() {
		return (Long) sessionFactory.getCurrentSession()//
				.createQuery("select count(*) from Solution")//
				.uniqueResult();
	}

	@SuppressWarnings("unchecked")
	public List<Solution> getSolutionByContentByPage(String searchContent, Integer pageSize, Integer currentPage) {
		if(currentPage <= 0 || pageSize <= 0)
			return null;

		int startNum = pageSize * (currentPage-1);

		List<Solution> solutions = new ArrayList<Solution>();
		solutions = sessionFactory.getCurrentSession()//
				.createSQLQuery("select * from solution s "
						+ "where s.title regexp replace(?1,' ','|') or s.solution regexp replace(?1,' ','|') "
						+ "order by s.createDate desc limit ?2, ?3")//
				.addEntity(Solution.class)//
				.setCacheable(true)//
				.setParameter("1", searchContent)//
				.setParameter("2", startNum)//
				.setParameter("3", pageSize)//
				.list();

		return solutions;
	}

	public BigInteger getTotalSolutionNumByContent(String searchContent) {
		return (BigInteger) sessionFactory.getCurrentSession()//
				.createSQLQuery("select count(*) from solution s "
						+ "where s.title regexp replace(?1,' ','|') or s.solution regexp replace(?1,' ','|') ")//
				.setParameter("1", searchContent)//
				.uniqueResult();			
	}

	public void updateSolution(Solution solution) {
		sessionFactory.getCurrentSession().update(solution);		
	}

	@SuppressWarnings("unchecked")
	public List<Solution> getLastVisitSolutionTitle(int num) {
		return sessionFactory.getCurrentSession()//
				.createQuery("from Solution s order by s.visitDate desc")//
				.setFirstResult(0)//
				.setMaxResults(num)//
				.list();
	}

	@SuppressWarnings("unchecked")
	public List<Solution> getHotVisitSolutionTitle(int num) {
		return sessionFactory.getCurrentSession()//
				.createQuery("from Solution s order by s.clickNum desc")//
				.setFirstResult(0)//
				.setMaxResults(num)//
				.list();
	}

	@SuppressWarnings("unchecked")
	public List<Solution> getSolutionsByCategory(TrainingMaterialsCategory caregory, int pageSize, int currentPage) {
		if(currentPage <= 0 || pageSize <= 0)
			return null;

		int startNum = pageSize * (currentPage-1);

		//若分类是一级分类,查询该类及子类的解决方案
		if(caregory.getParentTMC() == null){
			return  sessionFactory.getCurrentSession()//
					.createQuery("from Solution s "
							+ "where s.category.TMCId=?1 or s.category.parentTMC.TMCId=?1 order by s.createDate desc")//
					.setCacheable(true)//
					.setParameter("1", caregory.getTMCId())//
					.setFirstResult(startNum)//
					.setMaxResults(pageSize)//
					.list();			
		}else{
			return  sessionFactory.getCurrentSession()//
					.createQuery("from Solution s "
							+ "where s.category.TMCId=?1 order by s.createDate desc")//
					.setCacheable(true)//
					.setParameter("1", caregory.getTMCId())//
					.setFirstResult(startNum)//
					.setMaxResults(pageSize)//
					.list();
		}
	}

	@SuppressWarnings("unchecked")
	public List<Solution> getLastSolutions(int num) {
		return sessionFactory.getCurrentSession()//
				.createQuery("from Solution s order by s.createDate desc")//
				.setFirstResult(0)//
				.setMaxResults(num)//
				.list();
	}

	@Override
	public Long getSolutionsNumByCategory(TrainingMaterialsCategory category) {
		//若分类是一级分类,查询该类及子类的解决方案
		if(category.getParentTMC() == null){
			return  (Long) sessionFactory.getCurrentSession()//
					.createQuery("select count(*) from Solution s "
							+ "where s.category.TMCId=?1 or s.category.parentTMC.TMCId=?1")//
					.setCacheable(true)//
					.setParameter("1", category.getTMCId())//
					.uniqueResult();			
		}else{
			return  (Long) sessionFactory.getCurrentSession()//
					.createQuery("select count(*) from Solution s "
							+ "where s.category.TMCId=?1")//
					.setCacheable(true)//
					.setParameter("1", category.getTMCId())//					
					.uniqueResult();
		}
	}

}
