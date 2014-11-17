<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sx" uri="/struts-dojo-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>发送邮件</title>
<sx:head />
<link rel="stylesheet" type="text/css" href="style/main/default.css" />
<link rel="stylesheet" type="text/css" href="style/main/style.css" />
<link rel="stylesheet" type="text/css" href="style/main/table.css" />
<link rel="stylesheet" media="all" type="text/css"
	href="style/jqueryui/jquery-ui-1.10.3.custom.min.css" />
<link rel="stylesheet" media="all" type="text/css"
	href="style/jqueryui/jquery-ui-timepicker-addon.css" />
<script type="text/javascript" src="script/jquery/jquery-1.8.1.min.js"></script>
<script type="text/javascript"
	src="script/jquery/jquery-ui-1.10.3.custom.min.js"></script>
<script type="text/javascript"
	src="script/jquery/jquery-ui-timepicker-addon.js"></script>
<script type="text/javascript"
	src="script/jquery/jquery-ui-sliderAccess.js"></script>
<script type="text/javascript"
	src="script/jquery/jquery-ui-timepicker-zh-CN.js"></script>
<style type="text/css">
pre {
	font-size: 12px;
	line-height: 16px;
	padding: 5px 5px 5px 10px;
	margin: 10px 0;
	background-color: #e4f4d4;
	border-left: solid 5px #9EC45F;
	overflow: auto;
}
#divItem em{
	color:#000;
	font-style: normal;
}

