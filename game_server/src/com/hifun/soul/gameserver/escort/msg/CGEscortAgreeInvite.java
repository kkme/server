package com.hifun.soul.gameserver.escort.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.CGMessage;

/**
 * 请求同意邀请 
 * 
 * @author SevenSoul
 */
@Component
public class CGEscortAgreeInvite extends CGMessage{
	
	/** 邀请ID */
	private long inviteId;
	
	public CGEscortAgreeInvite (){
	}
	
	public CGEscortAgreeInvite (
			long inviteId ){
			this.inviteId = inviteId;
	}
	
	@Override
	protected boolean readImpl() {
		inviteId = readLong();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeLong(inviteId);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.CG_ESCORT_AGREE_INVITE;
	}
	
	@Override
	public String getTypeName() {
		return "CG_ESCORT_AGREE_INVITE";
	}

	public long getInviteId(){
		return inviteId;
	}
		
	public void setInviteId(long inviteId){
		this.inviteId = inviteId;
	}

	@Override
	public void execute() {
	}
}