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
<script language="javascript">
	function check() {		
		var password = form1.newPassword.value;		
		if (password.length < 6) {
			alert("密码不能少于6位");
			return false;
		}
		var passwordConfirm = form1.newPasswordConfirm.value;
		if(password != passwordConfirm){
			alert("两次输入的密码不一致");
			return false;
		}
		return true;
	}
</script>
</head>
<body>
<s:form name="form1" action="updateUserPassword.action" validate="true">
		<table>
			<tr>
				<td>用户ID</td>
				<td><s:property value="userId"></s:property></td>
			</tr>
			<tr>
				<td>用户名称</td>
				<td><s:property value="userName"></s:property></td>
			</tr>
			<tr>
				<td>旧密码</td>
				<td>
				<s:password name="oldPassword" theme="simple"></s:password>
				</td>
			</tr>
			<tr>
				<td>新密码</td>
				<td>				
				<s:password name="newPassword" theme="simple"></s:password></td>
			</tr>
			<tr>
				<td>确认新密码</td>
				<td><s:password name="newPasswordConfirm" theme="simple"></s:password></td>
			</tr>
			<tr>
				<td colspan="2"><input type="submit" onclick="return check()" value="提交" /></td>
			</tr>
		</table>
	</s:form>
</body>
</html>