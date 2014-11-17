<%@ page language="java" import="java.util.*,com.hifun.soul.proto.data.entity.Entity.Account, com.hifun.soul.core.enums.AccountState" pageEncoding="UTF-8"%>
<%@ page import="com.hifun.soul.gamedb.PlayerPermissionType" %>
<%
	int currentPage = 1;
	if (request.getAttribute("currentPage") != null) {
		currentPage = (Integer) request.getAttribute("currentPage");
	}
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<link rel="stylesheet" type="text/css" href="style/main/default.css" />
<link rel="stylesheet" type="text/css" href="style/main/style.css" />
<link rel="stylesheet" type="text/css" href="style/main/table.css" />
<script type="text/javascript" src="script/common/paging.js"></script>
<style type="text/css">
#name {
	visibility: hidden;
}
</style>
<script type="text/javascript">
	// 写进行某项操作比如发邮件之前的验证;1.是否有角色选中
	function doOperate() {
		var checked = false;
		var opChecked = false;
		for ( var i = 0; i < form1.guid.length; i++) {
			if (form1.guid[i].checked == true) {
				checked = true;
				break;
			}
		}
		if (!checked) {
			alert("请您选中至少一个角色再进行操作,谢谢!");
			return false;
		}
		for ( var j = 0; j < form1.operationId.length; j++) {
			if (form1.operationId[j].checked == true) {
				opChecked = true;
				break;
			}
		}
		if (!opChecked) {
			alert("请您先选中操作类型,然后再进行操作的执行,谢谢!");
			return false;
		}
		return true;
	}

	function clickGuid() {
		for ( var i = 0; i < form1.guid.length; i++) {
			if (form1.guid[i].checked == true) {
				form1.name[i].checked = true;
			} else {
				form1.name[i].checked = false;
			}
		}
	}
	//全选
	function selectAll() {
		if (form1.oprateAll.checked == true) {
			for ( var i = 0; i < form1.guid.length; i++) {
				form1.guid[i].checked = true;
				form1.name[i].checked = true;

			}
		} else {
			for ( var j = 0; j < form1.guid.length; j++) {
				form1.guid[j].checked = false;
				form1.name[j].checked = false;

			}
		}
	}
</script>
<title>用户列表</title>

</head>

<body>
	<%
		@SuppressWarnings("unchecked")
		List<Account> accountList = (List<Account>) request
				.getAttribute("accountList");
	%>
	<form name="form1">
		<table border="0">
			<tr>
				<th>账号ID</th>
				<th>账号名</th>
				<th>账号权限</th>
				<th>账号状态</th>
				<th>操作</th>
			</tr>
			<%
				for (Account account : accountList) {
			%>
			<tr>
				<td><%=account.getPassportId()%></td>
				<td><%=account.getUserName()%></td>
				<td><%=(account.getPermissionType() == PlayerPermissionType.NORMAL_PLAYER.getIndex()) ? "普通用户"
						: "GM用户" %></td>
				<td><%=(account.getState() == AccountState.NORMAL.getIndex()) ? "正常"
						: "<font color='gray'><b>已锁定</b></font>"%></td>
				<td><a
					href="<%if (account.getState() == AccountState.NORMAL.getIndex()) {
					out.println("lockAccountForward.action?pid="
							+ account.getPassportId());
				} else {
					out.println("unlockAccount.action?pid="
							+ account.getPassportId());
				}%>"
					onclick=" return confirm('确定要执行此操作么?')" ><%=(account.getState() == AccountState.NORMAL.getIndex()) ? "封锁此号"
						: "解锁此号"%></a> &nbsp;&nbsp;
						<a href="queryAccount.action?pid=<%=account.getPassportId()%>&update=1">修改权限</a>
				</td>
			</tr>
			<%
				}
			%>
			<tr><td colspan="2">
				记录总数[<%=request.getAttribute("totalCount")%>] 
				</td>
				<td colspan="1">共[<%=(((Long)request.getAttribute("totalCount"))/21+1)%>]页
						&nbsp;&nbsp;
						每页[20]条记录
				</td>
				<td colspan="2" align="center">
					<a
					href="queryAccount.action?page=<%=(currentPage - 1) == 0 ? 1 : (currentPage - 1)%>">上一页</a>
				第<%=currentPage%>页
				<a
					href="queryAccount.action?page=<%=(accountList.size() > 0) ? (currentPage + 1)
					: currentPage %>">下一页</a>
				</td>
			</tr>
		</table>		
	</form>


</body>
</html>
