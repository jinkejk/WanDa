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
<title>用户管理</title>
<script src="js/jquery.min.js"></script>

<script type="text/javascript">
$(document).ready(function(){	
	//返回用户审核结果
	if(${changeLogFlagResult == 'success'}){
		alert('操作成功！');
	}else if(${changeLogFlagResult == 'failed'}){
		alert('操作失败！');
	}
	//返回删除用户结果
	if(${deleteResult == 'success'}){
		alert('删除成功！');
	}else if(${deleteResult == 'failed'}){
		alert('删除失败！');
	}
	$("#nextPage").click(function(){
		 if(${userCurrentPage} >= ${totalPage} && count2 == 1){
			 alert("已经是最后一页了！");
		 }else{
			 //下一页	
			 if($("#searchContent").val()=='')
			     window.location.href="operateUser_searchUserByPage?userCurrentPage=${userCurrentPage+1}&userPageSize=${userPageSize}";
			 else
			     window.location.href="operateUser_searchUserByContent?searchContent="+$("#searchContent").val()+"&userCurrentPage=${userCurrentPage+1}&userPageSize=${userPageSize}";
		 }		 
	 });
	 $("#prePage").click(function(){
		 if(${userCurrentPage} <= 1){
			 alert("已经是第一页了！");
		 }else{
			 //上一页
			 if($("#searchContent").val()=='')
			     window.location.href="operateUser_searchUserByPage?userCurrentPage=${userCurrentPage-1}&userPageSize=${userPageSize}";
			 else
			     window.location.href="operateUser_searchUserByContent?searchContent="+$("#searchContent").val()+"&userCurrentPage=${userCurrentPage-1}&userPageSize=${userPageSize}";
             }		 
	 });
	 $("#firstPage").click(function(){
		 if(${userCurrentPage} > 1){
			 if($("#searchContent").val()=='')
		         window.location.href="operateUser_searchUserByPage?userCurrentPage=1&userPageSize=${userPageSize}";
			 else
			     window.location.href="operateUser_searchUserByContent?searchContent="+$("#searchContent").val()+"&userCurrentPage=1&userPageSize=${userPageSize}";
		 }
		 else
			 alert("已经是第一页了！");
	 });
	 $("#lastPage").click(function(){
		 if(${userCurrentPage} < ${totalPage}){
			 if($("#searchContent").val()=='')
		         window.location.href="operateUser_searchUserByPage?userCurrentPage=${totalPage}&userPageSize=${userPageSize}";
			 else
			     window.location.href="operateUser_searchUserByContent?searchContent="+$("#searchContent").val()+"&userCurrentPage=${totalPage}&userPageSize=${userPageSize}";
		 }
		 else
		     alert("已经是最后一页了！");
    }); 
 }); 
 
 function go(){
     if($("#pageNum").val()>=1 && $("#pageNum").val()<= ${totalPage}){
    	 if($("#searchContent").val()=='')
	         window.location.href="operateUser_searchUserByPage?userCurrentPage=" + $("#pageNum").val() + "&userPageSize=${userPageSize}";
	     else
		     window.location.href="operateUser_searchUserByContent?searchContent="+$("#searchContent").val()+"&userCurrentPage=" + $("#pageNum").val() + "&userPageSize=${userPageSize}";
     }
     else
		  alert("页码不正确！");
  } 
 
	 //删除用户事件
    function onDeleteUser(userId, loginName, roleName){
    	if(${currentUser.userId} != userId){
		    if('${currentUser.role.roleName}' != 'administrator' || roleName=='administrator'){
			    alert('权限不足！');
			    return;
		    }
    	}
         if(confirm("确定要删除：" + loginName + "？")){
        	 if($("#searchContent").val()=='')
        	     window.location.href="operateUser_deleteUserById?userId=" + userId + "&userCurrentPage=${userCurrentPage}&userPageSize=${userPageSize}";
        	 else
        	     window.location.href="operateUser_deleteUserById?searchContent="+$("#searchContent").val()+"&userId=" + userId + "&userCurrentPage=${userCurrentPage}&userPageSize=${userPageSize}";	 
         }
	 } 
	 //用户点击审核事件
	function onVerifyUser(userId,loginName,flag){
		 if(${currentUser.userId} != userId){
		    if('${currentUser.role.roleName}' != 'administrator'){
			     alert('权限不足！');
			     return;
		    }			 
		 }

		 //未审核通过
		 if(!flag){
			 if(confirm("确定要允许：" + loginName + "登陆？")){	
				 //修改登陆标志
				 if($("#searchContent").val()=='')
        	         window.location.href="operateUser_changeLogFlag?userId=" + userId + "&userCurrentPage=${userCurrentPage}&userPageSize=${userPageSize}";
        	     else
            	     window.location.href="operateUser_changeLogFlag?searchContent="+$("#searchContent").val()+"&userId=" + userId + "&userCurrentPage=${userCurrentPage}&userPageSize=${userPageSize}";
			 }			 
		 }else{
			 //禁止登陆
			 if(confirm("确定要禁止：" + loginName + "登陆？")){	
				 if($("#searchContent").val()=='')
        	         window.location.href="operateUser_changeLogFlag?userId=" + userId + "&userCurrentPage=${userCurrentPage}&userPageSize=${userPageSize}";				 
        	     else
            	     window.location.href="operateUser_changeLogFlag?searchContent="+$("#searchContent").val()+"&userId=" + userId + "&userCurrentPage=${userCurrentPage}&userPageSize=${userPageSize}";				  
			 } 			 
		 }
	 }
	 //用户修改事件
	 function onEditUser(userId, roleName){
		 if(${currentUser.userId} != userId){
		     if('${currentUser.role.roleName}' != 'administrator' || roleName=='administrator'){
			     alert('权限不足！');
			     return;
		     }
		 }
		 window.location.href="commonAction_editUser?userId="+userId+"&userCurrentPage=${userCurrentPage}&userPageSize=${userPageSize}";		 
	 }
	 
	 //点击全选按钮
	 function selectAll(){
		 if($("#selectBox").is(":checked")){
			 //全选
			 $("[name = checkItem]:input").prop("checked", true);
			 if($("#firstCheck").prop("disabled"))
				 $("#firstCheck").prop("checked",false);
		 }else{
			//全不选
			 $("[name = checkItem]:input").prop("checked", false);
			 if($("#firstCheck").prop("disabled"))
				 $("#firstCheck").prop("checked",false);
		 }
	 }
	 
	 //批量删除用户
	 function delectUsers(){
		 var userIds = new Array();
		 $("[name = checkItem]:checkbox").each(function () {
             if ($(this).is(":checked")) {
            	 userIds.push($(this).attr("value"));
             }
         });
		 if(userIds.length > 0){
			 if(confirm('确定删除这'+userIds.length+'位用户?')){
			     //批量删除
			     if($("#searchContent").val()=='')
			         window.location.href="operateUser_deleteUsers?userIds="+userIds+"&userCurrentPage=${userCurrentPage}&userPageSize=${userPageSize}";
			     else
			         window.location.href="operateUser_deleteUsers?searchContent="+$("#searchContent").val()+"&userIds="+userIds+"&userCurrentPage=${userCurrentPage}&userPageSize=${userPageSize}";
			 }
		 }else{
			 alert('请选择用户！');
		 }
	 }
	 
	 //批量审核用户
	 function verifyUsers(){
		 var userIds = new Array();
		 $("[name = checkItem]:checkbox").each(function () {
             if ($(this).is(":checked")) {
            	 userIds.push($(this).attr("value"));
             }
         });
		 if(userIds.length > 0){
			 if(confirm('若用户为已审核状态将变为待审核，是否确定更改？')){
				 //批量更改状态
				 if($("#searchContent").val()=='')
			         window.location.href="operateUser_changeUsersLogflag?userIds="+userIds+"&userCurrentPage=${userCurrentPage}&userPageSize=${userPageSize}";
			     else
			         window.location.href="operateUser_changeUsersLogflag?searchContent="+$("#searchContent").val()+"&userIds="+userIds+"&userCurrentPage=${userCurrentPage}&userPageSize=${userPageSize}";
			 }
		 }else{
			 alert('请选择用户！');
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
				<div class="table_title">用户列表</div>
			<!--添加按钮-->
			<shiro:hasPermission name="addUser">
			<div class="add">
				<a id="Add" href="commonAction_addUser?userCurrentPage=${userCurrentPage}&userPageSize=${userPageSize}">添加</a>
            </div>
            </shiro:hasPermission>
			<!--搜索-->
			<div class="search">
			<form action="operateUser_searchUserByContent" method="post">
                <input type="text" class="search_border" id="searchContent" name="searchContent" value="${searchContent}">
                <input type="hidden" name="userPageSize" value="${userPageSize}">
                <input type="hidden" name="userCurrentPage" value="1">
                <input type="submit" class="search_submit" id="search" value="搜索">
            </form>
            </div>
            <c:if test="${users.size() > 0}">
                <div >
                    <input type="checkbox" id="selectBox" onclick="selectAll()"><span>全选</span>
                    <input type="button" onclick="delectUsers()" class="search_submit" value="批量删除">
                    <input type="button" onclick="verifyUsers()" class="search_submit" value="批量审核">
                </div>    
			<!--表格内容-->
	        <div class="tabel_content">
	     		<table class="trail_tb">
	     			<tr id="First"><td></td><td>登录名</td><td>真实姓名</td><td>角色</td><td>注册时间</td><td>最近登录</td>
                        <td>职位</td><td>部门</td><td>公司</td><td>电话</td><td>QQ</td><td>备注</td>
                        <td colspan="3">操作</td></tr>
	     			<c:forEach var="user" items="${users}" varStatus="status">	  
                    <tr><td>
                        <c:if test="${user.userId==currentUser.userId}">
                            <input type="checkbox" name="checkItem" id="firstCheck" value="${user.userId}" disabled/>
                        </c:if>
                        <c:if test="${user.userId!=currentUser.userId}">
                            <input type="checkbox" name="checkItem" value="${user.userId}"/>
                        </c:if></td>
                        <td >
                            <c:if test="${user.userId==currentUser.userId}"><font color="red">${user.loginName}</font></c:if>
                            <c:if test="${user.userId!=currentUser.userId}">${loginNameList[status.index]}</c:if>
                       </td>
            <td >${trueNameList[status.index]}</td>
            <td >${user.role.roleRemark}</td>
            <td >${fn:substring(user.registerTime,0,10)}</td>
            <td >${fn:substring(user.lastLogin,0,10)}</td>
            <td class="td1">${user.position}</td>
            <td class="td1">${user.dept}</td>
            <td class="td1">${user.company}</td>
            <td class="td1">${user.telephone}</td>
            <td class="td1">${user.QQ}</td>
            <td class="td1">${user.remark}</td>
            <shiro:hasPermission name="deleteUser">
                <td>
                <c:if test="${user.loginName!=currentUser.loginName}">
                <a href="#" onclick="onDeleteUser(${user.userId},'${user.loginName}','${user.role.roleName}')">删除</a>                
                </c:if>
                <c:if test="${user.loginName==currentUser.loginName}">
                <a href="#" onclick="javascript:alert('无法操作！');">
                <font color="red">删除</font></a>                
                </c:if>
                </td>
            </shiro:hasPermission>
            <shiro:hasPermission name="editorUser">
                <td>
                <c:if test="${user.loginName!=currentUser.loginName}">
                <a href="#" onclick="onEditUser(${user.userId},'${user.role.roleName}')">修改</a>
                </c:if>
                <c:if test="${user.loginName==currentUser.loginName}">
                <a href="#" onclick="javascript:alert('无法操作！');">
                <font color="red">修改</font></a>
                </c:if>
                </td>
            </shiro:hasPermission>
            <shiro:hasPermission name="verifyUser">
            <c:if test="${user.loginName!=currentUser.loginName}">
            <c:if test="${user.logFlag == false}">
                <td><a href="#" onclick="onVerifyUser(${user.userId},'${user.loginName}',${false})"><font color="red">待审核</font></a></td>
            </c:if>
            <c:if test="${user.logFlag == true}">
                <td><a href="#" onclick="onVerifyUser(${user.userId},'${user.loginName}',${true})">已审核</a></td>
            </c:if>
            </c:if>
            <c:if test="${user.loginName==currentUser.loginName}">
                <td>
                <c:if test="${user.logFlag == false}">
                <a href="#" onclick="javascript:alert('无法操作！');">
                    <font color="red">待审核</font>
                    </a>
                 </c:if>
                <c:if test="${user.logFlag == true}">
                <a href="#" onclick="javascript:alert('无法操作！');">已审核 </a>
                </c:if>
                </td>
            </c:if>
            </shiro:hasPermission>                 
            </tr>
            </c:forEach>
	     </table>
	        </div>
	        </c:if>
	        <c:if test="${users.size() <= 0}">
                <div>当前页还没有记录.......<br><br></div>
            </c:if>
	        </div>
	        <!--翻页-->
	        <div class="page_change">
	        	<div>
	        	<span id="sta">共</span><span class="all">${userTotalNum}</span><span id="sta">条记录</span>
	        	<span id="sta">当前为：</span><span class="nowpage">${userCurrentPage}</span><span>/</span><span class="allpage">${totalPage}</span><span id="sta">页</span>
	        	<span id="sta">每页${userPageSize}条</span>
	        	</div>
	        	<div class="change">
	        	<span id="sta">跳转到第</span><input class="pacount" type="text" name="pageNum" id="pageNum"/><span id="sta">页</span>
	        	<span id="sta"><a href="#" id="firstPage">&lt;&lt;</a></span><span><a href="#" id="prePage">&lt;</a></span><span>${userCurrentPage}</span>
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