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
	<link rel="stylesheet" type="text/css" href="css/kind-table.css"/>
    <title>管理分类</title>
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
			 if(${module == null}){				 
			 if($("#searchContent").val()=='')
			     window.location.href="searchCategory_searchCategoryByPage?currentPage=${currentPage+1}&pageSize=${pageSize}";
			 else
			     window.location.href="searchCategory_searchCategoryBycontent?searchContent="+$("#searchContent").val()+"&currentPage=${currentPage+1}&pageSize=${pageSize}";
			 }else{
				 if($("#searchContent").val()=='')
			         window.location.href="searchCategory_searchCategoryByModule?module=${module}&currentPage=${currentPage+1}&pageSize=${pageSize}";
			     else
				     window.location.href="searchCategory_searchCategoryBycontent?module=${module}&searchContent="+$("#searchContent").val()+"&currentPage=${currentPage+1}&pageSize=${pageSize}";  
			}
		 }		 
	 });
	 $("#prePage").click(function(){
		 if(${currentPage} <= 1){
			 alert("已经是第一页了！");
		 }else{
			 //上一页
			 if(${module == null}){	
			 if($("#searchContent").val()=='')
			     window.location.href="searchCategory_searchCategoryByPage?currentPage=${currentPage-1}&pageSize=${pageSize}";
			 else
			     window.location.href="searchCategory_searchCategoryBycontent?searchContent="+$("#searchContent").val()+"&currentPage=${currentPage-1}&pageSize=${pageSize}";
			 }else{
				 if($("#searchContent").val()=='')
			         window.location.href="searchCategory_searchCategoryByModule?module=${module}&currentPage=${currentPage-1}&pageSize=${pageSize}";
			     else
				     window.location.href="searchCategory_searchCategoryBycontent?module=${module}&searchContent="+$("#searchContent").val()+"&currentPage=${currentPage-1}&pageSize=${pageSize}";  
				     }
			 }		 
	 });
	 $("#firstPage").click(function(){
		 if(${currentPage} > 1){
			 if(${module == null}){	
			 if($("#searchContent").val()=='')
		         window.location.href="searchCategory_searchCategoryByPage?currentPage=1&pageSize=${pageSize}";
		     else
			     window.location.href="searchCategory_searchCategoryBycontent?searchContent="+$("#searchContent").val()+"&currentPage=1&pageSize=${pageSize}";
			 }else{
				 if($("#searchContent").val()=='')
			         window.location.href="searchCategory_searchCategoryByModule?module=${module}&currentPage=1&pageSize=${pageSize}";
			     else
				     window.location.href="searchCategory_searchCategoryBycontent?module=${module}&searchContent="+$("#searchContent").val()+"&currentPage=1&pageSize=${pageSize}";  
			 }
			 }
		 else
			 alert("已经是第一页了！");
	 });
	 $("#lastPage").click(function(){
		 if(${currentPage} < ${totalPage}){
			 if(${module == null}){	
			 if($("#searchContent").val()=='')
		         window.location.href="searchCategory_searchCategoryByPage?currentPage=${totalPage}&pageSize=${pageSize}";
			 else
			     window.location.href="searchCategory_searchCategoryBycontent?searchContent="+$("#searchContent").val()+"&currentPage=${totalPage}&pageSize=${pageSize}";
			 }else{
				 if($("#searchContent").val()=='')
			         window.location.href="searchCategory_searchCategoryByModule?module=${module}&currentPage=${totalPage}&pageSize=${pageSize}";
			     else
				     window.location.href="searchCategory_searchCategoryBycontent?module=${module}&searchContent="+$("#searchContent").val()+"&currentPage=${totalPage}&pageSize=${pageSize}";

			 }
			 }
		 else
			 alert("已经是最后一页了！");	 
     }); 
	 
  }); 
  
  function go(){
      if($("#pageNum").val()>=1 && $("#pageNum").val()<= ${totalPage}){
    	  if(${module == null}){	
    	  if($("#searchContent").val()=='')
 	          window.location.href="searchCategory_searchCategoryByPage?currentPage=" + $("#pageNum").val() + "&pageSize=${pageSize}";
 	      else
			  window.location.href="searchCategory_searchCategoryBycontent?searchContent="+$("#searchContent").val()+"&currentPage=" + $("#pageNum").val() + "&pageSize=${pageSize}";
    	  }else{
    		  if($("#searchContent").val()=='')
			      window.location.href="searchCategory_searchCategoryByModule?module=${module}&currentPage="+$("#pageNum").val()+"&pageSize=${pageSize}";
			  else
				  window.location.href="searchCategory_searchCategoryBycontent?module=${module}&searchContent="+$("#searchContent").val()+"&currentPage=" + $("#pageNum").val() + "&pageSize=${pageSize}";

    	  }
    	  }
      else
		  alert("页码不正确！");
   }
	 
   //删除用户事件
   function onDeleteSolution(TMCId){
       if(confirm("确定要删除该分类？")){
    	   if($("#searchContent").val()=='')
    	       window.location.href="operateCategory_deleteCategoryById?module=${module}&TMCId=" + TMCId + "&currentPage=${currentPage}&pageSize=${pageSize}";
    	   else
    	       window.location.href="operateCategory_deleteCategoryById?module=${module}&searchContent="+$("#searchContent").val()+"&TMCId=" + TMCId + "&currentPage=${currentPage}&pageSize=${pageSize}";
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
				<div class="table_title">类别列表</div>
			<!--添加按钮-->
			<shiro:hasPermission name="addCategory">
			<div class="add">
				<a id="Add" href="commonAction_uploadCategory?currentPage=${currentPage}&pageSize=${pageSize}">添加</a>
            </div>
            </shiro:hasPermission>
			<!--搜索-->
			<div class="search">
			<form action="searchCategory_searchCategoryBycontent" method="post">
                <input type="text" class="search_border" id="searchContent" name="searchContent" value="${searchContent}">
                <input type="hidden" name="pageSize" value="${pageSize}">
                <input type="hidden" name="currentPage" value="1">
                <input type="hidden" name="module" value="${module}">
                <input type="submit" class="search_submit" id="search" value="搜索">
            </form>
            </div>
            <div class="section">
                <span id="Se"><a href="searchCategory_searchCategoryByModule?module=training&currentPage=1&pageSize=${pageSize}">培训资料模块</a></span>
                <span id="Se"><a href="searchCategory_searchCategoryByModule?module=sign&currentPage=1&pageSize=${pageSize}">签批资料模块</a></span>
                <span id="Se"><a href="searchCategory_searchCategoryByModule?module=solution&currentPage=1&pageSize=${pageSize}">问题档案模块</a></span>    
            </div>
            <c:if test="${categorys.size() > 0}">
			<!--表格内容-->
	        <div class="table_content">
	     		<table class="trail_tb">
	     			<tr id="First"><td>类名</td><td>父类名</td><td>所属模块</td><td>备注</td>
                    <td colspan="2">操作</td></tr>
	     			<c:forEach var="category" items="${categorys}" varStatus="status">	  
            <tr>
            <td id=${status.index}>${titleList[status.index]}</td>
            <td >${category.parentTMC.TMCName}</td>
            <td ><c:if test="${category.module=='training'}">培训资料</c:if>
            <c:if test="${category.module=='sign'}">签批资料</c:if>
            <c:if test="${category.module=='solution'}">问题档案库</c:if>
            </td>
            <td >${category.remark}</td>
            <shiro:hasPermission name="editCategory">
                <td><a href="commonAction_updateCategory?TMCId=${category.TMCId}&currentPage=${currentPage}&pageSize=${pageSize}">修改</a></td>
            </shiro:hasPermission>
            <shiro:hasPermission name="deleteCategory">
                <td><a href="#" onclick="onDeleteSolution(${category.TMCId})">删除</a></td>
            </shiro:hasPermission>
            </tr>
        </c:forEach>
        </table>
	        </div>
	        </c:if>
	        <c:if test="${categorys.size() <= 0}">
                <div>当前页还没有记录.......<br><br></div>
            </c:if>
	        </div>
	        <!--翻页-->
	        <div class="page_change">
	        	<div>
	        	<span id="sta">共</span><span class="all">${categoryNum}</span><span id="sta">条记录</span>
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