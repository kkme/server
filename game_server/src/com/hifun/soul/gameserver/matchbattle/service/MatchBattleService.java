package com.hifun.soul.gameserver.matchbattle.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hifun.soul.common.IInitializeRequired;
import com.hifun.soul.common.constants.LangConstants;
import com.hifun.soul.common.constants.Loggers;
import com.hifun.soul.common.constants.SystemMessageType;
import com.hifun.soul.core.i18n.SysLangService;
import com.hifun.soul.core.orm.IEntity;
import com.hifun.soul.core.util.TimeUtils;
import com.hifun.soul.gamedb.cache.CacheManager;
import com.hifun.soul.gamedb.cache.ICachableComponent;
import com.hifun.soul.gameserver.activity.ActivityType;
import com.hifun.soul.gameserver.activity.ITimingActivityManager;
import com.hifun.soul.gameserver.bulletin.manager.BulletinManager;
import com.hifun.soul.gameserver.cd.CdType;
import com.hifun.soul.gameserver.cd.manager.HumanCdManager;
import com.hifun.soul.gameserver.common.GameServerAssist;
import com.hifun.soul.gameserver.common.msg.GCSystemMessage;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.matchbattle.MatchBattleRole;
import com.hifun.soul.gameserver.matchbattle.MatchBattleRoleState;
import com.hifun.soul.gameserver.matchbattle.MatchBattleServiceStatus;
import com.hifun.soul.gameserver.matchbattle.Comparator.MatchBattleRoleComparator;
import com.hifun.soul.gameserver.matchbattle.msg.GCJoinMatchBattleScene;
import com.hifun.soul.gameserver.matchbattle.msg.MatchBattleRankRoleInfo;
import com.hifun.soul.gameserver.matchbattle.msg.MatchBattleRoleInfo;
import com.hifun.soul.gameserver.matchbattle.msg.internal.AutoJoinNextMatchScheduleMessage;
import com.hifun.soul.gameserver.matchbattle.template.MatchBattleConfigTemplate;
import com.hifun.soul.gameserver.rank.MatchBattleRank;
import com.hifun.soul.gameserver.rank.model.MatchBattleRankData;
import com.hifun.soul.gameserver.reward.RewardType;

/**
 * 魔石大混战
 * @author magicstone
 *
 */
@Component
public class MatchBattleService implements ITimingActivityManager,IInitializeRequired,ICachableComponent{
	private static Logger logger = Loggers.MATCH_BATTLE_LOGGER;
	/** 参加了本次大混战的所有角色 */
	private Map<Long,MatchBattleRole> allBattleRoles = new HashMap<Long,MatchBattleRole>();	
	/** 匹配战状态 */	
	private MatchBattleServiceStatus status;
	/** 匹配战场景 */
	private MatchBattleManager matchBattleManager ;
	/** 匹配战排行榜 */
	private MatchBattleRank matchBattleRank;	
	/** 匹配战常量配置 */
	private MatchBattleConfigTemplate matchBattleConfig;
	/** 获得开始时间 */
	private long beginTime;
	/** 匹配战排行通告，需要发送到聊天框的系统消息 */
	private String sysStreakWinRankContent ="";
	@Autowired
	private CacheManager cacheManager;
	@Autowired
	private BulletinManager bulletinManager;
	@Autowired
	private SysLangService sysLangService;
	/** 排行排序比较器 */
	private MatchBattleRoleComparator rankRoleSorter;
	
	@Override
	public void init(){
		status = MatchBattleServiceStatus.INIT;
		matchBattleManager = new MatchBattleManager();
		matchBattleRank = new MatchBattleRank();
		matchBattleConfig = GameServerAssist.getMatchBattleTemplateManager().getMatchBattleConfig();
		cacheManager.registerOtherCachableComponent(this);
		rankRoleSorter = new MatchBattleRoleComparator();
	}
	
	public Collection<MatchBattleRole> getAllBattleRoles(){
		return matchBattleManager.getAllBattleRoles().values();
	}
	
	@Override
	public void onStart() {
		status = MatchBattleServiceStatus.READY;
		matchBattleManager.startMatchSchedule(matchBattleConfig.getBeginWaitTime()*TimeUtils.SECOND);
		beginTime = GameServerAssist.getSystemTimeService().now();
		//发送开始公告
		String closeContent = sysLangService.read(LangConstants.MATCH_BATTLE_OPEN);
		bulletinManager.sendSystemBulletin(closeContent);
		logger.info("match battle start");
	}

