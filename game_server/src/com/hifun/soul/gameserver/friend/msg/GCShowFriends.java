package com.hifun.soul.gameserver.friend.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.GCMessage;

/**
 * 返回好友列表
 *
 * @author SevenSoul
 */
@Component
public class GCShowFriends extends GCMessage{
	
	/** 好友上限 */
	private int maxFriendNum;
	/** 接收体力次数上限 */
	private int maxRewardNum;
	/** 剩余接收次数 */
	private int leftRewardNum;
	/** 玩家 */
	private com.hifun.soul.gameserver.friend.FriendInfo[] friendInfos;

	public GCShowFriends (){
	}
	
	public GCShowFriends (
			int maxFriendNum,
			int maxRewardNum,
			int leftRewardNum,
			com.hifun.soul.gameserver.friend.FriendInfo[] friendInfos ){
			this.maxFriendNum = maxFriendNum;
			this.maxRewardNum = maxRewardNum;
			this.leftRewardNum = leftRewardNum;
			this.friendInfos = friendInfos;
	}

	@Override
	protected boolean readImpl() {
		short count=0;
		maxFriendNum = readInteger();
		maxRewardNum = readInteger();
		leftRewardNum = readInteger();
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
		writeInteger(maxFriendNum);
		writeInteger(maxRewardNum);
		writeInteger(leftRewardNum);
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
		return MessageType.GC_SHOW_FRIENDS;
	}
	
	@Override
	public String getTypeName() {
		return "GC_SHOW_FRIENDS";
	}

	public int getMaxFriendNum(){
		return maxFriendNum;
	}
		
	public void setMaxFriendNum(int maxFriendNum){
		this.maxFriendNum = maxFriendNum;
	}

	public int getMaxRewardNum(){
		return maxRewardNum;
	}
		
	public void setMaxRewardNum(int maxRewardNum){
		this.maxRewardNum = maxRewardNum;
	}

	public int getLeftRewardNum(){
		return leftRewardNum;
	}
		
	public void setLeftRewardNum(int leftRewardNum){
		this.leftRewardNum = leftRewardNum;
	}

	public com.hifun.soul.gameserver.friend.FriendInfo[] getFriendInfos(){
		return friendInfos;
	}

	public void setFriendInfos(com.hifun.soul.gameserver.friend.FriendInfo[] friendInfos){
		this.friendInfos = friendInfos;
	}	
}