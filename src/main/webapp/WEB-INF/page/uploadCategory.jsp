<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>上传培训资料</title>
<script src="js/jquery.min.js"></script>
<script type="text/javascript">
    $(function(){
    	$("#firstLevelTMCId").empty();
    	$("#firstLevelSelect").hide();
	    $('input:radio[name="categoryRadio"]').click(function(){
	        if($('input:radio[name="categoryRadio"]:checked').val()=="firstLevel"){
	        	//创建一级类，不需要下拉框
	        	$("#firstLevelSelect").hide();
	        }else if($('input:radio[name="categoryRadio"]:checked').val()=="secondLevel"){
	        	getFirstLevelCategory();	        	
	        }
	    });
	});
    //检查是否可以提交
    function check(){
    		if($("#TMCName").val()=='' || $("#TMCName").val().length>15){
    			$("#msg").html('<font color="red">标题不为空且长度小于15！</font>');
    			return false;
    		} 	
    	return true;
    }
    //异步获取第一级标题
    function getFirstLevelCategory(){
    	$("#firstLevelTMCId").empty();
    	if($('input:radio[name="categoryRadio"]:checked').val()=="secondLevel"){
    	//创建二级类，异步获取一级分类，设置下拉框
    	$.ajax({
			type:'POST',
			contentType: "application/json",  
		    url: "operateCategory_getFirstLevelCategory?module="+$("#module").val(),  
		    dataType: "json",  
		    success:function(datas){
		    	if(datas == null){
		    		$("#firstLevelTMCId").empty();
		    		$("#firstLevelTMCId").hide();
		    		alert('该模块不存在一级分类！');
	                return;	    		
		    	}
		    	if(datas == "wrong"){
		    		alert('出错了！');
	                return;	    		
		    	}
		    	//动态添加下拉列表
		    	$("#firstLevelTMCId").empty();
		    	var selectHtml = '';
		    	for(var i=0; i<datas.length; i++){
		    		selectHtml += '<option value="'+datas[i].TMCId+'">'+datas[i].TMCName+'</option>';
		    	}
		    	$("#firstLevelTMCId").html(selectHtml);
		    	$("#firstLevelSelect").show();
		    }  
		});
        }
    }
</script>
</head>
<body>
<br>
	<center>
		<form action="operateCategory_uploadCategory" onsubmit="return check()" method="post">
			<ul>
			    <li>
				类名：<input type="text" id="TMCName" name="TMCName"><br><br>
			    </li>
			    <li>
				备注：<input type="text" id="remark" name="remark"><br><br>
			    </li>
				<li>	
		          模块： 
			<select id="module" name="module" onchange="getFirstLevelCategory()">
		        <option value="training" selected="selected">培训资料</option>			    
		        <option value="sign">签批资料</option>			    
		        <option value="solution">问题档案库</option>			    
			</select><br><br>
			一级菜单：<input type="radio" checked="checked" name="categoryRadio" value="firstLevel">
                              二级菜单：<input type="radio" name="categoryRadio" value="secondLevel"><br><br>			
		    <div id="firstLevelSelect">		         
		         父类： 
			<select id="firstLevelTMCId" name="firstLevelTMCId"></select>
			</div>		       
			</li>			   
			</ul>		
			<div id="msg"></div>
			<input type="submit" value="提交">
			<input type="button" value="返回" onclick="javascript:window.location.href='searchCategory_searchCategoryByPage?currentPage=${currentPage}&pageSize=${pageSize}'">
		</form>
	</center>

</body>
</html>