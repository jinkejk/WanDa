<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<title>图纸管理</title>
<script src="js/jquery.min.js"></script>
<script type="text/javascript">
  $(document).ready(function(){
     if(${deleteDrawingResult == 'success'}){
    	 alert('删除成功！');
     }else if(${deleteDrawingResult == 'failed'}){
    	 alert('删除失败！');
     }
     if(${updateDrawingResult == 'success'}){
    	 alert('跟新成功！');
     }else if(${updateDrawingResult == 'failed'}){
    	 alert('更新失败！');
     }
     if(${addDrawingResult == 'success'}){
    	 alert('添加成功！');
     }else if(${addDrawingResult == 'failed'}){
    	 alert('添加失败！');
     }
	 $("#nextPage").click(function(){
		 if(${currentPage} >= ${totalPage}){
			 alert("已经是最后一页了！");
		 }else{
			 //下一页
			 if($("#searchContent").val()=='')
				 window.location.href="searchDrawing_searchDrawingByPage?currentPage=${currentPage+1}&pageSize=${pageSize}";
		     else
				 window.location.href="searchDrawing?searchContent=" + $("#searchContent").val() + "&currentPage=${currentPage+1}&pageSize=${pageSize}";
		     }		 
	 });
	 $("#prePage").click(function(){
		 if(${currentPage} <= 1){
			 alert("已经是第一页了！");
		 }else{
			 //上一页
			 if($("#searchContent").val()=='')
			     window.location.href="searchDrawing_searchDrawingByPage?currentPage=${currentPage-1}&pageSize=${pageSize}";
			 else
				 window.location.href="searchDrawing?searchContent=" + $("#searchContent").val() + "&currentPage=${currentPage-1}&pageSize=${pageSize}";
		 }		 
	 });
	 $("#firstPage").click(function(){
		 if(${currentPage} > 1){
			 if($("#searchContent").val()=='')
		         window.location.href="searchDrawing_searchDrawingByPage?currentPage=1&pageSize=${pageSize}";
		     else
				 window.location.href="searchDrawing?searchContent=" + $("#searchContent").val() + "&currentPage=1&pageSize=${pageSize}";
		 
		 }else
			 alert("已经是第一页了！");
	 });
	 $("#lastPage").click(function(){
		 if(${currentPage} < ${totalPage}){
			 if($("#searchContent").val()=='')
		         window.location.href="searchDrawing_searchDrawingByPage?currentPage=${totalPage}&pageSize=${pageSize}";
			 else
				 window.location.href="searchDrawing?searchContent=" + $("#searchContent").val() + "&currentPage=${totalPage}&pageSize=${pageSize}";
		 }else
			 alert("已经是最后一页了！");	 
     }); 
	 
  }); 
  
  function go(){
      if($("#pageNum").val()>=1 && $("#pageNum").val()<= ${totalPage}){
    	  if($("#searchContent").val()=='')
 	          window.location.href="searchDrawing_searchDrawingByPage?currentPage=" + $("#pageNum").val() + "&pageSize=${pageSize}";
    	  else
		      window.location.href="searchDrawing?searchContent=" + $("#searchContent").val() + "&currentPage="+ $("#pageNum").val()+"&pageSize=${pageSize}";
      }else
		  alert("页码不正确！");
   }

   //删除用户事件
   function onDeleteDrawing(drawingId){
       if(confirm("确定要删除图纸？")){
    	   if($("#searchContent").val()=='')
    	       window.location.href="operateDrawing_deleteDrawingById?drawingId=" + drawingId + "&currentPage=${currentPage}&pageSize=${pageSize}";
    	   else
    	       window.location.href="operateDrawing_deleteDrawingById?searchContent=" + $("#searchContent").val() + "&drawingId=" + drawingId + "&currentPage=${currentPage}&pageSize=${pageSize}";
    		   
       } 
   }  
</script> 
</head>

<body>
<center>
    <div>图纸管理</div>
    <shiro:hasPermission name="addDrawing">
        <div><a href="commonAction_uploadDrawing?currentPage=${currentPage}&pageSize=${pageSize}">添加</a></div><br><br>
    </shiro:hasPermission>
     <form action="searchDrawing" method="post">
        <input type="text" id="searchContent" name="searchContent" value="${searchContent}">
        <input type="hidden" name="pageSize" value="${pageSize}">
        <input type="hidden" name="currentPage" value="1">
        <input type="submit" id="search" value="搜索">
    </form><br><br>
    <c:if test="${drawings.size() > 0}">
    <table border="1">
        <tr>
            <td>ID</td>
            <td>图纸名称</td>
            <td>上传日期</td>
            <td>上传人</td> 
            <td>修改日期</td>
            <td>上次修改人</td>          
            <td>图纸说明</td>
            <td>保密级别</td>
            <td colspan="3">操作</td>
        </tr>
        <c:forEach var="drawing" items="${drawings}" varStatus="status">
            <tr>            
            <td>${drawing.stringId}</td>
            <td id=${status.index} >${titleList[status.index]}
                <c:if test="${fn:length(drawing.drawingName) > 40}">
                    ${'.....'}
                </c:if>
            </td>
            <td>${fn:substring(drawing.uploadDate,0,10)}</td>
            <td>${drawing.author.loginName}</td>            
            <td>${fn:substring(drawing.alterDate,0,10)}</td>
            <td>${drawing.lastAlter.loginName}</td>
            <td>${fn:substring(drawing.remark,0,40)}
                <c:if test="${fn:length(drawing.remark) > 40}">
                    ${'.....'}
                </c:if>
            </td>
            <td>${drawing.securityLevel.securityLevelValue}</td>
            <td><a href="searchDrawing_searchDrawingById?drawingId=${drawing.drawingId}" target="_blank">查看</a></td>
            <shiro:hasPermission name="editDrawing">
                <td><a href="commonAction_updateDrawing?drawingId=${drawing.drawingId}">修改</a></td>
            </shiro:hasPermission>
            <shiro:hasPermission name="deleteDrawing">
                <td><a href="#" onclick="onDeleteDrawing(${drawing.drawingId})">删除</a></td>
            </shiro:hasPermission>
            </tr>
        </c:forEach>
    </table>
    </c:if>
    <c:if test="${drawings.size() <= 0}">
        <div>当前页还没有图纸.......<br><br></div>
    </c:if>
    <br>
    <div>
		共 ${drawingTotalNum} 条记录  当前为:${currentPage}/${totalPage}页   每页${pageSize}条
	</div><br><br>
	<div>
		跳到第 <input name="pageNum" id="pageNum" type="text">	页 
		<a href="#" id="firstPage">&lt;&lt;</a>
		<a href="#" id="prePage">&lt;</a> 
		<a href="#" id="nextPage">&gt;</a>
		<a href="#" id="lastPage">&gt;&gt;</a> 
		<input type="button" id="go" value="跳转" onclick="go()">
	</div>	
</center>
</body>
</html>