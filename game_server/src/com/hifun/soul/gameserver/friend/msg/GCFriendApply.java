package com.hifun.soul.gameserver.friend.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.GCMessage;

/**
 * A请求添加好友B,如果B在线将A的信息发给B
 *
 * @author SevenSoul
 */
@Component
public class GCFriendApply extends GCMessage{
	
	/** 申请人 */
	private com.hifun.soul.gameserver.friend.FriendInfo applyer;

	public GCFriendApply (){
	}
	
	public GCFriendApply (
			com.hifun.soul.gameserver.friend.FriendInfo applyer ){
			this.applyer = applyer;
	}

	@Override
	protected boolean readImpl() {
		applyer = new com.hifun.soul.gameserver.friend.FriendInfo();
						applyer.setRoleId(readLong());
						applyer.setRoleName(readString());
						applyer.setLevel(readInteger());
						applyer.setOccupation(readInteger());
						applyer.setSendState(readBoolean());
						applyer.setGetState(readInteger());
						applyer.setIsOnLine(readBoolean());
						applyer.setYellowVipLevel(readInteger());
						applyer.setIsYearYellowVip(readBoolean());
						applyer.setIsYellowHighVip(readBoolean());
				return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeLong(applyer.getRoleId());
		writeString(applyer.getRoleName());
		writeInteger(applyer.getLevel());
		writeInteger(applyer.getOccupation());
		writeBoolean(applyer.getSendState());
		writeInteger(applyer.getGetState());
		writeBoolean(applyer.getIsOnLine());
		writeInteger(applyer.getYellowVipLevel());
		writeBoolean(applyer.getIsYearYellowVip());
		writeBoolean(applyer.getIsYellowHighVip());
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_FRIEND_APPLY;
	}
	
	@Override
	public String getTypeName() {
		return "GC_FRIEND_APPLY";
	}

	public com.hifun.soul.gameserver.friend.FriendInfo getApplyer(){
		return applyer;
	}
		
	public void setApplyer(com.hifun.soul.gameserver.friend.FriendInfo applyer){
		this.applyer = applyer;
	}
}