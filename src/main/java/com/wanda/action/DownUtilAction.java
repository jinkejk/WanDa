package com.wanda.action;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import com.opensymphony.xwork2.ActionSupport;
import com.wanda.beans.SignMaterial;
import com.wanda.service.SignMaterialService;
import com.wanda.util.UtilCommon;

@Controller("DownUtilAction")
@Scope("prototype")
public class DownUtilAction extends ActionSupport {
	@Resource
	private SignMaterialService signMaterialService;
	//文件的路径，相对路径
	private String filePath;
	private String fileName;

	//修改用户下载文件名
	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	//输出流
	public InputStream getInputStream() {
		InputStream in = null;
		try {
			if(fileName == null || fileName.isEmpty()){
				return null;
			}
			fileName = new String(fileName.getBytes(), "utf-8");
			filePath = new String(filePath.getBytes(), "utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return null;
		}

		String realPath = "E:/apache_tomcat_7.0.57/webapps" + filePath + "/" + fileName;

		//InputStream in=ServletActionContext.getServletContext().getResourceAsStream(realPath);
		try {
			in = new FileInputStream(realPath);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;  
		}
		return in; 
	} 

	public String execute(){
		//增加一条下载记录
		SignMaterial signMaterial = signMaterialService.getSignMaterialByFileName(fileName);
		signMaterialService.addSignMaterialDownloadNum(signMaterial.getSMId());
		
		return "success"; 
	} 

	//判断文件是否存在的异步操作
	public String isFileExist(){
		PrintWriter out = null;
		HttpServletResponse response = ServletActionContext.getResponse();  
		response.setCharacterEncoding("utf-8"); // 务必，防止返回文件名是乱码  
		try {
			out = response.getWriter();
			try {
				if(fileName == null || fileName.isEmpty()){
					out.print("none");
					return null;
				}
				fileName = new String(fileName.getBytes(), "utf-8");
				filePath = new String(filePath.getBytes(), "utf-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
				out.print("error");
				return null;
			}

			String realPath = "E:/apache_tomcat_7.0.57/webapps" + filePath + "/" + fileName;

			try {
				InputStream in = new FileInputStream(realPath);
				out.print("exist");
			} catch (FileNotFoundException e) {
				e.printStackTrace();
				return null;
			}
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}finally{
			if(out != null){				
				out.flush();  
				out.close();
			}
		}
		return null;
	}
}
