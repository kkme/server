package com.hifun.soul.gameserver.escort.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.CGMessage;

/**
 * 请求邀请护送好友 
 * 
 * @author SevenSoul
 */
@Component
public class CGEscortInviteFriend extends CGMessage{
	
	/** 邀请好友ID */
	private long inviteFriendId;
	
	public CGEscortInviteFriend (){
	}
	
	public CGEscortInviteFriend (
			long inviteFriendId ){
			this.inviteFriendId = inviteFriendId;
	}
	
	@Override
	protected boolean readImpl() {
		inviteFriendId = readLong();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeLong(inviteFriendId);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.CG_ESCORT_INVITE_FRIEND;
	}
	
	@Override
	public String getTypeName() {
		return "CG_ESCORT_INVITE_FRIEND";
	}

	public long getInviteFriendId(){
		return inviteFriendId;
	}
		
	public void setInviteFriendId(long inviteFriendId){
		this.inviteFriendId = inviteFriendId;
	}

	@Override
	public void execute() {
	}
}