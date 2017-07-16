<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src="js/jquery.min.js"></script>
<script type="text/javascript" src="js/validate.js"></script>
<script type="text/javascript">
    var flag_telephone = false;
    var flag_QQ = false;
    
    //重置密码
    function onResetPassword(){
    	if(confirm("确定要重置：" + '${user.loginName}' + "的密码？")){
    		if(${user.loginName == ""}){
        		alert('出错啦！');
        	}else{
        		//异步
        	    $.post("operateUserAjax_resetPassword", {loginName:'${user.loginName}'},
        			    function(data){
        		            if(data == 'success'){    		        	
        		        	    alert('操作成功！ 密码已重置为：000');
        		            }else if(data == 'failed'){
        		        	    alert('操作失败！');
        		            }else{
        		        	    alert('出错啦！');
        		            }   		        	
        	            },"json");
        	}
    	}
    }
    
  //提交表单钱验证
    function check(){
    	//重置所有flag为false
    	resetFlag();
    	
    	if(telephoneValidate($("#telephone").val()))
    		flag_telephone = true;
    	if(QQValidate($("#QQ").val()))
    		flag_QQ = true;
    	
    	if(!flag_telephone){
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
        flag_telephone = false;
        flag_QQ = false;
    }
</script>
<title>编辑用户_${user.loginName}</title>
</head>
<body>
<center>
    <form action="operateUser_updateUser" onsubmit="return check()" method="post">
        <table> 
            <tr>
            <tr>
                <td>登录名：</td><td>${user.loginName}</td>
                <td>真实姓名：</td><td>${user.trueName}</td>
            </tr>
            <tr>
                <td>角色：</td>
                <td><select name="roleId" >
			    <c:forEach var="role" items="${roles}">
			        <c:if test="${user.role.roleName==role.roleName}">
			            <option value="${role.roleId}" selected="selected">${role.roleRemark}</option>
			        </c:if>
			        <c:if test="${user.role.roleName!=role.roleName}">
				    <option value="${role.roleId}">${role.roleRemark}</option>
				    </c:if>			    
			    </c:forEach>
			    </select></td>
                <td>注册时间：</td><td>${user.registerTime}</td>
            </tr>
            <tr>
                <td>最近登录：</td><td>${empty user.lastLogin ? '未登陆':user.lastLogin}</td>
                <td>职位：</td><td><input type="text" value="${user.position}" name="position"/></td>
            </tr>
            <tr>
                <td>是否允许登陆：</td>
                <td>
                    <select name="logFlag">
                    <c:if test="${user.logFlag == false}">
                        <option value="false" selected="selected">不允许</option>
                        <option value="true">允许</option>
                    </c:if> 
                    <c:if test="${user.logFlag == true}">                    
                        <option value="true" selected="selected">允许</option>
                        <option value="false">不允许</option>
                    </c:if>
                    </select>
                </td>
            </tr>
            <tr>
                <td>部门：</td><td><input type="text"  value="${user.dept}" name="dept" id="dept"/></td>
                <td>公司：</td><td><input type="text"  value="${user.company}" name="company" id="company"/></td>
            </tr>
            <tr>
                <td>职位：</td><td><input type="text"  value="${user.position}" name="position" id="position"/></td>
                <td>电话：</td><td><input type="text"  value="${user.telephone}" name="telephone" id="telephone"/></td>
            </tr>
            <tr>
                <td>QQ：</td><td><input type="text"   value="${user.QQ}" name="QQ" id="QQ"/></td>
                <td>备注：</td><td><input type="text"  value="${user.remark}" name="remark" id="remark"/></td>
            </tr>           
        
            <tr>
              <td><input type="submit" value="提交" ></td>
              <td><input type="button"  value="重置密码" onclick="onResetPassword()"/></td>
              <td><input type="button" value="返回" onclick="javascript:window.location.href='operateUser_searchUserByPage?&userCurrentPage=${userCurrentPage}&userPageSize=${userPageSize}'";"></td>
            </tr>
        </table>
        <input type="hidden" value="${user.userId}" name="userId"/>
        <input type="hidden" value="${userPageSize}" name="userPageSize"/>
        <input type="hidden" value="${userCurrentPage}" name="userCurrentPage"/>        
     </form>
     <font color="red"><div id="validate_info"></div></font>
</center>
</body>
</html>