<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="stylesheet" type="text/css" href="style/main/default.css" />
<link rel="stylesheet" type="text/css" href="style/main/style.css" />
<link rel="stylesheet" type="text/css" href="style/main/table.css" />
<title>服务器信息</title>
</head>
<body>
	<%
		boolean open = (Boolean) request.getAttribute("open");
	%>
	<table border="0">
		<tr>
			<th>服务器对外开关状态</th>
			<th>操作</th>
		</tr>
		<tr>
			<td><%=open ? "打开状态" : "关闭状态"%></td>
			<td><a href="startExternalService.action">开</a>|<a
				href="stopExternalService.action">关</a></td>
		</tr>
		<tr>

		</tr>
	</table>
</body>
</html>