	@Override
	public void onStop() {
		//更新状态为已结束
		status = MatchBattleServiceStatus.FINISH;		
		//更新排行榜 发放排行奖励
		updateRank();		
		//清空场景中的角色,通知正在参与活动的玩家退出战斗
		matchBattleManager.cleanBattleRoles();
		//发送结束公告
		String closeContent = sysLangService.read(LangConstants.MATCH_BATTLE_CLOSE);
		bulletinManager.sendSystemBulletin(closeContent);		
		//发送排名消息到聊天框		
		GCSystemMessage sysMsg = new GCSystemMessage();
		sysMsg.setContent(sysStreakWinRankContent);
		sysMsg.setShowType(SystemMessageType.CHAT.getIndex());
		GameServerAssist.getGameWorld().boradcast(sysMsg);
		//清空活动参与角色
		resetAllBattleRoleData();	
		logger.info("match battle closed");
	}
	
	/**
	 * 活动结束时清空所有参加过本次活动的角色
	 */
	private void resetAllBattleRoleData(){
		for (MatchBattleRole role : allBattleRoles.values()) {			
			// 更新状态
			role.resetData();
			saveBattleRole(role);
		}
		allBattleRoles.clear();	
	}
	
	/**
	 * 生成活动结束时广播的消息
	 * @param rankRoles
	 */
	private void genSysStreakWinContent(List<MatchBattleRole> rankRoles){
		String content = sysLangService.read(LangConstants.MATCH_BATTLE_STREAK_WIN_RANK_HEAD);
		if(rankRoles.size()>0){			
			for(int i=0;i<rankRoles.size();i++){
				MatchBattleRole role = rankRoles.get(i);
				content+=sysLangService.read(LangConstants.MATCH_BATTLE_STREAK_WIN_RANK_MSG,
						i+1,
						role.getHuman().getLinkedName(),
						role.getMaxConsecutiveWinCount());
			}			
		}
		else{
			content = sysLangService.read(LangConstants.MATCH_BATTLE_CLOSE);
		}
		sysStreakWinRankContent = content;
	}
	
	/**
	 * 活动结束时更新排行榜,并生成广播所用的排行信息
	 */
	private void updateRank(){		
		List<MatchBattleRole> allRoles = new ArrayList<MatchBattleRole>(allBattleRoles.values());
		Collections.sort(allRoles, rankRoleSorter);
		List<MatchBattleRankData> rankDataList = new ArrayList<MatchBattleRankData>();
		int size = matchBattleConfig.getRankSize()<allRoles.size() ? matchBattleConfig.getRankSize() : allRoles.size();
		int hasRewardRankSize = GameServerAssist.getMatchBattleTemplateManager().getHasRewardRankSize();
		hasRewardRankSize = hasRewardRankSize<size ? hasRewardRankSize : size;		
		List<MatchBattleRole> rankRoles = new ArrayList<MatchBattleRole>();
		for (int i = 0; i < size; i++) {
			MatchBattleRole role = allRoles.get(i);
			if (role.getMaxConsecutiveWinCount() < 2) {
				break;
			}
			if(i<hasRewardRankSize){
				rankRoles.add(role);
			}
			role.setStreakWinRank(i + 1);
			if (role.getMaxConsecutiveWinCount() > 0
					&& GameServerAssist.getMatchBattleTemplateManager().getRankRewardItemId(i + 1) != 0) {// 判断是否有奖励要推送
				GameServerAssist.getRewardService().sendAddCommonRewardMessage(role.getHuman(),
						RewardType.MATCH_BATTLE_STREAK_WIN_RANK_REWARD);
			}
			MatchBattleRankData rankData = new MatchBattleRankData();
			rankData.setHumanGuid(allRoles.get(i).getRoleId());
			rankData.setHumanName(allRoles.get(i).getRoleName());
			rankData.setMaxStreakWinCount(allRoles.get(i).getMaxConsecutiveWinCount());
			rankData.setLevel(allRoles.get(i).getLevel());
			rankData.setOccupation(allRoles.get(i).getHuman().getOccupation().getIndex());
			rankDataList.add(rankData);
		}
		matchBattleRank.updateRankData(rankDataList);
		genSysStreakWinContent(rankRoles);		
	}
	
