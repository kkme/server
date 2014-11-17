package com.hifun.soul.gameserver.friend.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.GCMessage;

/**
 * 显示系统推荐好友
 *
 * @author SevenSoul
 */
@Component
public class GCShowLuckFriends extends GCMessage{
	
	/** 玩家 */
	private com.hifun.soul.gameserver.friend.FriendInfo[] friendInfos;

	public GCShowLuckFriends (){
	}
	
	public GCShowLuckFriends (
			com.hifun.soul.gameserver.friend.FriendInfo[] friendInfos ){
			this.friendInfos = friendInfos;
	}

	@Override
	protected boolean readImpl() {
		short count=0;
		count = readShort();
		count = count < 0 ? 0 : count;
		friendInfos = new com.hifun.soul.gameserver.friend.FriendInfo[count];
		for(int i=0; i<count; i++){
			com.hifun.soul.gameserver.friend.FriendInfo objfriendInfos = new com.hifun.soul.gameserver.friend.FriendInfo();
			friendInfos[i] = objfriendInfos;
					objfriendInfos.setRoleId(readLong());
							objfriendInfos.setRoleName(readString());
							objfriendInfos.setLevel(readInteger());
							objfriendInfos.setOccupation(readInteger());
							objfriendInfos.setSendState(readBoolean());
							objfriendInfos.setGetState(readInteger());
							objfriendInfos.setIsOnLine(readBoolean());
							objfriendInfos.setYellowVipLevel(readInteger());
							objfriendInfos.setIsYearYellowVip(readBoolean());
							objfriendInfos.setIsYellowHighVip(readBoolean());
				}
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
	writeShort(friendInfos.length);
	for(int i=0; i<friendInfos.length; i++){
	com.hifun.soul.gameserver.friend.FriendInfo objfriendInfos = friendInfos[i];
				writeLong(objfriendInfos.getRoleId());
				writeString(objfriendInfos.getRoleName());
				writeInteger(objfriendInfos.getLevel());
				writeInteger(objfriendInfos.getOccupation());
				writeBoolean(objfriendInfos.getSendState());
				writeInteger(objfriendInfos.getGetState());
				writeBoolean(objfriendInfos.getIsOnLine());
				writeInteger(objfriendInfos.getYellowVipLevel());
				writeBoolean(objfriendInfos.getIsYearYellowVip());
				writeBoolean(objfriendInfos.getIsYellowHighVip());
	}
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_SHOW_LUCK_FRIENDS;
	}
	
	@Override
	public String getTypeName() {
		return "GC_SHOW_LUCK_FRIENDS";
	}

	public com.hifun.soul.gameserver.friend.FriendInfo[] getFriendInfos(){
		return friendInfos;
	}

	public void setFriendInfos(com.hifun.soul.gameserver.friend.FriendInfo[] friendInfos){
		this.friendInfos = friendInfos;
	}	
}