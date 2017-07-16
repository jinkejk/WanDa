package com.wanda.dao;

import com.wanda.beans.HouseSearchHistory;

/**
 * 户型搜索历史dao
 * @author jinkejk
 *
 */
public interface HouseSearchHistoryDao {

	//查找搜索记录
	HouseSearchHistory getHouseSearchHistoryById(Integer searchId);
	
	//添加搜索记录
	void addHouseSearchHistory(HouseSearchHistory houseSearchHistory);
	
	//删除搜索记录
	void deleteHouseSearchHistory(HouseSearchHistory houseSearchHistory);
}
