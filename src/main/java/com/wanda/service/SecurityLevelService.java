package com.wanda.service;

import java.util.List;

import com.wanda.beans.SecurityLevel;

public interface SecurityLevelService {

	/**
	 * 根据id获取密级
	 */
    SecurityLevel getSecurityLevelById(int securityLevelId);
    
    /**
	 * 根据密级名获取密级
	 */
    SecurityLevel getSecurityLevelByName(String securityLevelName);
    
    /**
   	 * 获取所有SecurityLevel
   	 */
    List<SecurityLevel> getAllSecurityLevels();
}
