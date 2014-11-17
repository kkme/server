package com.hifun.soul.gameserver.escort.handler;

import org.springframework.stereotype.Component;

import com.hifun.soul.common.LogReasons.MoneyLogReason;
import com.hifun.soul.common.constants.LangConstants;
import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.core.msg.injection.IMessageHandlerWithType;
import com.hifun.soul.gameserver.common.GameServerAssist;
import com.hifun.soul.gameserver.currency.CurrencyType;
import com.hifun.soul.gameserver.escort.enums.EscortMonsterType;
import com.hifun.soul.gameserver.escort.msg.CGEscortCallMonster;
import com.hifun.soul.gameserver.escort.msg.GCEscortCallMonster;
import com.hifun.soul.gameserver.function.GameFuncType;
import com.hifun.soul.gameserver.human.Human;

/**
 * 召唤最高品质怪物
 * 
 * @author yandajun
 * 
 */
@Component
public class CGEscortCallMonsterHandler implements
		IMessageHandlerWithType<CGEscortCallMonster> {

	@Override
	public short getMessageType() {
		return MessageType.CG_ESCORT_CALL_MONSTER;
	}

	@Override
	public void execute(CGEscortCallMonster message) {
		Human human = message.getPlayer().getHuman();
		// 判断功能是否开启
		if (!GameServerAssist.getGameFuncService().gameFuncIsOpen(human,
				GameFuncType.ESCORT, true)) {
			return;
		}
		int monsterType = GameServerAssist.getGlobalEscortManager()
				.getEscortMonsterType(human.getHumanGuid());
		if (monsterType == EscortMonsterType.ORANGE_MONSTER.getIndex()) {
			human.sendErrorMessage(LangConstants.ESCORT_MONSTER_IS_HIGHEST);
			return;
		}
		if (human.getVipLevel() < GameServerAssist.getEscortTemplateManager()
				.getConstantsTemplate().getCallVipLevel()) {
			human.sendErrorMessage(LangConstants.ESCORT_NO_CALL_RIGHT);
			return;
		}
		int callCost = GameServerAssist.getEscortTemplateManager()
				.getConstantsTemplate().getCallCost();
		if (human.getWallet().costMoney(CurrencyType.CRYSTAL, callCost,
				MoneyLogReason.ESCORT_CALL_MONSTER, "")) {
			GameServerAssist.getGlobalEscortManager().setEscortMonsterType(
					human.getHumanGuid(),
					EscortMonsterType.ORANGE_MONSTER.getIndex());
			GCEscortCallMonster msg = new GCEscortCallMonster();
			msg.setDefaultMonsterType(EscortMonsterType.ORANGE_MONSTER
					.getIndex());
			human.sendMessage(msg);
		}

	}

}
