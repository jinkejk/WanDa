<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
		
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8"  http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
		<title>签批资料</title>
 		<link rel="stylesheet" type="text/css" href="css/pub.css" />
		<link rel="stylesheet" type="text/css" href="css/sub_document.css" />
		<link href="css/jquery.hovertree.0.1.2.min.css" type="text/css" rel="Stylesheet" />
		<script type="text/javascript" src="js/jquery.min.js"></script>
		<script type="text/javascript" src="js/jquery.hovertree.0.1.2.min.js"></script>
		<script type="text/javascript">
		$(document).ready(function(){
			  var contents = '';
			  var flag = 0;//判断是否存在子分类
			  //动态生成左边的目录
				$.each(${firstLevelTMC}, function(index, firstLevel){
					//动态生成一级目录
					contents += "<div><h3><b></b><a href='#' onclick='menuClick("+firstLevel.TMCId+")'>"+firstLevel.TMCName+"</a></h3>";
					
					$.each(${secondLevelTMC}, function(index,secondLevel){
						if(secondLevel.parentTMC.TMCId==firstLevel.TMCId){
							if(flag == 0){
								contents += "<ul>";
								flag = 1;
							}
						    contents += "<li><a class='sub' href='#' onclick='menuClick("+secondLevel.TMCId+")'>"+secondLevel.TMCName+"</a></li>";						
						}
					});
					if(flag == 1)
						contents += "</ul>";
					contents += "</div>";
					flag = 0;
				});
			  //alert(contents);
				$("#keleyihovertree").append(contents);
				$(".hovertree a").css("font-size","14px" );
				$(".hovertree a").css("color","#000" );
				$("#keleyihovertree").hovertree({ "width": "157px", "initStatus": 'close'});
		});
				
			 //点击菜单事件
			function menuClick(TMCId){
				$("#right_Panel").hide();
				$("#right_b1").hide();
				$("#right_b2").hide();
				$("#mainFrame").attr("src","searchSignMaterial_searchSignMaterialsByCategory?pageSize=${pageSize}&TMCId="+TMCId+"&currentPage=1");
				$("#mainFrame").show();
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
		
		//初始化iframe高度
		function setIframeHeight(iframe) {
			if (iframe) {
			var iframeWin = iframe.contentWindow || iframe.contentDocument.parentWindow;
			if (iframeWin.document.body) {
			    iframe.height = iframeWin.document.documentElement.scrollHeight || iframeWin.document.body.scrollHeight;
			}
			}
		}
		
		//点击搜索按钮
		function search(){
			if($("#searchContent").val()==''){
				alert('无搜索内容！');
				return;
			}
			$("#right_Panel").hide();
			$("#right_b1").hide();
			$("#right_b2").hide();
			$("#mainFrame").attr("src","searchSignMaterial_searchSMBycontent?flag=1&pageSize=${pageSize}&searchContent="+$("#searchContent").val()+"&currentPage=1");
			$("#mainFrame").show();
		}
		 //监听回车事件
        document.onkeydown=keyDownSearch;
        function keyDownSearch(e) {    
            // 兼容FF和IE和Opera    
            var theEvent = e || window.event;    
            var code = theEvent.keyCode || theEvent.which || theEvent.charCode;    
            if (code == 13) {    
           	 $("#search").click();    
                return false;    
            }    
            return true;    
      } 
    </script>			
	</head>

	<body>
		
		<div id="bg">
			<!--导航以及头部登录和logo-->
		<div class="head">
			<!--无锡万达城销售物业研发平台-->
			<div id="top_logo">
			
			<a href="index.jsp"><img  src="image/problem/tlogo.png"  /></a>
			</div>
			<span class="login_message">
			<span class="welcome"><img id="userimg" src="image/problem/user.png"/>欢迎：<span class="username" id="Username"><shiro:principal/></span></span>
			<span class="logout"><img id="outimg" src="image/problem/out.png"/><a href="userLogin_logout">退出</a></span>
			</span>
			<div class="top_banner">
			<!--导航-->
			    <ul class="switch">
						<li class="switch_li"><a href="#">产品研发大事记</a></li>
						<li class="switch_li"><a href="commonAction_lastSolutions">问题档案库</a></li>
						<li class="switch_li"><a href="#">图纸二维码</a></li>	
						<li class="switch_li" style="margin-top: -40px;text-align: center;"><img src="image/findex/bannerlogo.png"></li>
						<li class="switch_li" style="margin-left:110px "><a href="commonAction_lastSignMaterial">签批资料</a></li>
						<li class="switch_li"><a href="commonAction_lastTrainingMaterial">培训资料</a></li>
						<li class="switch_li"><a href="http://app.connect.trimble.com" target="_blank">BIM</a></li>
				</ul>
			</div>
		</div>
			<!--页面内容-->
		<div class="contain">
			<div id="contents" class="contents">
			<!--左边导航-->
			<div class="left" id="lefth">
			<!--左边分类-->
			<h2>签批资料</h2>
			<div class="left_kind" >
			<!-- 菜单树 -->
			<div id="keleyihovertree" class="hovertree" ></div>
			<!--左下图片 -->
		    <div class="left_img" id="left_Img">
			    <img  src="image/problem/zximg.png" />
			</div>
			</div>
			</div>		
				<div class="right" id="righth">
					<!--上面花朵背景-->
					<img class="top_flower" src="image/problem/tflower.png" />
					<!--右边头部-->
					<div class="right_banner">
							<div class="new_document" id="new_Document">
								<img  id="new_Img" src="image/problem/bnew2.jpg">
							</div>
							
							<div id="right_Search">
								<input type="text" class="search_border" id="searchContent" name="searchContent">
								<input type="submit" value="搜索" class="search_submit" id="search" onclick="search()">
							</div>
							
							
					</div>
					<!--右边内容-->
					<div class="right_content" id="right_Content">
					<!--右边内容上框-->
					<div class="right_b1" id="right_b1">
					<img src="image/problem/tborder.png">
					</div>
					<!--右边内容-->
					<div class="right_panel" id="right_Panel">
						<ul id="rh">
						<c:if test="${lastSignMaterials.size() > 0}">
						<c:forEach var="lastSignMaterial" items="${lastSignMaterials}" varStatus="status">
                            <li><div class="rp_title">${lastSignMaterial.title}</div>
                            <div class="rp_item"><span id="Kind">${lastSignMaterial.category.parentTMC.TMCName}</span><span id="Kind">${lastSignMaterial.category.TMCName}</span><span id="downlo" ><a href="#" onclick="downloadSignFile('${lastSignMaterial.signFile}')" id="Down" class="down" >下载</a></span>
												 <span id="Time"><span id="Time2">上传日期:</span><span id="Time2" class="time2">${fn:substring(lastSignMaterial.createDate,0,10)}</span><span id="Time2">下载次数:</span><span id="Time2" class="down_num">${lastSignMaterial.clickNum }</span><span>&nbsp;&nbsp;次</span></span>
							</div>
							<div class="rp_item">
								<span>备注：${lastSignMaterial.remark}</span>
							</div>
                            </li>
                        </c:forEach>
						</c:if>
						<c:if test="${lastSignMaterials.size() <= 0}">
                            <div>没有新增资料......</div>
                        </c:if>
						</ul>
					</div>

					<!--下面花朵背景-->
					<img class="bottom_flower" src="image/problem/bfl.png" />
					<div ><iframe id="mainFrame" onload="setIframeHeight(this)" scrolling="no"  frameborder="0"></iframe></div>
					<!--右边内容下框-->
					<div class="right_b2" id="right_b2">
					<img src="image/problem/bborder.png">
					</div>
					
					</div>
					<div class="foot" id="Foot">
					<p id="copy">版权所有&nbsp;&nbsp;&nbsp;2016-2026&nbsp;&nbsp;&nbsp;无锡万达城投资有限责任公司&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				联系号码：0000-0000000&nbsp;&nbsp;&nbsp;邮箱：100000qq.com    </p>
					</div>
		</div>
			</div>
			
		</div>
		
		</div>
	</body>

</html>