<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/12/10
  Time: 23:47
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>激活成功页面</title>
</head>
<body>
<center>
    <h2>恭喜您，账户激活成功！</h2>
</center>
<br>
<br>
<br>
<%--定时重定向--%>
3秒后自动跳转到登录页面...
<%
response.setHeader("Refresh","2;URL=http://localhost:8080/HeimaShop/login.jsp");
%>
<br>
<a href="login.jsp">页面没反应，手动跳转</a>
</body>
</html>
