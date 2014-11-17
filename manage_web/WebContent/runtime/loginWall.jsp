<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="stylesheet" type="text/css" href="style/main/default.css" />
<link rel="stylesheet" type="text/css" href="style/main/style.css" />
<link rel="stylesheet" type="text/css" href="style/main/table.css" />
<title>登陆墙状态</title>
</head>
<body>
	<%
		boolean enabled = (Boolean) request.getAttribute("enabled");
	%>
	<table border="0">
		<tr>
			<th>登陆墙状态</th>
			<th>操作</th>
		</tr>
		<tr>
		<% if(enabled){ %>
			<td>打开状态</td>
			<td><a href="updateLoginWallState.action?enabled=false">关闭</a></td>
		<%}else{ %>
			<td>关闭状态</td>
			<td><a href="updateLoginWallState.action?enabled=true">打开</a></td>
		<%} %>
		</tr>
		<tr>

		</tr>
	</table>
</body>
</html>