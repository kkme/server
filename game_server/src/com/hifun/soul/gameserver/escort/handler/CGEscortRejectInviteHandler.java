package com.hifun.soul.gameserver.escort.handler;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.core.msg.injection.IMessageHandlerWithType;
import com.hifun.soul.gameserver.common.GameServerAssist;
import com.hifun.soul.gameserver.escort.EscortInvite;
import com.hifun.soul.gameserver.escort.enums.EscortInviteSate;
import com.hifun.soul.gameserver.escort.manager.GlobalEscortManager;
import com.hifun.soul.gameserver.escort.msg.CGEscortRejectInvite;
import com.hifun.soul.gameserver.escort.msg.GCEscortAgreeInvite;
import com.hifun.soul.gameserver.function.GameFuncType;
import com.hifun.soul.gameserver.human.Human;

/**
 * 拒绝押运协助申请
 * 
 * @author yandajun
 * 
 */
@Component
public class CGEscortRejectInviteHandler implements
		IMessageHandlerWithType<CGEscortRejectInvite> {

	@Override
	public short getMessageType() {
		return MessageType.CG_ESCORT_REJECT_INVITE;
	}

	@Override
	public void execute(CGEscortRejectInvite message) {
		Human human = message.getPlayer().getHuman();
		if (human == null) {
			return;
		}
		// 判断功能是否开启
		if (!GameServerAssist.getGameFuncService().gameFuncIsOpen(human,
				GameFuncType.ESCORT, true)) {
			return;
		}
		GlobalEscortManager globalEscortManager = GameServerAssist
				.getGlobalEscortManager();
		EscortInvite escortInvite = globalEscortManager.getEscortInvite(message
				.getInviteId());
		if (escortInvite == null
				|| escortInvite.getInviteState() != EscortInviteSate.INVITE_NOT_AGREE
						.getIndex()) {
			return;
		}
		globalEscortManager.rejectEscortInvite(message.getInviteId());

		Human inviter = GameServerAssist.getGameWorld().getSceneHumanManager()
				.getHumanByGuid(message.getInviteId());
		if (inviter != null) {
			GCEscortAgreeInvite msg = new GCEscortAgreeInvite();
			msg.setInviteFriendState(escortInvite.getInviteState());
			msg.setInviteRemainTime((int) (escortInvite.getAgreeEndTime() - GameServerAssist
					.getSystemTimeService().now()));
			inviter.sendMessage(msg);
		}
	}

}
