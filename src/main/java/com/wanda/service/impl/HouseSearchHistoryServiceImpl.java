package com.wanda.service.impl;

import java.util.List;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.wanda.beans.HouseSearchHistory;
import com.wanda.dao.HouseSearchHistoryDao;
import com.wanda.service.HouseSearchHistoryService;

@Service("houseSearchHistoryService")
@Transactional
public class HouseSearchHistoryServiceImpl implements HouseSearchHistoryService {

	@Resource
	private HouseSearchHistoryDao houseSearchHistoryDao;
	
	@Override
	public HouseSearchHistory getHouseSearchHistoryById(Integer searchId) {
		return houseSearchHistoryDao.getHouseSearchHistoryById(searchId);
	}

	@Override
	public void addHouseSearchHistory(HouseSearchHistory houseSearchHistory) {
		houseSearchHistoryDao.addHouseSearchHistory(houseSearchHistory);
	}

	@Override
	public void deleteHouseSearchHistory(HouseSearchHistory houseSearchHistory) {
		houseSearchHistoryDao.deleteHouseSearchHistory(houseSearchHistory);
	}

}
