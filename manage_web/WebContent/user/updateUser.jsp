<%@page import="com.hifun.soul.manageweb.permission.PermissionType"%>
<%@page import="com.hifun.soul.proto.services.Services.User"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
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
<title>My JSP 'editUser.jsp' starting page</title>

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
	<%
		User user = null;
		user = (User) request.getAttribute("user");
		String permissions = user.getPermissions();
		String[] permissionArr = permissions.split(",");
	%>

	<center>
		<form name="form1"
			action="updateUserPermission.action?userId=<%=user.getId()%>"
			method="post">
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
						name="permission" value="<%=permission.getType()%>"
						<%if (Arrays.asList(permissionArr).contains(
							"" + permission.getType())) {
						out.println("checked = 'checked'");
					}%> /></td>
					<%
						}
					%>
				</tr>
				<%
					}
				%>
				<tr>
					<td colspan="5" align="center"><input type="submit"	 value="提交" /></td>
				</tr>
			</table>
		</form>
	</center>
</body>
</html>
