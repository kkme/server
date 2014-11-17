<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
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
	<s:form action="changeManageServer">
		<table>
			<tr class="title">
				<th>序号</th>
				<th>服务器id</th>
				<th>服务器名称</th>
				<th>是否为当前连接的服务器</th>
				<th>服务器运行状态</th>
				<th>操作</th>
			</tr>
			<s:iterator value="gameServerList" var="serverInfo" status="status">
				<tr>
					<td><s:property value="#status.index+1" /></td>
					<td><s:property value="#serverInfo.serverId" /></td>
					<td><s:property value="#serverInfo.serverName" /></td>
					<td><s:checkbox name="#serverInfo.connectionState"
							onclick="return false;" theme="simple"></s:checkbox></td>
					<td><s:property value="#serverInfo.runState" /></td>
					<td><s:if test="#serverInfo.runState==true">
							<a href="stopGameServer.action?serverId=<s:property value="#serverInfo.serverId" />">停服</a>
						</s:if></td>
				</tr>
			</s:iterator>
			<!-- 
			<tr>
				<td colspan="4">
					<div>
						连接服务器Id：
						<s:textfield id="txtGameServerId" name="gameServerId"
							theme="simple"></s:textfield>
						&nbsp;&nbsp;
						<s:submit title="提交修改" value="提交修改" theme="simple"></s:submit>
					</div>
				</td>
			</tr>
			 -->
		</table>

	</s:form>
</body>
</html>