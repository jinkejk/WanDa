package com.wanda.dao.impl;

import java.math.BigInteger;
import java.util.List;
import javax.annotation.Resource;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import com.wanda.beans.TrainingMaterialsCategory;
import com.wanda.dao.TrainingMaterialsCategoryDao;

@Repository("trainingMaterialsCategoryDao")
public class TrainingMaterialsCategoryDaoImpl implements TrainingMaterialsCategoryDao{
	@Resource
	private SessionFactory sessionFactory;
	
	@Override
	public void addTrainingMaterialsCategory(TrainingMaterialsCategory TMC) {
		sessionFactory.getCurrentSession().persist(TMC);
	}

	@Override
	public void deleteTrainingMaterialsCategoryById(Integer TMCId) {
		sessionFactory.getCurrentSession().delete(
				this.getTrainingMaterialsCategoryById(TMCId));		
	}

	@Override
	public void updateTrainingMaterialsCategory(TrainingMaterialsCategory TMC) {
		sessionFactory.getCurrentSession().merge(TMC);
	}

	@Override
	public TrainingMaterialsCategory getTrainingMaterialsCategoryById(Integer TMCId) {
		return (TrainingMaterialsCategory) sessionFactory.getCurrentSession().get(TrainingMaterialsCategory.class, TMCId);
	}

	@SuppressWarnings("unchecked")
	public List<TrainingMaterialsCategory> getAllTrainingMaterialsCategory() {
		return sessionFactory.getCurrentSession()//
				.createQuery("from TrainingMaterialsCategory t")//
				.list();
	}

	@SuppressWarnings("unchecked")
	public List<TrainingMaterialsCategory> getAllFirstLevelTMC() {
		return sessionFactory.getCurrentSession()//
				.createQuery("from TrainingMaterialsCategory t where t.parentTMC IS NULL")//
				.list();
	}

	@SuppressWarnings("unchecked")
	public List<TrainingMaterialsCategory> getSecondLevelTMCByFirstLevelTMCId(Integer TMCId) {
		return sessionFactory.getCurrentSession()//
				.createQuery("from TrainingMaterialsCategory t where t.parentTMC.TMCId=?1")//
				.setParameter("1", TMCId)//
				.list();
	}

	@SuppressWarnings("unchecked")
	public List<TrainingMaterialsCategory> getAllFirstLevelTMCByModule(String module) {
		return sessionFactory.getCurrentSession()//
				.createQuery("from TrainingMaterialsCategory t where t.parentTMC IS NULL and t.module=?1")//
				.setParameter("1", module)//
				.list();
	}

	@SuppressWarnings("unchecked")
	public List<TrainingMaterialsCategory> getTMCByPage(int pageSize, int currentPage) {
		if(currentPage <= 0 || pageSize <= 0)
			return null;

		int startNum = pageSize * (currentPage-1);

		return  sessionFactory.getCurrentSession()//
				.createQuery("from TrainingMaterialsCategory t order by t.TMCId asc")//
				.setCacheable(true)//
				.setFirstResult(startNum)//
				.setMaxResults(pageSize)//
				.list();
	}

	@Override
	public Long getTotalTMCNum() {
		return (Long) sessionFactory.getCurrentSession()//
				.createQuery("select count(*) from TrainingMaterialsCategory")//
				.uniqueResult();
	}

	@SuppressWarnings("unchecked")
	public List<TrainingMaterialsCategory> getTMCByContentByPage(String searchContent, Integer pageSize,
			Integer currentPage) {
		if(currentPage <= 0 || pageSize <= 0)
			return null;

		int startNum = pageSize * (currentPage-1);

		return sessionFactory.getCurrentSession()//
				.createSQLQuery("select * from TrainingMaterialsCategory t "
						+ "where t.TMCName regexp replace(?1,' ','|') "
						+ "order by t.TMCId asc limit ?2, ?3")//
				.addEntity(TrainingMaterialsCategory.class)//
				.setCacheable(true)//
				.setParameter("1", searchContent)//
				.setParameter("2", startNum)//
				.setParameter("3", pageSize)//
				.list();
	}

	@Override
	public BigInteger getTMCNumByContent(String searchContent) {
		return (BigInteger) sessionFactory.getCurrentSession()//
				.createSQLQuery("select count(*) from TrainingMaterialsCategory t "
						+ "where t.TMCName regexp replace(?1,' ','|')")//
				.setParameter("1", searchContent)//
				.uniqueResult();
	}

	@SuppressWarnings("unchecked")
	public List<TrainingMaterialsCategory> getAllTMCByModule(String module, int pageSize, int currentPage) {
		if(currentPage <= 0 || pageSize <= 0)
			return null;

		int startNum = pageSize * (currentPage-1);
		return sessionFactory.getCurrentSession()//
				.createQuery("from TrainingMaterialsCategory t where t.module=?1")//
				.setParameter("1", module)//
				.setFirstResult(startNum)//
				.setMaxResults(pageSize)//
				.list();
	}

	@Override
	public Long getAllTMCNumByModule(String module) {
		return (Long) sessionFactory.getCurrentSession()//
				.createQuery("select count(*) from TrainingMaterialsCategory t where t.module=?1")//
				.setParameter("1", module)//
				.uniqueResult();
	}

	@SuppressWarnings("unchecked")
	public List<TrainingMaterialsCategory> getTMCByContentByModuleByPage(String content, String module, int pageSize,
			int currentPage) {
		if(currentPage <= 0 || pageSize <= 0)
			return null;

		int startNum = pageSize * (currentPage-1);

		return sessionFactory.getCurrentSession()//
				.createSQLQuery("select * from TrainingMaterialsCategory t "
						+ "where t.TMCName regexp replace(?1,' ','|') and t.module=?2 "
						+ "order by t.TMCId asc limit ?3, ?4")//
				.addEntity(TrainingMaterialsCategory.class)//
				.setCacheable(true)//
				.setParameter("1", content)//
				.setParameter("2", module)//
				.setParameter("3", startNum)//
				.setParameter("4", pageSize)//
				.list();
	}

	@Override
	public BigInteger getTMCNumByContentByModule(String content, String module) {
		return (BigInteger) sessionFactory.getCurrentSession()//
				.createSQLQuery("select count(*) from TrainingMaterialsCategory t "
						+ "where t.TMCName regexp replace(?1,' ','|') and t.module=?2")//
				.setParameter("1", content)//
				.setParameter("2", module)//
				.uniqueResult();
	}

}
