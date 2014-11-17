<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sx" uri="/struts-dojo-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>回复玩家反馈</title>
<sx:head />
<link rel="stylesheet" type="text/css" href="style/main/default.css" />
<link rel="stylesheet" type="text/css" href="style/main/style.css" />
<link rel="stylesheet" type="text/css" href="style/main/table.css" />
<script type="text/javascript" src="ckeditor/ckeditor.js"></script>
</head>
<body>
<s:form action="answerQuestion" validate="true">
		<table>
			<tr>
				<td>问题反馈者ID</td>
				<td><s:property value="questionModel.askerId"></s:property></td>
			</tr>
			<tr>
				<td>角色名称</td>
				<td><s:property value="questionModel.askerName"></s:property></td>
			</tr>
			<tr>
				<td>反馈类型</td>
				<td>
				<s:if test="questionModel.questionType==1">bug</s:if>
				<s:elseif test="questionModel.questionType==2">投诉</s:elseif>
				<s:elseif test="questionModel.questionType==3">建议</s:elseif>
				<s:elseif test="questionModel.questionType==4">其他</s:elseif>
				<s:else>未知</s:else>
				</td>
			</tr>
			<tr>
				<td>反馈内容</td>
				<td><s:property value="questionModel.content"></s:property></td>
			</tr>
			<tr>
				<td>反馈时间</td>
				<td><s:date name="questionModel.askTime" format="yyyy-MM-dd hh:mm:ss"/></td>
			</tr>			
			<tr>
				<td>回复内容</td>
				<td><s:textarea name="answerModel.content" theme="simple" style="width:400px;height:300px;"></s:textarea></td>
			</tr>
			<tr>
				<td colspan="2"><s:submit value="提交"></s:submit></td>
			</tr>
		</table>
	</s:form>
</body>
</html>