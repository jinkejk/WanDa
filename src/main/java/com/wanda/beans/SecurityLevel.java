package com.wanda.beans;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * 密级实体类
 * @author jinkejk
 *
 */
@Entity
@Table(name="security_level")
public class SecurityLevel {
	@Id @Column
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer securityLevelID;
	private String securityLevelName;
	private Integer securityLevelValue;
	
	public SecurityLevel(){}
	
	public SecurityLevel(Integer securityLevelID){
		this.securityLevelID = securityLevelID;
	}
	
	public Integer getSecurityLevelID() {
		return securityLevelID;
	}
	public void setSecurityLevelID(Integer securityLevelID) {
		this.securityLevelID = securityLevelID;
	}
	public String getSecurityLevelName() {
		return securityLevelName;
	}
	public void setSecurityLevelName(String securityLevelName) {
		this.securityLevelName = securityLevelName;
	}
	public Integer getSecurityLevelValue() {
		return securityLevelValue;
	}
	public void setSecurityLevelValue(Integer securityLevelValue) {
		this.securityLevelValue = securityLevelValue;
	}
	@Override
	public String toString() {
		return "SecurityLevel [securityLevelID=" + securityLevelID + ", securityLevelName=" + securityLevelName
				+ ", securityLevelValue=" + securityLevelValue + "]";
	}
	
	

}
