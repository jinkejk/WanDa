package com.wanda.dao.impl;

import javax.annotation.Resource;

import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import com.wanda.beans.Permission;
import com.wanda.beans.Role;
import com.wanda.dao.PermissionDao;

@Repository("permissionDao")
public class PermissionDaoImpl implements PermissionDao {
	
	@Resource
	private SessionFactory sessionFactory;

	public Permission getPermissionById(Integer permissionId) {
		return (Permission) sessionFactory.getCurrentSession().get(Permission.class, permissionId);
	}

	public Permission getPermissionByName(String permissionName) {
		return (Permission) sessionFactory.getCurrentSession()//
				.createQuery("from Permission p where p.permissionName=?1")//
				.setString("1", permissionName)//
				.uniqueResult();
	}

	public void addPermission(Permission permission) {
		sessionFactory.getCurrentSession().persist(permission);

	}

	public void deletePermissionById(Integer permissionId) {
		sessionFactory.getCurrentSession().delete(
				sessionFactory.getCurrentSession().get(Permission.class, permissionId));
	}

}
