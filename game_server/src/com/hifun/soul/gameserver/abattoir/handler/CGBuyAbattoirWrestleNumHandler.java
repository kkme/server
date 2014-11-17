package com.hifun.soul.gameserver.abattoir.handler;

import org.springframework.stereotype.Component;

import com.hifun.soul.common.constants.LangConstants;
import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.core.msg.injection.IMessageHandlerWithType;
import com.hifun.soul.gameserver.abattoir.msg.CGBuyAbattoirWrestleNum;
import com.hifun.soul.gameserver.common.GameServerAssist;
import com.hifun.soul.gameserver.function.GameFuncType;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.vip.template.VipPrivilegeTemplate;

@Component
public class CGBuyAbattoirWrestleNumHandler implements
		IMessageHandlerWithType<CGBuyAbattoirWrestleNum> {

	@Override
	public short getMessageType() {
		return MessageType.CG_BUY_ABATTOIR_WRESTLE_NUM;
	}

	@Override
	public void execute(CGBuyAbattoirWrestleNum message) {
		Human human = message.getPlayer().getHuman();
		// 功能是否开启
		if (!GameServerAssist.getGameFuncService().gameFuncIsOpen(human,
				GameFuncType.ABATTOIR, true)) {
			return;
		}
		// 是否超出vip购买限制
		VipPrivilegeTemplate vipTemplate = GameServerAssist
				.getVipPrivilegeTemplateManager().getVipTemplate(
						human.getVipLevel());
		if (vipTemplate == null) {
			return;
		}
		int totalNum = human.getAbattoirBuyNum() + 1;
		if (totalNum > vipTemplate.getMaxBuyAbattoirTimes()) {
			human.sendErrorMessage(LangConstants.CAN_NOT_BUY_MORE_ABATTOIR_WRESTLE_TIMES);
			return;
		}
		human.getHumanAbattoirManager().buyWrestleNum();
	}

}
