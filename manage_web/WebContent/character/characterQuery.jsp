<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>角色查询</title>
<link rel="stylesheet" type="text/css" href="style/main/default.css" />
<link rel="stylesheet" type="text/css" href="style/main/style.css" />
<link rel="stylesheet" type="text/css" href="style/main/main.css" />
</head>
<body>
	<s:form action="queryCharacterList">
		<table>
			<tr class="title">
				<th style="text-align: left;">查询条件</th>
			</tr>
			<tr>
				<td>角色id： <s:textfield name="queryCondition.queryId"
						theme="simple" /> &nbsp;&nbsp;角色名称：<s:textfield
						name="queryCondition.queryName" theme="simple" />
					&nbsp;&nbsp;账户名称：<s:textfield name="queryCondition.accountName"
						theme="simple" /> <s:submit value="查询" theme="simple" />
				</td>
			</tr>
		</table>
	</s:form>
</body>
</html>