package com.wanda.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.PrintWriter;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.io.FileUtils;
import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import com.opensymphony.xwork2.ActionSupport;
import com.wanda.beans.Drawing;
import com.wanda.beans.House;
import com.wanda.beans.SignMaterial;
import com.wanda.beans.TrainingMaterial;
import com.wanda.service.DrawingService;
import com.wanda.service.HouseService;
import com.wanda.service.SignMaterialService;
import com.wanda.service.TrainingMaterialService;
import com.wanda.util.OpenCVUtils;
import com.wanda.util.QRcodeHandler;
import com.wanda.util.UtilCommon;
import com.wanda.util.officeToPdf;
import com.wanda.util.officeToSwf;

@Controller("UploadUtilAction")
@Scope("prototype")
public class UploadUtilAction extends ActionSupport{
	@Resource
	private DrawingService drawingService;
	@Resource
	private HouseService houseService;
	@Resource
	private TrainingMaterialService trainingMaterialService;
	@Resource
	private SignMaterialService signMaterialService;
	private File fileUpload; // 和JSP中input标记name同名  
	private String savePath; //配置文件注入
	private String stringId; //更新的时候需要获取id
	private int houseId; //更新是需要的户型id
	private int TMId; //更新培训资料需要的户型Id
	private int SMId; //签批资料ID
	private String originImgName;//更新图纸或户型图的原始名
	private String originPptName;//更新ppt原始名
	private String originFileName;//更新ppt原始名
	// Struts2拦截器获得的文件名,命名规则，File的名字+FileName  
	// 如此处为 'fileupload' + 'FileName' = 'fileuploadFileName'  
	private String fileUploadFileName; // 上传来的文件的名字	

	public String getOriginFileName() {
		return originFileName;
	}

	public void setOriginFileName(String originFileName) {
		this.originFileName = originFileName;
	}

	public int getSMId() {
		return SMId;
	}

	public void setSMId(int sMId) {
		SMId = sMId;
	}

	public String getOriginPptName() {
		return originPptName;
	}

	public void setOriginPptName(String originPptName) {
		this.originPptName = originPptName;
	}

	public int getTMId() {
		return TMId;
	}

	public void setTMId(int tMId) {
		TMId = tMId;
	}

	public int getHouseId() {
		return houseId;
	}

	public void setHouseId(int houseId) {
		this.houseId = houseId;
	}

	public String getOriginImgName() {
		return originImgName;
	}

	public void setOriginImgName(String originImgName) {
		this.originImgName = originImgName;
	}

	public String getStringId() {
		return stringId;
	}

	public void setStringId(String stringId) {
		this.stringId = stringId;
	}

	public File getFileUpload() {
		return fileUpload;
	}

	public void setFileUpload(File fileUpload) {
		this.fileUpload = fileUpload;
	}

	public String getSavePath() {
		return savePath;
	}

	public void setSavePath(String savePath) {
		this.savePath = savePath;
	}

	public String getFileUploadFileName() {
		return fileUploadFileName;
	}


	public void setFileUploadFileName(String fileUploadFileName) {
		this.fileUploadFileName = fileUploadFileName;
	}


	//图纸文件上传
	public String uploadFile() { 
		String frontStr = ""; //保存文件名
		String extName = ""; // 保存文件拓展名  
		String newFileName = ""; // 保存新的文件名  
		PrintWriter out = null;

		//存储QR图的路径
		String saveQRPath = savePath + "\\drawing\\qrcode\\";
		//存储缩略图的路径
		String saveSmallPath = savePath + "\\drawing\\small\\";
		//存储原图的路径
		savePath = savePath + "\\drawing\\origin\\";

		HttpServletResponse response = ServletActionContext.getResponse();  
		response.setCharacterEncoding("utf-8"); // 务必，防止返回文件名是乱码  

		// 生成文件名
		frontStr = UtilCommon.createImgName();

		// 获取拓展名  
		if (fileUploadFileName.lastIndexOf(".") >= 0) {  
			extName = fileUploadFileName.substring(fileUploadFileName  
					.lastIndexOf("."));
			//全部存小写
			extName = extName.toLowerCase();
		}  
		try {  
			out = response.getWriter();  
			newFileName = frontStr + extName; // 文件重命名后的名字  
			String filePath = savePath + newFileName;  
			String samllFilePath = saveSmallPath + newFileName;

			//大小限制
			InputStream picStream;  
			byte[] byt = new byte[0];   
			picStream = new FileInputStream(fileUpload);  
			byt = FileCopyUtils.copyToByteArray(picStream);  
			picStream.close();

			if(byt.length > (1024*1024*40)){  
				out.print("<font color='red'>上传图片不能超过40M!</font>");
				return null;
			}  
			//检查上传的是否是图片  
			if (UtilCommon.checkIsImage(extName)) {  
				FileUtils.copyFile(fileUpload, new File(filePath));

				//存储缩略图
				System.out.println(fileUpload.getAbsolutePath());
				OpenCVUtils.saveImg(samllFilePath, OpenCVUtils.resize(fileUpload.getAbsolutePath(), 600));

				//生成二维码
				String QRName = frontStr + ".jpg";
				String QRPath = saveQRPath + QRName;
				QRPath = QRPath.replace("\\", "\\\\");
				QRcodeHandler.encode(frontStr, 600, 600, QRPath);

				//删除原先的文件
				if(stringId != null){
					String fileName = savePath + originImgName;
					fileName = fileName.replace("\\", "\\\\");
					String smallFileName = saveSmallPath + originImgName;
					smallFileName = smallFileName.replace("\\", "\\\\");

					UtilCommon.deleteFile(fileName);
					UtilCommon.deleteFile(smallFileName);

					//更新图纸的imgName，否则若用户不提交，则图纸无法访问
					Drawing drawing = drawingService.getDrawingByStringId(stringId);
					drawing.setImgName(newFileName);
					drawingService.updateDrawing(drawing);
				}				

				out.print("<font color='red'>" + fileUploadFileName  
						+ "上传成功!</font>#" + newFileName + "#" + QRName + "#" + extName);  
			} else {  
				out.print("<font color='red'>上传的文件类型错误，请选择bmp,jpg,jpeg,png和gif格式的图片!</font>");  
			}   
		} catch (Exception e) {  
			e.printStackTrace();  
			out.print("上传失败，出错啦!");
		}finally {
			if(out != null){				
				out.flush();  
				out.close();
			}
		} 
		return null;  
	}

