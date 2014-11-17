package com.hifun.soul.gameserver.friend.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.GCMessage;

/**
 * 显示好友切磋信息
 *
 * @author SevenSoul
 */
@Component
public class GCShowFriendBattleinfos extends GCMessage{
	
	/** 好友切磋信息 */
	private com.hifun.soul.gameserver.friend.FriendBattleInfo[] friendBattleInfos;

	public GCShowFriendBattleinfos (){
	}
	
	public GCShowFriendBattleinfos (
			com.hifun.soul.gameserver.friend.FriendBattleInfo[] friendBattleInfos ){
			this.friendBattleInfos = friendBattleInfos;
	}

	@Override
	protected boolean readImpl() {
		short count=0;
		count = readShort();
		count = count < 0 ? 0 : count;
		friendBattleInfos = new com.hifun.soul.gameserver.friend.FriendBattleInfo[count];
		for(int i=0; i<count; i++){
			com.hifun.soul.gameserver.friend.FriendBattleInfo objfriendBattleInfos = new com.hifun.soul.gameserver.friend.FriendBattleInfo();
			friendBattleInfos[i] = objfriendBattleInfos;
					objfriendBattleInfos.setRoleId(readLong());
							objfriendBattleInfos.setRoleName(readString());
							objfriendBattleInfos.setOtherRoleId(readLong());
							objfriendBattleInfos.setOtherRoleName(readString());
							objfriendBattleInfos.setWin(readBoolean());
							objfriendBattleInfos.setTimeInterval(readInteger());
							objfriendBattleInfos.setIsChallenger(readBoolean());
				}
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
	writeShort(friendBattleInfos.length);
	for(int i=0; i<friendBattleInfos.length; i++){
	com.hifun.soul.gameserver.friend.FriendBattleInfo objfriendBattleInfos = friendBattleInfos[i];
				writeLong(objfriendBattleInfos.getRoleId());
				writeString(objfriendBattleInfos.getRoleName());
				writeLong(objfriendBattleInfos.getOtherRoleId());
				writeString(objfriendBattleInfos.getOtherRoleName());
				writeBoolean(objfriendBattleInfos.getWin());
				writeInteger(objfriendBattleInfos.getTimeInterval());
				writeBoolean(objfriendBattleInfos.getIsChallenger());
	}
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_SHOW_FRIEND_BATTLEINFOS;
	}
	
	@Override
	public String getTypeName() {
		return "GC_SHOW_FRIEND_BATTLEINFOS";
	}

	public com.hifun.soul.gameserver.friend.FriendBattleInfo[] getFriendBattleInfos(){
		return friendBattleInfos;
	}

	public void setFriendBattleInfos(com.hifun.soul.gameserver.friend.FriendBattleInfo[] friendBattleInfos){
		this.friendBattleInfos = friendBattleInfos;
	}	
}