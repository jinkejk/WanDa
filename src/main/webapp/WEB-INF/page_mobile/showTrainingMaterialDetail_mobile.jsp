<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<!doctype html>
<html>
<head>
	<meta charset="utf-8">
	<title>${trainingMaterial.title}</title>
	<link rel="stylesheet"  href="css/css_mobile.css" >
	<meta content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0;" name="viewport">
</head>

<body>
<div class="z_main">
	<div class="z_center">
		<div  class="z_top">培训内容</div>
		<div class="l_bt"><p>${trainingMaterial.title}</p></div>
		<div class="djl">
			<div style="margin-left: 5%">点击率: ${trainingMaterial.clickNum}</div>
			<div class="dj1">创建时间: ${fn:substring(trainingMaterial.createDate,0,10)}</div>
		</div>
		<div class="nry">
			<iframe name="myframe" src="generic/web/viewer_mobile.html?file=../../../trainingMaterialPpt/${fn:substring(trainingMaterial.pptName,0,fn:indexOf(trainingMaterial.pptName,'.'))}.pdf"
			 width="90%" height="650px"></iframe>
		</div>
		<div class="bz">备注: ${trainingMaterial.remark}</div>
	</div>
</div>
<script type="text/javascript" src="js/mobile/flexible.js"></script>
</body>
</html>