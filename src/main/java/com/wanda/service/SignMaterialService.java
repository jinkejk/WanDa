package com.wanda.service;

import java.math.BigInteger;
import java.util.List;
import com.wanda.beans.SignMaterial;
import com.wanda.beans.TrainingMaterialsCategory;

public interface SignMaterialService {
	/**
	 * 添加签批资料
	 */
	void addSignMaterial(SignMaterial SM);
	
	/**
	 * 删除签批资料
	 */
	void deleteSignMaterial(SignMaterial SM);
	
	/**
	 * 更新签批资料
	 */
	void updateSignMaterial(SignMaterial SM);
	
	/**
	 * 分页查找签批资料
	 */
	List<SignMaterial> getSignMaterialByPage(int pageSize, int currentPage);
	
	/**
	 * 获得签批资料的总个数
	 */
	Long getTotalSignMaterialNum();
	
	/**
	 * 根据内容分页搜索签批资料
	 */
    List<SignMaterial> getSignMaterialByContentByPage(String searchContent, Integer pageSize, Integer currentPage);

	/**
	 * 根据内容分页搜索签批资料的总个数
	 */
    BigInteger getTotalSignMaterialNumByContent(String searchContent);

	/**
	 * 根据id查找签批资料
	 */
    SignMaterial getSignMaterialById(Integer SMId);
    
    /**
	 * 查找最新上传的签批资料资料
	 */
    List<SignMaterial> getLastSignMaterial(int num);
    
    /**
     * 增加下载次数
     */
    void addSignMaterialDownloadNum(Integer SMId);
    
    /**
     * 根据signFile获取签批资料
     */
    SignMaterial getSignMaterialByFileName(String signFile);
    
    /**
     * 根据类别分页获取签批资料
     */
    List<SignMaterial> getSignMaterialsByCategory(TrainingMaterialsCategory category, int pageSize, int currentPage);
    
    /**
     * 获取该类别的资料数量
     */
    long getSignMaterialNumByCategory(TrainingMaterialsCategory category);
    
    /**
     * 批量删除签批资料
     */
    void deleteSignMaterials(List<Integer> SMIds);
}
