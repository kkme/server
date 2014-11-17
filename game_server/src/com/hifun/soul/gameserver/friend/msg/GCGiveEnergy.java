package com.hifun.soul.gameserver.friend.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.GCMessage;

/**
 * 好友赠送体力的通知
 *
 * @author SevenSoul
 */
@Component
public class GCGiveEnergy extends GCMessage{
	
	/** 玩家 */
	private com.hifun.soul.gameserver.friend.FriendInfo friendInfo;
	/** 体力数量 */
	private int energy;

	public GCGiveEnergy (){
	}
	
	public GCGiveEnergy (
			com.hifun.soul.gameserver.friend.FriendInfo friendInfo,
			int energy ){
			this.friendInfo = friendInfo;
			this.energy = energy;
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
				energy = readInteger();
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
		writeInteger(energy);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_GIVE_ENERGY;
	}
	
	@Override
	public String getTypeName() {
		return "GC_GIVE_ENERGY";
	}

	public com.hifun.soul.gameserver.friend.FriendInfo getFriendInfo(){
		return friendInfo;
	}
		
	public void setFriendInfo(com.hifun.soul.gameserver.friend.FriendInfo friendInfo){
		this.friendInfo = friendInfo;
	}

	public int getEnergy(){
		return energy;
	}
		
	public void setEnergy(int energy){
		this.energy = energy;
	}
}