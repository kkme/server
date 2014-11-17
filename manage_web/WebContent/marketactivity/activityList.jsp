<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ page import="com.hifun.soul.proto.services.Services.MarketActivitySetting" %>
<%@taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>游戏服务器列表</title>
<link rel="stylesheet" type="text/css" href="style/main/default.css" />
<link rel="stylesheet" type="text/css" href="style/main/style.css" />
<link rel="stylesheet" type="text/css" href="style/main/table.css" />
<script type="text/javascript" src="script/jquery/jquery-1.8.1.min.js"></script>
<script type="text/javascript" language="javascript">
	
	function onEditClick(typeId, state, roleLevel, vipLevel) {
		$("#txtMarketActType").val(typeId);
		if (state == "true") {
			$("#chkEnable").attr("checked", state);
		} else {
			$("#chkEnable").removeAttr("checked");
		}
		$("#txtRoleLevel").val(roleLevel);
		$("#txtVipLevel").val(vipLevel);
		$("#divEditBox").toggle(200);
	}
	
</script>
</head>
<body>
	<s:form id="form1" action="updateMarketActivitySetting">
		<table id="tblList" width="600">
			<tr class="title">
				<th>序号</th>
				<th>活动名称</th>
				<th>活动ID</th>
				<th>活动状态</th>
				<th>角色等级限制</th>
				<th>VIP等级限制</th>
				<th>操作</th>
			</tr>
			<s:iterator value="settings" var="settingInfo" status="status">
				<tr>
					<td><s:property value="#status.index+1" /></td>
					<td><s:if test="#settingInfo.marketActType==1">
					幸运大转盘
					</s:if> <s:else>
					未知
					</s:else>
					</td>
					<td><s:property value="#settingInfo.marketActType" /></td>
					<td>
						<s:checkbox name="#settingInfo.enable" onclick="javascript:return false;" theme="simple"></s:checkbox>
					</td>
					<td><s:property value="#settingInfo.roleLevel" /></td>
					<td><s:property value="#settingInfo.vipLevel" /></td>
					<td><s:a  href="javascript:onEditClick('%{#settingInfo.marketActType}','%{#settingInfo.enable}','%{#settingInfo.roleLevel}','%{#settingInfo.vipLevel}')">编辑</s:a></td>
				</tr>
			</s:iterator>			
		</table>
		<div id="divEditBox" style="display: none;" >
		<table  width="600">
			<tr>
			<td align="right">活动ID：</td>
			<td>
			<s:textfield id="txtMarketActType" name="marketActType" theme="simple"></s:textfield>
			</td>
			</tr>
			<tr>
			<td align="right">活动状态：</td>
			<td><s:checkbox id="chkEnable" name="enable" theme="simple"></s:checkbox>
			</td>
			</tr>
			<tr>
			<td align="right">角色等级限制：</td>
			<td><s:textfield id="txtRoleLevel" name="roleLevel" theme="simple"></s:textfield>
			</td>
			</tr>
			<tr>
			<td align="right">VIP等级限制：</td>
			<td><s:textfield id="txtVipLevel" name="vipLevel" theme="simple"></s:textfield>
			</td>
			</tr>
			<tr>
			<td  colspan="2" align="center" >
			<s:submit id="btnSubmit" title="提交修改" value="提交修改" theme="simple"/>
			
			</td>
			</tr>
		</table>
		</div>
	</s:form>
</body>
</html>