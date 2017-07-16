package com.wanda.dao.impl;

import java.math.BigInteger;
import java.util.List;
import javax.annotation.Resource;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import com.wanda.beans.TrainingMaterial;
import com.wanda.beans.TrainingMaterialsCategory;
import com.wanda.dao.TrainingMaterialDao;

@Repository("trainingMaterialDao")
public class TrainingMaterialDaoImpl implements TrainingMaterialDao {

	@Resource
	private SessionFactory sessionFactory;

	@Override
	public void addTrainingMaterial(TrainingMaterial TM) {
		sessionFactory.getCurrentSession().persist(TM);
	}

	@Override
	public void deleteTrainingMaterial(TrainingMaterial TM) {
		sessionFactory.getCurrentSession().delete(TM);
	}

	@Override
	public void updateTrainingMaterial(TrainingMaterial TM) {
		sessionFactory.getCurrentSession().merge(TM);
	}

	@SuppressWarnings("unchecked")
	public List<TrainingMaterial> getTrainingMaterialByPage(int pageSize, int currentPage) {
		if(currentPage <= 0 || pageSize <= 0)
			return null;

		int startNum = pageSize * (currentPage-1);

		return sessionFactory.getCurrentSession()//
				.createQuery("from TrainingMaterial t order by t.createDate desc")//
				.setCacheable(true)//
				.setFirstResult(startNum)//
				.setMaxResults(pageSize)//
				.list();
	}

	@Override
	public Long getTotalTrainingMaterialNum() {
		return (Long) sessionFactory.getCurrentSession()//
				.createQuery("select count(*) from TrainingMaterial")//
				.uniqueResult();
	}

	@SuppressWarnings("unchecked")
	public List<TrainingMaterial> getTrainingMaterialByContentByPage(String searchContent, Integer pageSize,
			Integer currentPage) {
		if(currentPage <= 0 || pageSize <= 0)
			return null;

		int startNum = pageSize * (currentPage-1);

		return sessionFactory.getCurrentSession()//
				.createSQLQuery("select * from trainingMaterial t "
						+ "where t.title regexp replace(?1,' ','|') or t.remark regexp replace(?1,' ','|') "
						+ "order by t.createDate desc limit ?2, ?3")//
				.addEntity(TrainingMaterial.class)//
				.setCacheable(true)//
				.setParameter("1", searchContent)//
				.setParameter("2", startNum)//
				.setParameter("3", pageSize)//
				.list();
	}

	@Override
	public BigInteger getTotalTrainingMaterialNumByContent(String searchContent) {
		return (BigInteger) sessionFactory.getCurrentSession()//
				.createSQLQuery("select count(*) from trainingMaterial t "
						+ "where t.title regexp replace(?1,' ','|') or t.remark regexp replace(?1,' ','|') ")//
				.setParameter("1", searchContent)//
				.uniqueResult();	
	}

	@Override
	public TrainingMaterial getTrainingMaterialById(Integer TMId) {
		return (TrainingMaterial) sessionFactory.getCurrentSession().get(TrainingMaterial.class, TMId);
	}

	@SuppressWarnings("unchecked")
	public List<TrainingMaterial> getLastTrainingMaterial(int num) {
		return sessionFactory.getCurrentSession()//
				.createQuery("from TrainingMaterial t order by t.createDate desc")//
				.setMaxResults(num)//
				.list();
	}

	@SuppressWarnings("unchecked")
	public List<TrainingMaterial> getTrainingMaterialsByCategory(TrainingMaterialsCategory category, int pageSize,
			int currentPage) {
		if(currentPage <= 0 || pageSize <= 0)
			return null;

		int startNum = pageSize * (currentPage-1);

		//若分类是一级分类,查询该类及子类的解决方案
		if(category.getParentTMC() == null){
			return  sessionFactory.getCurrentSession()//
					.createQuery("from TrainingMaterial s "
							+ "where s.category.TMCId=?1 or s.category.parentTMC.TMCId=?1 order by s.createDate desc")//
					.setCacheable(true)//
					.setParameter("1", category.getTMCId())//
					.setFirstResult(startNum)//
					.setMaxResults(pageSize)//
					.list();			
		}else{
			return  sessionFactory.getCurrentSession()//
					.createQuery("from TrainingMaterial s "
							+ "where s.category.TMCId=?1 order by s.createDate desc")//
					.setCacheable(true)//
					.setParameter("1", category.getTMCId())//
					.setFirstResult(startNum)//
					.setMaxResults(pageSize)//
					.list();
		}
	}

	@Override
	public long getTMNumByCategory(TrainingMaterialsCategory category) {
		//若分类是一级分类,查询该类及子类的解决方案
		if(category.getParentTMC() == null){
			return  (long) sessionFactory.getCurrentSession()//
					.createQuery("select count(*) from TrainingMaterial s "
							+ "where s.category.TMCId=?1 or s.category.parentTMC.TMCId=?1")//
					.setCacheable(true)//
					.setParameter("1", category.getTMCId())//
					.uniqueResult();			
		}else{
			return  (long) sessionFactory.getCurrentSession()//
					.createQuery("select count(*) from TrainingMaterial s "
							+ "where s.category.TMCId=?1")//
					.setCacheable(true)//
					.setParameter("1", category.getTMCId())//
					.uniqueResult();
		}
	}

}
