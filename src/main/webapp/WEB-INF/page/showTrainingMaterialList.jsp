<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8" http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
	<link rel="stylesheet" type="text/css" href="css/common.css"/>
	<link rel="stylesheet" type="text/css" href="css/table.css"/>
<title>培训资料管理</title>
<script src="js/jquery.min.js"></script>
<script type="text/javascript">
  $(document).ready(function(){
     if(${deleteResult == 'success'}){
    	 alert('删除成功！');
     }else if(${deleteResult == 'failed'}){
    	 alert('删除失败！');
     }
     if(${updateResult == 'success'}){
    	 alert('跟新成功！');
     }else if(${updateResult == 'failed'}){
    	 alert('更新失败！');
     }
     if(${addResult == 'success'}){
    	 alert('添加成功！');
     }else if(${addResult == 'failed'}){
    	 alert('添加失败！');
     }
	 $("#nextPage").click(function(){
		 if(${currentPage} >= ${totalPage}){
			 alert("已经是最后一页了！");
		 }else{
			 //下一页	
			 if($("#searchContent").val()=='')
			     window.location.href="searchTrainingMaterial_searchTMByPage?currentPage=${currentPage+1}&pageSize=${pageSize}";
			 else
			     window.location.href="searchTrainingMaterial_searchTMBycontent?searchContent="+$("#searchContent").val()+"&currentPage=${currentPage+1}&pageSize=${pageSize}";
		 }		 
	 });
	 $("#prePage").click(function(){
		 if(${currentPage} <= 1){
			 alert("已经是第一页了！");
		 }else{
			 //上一页
			 if($("#searchContent").val()=='')
			     window.location.href="searchTrainingMaterial_searchTMByPage?currentPage=${currentPage-1}&pageSize=${pageSize}";
			 else
			     window.location.href="searchTrainingMaterial_searchTMBycontent?searchContent="+$("#searchContent").val()+"&currentPage=${currentPage-1}&pageSize=${pageSize}";
		 }		 
	 });
	 $("#firstPage").click(function(){
		 if(${currentPage} > 1){
			 if($("#searchContent").val()=='')
		         window.location.href="searchTrainingMaterial_searchTMByPage?currentPage=1&pageSize=${pageSize}";
		     else
			     window.location.href="searchTrainingMaterial_searchTMBycontent?searchContent="+$("#searchContent").val()+"&currentPage=1&pageSize=${pageSize}";
		 }
		 else
			 alert("已经是第一页了！");
	 });
	 $("#lastPage").click(function(){
		 if(${currentPage} < ${totalPage}){
			 if($("#searchContent").val()=='')
		         window.location.href="searchTrainingMaterial_searchTMByPage?currentPage=${totalPage}&pageSize=${pageSize}";
			 else
			     window.location.href="searchTrainingMaterial_searchTMBycontent?searchContent="+$("#searchContent").val()+"&currentPage=${totalPage}&pageSize=${pageSize}";
		 }
		 else
			 alert("已经是最后一页了！");	 
     }); 
	 
  }); 
  
  function go(){
      if($("#pageNum").val()>=1 && $("#pageNum").val()<= ${totalPage}){
    	  if($("#searchContent").val()=='')
 	          window.location.href="searchTrainingMaterial_searchTMByPage?currentPage=" + $("#pageNum").val() + "&pageSize=${pageSize}";
 	      else
			  window.location.href="searchTrainingMaterial_searchTMBycontent?searchContent="+$("#searchContent").val()+"&currentPage=" + $("#pageNum").val() + "&pageSize=${pageSize}";
      }
      else
		  alert("页码不正确！");
   }
	 
   //删除用户事件
   function onDeleteSolution(TMId){
       if(confirm("确定要删除该资料？")){
    	   if($("#searchContent").val()=='')
    	       window.location.href="operateTrainingMaterial_deleteTMById?TMId=" + TMId + "&currentPage=${currentPage}&pageSize=${pageSize}";
    	   else
    	       window.location.href="operateTrainingMaterial_deleteTMById?searchContent="+$("#searchContent").val()+"&TMId=" + TMId + "&currentPage=${currentPage}&pageSize=${pageSize}";
	 } 
   }
 //点击全选按钮
	 function selectAll(){
		 if($("#selectBox").is(":checked")){
			 //全选
			 $("[name = checkItem]:input").prop("checked", true);
		 }else{
			//全不选
			 $("[name = checkItem]:input").prop("checked", false);
		 }
	 }
	 
	 //批量删除解决方案
	 function delectTrainingMaterials(){
		 var TMIds = new Array();
		 $("[name = checkItem]:checkbox").each(function () {
       if ($(this).is(":checked")) {
    	   TMIds.push($(this).attr("value"));
       }
       });
		 if(TMIds.length > 0){
			 if(confirm('确定删除'+TMIds.length+'个培训资料?')){
			     //批量删除
			     if($("#searchContent").val()=='')
			         window.location.href="operateTrainingMaterial_delectTrainingMaterials?TMIds="+TMIds+"&currentPage=${currentPage}&pageSize=${pageSize}";
			     else
			         window.location.href="operateTrainingMaterial_delectTrainingMaterials?searchContent="+$("#searchContent").val()+"&TMIds="+TMIds+"&currentPage=${currentPage}&pageSize=${pageSize}";
			 }
		 }else{
			 alert('请选择解决方案！');
		 }
	 }
