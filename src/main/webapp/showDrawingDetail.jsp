<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">

<title>${drawing.drawingName}</title>
<script src="js/jquery.min.js"></script>
<script type="text/javascript">
    $(document).ready(function(){
    	var path = '..\\drawing\\small\\' + '${drawing.imgName}';
  	    $("#showImage").attr("src",path);//填充内容
  	    var str = '${drawing.imgName}';
  	    var qrpath = '..\\drawing\\qrcode\\' + str.substring(0, 23) + '.jpg';
	    $("#showQRImage").attr("src",qrpath);//填充内容
    });
</script>
</head>
<body>
	<center>
        <table>
        <tr>
            <td>ID</td>
            <td>图纸名称</td>
            <td>上传日期</td>
            <td>上传人</td> 
            <td>修改日期</td>
            <td>上次修改人</td>          
            <td>图纸说明</td>
            <td>保密级别</td>            
        </tr>       
            <tr>            
            <td>${drawing.stringId}</td>
            <td >${drawing.drawingName}</td>
            <td>${fn:substring(drawing.uploadDate,0,10)}</td>
            <td>${drawing.author.loginName}</td>            
            <td>${fn:substring(drawing.alterDate,0,10)}</td>
            <td>${drawing.lastAlter.loginName}</td>
            <td>${drawing.remark}</td>
            <td>${drawing.securityLevel.securityLevelValue}</td>           
            </tr>
    </table>
    <br><br>
    <div>二维码 </div><br>  
    <img id="showQRImage" width="200px" height="200px"></img><br><br>
    <div>图片预览 </div><br><br>
    <img id="showImage" width="500px" height="300px"></img><br>
    <a href="downUtilAction?filePath=/drawing/qrcode&fileName=${fn:substring(drawing.imgName,0,23)}.jpg" >下载二维码</a>
    <a href="downUtilAction?filePath=/drawing/origin&fileName=${drawing.imgName}">下载图纸</a>
	</center>
</body>
</html>
