<%@page import="org.apache.struts2.ServletActionContext"%>
<%@page import="com.hifun.soul.proto.services.Services.User"%>
<%@page import="java.util.List"%>
<%@ page language="java"
	pageEncoding="UTF-8"%>
<%
// 	String path = request.getContextPath();
// 	String basePath = request.getScheme() + "://"
// 			+ request.getServerName() + ":" + request.getServerPort()
// 			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<link rel="stylesheet" type="text/css" href="style/main/default.css" />
<link rel="stylesheet" type="text/css" href="style/main/style.css" />
<link rel="stylesheet" type="text/css" href="style/main/table.css" />


<title>用户列表</title>

</head>

<body>
<%
	List<User> userList = (List<User>)ServletActionContext.getRequest().getAttribute("userList");
%>
<table border="0">
	<tr>
		<th colspan="3">后台用户列表</th>
	</tr>
	<tr>
		<td>用户ID</td>
		<td>用户名</td>
		<td>操作</td>
	</tr>
	<%
		for (User user : userList) {
	%>
	<tr>
		<td><%=user.getId()%></td>
		<td><%=user.getUserName()%></td>
		<td><a
			href="updateUserPermissionForward?userId=<%=user.getId()%>">编辑</a>|
		<a href="deleteUser.action?userId=<%=user.getId()%>"
			onclick="return confirm('确认要删除此用户么?')">删除</a>|
		<a href="managerUpdateUserPasswordForward.action?userId=<%=user.getId()%>&userName=<%=user.getUserName() %>" >修改密码</a></td>
	</tr>
	<%
		}
	%>
	<tr>
		<td colspan="3"><a href="addUserForward.action">添加用户</a></td>

	</tr>
</table>
</body>
</html>
