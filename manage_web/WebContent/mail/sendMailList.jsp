<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>邮件列表</title>
<link rel="stylesheet" type="text/css" href="style/main/default.css" />
<link rel="stylesheet" type="text/css" href="style/main/style.css" />
<link rel="stylesheet" type="text/css" href="style/main/table.css" />
<script type="text/javascript" src="script/common/paging.js"></script>
</head>
<body>
<s:form action="querySendMailList">
<table>
			<tr>
				<td colspan="12">主题：
					 <s:textfield name="themeKey" theme="simple" /> 
					 内容：<s:textfield name="contentKey" theme="simple" />
					 收件人id<s:textfield name="receiveHumanId" theme="simple" />
					 收件人姓名<s:textfield name="receiveHumanName" theme="simple" />
					 发送人姓名：<s:textfield name="sendHumanNameKey" theme="simple" />
					 备注：<s:textfield name="sendMemoKey" theme="simple" />
					 <s:submit value="查询" theme="simple" />
				</td>
			</tr>
			<tr class="title">
				<th>序号</th>
				<th>邮件类型</th>
				<th>收件人id</th>
				<th>主题</th>
				<th>内容</th>
				<th>发件人id</th>
				<th>发件人姓名</th>
				<th>邮件过期时间</th>
				<th>附件id</th>
				<th>备注</th>
				<th>发送时间</th>
				<th>操作</th>
			</tr>
			<s:iterator value="mailList" var="mailModel" status="status">
				<tr>
					<td><s:property value="#status.index+1" /> 
					</td>
					<td>
					<s:if test="#mailModel.mailType==1">
					系统邮件
					</s:if>
				<s:elseif test="#mailModel.mailType==2">
					用户邮件
				</s:elseif>
					</td>
					<td>
						<s:property value="#mailModel.receiveHumanIds" />
					</td>
					<td>
						<s:property value="#mailModel.theme" />
					</td>
					<td>
						<s:property value="#mailModel.content" escape="false"/>
					</td>
					<td>
						<s:property value="#mailModel.sendHumanId" />
					</td>
					<td>
						<s:property value="#mailModel.sendHumanName" />
					</td>
					<td>
						<s:date name="#mailModel.expireDate" format="yyyy-MM-dd HH:mm:ss" />
					</td>
					<td>
						<s:property value="#mailModel.itemId" />[<s:property value="#mailModel.itemNum" />],						
						<br />
						<s:property value="#mailModel.itemId2" />[<s:property value="#mailModel.itemNum2" />]
						
					</td>
					<td>
						<s:property value="#mailModel.sendMemo" />
					</td>
					<td>
						<s:date name="#mailModel.sendTime" format="yyyy-MM-dd HH:mm:ss"/>
					</td>
					<td>
						<s:a href="sendMailForward.action?receiverId=%{#mailModel.sendHumanId}" theme="simple">回复发件人</s:a>&nbsp;&nbsp;
						<s:a href="sendMailForward.action?receiverId=%{#mailModel.receiveHumanIds}" theme="simple">发送给收件人</s:a>&nbsp;&nbsp;
						<s:a href="copyMail.action?mailId=%{#mailModel.mailId}" theme="simple">转发</s:a>&nbsp;&nbsp;
					</td>
				</tr>

			</s:iterator>
			<tr>
				<td colspan="4">
				记录总数[<s:property value="pagingInfo.totalCount" />] 
				</td>
				<td colspan="5">共[<s:property
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
						name="pagingInfo.totalCount/pagingInfo.pageSize"></s:hidden>
				</td>
			</tr>
		</table>
		</s:form>
</body>
</html>