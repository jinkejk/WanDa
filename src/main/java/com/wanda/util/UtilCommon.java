package com.wanda.util;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date; 
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;
import com.wanda.beans.QRInfor;
import com.wanda.beans.TrainingMaterialsCategory;

/**
 * 一些工具函数
 * @author jinkejk
 *
 */
public class UtilCommon {  

	// 检查是否是图片格式  
	public static boolean checkIsImage(String imgStr) {  
		boolean flag = false;  
		if (imgStr != null) {  
			if (imgStr.equalsIgnoreCase(".gif")  
					|| imgStr.equalsIgnoreCase(".jpg")  
					|| imgStr.equalsIgnoreCase(".jpeg")  
					|| imgStr.equalsIgnoreCase(".png")
					|| imgStr.equalsIgnoreCase(".bmp")) {  
				flag = true;  
			}  
		}  
		return flag;  
	} 

	/* * 删除单个文件 
	 *  
	 * @param fileName 
	 *            要删除的文件的文件名 
	 * @return 单个文件删除成功返回true，否则返回false 
	 */  
	public static boolean deleteFile(String fileName) {  
		File file = new File(fileName);  
		// 如果文件路径所对应的文件存在，并且是一个文件，则直接删除  
		if (file.exists() && file.isFile()) {  
			if (file.delete()) {  
				//				System.out.println("删除单个文件" + fileName + "成功！");  
				return true;  
			} else {  
				//				System.out.println("删除单个文件" + fileName + "失败！");  
				return false;  
			}  
		} else {  
			//			System.out.println("删除单个文件失败：" + fileName + "不存在！");  
			return false;  
		}  
	}  

	//生成上传文件名
	public static String createImgName(){
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy_MM_dd_hh_mm_ss_SSS");
		Date date = new Date();

		return simpleDateFormat.format(date);
	}

	//解析二维码的内容，分装到QRInfo类中
	public static QRInfor readQRInfo(String qrString){
		List<String> split = Arrays.asList(qrString.split("\n"));

		QRInfor qRInfor = new QRInfor();
		Map<String, String> record = new HashMap<>();
		for(int i=0; i<split.size(); i++){
			String str = split.get(i);
			if(i <= 2){
				int index = str.indexOf(":");
				if(index != -1 && str.substring(0, index).equals("图号"))
					qRInfor.setNum(str.substring(index + 1));
				else if(index != -1 && str.substring(0, index).equals("图名"))
					qRInfor.setName(str.substring(index + 1));
				else if(index != -1 && str.substring(0, index).equals("工程名称"))
					qRInfor.setProjectName(str.substring(index + 1));			
			}else{
				int index1 = str.indexOf(":");
				int index2 = str.indexOf(":", index1 + 1);
				if(index1 != -1 && index2 != -1){
					record.put(str.substring(index1 + 1, index1 + 4), str.substring(index2 + 1));
				}
			}			
		}
		qRInfor.setRecord(record);;

		return qRInfor;
	}

	//判断是否是有效json	
	public static boolean isGoodJson(String json) {    

		try {    
			JsonParser parser = new JsonParser();  //创建JSON解析器
			JsonObject object = (JsonObject) parser.parse(json);  //创建JsonObject对象			          
			return true;    
		} catch (Exception e) {    
//			System.out.println("bad json: " + json);    
			return false;    
		}    
	} 
	
	//将list<T>转为json格式
	public static <T> String listToJson(List<T> objects){
		JsonArray jsonArray = new JsonArray();
		Gson gson = new Gson();  
		for(T object: objects){
			JsonObject jObject = new JsonParser().parse(gson.toJson(object)).getAsJsonObject();
			jsonArray.add(jObject);
	    }

		return jsonArray.toString();		
	}
	
}  