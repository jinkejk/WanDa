package com.wanda.dao.impl;

import java.math.BigInteger;
import java.util.List;
import javax.annotation.Resource;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import com.wanda.beans.SignMaterial;
import com.wanda.beans.TrainingMaterialsCategory;
import com.wanda.dao.SignMaterialDao;

@Repository("signMaterialDao")
public class SignMaterialDaoImpl implements SignMaterialDao {

	@Resource
	private SessionFactory sessionFactory;

	@Override
	public void addSignMaterial(SignMaterial SM) {
		sessionFactory.getCurrentSession().persist(SM);
	}

	@Override
	public void deleteSignMaterial(SignMaterial SM) {
		sessionFactory.getCurrentSession().delete(SM);
	}

	@Override
	public void updateSignMaterial(SignMaterial SM) {
		sessionFactory.getCurrentSession().merge(SM);
	}

	@SuppressWarnings("unchecked")
	public List<SignMaterial> getSignMaterialByPage(int pageSize, int currentPage) {
		if(currentPage <= 0 || pageSize <= 0)
			return null;

		int startNum = pageSize * (currentPage-1);

		return sessionFactory.getCurrentSession()//
				.createQuery("from SignMaterial s order by s.createDate desc")//
				.setCacheable(true)//
				.setFirstResult(startNum)//
				.setMaxResults(pageSize)//
				.list();
	}

	@Override
	public Long getTotalSignMaterialNum() {
		return (Long) sessionFactory.getCurrentSession()//
				.createQuery("select count(*) from SignMaterial")//
				.uniqueResult();
	}

	@SuppressWarnings("unchecked")
	public List<SignMaterial> getSignMaterialByContentByPage(String searchContent, Integer pageSize,
			Integer currentPage) {
		if(currentPage <= 0 || pageSize <= 0)
			return null;

		int startNum = pageSize * (currentPage-1);

		return sessionFactory.getCurrentSession()//
				.createSQLQuery("select * from signMaterial s "
						+ "where s.title regexp replace(?1,' ','|') or s.remark regexp replace(?1,' ','|') "
						+ "order by s.createDate desc limit ?2, ?3")//
				.addEntity(SignMaterial.class)//
				.setCacheable(true)//
				.setParameter("1", searchContent)//
				.setParameter("2", startNum)//
				.setParameter("3", pageSize)//
				.list();
	}

	@Override
	public BigInteger getTotalSignMaterialNumByContent(String searchContent) {
		return (BigInteger) sessionFactory.getCurrentSession()//
				.createSQLQuery("select count(*) from signMaterial s "
						+ "where s.title regexp replace(?1,' ','|') or s.remark regexp replace(?1,' ','|') ")//
				.setParameter("1", searchContent)//
				.uniqueResult();
	}

	@Override
	public SignMaterial getSignMaterialById(Integer SMId) {
		return (SignMaterial) sessionFactory.getCurrentSession().get(SignMaterial.class, SMId);
	}

	@SuppressWarnings("unchecked")
	public List<SignMaterial> getLastSignMaterial(int num) {
		return sessionFactory.getCurrentSession()//
				.createQuery("from SignMaterial s order by s.createDate desc")//
				.setMaxResults(num)//
				.list();
	}

	@Override
	public SignMaterial getSignMaterialByFileName(String signFile) {
		return (SignMaterial) sessionFactory.getCurrentSession()//
				.createQuery("from SignMaterial s where s.signFile=?1")//
				.setParameter("1", signFile)//
				.uniqueResult();
	}

	@SuppressWarnings("unchecked")
	public List<SignMaterial> getSignMaterialsByCategory(TrainingMaterialsCategory category, int pageSize,
			int currentPage) {
		if(currentPage <= 0 || pageSize <= 0)
			return null;

		int startNum = pageSize * (currentPage-1);

		//若分类是一级分类,查询该类及子类的解决方案
		if(category.getParentTMC() == null){
			return  sessionFactory.getCurrentSession()//
					.createQuery("from SignMaterial s "
							+ "where s.category.TMCId=?1 or s.category.parentTMC.TMCId=?1 order by s.createDate desc")//
					.setCacheable(true)//
					.setParameter("1", category.getTMCId())//
					.setFirstResult(startNum)//
					.setMaxResults(pageSize)//
					.list();			
		}else{
			return  sessionFactory.getCurrentSession()//
					.createQuery("from SignMaterial s "
							+ "where s.category.TMCId=?1 order by s.createDate desc")//
					.setCacheable(true)//
					.setParameter("1", category.getTMCId())//
					.setFirstResult(startNum)//
					.setMaxResults(pageSize)//
					.list();
		}
	}

	@Override
	public long getSignMaterialNumByCategory(TrainingMaterialsCategory category) {
		//若分类是一级分类,查询该类及子类的解决方案
		if(category.getParentTMC() == null){
			return  (long) sessionFactory.getCurrentSession()//
					.createQuery("select count(*) from SignMaterial s "
							+ "where s.category.TMCId=?1 or s.category.parentTMC.TMCId=?1")//
					.setCacheable(true)//
					.setParameter("1", category.getTMCId())//
					.uniqueResult();			
		}else{
			return  (long) sessionFactory.getCurrentSession()//
					.createQuery("select count(*) from SignMaterial s "
							+ "where s.category.TMCId=?1")//
					.setCacheable(true)//
					.setParameter("1", category.getTMCId())//
					.uniqueResult();
		}
	}
}
