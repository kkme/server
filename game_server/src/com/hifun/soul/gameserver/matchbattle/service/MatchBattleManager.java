package com.hifun.soul.gameserver.matchbattle.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;

import com.hifun.soul.common.constants.Loggers;
import com.hifun.soul.core.orm.IEntity;
import com.hifun.soul.core.util.TimeUtils;
import com.hifun.soul.gamedb.cache.CacheEntry;
import com.hifun.soul.gamedb.cache.ICachableComponent;
import com.hifun.soul.gameserver.battle.BattleType;
import com.hifun.soul.gameserver.common.GameServerAssist;
import com.hifun.soul.gameserver.gameworld.GameWorld;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.matchbattle.MatchBattleRole;
import com.hifun.soul.gameserver.matchbattle.MatchBattleRoleState;
import com.hifun.soul.gameserver.matchbattle.Comparator.MatchBattleRankRoleInfoComparator;
import com.hifun.soul.gameserver.matchbattle.battle.BattleMatcher;
import com.hifun.soul.gameserver.matchbattle.msg.GCAddMatchBattleRole;
import com.hifun.soul.gameserver.matchbattle.msg.GCBattleReport;
import com.hifun.soul.gameserver.matchbattle.msg.GCRemoveMatchBattleRole;
import com.hifun.soul.gameserver.matchbattle.msg.GCUpdateMatchBattleRank;
import com.hifun.soul.gameserver.matchbattle.msg.GCUpdateMatchBattleResult;
import com.hifun.soul.gameserver.matchbattle.msg.GCUpdateMatchBattleRole;
import com.hifun.soul.gameserver.matchbattle.msg.MatchBattleRankRoleInfo;
import com.hifun.soul.gameserver.matchbattle.msg.MatchBattleRoleInfo;
import com.hifun.soul.gameserver.matchbattle.msg.internal.DoBattleMatchMessage;
import com.hifun.soul.gameserver.matchbattle.report.MatchBattleReportService;
import com.hifun.soul.gameserver.matchbattle.template.MatchBattleConfigTemplate;
import com.hifun.soul.gameserver.matchbattle.template.MatchBattleRewardTemplate;

/**
 * 匹配战场景,负责该场景中角色信息的管理以及广播该场景中角色变化消息
 * 
 * @author magicstone
 * 
 */
public class MatchBattleManager implements ICachableComponent {
	private static Logger logger = Loggers.MATCH_BATTLE_LOGGER;
	/** 战斗时显示的条数 */
	private static final int SHORT_STREAKWIN_RANK_SIZE = 3;
	/** 进入大混战的所有角色 */
	private Map<Long, MatchBattleRole> battleRoles = new HashMap<Long, MatchBattleRole>();
	/** 等待匹配的角色 */
	private Map<Long, MatchBattleRole> waitForMatchRoles = new HashMap<Long, MatchBattleRole>();
	/** 排行榜 */
	private List<MatchBattleRankRoleInfo> rankList = new ArrayList<MatchBattleRankRoleInfo>();
	/** 排行排序比较器 */
	private MatchBattleRankRoleInfoComparator rankInfoSorter;
	/** 进入排行的角色id */
	private List<Long> rankRoleIdList = new ArrayList<Long>();
	/** 战斗匹配器 */
	private BattleMatcher battleMatcher;
	/** 匹配战常量配置 */
	private MatchBattleConfigTemplate matchBattleConfig;
	/** 匹配消息 */
	private DoBattleMatchMessage matchMessage;
	/** 是否完成第一轮匹配 */
	private boolean firstMatchComplete = false;
	/** 当前排行榜中的最小连胜次数 */
	private int minStreakWinInRank = 0;	
	private GameWorld gameWorld;
	private CacheEntry<Long,IEntity> cache = new CacheEntry<Long, IEntity>();

	public MatchBattleManager() {
		gameWorld = GameServerAssist.getGameWorld();
		matchBattleConfig = GameServerAssist.getMatchBattleTemplateManager().getMatchBattleConfig();
		battleMatcher = new BattleMatcher();
		battleMatcher.setLevelSpan(matchBattleConfig.getMatchLevelSpan());
		rankInfoSorter = new MatchBattleRankRoleInfoComparator();
	}

	/**
	 * 开始匹配定时任务
	 */
	public void startMatchSchedule(long delay) {
		matchMessage = new DoBattleMatchMessage();
		gameWorld.scheduleOnece(matchMessage, delay);
	}

