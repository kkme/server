package com.hifun.soul.gameserver.legion.handler;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.core.msg.injection.IMessageHandlerWithType;
import com.hifun.soul.gameserver.common.GameServerAssist;
import com.hifun.soul.gameserver.function.GameFuncType;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.legion.manager.GlobalLegionManager;
import com.hifun.soul.gameserver.legion.msg.CGShowLegionMemberTab;
import com.hifun.soul.gameserver.legion.msg.GCShowLegionMemberTab;

/**
 * 展示军团成员标签页
 * 
 * @author yandajun
 * 
 */
@Component
public class CGShowLegionMemberTabHandler implements
		IMessageHandlerWithType<CGShowLegionMemberTab> {

	@Override
	public short getMessageType() {
		return MessageType.CG_SHOW_LEGION_MEMBER_TAB;
	}

	@Override
	public void execute(CGShowLegionMemberTab message) {
		Human human = message.getPlayer().getHuman();
		// 功能是否开启
		if (!GameServerAssist.getGameFuncService().gameFuncIsOpen(human,
				GameFuncType.LEGION, true)) {
			return;
		}
		GlobalLegionManager globalLegionManager = GameServerAssist
				.getGlobalLegionManager();
		// 校验身份
		if (globalLegionManager.getLegion(human.getHumanGuid()) == null) {
			return;
		}

		GCShowLegionMemberTab gcMsg = new GCShowLegionMemberTab();

		gcMsg.setLegionMembers(globalLegionManager
				.generateMemberListInfo(human));
		human.sendMessage(gcMsg);
	}
}
