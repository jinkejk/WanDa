package com.wanda.dao.impl;

import java.util.HashSet;
import java.util.Set;

import javax.annotation.Resource;

import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import com.wanda.beans.Role;
import com.wanda.dao.RoleDao;

@Repository("roleDao")
public class RoleDaoImpl implements RoleDao {

	@Resource
	private SessionFactory sessionFactory;
	
	public Role getRoleById(Integer roleId) {
		return (Role) sessionFactory.getCurrentSession().get(Role.class, roleId);
	}

	public Role getRoleByRoleName(String roleName) {
		return (Role) sessionFactory.getCurrentSession()//
				.createQuery("from Role r where r.roleName=?1")//
				.setString("1", roleName)//
				.uniqueResult();
	}

	public void addRole(Role role) {
		sessionFactory.getCurrentSession().persist(role);
	}

	public void deleteRoleById(Integer roleId) {
		sessionFactory.getCurrentSession().delete(
				sessionFactory.getCurrentSession().get(Role.class, roleId));
	}

	public void updateRole(Role role) {
		sessionFactory.getCurrentSession().update(role);
	}

	@SuppressWarnings("unchecked")
	public Set<Role> getAllRoles() {
		return new HashSet<Role>(sessionFactory.getCurrentSession()//
				.createQuery("from Role")//
				.setCacheable(true)//
				.list());
	}

}