	/**
	 * 进入匹配战
	 * 
	 * @param role
	 */
	public void enterBattleScene(MatchBattleRole role) {
		role.setBattleState(MatchBattleRoleState.READY);
		long currentRoleId = role.getRoleId();		
		if (!battleRoles.containsKey(currentRoleId)) {
			battleRoles.put(currentRoleId, role);
			// TODO:WIll@匹配战 广播成员状态更新消息【是否有压力问题，是否需要定时器去搞??】
			GCAddMatchBattleRole gcMsg = new GCAddMatchBattleRole();
			MatchBattleRoleInfo[] joinRoles = new MatchBattleRoleInfo[1];
			joinRoles[0] = role.convertToMatchBattleRoleInfo();
			gcMsg.setJoinRoles(joinRoles);
			sendAddRoleMessage(currentRoleId, gcMsg);
		} else {
			battleRoles.put(currentRoleId, role);
		}
		this.saveMatchBattleRoleInfo(role);
		logger.debug(role.getRoleName()+"("+role.getRoleId()+")"+" enter match battle scene");
	}

	/**
	 * 发送增加参战人员的消息
	 * @param newRoleId
	 * @param message
	 */
	private void sendAddRoleMessage(long newRoleId, GCAddMatchBattleRole message) {
		// 不给自己发新增消息
		for (MatchBattleRole role : battleRoles.values()) {
			if (role.getRoleId() != newRoleId) {
				role.getHuman().sendMessage(message);
			}
		}
	}

	/**
	 * 发送人员离开匹配战消息
	 * @param message
	 */
	private void sendRemoveRoleMessage(GCRemoveMatchBattleRole message) {
		for (MatchBattleRole role : battleRoles.values()) {
			role.getHuman().sendMessage(message);
		}
	}

	/**
	 * 发送参战人员状态更新消息
	 * @param message
	 */
	public void sendUpdateRoleMessage(GCUpdateMatchBattleRole message) {
		for (MatchBattleRole role : battleRoles.values()) {
			role.getHuman().sendMessage(message);
		}
	}

	/**
	 * 更新角色的战斗状态
	 * 
	 * @param role
	 */
	public void battleRoleStateChange(MatchBattleRole[] roles) {
		List<MatchBattleRoleInfo> battleRoleInfos = new ArrayList<MatchBattleRoleInfo>();
		for (MatchBattleRole role : roles) {
			if (battleRoles.containsKey(role.getRoleId())) {				
				updateStreakWinRank(role);				
				this.saveMatchBattleRoleInfo(role);
				battleRoleInfos.add(role.convertToMatchBattleRoleInfo());
				if (role.getBattleState() == MatchBattleRoleState.WAIT_MATCH) {
					waitForMatchRoles.put(role.getRoleId(), role);
					if (firstMatchComplete && waitForMatchRoles.size() >= matchBattleConfig.getWaitMatchQueneSize()) {
						// 如果活动还在准备阶段,则可以继续添加,不开始匹配
						matchMessage.cancel();
						beginMatch();
					}
				}
				else if(waitForMatchRoles.containsKey(role.getRoleId())){
					waitForMatchRoles.remove(role.getRoleId());
				}
			}
		}
		if (battleRoleInfos.size() > 0) {
			GCUpdateMatchBattleRole gcMsg = new GCUpdateMatchBattleRole();
			MatchBattleRoleInfo[] changedStateRoles = battleRoleInfos.toArray(new MatchBattleRoleInfo[0]);
			gcMsg.setUpdateRoles(changedStateRoles);
			sendUpdateRoleMessage(gcMsg);
		}
	}

	/**
	 * 离开匹配战
	 * 
	 * @param roleId
	 */
	public void leaveBattleScene(Long roleId) {
		if (battleRoles.containsKey(roleId)) {
			MatchBattleRole role = battleRoles.get(roleId);
			role.setAutoJoinBattle(false);
			role.setBattleState(MatchBattleRoleState.NOT_JOIN);
			battleRoles.remove(roleId);
			GCRemoveMatchBattleRole gcMsg = new GCRemoveMatchBattleRole();
			gcMsg.setLeaveRolesId(new long[] { roleId });
			sendRemoveRoleMessage(gcMsg);
			if(waitForMatchRoles.containsKey(roleId)){
				waitForMatchRoles.remove(roleId);
			}
		}
	}

	/**
	 * 获取匹配战场景中的所有角色
	 * @return
	 */
	public Map<Long, MatchBattleRole> getAllBattleRoles() {
		return battleRoles;
	}

