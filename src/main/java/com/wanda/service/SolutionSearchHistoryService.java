package com.wanda.service;

import java.util.List;

import com.wanda.beans.SolutionSearchHistory;

public interface SolutionSearchHistoryService {

	/**
	 * 根据userId获取搜索历史
	 */
	List<SolutionSearchHistory> getHistoryByUserId(Integer userId);
	
	/**
	 * 根据SolutionSearchHistoryId获取搜索历史
	 */
	SolutionSearchHistory getHistoryById(Integer solutionSearchHistoryId);
	
	/**
	 * 添加搜索历史
	 */
	void addSolutionSearchHistory(SolutionSearchHistory solutionSearchHistory);
	
	/**
	 * 根据id删除搜索历史
	 */
    void deleteSolutionSearchHistory(Integer solutionSearchHistoryId);
    
    /**
	 * 跟新搜索历史
	 */
	void updateSolutionSearchHistory(SolutionSearchHistory solutionSearchHistory);
	
	/**
	 * @param num: 查询个数
	 * 最近查询关键字
	 */
	List<String> getLastSearchContent(int num);
}
