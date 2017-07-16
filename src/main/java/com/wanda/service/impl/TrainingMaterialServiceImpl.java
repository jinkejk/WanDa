package com.wanda.service.impl;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import com.wanda.beans.TrainingMaterial;
import com.wanda.beans.TrainingMaterialsCategory;
import com.wanda.dao.TrainingMaterialDao;
import com.wanda.service.TrainingMaterialService;
import com.wanda.util.UtilCommon;

@Service("trainingMaterialService")
@Transactional
public class TrainingMaterialServiceImpl implements TrainingMaterialService {

	@Resource
	private TrainingMaterialDao trainingMaterialDao;
	
	@Override
	public void addTrainingMaterial(TrainingMaterial TM) {
		trainingMaterialDao.addTrainingMaterial(TM);
	}

	//删除培训资料同时还要一起删除对应的ppt资料
	public void deleteTrainingMaterial(TrainingMaterial TM) {
		String path = "E:\\apache_tomcat_7.0.57\\webapps\\trainingMaterialPpt\\";
		String pptName = TM.getPptName();
		if(pptName != null)
			UtilCommon.deleteFile(path + pptName);
		//UtilCommon.deleteFile(path + pptName.substring(0,pptName.lastIndexOf(".")) + ".swf");

		trainingMaterialDao.deleteTrainingMaterial(TM);		
	}

	@Override
	public void updateTrainingMaterial(TrainingMaterial TM) {
		trainingMaterialDao.updateTrainingMaterial(TM);
	}

	@Transactional(propagation=Propagation.NOT_SUPPORTED,readOnly=true)
	public List<TrainingMaterial> getTrainingMaterialByPage(int pageSize, int currentPage) {
		return trainingMaterialDao.getTrainingMaterialByPage(pageSize, currentPage);
	}

	@Transactional(propagation=Propagation.NOT_SUPPORTED,readOnly=true)
	public Long getTotalTrainingMaterialNum() {
		return trainingMaterialDao.getTotalTrainingMaterialNum();
	}

	@Transactional(propagation=Propagation.NOT_SUPPORTED,readOnly=true)
	public List<TrainingMaterial> getTrainingMaterialByContentByPage(String searchContent, Integer pageSize,
			Integer currentPage) {
		return trainingMaterialDao.getTrainingMaterialByContentByPage(searchContent, pageSize, currentPage);
	}

	@Transactional(propagation=Propagation.NOT_SUPPORTED,readOnly=true)
	public BigInteger getTotalTrainingMaterialNumByContent(String searchContent) {
		return trainingMaterialDao.getTotalTrainingMaterialNumByContent(searchContent);
	}

	@Transactional(propagation=Propagation.NOT_SUPPORTED,readOnly=true)
	public TrainingMaterial getTrainingMaterialById(Integer TMId) {
		return trainingMaterialDao.getTrainingMaterialById(TMId);
	}

	@Override
	public TrainingMaterial visitTrainingMaterialById(Integer TMId) {
		TrainingMaterial TM = trainingMaterialDao.getTrainingMaterialById(TMId);
		
		//添加点击次数，更新访问日期
		TM.setClickNum(TM.getClickNum() + 1);
		TM.setVisitDate(new Date());
		trainingMaterialDao.updateTrainingMaterial(TM);
		
		return TM;
	}

	@Transactional(propagation=Propagation.NOT_SUPPORTED,readOnly=true)
	public List<TrainingMaterial> getLastTrainingMaterial(int num) {
		return trainingMaterialDao.getLastTrainingMaterial(num);
	}

	@Transactional(propagation=Propagation.NOT_SUPPORTED,readOnly=true)
	public List<TrainingMaterial> getTrainingMaterialsByCategory(TrainingMaterialsCategory category, int pageSize,
			int currentPage) {
		return trainingMaterialDao.getTrainingMaterialsByCategory(category, pageSize, currentPage);
	}

	@Transactional(propagation=Propagation.NOT_SUPPORTED,readOnly=true)
	public long getTMNumByCategory(TrainingMaterialsCategory category) {
		return trainingMaterialDao.getTMNumByCategory(category);
	}

	@Override
	public void deleteTrainingMaterials(List<Integer> TMIds) {
		for(int TMId: TMIds){
			trainingMaterialDao.deleteTrainingMaterial(
					trainingMaterialDao.getTrainingMaterialById(TMId));
		}		
	}

}
