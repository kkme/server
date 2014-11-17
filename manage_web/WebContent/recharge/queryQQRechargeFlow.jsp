<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sx" uri="/struts-dojo-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>qq充值业务查询</title>
<sx:head />
<link rel="stylesheet" type="text/css" href="style/main/default.css" />
<link rel="stylesheet" type="text/css" href="style/main/style.css" />
<link rel="stylesheet" type="text/css" href="style/main/table.css" />
</head>
<body>
	<s:form action="queryRechargeFlow">
		<table>
			<tr>
				<td colspan="22">开始日期
				<sx:datetimepicker name="beginTime" displayFormat="yyyy-MM-dd" ></sx:datetimepicker>
				结束日期
				<sx:datetimepicker name="endTime" displayFormat="yyyy-MM-dd" ></sx:datetimepicker>
				流水号
				<s:textfield name="billno" theme="simple" ></s:textfield>
				角色id
				<s:textfield name="humanId" theme="simple" ></s:textfield>
				openId
				<s:textfield name="openId" theme="simple" ></s:textfield>
				<s:submit value="查询" theme="simple" /></td>
			</tr>			
		</table>
	</s:form>
</body>
</html>