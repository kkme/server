package com.hifun.soul.gameserver.legion.handler;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.core.msg.injection.IMessageHandlerWithType;
import com.hifun.soul.gameserver.common.GameServerAssist;
import com.hifun.soul.gameserver.function.GameFuncType;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.legion.msg.CGShowLegionLogTab;

/**
 * 展示军团日志标签页
 * 
 * @author yandajun
 * 
 */
@Component
public class CGShowLegionLogTabHandler implements
		IMessageHandlerWithType<CGShowLegionLogTab> {

	@Override
	public short getMessageType() {
		return MessageType.CG_SHOW_LEGION_LOG_TAB;
	}

	@Override
	public void execute(CGShowLegionLogTab message) {
		Human human = message.getPlayer().getHuman();
		// 功能是否开启
		if (!GameServerAssist.getGameFuncService().gameFuncIsOpen(human,
				GameFuncType.LEGION, true)) {
			return;
		}
		GameServerAssist.getGlobalLegionManager().sendLegionLogList(human);

	}

}
