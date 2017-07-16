package com.wanda.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.wanda.beans.Permission;
import com.wanda.dao.PermissionDao;
import com.wanda.service.PermissionService;

@Service("permissionService")
@Transactional
public class PermissionServiceImpl implements PermissionService {
	
	@Resource
	private PermissionDao permissionDao;

	@Transactional(propagation=Propagation.NOT_SUPPORTED,readOnly=true)
	public Permission getPermissionById(Integer permissionId) {
		return permissionDao.getPermissionById(permissionId);
	}

	@Transactional(propagation=Propagation.NOT_SUPPORTED,readOnly=true)
	public Permission getPermissionByName(String permissionName) {
		return permissionDao.getPermissionByName(permissionName);
	}

	public void addPermission(Permission permission) {
		permissionDao.addPermission(permission);
	}

	public void deletePermissionById(Integer permissionId) {
		permissionDao.deletePermissionById(permissionId);

	}

}