	/**
	 * 开始匹配
	 */
	public void beginMatch() {
		startMatchSchedule(matchBattleConfig.getMatchTurnTime()*TimeUtils.SECOND);
		if (waitForMatchRoles.size() == 0) {
			return;
		}
		Map<Long, MatchBattleRoleInfo> battleRoleInfos = new HashMap<Long, MatchBattleRoleInfo>();
		for (MatchBattleRole role : waitForMatchRoles.values()) {
			role.setBattleState(MatchBattleRoleState.IN_BATTLE);
			battleRoleInfos.put(role.getRoleId(), role.convertToMatchBattleRoleInfo());
		}
		battleMatcher.addRoles(waitForMatchRoles);
		waitForMatchRoles.clear();
		firstMatchComplete = true;
		MatchBattleRole outOfTurnRole = battleMatcher.match();
		if (outOfTurnRole != null) {
			roleOutOfTurn(outOfTurnRole);
			battleRoleInfos.get(outOfTurnRole.getRoleId()).setBattleState(outOfTurnRole.getBattleState().getIndex());			
		}
		if (battleRoleInfos.size() > 0) {
			GCUpdateMatchBattleRole gcMsg = new GCUpdateMatchBattleRole();
			MatchBattleRoleInfo[] changedStateRoles = battleRoleInfos.values().toArray(new MatchBattleRoleInfo[0]);
			gcMsg.setUpdateRoles(changedStateRoles);
			sendUpdateRoleMessage(gcMsg);
		}
	}

	/**
	 * 匹配轮空处理
	 * @param outOfTurnRole
	 */
	public void roleOutOfTurn(MatchBattleRole outOfTurnRole) {
		if (outOfTurnRole == null) {
			return;
		}		
		MatchBattleRewardTemplate template = GameServerAssist.getMatchBattleTemplateManager().getMatchBattleRewardTemplate(outOfTurnRole.getLevel());
		int coinReward = template.getOutOfTurnCoin();
		outOfTurnRole.addTotalRewardCoin(coinReward);
		if (outOfTurnRole.isAutoJoinBattle()) {
			outOfTurnRole.setBattleState(MatchBattleRoleState.WAIT_MATCH);
			waitForMatchRoles.put(outOfTurnRole.getRoleId(), outOfTurnRole);
		} else {
			outOfTurnRole.setBattleState(MatchBattleRoleState.READY);			
		}
		// 发送轮空战报
		sendOutOfTurnBattleReport(outOfTurnRole,coinReward);		
		this.saveMatchBattleRoleInfo(outOfTurnRole);
		logger.debug(outOfTurnRole.getRoleName()+"("+outOfTurnRole.getRoleId()+")"+" out of turn in match battle");
	}

	/**
	 * 发送轮空战报
	 * @param outOfTurnRole
	 * @param coinReward
	 */
	private void sendOutOfTurnBattleReport(MatchBattleRole outOfTurnRole,int coinReward) {
		String selfReport = MatchBattleReportService.generateSelfOutOfTurnReport(coinReward);
		sendSelfBattleResultMessage(outOfTurnRole,selfReport);
		String content = MatchBattleReportService.generateOutOfTurnReport(outOfTurnRole,coinReward);
		sendBattleReport(content);
	}
	
	/**
	 * 发送个人的战斗结果消息
	 * @param self
	 * @param reportContent
	 */
	private void sendSelfBattleResultMessage(MatchBattleRole self,String reportContent){
		GCUpdateMatchBattleResult gcMsg = new GCUpdateMatchBattleResult();		
		gcMsg.setCoinReward(self.getCoinReward());
		gcMsg.setHonourReward(self.getHonourReward());		
		gcMsg.setConsecutiveWinCount(self.getConsecutiveWinCount());
		gcMsg.setContent(reportContent);
		gcMsg.setLoseCount(self.getLoseCount());
		gcMsg.setMaxConsecutiveWinCount(self.getMaxConsecutiveWinCount());
		gcMsg.setWinCount(self.getWinCount());
		self.getHuman().sendMessage(gcMsg);
	}

	/**
	 * 清空场景中的角色
	 */
	public void cleanBattleRoles() {
		if(matchMessage != null){
			matchMessage.cancel();
		}
		for (MatchBattleRole role : battleRoles.values()) {
			// 退出战斗
			Human otherHuman = gameWorld.getSceneHumanManager().getHumanByGuid(role.getRoleId());
			if (otherHuman != null) {
				if (otherHuman.isInBattleState()
						&& otherHuman.getBattleContext().getBattle().getBattleType() == BattleType.PVP_MATCH_BATTLE) {
					otherHuman.getBattleContext().getBattle().onBattleUnitQuit(otherHuman);
				}
			}			
		}
		battleRoles.clear();
		waitForMatchRoles.clear();
		rankList.clear();
		firstMatchComplete = false;
		this.minStreakWinInRank = 0;
	}

	

