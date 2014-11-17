<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%@page import="com.hifun.soul.manageweb.runtime.GameServerStatus"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>游戏服务器列表</title>
<link rel="stylesheet" type="text/css" href="style/main/default.css" />
<link rel="stylesheet" type="text/css" href="style/main/style.css" />
<link rel="stylesheet" type="text/css" href="style/main/table.css" />
<script type="text/javascript" src="script/common/paging.js"></script>
</head>
<body>
	<s:form>
		<table>
			<tr class="title">
				<th>序号</th>
				<th>服务器id</th>
				<th>服务器名称</th>
				<th>是否为当前连接的服务器</th>
				
			</tr>
			<s:iterator value="manageServerList" var="serverInfo" status="status">
				<tr>
					<td><s:property value="#status.index+1" /></td>
					<td><s:property value="#serverInfo.serverId" /></td>
					<td><s:property value="#serverInfo.serverName" /></td>
					<td><s:checkbox name="#serverInfo.connectionState"
							onclick="return false;" theme="simple"></s:checkbox></td>					
				</tr>
			</s:iterator>			
		</table>

	</s:form>
</body>
</html>