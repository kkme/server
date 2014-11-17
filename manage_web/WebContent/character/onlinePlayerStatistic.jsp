<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>玩家状态统计</title>
<link rel="stylesheet" type="text/css" href="style/main/default.css" />
<link rel="stylesheet" type="text/css" href="style/main/style.css" />
<link rel="stylesheet" type="text/css" href="style/main/table.css" />
</head>
<body>
<s:form action="kickOffAllCharacter">
	<table>
	<tr>
			<th colspan="2">玩家状态统计</th>
	</tr>	
	<tr>
		<td>状态</td>
		<td>人数</td>
	</tr>
	<tr>
		<td>建立网络会话中</td>
		<td><s:property value="connectedCount" /></td>
	</tr>
	<tr>
		<td>已认证</td>
		<td><s:property value="authronizedCount" /></td>
	</tr>
	<tr>
		<td>正在进入游戏中</td>
		<td><s:property value="enteringCount" /></td>
	</tr>
	<tr>
		<td>游戏中</td>
		<td><s:property value="gameingCount" /></td>
	</tr>
	<tr>
		<td>战斗中</td>
		<td><s:property value="battlingCount" /></td>
	</tr>
	<tr>
		<td>退出中</td>
		<td><s:property value="exitingCount" /></td>
	</tr>
	<tr>
		<td>其他</td>
		<td><s:property value="otherCount" /></td>
	</tr>
	<tr>
		<td>总计</td>
		<td><s:property value="totalCount" /></td>
	</tr>
	<tr>
		<td colspan="2"><s:submit value="将所有玩家踢下线" theme="simple" onclick="return confirm('是否确定将所有玩家踢下线？');" /></td>
	</tr>
	</table>
</s:form>
</body>
</html>