package com.hifun.soul.gameserver.mars.battle;

import com.hifun.soul.common.LogReasons.HonourLogReason;
import com.hifun.soul.common.constants.LangConstants;
import com.hifun.soul.gameserver.battle.BattleType;
import com.hifun.soul.gameserver.battle.IBattleUnit;
import com.hifun.soul.gameserver.battle.callback.ClientGameSceneType;
import com.hifun.soul.gameserver.battle.callback.PVPBattleCallback;
import com.hifun.soul.gameserver.battle.msg.GCExitBattle;
import com.hifun.soul.gameserver.battle.msg.GCJoinBattleRequest;
import com.hifun.soul.gameserver.common.GameServerAssist;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.mars.template.MarsBattleRewardTemplate;

public class MarsPVPBattleCallback extends PVPBattleCallback {
	private Human human;
	private int roomType;
	private int multiple;
	private boolean isAgree;

	public MarsPVPBattleCallback(Human challenger, int roomType, int multiple) {
		super(challenger);
		this.human = challenger;
		this.roomType = roomType;
		this.multiple = multiple;
	}

	@Override
	public void onChallengerWin(Human challenger, IBattleUnit beChallenged) {
		// 我方胜利，对方接受失败奖励
		if (isAgree) {
			Human beChallHuman = GameServerAssist.getGameWorld()
					.getSceneHumanManager()
					.getHumanByGuid(beChallenged.getUnitGuid());
			if (beChallHuman != null
					&& beChallHuman.getHumanMarsManager()
							.getRemainAcceptRewardNum() > 0) {
				MarsBattleRewardTemplate template = GameServerAssist
						.getMarsTemplateManager().getMarsBattleRewardTemplate(
								beChallHuman.getLevel());
				int rewardHonor = template.getAcceptLoseHonor();
				beChallHuman.addArenaHonor(rewardHonor, true,
						HonourLogReason.MARS, "");
				// 更改接受挑战奖励次数
				beChallHuman.setMarsAcceptRewardNum(beChallHuman
						.getMarsAcceptRewardNum() + 1);
			}
		}
		human.getHumanMarsManager().killCallBack(roomType, true, multiple);
	}

	@Override
	public void onChallengerLose(Human challenger, IBattleUnit beChallenged) {
		// 我方失败，对方接受胜利奖励
		if (isAgree) {
			Human beChallHuman = GameServerAssist.getGameWorld()
					.getSceneHumanManager()
					.getHumanByGuid(beChallenged.getUnitGuid());
			if (beChallHuman != null) {
				MarsBattleRewardTemplate template = GameServerAssist
						.getMarsTemplateManager().getMarsBattleRewardTemplate(
								beChallHuman.getLevel());
				int rewardHonor = template.getAcceptWinHonor();
				beChallHuman.addArenaHonor(rewardHonor, true,
						HonourLogReason.MARS, "");
				// 更改接受挑战奖励次数
				beChallHuman.setMarsAcceptRewardNum(beChallHuman
						.getMarsAcceptRewardNum() + 1);
			}
		}
		human.getHumanMarsManager().killCallBack(roomType, false, multiple);
	}

	@Override
	public BattleType getBattleType() {
		return BattleType.PVP_MARS_BATTLE;
	}

	@Override
	protected void setExitToSceneType(GCExitBattle exitBattle) {
		exitBattle.setGameSceneType(ClientGameSceneType.MARS_VIEW.getIndex());
	}

	@Override
	public void beforeBattleStart(Human challenger, IBattleUnit beChallenged) {
	}

	@Override
	public void sendJoinBattleRequest(Human beChallenge) {
		GCJoinBattleRequest requestBattle = new GCJoinBattleRequest();
		requestBattle.setChallengerGuid(challenger.getHumanGuid());
		requestBattle.setChallengerName(challenger.getName());
		requestBattle.setContent(GameServerAssist.getSysLangService().read(
				LangConstants.MARS_BATTLE));
		beChallenge.sendMessage(requestBattle);
	}

	public void setIsAgree(boolean isAgree) {
		this.isAgree = isAgree;
	}
}
