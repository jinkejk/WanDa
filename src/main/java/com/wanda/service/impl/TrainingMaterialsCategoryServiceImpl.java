package com.wanda.service.impl;

import java.math.BigInteger;
import java.util.List;
import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.wanda.beans.TrainingMaterialsCategory;
import com.wanda.dao.TrainingMaterialsCategoryDao;
import com.wanda.service.TrainingMaterialsCategoryService;

@Service("trainingMaterialsCategoryService")
@Transactional
public class TrainingMaterialsCategoryServiceImpl implements TrainingMaterialsCategoryService {
	@Resource
	private TrainingMaterialsCategoryDao trainingMaterialsCategoryDao;
	
	@Override
	public void addTrainingMaterialsCategory(TrainingMaterialsCategory TMC) {
		trainingMaterialsCategoryDao.addTrainingMaterialsCategory(TMC);
	}

	@Override
	public void deleteTrainingMaterialsCategoryById(Integer TMCId) {
		trainingMaterialsCategoryDao.deleteTrainingMaterialsCategoryById(TMCId);
	}

	@Override
	public void updateTrainingMaterialsCategory(TrainingMaterialsCategory TMC) {
		trainingMaterialsCategoryDao.updateTrainingMaterialsCategory(TMC);
	}

	@Transactional(propagation=Propagation.NOT_SUPPORTED,readOnly=true)
	public TrainingMaterialsCategory getTrainingMaterialsCategoryById(Integer TMCId) {
		return trainingMaterialsCategoryDao.getTrainingMaterialsCategoryById(TMCId);
	}

	@Transactional(propagation=Propagation.NOT_SUPPORTED,readOnly=true)
	public List<TrainingMaterialsCategory> getAllTrainingMaterialsCategory() {
		return trainingMaterialsCategoryDao.getAllTrainingMaterialsCategory();
	}

	@Transactional(propagation=Propagation.NOT_SUPPORTED,readOnly=true)
	public List<TrainingMaterialsCategory> getAllFirstLevelTMC() {
		return trainingMaterialsCategoryDao.getAllFirstLevelTMC();
	}

	@Transactional(propagation=Propagation.NOT_SUPPORTED,readOnly=true)
	public List<TrainingMaterialsCategory> getSecondLevelTMCByFirstLevelTMCId(Integer TMCId) {
		return trainingMaterialsCategoryDao.getSecondLevelTMCByFirstLevelTMCId(TMCId);
	}

	/**
	 * 获取二级分类的json数据
	 * 保存TMCId和TMCName两个属性
	 */
	@Transactional(propagation=Propagation.NOT_SUPPORTED,readOnly=true)
	public String getSecondLevelTMCJsonByFirstLevelTMCId(Integer TMCId) {
		//获取该一级菜单下的二级菜单
		List<TrainingMaterialsCategory> tmcs = getSecondLevelTMCByFirstLevelTMCId(TMCId);
		
		if(tmcs==null || tmcs.size()==0){
			return null;
		}
		
		//分装为jsonArray
		JsonArray jsonArray = new JsonArray();
		for(TrainingMaterialsCategory tmc: tmcs){
			String jsonStr = "{\"TMCId\":\""+tmc.getTMCId()+"\",\"TMCName\":\""+tmc.getTMCName()+"\"}";
			JsonObject object = new JsonParser().parse(jsonStr).getAsJsonObject();
			jsonArray.add(object);
		}

		return jsonArray.toString();
	}
	
	/**
	 * 获取一级分类的json数据
	 * 保存TMCId和TMCName两个属性
	 */
	@Transactional(propagation=Propagation.NOT_SUPPORTED,readOnly=true)
	public String getFirstLevelTMCJsonByFModule(String module) {
		//获取该一级菜单下的二级菜单
		List<TrainingMaterialsCategory> tmcs = getAllFirstLevelTMCByModule(module);
		
		if(tmcs==null || tmcs.size()==0){
			return null;
		}
		
		//分装为jsonArray
		JsonArray jsonArray = new JsonArray();
		for(TrainingMaterialsCategory tmc: tmcs){
			String jsonStr = "{\"TMCId\":\""+tmc.getTMCId()+"\",\"TMCName\":\""+tmc.getTMCName()+"\"}";
			JsonObject object = new JsonParser().parse(jsonStr).getAsJsonObject();
			jsonArray.add(object);
		}
		
		return jsonArray.toString();
	}

	@Transactional(propagation=Propagation.NOT_SUPPORTED,readOnly=true)
	public List<TrainingMaterialsCategory> getAllFirstLevelTMCByModule(String module) {
		return trainingMaterialsCategoryDao.getAllFirstLevelTMCByModule(module);
	}

	@Transactional(propagation=Propagation.NOT_SUPPORTED,readOnly=true)
	public List<TrainingMaterialsCategory> getTMCByPage(int pageSize, int currentPage) {
		return trainingMaterialsCategoryDao.getTMCByPage(pageSize, currentPage);
	}

	@Transactional(propagation=Propagation.NOT_SUPPORTED,readOnly=true)
	public Long getTotalTMCNum() {
		return trainingMaterialsCategoryDao.getTotalTMCNum();
	}

	@Transactional(propagation=Propagation.NOT_SUPPORTED,readOnly=true)
	public List<TrainingMaterialsCategory> getTMCByContentByPage(String searchContent, Integer pageSize,
			Integer currentPage) {
		return trainingMaterialsCategoryDao.getTMCByContentByPage(searchContent, pageSize, currentPage);
	}

	@Transactional(propagation=Propagation.NOT_SUPPORTED,readOnly=true)
	public BigInteger getTMCNumByContent(String searchContent) {
		return trainingMaterialsCategoryDao.getTMCNumByContent(searchContent);
	}

	@Transactional(propagation=Propagation.NOT_SUPPORTED,readOnly=true)
	public List<TrainingMaterialsCategory> getAllTMCByModule(String module, int pageSize, int currentPage) {
		return trainingMaterialsCategoryDao.getAllTMCByModule(module, pageSize, currentPage);
	}

	@Transactional(propagation=Propagation.NOT_SUPPORTED,readOnly=true)
	public Long getAllTMCNumByModule(String module) {
		return trainingMaterialsCategoryDao.getAllTMCNumByModule(module);
	}

	@Transactional(propagation=Propagation.NOT_SUPPORTED,readOnly=true)
	public List<TrainingMaterialsCategory> getTMCByContentByModuleByPage(String content, String module, int pageSize,
			int currentPage) {
		return trainingMaterialsCategoryDao.getTMCByContentByModuleByPage(content, module, pageSize, currentPage);
				
	}
	
	@Transactional(propagation=Propagation.NOT_SUPPORTED,readOnly=true)
	public BigInteger getTMCNumByContentByModule(String content, String module) {
		return trainingMaterialsCategoryDao.getTMCNumByContentByModule(content, module);
	}

}