	/**
	 * 更新连胜排行
	 * 
	 * @param role
	 */
	private void updateStreakWinRank(MatchBattleRole role) {		
		if(role.getMaxConsecutiveWinCount()<2){
			return;
		}
		if(rankRoleIdList.contains(role.getRoleId())){
			//是否已经在榜单中，是则进行重新排序即可
			MatchBattleRankRoleInfo rankInfo=null;
			for (int i = 0; i < rankList.size(); i++) {
				if(rankList.get(i).getRoleId() == role.getRoleId()){
					rankInfo = rankList.get(i);
					break;
				}
			}
			if(rankInfo == null){
				rankRoleIdList.remove(role.getRoleId());
				return;
			}
			if(rankInfo.getConsecutiveWinCount()<role.getMaxConsecutiveWinCount()){
				rankInfo.setConsecutiveWinCount(role.getMaxConsecutiveWinCount());
				//重新排序
				Collections.sort(rankList, rankInfoSorter);
				sendUpdateStreakWinRankMessage();
			}
		}
		else if (role.getMaxConsecutiveWinCount() > minStreakWinInRank) {
			int rankSize = rankList.size();
			if (rankSize == 0) {
				rankList.add(role.converToRankRole());
				rankRoleIdList.add(role.getRoleId());
			} else {
				boolean inserted = false;
				for (int i = 0; i < rankList.size(); i++) {
					if (role.getMaxConsecutiveWinCount() > rankList.get(i).getConsecutiveWinCount()) {
						rankList.add(i, role.converToRankRole());
						rankRoleIdList.add(role.getRoleId());
						inserted = true;
						break;
					}
				}
				if(!inserted){
					rankList.add(role.converToRankRole());
					rankRoleIdList.add(role.getRoleId());
					inserted = true;
				}
				if (inserted && rankSize >= SHORT_STREAKWIN_RANK_SIZE) {
					rankRoleIdList.add(rankList.remove(rankList.size() - 1).getRoleId());
					minStreakWinInRank = rankList.get(rankList.size() - 1).getConsecutiveWinCount();
				}				
			}
			sendUpdateStreakWinRankMessage();
		}
	}

	/**
	 * 发送连胜排行更新消息
	 */
	private void sendUpdateStreakWinRankMessage() {
		GCUpdateMatchBattleRank gcMsg = new GCUpdateMatchBattleRank();
		MatchBattleRankRoleInfo[] updateRankInfos;
		if (SHORT_STREAKWIN_RANK_SIZE > rankList.size()) {
			updateRankInfos = new MatchBattleRankRoleInfo[rankList.size()];
		} else {
			updateRankInfos = new MatchBattleRankRoleInfo[SHORT_STREAKWIN_RANK_SIZE];
		}
		for (int i = 0; i < updateRankInfos.length; i++) {
			updateRankInfos[i] = rankList.get(i);
		}
		gcMsg.setRankInfo(updateRankInfos);
		for (MatchBattleRole role : this.battleRoles.values()) {
			role.getHuman().sendMessage(gcMsg);
		}
	}

	/**
	 * 发送全体战报
	 * @param reportContent
	 */
	public void sendBattleReport(String reportContent) {
		GCBattleReport gcMsg = new GCBattleReport();
		gcMsg.setContent(reportContent);
		for (MatchBattleRole role : battleRoles.values()) {
			role.getHuman().sendMessage(gcMsg);
		}
	}
	
	/**
	 * 获取匹配战场景中显示的连胜排行
	 * @return
	 */
	public List<MatchBattleRankRoleInfo> getShortStreakWinRankList(){
		return this.rankList;
	}

	@Override
	public List<IEntity> getUpdateEntities() {		
		return cache.getAllUpdateData();
	}

	@Override
	public List<IEntity> getDeleteEntities() {
		return cache.getAllDeleteData();
	}
	
	/**
	 * 持久化角色的参战数据
	 * @param role
	 */
	public void saveMatchBattleRoleInfo(MatchBattleRole role){
		if(role.isNeedUpdate()){
			cache.addUpdate(role.getRoleId(), role.toEntity());
			role.setNeedUpdate(false);
		}
	}
}
