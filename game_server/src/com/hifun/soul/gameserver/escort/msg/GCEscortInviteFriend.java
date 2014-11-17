package com.hifun.soul.gameserver.escort.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.GCMessage;

/**
 * 响应邀请护送好友
 *
 * @author SevenSoul
 */
@Component
public class GCEscortInviteFriend extends GCMessage{
	
	/** 邀请状态 */
	private int inviteFriendState;

	public GCEscortInviteFriend (){
	}
	
	public GCEscortInviteFriend (
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
		return MessageType.GC_ESCORT_INVITE_FRIEND;
	}
	
	@Override
	public String getTypeName() {
		return "GC_ESCORT_INVITE_FRIEND";
	}

	public int getInviteFriendState(){
		return inviteFriendState;
	}
		
	public void setInviteFriendState(int inviteFriendState){
		this.inviteFriendState = inviteFriendState;
	}
}