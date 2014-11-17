<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>玩家反馈列表</title>
<link rel="stylesheet" type="text/css" href="style/main/default.css" />
<link rel="stylesheet" type="text/css" href="style/main/style.css" />
<link rel="stylesheet" type="text/css" href="style/main/table.css" />
<script type="text/javascript" src="script/common/paging.js"></script>
</head>
<body>
<s:form action="queryQuestionList">
<table>
			<tr>
				<td colspan="9">
				             反馈类型： <s:textfield name="questionType" theme="simple" /> 
					 角色名称：<s:textfield name="askerNameKey" theme="simple" />	
					 内容：<s:textfield name="contentKey" theme="simple" />
					 <s:checkbox id="onlyUnAnswered" name="onlyUnAnswered" theme="simple">查询所有未回复的反馈</s:checkbox>
					<s:submit value="查询" theme="simple" />						
				</td>
			</tr>
			<tr class="title">
				<th>序号</th>
				<th>主键id</th>
				<th>问题id</th>
				<th>问题类型</th>
				<th>角色id</th>
				<th>角色名称</th>				
				<th>内容</th>
				<th>反馈时间</th>				
				<th>操作</th>
			</tr>
			<s:iterator value="questionList" var="questionModel" status="status">
				<tr>
					<td><s:property value="#status.index+1" /> 
					</td>
					<td><s:property value="#questionModel.id" /> 
					</td>
					<td><s:property value="#questionModel.questionId" /> 
					</td>
					<td>
						<s:if test="#questionModel.questionType==1">bug</s:if>
						<s:elseif test="#questionModel.questionType==2">投诉</s:elseif>
						<s:elseif test="#questionModel.questionType==3">建议</s:elseif>
						<s:elseif test="#questionModel.questionType==4">其他</s:elseif>
						<s:else>未知</s:else>
					</td>
					<td>
						<s:property value="#questionModel.askerId" /> 
					</td>
					<td>
						<s:property value="#questionModel.askerName" /> 
					</td>
					<td>
						<s:property value="#questionModel.content" /> 
					</td>
					<td>
						<s:date name="#questionModel.askTime" format="yyyy-MM-dd HH:mm:ss" /> 
					</td>
					<td>
						<s:a href="answerQuestionForward.action?id=%{#questionModel.id}" theme="simple">回复</s:a>&nbsp;&nbsp;
					</td>
				</tr>
			</s:iterator>
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
						name="pagingInfo.totalCount/pagingInfo.pageSize"></s:hidden>
				</td>
			</tr>
		</table>
		</s:form>
</body>
</html>