	//上传户型图片
	public String upLoadHouseImg(){
		String frontStr = ""; //保存文件名
		String extName = ""; // 保存文件拓展名  
		String newFileName = ""; // 保存新的文件名  
		PrintWriter out = null;

		//存储缩略图的路径
		String saveSmallPath = savePath + "\\house\\small\\";
		//存储原图的路径
		savePath = savePath + "\\house\\origin\\";

		HttpServletResponse response = ServletActionContext.getResponse();  
		response.setCharacterEncoding("utf-8"); // 务必，防止返回文件名是乱码  

		//生成文件名
		frontStr = UtilCommon.createImgName();

		//获取拓展名  
		if (fileUploadFileName.lastIndexOf(".") >= 0) {  
			extName = fileUploadFileName.substring(fileUploadFileName  
					.lastIndexOf("."));
			//全部存小写
			extName = extName.toLowerCase();
		}  
		try {  
			out = response.getWriter();  
			newFileName = frontStr + extName; // 文件重命名后的名字  
			String filePath = savePath + newFileName;  
			String samllFilePath = saveSmallPath + newFileName;

			//大小限制
			InputStream picStream;  
			byte[] byt = new byte[0];   
			picStream = new FileInputStream(fileUpload);  
			byt = FileCopyUtils.copyToByteArray(picStream);  
			picStream.close();

			if(byt.length > (1024*1024*40)){  
				out.print("<font color='red'>上传图片不能超过40M!</font>");
				return null;
			}  
			//检查上传的是否是图片  
			if (UtilCommon.checkIsImage(extName)) {  
				FileUtils.copyFile(fileUpload, new File(filePath));

				//存储缩略图
				OpenCVUtils.saveImg(samllFilePath, OpenCVUtils.resize(fileUpload.getAbsolutePath(), 600));

				//更新操作，删除原先的文件
				if(houseId != 0){
					String fileName = savePath + originImgName;
					fileName = fileName.replace("\\", "\\\\");
					String smallFileName = saveSmallPath + originImgName;
					smallFileName = smallFileName.replace("\\", "\\\\");

					UtilCommon.deleteFile(fileName);
					UtilCommon.deleteFile(smallFileName);

					//更新户型的imgName，否则若用户不提交，则户型无法访问
					House house = houseService.getHouseById(houseId);
					house.setImgName(newFileName);
					houseService.updateHouse(house);
				}				

				out.print("<font color='red'>" + fileUploadFileName  
						+ "上传成功!</font>#" + newFileName + "#" + extName);  
			} else {  
				out.print("<font color='red'>上传的文件类型错误，请选择bmp,jpg,jpeg,png和gif格式的图片!</font>");  
			}   
		} catch (Exception e) {  
			e.printStackTrace();  
			out.print("上传失败，出错啦!");
		}finally {
			if(out != null){				
				out.flush();  
				out.close();
			}
		} 
		return null;
	}

