<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>        

<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8"  http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
		
		<title>${trainingMaterial.title}</title>
		<link rel="stylesheet" type="text/css" href="css/pub.css"/>
		<link rel="stylesheet" type="text/css" href="css/third_trail.css"/>
	</head>
	<body>
		<div id="bg" >
		<div class="head" id="Head">
			<div id="top_logo">
			<!--无锡万达城销售物业研发平台-->
			<div id="t_img"><a href="index.jsp"><img  src="image/problem/tlogo.png"  /></a></div>
			</div>
			<span class="login_message">
			<span class="welcome"><img id="userimg" src="image/problem/user.png"/>欢迎：<span class="username" id="Username"><shiro:principal/></span></span>
			<span class="logout"><img id="outimg" src="image/problem/out.png"/><a href="userLogin_logout">退出</a></span>
			</span>
			<div class="third_banner" id="third_Banner"></div>	
			</div>
		<div class="contain">
			<div class="ppt_panel" id="ppt_Panel">
			<div class="third_tflower">
				<img src="image/problem/tflower.png" />
			</div>
				<div class="ppt_tborder">
					<img src="image/third/ppt_tborder.png" />
				</div>
				<div class="ppt_content" id="ppt_Content">
					<div class="ppt_t"><h2 id="ppt_T">标题：</h2><h2 class="ppt_title">${trainingMaterial.title}</h2></div>
					<div class="ppt_push">
						<h2 id="ppt_P">点击率：</h2><h2 class="ppt_count" id="ppt_Count">${trainingMaterial.clickNum}</h2>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						<h2 id="ppt_D">创建日期：</h2><h2 class="ppt_date" dir="ppt_Date">${trainingMaterial.createDate}</h2><!-- <h2 class="ppt_time" id="ppt_Time">13:13:13</h2>-->
					</div>

				<div class="ppt_con">
                  <iframe name="myframe" src="generic/web/viewer.html?file=../../../trainingMaterialPpt/${fn:substring(trainingMaterial.pptName,0,fn:indexOf(trainingMaterial.pptName,'.'))}.pdf" 
                      width="896px" height="520px"></iframe>
				</div><br>
				<div class="ppt_other"><h2 id="ppt_O">备注：</h2><p class="ppt_othcon">${trainingMaterial.remark}</p></div>
				</div>
				<div class="ppt_bborder">
					<img src="image/third/pptborder.png" />
				</div>
			</div>
			<div class="third_bflower">
				<img src="image/problem/bfl.png" />
			</div>
			<div class="foot_copy">
				<p>版权所有&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;2016-2026&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;无锡万达城投资有限责任公司&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					联系号码：0000-0000000&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;邮箱：100000qq.com</p>
			</div>
		</div>
		</div>
	</body>
</html>
