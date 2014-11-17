<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sx" uri="/struts-dojo-tags"%>
<%@ page import="java.util.Date"%>
<%@ page import="java.text.DateFormat"%>
<%@ page import="java.text.SimpleDateFormat"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>发布公告</title>
<sx:head />
<link rel="stylesheet" type="text/css" href="style/main/default.css" />
<link rel="stylesheet" type="text/css" href="style/main/style.css" />
<link rel="stylesheet" type="text/css" href="style/main/table.css" />
<script type="text/javascript" src="script/jquery/jquery-1.8.1.min.js"></script>
<script type="text/javascript" src="ckeditor/ckeditor.js"></script>
<link rel="stylesheet" media="all" type="text/css"
	href="style/jqueryui/jquery-ui-1.10.3.custom.min.css" />
<link rel="stylesheet" media="all" type="text/css"
	href="style/jqueryui/jquery-ui-timepicker-addon.css" />
<script type="text/javascript"
	src="script/jquery/jquery-ui-1.10.3.custom.min.js"></script>
<script type="text/javascript"
	src="script/jquery/jquery-ui-timepicker-addon.js"></script>
<script type="text/javascript"
	src="script/jquery/jquery-ui-sliderAccess.js"></script>
<script type="text/javascript"
	src="script/jquery/jquery-ui-timepicker-zh-CN.js"></script>
<style type="text/css">
em{
	color:red;
}
</style>
<script type="text/javascript">
$(function() {
	$(".ui_timepicker").datetimepicker({
		//showOn: "button",
		//buttonImage: "./css/images/icon_calendar.gif",
		//buttonImageOnly: true,
		showSecond : false,
		timeFormat : 'HH:mm:ss',
		dateFormat : 'yy-mm-dd',
	});
});
function confirmBeforeSubmit(){
	var editFlag = $("#bulletinEditState").val()=="true";
	var confirmText = "是否确定发送这则公告？";
	if(editFlag){
		confirmText = "是否确定保存修改？";
	}
	return confirm(confirmText);
}
</script>

</head>
<body>
	<s:form action="addBulletin" validate="true">
		<table>
			<tr>
				<td>公告类型：</td>
				<td>
				<s:select name="bulletinModel.bulletinType" list="#{1:'一次性定时公告',3:'循环公告'}" headerKey="1" theme="simple" ></s:select>
				<em>*</em>	说明：若只发文本公告，请点击编辑器的源码		
				<s:hidden id="bulletinId" name="bulletinModel.id"></s:hidden>
				<s:hidden id="bulletinEditState" name="editState"></s:hidden>	
				</td>
			</tr>
			<tr>
				<td>内容：</td>
				<td><s:textarea id="content" name="bulletinModel.content" theme="simple" style="width:400px; height:300px;"></s:textarea>
				<em>*</em>	
				</td>
			</tr>
			<tr>
				<td>发布时间:</td>
				<td>
				<s:textfield id="publishTimeString" name="bulletinModel.publishTimeString" cssClass="ui_timepicker" theme="simple" /><em>*</em>	 格式：1999-1-1 11:11:00				
			</td>
			</tr>
			<tr>
				<td>公告级别：</td>
				<td>
				<s:textfield id="level" name="bulletinModel.level" theme="simple" value="1"></s:textfield><em>*</em>
				</td>
			</tr>
			<tr>
				<td>客户端显示时长：</td>
				<td>
				<s:textfield id="showTime" name="bulletinModel.showTime" theme="simple" value="3"></s:textfield><em>*</em>
				</td>
			</tr>
			<tr>
				<td>开始日期：</td>
				<td><sx:datetimepicker id="startDate" name="bulletinModel.startDate"
			displayFormat="yyyy-MM-dd" ></sx:datetimepicker></td>
			</tr>
			<tr>
				<td>结束日期：</td>
				<td><sx:datetimepicker id="endDate" name="bulletinModel.endDate"
			displayFormat="yyyy-MM-dd" ></sx:datetimepicker>必须大于开始日期</td>
			</tr>
			<tr>
				<td>每日开始时间：</td>
				<td>
				<s:textfield id="startTimeString" name="bulletinModel.startTimeString" theme="simple" /> 格式：12:00:00				
			</td>
			</tr>
			<tr>
				<td>每日结束时间：</td>
				<td>
				<s:textfield id="endTimeString" name="bulletinModel.endTimeString" theme="simple" /> 格式：12:00:00				
			</td>
			</tr>
			<tr>
				<td>发布时间间隔:</td>
				<td><s:textfield id="publishInterval" name="bulletinModel.publishInterval" theme="simple" /> 单位：秒</td>
			</tr>
			<tr>
				<td colspan="2"><s:submit value="提交" onclick="return confirmBeforeSubmit();"></s:submit></td>
			</tr>
			<tr>
				<td colspan="2"><s:fielderror></s:fielderror></td>
			</tr>
		</table>


		


		
		
	</s:form>
</body>
</html>