<%@page import="org.apache.struts2.ServletActionContext"%>
<%@page import="com.hifun.soul.proto.data.entity.Entity.*"%>
<%@page import="com.hifun.soul.proto.common.Character.CharProperty"%>
<%@page import="com.hifun.soul.proto.common.Quests.QuestAimCounterData"%>
<%@page import="com.hifun.soul.proto.common.Quests.DailyQuestRewardBox"%>
<%@page import="com.hifun.soul.proto.common.Stargazers.Stargazer"%>
<%@page import="com.hifun.soul.proto.common.Horoscopes.Horoscope"%>
<%@page import="com.hifun.soul.proto.common.Technologys.Technology"%>
<%@page import="com.hifun.soul.proto.common.Stages.StageMapStateData"%>
<%@page import="com.hifun.soul.proto.data.entity.Entity.HumanGift"%>
<%@page import="com.hifun.soul.proto.common.Warrior.HumanWarriorInfo"%>
<%@page import="com.hifun.soul.proto.common.Refine.RefineMapData"%>
<%@page import="com.hifun.soul.proto.common.Refine.RefineStageData"%>
<%@page
	import="com.hifun.soul.proto.common.EliteStage.HumanEliteStageInfo"%>
<%@page import="com.hifun.soul.proto.common.EliteStage.EliteStageState"%>
<%@page import="com.hifun.soul.proto.common.Skills.CarriedSkill"%>
<%@page
	import="com.hifun.soul.proto.common.CostNotifyDatas.CostNotifyData"%>
<%@page import="com.hifun.soul.proto.common.Rewards.LoginRewardData"%>
<%@page import="com.hifun.soul.proto.common.Mine.HumanMineInfo"%>
<%@page import="com.hifun.soul.proto.common.Mine.MineField"%>
<%@page import="com.hifun.soul.gameserver.role.properties.PropertyType"%>
<%@page
	import="com.hifun.soul.gameserver.role.properties.HumanIntProperty"%>
<%@page
	import="com.hifun.soul.gameserver.role.properties.Level1Property"%>
<%@page
	import="com.hifun.soul.gameserver.role.properties.Level2Property"%>
<%@page import="com.hifun.soul.gameserver.building.BuildingType"%>
<%@page import="com.hifun.soul.gameserver.item.Item"%>
<%@page import="com.hifun.soul.gameserver.item.ConsumeItem"%>
<%@page import="com.hifun.soul.gameserver.item.EquipItem"%>
<%@page import="com.hifun.soul.gameserver.item.MaterialItem"%>
<%@page import="com.hifun.soul.gameserver.item.ItemDetailType"%>
<%@page import="com.hifun.soul.gameserver.item.feature.*"%>
<%@page import="com.hifun.soul.gameserver.item.assist.GemItemInfo"%>
<%@page import="com.hifun.soul.core.util.KeyValuePair"%>
<%@page import="com.hifun.soul.manageweb.template.manager.TemplateManager"%>
<%@page
	import="com.hifun.soul.gameserver.human.quest.template.QuestTemplate"%>
<%@page
	import="com.hifun.soul.gameserver.gift.template.GiftTemplate"%>
<%@page import="com.hifun.soul.gameserver.horoscope.msg.HoroscopeInfo"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Date"%>
<%@page import="java.text.DateFormat"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="com.hifun.soul.manageweb.character.CharacterHelper"%>
<%@page import="com.hifun.soul.proto.services.Services.HumanFriendInfo"%>
<%@page
	import="com.hifun.soul.proto.services.Services.HumanQuestionData"%>

<%@page language="java" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<link rel="stylesheet" type="text/css" href="style/main/default.css" />
<link rel="stylesheet" type="text/css" href="style/main/style.css" />
<link rel="stylesheet" type="text/css" href="style/main/table.css" />
<script type="text/javascript" src="script/jquery/jquery-1.8.1.min.js"></script>
<script type="text/javascript" src="script/common/paging.js"></script>
<style type="text/css">
.divProperty {
	margin: 0px 5px 0px 5px;
	float: left;
}

.tableProperty {
	width: 300px;
}

.tableProperty td {
	witdh: 150px;
}
</style>
<script type="text/javascript">
	function checkOnlineState(humanGuid) {
		$.post("checkOnlineState", "id=" + humanGuid, function(data) {
			if (data == null) {
				return;
			}
			var dataObj = eval("(" + data + ")");
			var spanId = "#spanOnlineState_" + humanGuid;
			$(spanId).text(dataObj.isOnline);
		});
	}
	function queryOnlineNum() {
		$.post("queryCurrentOnlineNum", "", function(data) {
			if (data == null) {
				$("#spanOnlineNum").text("查询失败!");
				return;
			}
			var dataObj = eval("(" + data + ")");
			$("#spanOnlineNum").text(dataObj.onlineNum);
		});
	}
