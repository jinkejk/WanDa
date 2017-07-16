package com.wanda.service.impl;

import java.util.Set;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.wanda.beans.Permission;
import com.wanda.beans.Role;
import com.wanda.dao.PermissionDao;
import com.wanda.dao.RoleDao;
import com.wanda.service.RoleService;

@Service("roleService")
@Transactional
public class RoleServiceImpl implements RoleService{

	@Resource
	private RoleDao roleDao;
	@Resource
	private PermissionDao permissionDao;
	
	@Transactional(propagation=Propagation.NOT_SUPPORTED,readOnly=true)
	public Role getRoleById(Integer roleId) {
		return roleDao.getRoleById(roleId);
	}

	@Transactional(propagation=Propagation.NOT_SUPPORTED,readOnly=true)
	public Role getRoleByRoleName(String roleName) {
		return roleDao.getRoleByRoleName(roleName);
	}

	public void addRole(Role role) {
		roleDao.addRole(role);		
	}

	public void deleteRoleById(Integer roleId) {
		roleDao.deleteRoleById(roleId);		
	}

	public void addPermissionById(Integer permissionId, Integer roleId) {
		Permission permission = permissionDao.getPermissionById(permissionId);
		Role role = roleDao.getRoleById(roleId);
		role.getPermissions().add(permission);
		roleDao.updateRole(role);
	}

	@Transactional(propagation=Propagation.NOT_SUPPORTED,readOnly=true)
	public Set<Role> getAllRoles() {
		return roleDao.getAllRoles();
	}

}
