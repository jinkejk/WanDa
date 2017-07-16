<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<!--
<script type="text/javascript"> 
    //提交
    function decodeQRImg(){
    	$("#decodeResult").hide();
    	if(imgValidate()){
    		$("#decodeResult").html('上传中......');
        	$("#decodeResult").show();
    		$("#form1").submit();
    		var file = $("#QRfile")   
    		file.after(file.clone().val(""));     
    		file.remove();
    	}
    }   

    //判断文件是否符合要求
    function imgValidate(){
        var filepath = $("input[name='QRfile']").val();
        var extStart = filepath.lastIndexOf(".");
        var ext = filepath.substring(extStart, filepath.length).toUpperCase();

        if(filepath!=""){        	
            if (ext != ".BMP" && ext != ".PNG" && ext != ".GIF" && ext != ".JPG" && ext != ".JPEG") {
                alert("图片限于bmp,png,gif,jpeg,jpg格式！");           
                return false;
            } 
        }
        return true;
    }
    
    //按钮点击事件
    function btnClick(){
    	$("#QRfile").click();
    }
</script>    
   <a href="commonAction_searchSystem" target="_blank">专家查询系统</a><br><br>
   <a href="commonAction_showHouseList" target="_blank">房型筛选比较系统</a><br><br>
   <a href="commonAction_lastTrainingMaterial" target="_blank">培训资料</a><br><br>
   <a href="commonAction_manageLogin?" target="_blank">后台管理</a><br><br>
   <form action="commonAction_decodeQRImg" id="form1" method="post" enctype="multipart/form-data" style="display: none;">
       识别二维码：<input type="file" name="QRfile" id="QRfile" onchange="decodeQRImg()" accept="image/*" value="识别二维码"/>
   </form>
   <img id="qrBtn" onclick="btnClick()" src='image/camera.jpg' width='200px' height='200px' onmousedown=""></img><br> 
   <div id="decodeResult"><font color="red">${decodeResult}</font></div><br> 
 -->
 <!DOCTYPE html>
<html>
	<!--忘记密码和注册？-->
	<head>
	 	<meta charset="UTF-8" http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
	 	<meta name="keywords" content="万达，万达城，无锡，物业" />
	 	<title>万达主页</title>
		<script type="text/javascript" src="js/jquery-1.8.3.min.js"></script>
		<link href="css/f_index.css" rel="stylesheet" type="text/css">
		<script type="text/javascript">
			$(document).ready(function(){
				$("#Sliper").hover(function(){
				
//				$("#Ch").css("display","block").animate({left:"842px"},300);
				$("#Ch").css("display","block"); 
				
				},function(){
					// $("#Ch").animate({left:"1180px"},600).css("display","none");
					$("#Ch").css("display","none"); 
   					
  				});
  				$("#Ch").hover(function(){
  					$("#Ch").css("display","block");
  					
  				},function(){
  					$("#Ch").css("display","none");
  				});
			});
		</script>
	</head>
	<body>
		
		<div class="head">
		
			<div id="top_logo">
			<!--无锡万达城销售物业研发平台-->
			<div id="t_img"><img  src="image/findex/headlogo.png"  /></div>
			</div>
						
			<shiro:guest>
			<div class="login_button">
			    <a href="login.jsp" id="example">登录|注册</a>&nbsp;&nbsp;&nbsp;&nbsp;<a href="commonAction_manageLogin" target="_blank">后台管理</a>
			</div>
			</shiro:guest>
			
			<shiro:user>
			<span class="login_message">
			<span class="welcome"><img id="userimg" src="image/problem/user.png"/>欢迎：<span class="username" id="Username"><shiro:principal/></span></span>
			<span><a href="commonAction_manageLogin" target="_blank">后台管理</a></span>
			<span class="logout"><img id="outimg" src="image/problem/out.png"/><a href="userLogin_logout">退出</a></span>
			</span>
			</shiro:user>
			
			
			<div class="top_banner">
			<!--导航-->
					<ul class="switch">
						<li><a href="#">产品研发大事记</a></li>
						<li><a href="commonAction_lastSolutions">问题档案库</a></li>
						<li><a href="#">图纸二维码</a></li>	
						<li style="margin-top: -40px;text-align: center;"><img src="image/findex/bannerlogo.png"></li>
						<li style="margin-left:110px "><a href="commonAction_lastSignMaterial">签批资料</a></li>
						<li><a href="commonAction_lastTrainingMaterial">培训资料</a></li>
						<li><a href="http://app.connect.trimble.com" target="_blank">BIM</a></li>
					</ul>
				</div>
				
		</div>
		
		<div class="container">
		<div>
			<img id="lb_Img" src="image/findex/zx.png"/>
		</div>
		<div id="C">
		<div id="Sliper"></div>
		<div id="Ch">
			<div class="ch_connect">
			<div class="lxr"><img id="lxrimg" src="image/findex/lxr.png" />小组通讯录</div>
			<div class="ewm"><img id="ewmimg" src="image/findex/ewm.png" />手机二维码</div>
			</div>
		</div>
		</div>
		</div>
		
		
	</body>
</html>