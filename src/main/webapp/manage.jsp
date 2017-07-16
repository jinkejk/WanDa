<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8" http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
	<link rel="stylesheet" type="text/css" href="css/common.css"/>
	<link rel="stylesheet" type="text/css" href="css/pub-head.css"/>
	<link rel="stylesheet" type="text/css" href="css/default.css"/>
	<script src="js/jquery.min.js"></script>
	<script type="text/javascript" src="js/banner.js"></script>
    <title>后台管理</title>
</head>
<body>
	     <div id="bg">
		 <div class="head">
			<!--无锡万达城销售物业研发平台-->
			<div id="top_logo">
			<a><img  src="image/problem/tlogo.png"  /></a>
			</div>
			<span class="login_message">
			<shiro:user>
			<span class="welcome"><img id="userimg" src="image/problem/user.png"/>欢迎：<span class="username" id="Username"><shiro:principal/></span></span>
			<span class="logout"><img id="outimg" src="image/problem/out.png"/><a href="${pageContext.request.contextPath}/userLogin_logout">退出</a></span>
			</shiro:user>
			</span>
			<div class="top_banner">
			<!--导航-->
				<ul class="switch">
						<li><a onclick="move3('commonAction_showSolutionList')" href="#">问题档案库</a></li>
						<li><a href="#">图纸二维码</a></li>
						<li><a onclick="move3('commonAction_showSignMaterialList')" href="#">签批资料</a></li>
						<li><a onclick="move3('commonAction_showTrainingMaterialList')" href="#">培训资料</a></li>
						<li><a onclick="move3('commonAction_showCategoryList')" href="#">分类管理</a></li>
						<li><a onclick="move3('commonAction_showUserList')" href="#">用户管理</a></li>
				</ul>
			</div>
		</div>
		<!--以下是页面内容-->
		<iframe  class="mainFrame" id="external-frame" scrolling="no" src="commonAction_showSolutionList" "></iframe>
		
		</div>
	</body>
</html>