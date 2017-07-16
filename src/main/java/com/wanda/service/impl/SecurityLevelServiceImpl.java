package com.wanda.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.wanda.beans.SecurityLevel;
import com.wanda.dao.SecurityLevelDao;
import com.wanda.service.SecurityLevelService;

@Service("securityLevelService")
@Transactional
public class SecurityLevelServiceImpl implements SecurityLevelService {
	
	@Resource
	private SecurityLevelDao securityLevelDao;

	@Transactional(propagation=Propagation.NOT_SUPPORTED,readOnly=true)
	public SecurityLevel getSecurityLevelById(int securityLevelId) {
		return securityLevelDao.getSecurityLevelById(securityLevelId);
	}

	@Transactional(propagation=Propagation.NOT_SUPPORTED,readOnly=true)
	public SecurityLevel getSecurityLevelByName(String securityLevelName) {
		return securityLevelDao.getSecurityLevelByName(securityLevelName);
	}

	@Transactional(propagation=Propagation.NOT_SUPPORTED,readOnly=true)
	public List<SecurityLevel> getAllSecurityLevels() {
		return securityLevelDao.getAllSecurityLevels();
	}

}
