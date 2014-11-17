<%@page import="org.apache.struts2.ServletActionContext"%>
<%@page import="com.opensymphony.xwork2.ActionContext"%>
<%@page import="com.hifun.soul.proto.services.Services.User"%>
<%@ page language="java"
	pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<style type="text/css">
body {
	font: normal 11px auto "Trebuchet MS", Verdana, Arial, Helvetica,
		sans-serif;
	color: #4f6b72;
}

a {
	overflow: visible;
	font-family: Tahoma, Helvetica, Arial, "宋体", sans-serif;
	color: #0047D8;
}

a:hover {
	color: #9997D8;
}

#mytable {
	width: 700px;
	padding: 0;
	margin: 0;
}

caption {
	padding: 0 0 5px 0;
	width: 700px;
	font: 18px Bold "Trebuchet MS", Verdana, Arial, Helvetica, sans-serif;
	text-align: center;
}

th {
	font: bold 11px "Trebuchet MS", Verdana, Arial, Helvetica, sans-serif;
	color: #4f6b72;
	border-right: 1px solid #C1DAD7;
	border-bottom: 1px solid #C1DAD7;
	border-top: 1px solid #C1DAD7;
	letter-spacing: 2px;
	text-transform: uppercase;
	text-align: left;
	padding: 6px 6px 6px 12px;
	background: #CAE8EA url(images/bg_header.jpg) no-repeat;
}

th.nobg {
	border-top: 0;
	border-left: 0;
	border-right: 1px solid #C1DAD7;
	background: none;
}

td {
	border-right: 1px solid #C1DAD7;
	border-bottom: 1px solid #C1DAD7;
	background: #fff;
	font-size: 11px;
	padding: 6px 6px 6px 12px;
	color: #4f6b72;
}

td.alt {
	background: #F5FAFA;
	color: #797268;
}
</style>
<link rel="stylesheet" type="text/css" href="style/main/default.css" />
<link rel="stylesheet" type="text/css" href="style/main/style.css" />
<link rel="stylesheet" type="text/css" href="style/main/table.css" />
<title>用户基本信息</title>
</head>

<body>

<%
	User user = (User)ServletActionContext.getRequest().getSession().getAttribute("user");
%>

<table border="0">
	<tr>
		<th colspan="2">用户基本信息</th>

	</tr>
	<tr>
		<td>ID</td>
		<td><%=user.getId() %></td>
	</tr>
	<tr>
		<td>用户名</td>
		<td><%=user.getUserName() %></td>
	</tr>
</table>
</body>
</html>
