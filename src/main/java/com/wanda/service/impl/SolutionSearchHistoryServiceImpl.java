package com.wanda.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import com.wanda.beans.SolutionSearchHistory;
import com.wanda.dao.SolutionSearchHistoryDao;
import com.wanda.service.SolutionSearchHistoryService;

@Service("solutionSearchHistoryService")
@Transactional
public class SolutionSearchHistoryServiceImpl implements SolutionSearchHistoryService {
	
	@Resource
	private SolutionSearchHistoryDao solutionSearchHistoryDao;

	@Transactional(propagation=Propagation.NOT_SUPPORTED,readOnly=true)
	public List<SolutionSearchHistory> getHistoryByUserId(Integer userId) {
		return solutionSearchHistoryDao.getHistoryByUserId(userId);
	}
	
	@Transactional(propagation=Propagation.NOT_SUPPORTED,readOnly=true)
	public SolutionSearchHistory getHistoryById(Integer solutionSearchHistoryId) {
		return solutionSearchHistoryDao.getHistoryById(solutionSearchHistoryId);
	}

	public void addSolutionSearchHistory(SolutionSearchHistory solutionSearchHistory) {
		solutionSearchHistoryDao.addSolutionSearchHistory(solutionSearchHistory);
	}

	public void deleteSolutionSearchHistory(Integer solutionSearchHistoryId) {
		solutionSearchHistoryDao.deleteSolutionSearchHistory(solutionSearchHistoryId);
	}

	public void updateSolutionSearchHistory(SolutionSearchHistory solutionSearchHistory) {
		solutionSearchHistoryDao.updateSolutionSearchHistory(solutionSearchHistory);
	}

	@Transactional(propagation=Propagation.NOT_SUPPORTED,readOnly=true)
	public List<String> getLastSearchContent(int num) {
		return solutionSearchHistoryDao.getLastSearchContent(num);
	}

}
