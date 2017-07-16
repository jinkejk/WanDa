package com.wanda.service.impl;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.wanda.beans.Solution;
import com.wanda.beans.SolutionSearchHistory;
import com.wanda.beans.TrainingMaterialsCategory;
import com.wanda.beans.User;
import com.wanda.dao.SolutionDao;
import com.wanda.service.SolutionSearchHistoryService;
import com.wanda.service.SolutionService;

@Service("solutionService")
@Transactional
public class SolutionServiceImpl implements SolutionService {

	@Resource
	private SolutionDao solutionDao;
	@Resource
	private SolutionSearchHistoryService solutionSearchHistoryService;

	@Transactional(propagation=Propagation.NOT_SUPPORTED,readOnly=true)
	public Solution getSolutionById(Integer solutionId) {
		return solutionDao.getSolutionById(solutionId);
	}

	public void addSolution(Solution solution) {
		solutionDao.addSolution(solution);

	}

	public void deleteSolutionById(Integer solutionId) {
		solutionDao.deleteSolutionById(solutionId);

	}

	public Solution VisitSolutionById(Integer solutionId) {
		Solution solution = solutionDao.getSolutionById(solutionId);
		solution.setClickNum(solution.getClickNum() + 1);
		solution.setVisitDate(new Date());
		return solutionDao.getSolutionById(solutionId);
	}

	@Transactional(propagation=Propagation.NOT_SUPPORTED,readOnly=true)
	public List<Solution> getSolutionByPage(Integer pageSize, Integer currentPage) {
		return solutionDao.getSolutionByPage(pageSize, currentPage);
	}

	@Transactional(propagation=Propagation.NOT_SUPPORTED,readOnly=true)
	public Long getTotalSolutionNum() {
		return solutionDao.getTotalSolutionNum();
	}

	public List<Solution> getSolutionByContentByPage(User user, String searchContent, Integer pageSize, Integer currentPage, int isNewContent) { 	
		List<Solution> solutions = solutionDao.getSolutionByContentByPage(searchContent, pageSize, currentPage);
		
		//在搜索历史中添加一条记录
		if(isNewContent == 1){
			SolutionSearchHistory solutionSearchHistory = new SolutionSearchHistory();
			solutionSearchHistory.setKeyword(searchContent);
			if(user != null)
				solutionSearchHistory.setUser(user);
			solutionSearchHistory.setResultNum(solutionDao.getTotalSolutionNumByContent(searchContent).intValue());
			solutionSearchHistory.setSearchDate(new Date());
			solutionSearchHistoryService.addSolutionSearchHistory(solutionSearchHistory);			
		}
		
		return solutions;
	}

	@Transactional(propagation=Propagation.NOT_SUPPORTED,readOnly=true)
	public BigInteger getTotalSolutionNumByContent(String searchContent) {
		return solutionDao.getTotalSolutionNumByContent(searchContent);
	}

	public void updateSolution(Solution solution) {
		solutionDao.updateSolution(solution);		
	}

	@Transactional(propagation=Propagation.NOT_SUPPORTED,readOnly=true)
	public List<Solution> getLastVisitSolutionTitle(int num) {
		return solutionDao.getLastVisitSolutionTitle(num);
	}

	@Transactional(propagation=Propagation.NOT_SUPPORTED,readOnly=true)
	public List<Solution> getHotVisitSolutionTitle(int num) {
		return solutionDao.getHotVisitSolutionTitle(num);
	}

	@Transactional(propagation=Propagation.NOT_SUPPORTED,readOnly=true)
	public List<Solution> getSolutionsByCategory(TrainingMaterialsCategory category, int pageSize, int currentPage) {
		return solutionDao.getSolutionsByCategory(category, pageSize, currentPage);
	}

	@Transactional(propagation=Propagation.NOT_SUPPORTED,readOnly=true)
	public List<Solution> getLastSolutions(int num) {
		return solutionDao.getLastSolutions(num);
	}

	@Transactional(propagation=Propagation.NOT_SUPPORTED,readOnly=true)
	public Long getSolutionsNumByCategory(TrainingMaterialsCategory category) {
		return solutionDao.getSolutionsNumByCategory(category);
	}

	@Override
	public void deleteSolutions(List<Integer> solutionIds) {
		for(int solutionId: solutionIds){
		    	solutionDao.deleteSolutionById(solutionId);
		}		
	}

}
