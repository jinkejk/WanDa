package com.wanda.service.impl;

import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.wanda.beans.Drawing;
import com.wanda.dao.DrawingDao;
import com.wanda.service.DrawingService;

@Service("drawingService")
@Transactional
public class DrawingServiceImpl implements DrawingService {
	
	@Resource
	private DrawingDao drawingDao;
	
	@Transactional(propagation=Propagation.NOT_SUPPORTED,readOnly=true)
	public Drawing getDrawingById(Integer id) {
		return drawingDao.getDrawingById(id);
	}

	@Transactional(propagation=Propagation.NOT_SUPPORTED,readOnly=true)
	public Drawing getDrawingByStringId(String stringId) {
		return drawingDao.getDrawingByStringId(stringId);
	}
	
	public void addDrawing(Drawing drawing) {
		drawingDao.addDrawing(drawing);
	}

	public void deleteDrawingById(Integer id) {
		drawingDao.deleteDrawingById(id);

	}

	public void deleteDrawingByStringId(String stringId) {
		drawingDao.deleteDrawingByStringId(stringId);
	}

	public void deleteDrawing(Drawing drawing) {
		drawingDao.deleteDrawing(drawing);
	}

	@Transactional(propagation=Propagation.NOT_SUPPORTED,readOnly=true)
	public List<Drawing> getDrawingsByPage(int pageSize, int currentPage) {
		return drawingDao.getDrawingsByPage(pageSize, currentPage);
	}

	@Transactional(propagation=Propagation.NOT_SUPPORTED,readOnly=true)
	public Long getTotalDrawingNum() {
		return drawingDao.getTotalDrawingNum();
	}

	@Transactional(propagation=Propagation.NOT_SUPPORTED,readOnly=true)
	public List<Drawing> getDrawingByContentByPage(String searchContent, Integer pageSize, Integer currentPage) {
		return drawingDao.getDrawingByContentByPage(searchContent, pageSize, currentPage);
	}

	@Transactional(propagation=Propagation.NOT_SUPPORTED,readOnly=true)
	public BigInteger getTotalDrawingNumByContent(String searchContent) {
		return drawingDao.getTotalDrawingNumByContent(searchContent);
	}

	@Transactional(propagation=Propagation.NOT_SUPPORTED,readOnly=true)
	public String createUploadDrawingStringId() {
		//获取最新的stringId
		String stringId = drawingDao.getLastDrawingStringId();
		
		//判断是否是同一天的
		SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMdd");  
        Date date = new Date();
        String today = sdf.format(date);
        
        if(stringId==null || !stringId.substring(1, 9).equals(today)){
        	//当天的第一个
        	stringId = "D" + today + "S00001";
        }else if(stringId!=null && stringId.substring(1, 9).equals(today)){
        	//同一天
        	int num = Integer.parseInt(stringId.substring(10, stringId.length()));
        	//到达当天上限
        	if(num ==99999)
        	    return null;
        	//数组前面补0
        	stringId = "D" + today + "S" + String.format("%5d", num+1).replace(" ", "0");
        }
		return stringId;
	}

	public void updateDrawing(Drawing drawing) {
		drawingDao.updateDrawing(drawing);
	}

	public void deleteEmptyDrawing() {
		List<Drawing> drawings = drawingDao.getEmptyDrawings();
		
		for(Drawing drawing: drawings){
			drawingDao.deleteDrawing(drawing);
		}
		
	}

	@Transactional(propagation=Propagation.NOT_SUPPORTED,readOnly=true)
	public Drawing getDrawingByImgName(String imgName) {
		return drawingDao.getDrawingByImgName(imgName);
	}

	@Transactional(propagation=Propagation.NOT_SUPPORTED,readOnly=true)
	public Drawing getDrawingByFieldId(String fieldId) {
		return drawingDao.getDrawingByFieldId(fieldId);
	}

}
