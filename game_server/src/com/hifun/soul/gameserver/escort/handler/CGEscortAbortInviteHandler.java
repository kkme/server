package com.hifun.soul.gameserver.escort.handler;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.core.msg.injection.IMessageHandlerWithType;
import com.hifun.soul.gameserver.common.GameServerAssist;
import com.hifun.soul.gameserver.escort.EscortInvite;
import com.hifun.soul.gameserver.escort.enums.EscortInviteSate;
import com.hifun.soul.gameserver.escort.manager.GlobalEscortManager;
import com.hifun.soul.gameserver.escort.msg.CGEscortAbortInvite;
import com.hifun.soul.gameserver.escort.msg.GCEscortAbortInvite;
import com.hifun.soul.gameserver.function.GameFuncType;
import com.hifun.soul.gameserver.human.Human;

/**
 * 取消押运协助申请
 * 
 * @author yandajun
 * 
 */
@Component
public class CGEscortAbortInviteHandler implements
		IMessageHandlerWithType<CGEscortAbortInvite> {

	@Override
	public short getMessageType() {
		return MessageType.CG_ESCORT_ABORT_INVITE;
	}

	@Override
	public void execute(CGEscortAbortInvite message) {
		Human human = message.getPlayer().getHuman();
		// 判断功能是否开启
		if (!GameServerAssist.getGameFuncService().gameFuncIsOpen(human,
				GameFuncType.ESCORT, true)) {
			return;
		}
		GlobalEscortManager globalEscortManager = GameServerAssist
				.getGlobalEscortManager();
		EscortInvite escortInvite = globalEscortManager.getEscortInvite(human
				.getHumanGuid());
		if (escortInvite == null
				|| escortInvite.getInviteState() != EscortInviteSate.INVITE_NOT_AGREE
						.getIndex()) {
			return;
		}
		int inviteState = globalEscortManager.abortEscortInvite(human);
		GCEscortAbortInvite msg = new GCEscortAbortInvite();
		msg.setInviteFriendState(inviteState);
		human.sendMessage(msg);
	}

}
