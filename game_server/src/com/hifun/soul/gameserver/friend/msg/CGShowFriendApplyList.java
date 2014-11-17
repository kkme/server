package com.hifun.soul.gameserver.friend.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.CGMessage;

/**
 * 查看好友申请列表
 * 
 * @author SevenSoul
 */
@Component
public class CGShowFriendApplyList extends CGMessage{
	
	
	public CGShowFriendApplyList (){
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
		return MessageType.CG_SHOW_FRIEND_APPLY_LIST;
	}
	
	@Override
	public String getTypeName() {
		return "CG_SHOW_FRIEND_APPLY_LIST";
	}

	@Override
	public void execute() {
	}
}