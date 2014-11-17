package com.hifun.soul.gameserver.escort.handler;

import org.springframework.stereotype.Component;

import com.hifun.soul.common.constants.LangConstants;
import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.core.msg.injection.IMessageHandlerWithType;
import com.hifun.soul.gameserver.common.GameServerAssist;
import com.hifun.soul.gameserver.escort.EscortInvite;
import com.hifun.soul.gameserver.escort.enums.EscortInviteSate;
import com.hifun.soul.gameserver.escort.manager.GlobalEscortManager;
import com.hifun.soul.gameserver.escort.msg.CGEscortInviteFriend;
import com.hifun.soul.gameserver.escort.msg.GCEscortInviteFriend;
import com.hifun.soul.gameserver.function.GameFuncType;
import com.hifun.soul.gameserver.human.Human;

/**
 * 邀请好友协助押运
 * 
 * @author yandajun
 * 
 */
@Component
public class CGEscortInviteFriendHandler implements
		IMessageHandlerWithType<CGEscortInviteFriend> {

	@Override
	public short getMessageType() {
		return MessageType.CG_ESCORT_INVITE_FRIEND;
	}

	@Override
	public void execute(CGEscortInviteFriend message) {
		Human human = message.getPlayer().getHuman();
		// 判断功能是否开启
		if (!GameServerAssist.getGameFuncService().gameFuncIsOpen(human,
				GameFuncType.ESCORT, true)) {
			return;
		}
		GlobalEscortManager globalEscortManager = GameServerAssist
				.getGlobalEscortManager();
		long friendId = message.getInviteFriendId();
		if (!GameServerAssist.getFriendService().isFriend(human.getHumanGuid(),
				friendId)) {
			human.sendErrorMessage(LangConstants.ESCORT_INVITE_NOT_FRIEND);
			return;
		}
		Human friend = GameServerAssist.getGameWorld().getSceneHumanManager()
				.getHumanByGuid(friendId);
		if (friend == null) {
			human.sendErrorMessage(LangConstants.ESCORT_INVITE_FRIEND_OFFLINE);
			return;
		}
		EscortInvite escortInvite = globalEscortManager.getEscortInvite(human
				.getHumanGuid());
		if (escortInvite != null) {
			// 如果是邀请未同意，不能邀请
			if (escortInvite.getInviteState() == EscortInviteSate.INVITE_NOT_AGREE
					.getIndex()) {
				human.sendErrorMessage(LangConstants.ESCORT_CAN_NOT_INVITE);
				return;
			}
			// 如果是邀请同意了，并且在有效时间内，不能邀请
			long now = GameServerAssist.getSystemTimeService().now();
			if (escortInvite.getInviteState() == EscortInviteSate.AGREE
					.getIndex() && escortInvite.getAgreeEndTime() > now) {
				human.sendErrorMessage(LangConstants.ESCORT_CAN_NOT_INVITE);
				return;
			}
		}
		// 判断该好友是否可以接受邀请
		if (globalEscortManager.canNotReceiveInvite(friend.getHumanGuid())) {
			human.sendErrorMessage(LangConstants.ESCORT_RECEIVED_INVITE);
			return;
		}
		int inviteFriendState = globalEscortManager.inviteEscortFriend(human,
				friend);
		GCEscortInviteFriend msg = new GCEscortInviteFriend();
		msg.setInviteFriendState(inviteFriendState);
		human.sendMessage(msg);
	}
}
