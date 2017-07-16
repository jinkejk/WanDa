<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>解码结果</title>
</head>
<body>
<center>
    <c:if test="${empty decodeString}">
        <font color="red">${msg}</font>
    </c:if>
    <c:if test="${!empty decodeString}">
        <c:if test="${!empty qRInfo}">
            <table border="1">
                <tr><td>图号</td><td colspan="3">${qRInfo.num}</td></tr>
                <tr><td>图名</td><td colspan="3">${qRInfo.name}</td></tr>
                <tr><td>工程名称</td><td colspan="3">${qRInfo.projectName}</td></tr>
                <c:forEach var="record" items="${qRInfo.record}" varStatus="status">	
                    <tr>
                        <td>签名人</td><td>${record.key}</td><td>签名时间</td><td>${record.value}</td>                    
                    </tr>
                </c:forEach>
            </table>
        </c:if>
        <c:if test="${empty qRInfo}">
                                二维码内容如下：${decodeString}
             <!--   请扫描正确的二维码！-->         
        </c:if>
    </c:if>
    
  <br>

  <a href="index.jsp">返回</a>
  </center>
</body>
</html>