package com.hifun.soul.gameserver.friend.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.GCMessage;

/**
 * 返回好友申请列表
 *
 * @author SevenSoul
 */
@Component
public class GCShowFriendApplyList extends GCMessage{
	
	/** 玩家 */
	private com.hifun.soul.gameserver.friend.FriendInfo[] applyList;

	public GCShowFriendApplyList (){
	}
	
	public GCShowFriendApplyList (
			com.hifun.soul.gameserver.friend.FriendInfo[] applyList ){
			this.applyList = applyList;
	}

	@Override
	protected boolean readImpl() {
		short count=0;
		count = readShort();
		count = count < 0 ? 0 : count;
		applyList = new com.hifun.soul.gameserver.friend.FriendInfo[count];
		for(int i=0; i<count; i++){
			com.hifun.soul.gameserver.friend.FriendInfo objapplyList = new com.hifun.soul.gameserver.friend.FriendInfo();
			applyList[i] = objapplyList;
					objapplyList.setRoleId(readLong());
							objapplyList.setRoleName(readString());
							objapplyList.setLevel(readInteger());
							objapplyList.setOccupation(readInteger());
							objapplyList.setSendState(readBoolean());
							objapplyList.setGetState(readInteger());
							objapplyList.setIsOnLine(readBoolean());
							objapplyList.setYellowVipLevel(readInteger());
							objapplyList.setIsYearYellowVip(readBoolean());
							objapplyList.setIsYellowHighVip(readBoolean());
				}
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
	writeShort(applyList.length);
	for(int i=0; i<applyList.length; i++){
	com.hifun.soul.gameserver.friend.FriendInfo objapplyList = applyList[i];
				writeLong(objapplyList.getRoleId());
				writeString(objapplyList.getRoleName());
				writeInteger(objapplyList.getLevel());
				writeInteger(objapplyList.getOccupation());
				writeBoolean(objapplyList.getSendState());
				writeInteger(objapplyList.getGetState());
				writeBoolean(objapplyList.getIsOnLine());
				writeInteger(objapplyList.getYellowVipLevel());
				writeBoolean(objapplyList.getIsYearYellowVip());
				writeBoolean(objapplyList.getIsYellowHighVip());
	}
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_SHOW_FRIEND_APPLY_LIST;
	}
	
	@Override
	public String getTypeName() {
		return "GC_SHOW_FRIEND_APPLY_LIST";
	}

	public com.hifun.soul.gameserver.friend.FriendInfo[] getApplyList(){
		return applyList;
	}

	public void setApplyList(com.hifun.soul.gameserver.friend.FriendInfo[] applyList){
		this.applyList = applyList;
	}	
}