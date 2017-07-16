package com.wanda.service.impl;

import java.math.BigInteger;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.wanda.beans.House;
import com.wanda.beans.HouseSearchHistory;
import com.wanda.beans.User;
import com.wanda.dao.HouseDao;
import com.wanda.service.HouseSearchHistoryService;
import com.wanda.service.HouseService;

@Service("houseService")
@Transactional
public class HouseServiceImpl implements HouseService {

	@Resource
	private HouseDao houseDao;
	@Resource
	private HouseSearchHistoryService houseSearchHistoryService;

	@Transactional(propagation=Propagation.NOT_SUPPORTED,readOnly=true)
	public House getHouseById(Integer houseId) {
		return houseDao.getHouseById(houseId);
	}

	//用户点击获取house，增加house的点击次数，增加查询记录
	public House visitHouseById(Integer houseId, User user){
		House house = houseDao.getHouseById(houseId);
		house.setAttention(house.getAttention() + 1);

		HouseSearchHistory houseSearchHistory = new HouseSearchHistory();
		houseSearchHistory.setHouse(house);
		houseSearchHistory.setUser(user);
		houseSearchHistory.setSearchDate(new Date());
		houseSearchHistoryService.addHouseSearchHistory(houseSearchHistory);
		return house;
	}

	@Transactional(propagation=Propagation.NOT_SUPPORTED,readOnly=true)
	public List<House> getHousesByPage(int pageSize, int currentPage) {
		return houseDao.getHousesByPage(pageSize, currentPage);
	}

	@Override
	public void deleteHouseById(Integer houseId) {
		houseDao.deleteHouseById(houseId);
	}

	@Override
	public void addHouse(House house) {
		houseDao.addHouse(house);
	}

	@Override
	public void updateHouse(House house) {
		houseDao.updateHouse(house);
	}

	@Transactional(propagation=Propagation.NOT_SUPPORTED,readOnly=true)
	public Long getTotalHouseNum() {
		return houseDao.getTotalHouseNum();
	}

	@Transactional(propagation=Propagation.NOT_SUPPORTED,readOnly=true)
	public List<House> getHousesByContentByPage(String searchContent, int pageSize, int currentPage) {
		return houseDao.getHousesByContentByPage(searchContent, pageSize, currentPage);
	}

	@Transactional(propagation=Propagation.NOT_SUPPORTED,readOnly=true)
	public BigInteger getTotalHouseNumByContent(String searchContent) {
		return houseDao.getTotalHouseNumByContent(searchContent);
	}

	@Transactional(propagation=Propagation.NOT_SUPPORTED,readOnly=true)
	public String getAllLPName() {
		List<String> names = houseDao.getAllLPName();

		JsonArray jsonArray = new JsonArray();
		for(String name: names){
			String jsonStr = "{\"LPName\":\"" + name + "\"}";
//			System.out.println(jsonStr);
			JsonObject object = new JsonParser().parse(jsonStr).getAsJsonObject();
			jsonArray.add(object);
		}

		return jsonArray.toString();
	}
	
	@Transactional(propagation=Propagation.NOT_SUPPORTED,readOnly=true)
	public String getHXNameByLPName(String LPName) {
		List<String> names = houseDao.getHXNameByLPName(LPName);

		if(names == null || names.size() == 0)
			return null;
		
		JsonArray jsonArray = new JsonArray();
		for(String name: names){
			String jsonStr = "{\"HXName\":\"" + name + "\"}";
			JsonObject object = new JsonParser().parse(jsonStr).getAsJsonObject();
			jsonArray.add(object);
		}

		return jsonArray.toString();
	}

	@Override
	public House getHouseByLPAndHXName(String LPName, String HXName) {
		return houseDao.getHouseByLPAndHXName(LPName, HXName);
	}

}
