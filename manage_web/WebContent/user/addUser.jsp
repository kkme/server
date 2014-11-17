<%@page import="com.hifun.soul.manageweb.permission.PermissionType"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Map"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<link rel="stylesheet" type="text/css" href="style/main/default.css" />
<link rel="stylesheet" type="text/css" href="style/main/style.css" />
<link rel="stylesheet" type="text/css" href="style/main/table.css" />
<base href="<%=basePath%>">
<script language="javascript">
	function check() {
		var userName = form1.userName.value;
		var password = form1.password.value;
		if (userName.length == 0) {
			alert("用户名不能为空");
			return false;
		}
		if (password.length < 6) {
			alert("密码不能少于6位");
			return false;
		}
		if (userName.length < 4) {
			alert("username length must >= 4");
			return false;
		}
		if (password.length < 4) {
			alert("password length must >= 4");
			return false;
		}
		if (userName.length > 16) {
			alert("username to long");
			return false;
		}
		if (password.length > 16) {
			alert("password to long");
			return false;
		}
		var rule = /^[A-Za-z0-9]*$/;
		if (!rule.test(userName)) {
			alert("input error");
			return false;
		}
		if (!rule.test(password)) {
			alert("input error");
			return false;
		}
		return true;
	}
</script>

<title>用户信息编辑</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

</head>

<body>
	<center>
		<form action="addUser.action" method="post" name="form1">
			<table border="0">
				<tr>
					<td>用户名</td>
					<td><input type="text" name="userName" /></td>
				</tr>
				<tr>
					<td>密码</td>
					<td><input type="password" name="password" />*密码不能少于6位</td>
				</tr>
			</table>
			权限 <br />
			<table border="0">
				<%
					for (Map.Entry<String, List<PermissionType>> entry : PermissionType
							.getModuleMap().entrySet()) {
				%>
				<tr>
					<td><%=entry.getKey()%></td>
					<%
						for (PermissionType permission : entry.getValue()) {
					%>
					<td><%=permission.getDesc()%> <input type="checkbox"
						name="permission" value="<%=permission.getType()%>" /></td>
					<%
						}
					%>
				</tr>
				<%
					}
				%>
				<tr>
					<td colspan="5" align="center"><input type="submit" value="添加"
						onclick="return check();" /></td>
				</tr>
			</table>
		</form>
	</center>
</body>
</html>
