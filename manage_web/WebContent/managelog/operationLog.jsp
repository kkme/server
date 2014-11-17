<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Date"%>
<%@page import="java.text.DateFormat"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="com.hifun.soul.proto.services.LogServices.OperationLog"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sx" uri="/struts-dojo-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>GM后台操作日志列表</title>
<sx:head />
<link rel="stylesheet" type="text/css" href="style/main/default.css" />
<link rel="stylesheet" type="text/css" href="style/main/style.css" />
<link rel="stylesheet" type="text/css" href="style/main/table.css" />
<script type="text/javascript" src="script/common/paging.js"></script>
</head>
<body>
	<s:form action="queryOperationLogList">
		<table>
			<tr>
				<td colspan="9">开始日期
				<sx:datetimepicker name="beginDate" displayFormat="yyyy-MM-dd" ></sx:datetimepicker>
				结束日期
				<sx:datetimepicker name="endDate" displayFormat="yyyy-MM-dd" ></sx:datetimepicker>
				用户id
				<s:textfield name="userId" theme="simple" ></s:textfield>
				用户名称
				<s:textfield name="userName" theme="simple" ></s:textfield>
				操作类型
				<s:textfield name="operationType" theme="simple" ></s:textfield>												
				 <s:submit value="查询" theme="simple" /></td>
			</tr>
			<tr>
			<td>序号</td>
			<td>用户id</td>
			<td>用户名称</td>
			<td>操作类型</td>
			<td>操作描述</td>
			<td>是否具有权限</td>
			<td>操作参数</td>
			<td>操作结果</td>
			<td>操作时间</td>			
			</tr>
			<%
				Object logObject = request.getAttribute("operationLogs");
					if (logObject != null ) {
						@SuppressWarnings("unchecked")
						List<OperationLog> operationLogs = (List<OperationLog>) logObject;
						DateFormat dfDateTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						int index = 1;
						for (OperationLog log : operationLogs) {
			%>
			<tr>
				<td><%=index++ %></td>
				<td><%=log.getUserId() %></td>
				<td><%=log.getUserName() %></td>
				<td><%=log.getOperationType() %></td>
				<td><%=log.getOperationText() %></td>
				<td><%=log.getHasPermission() %></td>
				<td><%=log.getParam() %></td>
				<td><%=log.getResult() %></td>
				<td><%=dfDateTime.format(new Date(log.getOperateTime())) %></td>				
			</tr>
			<%
						}
					}
			%>
			<tr>
				<td colspan="3">
				记录总数[<s:property value="pagingInfo.totalCount" />] 
				</td>
				<td colspan="3">共[<s:property
						value="pagingInfo.totalCount/pagingInfo.pageSize+1" />]页
						&nbsp;&nbsp;
						每页[<s:property value="pagingInfo.pageSize" />]条记录
				</td>
				<td colspan="3" align="center">
				<a href="#"
					onclick="gotoPrePage()">上一页</a> &nbsp;&nbsp;[<s:property
						value="pagingInfo.pageIndex+1" />] &nbsp;&nbsp; <a href="#"
					onclick="gotoNextPage()">下一页</a> <s:hidden id="currentPage"
						name="pagingInfo.pageIndex"></s:hidden> <s:hidden id="totalPage"
						name="pagingInfo.totalCount/pagingInfo.pageSize"></s:hidden></td>
			</tr>
		</table>
	</s:form>
</body>
</html>