package com.hifun.soul.gameserver.matchbattle.battle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;

import com.hifun.soul.common.constants.Loggers;
import com.hifun.soul.gameserver.cd.CdType;
import com.hifun.soul.gameserver.cd.manager.HumanCdManager;
import com.hifun.soul.gameserver.common.GameServerAssist;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.matchbattle.MatchBattleRole;
import com.hifun.soul.gameserver.matchbattle.msg.internal.AutoJoinNextMatchScheduleMessage;
import com.hifun.soul.gameserver.player.state.PlayerState;

/**
 * 匹配器
 * 
 * @author magicstone
 * 
 */
public class BattleMatcher {
	private static Logger logger = Loggers.MATCH_BATTLE_LOGGER;
	/** 等待匹配的角色 */
	private Map<Long, MatchBattleRole> waitForMatchRoles = new HashMap<Long, MatchBattleRole>();
	/** 按照等级段进行的角色分组 */
	private Map<Integer, List<MatchBattleRole>> groupRoles = new HashMap<Integer, List<MatchBattleRole>>();
	/** 同等级匹配剩余的角色 */
	private List<MatchBattleRole> unmatchedInGroupRoles = new ArrayList<MatchBattleRole>();
	/** 等级段跨度 */
	private int levelSpan = 10;

	public int getLevelSpan() {
		return levelSpan;
	}

	public void setLevelSpan(int levelSpan) {
		this.levelSpan = levelSpan;
	}

	public void addRoles(Map<? extends Long, ? extends MatchBattleRole> roles) {
		waitForMatchRoles.putAll(roles);
	}

	/**
	 * 匹配角色进行战斗，返回轮空角色
	 * 
	 * @return
	 */
	public MatchBattleRole match() {
		// 等级段整理
		for (MatchBattleRole role : waitForMatchRoles.values()) {
			// 如果角色在线, 而且正在战斗中, 则匹配失败;
			if (role.getHuman().getPlayer().getState() == PlayerState.BATTLING
					|| role.getHuman().getPlayer().getState() == PlayerState.AUTOBATTLEING
					) {
				//踢出匹配战场景
				makeIllegalRoleLeave(role);
				continue;
			}					
			int groupIndex = role.getLevel() / levelSpan;
			if (groupRoles.containsKey(groupIndex)) {
				groupRoles.get(groupIndex).add(role);
			} else {
				List<MatchBattleRole> roleList = new ArrayList<MatchBattleRole>();
				roleList.add(role);
				groupRoles.put(groupIndex, roleList);
			}
		}
		// 开始匹配
		int groupSize = 0;
		int index = 0;
		for (List<MatchBattleRole> group : groupRoles.values()) {
			groupSize = group.size();
			if (groupSize % 2 > 0) {
				unmatchedInGroupRoles.add(group.remove(groupSize - 1));
				groupSize--;
			}
			for (index = 0; index < groupSize; index += 2) {
				MatchBattleRole beChallengeRole = group.get(index);
				MatchBattleRole challengeRole = group.get(index + 1);
				addBattleCdAndNextAutoMatch(challengeRole);
				addBattleCdAndNextAutoMatch(beChallengeRole);
				GameServerAssist.getBattleManager().pvpMatchBattleEnter(challengeRole.getHuman(),
						beChallengeRole.getRoleId(), new MatchBattleCallback(challengeRole, beChallengeRole));
				challengeRole.setInBattleScene(true);
				beChallengeRole.setInBattleScene(true);
				logger.debug("match battle:match success " + challengeRole.getRoleName() + " challenge "
						+ beChallengeRole.getRoleName());
			}
		}
		index = 0;
		groupSize = unmatchedInGroupRoles.size();
		MatchBattleRole outOfTurnRole = null;
		if (groupSize > 0 && groupSize % 2 > 0) {
			outOfTurnRole = unmatchedInGroupRoles.remove(groupSize - 1);
			groupSize--;
		}
		if (groupSize > 1) {
			for (index = 0; index < groupSize; index += 2) {
				MatchBattleRole beChallengeRole = unmatchedInGroupRoles.get(index);
				MatchBattleRole challengeRole = unmatchedInGroupRoles.get(index + 1);
				addBattleCdAndNextAutoMatch(challengeRole);
				addBattleCdAndNextAutoMatch(beChallengeRole);
				GameServerAssist.getBattleManager().pvpMatchBattleEnter(challengeRole.getHuman(),
						beChallengeRole.getRoleId(), new MatchBattleCallback(challengeRole, beChallengeRole));
				challengeRole.setInBattleScene(true);
				beChallengeRole.setInBattleScene(true);
				logger.debug("match battle:match success " + challengeRole.getRoleName() + " challenge "
						+ beChallengeRole.getRoleName());
			}
		}
		// 清理待处理的数据
		cleanData();
		return outOfTurnRole;
	}

	/**
	 * 情况匹配的数据
	 */
	private void cleanData() {
		waitForMatchRoles.clear();
		groupRoles.clear();
		unmatchedInGroupRoles.clear();
	}

	/**
	 * 添加匹配cd和自动参战的定时任务
	 * 
	 * @param role
	 */
	private void addBattleCdAndNextAutoMatch(MatchBattleRole role) {
		Human human = role.getHuman();
		HumanCdManager cdManager = human.getHumanCdManager();
		long cd = cdManager.getSpendTime(CdType.MATCH_BATTLE_CD, 0);
		cdManager.addCd(CdType.MATCH_BATTLE_CD, cd);
		cdManager.snapCdQueueInfo(CdType.MATCH_BATTLE_CD);
		if (role.isAutoJoinBattle()) {
			GameServerAssist.getGameWorld().scheduleOnece(new AutoJoinNextMatchScheduleMessage(role), cd);
		}
	}
	
	private void makeIllegalRoleLeave(MatchBattleRole role){
		GameServerAssist.getMatchBattleService().leaveMatchBattleScene(role.getHuman());
	}

}
