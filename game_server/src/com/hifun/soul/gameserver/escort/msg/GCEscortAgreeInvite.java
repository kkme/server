package com.hifun.soul.gameserver.escort.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.GCMessage;

/**
 * 响应同意邀请
 *
 * @author SevenSoul
 */
@Component
public class GCEscortAgreeInvite extends GCMessage{
	
	/** 邀请状态 */
	private int inviteFriendState;
	/** 邀请有效时间 */
	private int inviteRemainTime;

	public GCEscortAgreeInvite (){
	}
	
	public GCEscortAgreeInvite (
			int inviteFriendState,
			int inviteRemainTime ){
			this.inviteFriendState = inviteFriendState;
			this.inviteRemainTime = inviteRemainTime;
	}

	@Override
	protected boolean readImpl() {
		inviteFriendState = readInteger();
		inviteRemainTime = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(inviteFriendState);
		writeInteger(inviteRemainTime);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_ESCORT_AGREE_INVITE;
	}
	
	@Override
	public String getTypeName() {
		return "GC_ESCORT_AGREE_INVITE";
	}

	public int getInviteFriendState(){
		return inviteFriendState;
	}
		
	public void setInviteFriendState(int inviteFriendState){
		this.inviteFriendState = inviteFriendState;
	}

	public int getInviteRemainTime(){
		return inviteRemainTime;
	}
		
	public void setInviteRemainTime(int inviteRemainTime){
		this.inviteRemainTime = inviteRemainTime;
	}
}