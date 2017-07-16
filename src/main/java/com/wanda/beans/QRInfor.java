package com.wanda.beans;

import java.util.Map;

/**
 * 存放QR扫描结果
 * @author jinkejk
 *
 */
public class QRInfor {

	//图号
	private String num;
	//图名
	private String name;
	//工程名称
	private String projectName;
	//签名记录
	private Map<String, String> record;
	
	public String getNum() {
		return num;
	}
	public void setNum(String num) {
		this.num = num;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	
	public Map<String, String> getRecord() {
		return record;
	}
	public void setRecord(Map<String, String> record) {
		this.record = record;
	}
	
	@Override
	public String toString() {
		return "QRInfor [num=" + num + ", name=" + name + ", projectName=" + projectName + ", record=" + record + "]";
	}	
	
}
