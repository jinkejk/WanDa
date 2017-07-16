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
    <title>签批资料管理</title>
    <script src="js/jquery.min.js"></script>
<script type="text/javascript">
  $(document).ready(function(){
     if(${deleteResult == 'success'}){
    	 alert('删除成功！');
     }else if(${deleteResult == 'failed'}){
    	 alert('删除失败！');
     }
     if(${updateResult == 'success'}){
    	 alert('更新成功！');
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
			     window.location.href="searchSignMaterial_searchSMByPage?currentPage=${currentPage+1}&pageSize=${pageSize}";
			 else
			     window.location.href="searchSignMaterial_searchSMBycontent?searchContent="+$("#searchContent").val()+"&currentPage=${currentPage+1}&pageSize=${pageSize}";
		 }		 
	 });
	 $("#prePage").click(function(){
		 if(${currentPage} <= 1){
			 alert("已经是第一页了！");
		 }else{
			 //上一页
			 if($("#searchContent").val()=='')
			     window.location.href="searchSignMaterial_searchSMByPage?currentPage=${currentPage-1}&pageSize=${pageSize}";
			 else
			     window.location.href="searchSignMaterial_searchSMBycontent?searchContent="+$("#searchContent").val()+"&currentPage=${currentPage-1}&pageSize=${pageSize}";
		 }		 
	 });
	 $("#firstPage").click(function(){
		 if(${currentPage} > 1){
			 if($("#searchContent").val()=='')
		         window.location.href="searchSignMaterial_searchSMByPage?currentPage=1&pageSize=${pageSize}";
		     else
			     window.location.href="searchSignMaterial_searchSMBycontent?searchContent="+$("#searchContent").val()+"&currentPage=1&pageSize=${pageSize}";
		 }
		 else
			 alert("已经是第一页了！");
	 });
	 $("#lastPage").click(function(){
		 if(${currentPage} < ${totalPage}){
			 if($("#searchContent").val()=='')
		         window.location.href="searchSignMaterial_searchSMByPage?currentPage=${totalPage}&pageSize=${pageSize}";
			 else
			     window.location.href="searchSignMaterial_searchSMBycontent?searchContent="+$("#searchContent").val()+"&currentPage=${totalPage}&pageSize=${pageSize}";
		 }
		 else
			 alert("已经是最后一页了！");	 
     }); 
	 
  }); 
  
  function go(){
      if($("#pageNum").val()>=1 && $("#pageNum").val()<= ${totalPage}){
    	  if($("#searchContent").val()=='')
 	          window.location.href="searchSignMaterial_searchSMByPage?currentPage=" + $("#pageNum").val() + "&pageSize=${pageSize}";
 	      else
			  window.location.href="searchSignMaterial_searchSMBycontent?searchContent="+$("#searchContent").val()+"&currentPage=" + $("#pageNum").val() + "&pageSize=${pageSize}";
      }
      else
		  alert("页码不正确！");
   }
	 
   //删除用户事件
   function onDeleteSolution(SMId){
       if(confirm("确定要删除该资料？")){
    	   if($("#searchContent").val()=='')
    	       window.location.href="operateSignMaterial_deleteSMById?SMId=" + SMId + "&currentPage=${currentPage}&pageSize=${pageSize}";
    	   else
    	       window.location.href="operateSignMaterial_deleteSMById?searchContent="+$("#searchContent").val()+"&SMId=" + SMId + "&currentPage=${currentPage}&pageSize=${pageSize}";
	 } 
   }
   
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
	 function delectSignMaterials(){
		 var SMIds = new Array();
		 $("[name = checkItem]:checkbox").each(function () {
         if ($(this).is(":checked")) {
        	 SMIds.push($(this).attr("value"));
         }
     });
		 if(SMIds.length > 0){
			 if(confirm('确定删除'+SMIds.length+'个解决签批资料?')){
			     //批量删除
			     if($("#searchContent").val()=='')
			         window.location.href="operateSignMaterial_delectSignMaterials?SMIds="+SMIds+"&currentPage=${currentPage}&pageSize=${pageSize}";
			     else
			         window.location.href="operateSignMaterial_delectSignMaterials?searchContent="+$("#searchContent").val()+"&SMIds="+SMIds+"&currentPage=${currentPage}&pageSize=${pageSize}";
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
				<div class="table_title">签批资料列表</div>
			<!--添加按钮-->
			<shiro:hasPermission name="addSM">
			<div class="add">
				<a id="Add" href="commonAction_uploadSignMaterial?currentPage=${currentPage}&pageSize=${pageSize}">添加</a>
            </div>
            </shiro:hasPermission>
			<!--搜索-->
			<div class="search">
			<form action="searchSignMaterial_searchSMBycontent" method="post">
                <input type="text" class="search_border" id="searchContent" name="searchContent" value="${searchContent}">
                <input type="hidden" name="pageSize" value="${pageSize}">
                <input type="hidden" name="currentPage" value="1">
                <input type="submit" class="search_submit" id="search" value="搜索">
            </form>
            </div>
            <c:if test="${signMaterials.size() > 0}">
                <div >
                <input type="checkbox" id="selectBox" onclick="selectAll()"><span>全选</span>
                <input type="button" onclick="delectSignMaterials()" class="search_submit" value="批量删除">
                </div>
			<!--表格内容-->
	        <div class="tabel_content">
	     		<table class="trail_tb">
	     			<tr id="First"><td></td><td>标题</td><td>上传时间</td><td>上传人</td><td>修改时间</td><td>修改人</td><td>一级分类</td><td>二级分类</td><td>密级</td><td colspan="3">操作</td></tr>
	     			<c:forEach var="signMaterial" items="${signMaterials}" varStatus="status">	  
                        <tr><td><input type="checkbox" name="checkItem" value="${signMaterial.SMId}"/></td>
                            <td id=${status.index}>${titleList[status.index]}</td>
                            <td>${fn:substring(signMaterial.createDate,0,10)}</td>
                            <td>${signMaterial.author.loginName}</td>
                            <td>${fn:substring(signMaterial.alterDate,0,10)}</td>
                            <td>${signMaterial.lastAlter.loginName}</td>
                    <c:if test="${empty signMaterial.category.parentTMC}">
                        <td>${signMaterial.category.TMCName}</td>            
                        <td></td>            
                    </c:if>
                    <c:if test="${not empty signMaterial.category.parentTMC}">
                        <td>${signMaterial.category.parentTMC.TMCName}</td>            
                        <td>${signMaterial.category.TMCName}</td>            
                    </c:if>
                        <td>${signMaterial.securityLevel.securityLevelName}</td>
            <shiro:hasPermission name="downloadSM">
                <td><a href="#" onclick="downloadSignFile('${signMaterial.signFile}')">下载</a></td>
            </shiro:hasPermission>
            <shiro:hasPermission name="editSM">
                <td><a href="commonAction_updateSignMaterial?SMId=${signMaterial.SMId}&currentPage=${currentPage}&pageSize=${pageSize}">修改</a></td>
            </shiro:hasPermission>
            <shiro:hasPermission name="deleteSM">
                <td><a href="#" onclick="onDeleteSolution(${signMaterial.SMId})">删除</a></td>
            </shiro:hasPermission>
            </tr>
        </c:forEach>
	     		</table>
	        </div>
	        </c:if>
	        <c:if test="${signMaterials.size() <= 0}">
                <div>当前页还没有记录.......<br><br></div>
            </c:if>
	        </div>
	        <!--翻页-->
	        <div class="page_change">
	        	<div>
	        	<span id="sta">共</span><span class="all">${signMaterialNum}</span><span id="sta">条记录</span>
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
