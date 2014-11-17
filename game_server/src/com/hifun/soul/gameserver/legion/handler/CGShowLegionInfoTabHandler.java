package com.hifun.soul.gameserver.legion.handler;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.core.msg.injection.IMessageHandlerWithType;
import com.hifun.soul.gameserver.common.GameServerAssist;
import com.hifun.soul.gameserver.function.GameFuncType;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.legion.Legion;
import com.hifun.soul.gameserver.legion.LegionMember;
import com.hifun.soul.gameserver.legion.msg.CGShowLegionInfoTab;
import com.hifun.soul.gameserver.legion.msg.GCShowLegionInfoTab;

/**
 * 展示军团信息标签页
 * 
 * @author yandajun
 * 
 */
@Component
public class CGShowLegionInfoTabHandler implements
		IMessageHandlerWithType<CGShowLegionInfoTab> {

	@Override
	public short getMessageType() {
		return MessageType.CG_SHOW_LEGION_INFO_TAB;
	}

	@Override
	public void execute(CGShowLegionInfoTab message) {
		Human human = message.getPlayer().getHuman();
		// 功能是否开启
		if (!GameServerAssist.getGameFuncService().gameFuncIsOpen(human,
				GameFuncType.LEGION, true)) {
			return;
		}
		Legion legion = GameServerAssist.getGlobalLegionManager().getLegion(
				human.getHumanGuid());
		if (legion == null) {
			return;
		}
		GCShowLegionInfoTab gcMsg = new GCShowLegionInfoTab();
		LegionMember legionMember = GameServerAssist.getGlobalLegionManager()
				.getLegionMember(human.getHumanGuid());
		if (legionMember == null) {
			return;
		}
		boolean canEditNotice = GameServerAssist.getLegionTemplateManager()
				.getLegionRightTemplate(legionMember.getPosition())
				.getEditNotice() == 1;
		gcMsg.setCanEditNotice(canEditNotice);
		gcMsg.setOwnLegion(legion);
		human.sendMessage(gcMsg);
	}
}
