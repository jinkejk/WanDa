package com.wanda.dao;

import java.math.BigInteger;
import java.util.List;

import com.wanda.beans.TrainingMaterial;
import com.wanda.beans.TrainingMaterialsCategory;

/**
 * 训练资料类别的管理
 * @author jinkejk
 *
 */
public interface TrainingMaterialsCategoryDao {

	/**
	 * 添加
	 */
	void addTrainingMaterialsCategory(TrainingMaterialsCategory TMC);
	
	/**
	 * 删除
	 */
	void deleteTrainingMaterialsCategoryById(Integer TMCId);
	
	/**
	 * 修改
	 */
	void updateTrainingMaterialsCategory(TrainingMaterialsCategory TMC);
	
	/**
	 * 通过id查找
	 */
	TrainingMaterialsCategory getTrainingMaterialsCategoryById(Integer TMCId);
	
	/**
	 * 查找所有分类
	 */
	List<TrainingMaterialsCategory> getAllTrainingMaterialsCategory();
	
	/**
	 * 查找所有一级分类
	 */
	List<TrainingMaterialsCategory> getAllFirstLevelTMC();
	
	/**
	 * 查找某个模块的所有一级分类
	 */
	List<TrainingMaterialsCategory> getAllFirstLevelTMCByModule(String module);	
	
	/**
	 * 查找一级分类下的二级分类
	 */
	List<TrainingMaterialsCategory> getSecondLevelTMCByFirstLevelTMCId(Integer TMCId);
	
	/**
	 * 分页查找所有分类
	 */
	List<TrainingMaterialsCategory> getTMCByPage(int pageSize, int currentPage);

	/**
	 * 分类的总个数
	 */
	Long getTotalTMCNum();
	
	/**
	 * 根据内容分页搜索训练资料
	 */
    List<TrainingMaterialsCategory> getTMCByContentByPage(String searchContent, Integer pageSize, Integer currentPage);

	/**
	 * 根据内容分页搜索训练资料的总个数
	 */
    BigInteger getTMCNumByContent(String searchContent);
    
    /**
	 * 查找某个模块的所有分类
	 */
	List<TrainingMaterialsCategory> getAllTMCByModule(String module, int pageSize, int currentPage);
	
	/**
	 * 查找某个模块的所有分类数量
	 */
	Long getAllTMCNumByModule(String module);
	
	/**
	 * 按模块和内容查找分类
	 */
	List<TrainingMaterialsCategory> getTMCByContentByModuleByPage(String content,String module, int pageSize, int currentPage);
	
	/**
	 * 按模块和内容查找分类的总数量
	 */
	BigInteger getTMCNumByContentByModule(String content,String module);
}
