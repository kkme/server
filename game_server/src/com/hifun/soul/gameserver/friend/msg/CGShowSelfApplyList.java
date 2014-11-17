package com.hifun.soul.gameserver.friend.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.CGMessage;

/**
 * 查看自己发送出的好友申请
 * 
 * @author SevenSoul
 */
@Component
public class CGShowSelfApplyList extends CGMessage{
	
	
	public CGShowSelfApplyList (){
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
		return MessageType.CG_SHOW_SELF_APPLY_LIST;
	}
	
	@Override
	public String getTypeName() {
		return "CG_SHOW_SELF_APPLY_LIST";
	}

	@Override
	public void execute() {
	}
}