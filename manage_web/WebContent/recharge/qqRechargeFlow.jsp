<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Date"%>
<%@page import="java.text.DateFormat"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="com.hifun.soul.proto.services.Services.QQRechargeFlow"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sx" uri="/struts-dojo-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>qq充值业务流水列表</title>
<sx:head />
<link rel="stylesheet" type="text/css" href="style/main/default.css" />
<link rel="stylesheet" type="text/css" href="style/main/style.css" />
<link rel="stylesheet" type="text/css" href="style/main/table.css" />
<script type="text/javascript" src="script/common/paging.js"></script>
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
			<tr>
			<td>序号</td>
			<td>id</td>
			<td>流水号</td>
			<td>充值角色id</td>
			<td>openId</td>
			<td>购买的物品</td>
			<td>交易token</td>
			<td>扣款金额(0.1Q点为单位)</td>
			<td>扣取的游戏币总数，单位为Q点</td>
			<td>扣取的抵用券总金额，单位为Q点</td>
			<td>发送状态</td>
			<td>jsonValue</td>					
			<td>交易时间</td>
			</tr>
			<%
				Object rechargeFlowObject = request.getAttribute("qqRechargeFlowList");
					if (rechargeFlowObject != null ) {
						@SuppressWarnings("unchecked")
						List<QQRechargeFlow> rechargeFlows = (List<QQRechargeFlow>) rechargeFlowObject;
						DateFormat dfDateTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						int index = 1;
						for (QQRechargeFlow flow : rechargeFlows) {
			%>
			<tr>
				<td><%=index++ %></td>
				<td><%=flow.getId() %></td>
				<td><%=flow.getBillno() %></td>
				<td><%=flow.getHumanId() %></td>
				<td><%=flow.getOpenId() %></td>
				<td><%=flow.getPayItem() %></td>
				<td><%=flow.getToken() %></td>
				<td><%=flow.getAmt() %></td>
				<td><%=flow.getPayamtCoins() %></td>
				<td><%=flow.getPubacctPayamtCoins() %></td>
				<td><%=flow.getSended() %></td>
				<td><%=flow.getJsonValue()%></td>
				<td><%=dfDateTime.format(new Date(Long.parseLong(flow.getTimeStamp()))) %></td>				
			</tr>
			<%
						}
					}
			%>
			<tr>
				<td colspan="4">
				记录总数[<s:property value="pagingInfo.totalCount" />] 
				</td>
				<td colspan="4">共[<s:property
						value="pagingInfo.totalCount/pagingInfo.pageSize+1" />]页
						&nbsp;&nbsp;
						每页[<s:property value="pagingInfo.pageSize" />]条记录
				</td>
				<td colspan="5" align="center">
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