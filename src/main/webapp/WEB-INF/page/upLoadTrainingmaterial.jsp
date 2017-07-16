<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>上传培训资料</title>
<script src="js/jquery.min.js"></script>
<script src="js/jquery.form.js"></script>
<script type="text/javascript">
    var flag_ppt = false;//ppt是否上传标记    
    var fileName; //防止重复上传
    var lastFileName;
    
	$(function(){
		    //为表单绑定异步上传的事件
		    $("#upload_ppt").ajaxForm({
		    url : "uploadUtilAction_upLoadPpt", // 请求的url
		    type : "post", // 请求方式
		    dataType : "text", // 响应的数据类型
		    async :true, // 异步
		    success : function(msg){
           	 if (msg.indexOf("#") > 0) {
           		 flag_ppt = true;
           		 lastFileName = fileName;
           		 var data = msg.split("#");
           	     $("#upload_result").html(data[0]);           	     
           	     $("#pptName").attr("value",data[1]);//填充内容
               }else{
           	      $("#upload_result").html(msg);
           	      flag_ppt = false;
                }
		    },
		    error : function(){
		    	flag_ppt = false;
		    	$("#upload_result").html('上传失败！');
		    }
		});
	});
	
    //上传图片
    function uploadPpt() {
	   if(pptValidate()){
		   $("#upload_result").html('上传中...');
		   $("#upload_ppt").submit();	 
	   }
       return false;  
    }
    
    //判断图片是否符合要求
    function pptValidate(){
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
        if (ext != ".PPT" && ext != ".PPTX") {
            alert("仅限于ppt格式！");           
            return false;
        } 
        return true;
    }

    var title_flag = true;
    var remark_flag = true;
    var pptName_flag = true;
    //检查是否可以提交
    function check(){
    	if(flag_ppt){
    		if($("#title").val()=='' || $("#title").val().length>50){
    			$("#msg").html('<font color="red">标题不为空且长度小于50！</font>');
    			title_flag = false;
    			return false;
    		}
    		if($("#pptName").val()==''){
    			$("#msg").html('<font color="red">ppt未正确上传！</font>');
    			pptName_flag = false;
    			return false;    		
    		}
    	}else{
    		alert('请先上传ppt！');
    		return false;
    	}    	
    	return true;
    }
    
  //异步获取二级分类，并动态添加
    function getSecondLevelTMCs(){
    	$.ajax({
			type:'POST',
			contentType: "application/json",  
		    url: "operateTrainingMaterial_getSecondLevelTMCs?firstLevelTMCId="+$("#firstLevelTMCId").val(),  
		    dataType: "json",  
		    success:function(datas){
		    	if(datas == null){
		    		//alert('不存在二级菜单！');
		    		$("#secondLevelTMCId").empty();
	                return;	    		
		    	}
		    	if(datas == "wrong"){
		    		alert('出错了！');
	                return;	    		
		    	}
		    	//动态添加下拉列表
		    	$("#secondLevelTMCId").empty();
		    	var selectHtml = '';
		    	for(var i=0; i<datas.length; i++){
		    		selectHtml += '<option value="'+datas[i].TMCId+'">'+datas[i].TMCName+'</option>';
		    	}
		    	$("#secondLevelTMCId").html(selectHtml);
		    }  
		});
    }
</script>
</head>
<body>
<br>
	<center>
	   <form action="uploadUtilAction_upLoadPpt" id="upload_ppt" method="post" enctype="multipart/form-data">
	       <input id="fileupload" name="fileUpload" type="file"/>
		   <input type="button" value="上传" onclick="uploadPpt()">  
           <div id="upload_result"></div>
        </form>
		<form action="operateTrainingMaterial_uploadTM" onsubmit="return check()" method="post">
			<ul>
			    <li>
				标题：<input type="text" id="title" name="title"
					style="width: 650px; height: 30px"><br><br>
			    </li>
			    <li>
				备注：<input type="text" id="remark" name="remark"
					style="width: 650px; height: 30px"><br><br>
			    </li>
				<li>	
		          密级： 
			<select name="securityLevelName">
			    <c:forEach var="securityLevel" items="${securityLevels}">
				    <option value="${securityLevel.securityLevelName}">${securityLevel.securityLevelName}</option>			    
			    </c:forEach>
			</select><br><br>			
		        一级分类： 
			<select id="firstLevelTMCId" name="firstLevelTMCId" onchange="getSecondLevelTMCs()">
			    <c:forEach var="firstLevelTMC" items="${firstLevelTMCs}">			        
			        <option value="${firstLevelTMC.TMCId}">${firstLevelTMC.TMCName}</option>		        			    
			    </c:forEach>
			</select>
		       二级分类： 
			<select id="secondLevelTMCId" name="secondLevelTMCId">
			<c:if test="${!empty secondLevelTMCs}">
			    <c:forEach var="secondLevelTMC" items="${secondLevelTMCs}">			        
				    <option value="${secondLevelTMC.TMCId}">${secondLevelTMC.TMCName}</option>	    
			    </c:forEach>
			</c:if>
			</select><br><br>
			</li>			   
			</ul>		
			<div id="msg"></div>	
			<input type="hidden" id="pptName" name="pptName">
			<input type="submit" value="提交">
			<input type="button" value="返回" onclick="javascript:window.location.href='searchTrainingMaterial_searchTMByPage?currentPage=${currentPage}&pageSize=${pageSize}'">
		</form>
	</center>

</body>
</html>