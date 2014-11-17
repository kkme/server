package com.hifun.soul.gameserver.legion.handler;

import java.util.List;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.core.msg.injection.IMessageHandlerWithType;
import com.hifun.soul.gameserver.common.GameServerAssist;
import com.hifun.soul.gameserver.function.GameFuncType;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.legion.Legion;
import com.hifun.soul.gameserver.legion.msg.CGShowLegionListTab;
import com.hifun.soul.gameserver.legion.msg.GCShowLegionListTab;
import com.hifun.soul.gameserver.legion.msg.LegionListInfo;

/**
 * 展示所有军团列表
 * 
 * @author yandajun
 * 
 */
@Component
public class CGShowLegionListTabHandler implements
		IMessageHandlerWithType<CGShowLegionListTab> {

	@Override
	public short getMessageType() {
		return MessageType.CG_SHOW_LEGION_LIST_TAB;
	}

	@Override
	public void execute(CGShowLegionListTab message) {
		Human human = message.getPlayer().getHuman();
		// 功能是否开启
		if (!GameServerAssist.getGameFuncService().gameFuncIsOpen(human,
				GameFuncType.LEGION, true)) {
			return;
		}
		List<Legion> legionList = GameServerAssist.getGlobalLegionManager()
				.getLegionList();
		GCShowLegionListTab gcMsg = new GCShowLegionListTab();
		LegionListInfo[] legions = GameServerAssist.getGlobalLegionManager()
				.legionToListInfo(human, legionList);
		gcMsg.setLegions(legions);
		human.sendMessage(gcMsg);
	}

}