</script>
</head>
<body>
	<%
		@SuppressWarnings("unchecked")
		List<Human> characterList = (List<Human>) request.getAttribute("characterList");
		DateFormat dfDateTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	%>
	<s:form action="queryCharacterList">
		<table>
			<tr>
				<td colspan="17">角色id： <s:textfield
						name="queryCondition.queryId" theme="simple" /> &nbsp;&nbsp;角色名称：<s:textfield
						name="queryCondition.queryName" theme="simple" />
					&nbsp;&nbsp;账户名称：<s:textfield name="queryCondition.accountName"
						theme="simple" /> <s:submit value="查询" theme="simple" />
				</td>
				<td colspan="2">在线人数:<span id="spanOnlineNum"></span>&nbsp;&nbsp;<input
					type="button" value="刷新" onclick="queryOnlineNum()" />
				</td>
			</tr>
			<tr class="title">
				<th>序号</th>
				<th>角色ID</th>
				<th>角色名称</th>
				<th>角色等级</th>
				<th>职业</th>
				<th>技能</th>
				<th>物品</th>
				<th>好友</th>
				<th>任务</th>
				<th>星运</th>
				<th>科技</th>
				<th>关卡</th>
				<th>矿场</th>
				<th>奇遇答题</th>
				<th>主城收集</th>
				<th>连续登陆奖励</th>
				<th>消费提醒</th>
				<th>天赋</th>
				<th>勇者之路</th>
				<th>试炼挑战</th>
				<th>在线状态</th>
				<th>操作</th>
			</tr>
			<s:iterator value="characters" var="character" status="status">
				<tr>
					<td><s:property value="#status.index+1" /></td>
					<td><s:property value="#character.baseProperties.humanGuid" />
						<s:hidden id="humanGuid"
							name="#character.baseProperties.humanGuid"></s:hidden></td>
					<td><s:a
							href="showCharacterPropertyInfo.action?id=%{#character.baseProperties.humanGuid}">
							<s:property value="#character.baseProperties.name" />
						</s:a></td>
					<td><s:property value="#character.baseProperties.level" /></td>
					<td><s:if test="#character.baseProperties.occupation==1">
					战士
					</s:if> <s:elseif test="#character.baseProperties.occupation==2">
					游侠
				</s:elseif> <s:elseif test="#character.baseProperties.occupation==4">
					法师
				</s:elseif></td>
					<td><s:a
							href="showCharacterSkillInfo.action?id=%{#character.baseProperties.humanGuid}">查看</s:a></td>
					<td><s:a
							href="showCharacterItemInfo.action?id=%{#character.baseProperties.humanGuid}">查看</s:a></td>
					<td><s:a
							href="showCharacterFriendInfo.action?id=%{#character.baseProperties.humanGuid}">查看</s:a></td>
					<td><s:a
							href="showCharacterQuestInfo.action?id=%{#character.baseProperties.humanGuid}">查看</s:a></td>
					<td><s:a
							href="showCharacterHoroscopeInfo.action?id=%{#character.baseProperties.humanGuid}">查看</s:a></td>
					<td><s:a
							href="showCharacterTechnologyInfo.action?id=%{#character.baseProperties.humanGuid}">查看</s:a></td>
					<td><s:a
							href="showStageInfo.action?id=%{#character.baseProperties.humanGuid}">查看</s:a></td>
					<td><s:a
							href="showCharacterMineInfo.action?id=%{#character.baseProperties.humanGuid}">查看</s:a></td>
					<td><s:a
							href="showCharacterQuestionInfo.action?id=%{#character.baseProperties.humanGuid}">查看</s:a></td>
					<td><s:a
							href="showStoneCollectInfo.action?id=%{#character.baseProperties.humanGuid}">查看</s:a></td>
					<td><s:a
							href="showLoginRewardInfo.action?id=%{#character.baseProperties.humanGuid}">查看</s:a></td>
					<td><s:a
							href="showCostnotifyInfo.action?id=%{#character.baseProperties.humanGuid}">查看</s:a></td>
					<td><s:a
							href="showGiftInfo.action?id=%{#character.baseProperties.humanGuid}">查看</s:a></td>
					<td><s:a
							href="showWarriorInfo.action?id=%{#character.baseProperties.humanGuid}">查看</s:a></td>
					<td><s:a
							href="showRefineInfo.action?id=%{#character.baseProperties.humanGuid}">查看</s:a></td>
					<td><span
						id="spanOnlineState_<s:property value="#character.baseProperties.humanGuid" />">unknown</span>
						<input type="button" value="刷新"
						onclick="checkOnlineState('<s:property value="#character.baseProperties.humanGuid" />')" />
					</td>
					<td>
					<s:url id="sendMailUrl" action="sendMailForward">
							<s:param name="receiveHumanIds"
								value="#character.baseProperties.humanGuid"></s:param>
						</s:url> <s:a
							href="kickOffCharacter.action?id=%{#character.baseProperties.humanGuid}"
							onclick="return confirm('确认将 %{#character.baseProperties.name} 踢下线吗？');">踢人</s:a>&nbsp;&nbsp;
						<s:a href="sendMailForward.action?receiverId=%{#character.baseProperties.humanGuid}">发送邮件</s:a>&nbsp;&nbsp;
					</td>
				</tr>

			</s:iterator>
			<tr>
				<td colspan="7">记录总数[<s:property
						value="queryCondition.pagingInfo.totalCount" />]
				</td>
				<td colspan="8" align="center"><a href="#"
					onclick="gotoPrePage()">上一页</a> &nbsp;&nbsp;[<s:property
						value="queryCondition.pagingInfo.pageIndex+1" />] &nbsp;&nbsp; <a
					href="#" onclick="gotoNextPage()">下一页</a> <s:hidden
						id="currentPage" name="queryCondition.pagingInfo.pageIndex"></s:hidden>
					<s:hidden id="totalPage"
						name="queryCondition.pagingInfo.totalCount/queryCondition.pagingInfo.pageSize"></s:hidden>
				</td>
				<td colspan="7">共[<s:property
						value="queryCondition.pagingInfo.totalCount/queryCondition.pagingInfo.pageSize+1" />]页
					&nbsp;&nbsp; 每页[<s:property
						value="queryCondition.pagingInfo.pageSize" />]条记录
				</td>
			</tr>
		</table>
		<br />
		<br />

		<div id="divCharacterInfo">
			<div id="divProperty">
				<%
					Object objBaseProperties = request.getAttribute("baseProperties");
						if (objBaseProperties != null) {
							HumanBaseProperties baseProperties = (HumanBaseProperties) objBaseProperties;
							Map<Integer, Integer> properties = (Map<Integer, Integer>) request.getAttribute("otherProperties");
				%>
				<div id="divProperty1" class="divProperty">
					<table class="tableProperty">
						<tr>
							<th colspan="2">角色整形属性</th>
						</tr>
						<tr>
							<td>账号id</td>
							<td><%=baseProperties.getPassportId()%></td>
						</tr>
						<tr>
							<td>角色名称</td>
							<td><%=baseProperties.getName()%></td>
						</tr>
						<tr>
							<td>角色等级</td>
							<td><%=baseProperties.getLevel()%></td>
						</tr>
						<tr>
							<td>职业</td>
							<td>
								<%
									String occupation = "";
											if (baseProperties.getOccupation() == 1) {
												occupation = "战士";
											} else if (baseProperties.getOccupation() == 2) {
												occupation = "游侠";
											} else if (baseProperties.getOccupation() == 4) {
												occupation = "法师";
											}
								%> <%=occupation%>
							</td>
						</tr>
						<tr>
							<td>主城等级</td>
							<td><%=baseProperties.getHomeLevel()%></td>
						</tr>
						<tr>
							<td>当前精力值</td>
							<td><%=baseProperties.getEnergy()%></td>
						</tr>
						<tr>
							<td>最大精力值</td>
							<td><%=properties.get(PropertyType.genPropertyKey(HumanIntProperty.MAX_ENERGY,
							PropertyType.HUMAN_INT_PROPERTY))%></td>
						</tr>

						<tr>
							<td>VIP等级</td>
							<td><%=properties.get(PropertyType.genPropertyKey(HumanIntProperty.VIPLEVEL,
							PropertyType.HUMAN_INT_PROPERTY))%></td>
						</tr>
						<tr>
							<td>魔晶数量</td>
							<td><%=properties.get(PropertyType.genPropertyKey(HumanIntProperty.CRYSTAL,
							PropertyType.HUMAN_INT_PROPERTY))%></td>
						</tr>
						<tr>
							<td>金币数量</td>
							<td><%=baseProperties.getCoins()%></td>
						</tr>
						<tr>
							<td>当前经验值</td>
							<td><%=properties.get(PropertyType.genPropertyKey(HumanIntProperty.EXPERIENCE,
							PropertyType.HUMAN_INT_PROPERTY))%></td>
						</tr>
						<tr>
							<td>最大经验值</td>
							<td><%=properties.get(PropertyType.genPropertyKey(HumanIntProperty.MAX_EXPERIENCE,
							PropertyType.HUMAN_INT_PROPERTY))%></td>
						</tr>
						<tr>
							<td>背包大小</td>
							<td><%=properties.get(PropertyType.genPropertyKey(HumanIntProperty.BAG_SIZE,
							PropertyType.HUMAN_INT_PROPERTY))%></td>
						</tr>
						<tr>
							<td>当日收税次数</td>
							<td><%=properties.get(PropertyType.genPropertyKey(HumanIntProperty.LEVY_REMAIN_TIMES,
							PropertyType.HUMAN_INT_PROPERTY))%></td>
						</tr>
						<tr>
							<td>当日收获宝石次数</td>
							<td><%=properties.get(PropertyType.genPropertyKey(HumanIntProperty.HARVEST_GEM_REMAIN_TIMES,
							PropertyType.HUMAN_INT_PROPERTY))%></td>
						</tr>
						<tr>
							<td>系统分配的火焰点数</td>
							<td><%=properties.get(PropertyType.genPropertyKey(HumanIntProperty.SYSTEM_FIRE,
							PropertyType.HUMAN_INT_PROPERTY))%></td>
						</tr>
						<tr>
							<td>系统分配的寒冰点数</td>
							<td><%=properties.get(PropertyType.genPropertyKey(HumanIntProperty.SYSTEM_ICE,
							PropertyType.HUMAN_INT_PROPERTY))%></td>
						</tr>
						<tr>
							<td>系统分配的光明点数</td>
							<td><%=properties.get(PropertyType.genPropertyKey(HumanIntProperty.SYSTEM_LIGHT,
							PropertyType.HUMAN_INT_PROPERTY))%></td>
						</tr>
						<tr>
							<td>系统分配的暗影点数</td>
							<td><%=properties.get(PropertyType.genPropertyKey(HumanIntProperty.SYSTEM_SHADOW,
							PropertyType.HUMAN_INT_PROPERTY))%></td>
						</tr>
						<tr>
							<td>系统分配的自然点数</td>
							<td><%=properties.get(PropertyType.genPropertyKey(HumanIntProperty.SYSTEM_NATURE,
							PropertyType.HUMAN_INT_PROPERTY))%></td>
						</tr>
						<tr>
							<td>培养的武力点数</td>
							<td><%=properties.get(PropertyType.genPropertyKey(HumanIntProperty.FOSTER_FORCE,
							PropertyType.HUMAN_INT_PROPERTY))%></td>
						</tr>
						<tr>
							<td>培养的敏捷点数</td>
							<td><%=properties.get(PropertyType.genPropertyKey(HumanIntProperty.FOSTER_AGILE,
							PropertyType.HUMAN_INT_PROPERTY))%></td>
						</tr>
						<tr>
							<td>培养的体力点数</td>
							<td><%=properties.get(PropertyType.genPropertyKey(HumanIntProperty.FOSTER_STAMINA,
							PropertyType.HUMAN_INT_PROPERTY))%></td>
						</tr>
						<tr>
							<td>培养的智力点数</td>
							<td><%=properties.get(PropertyType.genPropertyKey(HumanIntProperty.FOSTER_INTELLIGENCE,
							PropertyType.HUMAN_INT_PROPERTY))%></td>
						</tr>
						<tr>
							<td>培养的精神点数</td>
							<td><%=properties.get(PropertyType.genPropertyKey(HumanIntProperty.FOSTER_SPIRIT,
							PropertyType.HUMAN_INT_PROPERTY))%></td>
						</tr>
						<tr>
							<td>剩余科技点数</td>
							<td><%=properties.get(PropertyType.genPropertyKey(HumanIntProperty.TECHNOLOGY_POINT,
							PropertyType.HUMAN_INT_PROPERTY))%></td>
						</tr>
						<tr>
							<td>剩余普通训练时长(分钟)</td>
							<td><%=properties.get(PropertyType.genPropertyKey(HumanIntProperty.NORMAL_TRAINING_TIME,
							PropertyType.HUMAN_INT_PROPERTY))%></td>
						</tr>
						<tr>
							<td>vip训练剩余魔晶消费数</td>
							<td><%=properties.get(PropertyType.genPropertyKey(
							HumanIntProperty.VIP_TRAINING_CRYSTAL_CONSUME_NUM, PropertyType.HUMAN_INT_PROPERTY))%></td>
						</tr>
						<tr>
							<td>正在训练的训练类型</td>
							<td><%=properties.get(PropertyType.genPropertyKey(HumanIntProperty.CURRENT_TRAINING_TYPE,
							PropertyType.HUMAN_INT_PROPERTY))%></td>
						</tr>
						<tr>
							<td>占星次数</td>
							<td><%=properties.get(PropertyType.genPropertyKey(HumanIntProperty.ASTROLOGICAL_TIME,
							PropertyType.HUMAN_INT_PROPERTY))%></td>
						</tr>
						<tr>
							<td>魔晶兑换次数</td>
							<td><%=properties.get(PropertyType.genPropertyKey(HumanIntProperty.CRYSTAL_EXCHANGE_TIME,
							PropertyType.HUMAN_INT_PROPERTY))%></td>
						</tr>
						<tr>
							<td>当前日常任务积分</td>
							<td><%=properties.get(PropertyType.genPropertyKey(HumanIntProperty.DAILY_SCORE,
							PropertyType.HUMAN_INT_PROPERTY))%></td>
						</tr>
						<tr>
							<td>在线奖励领取次数</td>
							<td><%=properties.get(PropertyType.genPropertyKey(HumanIntProperty.ONLINE_REWARD_TIME,
							PropertyType.HUMAN_INT_PROPERTY))%></td>
						</tr>
						<tr>
							<td>大转盘每日领奖次数</td>
							<td><%=properties.get(PropertyType.genPropertyKey(HumanIntProperty.TURNTABLE_TIME,
							PropertyType.HUMAN_INT_PROPERTY))%></td>
						</tr>
						<tr>
							<td>下一个关卡ID</td>
							<td><%=properties.get(PropertyType.genPropertyKey(HumanIntProperty.NEXT_STAGE_ID,
							PropertyType.HUMAN_INT_PROPERTY))%></td>
						</tr>
						<tr>
							<td>充值获得魔晶数</td>
							<td><%=properties.get(PropertyType.genPropertyKey(HumanIntProperty.RECHARGED_NUM,
							PropertyType.HUMAN_INT_PROPERTY))%></td>
						</tr>
						<tr>
							<td>未分配的属性点数</td>
							<td><%=properties.get(PropertyType.genPropertyKey(HumanIntProperty.UNDISTRIBUTED_POINT,
							PropertyType.HUMAN_INT_PROPERTY))%></td>
						</tr>
						<tr>
							<td>连续登陆天数</td>
							<td><%=properties.get(PropertyType.genPropertyKey(HumanIntProperty.LOGINREWARD_DAYS,
							PropertyType.HUMAN_INT_PROPERTY))%></td>
						</tr>
						<tr>
							<td>当天剩余领奖次数</td>
							<td><%=properties.get(PropertyType.genPropertyKey(HumanIntProperty.LOGINREWARD_TIMES,
							PropertyType.HUMAN_INT_PROPERTY))%></td>
						</tr>
						<tr>
							<td>登陆奖励-特殊奖励一状态</td>
							<td><%=properties.get(PropertyType.genPropertyKey(HumanIntProperty.LOGINREWARD_ONE,
							PropertyType.HUMAN_INT_PROPERTY))%></td>
						</tr>
						<tr>
							<td>登陆奖励-特殊奖励二状态</td>
							<td><%=properties.get(PropertyType.genPropertyKey(HumanIntProperty.LOGINREWARD_TWO,
							PropertyType.HUMAN_INT_PROPERTY))%></td>
						</tr>
						<tr>
							<td>登陆奖励-特殊奖励三状态</td>
							<td><%=properties.get(PropertyType.genPropertyKey(HumanIntProperty.LOGINREWARD_THREE,
							PropertyType.HUMAN_INT_PROPERTY))%></td>
						</tr>
					</table>
				</div>


				<div id="divProperty2" class="divProperty">
					<table class="tableProperty">
						<tr>
							<th colspan="2">角色一级属性</th>
						</tr>
						<tr>
							<td>火焰</td>
							<td><%=properties.get(PropertyType.genPropertyKey(Level1Property.FIRE,
							PropertyType.LEVEL1_PROPERTY))%></td>
						</tr>
						<tr>
							<td>冰霜</td>
							<td><%=properties.get(PropertyType.genPropertyKey(Level1Property.ICE,
							PropertyType.LEVEL1_PROPERTY))%></td>
						</tr>
						<tr>
							<td>光明</td>
							<td><%=properties.get(PropertyType.genPropertyKey(Level1Property.LIGHT,
							PropertyType.LEVEL1_PROPERTY))%></td>
						</tr>
						<tr>
							<td>暗影</td>
							<td><%=properties.get(PropertyType.genPropertyKey(Level1Property.SHADOW,
							PropertyType.LEVEL1_PROPERTY))%></td>
						</tr>
						<tr>
							<td>自然</td>
							<td><%=properties.get(PropertyType.genPropertyKey(Level1Property.NATURE,
							PropertyType.LEVEL1_PROPERTY))%></td>
						</tr>
						<tr>
							<td>武力</td>
							<td><%=properties.get(PropertyType.genPropertyKey(Level1Property.FORCE,
							PropertyType.LEVEL1_PROPERTY))%></td>
						</tr>
						<tr>
							<td>敏捷</td>
							<td><%=properties.get(PropertyType.genPropertyKey(Level1Property.AGILE,
							PropertyType.LEVEL1_PROPERTY))%></td>
						</tr>
						<tr>
							<td>体力</td>
							<td><%=properties.get(PropertyType.genPropertyKey(Level1Property.STAMINA,
							PropertyType.LEVEL1_PROPERTY))%></td>
						</tr>
						<tr>
							<td>智力</td>
							<td><%=properties.get(PropertyType.genPropertyKey(Level1Property.INTELLIGENCE,
							PropertyType.LEVEL1_PROPERTY))%></td>
						</tr>
						<tr>
							<td>精神</td>
							<td><%=properties.get(PropertyType.genPropertyKey(Level1Property.SPIRIT,
							PropertyType.LEVEL1_PROPERTY))%></td>
						</tr>
					</table>
				</div>

				<div id="divProperty3" class="divProperty">
					<table class="tableProperty">
						<tr>
							<th colspan="2">角色二级属性</th>
						</tr>
						<tr>
							<td>最大HP</td>
							<td><%=properties.get(PropertyType.genPropertyKey(Level2Property.MAX_HP,
							PropertyType.LEVEL2_PROPERTY))%></td>
						</tr>
						<tr>
							<td>攻击力</td>
							<td><%=properties.get(PropertyType.genPropertyKey(Level2Property.ATTACK,
							PropertyType.LEVEL2_PROPERTY))%></td>
						</tr>
						<tr>
							<td>防御力</td>
							<td><%=properties.get(PropertyType.genPropertyKey(Level2Property.DEFENSE,
							PropertyType.LEVEL2_PROPERTY))%></td>
						</tr>
						<tr>
							<td>命中</td>
							<td><%=properties.get(PropertyType.genPropertyKey(Level2Property.HIT,
							PropertyType.LEVEL2_PROPERTY))%></td>
						</tr>
						<tr>
							<td>闪避</td>
							<td><%=properties.get(PropertyType.genPropertyKey(Level2Property.DODGE,
							PropertyType.LEVEL2_PROPERTY))%></td>
						</tr>
						<tr>
							<td>暴击</td>
							<td><%=properties.get(PropertyType.genPropertyKey(Level2Property.CRITICAL,
							PropertyType.LEVEL2_PROPERTY))%></td>
						</tr>
						<tr>
							<td>韧性</td>
							<td><%=properties.get(PropertyType.genPropertyKey(Level2Property.UNCRITICAL,
							PropertyType.LEVEL2_PROPERTY))%></td>
						</tr>
						<tr>
							<td>暴击伤害</td>
							<td><%=properties.get(PropertyType.genPropertyKey(Level2Property.CRITICAL_DAMAGE,
							PropertyType.LEVEL2_PROPERTY))%></td>
						</tr>
						<tr>
							<td>韧性伤害</td>
							<td><%=properties.get(PropertyType.genPropertyKey(Level2Property.UNCRITICAL_DAMAGE,
							PropertyType.LEVEL2_PROPERTY))%></td>
						</tr>
						<tr>
							<td>格挡</td>
							<td><%=properties.get(PropertyType.genPropertyKey(Level2Property.PARRY,
							PropertyType.LEVEL2_PROPERTY))%></td>
						</tr>
						<tr>
							<td>格挡伤害</td>
							<td><%=properties.get(PropertyType.genPropertyKey(Level2Property.PARRY_DAMAGE,
							PropertyType.LEVEL2_PROPERTY))%></td>
						</tr>
						<tr>
							<td>破击</td>
							<td><%=properties.get(PropertyType.genPropertyKey(Level2Property.UNPARRY,
							PropertyType.LEVEL2_PROPERTY))%></td>
						</tr>
						<tr>
							<td>破击伤害</td>
							<td><%=properties.get(PropertyType.genPropertyKey(Level2Property.UNPARRY_DAMAGE,
							PropertyType.LEVEL2_PROPERTY))%></td>
						</tr>
						<tr>
							<td>红魔上限</td>
							<td><%=properties.get(PropertyType.genPropertyKey(Level2Property.RED_MAX,
							PropertyType.LEVEL2_PROPERTY))%></td>
						</tr>
						<tr>
							<td>红魔初始值</td>
							<td><%=properties.get(PropertyType.genPropertyKey(Level2Property.RED_INIT_VALUE,
							PropertyType.LEVEL2_PROPERTY))%></td>
						</tr>
						<tr>
							<td>消除红魔加成值</td>
							<td><%=properties.get(PropertyType.genPropertyKey(Level2Property.RED_ELIMINATE_BONUS,
							PropertyType.LEVEL2_PROPERTY))%></td>
						</tr>
						<tr>
							<td>黄魔上限</td>
							<td><%=properties.get(PropertyType.genPropertyKey(Level2Property.YELLOW_MAX,
							PropertyType.LEVEL2_PROPERTY))%></td>
						</tr>
						<tr>
							<td>黄魔初始值</td>
							<td><%=properties.get(PropertyType.genPropertyKey(Level2Property.YELLOW_INIT_VALUE,
							PropertyType.LEVEL2_PROPERTY))%></td>
						</tr>
						<tr>
							<td>消除黄魔加成</td>
							<td><%=properties.get(PropertyType.genPropertyKey(Level2Property.YELLOW_ELIMINATE_BONUS,
							PropertyType.LEVEL2_PROPERTY))%></td>
						</tr>
						<tr>
							<td>蓝魔上限</td>
							<td><%=properties.get(PropertyType.genPropertyKey(Level2Property.BLUE_MAX,
							PropertyType.LEVEL2_PROPERTY))%></td>
						</tr>
						<tr>
							<td>蓝魔初始值</td>
							<td><%=properties.get(PropertyType.genPropertyKey(Level2Property.BLUE_INIT_VALUE,
							PropertyType.LEVEL2_PROPERTY))%></td>
						</tr>
						<tr>
							<td>消除蓝魔加成值</td>
							<td><%=properties.get(PropertyType.genPropertyKey(Level2Property.BLUE_ELIMINATE_BONUS,
							PropertyType.LEVEL2_PROPERTY))%></td>
						</tr>
						<tr>
							<td>绿魔上限</td>
							<td><%=properties.get(PropertyType.genPropertyKey(Level2Property.GREEN_MAX,
							PropertyType.LEVEL2_PROPERTY))%></td>
						</tr>
						<tr>
							<td>绿魔初始值</td>
							<td><%=properties.get(PropertyType.genPropertyKey(Level2Property.GREEN_INIT_VALUE,
							PropertyType.LEVEL2_PROPERTY))%></td>
						</tr>
						<tr>
							<td>消除绿魔获得的加成</td>
							<td><%=properties.get(PropertyType.genPropertyKey(Level2Property.GREEN_ELIMINATE_BONUS,
							PropertyType.LEVEL2_PROPERTY))%></td>
						</tr>
						<tr>
							<td>紫魔上限</td>
							<td><%=properties.get(PropertyType.genPropertyKey(Level2Property.PURPLE_MAX,
							PropertyType.LEVEL2_PROPERTY))%></td>
						</tr>
						<tr>
							<td>紫魔初始值</td>
							<td><%=properties.get(PropertyType.genPropertyKey(Level2Property.PURPLE_INIT_VALUE,
							PropertyType.LEVEL2_PROPERTY))%></td>
						</tr>
						<tr>
							<td>消除紫魔加成值</td>
							<td><%=properties.get(PropertyType.genPropertyKey(Level2Property.PURPLE_ELIMINATE_BONUS,
							PropertyType.LEVEL2_PROPERTY))%></td>
						</tr>
					</table>
				</div>
				<%
					}
				%>
			</div>

			<div id="divSkill">
				<%
					Object objHumanSkills = request.getAttribute("humanSkills");
						if (objHumanSkills != null) {
							List<HumanCarriedSkill> skills = (List<HumanCarriedSkill>) objHumanSkills;
				%>
				<table>
					<tr>
						<th colspan="3">角色技能列表</th>
					</tr>
					<tr>
						<th>技能id</th>
						<th>技能名称</th>
						<th>装备栏索引</th>
						<th>技能状态</th>
					</tr>
					<%
						for (HumanCarriedSkill skill : skills) {
					%>
					<tr>
						<td><%=skill.getSkill().getSkillId()%></td>
						<td><%=CharacterHelper.getSkillName(skill.getSkill().getSkillId())%></td>
						<td><%=skill.getSkill().getSlotIndex()%></td>
						<td><%=skill.getSkill().getSkillState()%></td>
					</tr>
					<%
						}
					%>
				</table>
				<%
					}
				%>
			</div>

			<div id="divItem">
				<%
					Object objEquipedItems = request.getAttribute("equipedItems");
					if (objEquipedItems != null) {
						List<EquipItem> equipItems = (List<EquipItem>) objEquipedItems;							
				%>
				<table>
					<tr>
						<th colspan="15">装备栏</th>
					</tr>
					<tr>
						<th>装备类型</th>
						<th>装备名称</th>
						<th>装备品质</th>
						<th>装备描述</th>
						<th>出售货币类型</th>
						<th>出售价格</th>
						<th>等级限制</th>
						<th>职业限制</th>
						<th>性别限制</th>
						<th>装备耐久</th>
						<th>强化等级</th>
						<th>宝石孔数</th>
						<th>基础属性</th>
						<th>强化属性</th>
						<th>镶嵌宝石</th>
					</tr>
					<%
						for (EquipItem item : equipItems) {
									String itemTypeName = "";
									if (item.getType() == ItemDetailType.WEAPON.getIndex()) {
										itemTypeName = "武器";
									} else if (item.getType() == ItemDetailType.CLOTH.getIndex()) {
										itemTypeName = "衣服";
									} else if (item.getType() == ItemDetailType.HAT.getIndex()) {
										itemTypeName = "头盔";
									} else if (item.getType() == ItemDetailType.NECKLACE.getIndex()) {
										itemTypeName = "项链";
									} else if (item.getType() == ItemDetailType.GLOVE.getIndex()) {
										itemTypeName = "手套";
									} else if (item.getType() == ItemDetailType.SHOES.getIndex()) {
										itemTypeName = "鞋子";
									}
					%>
					<tr>
						<td><%=itemTypeName%></td>
						<td><%=item.getName()%></td>
						<td><%=item.getRarity()%></td>
						<td><%=item.getDesc()%></td>
						<td><%=item.getSellCurrencyType()%></td>
						<td><%=item.getSellCurrencyNum()%></td>
						<td><%=item.getLimitLevel()%></td>
						<td><%=item.getLimitOccupation()%></td>
						<td><%=item.getLimitSex()%></td>
						<td>
							<%
								EquipItemFeature equipItemFeature = (EquipItemFeature) item.getFeature();
							%> <%=equipItemFeature.getEndure()%>
						</td>
						<td><%=equipItemFeature.getLevel()%></td>
						<td><%=equipItemFeature.getEquipHole()%></td>
						<td><%=CharacterHelper.getKeyValuePairPropertiesString(equipItemFeature
								.getEquipBaseAttributes())%></td>
						<td><%=CharacterHelper.getKeyValuePairPropertiesString(equipItemFeature
								.getEquipUpgradeAttributes())%></td>
						<td>
							<%
								for (GemItemInfo gemItem : equipItemFeature.getGemItemInfos()) {
												if (gemItem == null) {
													continue;
												}
							%> <%=CharacterHelper.getKeyValuePairPropertiesString(gemItem.getEquipGemAttributes())%>
							<%
								}
							%>
						</td>
					</tr>
					<%						
					}
					%>
				</table>
				<br />
				<%
					}				
				Object objBagItems = request.getAttribute("bagItems");
				if (objBagItems != null) {
					%>
				<table>
					<tr>
						<th colspan="8">背包</th>
					</tr>
					<tr>
						<th>装备类型</th>
						<th>装备名称</th>
						<th>装备品质</th>
						<th>装备描述</th>
						<th>出售货币类型</th>
						<th>出售价格</th>
						<th>堆叠数量</th>
						<th>其他</th>
					</tr>
					<%
					
						List<Item> bagItems = (List<Item>) request.getAttribute("bagItems");
						for (Item item : bagItems) {
									String itemTypeName = "";
									if (item.getType() == ItemDetailType.WEAPON.getIndex()) {
										itemTypeName = "武器";
									} else if (item.getType() == ItemDetailType.CLOTH.getIndex()) {
										itemTypeName = "衣服";
									} else if (item.getType() == ItemDetailType.HAT.getIndex()) {
										itemTypeName = "头盔";
									} else if (item.getType() == ItemDetailType.NECKLACE.getIndex()) {
										itemTypeName = "项链";
									} else if (item.getType() == ItemDetailType.GLOVE.getIndex()) {
										itemTypeName = "手套";
									} else if (item.getType() == ItemDetailType.SHOES.getIndex()) {
										itemTypeName = "鞋子";
									} else if (item.getType() == ItemDetailType.MATERIAL.getIndex()) {
										itemTypeName = "材料";
									} else if (item.getType() == ItemDetailType.CONSUME.getIndex()) {
										itemTypeName = "消耗品";
									} else if (item.getType() == ItemDetailType.GEM.getIndex()) {
										itemTypeName = "宝石";
									} else if (item.getType() == ItemDetailType.GUARDSTONE.getIndex()) {
										itemTypeName = "守护石";
									} else if (item.getType() == ItemDetailType.FORTUNESTONE.getIndex()) {
										itemTypeName = "幸运石";
									} else if (item.getType() == ItemDetailType.UPGRADESTONE.getIndex()) {
										itemTypeName = "强化石";
									} else if (item.getType() == ItemDetailType.EQUIPPAPER.getIndex()) {
										itemTypeName = "装备图纸";
									}
					%>
					<tr>
						<td><%=itemTypeName%></td>
						<td><%=item.getName()%></td>
						<td><%=item.getRarity()%></td>
						<td><%=item.getDesc()%></td>
						<td><%=item.getSellCurrencyType()%></td>
						<td><%=item.getSellCurrencyNum()%></td>
						<td><%=item.getOverlapNum()%></td>
						<td><%=CharacterHelper.getItemSpecialPropertyInfo(item)%></td>
					</tr>
					<%						
						}
					%>
				</table>
				<%
					}
				%>
			</div>

			<div id="divFriend">
				<%
					Object objHumanFriends = request.getAttribute("humanFriends");
						if (objHumanFriends != null) {
							List<HumanFriendInfo> humanFriends = (List<HumanFriendInfo>) objHumanFriends;
				%>
				<table>
					<tr>
						<th colspan="6">好友信息</th>
					</tr>
					<tr>
						<th>好友id</th>
						<th>未处理的好友申请</th>
						<th>自己发送出去的好友申请</th>
						<th>今日已赠送体力的好友</th>
						<th>今日赠送体力给自己的好友</th>
						<th>已接收体力的好友</th>
					</tr>
					<%
						for (HumanFriendInfo humanFriend : humanFriends) {
					%>
					<tr>
						<td><%=humanFriend.getFriendIds()%></td>
						<td><%=humanFriend.getOtherSendFriendApplyIds()%></td>
						<td><%=humanFriend.getSelfSendFriendApplyIds()%></td>
						<td><%=humanFriend.getSendEnergyToOtherFriendIds()%></td>
						<td><%=humanFriend.getSendEnergyToSelfFriendIds()%></td>
						<td><%=humanFriend.getRecievedEnergyFriendIds()%></td>
					</tr>
					<%
						}
					%>
				</table>
				<br />
				<%
					}
				%>

			</div>

			<div id="divQuest">
				<%
					Object objHumanQuestDatas = request.getAttribute("humanQuestDatas");
						if (objHumanQuestDatas != null) {
							List<HumanQuestData> questDataList = (List<HumanQuestData>) objHumanQuestDatas;
							List<HumanQuestFinishData> questFinishDataList = (List<HumanQuestFinishData>) request
									.getAttribute("humanQuestFinishDatas");
							List<HumanDailyQuestRewardBox> dailyRewardBox = (List<HumanDailyQuestRewardBox>) request
									.getAttribute("dailyRewardBox");
				%>
				<table>
					<tr>
						<th colspan="3">未完成任务</th>
					</tr>
					<tr>
						<th>任务id</th>
						<th>任务名称</th>
						<th>任务类型</th>
						<th>任务状态</th>
						<th>任务描述</th>
						<th>任务获得的积分</th>
						<th>最小等级</th>
						<th>最大等级</th>
						<th>任务经验</th>
						<th>任务金币奖励</th>
						<th>任务目标</th>
						<th>任务目标计数器</th>
					</tr>
					<%
						for (HumanQuestData questData : questDataList) {
									int questId = questData.getQuestStateData().getQuestId();
									QuestTemplate template = CharacterHelper.getQuestTemplateByQuestId(questId);
					%>
					<tr>
						<td><%=questId%></td>
						<td><%=template.getQuestName()%></td>
						<td><%=template.getQuestType()%></td>
						<td><%=questData.getQuestStateData().getState()%></td>
						<td><%=template.getQuestDesc()%></td>
						<td><%=template.getGetScore()%></td>
						<td><%=template.getMinGetLevel()%></td>
						<td><%=template.getMaxGetLevel()%></td>
						<td><%=template.getRewardExp()%></td>
						<td><%=template.getRewardMoney()%></td>
						<td><%=CharacterHelper.getQuestAimInfoListString(template.getAimInfoList())%></td>
						<td><%=CharacterHelper.getQuestAimCounterListString(questData.getQuestStateData()
								.getAimCounterList())%></td>
					</tr>
					<%
						}
					%>
				</table>
				<br />
				<table>
					<tr>
						<th colspan="3">已完成任务</th>
					</tr>
					<tr>
						<th>任务id</th>
						<th>任务名称</th>
						<th>任务类型</th>
						<th>任务状态</th>
						<th>任务描述</th>
						<th>任务获得的积分</th>
						<th>最小等级</th>
						<th>最大等级</th>
						<th>任务经验</th>
						<th>任务金币奖励</th>
						<th>任务目标</th>
						<th>任务目标计数器</th>
					</tr>
					<%
						for (HumanQuestFinishData finishData : questFinishDataList) {
									int questId = finishData.getQuestFinishData().getQuestId();
									QuestTemplate template = CharacterHelper.getQuestTemplateByQuestId(questId);
					%>
					<tr>
						<td><%=questId%></td>
						<td><%=template.getQuestName()%></td>
						<td><%=template.getQuestType()%></td>
						<td><%=template.getQuestDesc()%></td>
						<td><%=template.getGetScore()%></td>
						<td><%=template.getMinGetLevel()%></td>
						<td><%=template.getMaxGetLevel()%></td>
						<td><%=template.getRewardExp()%></td>
						<td><%=template.getRewardMoney()%></td>
						<td><%=template.getRewardMoney()%></td>
						<td><%=CharacterHelper.getQuestAimInfoListString(template.getAimInfoList())%></td>
					</tr>
					<%
						}
					%>
				</table>
				<br />
				<table>
					<tr>
						<th colspan="2">日常任务宝箱奖励</th>
					</tr>
					<tr>
						<th>宝箱id</th>
						<th>宝箱开启状态</th>
					</tr>
					<%
						for (HumanDailyQuestRewardBox box : dailyRewardBox) {
					%>
					<tr>
						<td><%=box.getBox().getBoxId()%></td>
						<td><%=box.getBox().getState()%></td>
					</tr>
					<%
						}
					%>
				</table>

				<%
					}
				%>
			</div>

			<div id="divHoroscope">
				<%
					Object objStargazers = request.getAttribute("stargazer");
						if (objStargazers != null) {
							List<HumanStargazer> stargazers = (List<HumanStargazer>) objStargazers;
							List<HumanHoroscope> horoscopes = (List<HumanHoroscope>) request.getAttribute("horoscopes");
				%>
				<table>
					<tr>
						<th colspan="2">占星师</th>
					</tr>
					<tr>
						<th>占星师id</th>
						<th>占星师状态</th>
					</tr>
					<%
						for (HumanStargazer stargazer : stargazers) {
					%>
					<tr>
						<td><%=stargazer.getStargazer().getStargazerId()%></td>
						<td><%=stargazer.getStargazer().getOpen()%></td>
					</tr>
					<%
						}
					%>
				</table>
				<br />

				<table>
					<tr>
						<th colspan="2">星运</th>
					</tr>
					<tr>
						<th>星运id</th>
						<th>星运名称</th>
						<th>星运背包类型</th>
						<th>星运背包位置索引</th>
						<th>星运经验</th>
						<th>星运描述</th>
						<th>星运等级</th>
						<th>星运效果</th>
						<th>星运效果值</th>
					</tr>
					<%
						for (HumanHoroscope horoscope : horoscopes) {
									HoroscopeInfo horoscopeInfo = CharacterHelper.getHoroscopeInfo(horoscope.getHoroscope()
											.getHoroscopeId(), horoscope.getHoroscope().getBagType());
					%>
					<tr>
						<td><%=horoscope.getHoroscope().getHoroscopeId()%></td>
						<td><%=horoscopeInfo.getName()%></td>
						<td><%=horoscope.getHoroscope().getBagType()%></td>
						<td><%=horoscope.getHoroscope().getBagIndex()%></td>
						<td><%=horoscope.getHoroscope().getExperience()%></td>
						<td><%=horoscopeInfo.getDesc()%></td>
						<td><%=horoscopeInfo.getLevel()%></td>
						<td><%=horoscopeInfo.getKey()%></td>
						
						<td><%=horoscopeInfo.getValue()%></td>
					</tr>
					<%
						}
					%>
				</table>
				<%
					}
				%>
			</div>

			<div id="divTechnology">
				<%
					Object objTechnologys = request.getAttribute("technologys");
						if (objTechnologys != null) {
							List<HumanTechnology> technologys = (List<HumanTechnology>) objTechnologys;
				%>
				<table>
					<tr>
						<th colspan="2">科技</th>
					</tr>
					<tr>
						<th>科技id</th>
						<th>科技等级</th>
					</tr>
					<%
						for (HumanTechnology technology : technologys) {
					%>
					<tr>
						<td><%=technology.getTechnology().getTechnologyId()%></td>
						<td><%=technology.getTechnology().getLevel()%></td>
					</tr>
					<%
						}
					%>
				</table>
				<%
					}
				%>
			</div>

			<div id="divStageMapState">
				<%
					Object objStageMapState = request.getAttribute("stageMapState");
						if (objStageMapState != null) {
							List<HumanStageMapState> stageMapStates = (List<HumanStageMapState>) objStageMapState;
				%>
				<table>
					<tr>
						<th colspan="2">关卡</th>
					</tr>
					<tr>
						<th>关卡id</th>
						<th>关卡状态</th>
					</tr>
					<%
						for (HumanStageMapState stageMapState : stageMapStates) {
					%>
					<tr>
						<td><%=stageMapState.getStageMapStateData().getMapId()%></td>
						<td><%=stageMapState.getStageMapStateData().getState()%></td>
					</tr>
					<%
						}
					%>
				</table>
				<%
					}
						Object objEliteStageInfo = request.getAttribute("eliteStageInfo");
						if (objEliteStageInfo != null) {
							HumanEliteStageInfo eliteStageInfo = (HumanEliteStageInfo) objEliteStageInfo;
				%>
				<table>
					<tr>
						<th colspan="2">精英副本</th>
					</tr>
					<tr>
						<td>今日刷新副本次数</td>
						<td><%=eliteStageInfo.getRefreshStageStateNum()%></td>
					</tr>
					<tr>
						<td>开启的精英副本类型</td>
						<td>
							<%
								String openedTypeId = "";
										for (int id : eliteStageInfo.getOpenedStageTypeIdsList()) {
											openedTypeId = openedTypeId + String.valueOf(id) + ",";
										}
							%> <%=openedTypeId%>
						</td>
					</tr>
				</table>

				<table>
					<tr>
						<th colspan="3">副本状态</th>
					</tr>
					<tr>
						<th>副本id</th>
						<th>今日是否已挑战</th>
						<th>是否战胜过该副本</th>
					</tr>
					<%
						for (EliteStageState stageState : eliteStageInfo.getStageStateList()) {
					%>
					<tr>
						<td><%=stageState.getStageId()%></td>
						<td><%=stageState.getChallengeState()%></td>
						<td><%=stageState.getConquerState()%></td>
					</tr>
					<%
						}
					%>
				</table>
				<%
					}
				%>
			</div>

			<div id="divMine">
				<%
					Object objMineInfo = request.getAttribute("mineInfo");
						if (objMineInfo != null) {
							HumanMineInfo mineInfo = (HumanMineInfo) objMineInfo;
				%>
				<table>
					<tr>
						<th colspan="2">矿场</th>
					</tr>
					<tr>
						<td>剩余免费次数</td>
						<td><%=mineInfo.getRemainFreeMineNum()%></td>
					</tr>
					<tr>
						<td>花费魔晶开采次数</td>
						<td><%=mineInfo.getBuyMineFieldNum()%></td>
					</tr>
					<tr>
						<td>今日已重置矿坑次数</td>
						<td><%=mineInfo.getResetMineFieldNum()%></td>
					</tr>
				</table>
				<br />
				<%
					if (mineInfo.getMineFieldsList() != null) {
				%>
				<table>
					<tr>
						<th>矿坑索引</th>
						<th>矿坑类型</th>
						<th>矿坑名称</th>
					</tr>
					<%
						for (MineField mineField : mineInfo.getMineFieldsList()) {
					%>

					<tr>
						<td><%=mineField.getIndex()%></td>
						<td><%=mineField.getType()%></td>
						<td><%=CharacterHelper.getMineName(mineField.getType())%></td>
					</tr>
					<%
						}
					%>
				</table>
				<%
					}
						}
				%>
			</div>

			<div id="divQuestion">
				<%
					Object objQuestion = request.getAttribute("humanQuestion");
						if (objQuestion != null) {
							HumanQuestionData questionData = (HumanQuestionData) objQuestion;
				%>
				<table>
					<tr>
						<th colspan="2">问答数据</th>
					</tr>
					<tr>
						<td>当前积分</td>
						<td><%=questionData.getTotalScore()%></td>
					</tr>
					<tr>
						<td>可用祈福次数</td>
						<td><%=questionData.getUsableBlessNum()%></td>
					</tr>
					<tr>
						<td>购买祈福次数</td>
						<td><%=questionData.getBuyBlessTimes()%></td>
					</tr>
					<tr>
						<td>题目序号</td>
						<td><%=questionData.getQuestionIndex()%></td>
					</tr>
					<tr>
						<td>题目id</td>
						<td><%=questionData.getQuestionId()%></td>
					</tr>
					<tr>
						<td>积分兑换状态</td>
						<td>
							<%
								if (questionData.getScoreExchangeStateList() != null) {
											for (Integer state : questionData.getScoreExchangeStateList()) {
												out.print(state + ",");
											}
										}
							%>
						</td>
					</tr>
					<tr>
						<td>上次重置时间（每日任务）</td>
						<td><%=dfDateTime.format(new Date(questionData.getLastDailyResetTime()))%></td>
					</tr>
					<tr>
						<td>上次重置时间（每周任务）</td>
						<td><%=dfDateTime.format(new Date(questionData.getLastWeeklyResetTime()))%></td>
					</tr>
				</table>
				<%
					}
				%>
			</div>

			<div id="divStoneCollect">
				<%
					Object objMainCityInfo = request.getAttribute("mainCityInfo");
						if (objMainCityInfo != null) {
							HumanMainCityInfo mainCityInfo = (HumanMainCityInfo) objMainCityInfo;
				%>
				<table>
					<tr>
						<th colspan="2">问答数据</th>
					</tr>
					<tr>
						<td>剩余免费收集次数</td>
						<td><%=mainCityInfo.getRemainFreeCollectNum()%></td>
					</tr>
					<tr>
						<td>花费魔晶已收集次数</td>
						<td><%=mainCityInfo.getCostCrystalCollectNum()%></td>
					</tr>
					<tr>
						<td>税收加成</td>
						<td><%=mainCityInfo.getLevyExtraRate()%></td>
					</tr>
					<tr>
						<td>收集目标</td>
						<td>
							<%
								if (mainCityInfo.getCollectTargetList() != null) {
											for (Integer id : mainCityInfo.getCollectTargetList()) {
												out.print(id + ",");
											}
										}
							%>
						</td>
					</tr>
					<tr>
						<td>收集状态</td>
						<td>
							<%
								if (mainCityInfo.getCurrentCollectedList() != null) {
											for (Integer id : mainCityInfo.getCurrentCollectedList()) {
												out.print(id + ",");
											}
										}
							%>
						</td>
					</tr>
				</table>
				<%
					}
				%>
			</div>

			<div id="divLoginReward">
				<%
					Object objLoginRewards = request.getAttribute("loginRewards");
						if (objLoginRewards != null) {
							List<HumanLoginReward> loginRewards = (List<HumanLoginReward>) objLoginRewards;
				%>
				<table>
					<tr>
						<th colspan="2">连续登陆奖励</th>
					</tr>
					<tr>
						<th>index</th>
						<th>物品id</th>
					</tr>
					<%
						for (HumanLoginReward reward : loginRewards) {
					%>
					<tr>
						<td><%=reward.getLoginRewardData().getIndex()%></td>
						<td><%=reward.getLoginRewardData().getItemId()%></td>
					</tr>
					<%
						}
					%>
				</table>
				<%
					}
				%>
			</div>

			<div id="divCostNotify">
				<%
					Object objCostnotifys = request.getAttribute("costnotifys");
						if (objCostnotifys != null) {
							List<HumanCostNotify> costnotifys = (List<HumanCostNotify>) objCostnotifys;
				%>
				<table>
					<tr>
						<th colspan="2">连续登陆奖励</th>
					</tr>
					<tr>
						<th>消费提醒类型</th>
						<th>消费提醒名称</th>
						<th>状态</th>
					</tr>
					<%
						for (HumanCostNotify notify : costnotifys) {
					%>
					<tr>
						<td><%=notify.getCostNotifyData().getCostNotifyType()%></td>
						<td><%=CharacterHelper.getCostNotifyName(notify.getCostNotifyData().getCostNotifyType())%></td>
						<td><%=notify.getCostNotifyData().getOpen()%></td>
					</tr>
					<%
						}
					%>
				</table>
				<%
					}
				%>
			</div>

			<div id="divGift">
				<%
					Object objGifts = request.getAttribute("gifts");
						if (objGifts != null) {
							HumanGift gifts = (HumanGift) objGifts;
				%>
				<table>
					<tr>
						<th colspan="3">已激活天赋</th>
					</tr>
					<tr>
						<th>天赋id</th>
						<th>天赋名称</th>			
						<th>天赋类型</th>					
					</tr>
					<%
						for (Integer giftId : gifts.getGiftIdList()) {
							GiftTemplate giftTemplate = TemplateManager.getGiftTemplate(giftId);
					%>
					<tr>						
						<td><%=giftId%></td>
						<td><%=giftTemplate.getName()%></td>
						<td><%=giftTemplate.getType()%></td>
					</tr>
					<%
						}
					%>
				</table>
				<%
					}
				%>
			</div>
		<div id="divWarrior">
				<%
					Object objWarriorInfo = request.getAttribute("warriorInfo");
						if (objWarriorInfo != null) {
							HumanWarriorInfo warriorInfo = (HumanWarriorInfo) objWarriorInfo;
				%>
				<table>
					<tr>
						<th colspan="2">勇者之路</th>
					</tr>
					<tr>
						<td>当前任务id</td>
						<td><%=warriorInfo.getCurrentQuestIndex()%></td>
					</tr>					
					<tr>
						<td>当前任务状态</td>
						<td><%=warriorInfo.getQuestRewardState()%></td>
					</tr>
					<tr>
						<td>任务奖励状态</td>
						<td><%=warriorInfo.getCurrentQuestState()%></td>
					</tr>
					<tr>
						<td>当前任务进度</td>
						<td><%=warriorInfo.getCurrentQuestCompleteNum()%></td>
					</tr>
				</table>
				<%
					}
				%>
			</div>
			
			<div id="divRefine">
				<%
					Object objRefineMap = request.getAttribute("refineMap");
						if (objRefineMap != null) {
							List<RefineMapData> refineMapList = (List<RefineMapData>) objRefineMap;
				%>
				<table>
					<tr>
						<th colspan="3">试炼副本</th>
					</tr>
					<tr>
						<th>副本id</th>
						<th>征战最高记录</th>
					</tr>	
					<%
						for (RefineMapData refineMap : refineMapList) {							
					%>			
					<tr>
						<td><%=refineMap.getMapId() %></td>
						<td><%=refineMap.getBestStageId()%></td>
					</tr>
					<% } %>
				</table>
				<%
					}
				
					Object objRefineStage = request.getAttribute("refineStage");
						if (objRefineStage != null) {
							List<RefineStageData> refineStageList = (List<RefineStageData>) objRefineStage;
				%>
				<table>
					<tr>
						<th colspan="3">试炼副本</th>
					</tr>
					<tr>
						<th>副本id</th>
						<th>试炼关卡id</th>
						<th>试炼关卡状态</th>
						<th>关卡是否领取过</th>
					</tr>	
					<%
						for (RefineStageData refineStage : refineStageList) {							
					%>			
					<tr>
						<td><%=refineStage.getMapId() %></td>
						<td><%=refineStage.getStageId()%></td>
						<td><%=refineStage.getStageState() %></td>
						<td><%=refineStage.getGetted()%></td>
					</tr>
					<% } %>
				</table>
				<% } %>
			</div>
		</div>
	</s:form>
</body>
</html>