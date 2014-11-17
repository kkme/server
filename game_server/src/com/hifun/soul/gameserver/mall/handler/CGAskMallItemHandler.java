package com.hifun.soul.gameserver.mall.handler;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.core.msg.injection.IMessageHandlerWithType;
import com.hifun.soul.gameserver.common.GameServerAssist;
import com.hifun.soul.gameserver.function.GameFuncType;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.mall.msg.CGAskMallItem;
import com.hifun.soul.gameserver.mall.service.MallService;
import com.hifun.soul.gameserver.player.Player;

@Component
public class CGAskMallItemHandler implements IMessageHandlerWithType<CGAskMallItem> {

	@Override
	public short getMessageType() {
		return MessageType.CG_ASK_MALL_ITEM;
	}

	@Override
	public void execute(CGAskMallItem message) {
		Player player = message.getPlayer();
		if (player == null) {
			return;
		}

		Human human = player.getHuman();
		if (human == null) {
			return;
		}
		// 判断功能是否开启
		if (!GameServerAssist.getGameFuncService().gameFuncIsOpen(human,
				GameFuncType.MALL, true)) {
			return;
		}
		MallService.sendAskMallItemMessage(human, message.getItemId());
	}

}
