package com.hifun.soul.gameserver.bloodtemple.handler;

import org.springframework.stereotype.Component;

import com.hifun.soul.common.constants.LangConstants;
import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.core.msg.injection.IMessageHandlerWithType;
import com.hifun.soul.gameserver.bloodtemple.msg.CGBuyBloodTempleWrestleNum;
import com.hifun.soul.gameserver.common.GameServerAssist;
import com.hifun.soul.gameserver.function.GameFuncType;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.vip.template.VipPrivilegeTemplate;

@Component
public class CGBuyBloodTempleWrestleNumHandler implements
		IMessageHandlerWithType<CGBuyBloodTempleWrestleNum> {

	@Override
	public short getMessageType() {
		return MessageType.CG_BUY_BLOOD_TEMPLE_WRESTLE_NUM;
	}

	@Override
	public void execute(CGBuyBloodTempleWrestleNum message) {
		Human human = message.getPlayer().getHuman();
		// 功能是否开启
		if (!GameServerAssist.getGameFuncService().gameFuncIsOpen(human,
				GameFuncType.BLOOD_TEMPLE, true)) {
			return;
		}
		// 是否超出vip购买限制
		VipPrivilegeTemplate vipTemplate = GameServerAssist
				.getVipPrivilegeTemplateManager().getVipTemplate(
						human.getVipLevel());
		if (vipTemplate == null) {
			return;
		}
		int totalNum = human.getBloodTempleBuyNum() + 1;
		if (totalNum > vipTemplate.getMaxBuyBloodTempleTimes()) {
			human.sendErrorMessage(LangConstants.CAN_NOT_BUY_MORE_BLOOD_TEMPLE_WRESTLE_TIMES);
			return;
		}
		human.getHumanBloodTempleManager().buyWrestleNum();
	}

}
