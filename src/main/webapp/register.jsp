<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>注册</title>
<script src="js/jquery.min.js"></script>
<script type="text/javascript" src="js/validate.js"></script>
<script type="text/javascript">
    var flag_userName = false;		//记录name是否合法
    var flag_password = false;
    var flag_trueName = false;
    var flag_telephone = false;
    var flag_QQ = false;
    
    //异步验证用户名是否存在
    function judgeName(){
    	if(!userNameValidate($("#userName").val())){
    		flag_userName = false;
    		$("#validate_info").text('用户名长度在1-15之间！');
    		$("#validate_info").show();
    	}else{
    		//异步验证
    	    $("#validate_info").hide();
    	    $.post("userRegisterAjax", {userName:$("#userName").val()},
    			    function(data){
    		            if(data == 'exist'){    		        	
    		        	    $("#validate_info").text('用户名已存在！');
    		        	    $("#validate_info").show();
    		        	    flag_userName = false;
    		            }else if(data == 'not exist'){
    	    	            //alert("result: " + data);
    		        	    $("#validate_info").hide();
    		        	    flag_userName = true;
    		            }else{
        	    	        alert("result: " + data);
    		            }   		        	
    	            },"json");
    	}
    }
    
    //提交表单前验证
    function check(){
    	//重置所有flag为false
    	resetFlag();
    	if(passwordValidate($("#password").val()))
    		flag_password = true;    	
    	if($("#password").val() != $("#passwordAgain").val()){
    		$("#validate_info").text("两次密码不一致！");
    		$("#validate_info").show();
    		return false;
    	}
    	if(trueNameValidate($("#trueName").val()))
    		flag_trueName = true;
    	if(telephoneValidate($("#telephone").val()))
    		flag_telephone = true;
    	if(QQValidate($("#QQ").val()))
    		flag_QQ = true;
    	
    	if(!flag_userName){
    		$("#validate_info").text("用户名不合法！");
    		$("#validate_info").show();
    		return false;
    	}else if(!flag_password){
    		$("#validate_info").text("密码长度在3-15之间！");
    		$("#validate_info").show();
    		return false;
    	}else if(!flag_trueName){
    		$("#validate_info").text("真实姓名不合法！");
    		$("#validate_info").show();
    		return false;
    	}else if(!flag_telephone){
    		$("#validate_info").text("电话格式不合法！");
    		$("#validate_info").show();
    		return false;
    	}else if(!flag_QQ){
    		$("#validate_info").text("QQ格式不合法！");
    		$("#validate_info").show();
    		return false;
    	}else if($("#position").val().length > 20){
    		$("#validate_info").text("职位长度需小于20！");
    		$("#validate_info").show();
    		return false;
    	}else if($("#dept").val().length > 20){
    		$("#validate_info").text("部门长度需小于20！");
    		$("#validate_info").show();
    		return false;
    	}else if($("#company").val().length > 30){
    		$("#validate_info").text("公司长度需小于20！");
    		$("#validate_info").show();
    		return false;
    	}else if($("#remark").val().length > 50){
    		$("#validate_info").text("备注长度需小于50！");
    		$("#validate_info").show();
    		return false;
    	}
    	else{
    		return true;
    	}
    }
   
    //重置所有flag为false
    function resetFlag(){
        flag_password = false;
        flag_trueName = false;
        flag_telephone = false;
        flag_QQ = false;
    }
</script>

</head>
<body>
    <center>
    <form action="userRegister" onsubmit="return check()" method="post">
        <table> 
            <tr>
            <tr>
                <td>用户名：</td><td><input type="text" name="userName" id="userName" onblur="judgeName()"/></td>
                <td>密码：</td><td><input type="password" name="password" id="password"/></td>
            </tr>

            <tr>
                <td>确认密码：</td><td><input type="password" name="passwordAgain" id="passwordAgain"/></td>
                <td>真实姓名：</td><td><input type="text" name="trueName" id="trueName"/></td>
            </tr>
            <tr>
                <td>职位：</td><td><input type="text" name="position" id="position"/></td>
                <td>部门：</td><td><input type="text" name="dept" id="dept"/></td>
            </tr>
            <tr>
                <td>公司：</td><td><input type="text" name="company" id="company"/></td>
                <td>电话：</td><td><input type="text" name="telephone" id="telephone"/></td>
            </tr>        
        
            <tr>
                <td>QQ：</td><td><input type="text"  name="QQ" id="QQ"/></td>
                <td>备注：</td><td><input type="text" name="remark" id="remark"/></td>
            </tr>
            
            <tr>
                <td><input type="submit" value="注册" ></td>
                <td><input type="button" value="取消" onclick="javascript:window.location.href='login.jsp'"></td>   
            
            </tr>
        </table>        
     </form>
     <font color="red"><p id="validate_info"></p></font>
</center>
</body>
</html>