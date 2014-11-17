package com.hifun.soul.gameserver.friend.handler;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.core.msg.injection.IMessageHandlerWithType;
import com.hifun.soul.gameserver.friend.msg.CGCancelApplyFriend;

/**
 * 取消自己发出的申请
 */
@Component
public class CGCancelApplyFriendHandler implements
		IMessageHandlerWithType<CGCancelApplyFriend> {
	
	@Override
	public short getMessageType() {
		return MessageType.CG_CANCEL_APPLY_FRIEND;
	}

	@Override
	public void execute(CGCancelApplyFriend message) {
	}

}
