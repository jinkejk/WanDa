<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link rel="stylesheet" href="css/flexpaper.css" type="text/css" />
<script src="js/jquery.min.js"></script>
<script type="text/javascript">
//文件下载，先用异步判断是否存在文件
function downloadSignFile(signFile){
	   $.ajax({
			type:'POST',
			contentType: "application/text",  
		    url: "downUtilAction_isFileExist?filePath=/signMaterial&fileName="+signFile,  
		    dataType: "text",  
		    success:function(datas){
		    	if(datas == "none"){
		    		alert('不存在该文件！');
	                return;	    		
		    	}		    	
		    	if(datas == "error"){
		    		alert('下载出错！');
	                return;	    		
		    	}
		    	if(datas == "exist"){
		    		window.location.href = "downUtilAction?filePath=/signMaterial&fileName="+signFile;
		    	}
		    }  
		});
}
</script>
<title>${signMaterial.title}</title>
</head>
<body>
	<center>
	    <ul>
	        <li>标题：${signMaterial.title}</li>
	        <li>备注：${signMaterial.remark}</li>
	        <li>下载次数：${signMaterial.clickNum}<a href="#" onclick="downloadSignFile('${signMaterial.signFile}')">下载</a></li>
	        <li>创建日期：${signMaterial.createDate}</li>
	    </ul>
	    <br>
	</center>
</body>
</html>
