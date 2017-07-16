package com.wanda.dao;

import java.math.BigInteger;
import java.util.List;
import com.wanda.beans.TrainingMaterial;
import com.wanda.beans.TrainingMaterialsCategory;

/**
 * 训练资料Dao
 * @author jinkejk
 *
 */
public interface TrainingMaterialDao {

	/**
	 * 添加训练资料
	 */
	void addTrainingMaterial(TrainingMaterial TM);
	
	/**
	 * 删除训练资料
	 */
	void deleteTrainingMaterial(TrainingMaterial TM);
	
	/**
	 * 更新资料
	 */
	void updateTrainingMaterial(TrainingMaterial TM);
	
	/**
	 * 分页查找训练资料
	 */
	List<TrainingMaterial> getTrainingMaterialByPage(int pageSize, int currentPage);
	
	/**
	 * 获得训练资料的总个数
	 */
	Long getTotalTrainingMaterialNum();
	
	/**
	 * 根据内容分页搜索训练资料
	 */
    List<TrainingMaterial> getTrainingMaterialByContentByPage(String searchContent, Integer pageSize, Integer currentPage);

	/**
	 * 根据内容分页搜索训练资料的总个数
	 */
    BigInteger getTotalTrainingMaterialNumByContent(String searchContent);

	/**
	 * 根据id查找训练资料
	 */
    TrainingMaterial getTrainingMaterialById(Integer TMId);
    
    /**
	 * 查找训练最新的上传资料
	 */
    List<TrainingMaterial> getLastTrainingMaterial(int num);
    
    /**
     * 根据类别分页获取训练资料
     */
    List<TrainingMaterial> getTrainingMaterialsByCategory(TrainingMaterialsCategory category, int pageSize, int currentPage);
    
    /**
     * 根据类别分页获取训练资料数量
     */
    long getTMNumByCategory(TrainingMaterialsCategory category);
}
