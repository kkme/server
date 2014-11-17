package com.hifun.soul.gameserver.escort.handler;

import org.springframework.stereotype.Component;

import com.hifun.soul.common.constants.LangConstants;
import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.core.msg.injection.IMessageHandlerWithType;
import com.hifun.soul.gameserver.common.GameServerAssist;
import com.hifun.soul.gameserver.escort.EscortInvite;
import com.hifun.soul.gameserver.escort.enums.EscortInviteSate;
import com.hifun.soul.gameserver.escort.manager.GlobalEscortManager;
import com.hifun.soul.gameserver.escort.msg.CGEscortAgreeInvite;
import com.hifun.soul.gameserver.escort.msg.GCEscortAgreeInvite;
import com.hifun.soul.gameserver.function.GameFuncType;
import com.hifun.soul.gameserver.human.Human;

/**
 * 同意协助押运
 * 
 * @author yandajun
 * 
 */
@Component
public class CGEscortAgreeInviteHandler implements
		IMessageHandlerWithType<CGEscortAgreeInvite> {

	@Override
	public short getMessageType() {
		return MessageType.CG_ESCORT_AGREE_INVITE;
	}

	@Override
	public void execute(CGEscortAgreeInvite message) {
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
		if (escortInvite == null) {
			return;
		}
		if (escortInvite.getInviteState() != EscortInviteSate.INVITE_NOT_AGREE
				.getIndex()) {
			human.sendErrorMessage(LangConstants.ESCORT_ABORTED_INVITE);
			return;
		}
		// 判断自己是否可以接受邀请
		if (globalEscortManager.canNotReceiveInvite(human.getHumanGuid())) {
			human.sendErrorMessage(LangConstants.ESCORT_RECEIVED_INVITE);
			return;
		}

		globalEscortManager.agreeEscortInvite(message.getInviteId());

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
