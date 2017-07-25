<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>

<!doctype html>
<html>
<head>
	<meta charset="utf-8">
	<title>${solution.title}</title>
	<link rel="stylesheet"  href="css/css_mobile.css" >
	<meta content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0;" name="viewport">
</head>

<div class="z_main">
	<div class="z_center">
		<div class="z_top"><a href="javascript:window.history.back();" class="z_topl"></a>解决方案</div>

		<div class="l_bt"><p>${solution.title}</p></div>
		<div class="con_pro">${solution.solution }</div>
	</div>
</div>
<script type="text/javascript" src="js/mobile/flexible.js"></script>
</body>
</html>
