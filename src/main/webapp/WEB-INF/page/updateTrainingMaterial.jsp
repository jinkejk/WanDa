<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>修改培训资料</title>
<link rel="stylesheet" href="css/flexpaper.css" type="text/css" />
<script src="js/jquery.min.js"></script>
<script src="js/jquery.form.js"></script>
<script src="js/flexpaper.js"></script>
<script src="js/flexpaper_handlers.js"></script>
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
	   if(pptValidate() && confirm("上传会覆盖原先图纸！确定上传？")){
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
	   更换ppt：<form action="uploadUtilAction_upLoadPpt" id="upload_ppt" method="post" enctype="multipart/form-data">
	       <input id="fileupload" name="fileUpload" type="file"/>
		   <input type="button" value="上传" onclick="uploadPpt()">
		   <input type="hidden" name="TMId" id="TMId" value="${trainingMaterial.TMId}">
           <input type="hidden" name="originPptName" value="${trainingMaterial.pptName}">  
           <div id="upload_result"></div>
        </form><br>
        <!--  
        <div id="documentViewer" class="flexpaper_viewer" 
         style="width:60%;height:500px">
<script type="text/javascript">
$('#documentViewer').FlexPaperViewer({
    config : {  
        SwfFile : "../trainingMaterialPpt/${fn:substring(trainingMaterial.pptName,0,fn:indexOf(trainingMaterial.pptName,"."))}.swf",  
        Scale: 0.6,  
        ZoomTransition: 'easeOut',  
        ZoomTime: 0.5,  
        ZoomInterval: 0.2,  
        FitPageOnLoad: true,  
        FitWidthOnLoad: true,  
        FullScreenAsMaxWindow: true,  
        ProgressiveLoading: true,  
        MinZoomSize: 0.2,  
        MaxZoomSize: 5,  
        SearchMatchAll: false,  
        InitViewMode: 'Portrait',  
        ViewModeToolsVisible: true,  
        ZoomToolsVisible: true,  
        NavToolsVisible: true,  
        CursorToolsVisible: true,  
        SearchToolsVisible: true,  
        localeChain: 'zh_CN',
        }  
});  
</script>
</div>
-->
		<form action="operateTrainingMaterial_uploadTM" onsubmit="return check()" method="post">
			<ul>
			    <li>
				标题：<input type="text" id="title" name="title" value="${trainingMaterial.title}"
					style="width: 650px; height: 30px"><br><br>
			    </li>
			    <li>
				备注：<input type="text" id="remark" name="remark" value="${trainingMaterial.remark}"
					style="width: 650px; height: 30px"><br><br>
			    </li>
				<li>	
		          密级： 
			<select name="securityLevelName">
			    <c:forEach var="securityLevel" items="${securityLevels}">
			        <c:if test="${trainingMaterial.securityLevel.securityLevelName==securityLevel.securityLevelName}">
			            <option value="${securityLevel.securityLevelName}" selected="selected">${securityLevel.securityLevelName}</option>
			        </c:if>
			        <c:if test="${trainingMaterial.securityLevel.securityLevelName!=securityLevel.securityLevelName}">
				        <option value="${securityLevel.securityLevelName}">${securityLevel.securityLevelName}</option>
				    </c:if>
			    </c:forEach>
			</select><br><br>			
		       一级分类： 
			<select id="firstLevelTMCId" name="firstLevelTMCId" onchange="getSecondLevelTMCs()">
			    <c:forEach var="firstLevelTMC" items="${firstLevelTMCs}">
			        <c:if test="${trainingMaterial.category.TMCId==firstLevelTMC.TMCId}">
			            <option value="${firstLevelTMC.TMCId}" selected="selected">${firstLevelTMC.TMCName}</option>
			        </c:if>
			        <c:if test="${trainingMaterial.category.TMCId!=firstLevelTMC.TMCId}">
				        <option value="${firstLevelTMC.TMCId}">${firstLevelTMC.TMCName}</option>
				    </c:if>			    
			    </c:forEach>
			</select>
		       二级分类： 
			<select id="secondLevelTMCId" name="secondLevelTMCId">
			<c:if test="${!empty secondLevelTMCs}">
			    <c:forEach var="secondLevelTMC" items="${secondLevelTMCs}">
			        <c:if test="${trainingMaterial.category.TMCId==secondLevelTMC.TMCId}">
			            <option value="${secondLevelTMC.TMCId}" selected="selected">${secondLevelTMC.TMCName}</option>
			        </c:if>
			        <c:if test="${trainingMaterial.category.TMCId!=secondLevelTMC.TMCId}">
				        <option value="${secondLevelTMC.TMCId}">${secondLevelTMC.TMCName}</option>
				    </c:if>		    
			    </c:forEach>
			</c:if>
			</select><br><br>
			</li>			   
			</ul>		
			<div id="msg"></div>
			<input type="hidden" name="TMId" id="TMId" value="${trainingMaterial.TMId}">	
			<input type="hidden" id="pptName" name="pptName" value="${trainingMaterial.pptName}">
			<input type="submit" value="提交">
			<input type="button" value="返回" onclick="javascript:window.location.href='searchTrainingMaterial_searchTMByPage?currentPage=${currentPage}&pageSize=${pageSize}'">
		</form>
	</center>

</body>
</html>