	/**
	 * 进入匹配战场景
	 * @param newRole
	 */
	public void enterMatchBattle(MatchBattleRole newRole){	
		//检查活动状态
		if(status == MatchBattleServiceStatus.READY || status == MatchBattleServiceStatus.RUNNING){			
			newRole.setAutoJoinBattle(false);
			if(!allBattleRoles.containsKey(newRole.getRoleId())){
				newRole.resetData();				
			}else{
				newRole.setMaxConsecutiveWinTimeStamp(allBattleRoles.get(newRole.getRoleId()).getMaxConsecutiveWinTimeStamp());
			}
			allBattleRoles.put(newRole.getRoleId(), newRole);	
			matchBattleManager.enterBattleScene(newRole);
			sendEnterMatchSceneMessage(newRole);			
		}		
	}
	
	/**
	 * 离开匹配战场景
	 * @param human
	 */
	public void leaveMatchBattleScene(Human human){
		MatchBattleRole role = searchMatchBattleRole(human.getHumanGuid());
		if(role != null){
			role.setAutoJoinBattle(false);
			if(role.getAutoJoinNextMatchScheduleMessage() != null){
				role.getAutoJoinNextMatchScheduleMessage().cancel();
			}
			role.setAutoJoinNextMatchScheduleMessage(null);
			matchBattleManager.leaveBattleScene(human.getHumanGuid());
		}
	}
	
	/**
	 * 获取匹配战活动的状态
	 * @return
	 */
	public MatchBattleServiceStatus getStatus(){
		return status;
	}
	
	/**
	 * 从已经参加过的角色中查找角色信息
	 * @param roleId
	 * @return
	 */
	public MatchBattleRole searchMatchBattleRole(long roleId){
		return allBattleRoles.get(roleId);
	}
	
	/**
	 * 参战的角色属性变化通知
	 * @param role
	 */
	public void battleRoleStateChange(MatchBattleRole role){
		matchBattleManager.battleRoleStateChange(new MatchBattleRole[]{role});
	}
	
	/**
	 * 参战的角色属性变化通知
	 * @param roles
	 */
	public void battleRoleStateChange(MatchBattleRole[] roles){
		matchBattleManager.battleRoleStateChange(roles);
	}
	
	/**
	 * 持久化参战角色数据
	 * @param role
	 */
	public void saveBattleRole(MatchBattleRole role){		
		matchBattleManager.saveMatchBattleRoleInfo(role);
	}
	
	
	/**
	 * 开始进行匹配
	 */
	public void beginMatch(){
		//检查活动状态
		if(status == MatchBattleServiceStatus.READY){
			status = MatchBattleServiceStatus.RUNNING;
		}
		else if(status != MatchBattleServiceStatus.RUNNING){
			return;
		}
		matchBattleManager.beginMatch();
	}

	/**
	 * 发送全体战报
	 * @param reportContent
	 */
	public void sendBattleReport(String reportContent) {
		matchBattleManager.sendBattleReport(reportContent);
	}
	
	/**
	 * 进入战斗超时的处理
	 * @param firstRole
	 * @param secondRole
	 */
	public void onEnterBattleFailed(MatchBattleRole firstRole, MatchBattleRole secondRole) {
		firstRole.setInBattleScene(false);
		secondRole.setInBattleScene(false);
		if(firstRole.isAutoJoinBattle()){
			firstRole.setBattleState(MatchBattleRoleState.WAIT_MATCH);
		}
		else{
			firstRole.setBattleState(MatchBattleRoleState.READY);
		}
		if(secondRole.isAutoJoinBattle()){
			secondRole.setBattleState(MatchBattleRoleState.WAIT_MATCH);
		}
		else{
			secondRole.setBattleState(MatchBattleRoleState.READY);
		}
		//更新状态
		matchBattleManager.battleRoleStateChange(new MatchBattleRole[]{firstRole,secondRole});
	}
	
