<%@page import="org.apache.struts2.ServletActionContext"%>
<%@page import="com.opensymphony.xwork2.ActionContext"%>
<%@page import="com.hifun.soul.proto.services.Services.User"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">
<title>管理首页</title>
<link rel="stylesheet" type="text/css" href="style/main/default.css" />
<link rel="stylesheet" type="text/css" href="style/main/style.css" />
<link rel="stylesheet" type="text/css" href="style/main/main.css" />
<script type="text/javascript" src="script/jquery/jquery-1.8.1.min.js"></script>
<script type="text/javascript" language="javascript">
	$(document).ready(function(){
		$(".title").each(function(){
			$(this.nextSibling.nextSibling).hide();
			$(this).click(function(){
				$(this.nextSibling.nextSibling).toggle();
			});
		});		
	});
</script>
</head>

<body>
	<%
		User user = (User) ServletActionContext.getRequest().getSession()
				.getAttribute("user");
	%>
	<div id="container">
		<div id="header">
			<p>
				当前用户:<%=user.getUserName()%>

				| <a href="logout.action">注销登陆</a>
			</p>
		</div>
		<div id="content">
			<div id="left">
				<div id="menu">
					<p class="title">账号管理</p>
					<ul>
						<li><a href="queryAccountForward.action" target="content">账号信息查询</a></li>
					</ul>
					<p class="title">角色管理</p>
					<ul>
						<li><a href="queryCharacterListForward.action" target="content">角色信息查询</a></li>
						<li><a href="queryPlayerStateStatistic.action" target="content">玩家状态统计</a></li>
						<li><a href="queryCharacterDistribute.action" target="content">角色分布统计</a></li>
					</ul>
					<p class="title">后台系统公告</p>
					<ul>
						<li><a href="queryBulletinList.action" target="content">系统公告列表</a></li>
					</ul>
					<p class="title">邮件管理</p>
					<ul>
						<li><a href="querySendMailList.action" target="content">邮件列表</a></li>
						<li><a href="queryTimingMailList.action" target="content">定时邮件列表</a></li>
						<li><a href="sendMailForward.action" target="content">发送邮件</a></li>
					</ul>
					<p class="title">玩家反馈管理</p>
					<ul>
						<li><a href="queryQuestionList.action" target="content">玩家反馈列表</a></li>
					</ul>
					<p class="title">充值管理</p>
					<ul>
						<li><a href="queryRechargeFlowForward.action" target="content">充值流水查询</a></li>
					</ul>
					<p class="title">日志管理</p>
					<ul>
						<li><a href="queryBasicPlayerLogList.action" target="content">角色登录日志</a></li>
						<li><a href="queryBattleLogList.action" target="content">战斗日志</a></li>
						<li><a href="queryChatLogList.action" target="content">聊天日志</a></li>
						<li><a href="queryFriendLogList.action" target="content">好友日志</a></li>
						<li><a href="queryGMCommandLogList.action" target="content">GM命令日志</a></li>
						<li><a href="queryHoroscopeLogList.action" target="content">占星日志</a></li>
						<li><a href="queryItemLogList.action" target="content">物品日志</a></li>
						<li><a href="queryMoneyLogList.action" target="content">货币系统日志</a></li>
						<li><a href="queryOnlineTimeLogList.action" target="content">每日在线统计日志</a></li>
						<li><a href="queryPropertyLogList.action" target="content">角色属性日志</a></li>
						<li><a href="queryQuestLogList.action" target="content">任务日志</a></li>
						<li><a href="queryRechargeLogList.action" target="content">充值日志</a></li>
						<li><a href="queryEnergyLogList.action" target="content">体力日志</a></li>
						<li><a href="queryHonourLogList.action" target="content">荣誉日志</a></li>
						<li><a href="queryExperienceLogList.action" target="content">经验日志</a></li>
						<li><a href="queryTechPointLogList.action" target="content">科技点日志</a></li>
						<li><a href="querySkillPointLogList.action" target="content">技能点日志</a></li>
						<li><a href="queryOperationLogList.action" target="content">gm后台操作日志</a></li>
					</ul>
					
					<p class="title">运营系统管理</p>
					<ul>			
						<li><a href="queryAllMarketActivitySettings.action"
							target="content">运营系统状态</a></li>
					</ul>
					
					<p class="title">运行时管理</p>
					<ul>
						<li><a href="queryManageServerList.action"
							target="content">管理服务器列表</a></li>		
						<li><a href="queryGameServerList.action"
							target="content">游戏服务器列表</a></li>	
						<li><a href="queryLoginWallState.action"
							target="content">登陆墙状态</a></li>		
						<li><a href="getRealmInfo.action"
							target="content">服务器组信息</a></li>
						<li><a href="queryDBEntityOperationInfo"
							target="content">数据库操作实时信息查询</a></li>
						
					</ul>

					<p class="title">后台用户管理</p>
					<ul>
						<li><a href="getUserList.action" target="content">用户列表</a></li>
						<li><a href="updateUserPasswordForward.action" target="content">修改密码</a></li>
					</ul>

					
				</div>
			</div>
			<div id="right">
				<iframe frameborder="0" name="content" src="user/userInfo.jsp"
					width="100%" height="98%" scrolling="auto" align="left"></iframe>
			</div>
		</div>
		<div id="footer"></div>
	</div>
</body>
</html>
