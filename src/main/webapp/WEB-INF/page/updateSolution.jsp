<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" href="css/pub-head.css"/>
<link rel="stylesheet" type="text/css" href="css/document-edit.css"/>
<link rel="stylesheet" type="text/css" href="css/common.css"/>
<title>${solution.title}</title>
<script src="js/jquery.min.js"></script>
<script src="js/ewebeditor.js"></script>
<script type="text/javascript">
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
<div id="bg">
	<div class="contain">
			<img class="top_flower" src="image/problem/tflower.png" />
			<!--标题备注-->
		   <form action="operateSolution_updateSolution" method="post">
			<div class="content">
			<div class="title">
				标题&nbsp;&nbsp;&nbsp; <input type="text" id="title" name="title" value="${solution.title}">
			</div></div>
			<br>
			<textarea name="solution" id="solution" style="display: none;">${solution.solution}</textarea>
			<IFRAME ID="eWebEditor1"
				SRC="ewebeditor/ewebeditor.htm?id=solution&style=green"
				FRAMEBORDER="0" SCROLLING="no" WIDTH="950" HEIGHT="600"> </IFRAME>
		    <div style="margin-top: 10px">
		        <label>密级:&nbsp;</label> 
			    <select name="securityLevelName">
			    <c:forEach var="securityLevel" items="${securityLevels}">
			        <c:if test="${solution.securityLevel.securityLevelName==securityLevel.securityLevelName}">
			            <option value="${securityLevel.securityLevelName}" selected="selected">${securityLevel.securityLevelName}</option>
			        </c:if>
			        <c:if test="${solution.securityLevel.securityLevelName!=securityLevel.securityLevelName}">
				        <option value="${securityLevel.securityLevelName}">${securityLevel.securityLevelName}</option>
				    </c:if>
			    </c:forEach>
			</select>
			</div>
            <div style="margin-top: 10px">
			<label>一级分类:&nbsp;</label> 
			<select id="firstLevelTMCId" name="firstLevelTMCId" onchange="getSecondLevelTMCs()">
			    <c:forEach var="firstLevelTMC" items="${firstLevelTMCs}">
			        <c:if test="${solution.category.TMCId==firstLevelTMC.TMCId}">
			            <option value="${firstLevelTMC.TMCId}" selected="selected">${firstLevelTMC.TMCName}</option>
			        </c:if>
			        <c:if test="${solution.category.TMCId!=firstLevelTMC.TMCId}">
				        <option value="${firstLevelTMC.TMCId}">${firstLevelTMC.TMCName}</option>
				    </c:if>			    
			    </c:forEach>
			</select>
		       <label>二级分类:&nbsp;</label> 
			<select id="secondLevelTMCId" name="secondLevelTMCId">
			<c:if test="${!empty secondLevelTMCs}">
			    <c:forEach var="secondLevelTMC" items="${secondLevelTMCs}">
			        <c:if test="${solution.category.TMCId==secondLevelTMC.TMCId}">
			            <option value="${secondLevelTMC.TMCId}" selected="selected">${secondLevelTMC.TMCName}</option>
			        </c:if>
			        <c:if test="${solution.category.TMCId!=secondLevelTMC.TMCId}">
				        <option value="${secondLevelTMC.TMCId}">${secondLevelTMC.TMCName}</option>
				    </c:if>		    
			    </c:forEach>
			</c:if>
			</select></div>
			<div style="margin-top: 10px">
			<input type="hidden" name="solutionId" value="${solution.solutionId}">
			<input type="hidden" value="1" name="currentPage">
			<input type="submit" value="提交">
			<input type="button" value="返回" onclick="javascript:window.location.href='searchSolution_searchSolutionByPage'">
		    </div>
		</form>
		<!--版权-->
		<div class="foot" style="margin-top: 40px;">
			<p id="copy">版权所有&nbsp;&nbsp;&nbsp;2016-2026&nbsp;&nbsp;&nbsp;无锡万达城投资有限责任公司&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				联系号码：0000-0000000&nbsp;&nbsp;&nbsp;邮箱：100000qq.com    </p>
		</div>
	</div></div>
</body>
</html>