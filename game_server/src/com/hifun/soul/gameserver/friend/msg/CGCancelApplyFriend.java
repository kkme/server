package com.hifun.soul.gameserver.friend.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.CGMessage;

/**
 * 取消自己发出的好友申请
 * 
 * @author SevenSoul
 */
@Component
public class CGCancelApplyFriend extends CGMessage{
	
	/** 角色id */
	private long toRoleId;
	
	public CGCancelApplyFriend (){
	}
	
	public CGCancelApplyFriend (
			long toRoleId ){
			this.toRoleId = toRoleId;
	}
	
	@Override
	protected boolean readImpl() {
		toRoleId = readLong();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeLong(toRoleId);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.CG_CANCEL_APPLY_FRIEND;
	}
	
	@Override
	public String getTypeName() {
		return "CG_CANCEL_APPLY_FRIEND";
	}

	public long getToRoleId(){
		return toRoleId;
	}
		
	public void setToRoleId(long toRoleId){
		this.toRoleId = toRoleId;
	}

	@Override
	public void execute() {
	}
}