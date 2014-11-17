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
</head>
<body>
	<s:form>		
		<table>
			<tr>
				<th colspan="2">职业分布</th>
			</tr>
			<tr class="title">
				<th>职业</th>
				<th>人数</th>
			</tr>
			<s:iterator value="occupationCounter" var="counter" status="status">
				<tr>
					<td><s:property value="#counter.occupation" /></td>
					<td><s:property value="#counter.count" /></td>
				</tr>
			</s:iterator>
			<tr>
			<td>总人数</td>
			<td><s:property value="roleTotalNum" /></td>
			</tr>
		</table>
		<table>
			<tr>
				<th colspan="2">等级分布</th>
			</tr>
			<tr class="title">
				<th>等级</th>
				<th>人数</th>
			</tr>
			<s:iterator value="levelCounter" var="counter" status="status">
				<tr>
					<td><s:property value="#counter.level" /></td>
					<td><s:property value="#counter.count" /></td>
				</tr>
			</s:iterator>
		</table>
	</s:form>
</body>
</html>