<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.Date"%>
<%@ page import="com.hifun.soul.proto.services.Services.Bulletin"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<link rel="stylesheet" type="text/css" href="style/main/default.css" />
<link rel="stylesheet" type="text/css" href="style/main/style.css" />
<link rel="stylesheet" type="text/css" href="style/main/table.css" />
<script type="text/javascript" src="script/common/paging.js"></script>
</head>
<body>
	<%
		@SuppressWarnings("unchecked")
		List<Bulletin> characterList = (List<Bulletin>) request.getAttribute("bulletinList");
	%>
	<s:form action="queryBulletinList">
		<table>
			<tr>
				<td colspan="16">
				<span style="float: left">内容关键字：
				<s:textfield name="queryCondition.content" type="text" theme="simple" /> &nbsp;&nbsp; 
				有效标识<s:textfield name="queryCondition.validState" theme="simple" />[-1表示所有状态] 
						<s:submit value="查询" theme="simple" />
				</span> <span style="float: right"> <a
						href="addBulletinForward.action">添加公告</a>
				</span></td>
			</tr>
			<tr class="title">
				<th>序号</th>
				<th>公告id</th>
				<th>公告类型</th>
				<th>公告内容</th>
				<th>发布时间</th>
				<th>公告级别</th>
				<th>客户端显示时长</th>
				<th>是否有效</th>
				<th>开始日期</th>
				<th>截止日期</th>
				<th>每日开始时间</th>
				<th>每日截止时间</th>
				<th>发布时间间隔</th>
				<th>下一次发布时间</th>
				<th>创建时间</th>
				<th>操作</th>
			</tr>
			<s:iterator value="bulletins" var="bulletin" status="status">
				<tr>
					<td><s:property value="#status.index+1" /></td>
					<td><s:property value="#bulletin.id" /></td>
					<td><s:if test="#bulletin.bulletinType==1">
						一次性定时公告</s:if> <s:elseif test="#bulletin.bulletinType==2">
						每天一次的循环公告
						</s:elseif> <s:elseif test="#bulletin.bulletinType==3">
						循环公告
						</s:elseif></td>
					<td><s:property value="#bulletin.content"  escape="false" /></td>
					<td><s:date name="#bulletin.publishTime"
							format="yyyy-MM-dd hh:mm:ss" /></td>
					<td><s:property value="#bulletin.level" /></td>
					<td><s:property value="#bulletin.showTime" /></td>
					<td><s:property value="#bulletin.validState" /></td>
					<td><s:property value="#bulletin.startDate" /></td>
					<td><s:property value="#bulletin.endDate" /></td>
					<td><s:date name="#bulletin.startTime" format="HH:mm:ss" /></td>
					<td><s:date name="#bulletin.endTime" format="HH:mm:ss" /></td>
					<td><s:property value="#bulletin.publishInterval" /></td>
					<td><s:property value="#bulletin.lastPublishTime" /></td>
					<td><s:property value="#bulletin.createTime" /></td>
					<td>
					<s:if test="#bulletin.validState==1">
						<s:a href="removeBulletin.action?id=%{#bulletin.id}">置为无效</s:a>						
						<s:a href="editBulletin.action?id=%{#bulletin.id}">编辑</s:a>
					</s:if>
					<s:a href="copyBulletin.action?id=%{#bulletin.id}">复制公告</s:a>
					</td>
				</tr>

			</s:iterator>
			<tr>
				<td colspan="6">记录总数[<s:property value="pagingInfo.totalCount" />]
				</td>
				<td colspan="5">共[<s:property
						value="pagingInfo.totalCount/pagingInfo.pageSize+1" />]页
					&nbsp;&nbsp; 每页[<s:property value="pagingInfo.pageSize" />]条记录
				</td>
				<td colspan="5" align="center">						
						<a href="#"
					onclick="gotoPrePage()">上一页</a> &nbsp;&nbsp;[<s:property
						value="pagingInfo.pageIndex+1" />] &nbsp;&nbsp; <a href="#"
					onclick="gotoNextPage()">下一页</a> <s:hidden id="currentPage"
						name="pagingInfo.pageIndex"></s:hidden> <s:hidden id="totalPage"
						name="pagingInfo.totalCount/pagingInfo.pageSize"></s:hidden>
				</td>
						
						
			</tr>
		</table>
		<br />
	</s:form>
</body>
</html>