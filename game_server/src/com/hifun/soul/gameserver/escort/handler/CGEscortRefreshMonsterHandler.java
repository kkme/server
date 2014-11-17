package com.hifun.soul.gameserver.escort.handler;

import org.apache.commons.lang.math.RandomUtils;
import org.springframework.stereotype.Component;

import com.hifun.soul.common.LogReasons.MoneyLogReason;
import com.hifun.soul.common.constants.LangConstants;
import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.core.msg.injection.IMessageHandlerWithType;
import com.hifun.soul.gameserver.common.GameServerAssist;
import com.hifun.soul.gameserver.currency.CurrencyType;
import com.hifun.soul.gameserver.escort.enums.EscortMonsterType;
import com.hifun.soul.gameserver.escort.msg.CGEscortRefreshMonster;
import com.hifun.soul.gameserver.escort.msg.GCEscortRefreshMonster;
import com.hifun.soul.gameserver.escort.template.EscortRefreshMonsterRateTemplate;
import com.hifun.soul.gameserver.function.GameFuncType;
import com.hifun.soul.gameserver.human.Human;

/**
 * 刷新押运怪物的品质
 * 
 * @author yandajun
 * 
 */
@Component
public class CGEscortRefreshMonsterHandler implements
		IMessageHandlerWithType<CGEscortRefreshMonster> {

	@Override
	public short getMessageType() {
		return MessageType.CG_ESCORT_REFRESH_MONSTER;
	}

	@Override
	public void execute(CGEscortRefreshMonster message) {
		Human human = message.getPlayer().getHuman();
		// 判断功能是否开启
		if (!GameServerAssist.getGameFuncService().gameFuncIsOpen(human,
				GameFuncType.ESCORT, true)) {
			return;
		}
		int refreshNum = human.getEscortRefreshMonsterNum();
		int refreshCost = GameServerAssist.getEscortTemplateManager()
				.getRefreshMonsterCost(refreshNum + 1);
		int monsterType = GameServerAssist.getGlobalEscortManager()
				.getEscortMonsterType(human.getHumanGuid());
		if (monsterType >= EscortMonsterType.ORANGE_MONSTER.getIndex()) {
			human.sendErrorMessage(LangConstants.ESCORT_MONSTER_IS_HIGHEST);
			return;
		}
		// 消费
		if (human.getWallet().costMoney(CurrencyType.CRYSTAL, refreshCost,
				MoneyLogReason.ESCORT_REFRESH_MONSTER, "")) {
			EscortRefreshMonsterRateTemplate refreshRateTemplate = GameServerAssist
					.getEscortTemplateManager().getRefreshRateTemplate(
							monsterType);
			int upRate = refreshRateTemplate.getUpRate();
			int downRate = refreshRateTemplate.getDownRate();
			// 概率刷新
			int randomNum = RandomUtils.nextInt(10000);
			if (randomNum < upRate) {// 升一级
				monsterType++;
			} else if (randomNum < downRate + upRate) {// 降一级
				monsterType--;
			}
			human.setEscortRefreshMonsterNum(refreshNum + 1);
			GameServerAssist.getGlobalEscortManager().setEscortMonsterType(
					human.getHumanGuid(), monsterType);
			GCEscortRefreshMonster msg = new GCEscortRefreshMonster();
			msg.setDefaultMonsterType(monsterType);
			msg.setRefreshMonsterCost(GameServerAssist
					.getEscortTemplateManager().getRefreshMonsterCost(
							refreshNum + 2));
			human.sendMessage(msg);
		}
	}
}
