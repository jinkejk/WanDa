package com.wanda.service;

import java.math.BigInteger;
import java.util.List;

import com.wanda.beans.Solution;
import com.wanda.beans.TrainingMaterialsCategory;
import com.wanda.beans.User;

/**
 * 解决方案数据操作服务类
 * @author jinkejk
 *
 */
public interface SolutionService {
	
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
     * 更新解决方案
     */
    void updateSolution(Solution solution);
    
    /**
     * 用户根据id访问解决方案
     * 访问次数加一
     * 更新访问时间
     */
    Solution VisitSolutionById(Integer solutionId);

    /**
     * 分页查询
     * @param pageSize
     * @param currentPage
     * @return
     */
    List<Solution> getSolutionByPage(Integer pageSize, Integer currentPage);
    
    /**
     * 获取解决方案总个数
     * @return
     */
    Long getTotalSolutionNum();
    
    /**
     * 根据搜索内容分页查询解决方案
     * @param searchContent
     * @param pageSize
     * @param currentPage
     * @param isNewContent 是否需要记录，有的翻页操作不记录
     * @return
     */
    List<Solution> getSolutionByContentByPage(User user, String searchContent, Integer pageSize, Integer currentPage, int isNewContent);
    
    /**
     * 根据搜索内容分页查询解决方案
     * @param searchContent
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
     
     /**
      * 批量删除解决方案
      */
     void deleteSolutions(List<Integer> solutionIds);
}
