package com.hifun.soul.gameserver.friend.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.CGMessage;

/**
 * 请求显示好友列表
 * 
 * @author SevenSoul
 */
@Component
public class CGShowFriends extends CGMessage{
	
	
	public CGShowFriends (){
	}
	
	
	@Override
	protected boolean readImpl() {
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.CG_SHOW_FRIENDS;
	}
	
	@Override
	public String getTypeName() {
		return "CG_SHOW_FRIENDS";
	}

	@Override
	public void execute() {
	}
}