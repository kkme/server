package com.hifun.soul.gameserver.escort.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.GCMessage;

/**
 * 向好友发送邀请申请
 *
 * @author SevenSoul
 */
@Component
public class GCEscortInviteFriendApply extends GCMessage{
	
	/** 申请角色ID */
	private long applyHumanGuid;
	/** 申请角色名称 */
	private String applyHumanName;

	public GCEscortInviteFriendApply (){
	}
	
	public GCEscortInviteFriendApply (
			long applyHumanGuid,
			String applyHumanName ){
			this.applyHumanGuid = applyHumanGuid;
			this.applyHumanName = applyHumanName;
	}

	@Override
	protected boolean readImpl() {
		applyHumanGuid = readLong();
		applyHumanName = readString();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeLong(applyHumanGuid);
		writeString(applyHumanName);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_ESCORT_INVITE_FRIEND_APPLY;
	}
	
	@Override
	public String getTypeName() {
		return "GC_ESCORT_INVITE_FRIEND_APPLY";
	}

	public long getApplyHumanGuid(){
		return applyHumanGuid;
	}
		
	public void setApplyHumanGuid(long applyHumanGuid){
		this.applyHumanGuid = applyHumanGuid;
	}

	public String getApplyHumanName(){
		return applyHumanName;
	}
		
	public void setApplyHumanName(String applyHumanName){
		this.applyHumanName = applyHumanName;
	}
}