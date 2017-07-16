package com.wanda.service;

import java.util.List;

import com.wanda.beans.HouseSearchHistory;

/**
 * 户型搜索历史
 * @author jinkejk
 *
 */
public interface HouseSearchHistoryService {
	
	//查找搜索记录
	HouseSearchHistory getHouseSearchHistoryById(Integer searchId);

	//添加搜索记录
	void addHouseSearchHistory(HouseSearchHistory houseSearchHistory);

	//删除搜索记录
	void deleteHouseSearchHistory(HouseSearchHistory houseSearchHistory);	
	
}
