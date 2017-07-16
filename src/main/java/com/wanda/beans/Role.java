package com.wanda.beans;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

/**
 * 角色实体类
 * @author jinkejk
 *
 */
@Entity
@Table(name="role")
public class Role {
	@Id @Column
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer roleId;
	@Column(nullable=false)
	private String roleName;
	//角色说明
	private String roleRemark;
	//对应密级
	@ManyToOne(targetEntity=SecurityLevel.class)
	@JoinColumn(name="securityLevelId")
	private SecurityLevel securityLevel;
	//该角色对应的所有用户，懒加载
	@OneToMany(targetEntity=User.class,mappedBy="role",fetch=FetchType.LAZY)
	private Set<User> users = new HashSet<User>();
	//对应的权限
	//拥有该权限的角色，双向关联
	@ManyToMany(targetEntity=Permission.class,fetch=FetchType.EAGER)
	@Cascade(value = {CascadeType.PERSIST})
	@JoinTable(
			name="role_permission",
			joinColumns=@JoinColumn(name="roleId",referencedColumnName="roleId"),
			inverseJoinColumns=@JoinColumn(name="permissionId",referencedColumnName="permissionId")
			)
	private Set<Permission> permissions = new HashSet<Permission>();
	
	//构造函数
	public Role(){};
	
	public Role(Integer roleId){
		this.roleId = roleId;
	}
	public Integer getRoleId() {
		return roleId;
	}
	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	public String getRoleRemark() {
		return roleRemark;
	}
	public void setRoleRemark(String roleRemark) {
		this.roleRemark = roleRemark;
	}
	public SecurityLevel getSecurityLevel() {
		return securityLevel;
	}
	public void setSecurityLevel(SecurityLevel securityLevel) {
		this.securityLevel = securityLevel;
	}
	public Set<User> getUsers() {
		return users;
	}
	public void setUsers(Set<User> users) {
		this.users = users;
	}
	public Set<Permission> getPermissions() {
		return permissions;
	}
	public void setPermissions(Set<Permission> permissions) {
		this.permissions = permissions;
	}
	
	@Override
	public String toString() {
		return "Role [roleId=" + roleId + ", roleName=" + roleName + ", roleRemark=" + roleRemark + ", securityLevel="
				+ securityLevel + "]";
	}
	
	
}
