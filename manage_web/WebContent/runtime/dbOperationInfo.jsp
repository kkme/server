<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>数据库实体操作信息</title>
<link rel="stylesheet" type="text/css" href="style/main/default.css" />
<link rel="stylesheet" type="text/css" href="style/main/style.css" />
<link rel="stylesheet" type="text/css" href="style/main/table.css" />
<script type="text/javascript" src="script/jquery/jquery-1.8.1.min.js"></script>
<style type="text/css">
.tabSelected {
	background: #CAE8EA;
}
</style>

<script type="text/javascript" language="javascript">
	$(document).ready(function() {
		$("#btnOperation").click(function() {
			$("#btnOperation").addClass("tabSelected");
			$("#dbOperationCounter").css("display", "block");
			$("#btnQuery").removeClass("tabSelected");
			$("#queryCounter").css("display", "none");
		});
		$("#btnQuery").click(function() {
			$("#btnOperation").removeClass("tabSelected");
			$("#dbOperationCounter").css("display", "none");
			$("#btnQuery").addClass("tabSelected");
			$("#queryCounter").css("display", "block");
		});
	});
</script>
</head>
<body>
	<s:form>
		<br />
		<div>
			<input type="button" id="btnOperation" value="数据实体操作压力表"
				class="tabSelected"  style="cursor:auto;"/> 
			<input type="button" id="btnQuery"
				value="数据库查询压力表"  style="cursor:auto;" />
			<div>
				<table id="dbOperationCounter">
					<tr class="title">
						<th>序号</th>
						<th>实体类</th>
						<th>UPDATE</th>
						<th>INSERT</th>
						<th>DELETE</th>
						<th>GET</th>
						<th>QUERY</th>
					</tr>
					<s:iterator value="dbOperationList" var="info" status="status">
						<tr>
							<td><s:property value="#status.index+1" /></td>
							<td><s:property value="#info.className" /></td>
							<td><s:property value="#info.updateCount" /></td>
							<td><s:property value="#info.insertCount" /></td>
							<td><s:property value="#info.deleteCount" /></td>
							<td><s:property value="#info.getCount" /></td>
							<td><s:property value="#info.queryCount" /></td>
						</tr>
					</s:iterator>
				</table>
			</div>
			<div id="queryCounter" style="display:none;">
				<table>
					<tr class="title">
						<th>序号</th>
						<th>查询名称</th>
						<th>请求数</th>
					</tr>
					<s:iterator value="dbQueryCounter" var="info" status="status">
						<tr>
							<td><s:property value="#status.index+1" /></td>
							<td><s:property value="#info.queryName" /></td>
							<td><s:property value="#info.count" /></td>
						</tr>
					</s:iterator>
				</table>
			</div>
		</div>
	</s:form>
</body>
</html>