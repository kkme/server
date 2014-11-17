package com.hifun.soul.gameserver.friend.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.CGMessage;

/**
 * 系统推荐好友
 * 
 * @author SevenSoul
 */
@Component
public class CGShowLuckFriends extends CGMessage{
	
	
	public CGShowLuckFriends (){
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
		return MessageType.CG_SHOW_LUCK_FRIENDS;
	}
	
	@Override
	public String getTypeName() {
		return "CG_SHOW_LUCK_FRIENDS";
	}

	@Override
	public void execute() {
	}
}