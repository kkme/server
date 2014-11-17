package com.hifun.soul.gameserver.friend.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.CGMessage;

/**
 * 显示好友切磋信息
 * 
 * @author SevenSoul
 */
@Component
public class CGShowFriendBattleinfos extends CGMessage{
	
	
	public CGShowFriendBattleinfos (){
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
		return MessageType.CG_SHOW_FRIEND_BATTLEINFOS;
	}
	
	@Override
	public String getTypeName() {
		return "CG_SHOW_FRIEND_BATTLEINFOS";
	}

	@Override
	public void execute() {
	}
}