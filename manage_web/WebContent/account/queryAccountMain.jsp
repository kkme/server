<%@page contentType="text/html" pageEncoding="UTF-8"%>
<html>
<head>
<link rel="stylesheet" type="text/css" href="style/main/default.css" />
<link rel="stylesheet" type="text/css" href="style/main/style.css" />

<link rel="stylesheet" type="text/css" href="style/main/table.css" />	
<style type="text/css">
#accountName {
	visibility:hidden;
}
#accountId {
	visibility:hidden;
}
#search {
	visibility:hidden;
}
</style>
<script type="text/javascript">
//根据id隐藏指定元素
function hiddenTarget(id) {
	var target = document.getElementById(id);
	target.style.display = "none";
	if (target.style.visibility = "visible") {
		target.style.visibility = "hidden"
	}
}

// 显示指定的元素
function showTarget(id) {
	var target = document.getElementById(id);
	if (target.style.display = "none") {
		target.style.display = "";
	}
	if (target.style.visibility = "hidden") {
		target.style.visibility = "visible"
	}
}
function checkQueryType() {
	if (form1.queryType[0].checked == true) {
		hiddenTarget("accountId");
		showTarget("accountName");
	} else if (form1.queryType[1].checked == true) {
		hiddenTarget("accountName");
		showTarget("accountId");
	} 
	showTarget("search");
}
</script>
<title>JSP Page</title>
</head>
<body>
<form action="queryAccount.action" method="post" name = "form1">
<table>
	<tr>
		<th colspan="4">账号信息查询</th>
	</tr>
	<tr>
		<td>查询方式</td>
		<td><input type="radio" name="queryType" onClick ="checkQueryType()" />根据账号名称查询</td>
		<td><input type="radio" name="queryType" onClick ="checkQueryType()"/>根据账号ID查询</td>
	</tr>
	<tr id = "accountName">
		<td>账号名称</td>
		<td><input type="text" name="name" /></td>
	</tr>
	<tr id = "accountId">
		<td>账号ID</td>
		<td><input type="text" name="pid" /></td>
	</tr>
	<tr id = "search">
		<td colspan="2"><input type="submit" value="查找" /></td>
	</tr>
</table>
</form>
</body>
</html>
