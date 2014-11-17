package com.hifun.soul.gameserver.warrior.battle;

import com.hifun.soul.common.LogReasons.MoneyLogReason;
import com.hifun.soul.common.constants.LangConstants;
import com.hifun.soul.gameserver.battle.BattleType;
import com.hifun.soul.gameserver.battle.IBattleUnit;
import com.hifun.soul.gameserver.battle.callback.PVPBattleCallback;
import com.hifun.soul.gameserver.battle.msg.GCExitBattle;
import com.hifun.soul.gameserver.common.GameServerAssist;
import com.hifun.soul.gameserver.currency.CurrencyType;
import com.hifun.soul.gameserver.friend.msg.GCRecommendFriendAddInfo;
import com.hifun.soul.gameserver.friend.service.FriendService;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.warrior.manager.HumanWarriorManager;
import com.hifun.soul.gameserver.warrior.msg.GCJoinWarriorBattleRequest;
import com.hifun.soul.gameserver.warrior.template.WarriorChallengeRewardTemplate;

public class WarriorPVPBattleCallback extends PVPBattleCallback {
	private Human human;
	private int winRewardWarriorHeartNum;
	private int orignalHp = 0;

	public WarriorPVPBattleCallback(Human challenger, int winBattleRewardNum) {
		super(challenger);
		human = challenger;
		winRewardWarriorHeartNum = winBattleRewardNum;
	}

	@Override
	public void onChallengerWin(Human challenger, IBattleUnit beChallenged) {
		HumanWarriorManager warriorManager = challenger.getHumanWarriorManager();
		warriorManager.addWarriorHeartNum(winRewardWarriorHeartNum);
		warriorManager.updateQuestCounter(1);
		onBattleFinished(challenger, beChallenged, true);
	}

	@Override
	public void onChallengerLose(Human challenger, IBattleUnit beChallenged) {
		HumanWarriorManager warriorManager = challenger.getHumanWarriorManager();
		int damage = orignalHp - beChallenged.getBattleContext().getBattleProperty().getHp();
		warriorManager.updateQuestCounter(damage / (orignalHp * 1f));
		onBattleFinished(challenger, beChallenged, false);
	}

	private void onBattleFinished(Human challenger, IBattleUnit beChallenged, boolean isWin) {
		HumanWarriorManager warriorManager = challenger.getHumanWarriorManager();
		warriorManager.refreshOpponent(true);
		warriorManager.sendUpdateOpponentMessage();
		if (beChallenged instanceof Human) {
			Human beAttacked = (Human) beChallenged;
			if (beAttacked.getHumanWarriorManager().getAcceptChallengeRewardRemainTimes() > 0) {
				beAttacked.getHumanWarriorManager().addAcceptChallengeRewardTimes();
				WarriorChallengeRewardTemplate rewardTemplate = GameServerAssist.getWarriorTemplateManager()
						.getWarriorChallengeRewardTemplate(beChallenged.getLevel());
				if (isWin) {
					// 判断是否是中途退出战斗导致挑战者胜利
					if (beChallenged.getBattleContext().getBattleProperty().getHp() == 0) {
						beAttacked.getWallet().addMoney(CurrencyType.COIN, rewardTemplate.getOtherLoseCoin(), true,
								MoneyLogReason.RECEIVE_WARRIOR_CHALLENGE_LOSS, "");
					}
				} else {
					beAttacked.getWallet().addMoney(CurrencyType.COIN, rewardTemplate.getOtherWinCoin(), true,
							MoneyLogReason.RECEIVE_WARRIOR_CHALLENGE_WIN, "");
				}
			}
		}
		if (!GameServerAssist.getFriendService().isFriend(challenger.getHumanGuid(), beChallenged.getUnitGuid())) {
			addStrangerAsFriend(beChallenged);
		}
	}

	@Override
	public BattleType getBattleType() {
		return BattleType.PVP_WARRIOR_BATTLE;
	}

	@Override
	protected void setExitToSceneType(GCExitBattle exitBattle) {
		// do nothing
	}

	@Override
	public void beforeBattleStart(Human challenger, IBattleUnit beChallenged) {
		orignalHp = beChallenged.getBattleContext().getBattleProperty().getHp();
	}

	@Override
	public void sendJoinBattleRequest(Human beChallenge) {
		String content = "";
		if (beChallenge.getHumanWarriorManager().getAcceptChallengeRewardRemainTimes() > 0) {
			WarriorChallengeRewardTemplate chalengeRewardTemplate = GameServerAssist.getWarriorTemplateManager()
					.getWarriorChallengeRewardTemplate(beChallenge.getLevel());
			content = GameServerAssist.getSysLangService().read(LangConstants.WARRIOR_BATTLE_REQUEST_CONTENT,
					human.getName(), chalengeRewardTemplate.getOtherWinCoin(),
					chalengeRewardTemplate.getOtherLoseCoin());
		} else {
			content = GameServerAssist.getSysLangService().read(LangConstants.WARRIOR_BATTLE_REQUEST_CONTENT_WITHOUT_REWARD);
		}
		GCJoinWarriorBattleRequest gcMsg = new GCJoinWarriorBattleRequest();
		gcMsg.setChallengerGuid(human.getHumanGuid());
		gcMsg.setContent(content);
		beChallenge.sendMessage(gcMsg);
	}

	private void addStrangerAsFriend(IBattleUnit oppoent) {
		FriendService friendService = GameServerAssist.getFriendService();
		// 在好友列表的也不能添加
		if (friendService.isFriend(human.getHumanGuid(), oppoent.getUnitGuid())) {
			return;
		}
		// 自己发出过申请不能添加
		if (friendService.isSelfApplyed(human.getHumanGuid(), oppoent.getUnitGuid())) {
			return;
		}
		// 在申请列表的不能发送
		if (friendService.isFriendApplying(human.getHumanGuid(), oppoent.getUnitGuid())) {
			return;
		}
		GCRecommendFriendAddInfo gcRecommendFirendMsg = new GCRecommendFriendAddInfo();
		gcRecommendFirendMsg.setRoleId(oppoent.getUnitGuid());
		gcRecommendFirendMsg.setRoleName(oppoent.getUnitName());
		human.sendMessage(gcRecommendFirendMsg);
	}

}
