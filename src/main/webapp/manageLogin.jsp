<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8"  http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
		<title>管理员登录</title>
		<link rel="stylesheet" type="text/css" href="css/login.css"/>
		<script type="application/javascript" src="js/jquery.min.js"></script>
        <script type="text/javascript">
        var urlFlag = 'front';
	$(document).ready(function() {
		//$("#btn_register").click(regClick);
		if(${result == 'verify'}){
			alert("管理员审核中！");
		}
		if(${!empty message}){
			alert('${message}');
		}
		if(${registerResult == 'success'}){
			alert("注册成功！");
		}else if(${registerResult == 'failed'}){
			alert("注册失败！");
		}
		//判断当前的url是后台还是前台
		if(parent.document.URL.indexOf('Login')>=0 && document.URL.indexOf('commonAction_manageLogin')==-1)
			urlFlag='manage';
		else
			urlFlag='front';
		$("input[name='urlFlag']").attr("value",urlFlag);
		//alert($("input[name='urlFlag']").val());
	});	
	
	//监听回车事件
    document.onkeydown=keyDownSearch;
    function keyDownSearch(e) {    
        // 兼容FF和IE和Opera    
        var theEvent = e || window.event;    
        var code = theEvent.keyCode || theEvent.which || theEvent.charCode;    
        if (code == 13) {    
       	 $("#loginBtn").click();    
            return false;    
        }    
        return true;    
  }
    //
</script>		
	</head>
	<body leftmargin="0" onload="changeImg()">
		<div class="big_logo">
			<img src="image/third/biglogo.png" />
		</div>
		<div class="top_weibo">
			<div class="login_banner"><span class="wei"><input id="wb" type="checkbox" /></span><h2>注册后自动关注博客</h2></div>
		</div>
		
		<div class="page_box">
		<div id="LoginBox" class="login_box">
       
       <form action="userLogin_manageLogin" id="form1" method="post">
        <div class="all_row">
        <div class="row">
        	账号:&nbsp;&nbsp;&nbsp;&nbsp;<span class="inputBox">
                <input type="text" id="userName" name="userName" value="${userName}" placeholder="账号/邮箱" />
            </span>
             <span title="提示" class="warning1" id="warn1">*</span>
        </div>
        <div class="row">
            密码: &nbsp;&nbsp;&nbsp;&nbsp;<span class="inputBox">
                <input type="password" id="password" name="password" placeholder="密码" />
            </span>
            <span class="remember"><input type="checkbox"/>记住密码</span>
            <span class="warning2" id="warn2">*</span>
        </div>
        <div class="row_check">
            验证码: &nbsp;&nbsp;
                <input id="vcode" type="text" placeholder="验证码" onfocus="this.value=''" onblur="if(this.value=='')this.value='验证码'" />
                <span id="code" title="换一张"></span>
                       
        </div>
      	<div class="login_submit">
        	<a href="#" id="loginBtn" >登录</a>
        </div>
        </div>
        <input type="hidden" name="urlFlag">
        </form>
        
    	</div>
    	<div class="foot" id="Foot">
			<p id="copy">版权所有&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;2016-2026&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;无锡万达城投资有限责任公司&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;联系号码：0000-0000000&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;邮箱：100000qq.com    </p>
		</div>
		</div>
		<script type="application/javascript" src="js/login.js"></script>
	</body>
</html>