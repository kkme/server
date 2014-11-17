package com.hifun.soul.gameserver.common.log;

import com.hifun.soul.logserver.model.BasicPlayerLog;
import com.hifun.soul.logserver.model.MoneyLog;
import com.hifun.soul.logserver.model.ItemLog;
import com.hifun.soul.logserver.model.PropertyLog;
import com.hifun.soul.logserver.model.GmCommandLog;
import com.hifun.soul.logserver.model.OnlineTimeLog;
import com.hifun.soul.logserver.model.ChatLog;
import com.hifun.soul.logserver.model.BattleLog;
import com.hifun.soul.logserver.model.FriendLog;
import com.hifun.soul.logserver.model.HoroscopeLog;
import com.hifun.soul.logserver.model.QuestLog;
import com.hifun.soul.logserver.model.RechargeLog;
import com.hifun.soul.logserver.model.EnergyLog;
import com.hifun.soul.logserver.model.HonourLog;
import com.hifun.soul.logserver.model.TechPointLog;
import com.hifun.soul.logserver.model.ExperienceLog;
import com.hifun.soul.logserver.model.SkillPointLog;
import com.hifun.soul.core.log.UdpLoggerClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.hifun.soul.common.LogReasons;
import com.hifun.soul.gameserver.human.Human;
import org.springframework.beans.factory.annotation.Autowired;
import com.hifun.soul.core.time.SystemTimeService;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * This is an auto generated source,please don't modify it.
 */
@Scope("singleton")
@Component
public class LogService {
	private static final Logger logger = LoggerFactory.getLogger(LogService.class);
	/** 日志客户端 */
	private final UdpLoggerClient udpLoggerClient = UdpLoggerClient.getInstance();
	/**  游戏区ID */
	private int regionID;
	/** 游戏服务器ID */
	private int serverID;
	/** 游戏时间服务管理 */
	@Autowired
	private SystemTimeService timeService;
	
	private boolean isLogServerSwitch = true;
	
	public LogService() {
	}
	
	public LogService(boolean isLogServerSwitch) {
		this.isLogServerSwitch = isLogServerSwitch;
	}
	
	public void setIsLogServerSwitch(boolean isLogServerSwitch) {
		this.isLogServerSwitch = isLogServerSwitch;
	}
	/**
	 * 初始化
	 * 
	 * @param logServerIp
	 *            日志服务器IP
	 * @param logServerPort
	 *            日志服务器端口
	 * @param regionID
	 *            游戏区ID
	 * @param serverID
	 *            游戏服务器ID
	 */
	public void init(String logServerIp, int logServerPort, int regionID, int serverID) {
		if(isLogServerSwitch){
			udpLoggerClient.initialize(logServerIp, logServerPort);
			udpLoggerClient.setRegionId(regionID);
			udpLoggerClient.setServerId(serverID);
	
			this.regionID = regionID;
			this.serverID = serverID;
			
			if (logger.isInfoEnabled()) {
				logger.info("Connnect to Log server : " + logServerIp + " : " + logServerPort);
			}
		}
	}

