<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>${house.HXName}</title>
<script src="js/jquery.min.js"></script>
<script src="js/jquery.form.js"></script>
<script type="text/javascript">
    var flag_img = false;//图片是否上传标记    
    var fileName; //防止重复上传
    var lastFileName;
    
	$(function(){
		    //为表单绑定异步上传的事件
		    $("#upload_img").ajaxForm({
		    url : "${pageContext.request.contextPath}/uploadUtilAction_upLoadHouseImg", // 请求的url
		    type : "post", // 请求方式
		    dataType : "text", // 响应的数据类型
		    async :true, // 异步
		    success : function(msg){
           	 if (msg.indexOf("#") > 0) {
           		 flag_img = true;
           		 lastFileName = fileName;
           		 var data = msg.split("#");
           	     $("#upload_result").html(data[0]);
           	     //加随机数让浏览器跟新，否则它会读取缓存
           	     var path = '..\\house\\small\\' + data[1] + '?' + Math.random();           	     
           	     $("#imgName").attr("value",data[1]);//填充内容
                 $("#showImage").attr("src",path);
               }else{
           	      $("#upload_result").html(msg);
           	      flag_img = false;
                }
		    },
		    error : function(){
		    	flag_img = false;
		        alert("上传失败！");
		    }
		});
	});
	
    //上传图片
    function uploadImage() {
	   if(imgValidate() && confirm("上传会覆盖原先图纸！确定上传？")){
		   $("#upload_result").html('上传中...');
		   $("#upload_img").submit();	 
	   }
       return false;  
    }
    
    //判断图片是否符合要求
    function imgValidate(){
        var filepath = $("input[name='fileUpload']").val();
        if(filepath==""){
        	alert('请选择文件！');
        	return false;
        }
        var extStart = filepath.lastIndexOf(".");
        var fileNameStart = filepath.lastIndexOf("\\");
        var ext = filepath.substring(extStart, filepath.length).toUpperCase();
        
        fileName = filepath.substring(fileNameStart+1,filepath.length);
        //重复上传
        if(fileName == lastFileName){
        	alert('请不要重复提交！');
        	return false;        	
        }
        if (ext != ".BMP" && ext != ".PNG" && ext != ".GIF" && ext != ".JPG" && ext != ".JPEG") {
            alert("图片限于bmp,png,gif,jpeg,jpg格式");           
            return false;
        } 
        return true;
    }

    var LPName_flag = true;
    var HXName_flag = true;
    var area_flag = true;
    var rate_flag = true;
    var layer_flag = true;
    var outWinType_flag = true;
    var warmMaterial_flag = true;
    var insulation_flag = true;
    var soundProof_flag = true;
    var shadeType_flag = true;
    var hasSolar_flag = true;
    //检查是否可以提交
    function check(){
    	//if(flag_img){
    		if($("#LPName").val()=='' || $("#LPName").val().length>50){
    			$("#msg").html('<font color="red">楼盘名不为空且长度小于50！</font>');
    			LPName_flag = false;
    			return false;
    		}
    		if($("#HXName").val()=='' || $("#HXName").val().length>50){
    			$("#msg").html('<font color="red">户型名不为空且长度小于50！</font>');
    			HXName_flag = false;
    			return false;
    		}
    		if($("#area").val()!='' && (isNaN($("#area").val()) || $("#area").val()<0)){
    			$("#msg").html('<font color="red">建筑面积不合法！</font>');
    			area_flag = false;
    			return false;
    		}
    		if($("#rate").val()!='' && (isNaN($("#rate").val()) || $("#rate").val()<0 || $("#rate").val()>1)){
    			$("#msg").html('<font color="red">得房率不合法！</font>');
    			rate_flag = false;
    			return false;
    		}
    		if($("#layer").val()!='' && (isNaN($("#layer").val()) || $("#layer").val()<=0)){
    			$("#msg").html('<font color="red">总层数不合法！</font>');
    			layer_flag = false;
    			return false;
    		}
    		if($("#outWinType").val()=='' || $("#outWinType").val().length>50){
    			$("#msg").html('<font color="red">外窗材型不为空且长度小于50！</font>');
    			outWinType_flag = false;
    			return false;
    		}
    		if($("#warmMaterial").val()=='' || $("#warmMaterial").val().length>50){
    			$("#msg").html('<font color="red">外墙保温材料不为空且长度小于50！</font>');
    			warmMaterial_flag = false;
    			return false;
    		}
    		if($("#insulation").val()=='' || $("#insulation").val().length>50){
    			$("#msg").html('<font color="red">分户楼板保温材料不为空且长度小于50！</font>');
    			insulation_flag = false;
    			return false;
    		}
    		if($("#soundProof").val()=='' || $("#soundProof").val().length>50){
    			$("#msg").html('<font color="red">分户楼板隔声材料不为空且长度小于50！</font>');
    			soundProof_flag = false;
    			return false;
    		}
    		if($("#shadeType").val()=='' || $("#shadeType").val().length>50){
    			$("#msg").html('<font color="red">遮阳形式不为空且长度小于50！</font>');
    			shadeType_flag = false;
    			return false;
    		}
    		if($("#hasSolar").val()=='' || $("#hasSolar").val().length>50){
    			$("#msg").html('<font color="red">是否有太阳能热水器不为空且长度小于50！</font>');
    			hasSolar_flag = false;
    			return false;
    		}
    	//}else{
    		//alert('请先上传图片！');
    		//return false;
    	//}
    	
    	return true;
    }
