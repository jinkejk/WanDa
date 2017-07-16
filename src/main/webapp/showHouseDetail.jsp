<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">

<title>${house.HXName}</title>
<script src="js/jquery.min.js"></script>
<script type="text/javascript" src="js/jquery.autocomplete.js"></script>
<link rel="stylesheet" type="text/css" href="css/jquery.autocomplete.css">  

<script type="text/javascript">
$(document).ready(function(){
	$.ajax({
		type:'POST',
		contentType: "application/json",  
	    url: "commonAction_getLPNameJson",  
	    dataType: "json",  
	    success:function(datas){
	    	if(datas == null || datas == "wrong"){
	    		alert('查询楼盘数据出错！');
                return;	    		
	    	}
	        autocompleteFn(datas);  
	    }  
	});  
});  
//自动 补全方法  
function autocompleteFn(datas){
	$("#LPsearch").flushCache();//刷新缓存（非常重要）
	$("#LPsearch").autocomplete(datas,{
		minChars:1,  
		max: 10,  
	    dataType:"json",  
	    autoFill: false,  
	    mustMatch: true,  
	    matchContains: true,  
	    scrollHeight: 220,  
	    formatItem: function(data, i, total) {
	        return "<I><b>"+data.LPName+"</b></I>";  
	    },  
	    formatMatch: function(data, i, total) {
	        return data.LPName;  
	    },  
	    formatResult: function(data) {  
	        return data.LPName;  
	    }  
	});  
} 

//获取楼盘对应户型信息
function getHXName(){
	if($("#LPsearch").val() == ''){
		alert('请先填写楼盘信息！');
		return;
	}
	$.ajax({
		type:'POST',
		contentType: "application/json",  
	    url: "commonAction_getHXNameJson?LPsearch=" + encodeURI($("#LPsearch").val()),  
	    dataType: "json",  
	    success:function(datas){
	    	if(datas == null){
	    		alert('不存在该楼盘！');
	    		return;
	    	}
	    	if(datas == "wrong"){
	    		alert('出错啦！');
	    		return;	    		
	    	}
	    	//动态添加下拉列表
	    	$("#select").empty();
	    	var selectHtml = $("#select").html();
	    	for(var i=0; i<datas.length; i++){
	    		selectHtml += '<option value="'+datas[i].HXName+'">'+datas[i].HXName+'</option>';
	    	}
	    	$("#select").html(selectHtml);
	    }  
	});
}

var colCount = 1;
var idCount = 2;
function addCol(json){	
    var trHtml = $("#imgName").html();
    trHtml += '<td id="imgName'+idCount+'" width="300"><img src="../house/small/'+json.imgName+'" width="300" height="200"></td>';
    $("#imgName").html(trHtml);
   
    var trHtml = $("#LPName").html();
    var str = json.LPName;
    if(str.length>30){
    	str = str.substring(0,30) + '......';
    }
    trHtml += '<td id="LPName'+idCount+'">'+str+'</td>';
    $("#LPName").html(trHtml);
    
    var trHtml = $("#HXName").html();
    var str = json.HXName;
    if(str.length>30){
    	str = str.substring(0,30) + '......';
    }
    trHtml += '<td id="HXName'+idCount+'">'+str+'</td>';    
    $("#HXName").html(trHtml);

    var trHtml = $("#area").html();
    trHtml += '<td id="area'+idCount+'">'+json.area+'</td>';
    $("#area").html(trHtml);
    
    var trHtml = $("#rate").html();
    trHtml += '<td id="rate'+idCount+'">'+json.rate+'</td>';
    $("#rate").html(trHtml);
    
    var trHtml = $("#layer").html();
    trHtml += '<td id="layer'+idCount+'">'+json.layer+'</td>';
    $("#layer").html(trHtml);
    
    var trHtml = $("#outWinType").html();
    var str = json.outWinType;
    if(typeof(str) == "undefined"){
    	str = '';
    }else if(str.length>30){
    	str = str.substring(0,30) + '......';
    }
    trHtml += '<td id="outWinType'+idCount+'">'+str+'</td>';
    $("#outWinType").html(trHtml);
    
    var trHtml = $("#warmMaterial").html();
    var str = json.warmMaterial;
    if(typeof(str) == "undefined"){
    	str = '';
    }else if(str.length>30){
    	str = str.substring(0,30) + '......';
    }
    trHtml += '<td id="warmMaterial'+idCount+'">'+str+'</td>';
    $("#warmMaterial").html(trHtml);
    
    var trHtml = $("#insulation").html();
    var str = json.insulation;
    if(typeof(str) == "undefined"){
    	str = '';
    }else if(str.length>30){
    	str = str.substring(0,30) + '......';
    }
    trHtml += '<td id="insulation'+idCount+'">'+str+'</td>';
    $("#insulation").html(trHtml);
    
    var trHtml = $("#soundProof").html();
    var str = json.soundProof;
    if(typeof(str) == "undefined"){
    	str = '';
    }else if(str.length>30){
    	str = str.substring(0,30) + '......';
    }
    trHtml += '<td id="soundProof'+idCount+'">'+str+'</td>';
    $("#soundProof").html(trHtml);
    
    var trHtml = $("#shadeType").html();
    var str = json.shadeType;
    if(typeof(str) == "undefined"){
    	str = '';
    }else if(str.length>30){
    	str = str.substring(0,30) + '......';
    }
    trHtml += '<td id="shadeType'+idCount+'">'+str+'</td>';
    $("#shadeType").html(trHtml);
    
    var trHtml = $("#hasSolar").html();
    var str = json.hasSolar;
    if(typeof(str) == "undefined"){
    	str = '';
    }else if(str.length>30){
    	str = str.substring(0,30) + '......';
    }
    trHtml += '<td id="hasSolar'+idCount+'">'+str+'</td>';
    $("#hasSolar").html(trHtml);
    
    var trHtml = $("#attention").html();
    trHtml += '<td id="attention'+idCount+'">'+json.attention+'</td>';
    $("#attention").html(trHtml);
    
    var trHtml = $("#button").html();
    trHtml += '<td align="center" id="'+idCount+'"><input type="button" onclick="delCol(this)" value="删除"></td>';
    $("#button").html(trHtml);
    
    colCount++;
    idCount++;
  }
  
  function delCol(btn){
	var id = btn.parentNode.id;
	if(colCount <= 1){
		alert('无法删除！');
	}else{
        $("#table1 tr").each(function(){
        $("td:eq("+id+")",this).hide();
        });
        colCount--;	
	}
  }
  
  //获取相应的house
  function getHouse(){
	  if($("#LPsearch").val()=='' || $("#select").val()==''){
		  alert('请完善楼盘和户型信息！');
		  return;
	  }
	  if(colCount >=3){
		  alert('已达上限！');
		  return;
	  }
	  $.ajax({
			type:'POST',
			contentType: "application/json",  
		    url: "commonAction_getHouseByLPAndHXName?LPName="+encodeURI($("#LPsearch").val())+"&HXName="+encodeURI($("#select").val()),  
		    dataType: "text",  
		    success:function(data){
		    	if(data == "failed"){
		    		alert('未查询到该户型！');
	                return;	    		
		    	}
		    	if(data == "wrong"){
		    		alert('出错了！');
	                return;	    		
		    	}
		    	var json = eval('(' + data + ')');
		    	addCol(json);
		    }  
		});
  }