</script> 
</head>
<body>
		<div id="bg">
		<div class="contain">
			<img class="top_flower" src="image/problem/tflower.png" />
			<!--解决方案列表-->
			<div class="table_panel">
				<div class="table_title">培训资料列表</div>
			<!--添加按钮-->
			<shiro:hasPermission name="addTM">
			<div class="add">
				<a id="Add" href="commonAction_uploadTrainingMaterial?currentPage=${currentPage}&pageSize=${pageSize}">添加</a>
            </div>
            </shiro:hasPermission>
			<!--搜索-->
			<div class="search">
			<form action="searchTrainingMaterial_searchTMBycontent" method="post">
                <input type="text" class="search_border" id="searchContent" name="searchContent" value="${searchContent}">
                <input type="hidden" name="pageSize" value="${pageSize}">
                <input type="hidden" name="currentPage" value="1">
                <input type="submit" class="search_submit" id="search" value="搜索">
            </form>
            </div>
            <c:if test="${trainingMaterials.size() > 0}">
                <div >
                <input type="checkbox" id="selectBox" onclick="selectAll()"><span>全选</span>
                <input type="button" onclick="delectTrainingMaterials()" class="search_submit" value="批量删除">
                </div>
			<!--表格内容-->
	        <div class="tabel_content">
	     		<table class="trail_tb">
	     			<tr id="First"><td></td><td>标题</td><td>上传时间</td>
	     			<td >上传人</td><td>修改时间</td><td>修改人</td><td>一级分类</td>
	     			<td>二级分类</td><td >密级</td><td colspan="3">操作</td></tr>
	     			<c:forEach var="trainingMaterial" items="${trainingMaterials}" varStatus="status">	  
                        <tr><td><input type="checkbox" name="checkItem" value="${trainingMaterial.TMId}"/></td>
                        <td id=${status.index} class="title">${titleList[status.index]}</td>
                        <td>${fn:substring(trainingMaterial.createDate,0,10)}</td>
                        <td>${trainingMaterial.author.loginName}</td>
                        <td>${fn:substring(trainingMaterial.alterDate,0,10)}</td>
                        <td>${trainingMaterial.lastAlter.loginName}</td>
                     <c:if test="${empty trainingMaterial.category.parentTMC}">
                        <td>${trainingMaterial.category.TMCName}</td>            
                        <td></td>            
                      </c:if>
                      <c:if test="${not empty trainingMaterial.category.parentTMC}">
                        <td>${trainingMaterial.category.parentTMC.TMCName}</td>            
                        <td>${trainingMaterial.category.TMCName}</td>            
                      </c:if>
                        <td>${trainingMaterial.securityLevel.securityLevelName}</td>
                    <shiro:hasPermission name="showTM">
                        <td ><a href="commonAction_showTrainingMaterialDetail?TMId=${trainingMaterial.TMId}" target="_blank">查看</a></td>
                    </shiro:hasPermission>
                    <shiro:hasPermission name="editTM">
                        <td ><a href="commonAction_updateTrainingMaterial?TMId=${trainingMaterial.TMId}&currentPage=${currentPage}&pageSize=${pageSize}">修改</a></td>
                    </shiro:hasPermission>
                    <shiro:hasPermission name="deleteTM">
                        <td ><a href="#" onclick="onDeleteSolution(${trainingMaterial.TMId})">删除</a></td>
                    </shiro:hasPermission>
                    </tr>
                    </c:forEach>
	     		</table>
	        </div>
	        </c:if>
	        <c:if test="${trainingMaterials.size() <= 0}">
                <div>当前页还没有记录.......<br><br></div>
            </c:if>
	        </div>
	        <!--翻页-->
	        <div class="page_change">
	        	<div>
	        	<span id="sta">共</span><span class="all">${trainingMaterialNum}</span><span id="sta">条记录</span>
	        	<span id="sta">当前为：</span><span class="nowpage">${currentPage}</span><span>/</span><span class="allpage">${totalPage}</span><span id="sta">页</span>
	        	<span id="sta">每页${pageSize}条</span>
	        	</div>
	        	<div class="change">
	        	<span id="sta">跳转到第</span><input class="pacount" type="text" name="pageNum" id="pageNum"/><span id="sta">页</span>
	        	<span id="sta"><a href="#" id="firstPage">&lt;&lt;</a></span><span><a href="#" id="prePage">&lt;</a></span><span>${currentPage}</span>
	        	<span id="sta"><a href="#" id="nextPage">&gt;</a></span><span><a href="#" id="lastPage">&gt;&gt;</a></span>
	        	<span class="move_button"><a href="#" class="moveb" id="Moveb" onclick="go()">跳转</a></span>
	        	</div>
			</div>
		<!--版权-->
		<div class="foot">
			<p id="copy">版权所有&nbsp;&nbsp;&nbsp;2016-2026&nbsp;&nbsp;&nbsp;无锡万达城投资有限责任公司&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				联系号码：0000-0000000&nbsp;&nbsp;&nbsp;邮箱：100000qq.com    </p>
		</div>
		</div>
		</div>
	</body>
</html>