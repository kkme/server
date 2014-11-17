package com.hifun.soul.gameserver.friend.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.GCMessage;

/**
 * 新增好友切磋信息
 *
 * @author SevenSoul
 */
@Component
public class GCAddFriendBattleinfo extends GCMessage{
	
	/** 好友切磋信息 */
	private com.hifun.soul.gameserver.friend.FriendBattleInfo friendBattleInfo;

	public GCAddFriendBattleinfo (){
	}
	
	public GCAddFriendBattleinfo (
			com.hifun.soul.gameserver.friend.FriendBattleInfo friendBattleInfo ){
			this.friendBattleInfo = friendBattleInfo;
	}

	@Override
	protected boolean readImpl() {
		friendBattleInfo = new com.hifun.soul.gameserver.friend.FriendBattleInfo();
						friendBattleInfo.setRoleId(readLong());
						friendBattleInfo.setRoleName(readString());
						friendBattleInfo.setOtherRoleId(readLong());
						friendBattleInfo.setOtherRoleName(readString());
						friendBattleInfo.setWin(readBoolean());
						friendBattleInfo.setTimeInterval(readInteger());
						friendBattleInfo.setIsChallenger(readBoolean());
				return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeLong(friendBattleInfo.getRoleId());
		writeString(friendBattleInfo.getRoleName());
		writeLong(friendBattleInfo.getOtherRoleId());
		writeString(friendBattleInfo.getOtherRoleName());
		writeBoolean(friendBattleInfo.getWin());
		writeInteger(friendBattleInfo.getTimeInterval());
		writeBoolean(friendBattleInfo.getIsChallenger());
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_ADD_FRIEND_BATTLEINFO;
	}
	
	@Override
	public String getTypeName() {
		return "GC_ADD_FRIEND_BATTLEINFO";
	}

	public com.hifun.soul.gameserver.friend.FriendBattleInfo getFriendBattleInfo(){
		return friendBattleInfo;
	}
		
	public void setFriendBattleInfo(com.hifun.soul.gameserver.friend.FriendBattleInfo friendBattleInfo){
		this.friendBattleInfo = friendBattleInfo;
	}
}