	/**
	 * 发送角色基本日志(登陆登出日志)
	 * @param logTime 日志产生时间
	 * @param accountId 玩家账号Id
	 * @param accountName 玩家的账号名
	 * @param charId 角色ID
	 * @param charName 玩家的角色名
	 * @param level 玩家等级
	 * @param allianceId 玩家阵营
	 * @param vipLevel 玩家VIP等级
	 * @param reason 原因
	 * @param param 其他参数
	 * @param ip IP地址
	 * @param crystal 魔晶数量
	 * @param coin 金币
	 * @param experience 经验
	 * @param energy 精力值
	 * @param online_time 在线时长
	 */
	public void sendBasicPlayerLog(Human human,
				LogReasons.BasicPlayerLogReason reason,				String param			,String ip
			,int crystal
			,int coin
			,int experience
			,int energy
			,long online_time
		) {
		if(isLogServerSwitch){
		udpLoggerClient.sendMessage(new BasicPlayerLog(
			
				timeService.now(),			
				this.regionID,			
				this.serverID,			
				human.getPlayer().getPassportId(),			
				human.getPlayer().getAccount(),			
				human.getHumanGuid(),			
				human.getName(),			
				human.getLevel(),			
				human.getOccupation().getIndex(),			
				human.getVipLevel(),			
				reason.reason,			
				param			
			,ip
			,crystal
			,coin
			,experience
			,energy
			,online_time
		));
		}
	}
	/**
	 * 发送金钱日志
	 * @param logTime 日志产生时间
	 * @param accountId 玩家账号Id
	 * @param accountName 玩家的账号名
	 * @param charId 角色ID
	 * @param charName 玩家的角色名
	 * @param level 玩家等级
	 * @param allianceId 玩家阵营
	 * @param vipLevel 玩家VIP等级
	 * @param reason 原因
	 * @param param 其他参数
	 * @param currencyType 变化货币类型
	 * @param currencyNum 变化货币数量
	 * @param afterNum 变化之后数量
	 */
	public void sendMoneyLog(Human human,
				LogReasons.MoneyLogReason reason,				String param			,int currencyType
			,int currencyNum
			,int afterNum
		) {
		if(isLogServerSwitch){
		udpLoggerClient.sendMessage(new MoneyLog(
			
				timeService.now(),			
				this.regionID,			
				this.serverID,			
				human.getPlayer().getPassportId(),			
				human.getPlayer().getAccount(),			
				human.getHumanGuid(),			
				human.getName(),			
				human.getLevel(),			
				human.getOccupation().getIndex(),			
				human.getVipLevel(),			
				reason.reason,			
				param			
			,currencyType
			,currencyNum
			,afterNum
		));
		}
	}
	/**
	 * 发送物品改变日志
	 * @param logTime 日志产生时间
	 * @param accountId 玩家账号Id
	 * @param accountName 玩家的账号名
	 * @param charId 角色ID
	 * @param charName 玩家的角色名
	 * @param level 玩家等级
	 * @param allianceId 玩家阵营
	 * @param vipLevel 玩家VIP等级
	 * @param reason 原因
	 * @param param 其他参数
	 * @param bagType 包裹id
	 * @param bagIndex 在包裹中的位置索引
	 * @param templateId 道具模板ID
	 * @param itemUUID 道具实例ID
	 * @param count 操作后的最终叠加数
	 */
	public void sendItemLog(Human human,
				LogReasons.ItemLogReason reason,				String param			,int bagType
			,int bagIndex
			,int templateId
			,String itemUUID
			,int count
		) {
		if(isLogServerSwitch){
		udpLoggerClient.sendMessage(new ItemLog(
			
				timeService.now(),			
				this.regionID,			
				this.serverID,			
				human.getPlayer().getPassportId(),			
				human.getPlayer().getAccount(),			
				human.getHumanGuid(),			
				human.getName(),			
				human.getLevel(),			
				human.getOccupation().getIndex(),			
				human.getVipLevel(),			
				reason.reason,			
				param			
			,bagType
			,bagIndex
			,templateId
			,itemUUID
			,count
		));
		}
	}
	/**
	 * 发送属性日志
	 * @param logTime 日志产生时间
	 * @param accountId 玩家账号Id
	 * @param accountName 玩家的账号名
	 * @param charId 角色ID
	 * @param charName 玩家的角色名
	 * @param level 玩家等级
	 * @param allianceId 玩家阵营
	 * @param vipLevel 玩家VIP等级
	 * @param reason 原因
	 * @param param 其他参数
	 * @param propertyKey key
	 * @param propertyChange 属性变化
	 */
	public void sendPropertyLog(Human human,
				LogReasons.PropertyLogReason reason,				String param			,int propertyKey
			,String propertyChange
		) {
		if(isLogServerSwitch){
		udpLoggerClient.sendMessage(new PropertyLog(
			
				timeService.now(),			
				this.regionID,			
				this.serverID,			
				human.getPlayer().getPassportId(),			
				human.getPlayer().getAccount(),			
				human.getHumanGuid(),			
				human.getName(),			
				human.getLevel(),			
				human.getOccupation().getIndex(),			
				human.getVipLevel(),			
				reason.reason,			
				param			
			,propertyKey
			,propertyChange
		));
		}
	}
	/**
	 * 发送gm命令日志
	 * @param logTime 日志产生时间
	 * @param accountId 玩家账号Id
	 * @param accountName 玩家的账号名
	 * @param charId 角色ID
	 * @param charName 玩家的角色名
	 * @param level 玩家等级
	 * @param allianceId 玩家阵营
	 * @param vipLevel 玩家VIP等级
	 * @param reason 原因
	 * @param param 其他参数
	 * @param gmCommandContext gm命令的内容
	 */
	public void sendGmCommandLog(Human human,
				LogReasons.GmCommandLogReason reason,				String param			,String gmCommandContext
		) {
		if(isLogServerSwitch){
		udpLoggerClient.sendMessage(new GmCommandLog(
			
				timeService.now(),			
				this.regionID,			
				this.serverID,			
				human.getPlayer().getPassportId(),			
				human.getPlayer().getAccount(),			
				human.getHumanGuid(),			
				human.getName(),			
				human.getLevel(),			
				human.getOccupation().getIndex(),			
				human.getVipLevel(),			
				reason.reason,			
				param			
			,gmCommandContext
		));
		}
	}
	/**
	 * 发送玩家在线时间日志
	 * @param logTime 日志产生时间
	 * @param accountId 玩家账号Id
	 * @param accountName 玩家的账号名
	 * @param charId 角色ID
	 * @param charName 玩家的角色名
	 * @param level 玩家等级
	 * @param allianceId 玩家阵营
	 * @param vipLevel 玩家VIP等级
	 * @param reason 原因
	 * @param param 其他参数
	 * @param minute 当天累计在线时间(分钟)
	 * @param totalMinute 累计在线时间(分钟)
	 * @param lastLoginTime 最后一次登录时间
	 * @param lastLogoutTime 最后一次登出时间
	 */
	public void sendOnlineTimeLog(Human human,
				LogReasons.OnlineTimeLogReason reason,				String param			,int minute
			,int totalMinute
			,long lastLoginTime
			,long lastLogoutTime
		) {
		if(isLogServerSwitch){
		udpLoggerClient.sendMessage(new OnlineTimeLog(
			
				timeService.now(),			
				this.regionID,			
				this.serverID,			
				human.getPlayer().getPassportId(),			
				human.getPlayer().getAccount(),			
				human.getHumanGuid(),			
				human.getName(),			
				human.getLevel(),			
				human.getOccupation().getIndex(),			
				human.getVipLevel(),			
				reason.reason,			
				param			
			,minute
			,totalMinute
			,lastLoginTime
			,lastLogoutTime
		));
		}
	}
	/**
	 * 发送聊天日志
	 * @param logTime 日志产生时间
	 * @param accountId 玩家账号Id
	 * @param accountName 玩家的账号名
	 * @param charId 角色ID
	 * @param charName 玩家的角色名
	 * @param level 玩家等级
	 * @param allianceId 玩家阵营
	 * @param vipLevel 玩家VIP等级
	 * @param reason 原因
	 * @param param 其他参数
	 * @param destPassportId 目标账号id
	 * @param destRoleName 目标角色名称
	 * @param chatType 聊天类型
	 * @param content 聊天内容
	 */
	public void sendChatLog(Human human,
				LogReasons.ChatLogReason reason,				String param			,long destPassportId
			,String destRoleName
			,int chatType
			,String content
		) {
		if(isLogServerSwitch){
		udpLoggerClient.sendMessage(new ChatLog(
			
				timeService.now(),			
				this.regionID,			
				this.serverID,			
				human.getPlayer().getPassportId(),			
				human.getPlayer().getAccount(),			
				human.getHumanGuid(),			
				human.getName(),			
				human.getLevel(),			
				human.getOccupation().getIndex(),			
				human.getVipLevel(),			
				reason.reason,			
				param			
			,destPassportId
			,destRoleName
			,chatType
			,content
		));
		}
	}
	/**
	 * 发送战斗日志
	 * @param logTime 日志产生时间
	 * @param accountId 玩家账号Id
	 * @param accountName 玩家的账号名
	 * @param charId 角色ID
	 * @param charName 玩家的角色名
	 * @param level 玩家等级
	 * @param allianceId 玩家阵营
	 * @param vipLevel 玩家VIP等级
	 * @param reason 原因
	 * @param param 其他参数
	 * @param stageId 关卡id
	 * @param result 战斗结果
	 * @param itemIds 战斗奖励物品
	 */
	public void sendBattleLog(Human human,
				LogReasons.BattleLogReason reason,				String param			,int stageId
			,int result
			,String itemIds
		) {
		if(isLogServerSwitch){
		udpLoggerClient.sendMessage(new BattleLog(
			
				timeService.now(),			
				this.regionID,			
				this.serverID,			
				human.getPlayer().getPassportId(),			
				human.getPlayer().getAccount(),			
				human.getHumanGuid(),			
				human.getName(),			
				human.getLevel(),			
				human.getOccupation().getIndex(),			
				human.getVipLevel(),			
				reason.reason,			
				param			
			,stageId
			,result
			,itemIds
		));
		}
	}
	/**
	 * 发送好友日志
	 * @param logTime 日志产生时间
	 * @param accountId 玩家账号Id
	 * @param accountName 玩家的账号名
	 * @param charId 角色ID
	 * @param charName 玩家的角色名
	 * @param level 玩家等级
	 * @param allianceId 玩家阵营
	 * @param vipLevel 玩家VIP等级
	 * @param reason 原因
	 * @param param 其他参数
	 * @param friendPassportId 好友账号id
	 * @param friendPassportName 好友账号名称
	 * @param friendRoleId 好友角色id
	 * @param friendRoleName 好友角色名称
	 */
	public void sendFriendLog(Human human,
				LogReasons.FriendLogReason reason,				String param			,long friendPassportId
			,String friendPassportName
			,long friendRoleId
			,String friendRoleName
		) {
		if(isLogServerSwitch){
		udpLoggerClient.sendMessage(new FriendLog(
			
				timeService.now(),			
				this.regionID,			
				this.serverID,			
				human.getPlayer().getPassportId(),			
				human.getPlayer().getAccount(),			
				human.getHumanGuid(),			
				human.getName(),			
				human.getLevel(),			
				human.getOccupation().getIndex(),			
				human.getVipLevel(),			
				reason.reason,			
				param			
			,friendPassportId
			,friendPassportName
			,friendRoleId
			,friendRoleName
		));
		}
	}
	/**
	 * 发送星运日志
	 * @param logTime 日志产生时间
	 * @param accountId 玩家账号Id
	 * @param accountName 玩家的账号名
	 * @param charId 角色ID
	 * @param charName 玩家的角色名
	 * @param level 玩家等级
	 * @param allianceId 玩家阵营
	 * @param vipLevel 玩家VIP等级
	 * @param reason 原因
	 * @param param 其他参数
	 * @param bagType 星运所在包
	 * @param bagIndex 星运所在包索引
	 * @param horoscopeId 星运的id
	 * @param experience 星运当前经验
	 */
	public void sendHoroscopeLog(Human human,
				LogReasons.HoroscopeLogReason reason,				String param			,int bagType
			,int bagIndex
			,int horoscopeId
			,int experience
		) {
		if(isLogServerSwitch){
		udpLoggerClient.sendMessage(new HoroscopeLog(
			
				timeService.now(),			
				this.regionID,			
				this.serverID,			
				human.getPlayer().getPassportId(),			
				human.getPlayer().getAccount(),			
				human.getHumanGuid(),			
				human.getName(),			
				human.getLevel(),			
				human.getOccupation().getIndex(),			
				human.getVipLevel(),			
				reason.reason,			
				param			
			,bagType
			,bagIndex
			,horoscopeId
			,experience
		));
		}
	}
	/**
	 * 发送任务日志
	 * @param logTime 日志产生时间
	 * @param accountId 玩家账号Id
	 * @param accountName 玩家的账号名
	 * @param charId 角色ID
	 * @param charName 玩家的角色名
	 * @param level 玩家等级
	 * @param allianceId 玩家阵营
	 * @param vipLevel 玩家VIP等级
	 * @param reason 原因
	 * @param param 其他参数
	 * @param questId 任务id
	 */
	public void sendQuestLog(Human human,
				LogReasons.QuestLogReason reason,				String param			,int questId
		) {
		if(isLogServerSwitch){
		udpLoggerClient.sendMessage(new QuestLog(
			
				timeService.now(),			
				this.regionID,			
				this.serverID,			
				human.getPlayer().getPassportId(),			
				human.getPlayer().getAccount(),			
				human.getHumanGuid(),			
				human.getName(),			
				human.getLevel(),			
				human.getOccupation().getIndex(),			
				human.getVipLevel(),			
				reason.reason,			
				param			
			,questId
		));
		}
	}
	/**
	 * 发送充值日志
	 * @param logTime 日志产生时间
	 * @param accountId 玩家账号Id
	 * @param accountName 玩家的账号名
	 * @param charId 角色ID
	 * @param charName 玩家的角色名
	 * @param level 玩家等级
	 * @param allianceId 玩家阵营
	 * @param vipLevel 玩家VIP等级
	 * @param reason 原因
	 * @param param 其他参数
	 * @param rechargeNum 充值数
	 * @param crystalNum 充值获得的魔晶数
	 * @param crystalRewardNum 获得奖励魔晶数
	 * @param beforeNum 充值前的魔晶数
	 * @param afterNum 充值后的魔晶数
	 * @param exchangeRate 兑换比率
	 */
	public void sendRechargeLog(Human human,
				LogReasons.RechargeLogReason reason,				String param			,int rechargeNum
			,int crystalNum
			,int crystalRewardNum
			,int beforeNum
			,int afterNum
			,int exchangeRate
		) {
		if(isLogServerSwitch){
		udpLoggerClient.sendMessage(new RechargeLog(
			
				timeService.now(),			
				this.regionID,			
				this.serverID,			
				human.getPlayer().getPassportId(),			
				human.getPlayer().getAccount(),			
				human.getHumanGuid(),			
				human.getName(),			
				human.getLevel(),			
				human.getOccupation().getIndex(),			
				human.getVipLevel(),			
				reason.reason,			
				param			
			,rechargeNum
			,crystalNum
			,crystalRewardNum
			,beforeNum
			,afterNum
			,exchangeRate
		));
		}
	}
	/**
	 * 发送体力日志
	 * @param logTime 日志产生时间
	 * @param accountId 玩家账号Id
	 * @param accountName 玩家的账号名
	 * @param charId 角色ID
	 * @param charName 玩家的角色名
	 * @param level 玩家等级
	 * @param allianceId 玩家阵营
	 * @param vipLevel 玩家VIP等级
	 * @param reason 原因
	 * @param param 其他参数
	 * @param changeNum 变化数量
	 * @param afterChangeNum 变化后的值
	 */
	public void sendEnergyLog(Human human,
				LogReasons.EnergyLogReason reason,				String param			,int changeNum
			,int afterChangeNum
		) {
		if(isLogServerSwitch){
		udpLoggerClient.sendMessage(new EnergyLog(
			
				timeService.now(),			
				this.regionID,			
				this.serverID,			
				human.getPlayer().getPassportId(),			
				human.getPlayer().getAccount(),			
				human.getHumanGuid(),			
				human.getName(),			
				human.getLevel(),			
				human.getOccupation().getIndex(),			
				human.getVipLevel(),			
				reason.reason,			
				param			
			,changeNum
			,afterChangeNum
		));
		}
	}
	/**
	 * 发送荣誉日志
	 * @param logTime 日志产生时间
	 * @param accountId 玩家账号Id
	 * @param accountName 玩家的账号名
	 * @param charId 角色ID
	 * @param charName 玩家的角色名
	 * @param level 玩家等级
	 * @param allianceId 玩家阵营
	 * @param vipLevel 玩家VIP等级
	 * @param reason 原因
	 * @param param 其他参数
	 * @param changeNum 变化数量
	 * @param afterChangeNum 变化后的值
	 */
	public void sendHonourLog(Human human,
				LogReasons.HonourLogReason reason,				String param			,int changeNum
			,int afterChangeNum
		) {
		if(isLogServerSwitch){
		udpLoggerClient.sendMessage(new HonourLog(
			
				timeService.now(),			
				this.regionID,			
				this.serverID,			
				human.getPlayer().getPassportId(),			
				human.getPlayer().getAccount(),			
				human.getHumanGuid(),			
				human.getName(),			
				human.getLevel(),			
				human.getOccupation().getIndex(),			
				human.getVipLevel(),			
				reason.reason,			
				param			
			,changeNum
			,afterChangeNum
		));
		}
	}
	/**
	 * 发送科技点日志
	 * @param logTime 日志产生时间
	 * @param accountId 玩家账号Id
	 * @param accountName 玩家的账号名
	 * @param charId 角色ID
	 * @param charName 玩家的角色名
	 * @param level 玩家等级
	 * @param allianceId 玩家阵营
	 * @param vipLevel 玩家VIP等级
	 * @param reason 原因
	 * @param param 其他参数
	 * @param changeNum 变化数量
	 * @param afterChangeNum 变化后的值
	 */
	public void sendTechPointLog(Human human,
				LogReasons.TechPointLogReason reason,				String param			,int changeNum
			,int afterChangeNum
		) {
		if(isLogServerSwitch){
		udpLoggerClient.sendMessage(new TechPointLog(
			
				timeService.now(),			
				this.regionID,			
				this.serverID,			
				human.getPlayer().getPassportId(),			
				human.getPlayer().getAccount(),			
				human.getHumanGuid(),			
				human.getName(),			
				human.getLevel(),			
				human.getOccupation().getIndex(),			
				human.getVipLevel(),			
				reason.reason,			
				param			
			,changeNum
			,afterChangeNum
		));
		}
	}
	/**
	 * 发送经验日志
	 * @param logTime 日志产生时间
	 * @param accountId 玩家账号Id
	 * @param accountName 玩家的账号名
	 * @param charId 角色ID
	 * @param charName 玩家的角色名
	 * @param level 玩家等级
	 * @param allianceId 玩家阵营
	 * @param vipLevel 玩家VIP等级
	 * @param reason 原因
	 * @param param 其他参数
	 * @param changeNum 变化数量
	 * @param afterChangeNum 变化后的值
	 */
	public void sendExperienceLog(Human human,
				LogReasons.ExperienceLogReason reason,				String param			,int changeNum
			,int afterChangeNum
		) {
		if(isLogServerSwitch){
		udpLoggerClient.sendMessage(new ExperienceLog(
			
				timeService.now(),			
				this.regionID,			
				this.serverID,			
				human.getPlayer().getPassportId(),			
				human.getPlayer().getAccount(),			
				human.getHumanGuid(),			
				human.getName(),			
				human.getLevel(),			
				human.getOccupation().getIndex(),			
				human.getVipLevel(),			
				reason.reason,			
				param			
			,changeNum
			,afterChangeNum
		));
		}
	}
	/**
	 * 发送技能点日志
	 * @param logTime 日志产生时间
	 * @param accountId 玩家账号Id
	 * @param accountName 玩家的账号名
	 * @param charId 角色ID
	 * @param charName 玩家的角色名
	 * @param level 玩家等级
	 * @param allianceId 玩家阵营
	 * @param vipLevel 玩家VIP等级
	 * @param reason 原因
	 * @param param 其他参数
	 * @param changeNum 变化数量
	 * @param afterChangeNum 变化后的值
	 */
	public void sendSkillPointLog(Human human,
				LogReasons.SkillPointLogReason reason,				String param			,int changeNum
			,int afterChangeNum
		) {
		if(isLogServerSwitch){
		udpLoggerClient.sendMessage(new SkillPointLog(
			
				timeService.now(),			
				this.regionID,			
				this.serverID,			
				human.getPlayer().getPassportId(),			
				human.getPlayer().getAccount(),			
				human.getHumanGuid(),			
				human.getName(),			
				human.getLevel(),			
				human.getOccupation().getIndex(),			
				human.getVipLevel(),			
				reason.reason,			
				param			
			,changeNum
			,afterChangeNum
		));
		}
	}

}