package com.wanda.service;

import java.math.BigInteger;
import java.util.List;

import com.wanda.beans.House;
import com.wanda.beans.User;

/**
 * 户型
 * @author jinkejk
 *
 */
public interface HouseService {

	//根据id查找
	House getHouseById(Integer houseId);

	//分页查找
	List<House> getHousesByPage(int pageSize, int currentPage);

	//根据内容分页查找
    List<House> getHousesByContentByPage(String searchContent, int pageSize, int currentPage);
    
    //获取所有楼盘名，并保存为json数组
  	String getAllLPName();
  	
    //获取楼盘对应的户型名，对应的json数组
  	String getHXNameByLPName(String LPName);
  	
   //根据楼盘和户型查询具体户型
  	House getHouseByLPAndHXName(String LPName, String HXName);
  	
    //根据内容查找的户型数量
    BigInteger getTotalHouseNumByContent(String searchContent);
  	
	//用户访问
	House visitHouseById(Integer houseId, User user);
	
	//根据id删除
	void deleteHouseById(Integer houseId);

	//添加house
	void addHouse(House house);

	//修改house
	void updateHouse(House house);
	
	//获取总的户型数量
	Long getTotalHouseNum();
}
