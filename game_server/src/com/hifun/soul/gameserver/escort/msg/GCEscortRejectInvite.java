package com.hifun.soul.gameserver.escort.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.GCMessage;

/**
 * 响应拒绝邀请
 *
 * @author SevenSoul
 */
@Component
public class GCEscortRejectInvite extends GCMessage{
	
	/** 邀请状态 */
	private int inviteFriendState;

	public GCEscortRejectInvite (){
	}
	
	public GCEscortRejectInvite (
			int inviteFriendState ){
			this.inviteFriendState = inviteFriendState;
	}

	@Override
	protected boolean readImpl() {
		inviteFriendState = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(inviteFriendState);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_ESCORT_REJECT_INVITE;
	}
	
	@Override
	public String getTypeName() {
		return "GC_ESCORT_REJECT_INVITE";
	}

	public int getInviteFriendState(){
		return inviteFriendState;
	}
		
	public void setInviteFriendState(int inviteFriendState){
		this.inviteFriendState = inviteFriendState;
	}
}