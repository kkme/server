package com.hifun.soul.gameserver.sprite.msg.handler;

import org.springframework.stereotype.Component;

import com.hifun.soul.common.LogReasons.MoneyLogReason;
import com.hifun.soul.common.constants.LangConstants;
import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.core.msg.injection.IMessageHandlerWithType;
import com.hifun.soul.gameserver.common.GameServerAssist;
import com.hifun.soul.gameserver.currency.CurrencyType;
import com.hifun.soul.gameserver.function.GameFuncType;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.player.Player;
import com.hifun.soul.gameserver.sprite.msg.CGSucceedFinger;
import com.hifun.soul.gameserver.vip.template.VipPrivilegeTemplate;

/**
 * 处理必胜出拳;
 * 
 * @author crazyjohn
 * 
 */
@Component
public class CGSucceedFingerHandler implements
		IMessageHandlerWithType<CGSucceedFinger> {

	@Override
	public short getMessageType() {
		return MessageType.CG_SUCCEED_FINGER;
	}

	@Override
	public void execute(CGSucceedFinger message) {
		Player player = message.getPlayer();
		if (player == null) {
			return;
		}
		Human human = player.getHuman();
		if (human == null) {
			return;
		}
		// 功能是否开启
		if (!GameServerAssist.getGameFuncService().gameFuncIsOpen(human,
				GameFuncType.SPRITE_PUB, true)) {
			return;
		}
		// 是否可以出拳
		if (!human.getHumanSpritePubManager().canGiveFinger()) {
			return;
		}
		// 是否达到最大必胜次数
		VipPrivilegeTemplate vipTemplate = GameServerAssist
				.getVipPrivilegeTemplateManager().getVipTemplate(
						human.getVipLevel());
		if (vipTemplate == null) {
			return;
		}
		int maxWinNum = vipTemplate.getMaxSpritePubCertainWinTimes();
		if (human.getSpritePubWinUsedNum() >= maxWinNum) {
			human.sendWarningMessage(LangConstants.SPRITE_PUB_SUCCEED_NO_TIMES);
			return;
		}
		// 消耗魔晶
		int costCrystal = human.getHumanSpritePubManager().getSucceedCrystalCost();
		if (human.getWallet().costMoney(CurrencyType.CRYSTAL, costCrystal,
				MoneyLogReason.SUCCEED_FINGER_GUESS_ROUND, "")) {
			human.getHumanSpritePubManager().startSucceedFingerGuessRound();
		}
	}

}
