<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>文章上传页</title>
<script src="js/jquery.min.js"></script>
<script src="ewebeditor.js"></script>
<script type="text/javascript">
//检查是否可以提交
function check(){
		if($("#title").val()=='' || $("#title").val().length>50){
			$("#msg").html('<font color="red">标题不为空且长度小于50！</font>');
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
		<form action="operateSolution_uploadSolution" method="post" onsubmit="return check()">
			<p>
				文章标题&nbsp;&nbsp;&nbsp; <input type="text" id="title" name="title"
					style="width: 650px; height: 30px">
			</p>
			<br>
			<textarea name="solution" id="solution" style="display: none;"></textarea>
			<IFRAME ID="eWebEditor1"
				SRC="ewebeditor/ewebeditor.htm?id=solution&style=green"
				FRAMEBORDER="0" SCROLLING="no" WIDTH="950" HEIGHT="500"> </IFRAME>
		       <br><br>密级： 
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
			<div id="msg"></div>
			<input type="hidden" value="1" name="currentPage">
			<input type="submit" value="提交">
			<input type="button" value="返回" onclick="javascript:window.location.href='searchSolution_searchSolutionByPage?currentPage=${currentPage}&pageSize=${pageSize}'">
		</form>
	</center>

</body>
</html>