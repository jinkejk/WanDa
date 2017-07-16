package com.wanda.dao.impl;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import com.wanda.beans.Drawing;
import com.wanda.dao.DrawingDao;

@Repository("drawingDao")
public class DrawingDaoImpl implements DrawingDao {

	@Resource
	private SessionFactory sessionFactory;
	
	public Drawing getDrawingById(Integer id) {
		return (Drawing) sessionFactory.getCurrentSession()//
				.get(Drawing.class, id);
	}

	public Drawing getDrawingByStringId(String stringId) {
		return (Drawing) sessionFactory.getCurrentSession()//
				.createQuery("from Drawing d where d.stringId=?1")//
				.setString("1", stringId)//
				.uniqueResult();
	}

	public void addDrawing(Drawing drawing) {
		sessionFactory.getCurrentSession().persist(drawing);

	}

	public void deleteDrawingById(Integer id) {
		sessionFactory.getCurrentSession().delete(
				sessionFactory.getCurrentSession().get(Drawing.class, id));

	}

	public void deleteDrawingByStringId(String stringId) {
		sessionFactory.getCurrentSession().delete(getDrawingByStringId(stringId));
	}

	public void deleteDrawing(Drawing drawing) {
		sessionFactory.getCurrentSession().delete(drawing);		
	}

	@SuppressWarnings("unchecked")
	public List<Drawing> getDrawingsByPage(int pageSize, int currentPage) {
		if(currentPage <= 0 || pageSize <= 0)
			return null;
		
		int startNum = pageSize * (currentPage-1);

		List<Drawing> drawings = new ArrayList<Drawing>();
		drawings = sessionFactory.getCurrentSession()//
				.createQuery("from Drawing d where d.drawingName != null order by d.alterDate desc")//
				.setFirstResult(startNum)//
				.setCacheable(true)//
				.setMaxResults(pageSize)//
				.list();

		return drawings;
	}

	public Long getTotalDrawingNum() {
		return (Long) sessionFactory.getCurrentSession()//
				.createQuery("select count(*) from Drawing d where d.drawingName != null")//
				.uniqueResult();
	}

	@SuppressWarnings("unchecked")
	public List<Drawing> getDrawingByContentByPage(String searchContent, Integer pageSize, Integer currentPage) {
		if(currentPage <= 0 || pageSize <= 0)
			return null;
		
		int startNum = pageSize * (currentPage-1);

		List<Drawing> drawings = new ArrayList<Drawing>();
		drawings = sessionFactory.getCurrentSession()//
				.createSQLQuery("select * from drawing d "
						+ "where d.drawingName regexp replace(?1,' ','|') or d.remark regexp replace(?1,' ','|') "
						+ "order by d.alterDate desc limit ?2, ?3")//
				.addEntity(Drawing.class)//
				.setParameter("1", searchContent)//
				.setParameter("2", startNum)//
				.setParameter("3", pageSize)//
				.setCacheable(true)//
				.list();

		return drawings;
	}

	public BigInteger getTotalDrawingNumByContent(String searchContent) {
		return (BigInteger) sessionFactory.getCurrentSession()//
				.createSQLQuery("select count(*) from drawing d "
						+ "where d.drawingName regexp replace(?1,' ','|') or d.remark regexp replace(?1,' ','|') ")//
				.setParameter("1", searchContent)//
				.uniqueResult();
	}

	public String getLastDrawingStringId() {
		return (String) sessionFactory.getCurrentSession()//
				.createQuery("select d.stringId from Drawing d"
						+ " order by d.uploadDate desc")//
				.setFirstResult(0)//
				.setMaxResults(1)
				.uniqueResult();
	}

	public void updateDrawing(Drawing drawing) {
		sessionFactory.getCurrentSession().update(drawing);		
	}

	@SuppressWarnings("unchecked")
	public List<Drawing> getEmptyDrawings() {
		List<Drawing> drawings = new ArrayList<Drawing>();
		drawings = sessionFactory.getCurrentSession()//
				.createQuery("from Drawing d where d.drawingName = null ")//
				.setCacheable(true)//
				.list();
		return drawings;
	}

	@Override
	public Drawing getDrawingByImgName(String imgName) {
		imgName += "%";
		return (Drawing) sessionFactory.getCurrentSession()//
				.createQuery("from Drawing d where d.imgName like ?1")//
				.setString("1", imgName)//
				.uniqueResult();
	}

	@Override
	public Drawing getDrawingByFieldId(String fieldId) {
		return (Drawing) sessionFactory.getCurrentSession()//
				.createQuery("from Drawing d where d.fieldId=?1")//
				.setString("1", fieldId)//
				.uniqueResult();
	}

}
