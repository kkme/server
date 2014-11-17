package com.hifun.soul.gameserver.levy.handler;

import org.springframework.stereotype.Component;

import com.hifun.soul.common.constants.LangConstants;
import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.core.msg.injection.IMessageHandlerWithType;
import com.hifun.soul.gameserver.cd.CdType;
import com.hifun.soul.gameserver.cd.manager.HumanCdManager;
import com.hifun.soul.gameserver.common.GameServerAssist;
import com.hifun.soul.gameserver.function.GameFuncType;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.levy.MainCityMonsterInfo;
import com.hifun.soul.gameserver.levy.battle.MainCityPVEBattleCallback;
import com.hifun.soul.gameserver.levy.msg.CGBattleMainCityMonster;
import com.hifun.soul.gameserver.monster.Monster;

@Component
public class CGBattleMainCityMonsterHandler implements
		IMessageHandlerWithType<CGBattleMainCityMonster> {

	@Override
	public short getMessageType() {
		return MessageType.CG_BATTLE_MAIN_CITY_MONSTER;
	}

	@Override
	public void execute(CGBattleMainCityMonster message) {
		Human human = message.getPlayer().getHuman();
		// 判断功能是否开启
		if (!GameServerAssist.getGameFuncService().gameFuncIsOpen(human,
				GameFuncType.MAIN_CITY_MONSTER, true)) {
			return;
		}
		// 判断怪物是否已经打完
		if (human.getMainCityMonsterRemainNum() <= 0) {
			human.sendErrorMessage(LangConstants.NO_MAIN_CITY_MONSTER);
			return;
		}
		// 判断是否在CD中
		HumanCdManager cdManager = human.getHumanCdManager();
		long spendTime = cdManager.getSpendTime(CdType.MAIN_CITY_BATTLE, 0);
		if (!cdManager.canAddCd(CdType.MAIN_CITY_BATTLE, spendTime)) {
			human.sendWarningMessage(LangConstants.CD_LIMIT);
			return;
		}
		MainCityMonsterInfo monsterInfo = human.getLevyManager()
				.getMonsterInfo();
		if (monsterInfo == null) {
			return;
		}
		Monster monster = GameServerAssist.getMonsterFactory().createMonster(
				monsterInfo.getMonsterId());
		if (monster == null) {
			return;
		}
		GameServerAssist.getBattleManager().startBattleWithMapMonster(human,
				monster, new MainCityPVEBattleCallback(human));
	}
}
