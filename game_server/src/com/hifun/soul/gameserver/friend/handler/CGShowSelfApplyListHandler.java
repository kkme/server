package com.hifun.soul.gameserver.friend.handler;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.core.msg.injection.IMessageHandlerWithType;
import com.hifun.soul.gameserver.friend.msg.CGShowSelfApplyList;

/**
 * 显示自己发送的好友申请
 */
@Component
public class CGShowSelfApplyListHandler implements
		IMessageHandlerWithType<CGShowSelfApplyList> {
	
	@Override
	public short getMessageType() {
		return MessageType.CG_SHOW_SELF_APPLY_LIST;
	}

	@Override
	public void execute(CGShowSelfApplyList message) {
	}

}
