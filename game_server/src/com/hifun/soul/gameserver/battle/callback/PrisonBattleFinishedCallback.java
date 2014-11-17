package com.hifun.soul.gameserver.battle.callback;

import com.hifun.soul.common.constants.LangConstants;
import com.hifun.soul.gameserver.battle.BattleType;
import com.hifun.soul.gameserver.battle.IBattleUnit;
import com.hifun.soul.gameserver.battle.msg.GCExitBattle;
import com.hifun.soul.gameserver.battle.msg.GCJoinBattleRequest;
import com.hifun.soul.gameserver.common.GameServerAssist;
import com.hifun.soul.gameserver.event.PrisonBattleEvent;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.prison.PrisonBattleType;
import com.hifun.soul.gameserver.prison.manager.GlobalPrisonManager;

/**
 * 战俘营战斗回调;
 * 
 * @author yandajun
 * 
 */
public class PrisonBattleFinishedCallback extends PVPBattleCallback {
	private PrisonBattleType prisonBattleType;
	private GlobalPrisonManager globalPrisonManager;
	private Long slaveId;

	public PrisonBattleFinishedCallback(Human challenger,
			PrisonBattleType prisonBattleType, Long slaveId) {
		super(challenger);
		this.prisonBattleType = prisonBattleType;
		globalPrisonManager = GameServerAssist.getGlobalPrisonManager();
		this.slaveId = slaveId;
	}

	@Override
	public void onChallengerWin(Human challenger, IBattleUnit beChallenged) {
		fireBattleEvent(challenger);
		switch (prisonBattleType) {
		case ARREST:
			globalPrisonManager.arrestCallBack(challenger,
					beChallenged.getUnitGuid(), true);
			break;
		case LOOT:
			globalPrisonManager.lootCallBack(challenger,
					beChallenged.getUnitGuid(), slaveId, true);
			break;
		case RESCUE:
			globalPrisonManager.rescueCallBack(challenger, slaveId, true);
			break;
		case REVOLT:
			globalPrisonManager.revoltCallBack(challenger, true);
			break;
		}
	}

	@Override
	public void onChallengerLose(Human challenger, IBattleUnit beChallenged) {
		fireBattleEvent(challenger);
		switch (prisonBattleType) {
		case ARREST:
			globalPrisonManager.arrestCallBack(challenger,
					beChallenged.getUnitGuid(), false);
			break;
		case LOOT:
			globalPrisonManager.lootCallBack(challenger,
					beChallenged.getUnitGuid(), slaveId, false);
			break;
		case RESCUE:
			globalPrisonManager.rescueCallBack(challenger, slaveId, false);
			break;
		case REVOLT:
			globalPrisonManager.revoltCallBack(challenger, false);
			break;
		}
	}

	@Override
	public BattleType getBattleType() {
		return BattleType.PVP_PRISON_BATTLE;
	}

	@Override
	protected void setExitToSceneType(GCExitBattle exitBattle) {
		exitBattle.setGameSceneType(ClientGameSceneType.PRISON_VIEW.getIndex());
	}

	@Override
	public void sendJoinBattleRequest(Human beChallenge) {
		GameServerAssist.getGlobalPrisonManager()
				.getPrisoner(challenger.getHumanGuid()).setFighting(true);
		GameServerAssist.getGlobalPrisonManager()
				.getPrisoner(beChallenge.getHumanGuid()).setFighting(true);
		GCJoinBattleRequest requestBattle = new GCJoinBattleRequest();
		requestBattle.setChallengerGuid(challenger.getHumanGuid());
		requestBattle.setChallengerName(challenger.getName());
		String content = GameServerAssist.getSysLangService().read(
				LangConstants.PRISON_BATTLE);
		requestBattle.setContent(content);
		beChallenge.sendMessage(requestBattle);
	}

	@Override
	public void beforeBattleStart(Human challenger, IBattleUnit beChallenged) {
		GameServerAssist.getGlobalPrisonManager()
				.getPrisoner(challenger.getHumanGuid()).setFighting(true);
		GameServerAssist.getGlobalPrisonManager()
				.getPrisoner(beChallenged.getUnitGuid()).setFighting(true);
	}

	/**
	 * 发送战俘营战斗事件
	 */
	private void fireBattleEvent(Human challenger) {
		challenger.getEventBus().fireEvent(new PrisonBattleEvent());
	}
}