.selectedItem{
	background:#ff0000;	
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
	//生成物品列表	
	function queryCharIdByName() {
		var name = $("#characterName").val();
		$.post("queryCharacterIdByName", "charName=" + name, function(data) {
			if (data == null) {
				return;
			}
			var dataObj = eval("(" + data + ")");
			$("#characterId").val(dataObj.charId);
		});
	}

	function onTimingMailOptionChange() {
		var timingChecked = $("#timingMailOptiontrue").attr("checked");
		if (timingChecked == "checked") {
			$("#divPlanSendTime").show();
		} else {
			$("#divPlanSendTime").hide();
		}
	}

	$(document).ready(function() {
		$("#btnQueryCharName").click(queryCharIdByName);		
		$("#characterName").keypress(function(e){
			   var curKey = e.which;
			   if(curKey == 13)
			   {
				   queryCharIdByName();
			   }
			   });
		$("#btnAddToReveiver").click(function(){
			var roleId = $("#characterId").val();
			if(roleId>0){
				var oldValue = $("#receiveHumanIds").val();
				if(oldValue != null && oldValue.length>0){
					if(oldValue.substring(oldValue.length-1,oldValue.length)==","){
						$("#receiveHumanIds").val(oldValue+=roleId);
					}else{
						$("#receiveHumanIds").val(oldValue+","+roleId);
					}
				}else{
					$("#receiveHumanIds").val(roleId);
				}
			}
		});
		
		onTimingMailOptionChange();
	});
	
	function submitForm(){
		var editFlag = $("#editState").val()=="true";
		var confirmText = "是否确定发送这封邮件？";
		if(editFlag){
			confirmText = "是否确定保存修改？";
		}
		if(confirm(confirmText)){
			var formMail = $("#formMail");
			if($("#editState").val()=="true"){
				formMail.attr("action","updateTimingMail");
			}else{
				formMail.attr("action","sendMail");
			}
			formMail[0].submit();
		}
	}
</script>
</head>
<body>
	<table width="1000">
		<tr>
			<th colspan="2">角色id查询工具</th>
		</tr>
		<tr>
			<td>角色名称：<input id="characterName" type="text" width="80" /> <input
				id="btnQueryCharName" type="button" value="查询" /></td>
			<td>角色id：<input id="characterId" type="text" width="200" /><input
				id="btnAddToReveiver" type="button" value="添加到收件人" /></td>
		</tr>
	</table>
	<br />
	<s:form id="formMail" action="sendMail" validate="true">
		<table width="1000">
			<tr>
				<th colspan="3">邮件内容</th>
			</tr>
			<tr>
				<td>收件人ID</td>
				<td colspan="2"><s:textarea id="receiveHumanIds" name="receiveHumanIds"
						theme="simple" style="width:400px;height:100px;"></s:textarea> <pre>1表示所有玩家，2表示所有在线玩家;多个收件人用逗号“,”分隔
					</pre>
					<s:hidden id="mailId" name="mailId"></s:hidden>	
					<s:hidden id="editState" name="editState"></s:hidden>	
					</td>
			</tr>
			<tr>
				<td>主题</td>
				<td colspan="2"><s:textfield name="mailTheme" theme="simple"
						style="width:400px;"></s:textfield></td>
			</tr>
			<tr>
				<td>内容</td>
				<td><s:textarea name="content" theme="simple"
						style="width:230px;height:300px;"></s:textarea></td>
				<td style="vertical-align:top;">
					<table width="100%">
						<tr>
							<td>物品1id</td>
							<td><s:textfield id="itemId" name="itemId" theme="simple"></s:textfield>
							数量<s:textfield id="itemNum" name="itemNum" theme="simple"></s:textfield></td>
						</tr>
						<tr>
							<td>物品2id</td>
							<td><s:textfield id="itemId2" name="itemId2" theme="simple"></s:textfield>
							数量<s:textfield id="itemNum2" name="itemNum2" theme="simple"></s:textfield></td>
						</tr>
						<tr>
							<td>是否有过期时间</td>
							<td><s:radio name="hasExpireDate"
									list="%{#{true:'是',false:'否'}}" theme="simple"></s:radio></td>
						</tr>
						<tr>
							<td>过期时间</td>
							<td><sx:datetimepicker name="expireDate"
									displayFormat="yyyy-MM-dd"></sx:datetimepicker></td>
						</tr>
						<tr>
							<td>备注</td>
							<td><s:textfield name="sendMemo" theme="simple"></s:textfield></td>
						</tr>
					</table>
				</td>
			</tr>

			<tr>
				<td>是否定时发送</td>
				<td colspan="2"><s:radio id="timingMailOption" name="timingMail"
						list="%{#{true:'是',false:'否'}}" theme="simple"
						onchange="onTimingMailOptionChange()"></s:radio> &nbsp;&nbsp;<span
					id="divPlanSendTime">					
					 计划发送时间：<s:textfield name="planSendTimeString" id="planSendTime" cssClass="ui_timepicker" theme="simple" />

				</span></td>
			</tr>
			<tr>
				<td colspan="3" style="text-align: center;">
				<s:if test="#request.editState == true" >
				<input type="button"
						value="保存" onclick="submitForm()" />
				</s:if>
				<s:else>
				<input type="button"
						value="发 送" onclick="submitForm()" />
				</s:else>
				</td>
			</tr>
		</table>
	</s:form>
<div id="divItemBox" style="position:absolute;top:0;left:1000px;width:500px;">
<div>分类：<select id="itemCate"></select></div>
<div id="divItem" >

</div>
</div>
<script type="text/javascript">


generateItemHtml();

//生成物品列表
function generateItemHtml(){
	$("#itemCate").change(onSelectCategoryChange);
	$.post("querySimpleItemCategories","",function(cateData){
		var dataObj = eval("(" + cateData + ")");
		var cateObj = $.parseJSON(dataObj.categories);
		fillCategoryData(cateObj);
		onSelectCategoryChange();
	});
	
}

function onSelectCategoryChange(){
	var cate = $("#itemCate").val();
	$.post("querySimpleConsumeItems","category="+cate,function(data){
		if(data == null){
			return;
		}
		var dataObj = eval("(" + data + ")");
		var jsonObj = $.parseJSON(dataObj.items);
		createItemTable(jsonObj);
	});
}

function fillCategoryData(categories){
	var sltCate = $("#itemCate");
	for(var i=0;i<categories.length;i++){
		if(categories[i]==null || categories[i]==""){
			continue;
		}
		sltCate.append("<option value='"+categories[i]+"'>"+categories[i]+"</option>");
	}	
}

function createItemTable(items){
	if(items == undefined || items == null){
		return;
	}
	var tableHtml="<table width='500'><tr><th>操作</th><th>物品id</th><th>物品名称</th><th>物品描述</th></tr>";	
	for(var i=0;i<items.length;i++){
		tableHtml+="<tr><td width='150'><input id='setAsItem_"+i+"' name='setAsItem' type='button' value='物品1' /><input id='setAsItem2_"+i+"' name='setAsItem2' type='button' value='物品2' /></td>"
		tableHtml+="<td><em id='itemId_"+i+"'>";
		tableHtml+=items[i].itemId;
		tableHtml+="</em></td><td><em id='itemName_"+i+"''>";
		tableHtml+=items[i].itemName;
		tableHtml+="</em></td><td><em id='itemDesc_"+i+"'>";
		tableHtml+=items[i].itemDesc;
		tableHtml+="</em></td></tr>";
	}
	tableHtml+="</table>";
	$("#divItem").html(tableHtml);	
	$("input[name='setAsItem']").click(function(){
		var id = $(this).attr("id");
		var identity = id.split("_")[1];
		var emItemId = $("#itemId_"+identity);		
		var itemId2 = $("#itemId2").val();
		var currentItemId = emItemId.html();
		$(".selectedItem").each(function(index){
			var selectedItemId = $(this).html();
			if( selectedItemId != itemId2 && selectedItemId != currentItemId ){
				$(this).removeClass("selectedItem");
			}
		});
		emItemId.addClass("selectedItem");		
		$("#itemId").val(emItemId.html());
	});
	$("input[name='setAsItem2']").click(function(){
		var id = $(this).attr("id");
		var identity = id.split("_")[1];
		var emItem2Id = $("#itemId_"+identity);		
		var itemId = $("#itemId").val();
		var currentItemId = emItem2Id.html();
		$(".selectedItem").each(function(index){
			var selectedItemId = $(this).html();
			if( selectedItemId != itemId && selectedItemId != currentItemId ){
				$(this).removeClass("selectedItem");
			}
		});
		emItem2Id.addClass("selectedItem");	
		$("#itemId2").val($("#itemId_"+identity).html());
	});
}

</script>
</body>
</html>