</script>
</head>
<body>
	<center><br>
	    <div>动态户型筛选比较系统</div><br>
        <table id="table1" style="TABLE-LAYOUT: auto;" border="1">
        <tr id="imgName">        
            <td>户型图</td><td id="imgName1" width="300"><img src="../house/small/${house.imgName}" width="300" height="200"></td>
        </tr>
        <tr id="LPName">
            <td>楼盘名称</td>
            <td id="LPName1">${fn:substring(house.LPName,0,30)}
                <c:if test="${fn:length(house.LPName) > 30}">
                    ${'.....'}
                </c:if>
            </td>
        </tr>
        <tr id="HXName">  
            <td>户型名称</td>  
            <td id="HXName1">${fn:substring(house.HXName,0,30)}
                <c:if test="${fn:length(house.HXName) > 30}">
                    ${'.....'}
                </c:if>
            </td>
        </tr>
        <tr id="area">    
            <td>建筑面积</td><td id="area1">${house.area}</td>
        </tr>    
        <tr id="rate">
            <td>得房率</td><td id="rate1">${house.rate}</td>
        </tr>
        <tr id="layer">
            <td>总层数</td><td id="layer1">${house.layer}</td>
        </tr>
        <tr id="outWinType">    
            <td>外窗材型</td><td id="outWinType1">${fn:substring(house.outWinType,0,30)}</td>
        </tr>
        <tr id="warmMaterial">           
            <td>外墙保温材料</td><td id="warmMaterial1">${fn:substring(house.warmMaterial,0,30)}</td>
        </tr>
        <tr id="insulation">     
            <td>分户楼板保温材料</td><td id="insulation1">${fn:substring(house.insulation,0,30)}</td>
        </tr>
        <tr id="soundProof">    
            <td>分户楼板隔声材料</td><td id="soundProof1">${fn:substring(house.soundProof,0,30)}</td>
        </tr>
        <tr id="shadeType">    
            <td>遮阳形式</td> <td id="shadeType1">${fn:substring(house.shadeType,0,30)}</td>
        </tr>
        <tr id="hasSolar">    
            <td>是否有太阳能热水器</td><td id="hasSolar1">${fn:substring(house.hasSolar,0,30)}</td>
        </tr>     
        <tr id="attention">    
            <td>关注度</td><td id="attention1">${house.attention}</td>
        </tr>     
        <tr id="button">    
            <td height="20"></td><td align="center" id="1"><input type="button" onclick="delCol(this)" value="删除"></td>
        </tr>     
        </table><br>
        
        楼盘名称：<input  type="text" id="LPsearch" placeHolder="请输入要搜索的内容!"/><input type="button" onclick="getHXName()" value="确定"><br><br>  
        户型名称：<select id="select"></select><div id="validate_info"></div></font><br><br>  
        <input type="button" onclick="getHouse()" value="添加">
        <br>
	</center>
</body>
</html>