</script>
</head>
<body>
	<center>
		<div>上传新户型</div>
		<br>		
		<table border="1">
			<tr>
				<td>上传户型图</td>
				<td width="300">				
				    <form action="uploadUtilAction_upLoadHouseImg" id="upload_img" method="post" enctype="multipart/form-data">
				        <input id="fileupload" name="fileUpload" type="file" accept="image/*"/>
				        <input type="button" value="上传" onclick="uploadImage()">  
                        <div id="upload_result"></div>
                        <input type="hidden" id="houseId" name="houseId" value="${house.houseId}">
                        <input type="hidden" name="originImgName" value="${house.imgName}">                   
                    </form>
                    <img id="showImage" width="300" height="200" src="../house/small/${house.imgName}"/>
				</td>
			</tr>
		</table><br>
	    <form action="operateHouse_uploadHouse" method="post" onsubmit="return check()">
		   <table border="1">
			<tr>
				<td>楼盘名称</td>
				<td><input type="text" name="LPName" id="LPName" value="${house.LPName}"></td>
			</tr>
			<tr>
				<td>户型名称</td>
				<td><input type="text" name="HXName" id="HXName" value="${house.HXName }"></td>
			</tr>
			<tr>
				<td>建筑面积</td>
				<td><input type="text" name="area" id="area" value="${house.area }"></td>
			</tr>
			<tr>
				<td>得房率</td>
				<td><input type="text" name="rate" id="rate" value="${house.rate }"></td>
			</tr>
			<tr>
				<td>总层数</td>
				<td><input type="text" name="layer" id="layer" value="${house.layer }"></td>
			</tr>
			<tr>
				<td>外窗材型</td>
				<td><input type="text" name="outWinType" id="outWinType" value="${house.outWinType }"></td>
			</tr>
			<tr>
				<td>外墙保温材料</td>
				<td><input type="text" name="warmMaterial" id="warmMaterial" value="${house.warmMaterial }"></td>
			</tr>
			<tr>
				<td>分户楼板保温材料</td>
				<td><input type="text" name="insulation" id="insulation" value="${house.insulation }"></td>
			</tr>
			<tr>
				<td>分户楼板隔声材料</td>
				<td><input type="text" name="soundProof" id="soundProof" value="${house.soundProof }"></td>
			</tr>
			<tr>
				<td>遮阳形式</td>
				<td><input type="text" name="shadeType" id="shadeType" value="${house.shadeType }"></td>
			</tr>
			<tr>
				<td>是否有太阳能热水器</td>
				<td><input type="text" name="hasSolar" id="hasSolar" value="${house.hasSolar }"></td>
			</tr>			
		</table>
		    <div id="msg"></div>
		    <input type="hidden" id="houseId" name="houseId" value="${house.houseId}">		
		    <input type="hidden" name="imgName" id="imgName" value="${house.imgName}">
		    <input type="submit" value="提交">
			<input type="button" value="返回" onclick="javascript:window.location.href='searchHouse_searchHouseByPage?currentPage=${currentPage}&pageSize=${pageSize}'">
		</form>
	</center>
</body>
</html>