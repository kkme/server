package com.hifun.soul.gameserver.friend.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.GCMessage;

/**
 * 更新好友信息
 *
 * @author SevenSoul
 */
@Component
public class GCUpdateFriendInfo extends GCMessage{
	
	/** 玩家 */
	private com.hifun.soul.gameserver.friend.FriendInfo friendInfo;

	public GCUpdateFriendInfo (){
	}
	
	public GCUpdateFriendInfo (
			com.hifun.soul.gameserver.friend.FriendInfo friendInfo ){
			this.friendInfo = friendInfo;
	}

	@Override
	protected boolean readImpl() {
		friendInfo = new com.hifun.soul.gameserver.friend.FriendInfo();
						friendInfo.setRoleId(readLong());
						friendInfo.setRoleName(readString());
						friendInfo.setLevel(readInteger());
						friendInfo.setOccupation(readInteger());
						friendInfo.setSendState(readBoolean());
						friendInfo.setGetState(readInteger());
						friendInfo.setIsOnLine(readBoolean());
						friendInfo.setYellowVipLevel(readInteger());
						friendInfo.setIsYearYellowVip(readBoolean());
						friendInfo.setIsYellowHighVip(readBoolean());
				return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeLong(friendInfo.getRoleId());
		writeString(friendInfo.getRoleName());
		writeInteger(friendInfo.getLevel());
		writeInteger(friendInfo.getOccupation());
		writeBoolean(friendInfo.getSendState());
		writeInteger(friendInfo.getGetState());
		writeBoolean(friendInfo.getIsOnLine());
		writeInteger(friendInfo.getYellowVipLevel());
		writeBoolean(friendInfo.getIsYearYellowVip());
		writeBoolean(friendInfo.getIsYellowHighVip());
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_UPDATE_FRIEND_INFO;
	}
	
	@Override
	public String getTypeName() {
		return "GC_UPDATE_FRIEND_INFO";
	}

	public com.hifun.soul.gameserver.friend.FriendInfo getFriendInfo(){
		return friendInfo;
	}
		
	public void setFriendInfo(com.hifun.soul.gameserver.friend.FriendInfo friendInfo){
		this.friendInfo = friendInfo;
	}
}