package com.wanda.dao;

import java.math.BigInteger;
import java.util.List;

import com.wanda.beans.Solution;
import com.wanda.beans.TrainingMaterialsCategory;

/**
 * 解决方案数据库操作
 * @author jinkejk
 *
 */
public interface SolutionDao {

	/**
	 * 根据id获取解决方案
	 */
	Solution getSolutionById(Integer solutionId);
       
    /**
     * 添加解决方案
     */
    void addSolution(Solution solution);
    
    /**
     * 根据id删除解决方案
     */
    void deleteSolutionById(Integer solutionId); 
      
    /**
     * 根据id删除解决方案
     */
    void addClickNumById(Integer solutionId);
    
    /**
     * 更新解决方案
     */
    void updateSolution(Solution solution);
    
    /**
     * 分页查询解决方案
     */
    List<Solution> getSolutionByPage(int pageSize, int currentPage);
    
    /**
     * 获取解决方案总个数
     * @return
     */
    Long getTotalSolutionNum();
    
    /**
     * 根据搜索内容分页查询解决方案
     */
    List<Solution> getSolutionByContentByPage(String searchContent, Integer pageSize, Integer currentPage);
    
    /**
     * 获取按内容搜索后的
     * 解决方案总个数
     * @return
     */
    BigInteger getTotalSolutionNumByContent(String searchContent);
    
    /**
     * @param num: 查询数量
     * 获取最近访问的条目标题
     */
    List<Solution> getLastVisitSolutionTitle(int num);
    
    /**
     * @param num: 查询数量
     * 获取热门条目标题
     */
    List<Solution> getHotVisitSolutionTitle(int num);
    
    /**
     * @param num: 查询数量
     * 获取最近添加解决方案
     */
    List<Solution> getLastSolutions(int num);
    
    /**
     * 根据类别分页获取解决方案
     */
    List<Solution> getSolutionsByCategory(TrainingMaterialsCategory category, int pageSize, int currentPage);
    
    /**
     * 根据类别分页获取解决方案的总个数
     */
     Long getSolutionsNumByCategory(TrainingMaterialsCategory category);
}
