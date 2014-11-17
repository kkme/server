<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Date"%>
<%@page import="java.text.DateFormat"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="com.hifun.soul.proto.services.LogServices.QuestLog"%>
<%@page import="com.hifun.soul.manageweb.serverlog.LogReasonParser"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sx" uri="/struts-dojo-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>任务日志列表</title>
<sx:head />
<link rel="stylesheet" type="text/css" href="style/main/default.css" />
<link rel="stylesheet" type="text/css" href="style/main/style.css" />
<link rel="stylesheet" type="text/css" href="style/main/table.css" />
<script type="text/javascript" src="script/common/paging.js"></script>
</head>
<body>
	<s:form action="queryQuestLogList">
		<table>
			<tr>
				<td colspan="17">开始日期
				<sx:datetimepicker name="beginDate" displayFormat="yyyy-MM-dd" ></sx:datetimepicker>
				结束日期
				<sx:datetimepicker name="endDate" displayFormat="yyyy-MM-dd" ></sx:datetimepicker>
				服务器id
				<s:textfield name="serverId" theme="simple" ></s:textfield>
				账号id
				<s:textfield name="accountId" theme="simple" ></s:textfield>
				账号名称
				<s:textfield name="accountName" theme="simple" ></s:textfield>
				角色id
				<s:textfield name="characterId" theme="simple" ></s:textfield>
				角色名称
				<s:textfield name="characterName" theme="simple" ></s:textfield>
				记录原因id<s:textfield name="logReason" theme="simple" ></s:textfield>				
				 <s:submit value="查询" theme="simple" /></td>
			</tr>
			<tr>
			<td>序号</td>
			<td>地区编号</td>
			<td>服务器编号</td>
			<td>账号id</td>
			<td>账号名称</td>
			<td>角色id</td>
			<td>角色名称</td>
			<td>角色等级</td>
			<td>联盟id</td>
			<td>VIP等级</td>
			<td>记录原因</td>
			<td>记录原因描述</td>
			<td>原因参数</td>
			<td>日志类型</td>
			<td>记录时间</td>
			<td>任务编号</td>			
			<td>日志创建时间</td>
			</tr>
			<%
				Object logObject = request.getAttribute("questLogs");
					if (logObject != null ) {
						@SuppressWarnings("unchecked")
						List<QuestLog> questLogs = (List<QuestLog>) logObject;
						DateFormat dfDateTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						int index = 1;
						for (QuestLog log : questLogs) {
			%>
			<tr>
				<td><%=index++ %></td>
				<td><%=log.getBaseLogInfoOrBuilder().getRegionId() %></td>
				<td><%=log.getBaseLogInfoOrBuilder().getServerId() %></td>
				<td><%=log.getBaseLogInfoOrBuilder().getAccountId() %></td>
				<td><%=log.getBaseLogInfoOrBuilder().getAccountName() %></td>
				<td><%=log.getBaseLogInfoOrBuilder().getCharId() %></td>
				<td><%=log.getBaseLogInfoOrBuilder().getCharName() %></td>
				<td><%=log.getBaseLogInfoOrBuilder().getLevel() %></td>
				<td><%=log.getBaseLogInfoOrBuilder().getAllianceId() %></td>
				<td><%=log.getBaseLogInfoOrBuilder().getVipLevel() %></td>
				<td><%=log.getBaseLogInfoOrBuilder().getReason() %></td>
				<td><%=LogReasonParser.getInstance().getReasonDesc(log.getBaseLogInfoOrBuilder().getLogType(),log.getBaseLogInfoOrBuilder().getReason())%></td>
				<td><%=log.getBaseLogInfoOrBuilder().getParam() %></td>
				<td><%=log.getBaseLogInfoOrBuilder().getLogType() %></td>
				<td><%=dfDateTime.format(new Date(log.getBaseLogInfoOrBuilder().getLogTime())) %></td>
				<td><%=log.getQuestId() %></td>				
				<td><%=dfDateTime.format(new Date(log.getCreateTime())) %></td>
			</tr>
			<%
						}
					}
			%>
			<tr>
				<td colspan="5">
				记录总数[<s:property value="pagingInfo.totalCount" />] 
				</td>
				<td colspan="6">共[<s:property
						value="pagingInfo.totalCount/pagingInfo.pageSize+1" />]页
						&nbsp;&nbsp;
						每页[<s:property value="pagingInfo.pageSize" />]条记录
				</td>
				<td colspan="6" align="center">
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