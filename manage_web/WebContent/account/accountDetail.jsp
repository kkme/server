<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.hifun.soul.manageweb.account.AccountModel" %>
<%@ page import="com.hifun.soul.core.enums.AccountState" %>
<%@ page import="com.hifun.soul.gamedb.PlayerPermissionType" %>
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

<title>用户列表</title>

</head>
<%
	AccountModel account = (AccountModel)request.getAttribute("accountInfo");
%>
<body>

	<table border="0">
		<tr>
			<th>账号ID</th>
			<td><%=account.getPassportId()%></td>
		</tr>
		<tr>
			<th>账号名称</th>
			<td><%=account.getUserName()%></td>
		</tr>
		<tr>
			<th>账号权限</th>
			<td><%=account.getPermissionType() == PlayerPermissionType.NORMAL_PLAYER.getIndex() ? "普通用户" : "GM用户"%>
			&nbsp;&nbsp;
			<a href="queryAccount.action?pid=<%=account.getPassportId()%>&edit=1">修改权限</a>
			</td>
		</tr>
		<tr>
			<th>账号状态</th>
			<td><%=account.getState() == AccountState.NORMAL.getIndex() ? "正常" : "账号被锁定"%></td>
		</tr>
		<tr>
			<th>操作</th>
			<td><a
				href="<%if (account.getState() == AccountState.NORMAL.getIndex()) {
				out.println("lockAccountForward.action?pid="
						+ account.getPassportId());
			} else {
				out.println("unlockAccount.action?pid="
						+ account.getPassportId());
			}%>"
				onclick="return confirm('确定要执行此操作么?')"><%=(account.getState() == AccountState.NORMAL.getIndex()) ? "封锁此号" : "解锁此号"%></a></td>
		</tr>

	</table>
</body>
</html>
