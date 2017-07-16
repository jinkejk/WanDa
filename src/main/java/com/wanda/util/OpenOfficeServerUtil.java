package com.wanda.util;
import java.io.IOException;

/**
 * 开启openofficeServer服务
 * JODConverter可以调用该服务将office文档
 * 转为pdf
 * @author jinkejk
 *
 */
public class OpenOfficeServerUtil {
    //soffice.exe的安装目录
	//private static final String openOfficePath= "C:\\Program Files (x86)\\OpenOffice 4\\program\\";
	
	//开启openoffic服务
	public static void start(String openOfficePath){
		String cmd=openOfficePath + 
				"soffice.exe -headless -accept=\"socket,host=127.0.0.1,port=8100;urp;\" -nofirststartwizard";
		try {
			Runtime.getRuntime().exec(cmd);
			System.out.println("openoffice启动成功！");
		} catch (IOException e) {
			System.out.println("openoffice启动失败！");
			e.printStackTrace();
		}			

	}

	//关闭openoffice服务
	public static void shutDown(){	
		//关闭soffice进程的命令
		String cmd="taskkill /f /im soffice.exe";
		try {
			Runtime.getRuntime().exec(cmd);
			System.out.println("openoffice正常关闭.......");
		} catch (IOException e) {
			System.out.println("openoffice关闭错误.......");
			e.printStackTrace();
		}
	}
}
