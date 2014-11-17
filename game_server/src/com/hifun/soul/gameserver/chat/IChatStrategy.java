package com.hifun.soul.gameserver.chat;

import com.hifun.soul.gameserver.chat.msg.CGChatMsg;


/**
 * 
 * 聊天处理接口
 * 
 * @author magicstone
 *
 */
public interface IChatStrategy {

	public void execute(CGChatMsg msg);
}
