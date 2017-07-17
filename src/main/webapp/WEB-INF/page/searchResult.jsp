<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>        

<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8" http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
		<title>问题档案库</title>
	    <link rel="stylesheet" type="text/css" href="css/pub.css" />
		<link rel="stylesheet" type="text/css" href="css/problem.css"/>
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
			//点击菜单树
				function menuClick(TMCId){
					$("#right_Panel").hide();
					$("#right_b1").hide();
					$("#right_b2").hide();
					$("#mainFrame").attr("src","searchSolution_searchSolutionByCategory?pageSize=${pageSize}&TMCId="+TMCId+"&currentPage=1");
					$("#mainFrame").show();
				}
			
				function setIframeHeight(iframe) {
					if (iframe) {
					var iframeWin = iframe.contentWindow || iframe.contentDocument.parentWindow;
					if (iframeWin.document.body) {
					iframe.height = iframeWin.document.documentElement.scrollHeight || iframeWin.document.body.scrollHeight;
					}
					}
				};

   </script>
	</head>

	<body>
		
		<div id="bg">
			<!--导航以及头部登录和logo-->
		<div class="head">
			<!--无锡万达城销售物业研发平台-->
			<div id="top_logo">
			
			<a href="index.jsp"><img src="image/problem/tlogo.png"  /></a>
			</div>
			<span class="login_message">
			<span class="welcome"><img id="userimg" src="image/problem/user.png"/>欢迎：<span class="username" id="Username"><shiro:principal/></span></span>
			<span class="logout"><img id="outimg" src="image/problem/out.png"/><a href="userLogin_logout">退出</a></span>
			</span>
			<div class="top_banner">
			<!--导航-->
				<ul class="switch">
						<li><a href="#">产品研发大事记</a></li>
						<li><a href="commonAction_lastSolutions">问题档案库</a></li>
						<li><a href="#">图纸二维码</a></li>
						<li><a href="commonAction_lastSignMaterial">签批资料</a></li>
						<li><a href="commonAction_lastTrainingMaterial">培训资料</a></li>
				</ul>		
				
			</div>
		</div>
			<!--页面内容-->
		<div class="contain">
			<div id="contents" class="contents">
			<!--左边导航-->
			<div class="left" id="lefth">
			<!--左边分类-->
			<div class="left_kind">
			<h2>问题档案库</h2>
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
					<img class="top_flower"  src="image/problem/tflower.png" />
					<!--右边头部-->
					<div class="right_banner">
							<div class="new_document" id="new_Document">
								<img  id="new_Img" src="image/problem/pnew.jpg">
							</div>							
							<form action="searchSolution" id="right_Search" method="post" id="form1">
                                <input type="hidden" name="pageSize" value="15">
                                <input type="hidden" name="currentPage" value="1">
                                <input type="hidden" name="isNewContent" value="1">                            
								<input type="text" class="search_border" id="searchContent" name="searchContent" value="${searchContent}">
								<input type="submit" id="search" value="搜索" class="search_submit" >
                            </form>						
					</div>
					<!--右边内容-->
					<div class="right_content" id="right_Content">
					<!--右边内容上框-->
					<div class="right_b1" id="right_b1">
					<img src="image/problem/tborder.png">
					</div>
					<!--右边内容-->
					  
					<div class="right_panel" id="right_Panel">					
						<ul id="Rh2" class="rh2">
						<c:if test="${solutions.size() > 0}">
			                <c:forEach var="solution" items="${solutions}" varStatus="status">
				                <li><div class="rp_title2">
				                        <a href="searchSolution_searchSolutionById?solutionId=${solution.solutionId}" target="_blank">${titleList[status.index]}</a>
				                    </div>
					                <div class="rp_item2">
					                    <span class="Subkind">${solution.category.TMCName}</span><span class="Subkind">${solution.category.parentTMC.TMCName}</span><span class="Subkind2">${fn:substring(solution.createDate,0,10)}<img class="new_img" src="image/problem/new3.png"/>
					                    </span>
					                </div>
			                    </li>							
			                </c:forEach>
		                 </c:if>
		                <c:if test="${empty solutions || solutions.size() <= 0}">
			                <li><div class="rp_title2"> 无相关内容.......</div></li>
		                </c:if>						
					    <li>
					    <div >
		                                        共 ${solutionTotalNum} 条记录  当前为:${currentPage}/${totalPage}页   每页${pageSize}条	
		                                        跳到第 <input name="pageNum" id="pageNum" type="text">	页 
		                <a href="#" id="firstPage">&lt;&lt;</a>
		                <a href="#" id="prePage">&lt;</a> 
		                <a href="#" id="nextPage">&gt;</a>
		                <a href="#" id="lastPage">&gt;&gt;</a> 
		                <input type="button" id="go" value="跳转">
		                </div>
	                    </li>
						</ul>					
						</div>							
						<div ><iframe id="mainFrame" src="searchSolution_searchSolutionByCategory?pageSize=${pageSize}&TMCId=${TMCId}&currentPage=1"
						       onload="setIframeHeight(this)" scrolling="no"  frameborder="0"></iframe></div>
						       
					<div class="right_b2" id="right_b2">
					<img src="image/problem/bborder.png">
					</div>		
					
					</div>
					
					<div class="foot" id="Foot">
			<p id="copy">版权所有&nbsp;&nbsp;&nbsp;2016-2026&nbsp;&nbsp;&nbsp;锡万达城投资有限责任公司&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				联系号码：0000-0000000&nbsp;&nbsp;&nbsp;邮箱：100000qq.com    </p>
		</div>
		<!--下面花朵背景-->
		<img class="p_bottomflower" src="image/problem/bflower.png" />
		</div>
			</div>
		</div>
		
		</div>
	</body>

</html>