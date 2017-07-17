<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8" http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
		<title>问题档案库</title>		
		<script type="text/javascript" src="js/jquery.min.js"></script>
		<script type="text/javascript">
		    $(document).ready(function(){		    
			  $("#nextPage").click(function(){
				 if(${currentPage} >= ${totalPage}){
					 alert("已经是最后一页了！");
				 }else{
					 //下一页
					 if(${!empty searchContent})
						 window.location.href="searchTrainingMaterial_searchTMBycontent?flag=1&pageSize=${pageSize}&searchContent=${searchContent}&currentPage=${currentPage+1}";
				     else
					     window.location.href="searchTrainingMaterial_searchTrainingMaterialsByCategory?TMCId=${TMCId}&currentPage=${currentPage+1}&pageSize=${pageSize}";
				 }		 
			 });
			 $("#prePage").click(function(){
				 if(${currentPage} <= 1){
					 alert("已经是第一页了！");
				 }else{
					 //上一页
					 if(${!empty searchContent})
						 window.location.href="searchTrainingMaterial_searchTMBycontent?flag=1&pageSize=${pageSize}&searchContent=${searchContent}&currentPage=${currentPage-1}";
				     else
					     window.location.href="searchTrainingMaterial_searchTrainingMaterialsByCategory?TMCId=${TMCId}&currentPage=${currentPage-1}&pageSize=${pageSize}&searchContent=${searchContent}";
				 }		 
			 });
			 $("#firstPage").click(function(){
				 if(${currentPage} != 1){
					 if(${!empty searchContent})
						 window.location.href="searchTrainingMaterial_searchTMBycontent?flag=1&pageSize=${pageSize}&searchContent=${searchContent}&currentPage=1";
				     else
				         window.location.href="searchTrainingMaterial_searchTrainingMaterialsByCategory?TMCId=${TMCId}&currentPage=1&pageSize=${pageSize}&searchContent=${searchContent}";			 
				 }
			 });
			 $("#lastPage").click(function(){
				 if(${currentPage} != ${totalPage}){
					 if(${!empty searchContent})
						 window.location.href="searchTrainingMaterial_searchTMBycontent?flag=1&pageSize=${pageSize}&searchContent=${searchContent}&currentPage=${totalPage}";
				     else
					     window.location.href="searchTrainingMaterial_searchTrainingMaterialsByCategory?TMCId=${TMCId}&currentPage=${totalPage}&pageSize=${pageSize}&searchContent=${searchContent}";
				 }
		     });
			 $("#go").click(function(){
				 if($("#pageNum").val()==${currentPage})
			         return;
				 if($("#pageNum").val()>=1 && $("#pageNum").val()<= ${totalPage}){
					 if(${!empty searchContent})
						 window.location.href="searchTrainingMaterial_searchTMBycontent?flag=1&pageSize=${pageSize}&searchContent=${searchContent}&currentPage=" + $("#pageNum").val();
				     else
					     window.location.href="searchTrainingMaterial_searchTrainingMaterialsByCategory?TMCId=${TMCId}&pageSize=${pageSize}&searchContent=${searchContent}&currentPage=" + $("#pageNum").val();
				 }
				 else 
					 alert("页码不正确！");
		     });
		  });
		    
   </script>
		<link rel="stylesheet" type="text/css" href="css/pub.css"/>
	</head>

	<body>
        <div id="bg">
            <div class="right_content" id="right_Content">
					<!--右边内容上框-->
					<div class="right_b1">
					<img src="image/problem/tborder.png">
					</div>
					<!--右边内容-->
					<div class="right_panel" id="right_Panel">					
						<ul id="Rh2">
						<c:if test="${trainingMaterials.size() > 0}">
						<c:forEach var="trainingMaterial" items="${trainingMaterials}" varStatus="status">
                            <li><div class="rp_title2"><a href="commonAction_showTrainingMaterialDetail?TMId=${trainingMaterial.TMId}" target="_blank" >${titleList[status.index]}</a></div>            
                                <div class="rp_item2"><span class="Subkind">${trainingMaterial.category.parentTMC.TMCName}</span><span class="Subkind">${trainingMaterial.category.TMCName}</span>
                                    <span class="Subkind2">${fn:substring(trainingMaterial.createDate,0,10)}<img class="new_img" src="image/problem/new3.png"/></span></div>
                            </li>
                        </c:forEach>
                        </c:if>
                        <c:if test="${trainingMaterials.size() <= 0}">
                            <li>没有资料......</li>
                        </c:if>
                        <li>
					    <div >
		                                        共 ${trainingMaterialNum} 条记录  当前为:${currentPage}/${totalPage}页   每页${pageSize}条	
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
										
					<div class="right_b2">
					<img src="image/problem/bborder.png">
					</div>
			</div>
	    </div>
	</body>

</html>