	//上传培训资料ppt
	public String upLoadPpt(){
		String frontStr = ""; //保存文件名
		String extName = ""; // 保存文件拓展名  
		String newFileName = ""; // 保存新的文件名  
		PrintWriter out = null;

		//存储ppt和pdf的路径
		savePath = savePath + "\\trainingMaterialPpt\\";

		HttpServletResponse response = ServletActionContext.getResponse();  
		response.setCharacterEncoding("utf-8"); // 务必，防止返回文件名是乱码  

		//生成文件名
		frontStr = UtilCommon.createImgName();

		//获取拓展名  
		if (fileUploadFileName.lastIndexOf(".") >= 0) {  
			extName = fileUploadFileName.substring(fileUploadFileName  
					.lastIndexOf("."));
			//全部存小写
			extName = extName.toLowerCase();
		}  
		try {  
			out = response.getWriter();  
			newFileName = frontStr + extName; // 文件重命名后的名字  
			String filePath = savePath + newFileName;

			//大小限制
			InputStream picStream;  
			byte[] byt = new byte[0];   
			picStream = new FileInputStream(fileUpload);  
			byt = FileCopyUtils.copyToByteArray(picStream);  
			picStream.close();

			if(byt.length > (1024*1024*40)){  
				out.print("<font color='red'>上传ppt不能超过40M!</font>");
				return null;
			}  

			//检查上传的是否是ppt
			if (extName.equals(".ppt") || extName.equals(".pptx")) {  
				FileUtils.copyFile(fileUpload, new File(filePath));

				//生成swf文件
				File pptFile = new File(filePath);
				File pdfFile = new File(savePath + frontStr + ".pdf");
				officeToPdf ots = new officeToPdf(pptFile,pdfFile);
				try{
					ots.docToPdf();					
				}catch (Exception e) {
					out.print("ppt转换失败！无法预览！！");
				}

				//更新操作，删除原先的文件
				if(TMId != 0){
					String fileName = savePath + originPptName;
					fileName = fileName.replace("\\", "\\\\");
					String pdfName = savePath + originPptName.substring(0, originPptName.lastIndexOf(".")) + ".pdf";
					pdfName = pdfName.replace("\\", "\\\\");

					//删除原ppt和swf文件
					UtilCommon.deleteFile(fileName);
					UtilCommon.deleteFile(pdfName);

					//更新资料的ppt名字，否则若用户不提交，则无法预览
					TrainingMaterial tm = trainingMaterialService.getTrainingMaterialById(TMId);
					tm.setPptName(newFileName); //包括后缀
					trainingMaterialService.updateTrainingMaterial(tm);
				}				

				out.print("<font color='red'>" + fileUploadFileName  
						+ "上传成功!</font>#" + newFileName + "#" + frontStr);  
			} else {  
				out.print("<font color='red'>上传的文件类型错误，请选择ppt文件!</font>");  
			}   
		} catch (Exception e) {  
			e.printStackTrace();  
			out.print("上传失败，出错啦!");
		}finally {
			if(out != null){				
				out.flush();  
				out.close();
			}
		} 
		return null;
	}

	//上传签批资料
	public String uploadSignMaterial(){
		String frontStr = ""; //保存文件名
		String extName = ""; // 保存文件拓展名  
		String newFileName = ""; // 保存新的文件名  
		PrintWriter out = null;

		//存储签批资料
		savePath = savePath + "\\signMaterial\\";

		HttpServletResponse response = ServletActionContext.getResponse();  
		response.setCharacterEncoding("utf-8"); // 务必，防止返回文件名是乱码  

		//生成文件名
		frontStr = UtilCommon.createImgName();

		//获取拓展名  
		if (fileUploadFileName.lastIndexOf(".") >= 0) {  
			extName = fileUploadFileName.substring(fileUploadFileName  
					.lastIndexOf("."));
			//全部存小写
			extName = extName.toLowerCase();
		}  
		try {  
			out = response.getWriter();  
			newFileName = frontStr + extName; // 文件重命名后的名字  
			String filePath = savePath + newFileName;

			//大小限制
			InputStream picStream;  
			byte[] byt = new byte[0];   
			picStream = new FileInputStream(fileUpload);  
			byt = FileCopyUtils.copyToByteArray(picStream);  
			picStream.close();

			if(byt.length > (1024*1024*40)){  
				out.print("<font color='red'>上传ppt不能超过40M!</font>");
				return null;
			}  

			FileUtils.copyFile(fileUpload, new File(filePath));

			//更新操作，删除原先的文件
			if(SMId != 0){
				String fileName = savePath + originFileName;
				fileName = fileName.replace("\\", "\\\\");
				
				//删除文件
				UtilCommon.deleteFile(fileName);

				//更新签批资料的名字，否则若不更新，则无法下载
				SignMaterial sm = signMaterialService.getSignMaterialById(SMId);
				sm.setSignFile(newFileName); //包括后缀
				signMaterialService.updateSignMaterial(sm);
			}				

			out.print("<font color='red'>" + fileUploadFileName  
					+ "上传成功!</font>#" + newFileName + "#" + frontStr); 
		} catch (Exception e) {  
			e.printStackTrace();  
			out.print("上传失败，出错啦!");
		}finally {
			if(out != null){				
				out.flush();  
				out.close();
			}
		} 
		return null;
	}
}
