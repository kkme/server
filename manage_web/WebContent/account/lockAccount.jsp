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
<script language="javascript">
	function check() {
		var lockReason = form1.lockReason.value;
		if (userName.length == 0) {
			alert("封锁账号原因不能不能为空");
			return false;
		}
		if (lockReason.length > 20) {
			alert("原因过长,超过限制 : " + 20);
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
	<form action="lockAccount.action" method="post" name="form1">
		<table border="0">
			<tr>
				<th>封锁账号原因:</th>
			</tr>
			<tr>
				<td><input type="hidden" name="pid"
					value="<%=request.getParameter("pid")%>" /> <textarea rows="3"
						cols="25" name="lockReason"></textarea></td>
			</tr>
			<tr>
				<td align="center"><input type="submit" value="执行封号"
					onclick="return check()" /></td>
			</tr>
		</table>
	</form>
</body>
</html>
