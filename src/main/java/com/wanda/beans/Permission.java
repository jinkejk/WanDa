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
import javax.persistence.Table;

/**
 * 用户实体类
 * @author jinkejk
 *
 */
@Entity
@Table(name="permission")
public class Permission {

	@Id @Column
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer permissionId;
	@Column(nullable=false)
	private String permissionName;
	//权限说明
	private String permissionRemark;
	//拥有改权限的角色
	@ManyToMany(targetEntity=Role.class,fetch=FetchType.LAZY)
    @JoinTable(
            name="role_permission",
            joinColumns=@JoinColumn(name="permissionId",referencedColumnName="permissionId"),
            inverseJoinColumns=@JoinColumn(name="roleId",referencedColumnName="roleId")
    )
	private Set<Role> roles = new HashSet<Role>();
	public Integer getPermissionId() {
		return permissionId;
	}
	public void setPermissionId(Integer permissionId) {
		this.permissionId = permissionId;
	}
	public String getPermissionName() {
		return permissionName;
	}
	public void setPermissionName(String permissionName) {
		this.permissionName = permissionName;
	}
	public String getPermissionRemark() {
		return permissionRemark;
	}
	public void setPermissionRemark(String permissionRemark) {
		this.permissionRemark = permissionRemark;
	}
	public Set<Role> getRoles() {
		return roles;
	}
	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}
	@Override
	public String toString() {
		return "Permission [permissionId=" + permissionId + ", permissionName=" + permissionName + ", permissionRemark="
				+ permissionRemark + "]";
	}
	
	
}