	/**
	 * 角色进入匹配战活动时更新匹配战的所有信息
	 * @param matchBattleRole
	 */
	private void sendEnterMatchSceneMessage(MatchBattleRole matchBattleRole){
		Collection<MatchBattleRole> allRoles = getAllBattleRoles();
		MatchBattleRoleInfo[] allBattleRoleInfos = new MatchBattleRoleInfo[allRoles.size()];
		int index=0;
		for(MatchBattleRole battleRole : getAllBattleRoles()){
			allBattleRoleInfos[index] = new MatchBattleRoleInfo();
			allBattleRoleInfos[index].setRoleId(battleRole.getRoleId());
			allBattleRoleInfos[index].setRoleName(battleRole.getRoleName());
			allBattleRoleInfos[index].setBattleState(battleRole.getBattleState().getIndex());
			index++;
		}
		MatchBattleConfigTemplate battleMatchConfig = GameServerAssist.getMatchBattleTemplateManager().getMatchBattleConfig();
		GCJoinMatchBattleScene gcMsg = new GCJoinMatchBattleScene();
		gcMsg.setCoinReward(matchBattleRole.getCoinReward());
		gcMsg.setConsecutiveWinCount(matchBattleRole.getConsecutiveWinCount());
		gcMsg.setEncourageCoinCost(battleMatchConfig.getEncourageCoinCost());
		gcMsg.setEncourageCrystalCost(battleMatchConfig.getEncourageCrystalCost());
		gcMsg.setEncourageForgeStoneCost(battleMatchConfig.getForgeStoneEncourageCost());
		gcMsg.setEncourageRate(matchBattleRole.getEncourageRate()/100);
		gcMsg.setHonourReward(matchBattleRole.getHonourReward());		
		gcMsg.setLoseCount(matchBattleRole.getLoseCount());
		gcMsg.setMaxConsecutiveWinCount(matchBattleRole.getMaxConsecutiveWinCount());
		gcMsg.setRankInfo(matchBattleManager.getShortStreakWinRankList().toArray(new MatchBattleRankRoleInfo[0]));
		gcMsg.setRemainTime((int)(GameServerAssist.getGlobalActivityManager().getRemainTime(ActivityType.MATCH_BATTLE)/TimeUtils.SECOND));
		gcMsg.setRoleInfo(allBattleRoleInfos);
		if(status != MatchBattleServiceStatus.READY){
			gcMsg.setWaitCDTime(0);
		}
		else{
			int waitCd = battleMatchConfig.getBeginWaitTime() - (int)((GameServerAssist.getSystemTimeService().now() - beginTime)/TimeUtils.SECOND);
			waitCd = waitCd>0 ? waitCd : 0;
			gcMsg.setWaitCDTime(waitCd);
		}
		gcMsg.setWinCount(matchBattleRole.getWinCount());
		matchBattleRole.getHuman().sendMessage(gcMsg);
	}

	@Override
	public List<IEntity> getUpdateEntities() {		
		return matchBattleManager.getUpdateEntities();
	}

	@Override
	public List<IEntity> getDeleteEntities() {		
		return matchBattleManager.getDeleteEntities();
	}

	/**
	 * 更新自动参战状态
	 * @param humanGuid
	 * @param isAutoJoinBattle
	 */
	public void updateAutoMatchSetting(long humanGuid,boolean isAutoJoinBattle) {
		MatchBattleRole role = searchMatchBattleRole(humanGuid);
		if(role == null){
			return;
		}
		role.setAutoJoinBattle(isAutoJoinBattle);
		if(!isAutoJoinBattle && role.getAutoJoinNextMatchScheduleMessage() !=null){
			role.getAutoJoinNextMatchScheduleMessage().cancel();
			role.setAutoJoinNextMatchScheduleMessage(null);
		}
		else if(isAutoJoinBattle && role.getAutoJoinNextMatchScheduleMessage()==null){
			HumanCdManager cdManager = role.getHuman().getHumanCdManager();
			if(!cdManager.canAddCd(CdType.MATCH_BATTLE_CD, 0)){
				long cdTime = cdManager.getCdInfo(CdType.MATCH_BATTLE_CD).getEndTime()-GameServerAssist.getSystemTimeService().now();
				if(cdTime>0){
					GameServerAssist.getGameWorld().scheduleOnece(new AutoJoinNextMatchScheduleMessage(role), cdTime);
				}				
			}
			else if(role.getBattleState() == MatchBattleRoleState.READY){
				role.setBattleState(MatchBattleRoleState.WAIT_MATCH);
				this.battleRoleStateChange(role);
			}
		}
		this.saveBattleRole(role);		
	}
	
	
}
