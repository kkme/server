<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ page import="com.hifun.soul.core.enums.AccountState" %>
<%@ page import="com.hifun.soul.gamedb.PlayerPermissionType" %>
<%@taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>更新账户信息</title>
<link rel="stylesheet" type="text/css" href="style/main/default.css" />
<link rel="stylesheet" type="text/css" href="style/main/style.css" />
<link rel="stylesheet" type="text/css" href="style/main/table.css" />
<script type="text/javascript" src="script/jquery/jquery-1.8.1.min.js"></script>
<script language="javascript" type="text/javascript">
	function onPermissionChange(event){
		$("#hidPermission").val(event.value);
	}
</script>
</head>
<body>
<form action="updateAccountInfo.action">
<table>
	<tr>
		<td>账号id</td>
		<td><s:property value="accountInfo.passportId" /></td>
	</tr>
	<tr>
		<td>账号名称</td>
		<td><s:property value="accountInfo.userName" /></td>
	</tr>
	<tr>
		<td>账号权限</td>
		<td>
		
			<select id="sltPermission" onchange="onPermissionChange(this)">				
				<s:if test="accountInfo.permissionType==1">
				<option value="1" selected>普通用户</option>
				<option value="2">GM用户</option>
				</s:if>
				<s:elseif test="accountInfo.permissionType==2">
				<option value="1">普通用户</option>
				<option value="2" selected>GM用户</option>
				</s:elseif>
			</select>
		<s:hidden id="hidPermission" name="accountInfo.permissionType" value="%{accountInfo.permissionType}"></s:hidden>
		</td>
	</tr>
	<tr>
		<td>账号状态</td>
		<td>		
		<s:if test="accountInfo.state==1" >
			正常
			</s:if>
			<s:elseif test="accountInfo.state==0">
			已锁定
			</s:elseif>
		</td>
	</tr>
	<tr>
		<td colspan="2" align="center">
			<s:submit value="提交" theme="simple"></s:submit>
		</td>
	</tr>	
</table>
</form>
</body>
</html>