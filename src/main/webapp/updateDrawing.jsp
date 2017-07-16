<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>修改图纸_${drawing.drawingName }</title>
<script src="js/jquery.min.js"></script>
<script src="js/jquery.form.js"></script>
<script type="text/javascript">
    var fileName; //防止重复上传
    var lastFileName;
	$(function(){
		    var path = '..\\drawing\\small\\' + '${drawing.imgName}';
  	        $("#showImage").attr("src",path);//填充内容
  	        var str = '${drawing.imgName}';
  	        var qrpath = '..\\drawing\\qrcode\\' + str.substring(0, 23) + '.jpg';
	        $("#showQRImage").attr("src",qrpath);//填充内容

		    // 为表单绑定异步上传的事件
		    $("#upload_img").ajaxForm({
		    url : "${pageContext.request.contextPath}/uploadUtilAction_uploadFile", // 请求的url
		    type : "post", // 请求方式
		    dataType : "text", // 响应的数据类型
		    async :true, // 异步
		    success : function(msg){
           	 if (msg.indexOf("#") > 0) {
           		 lastFileName = fileName;
           		 var data = msg.split("#");
           	     $("#upload_result").html(data[0]);
           	     var path = '..\\drawing\\small\\' + data[1] + '?' + Math.random();
           	     var qrpath = '..\\drawing\\qrcode\\' + data[2];
                 $("#originType").attr("value",data[3]);//更新type
                 $("#imgName").attr("value",data[1]);//填充内容
                 $("#showImage").attr("src",path);
                 $("#showQRImage").attr("src",qrpath);
                 
               }else{
           	      $("#upload_result").html(msg);
                       }
		    },
		    error : function(){
		        alert("上传失败！");
		    }
		});
	});
	
    function uploadImage() {
    	if(imgValidate() && confirm("上传会覆盖原先图纸！确定上传？")){
		    $("#upload_result").html('上传中...');
            $("#upload_img").submit();		 
	    }
    }
    //判断文件是否符合要求
    function imgValidate(){
        var filepath = $("input[name='fileUpload']").val();
        if(filepath==""){
        	alert('请选择文件！');
        	return false;
        }
        var extStart = filepath.lastIndexOf(".");
        var fileNameStart = filepath.lastIndexOf("\\");
        var ext = filepath.substring(extStart, filepath.length).toUpperCase();
        
        fileName = filepath.substring(fileNameStart+1,filepath.length);
        //重复上传
        if(fileName == lastFileName){
        	alert('请不要重复提交！');
        	return false;        	
        }
        if (ext != ".BMP" && ext != ".PNG" && ext != ".GIF" && ext != ".JPG" && ext != ".JPEG") {
            alert("图片限于bmp,png,gif,jpeg,jpg格式");
           
            return false;
        } 
        return true;
    }

    //检查是否可以提交
    function check(){    	
       if($("#drawingName").val()=='' || $("#remark").val()==''){
    		alert('请完善内容！');
    		return false;
    	}
    	if($("#stringId").val()==''){
    		alert('图片未正常上传！');
    		return false;
    	}    	
    	
    	return true;
    }
</script>
</head>
<body>
<center>
    <form id="upload_img" method="post" enctype="multipart/form-data">  
            <table>  
                <tr>  
                    <td height="25">浏览图片：</td>  
                    <td>  
                        <input id="fileupload" name="fileUpload" type="file" accept="image/*" onchange="imgValidate()"/>  
                        <div id="upload_result"></div>  
                    </td>  
                </tr>  
                <tr>  
                    <td colspan="2" align="center">  
                        <input type="button" onclick="uploadImage()" value="上传" />  
                    </td>  
                </tr>  
            </table> 
            <input type="hidden" name="stringId" id="stringId" value="${drawing.stringId}">
            <input type="hidden" name="originImgName" value="${drawing.imgName}">            
        </form>
        <br></br>
        <div>二维码 </div><br>  
        <img id="showQRImage" width="200px" height="200px"></img><br><br>
       <br>
        <div>图片预览 </div><br>  
        <img id="showImage" width="500px" height="300px"></img><br><br>
		<form action="operateDrawing_uploadDrawing" method="post" onsubmit="return check()">
			图纸名称：<input type="text" name="drawingName" id="drawingName" value="${drawing.drawingName}"><br><br>
			图纸说明：<input type="text" name="remark" id="remark" value="${drawing.remark}">
			<br>
		       <br><br>密级： 
			<select name="securityLevelName">
			    <c:forEach var="securityLevel" items="${securityLevels}">
			        <c:if test="${drawing.securityLevel.securityLevelName==securityLevel.securityLevelName}">
			            <option value="${securityLevel.securityLevelName}" selected="selected">${securityLevel.securityLevelName}</option>
			        </c:if>
			        <c:if test="${drawing.securityLevel.securityLevelName!=securityLevel.securityLevelName}">
				        <option value="${securityLevel.securityLevelName}">${securityLevel.securityLevelName}</option>
				    </c:if>
			    </c:forEach>
			</select>
			<br><br>
			<input type="hidden" name="stringId" id="stringId" value="${drawing.stringId}">
			<input type="hidden" name="imgName" id="imgName">
			<input type="submit" value="提交">
			<input type="button" value="返回" onclick="javascript:window.history.back(-1)">
		</form>
	</center>

</body>
</html>