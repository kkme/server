<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %> 
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<base href="<%=basePath%>">

		<title>战神之魂后台管理</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<script type="text/javascript" src="script/jquery/jquery-1.8.1.min.js"></script>
		<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
		<style type="text/css">
#login {
	border: 1px #4f6b72 solid;
	width: 300px;
	margin-top: 200px;
	height: 200px;
}

#login table {
	font-weight: bold;
	margin-top: 15px;
	font-size: 13px;
}

#login p {
	font-weight: bold;
	height: 30px;
	border-bottom: 1px solid #D9D9D9;
	font-weight: bold;
	font-size: 15px;
	color: #ffffff;
	padding-top: 10px;
	margin-bottom: 0px;
	background-color: #4f6b72;
}
</style>

<script type="text/javascript">
	
$(document).ready(function() {
	$.post("queryManageServerListByJson","",function(serverData){
		var dataObj = eval("(" + serverData + ")");
		var serverListObj = $.parseJSON(dataObj.serverBaseInfoList);
		var sltCate = $("#sltServerList");
		for(var i=0;i<serverListObj.length;i++){
			sltCate.append("<option value='"+serverListObj[i].serverId+"'>"+serverListObj[i].serverName+"</option>");
		}	
	});
});
	function onRememberMeChange(){
		
	}
</script>

	</head>

	<body>
		<%
			String loginInfo = (String) request.getAttribute("loginInfo");
			if (loginInfo == null) {
				loginInfo = "";
			}
		%>
		<center>
			<div id="login">
				<form method="post" action="<s:url action='login.action' />">
					<p>
						战神之魂后台管理系统
					</p>
					<table>
						<tr>
							<td >
								服务器</td>
							<td><select id="sltServerList" name="serverId"></select>
							</td>
						</tr>
						<tr>
							<td>
								用户名
							</td>
							<td>
								<input type="text" name="userName" size="27" value=""/>
							</td>
						</tr>
						<tr>
							<td>
								密 码
							</td>
							<td>
								<input type="password" name="password" size="27" value="" />
							</td>
						</tr>
						<!-- 
						<tr>
							<td colspan="2">
								<input id="chkRememberMe" type="checkbox" value="记住密码" onchange="onRememberMeChange">
							</td>
						</tr>
						 -->
						<tr align="center">
							<td colspan="2">
								<input type="submit" value="登陆" class="tj" />
							</td>
						</tr>
						<tr>
							<td colspan="2">
								<font color="red"><%=loginInfo%></font>
							</td>
						</tr>
					</table>
				</form>
			</div>
		</center>
	</body>
</html>
