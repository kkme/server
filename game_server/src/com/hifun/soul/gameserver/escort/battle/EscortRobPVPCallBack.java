package com.hifun.soul.gameserver.escort.battle;

import com.hifun.soul.common.constants.LangConstants;
import com.hifun.soul.gameserver.battle.BattleType;
import com.hifun.soul.gameserver.battle.IBattleUnit;
import com.hifun.soul.gameserver.battle.callback.ClientGameSceneType;
import com.hifun.soul.gameserver.battle.callback.PVPBattleCallback;
import com.hifun.soul.gameserver.battle.msg.GCExitBattle;
import com.hifun.soul.gameserver.battle.msg.GCJoinBattleRequest;
import com.hifun.soul.gameserver.common.GameServerAssist;
import com.hifun.soul.gameserver.human.Human;

public class EscortRobPVPCallBack extends PVPBattleCallback {
	private long escortId;
	private boolean hasFriendHelp;

	public EscortRobPVPCallBack(Human challenger, long escortId,
			boolean hasFriendHelp) {
		super(challenger);
		this.escortId = escortId;
		this.hasFriendHelp = hasFriendHelp;
	}

	@Override
	public void onChallengerWin(Human challenger, IBattleUnit beChallenged) {
		GameServerAssist.getGlobalEscortManager().robCallBack(challenger,
				escortId, true);
	}

	@Override
	public void onChallengerLose(Human challenger, IBattleUnit beChallenged) {
		GameServerAssist.getGlobalEscortManager().robCallBack(challenger,
				escortId, false);
	}

	@Override
	public void beforeBattleStart(Human challenger, IBattleUnit beChallenged) {
		GameServerAssist.getGlobalEscortManager().getEscortInfo(escortId)
				.setRobing(true);
	}

	@Override
	public BattleType getBattleType() {
		return BattleType.PVP_ESCORT_ROB;
	}

	@Override
	protected void setExitToSceneType(GCExitBattle exitBattle) {
		exitBattle.setGameSceneType(ClientGameSceneType.ESCORT_VIEW.getIndex());
	}

	@Override
	public void sendJoinBattleRequest(Human beChallenge) {
		GCJoinBattleRequest requestBattle = new GCJoinBattleRequest();
		requestBattle.setChallengerGuid(challenger.getHumanGuid());
		requestBattle.setChallengerName(challenger.getName());
		// 如果有好友协助
		if (hasFriendHelp) {
			requestBattle.setContent(GameServerAssist.getSysLangService().read(
					LangConstants.ESCORT_ROB_FRIEND_BATTLE));
		} else {
			requestBattle.setContent(GameServerAssist.getSysLangService().read(
					LangConstants.ESCORT_ROB_BATTLE));
		}

		beChallenge.sendMessage(requestBattle);
	}
}
