package com.hifun.soul.gameserver.legion.handler;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.core.msg.injection.IMessageHandlerWithType;
import com.hifun.soul.gameserver.common.GameServerAssist;
import com.hifun.soul.gameserver.function.GameFuncType;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.legion.msg.CGSearchLegion;

/**
 * 搜索军团
 * 
 * @author yandajun
 * 
 */
@Component
public class CGSearchLegionHandler implements
		IMessageHandlerWithType<CGSearchLegion> {

	@Override
	public short getMessageType() {
		return MessageType.CG_SEARCH_LEGION;
	}

	@Override
	public void execute(CGSearchLegion message) {
		Human human = message.getPlayer().getHuman();
		// 功能是否开启
		if (!GameServerAssist.getGameFuncService().gameFuncIsOpen(human,
				GameFuncType.LEGION, true)) {
			return;
		}
		GameServerAssist.getGlobalLegionManager().searchLegion(human,
				message.getLegionName());
	}

}
