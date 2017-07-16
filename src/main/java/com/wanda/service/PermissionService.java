package com.wanda.service;

import com.wanda.beans.Permission;

public interface PermissionService {
	/**
	 * 根据id获取权限
	 * @param permissionId
	 */
	Permission getPermissionById(Integer permissionId);
    
    /**
     * 根据权限名获取权限 
     */
	Permission getPermissionByName(String permissionName);
       
    /**
     * 添加权限
     */
    void addPermission(Permission permission);
    
    /**
     * 根据id删除权限
     */
    void deletePermissionById(Integer permissionId);
}
