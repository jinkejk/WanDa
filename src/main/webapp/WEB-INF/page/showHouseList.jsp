<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<title>房型筛选比较系统</title>
<script src="js/jquery.min.js"></script>
<script type="text/javascript">
  $(document).ready(function(){
     if(${deleteResult == 'success'}){
    	 alert('删除成功！');
     }else if(${deleteResult == 'failed'}){
    	 alert('删除失败！');
     }
     if(${updateHouseResult == 'success'}){
    	 alert('跟新成功！');
     }else if(${updateHouseResult == 'failed'}){
    	 alert('更新失败！');
     }
     if(${addHouseResult == 'success'}){
    	 alert('添加成功！');
     }else if(${addHouseResult == 'failed'}){
    	 alert('添加失败！');
     }
	 $("#nextPage").click(function(){
		 if(${currentPage} >= ${totalPage}){
			 alert("已经是最后一页了！");
		 }else{
			 //下一页
			 if($("#searchContent").val()=='')
				 window.location.href="searchHouse_searchHouseByPage?currentPage=${currentPage+1}&pageSize=${pageSize}";
		     else
				 window.location.href="searchHouse?searchContent=" + $("#searchContent").val() + "&currentPage=${currentPage+1}&pageSize=${pageSize}";
		     }		 
	 });
	 $("#prePage").click(function(){
		 if(${currentPage} <= 1){
			 alert("已经是第一页了！");
		 }else{
			 //上一页
			 if($("#searchContent").val()=='')
			     window.location.href="searchHouse_searchHouseByPage?currentPage=${currentPage-1}&pageSize=${pageSize}";
			 else
				 window.location.href="searchHouse?searchContent=" + $("#searchContent").val() + "&currentPage=${currentPage-1}&pageSize=${pageSize}";
		 }		 
	 });
	 $("#firstPage").click(function(){
		 if(${currentPage} > 1){
			 if($("#searchContent").val()=='')
		         window.location.href="searchHouse_searchHouseByPage?currentPage=1&pageSize=${pageSize}";
		     else
				 window.location.href="searchHouse?searchContent=" + $("#searchContent").val() + "&currentPage=1&pageSize=${pageSize}";
		 
		 }else
			 alert("已经是第一页了！");
	 });
	 $("#lastPage").click(function(){
		 if(${currentPage} < ${totalPage}){
			 if($("#searchContent").val()=='')
		         window.location.href="searchHouse_searchHouseByPage?currentPage=${totalPage}&pageSize=${pageSize}";
			 else
				 window.location.href="searchHouse?searchContent=" + $("#searchContent").val() + "&currentPage=${totalPage}&pageSize=${pageSize}";
		 }else
			 alert("已经是最后一页了！");	 
     }); 
	 
  }); 
  
  function go(){
      if($("#pageNum").val()>=1 && $("#pageNum").val()<= ${totalPage}){
    	  if($("#searchContent").val()=='')
 	          window.location.href="searchHouse_searchHouseByPage?currentPage=" + $("#pageNum").val() + "&pageSize=${pageSize}";
    	  else
		      window.location.href="searchHouse?searchContent=" + $("#searchContent").val() + "&currentPage="+ $("#pageNum").val()+"&pageSize=${pageSize}";
      }else
		  alert("页码不正确！");
   }

   //删除用户事件
   function onDeleteHouse(houseId){
       if(confirm("确定要删除该户型？")){
    	   if($("#searchContent").val()=='')
    	       window.location.href="operateHouse_deleteHouseById?houseId=" + houseId + "&currentPage=${currentPage}&pageSize=${pageSize}";
    	   else
    	       window.location.href="operateHouse_deleteHouseById?searchContent=" + $("#searchContent").val() + "&houseId=" + houseId + "&currentPage=${currentPage}&pageSize=${pageSize}";
    		   
       } 
   }
</script> 
</head>

<body>
<center>
    <div>房型筛选比较系统</div>
    <shiro:hasPermission name="addHouse">
        <div><a href="commonAction_uploadHouse?currentPage=${currentPage}&pageSize=${pageSize}">添加</a></div><br><br>
    </shiro:hasPermission>
     <form action="searchHouse" method="post">
        <input type="text" id="searchContent" name="searchContent" value="${searchContent}">
        <input type="hidden" name="pageSize" value="${pageSize}">
        <input type="hidden" name="currentPage" value="1">
        <input type="submit" id="search" value="搜索">
    </form><br><br>
    <c:if test="${houses.size() > 0}">
    <table border="1">
        <tr>
            <td></td>
            <td>户型</td>
            <td>楼盘</td>
            <td>面积</td>
            <td>得房率</td> 
            <td>总层数</td>
            <td>关注度</td>          
            <td>创建日期</td>
            <td>操作</td>
        </tr>
        <c:forEach var="house" items="${houses}" varStatus="status">
            <tr>
            <td><img src="../house/small/${house.imgName}" width="70" height="50"></td>            
            <td>${HXNameList[status.index]}
                <c:if test="${fn:length(house.HXName) > 40}">
                    ${'.....'}
                </c:if>                
            </td>
            <td>${LPNameList[status.index]}
                <c:if test="${fn:length(house.LPName) > 40}">
                    ${'.....'}
                </c:if>
            </td>
            <td>${house.area}</td>
            <td>${house.rate}</td>            
            <td>${house.layer}</td>
            <td>${house.attention}</td>
            <td>${fn:substring(house.createDate,0,10)}</td>
            <td><a href="searchHouse_searchHouseById?houseId=${house.houseId}" target="_blank">详细</a></td>
            <shiro:hasPermission name="editHouse">
                <td><a href="commonAction_updateHouse?houseId=${house.houseId}&currentPage=${currentPage}&pageSize=${pageSize}">修改</a></td>
            </shiro:hasPermission>
            <shiro:hasPermission name="deleteHouse">
                <td><a href="#" onclick="onDeleteHouse(${house.houseId})">删除</a></td>
            </shiro:hasPermission>
            </tr>
        </c:forEach>
    </table>
    </c:if>
    <c:if test="${houses.size() <= 0}">
        <div>当前页还没有房型.......<br><br></div>
    </c:if>
    <br>
    <div>
		共 ${houseTotalNum} 条记录  当前为:${currentPage}/${totalPage}页   每页${pageSize}条
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