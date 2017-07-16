package com.wanda.service;

import java.math.BigInteger;
import java.util.List;

import com.wanda.beans.Drawing;

public interface DrawingService{
	/**
	 * 根据id查找图纸
	 */
	Drawing getDrawingById(Integer id);
	
	/**
	 * 根据stringId查找图纸
	 */
	Drawing getDrawingByStringId(String stringId);
	
	/**
	 * 根据imgName查找不包括后缀
	 */
	Drawing getDrawingByImgName(String imgName);
	
	/**
	 * 根据fieldId查找
	 */
	Drawing getDrawingByFieldId(String fieldId);
	
	/**
	 * 为需要上传的图纸确定名称
	 */
	String createUploadDrawingStringId();
	
	/**
	 * 添加drawing
	 */
	void addDrawing(Drawing drawing);
	
	/**
	 * 根据id删除drawing
	 */
	void deleteDrawingById(Integer id);
	
	/**
	 * 删除为空的drawing
	 */
	void deleteEmptyDrawing();
	
	/**
	 * 根据stringId删除drawing
	 */
	void deleteDrawingByStringId(String stringId);
	
	/**
	 * 删除drawing
	 */
	void deleteDrawing(Drawing drawing);
	
	/**
	 * 更新drawing
	 */
	void updateDrawing(Drawing drawing);
	
	/**
	 * 分页获取图纸
	 */
	List<Drawing> getDrawingsByPage(int pageSize, int currentPage);
	
	/**
	 * 获取总共的图纸数量
	 */
	Long getTotalDrawingNum();
	
	/**
	 * 根据搜索内容获取图纸信息
	 */
	List<Drawing> getDrawingByContentByPage(String searchContent, Integer pageSize, Integer currentPage);
	 
	/**
     * 获取按内容搜索后的
     * 图纸总个数
     * @return
     */
    BigInteger getTotalDrawingNumByContent(String searchContent);
}
