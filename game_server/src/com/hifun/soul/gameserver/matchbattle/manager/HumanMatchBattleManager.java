package com.hifun.soul.gameserver.matchbattle.manager;

import com.hifun.soul.common.LogReasons.ItemLogReason;
import com.hifun.soul.common.constants.LangConstants;
import com.hifun.soul.gamedb.callback.IDBCallback;
import com.hifun.soul.gamedb.entity.HumanEntity;
import com.hifun.soul.gamedb.entity.HumanMatchBattleEntity;
import com.hifun.soul.gameserver.activity.Activity;
import com.hifun.soul.gameserver.activity.ActivityType;
import com.hifun.soul.gameserver.activity.IHumanActivityManager;
import com.hifun.soul.gameserver.bag.BagType;
import com.hifun.soul.gameserver.common.GameServerAssist;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.human.IHumanPersistenceManager;
import com.hifun.soul.gameserver.human.ILoginManager;
import com.hifun.soul.gameserver.matchbattle.MatchBattleRole;
import com.hifun.soul.gameserver.matchbattle.MatchBattleRoleState;
import com.hifun.soul.gameserver.matchbattle.service.MatchBattleService;
import com.hifun.soul.gameserver.reward.RewardType;

public class HumanMatchBattleManager implements IHumanActivityManager, IHumanPersistenceManager, ILoginManager {
	private Human human;
	private MatchBattleRole matchBattleRole;
	private MatchBattleService matchBattleService;

	public HumanMatchBattleManager(Human human) {
		this.human = human;
		matchBattleService = GameServerAssist.getMatchBattleService();
		this.human.registerPersistenceManager(this);
		this.human.registerLoginManager(this);
	}

	@Override
	public ActivityType getActivityType() {
		return ActivityType.MATCH_BATTLE;
	}

	@Override
	public void onOpenClick() {
		// 判断功能是否开放
		Activity activity = GameServerAssist.getGlobalActivityManager().getActivity(getActivityType());
		if(activity == null || activity.getOpenLevel()>human.getLevel()){
			return;
		}
		if (matchBattleRole == null) {
			matchBattleRole = new MatchBattleRole(human);
			HumanMatchBattleEntity matchBattleRoleEntity = matchBattleRole.toEntity();
			GameServerAssist.getDataService().insert(matchBattleRoleEntity, new IDBCallback<Long>() {
				@Override
				public void onSucceed(Long result) {

				}

				@Override
				public void onFailed(String errorMsg) {

				}
			});
		}
		matchBattleService.enterMatchBattle(matchBattleRole);
	}

	/**
	 * 更新战斗状态
	 * 
	 * @param battleState
	 */
	private void updateBattleState(MatchBattleRoleState battleState) {
		matchBattleRole.setBattleState(battleState);
		matchBattleService.battleRoleStateChange(matchBattleRole);
	}

	/**
	 * 已经准备好，等待匹配
	 */
	public void readyForMatch() {
		if(matchBattleRole.getBattleState()==MatchBattleRoleState.READY){
			updateBattleState(MatchBattleRoleState.WAIT_MATCH);
		}
	}

	/**
	 * 取消等待匹配的状态
	 */
	public void cancelReadyForMatch() {
		if(matchBattleRole.getBattleState() == MatchBattleRoleState.WAIT_MATCH){
			if (matchBattleRole.getAutoJoinNextMatchScheduleMessage() != null) {
				matchBattleRole.getAutoJoinNextMatchScheduleMessage().cancel();
			}
			updateBattleState(MatchBattleRoleState.READY);
		}
	}

	/**
	 * 离开匹配战场景
	 */
	public void leaveMatchBattleScene() {
		GameServerAssist.getMatchBattleService().leaveMatchBattleScene(human);
	}

	@Override
	public Human getOwner() {
		return human;
	}

	@Override
	public void onLoad(HumanEntity humanEntity) {
		matchBattleRole = matchBattleService.searchMatchBattleRole(human.getHumanGuid());
		if (matchBattleRole == null && humanEntity.getMatchBattleEntity() != null) {
			HumanMatchBattleEntity matchBattleRoleEntity = humanEntity.getMatchBattleEntity();
			matchBattleRole = new MatchBattleRole(human, matchBattleRoleEntity);
		}
		if(matchBattleRole !=null){
			matchBattleRole.setHuman(human);
		}
	}

	@Override
	public void onPersistence(HumanEntity humanEntity) {
		if (matchBattleRole == null) {
			return;
		}
		HumanMatchBattleEntity matchBattleRoleEntity = this.matchBattleRole.toEntity();
		humanEntity.setMatchBattleEntity(matchBattleRoleEntity);
	}

	/**
	 * 是否有可领取的连胜奖励
	 * @return
	 */
	private boolean hasStreakWinRankReward() {
		if (matchBattleRole == null) {
			return false;
		}
		if (matchBattleRole.getStreakWinRank() <= 0) {
			return false;
		}
		int rewardItemId = GameServerAssist.getMatchBattleTemplateManager().getRankRewardItemId(
				matchBattleRole.getStreakWinRank());
		if (rewardItemId <= 0) {
			return false;
		}
		return true;
	}

	/**
	 * 领取连胜奖励
	 */
	public void recieveStreakWinRankReward() {
		if (hasStreakWinRankReward()) {
			if (human.getBagManager().isFull(BagType.MAIN_BAG)) {
				human.sendErrorMessage(LangConstants.BAG_IS_FULL);
				return;
			}
			int rewardItemId = GameServerAssist.getMatchBattleTemplateManager().getRankRewardItemId(
					matchBattleRole.getStreakWinRank());
			human.getBagManager().putItems(BagType.MAIN_BAG, rewardItemId, 1,
					ItemLogReason.MATCH_BATTLE_STREAK_WIN_RANK_REWARD, "");
			matchBattleRole.setStreakWinRank(0);
			matchBattleService.saveBattleRole(matchBattleRole);
			GameServerAssist.getRewardService().sendRemoveCommonRewardMessage(human,
					RewardType.MATCH_BATTLE_STREAK_WIN_RANK_REWARD);
		}
	}

	@Override
	public void onLogin() {
		if (hasStreakWinRankReward()) {
			GameServerAssist.getRewardService().sendAddCommonRewardMessage(human,
					RewardType.MATCH_BATTLE_STREAK_WIN_RANK_REWARD);
		}
	}
	
	public void clientAlreadyLeaveBattleScene(){
		if (matchBattleRole == null) {
			return ;
		}
		matchBattleRole.setInBattleScene(false);		
		matchBattleService.battleRoleStateChange(matchBattleRole);
	}
}
