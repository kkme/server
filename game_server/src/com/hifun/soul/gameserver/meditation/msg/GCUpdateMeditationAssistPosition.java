package com.hifun.soul.gameserver.meditation.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.GCMessage;

/**
 * 更新好友协助位信息
 *
 * @author SevenSoul
 */
@Component
public class GCUpdateMeditationAssistPosition extends GCMessage{
	
	/** 好友信息 */
	private com.hifun.soul.gameserver.meditation.msg.MeditationFriendAssistInfo friendAssistInfo;

	public GCUpdateMeditationAssistPosition (){
	}
	
	public GCUpdateMeditationAssistPosition (
			com.hifun.soul.gameserver.meditation.msg.MeditationFriendAssistInfo friendAssistInfo ){
			this.friendAssistInfo = friendAssistInfo;
	}

	@Override
	protected boolean readImpl() {
		friendAssistInfo = new com.hifun.soul.gameserver.meditation.msg.MeditationFriendAssistInfo();
						friendAssistInfo.setIndex(readInteger());
							{
	com.hifun.soul.gameserver.friend.FriendInfo objfriendInfo = new com.hifun.soul.gameserver.friend.FriendInfo();
	friendAssistInfo.setFriendInfo(objfriendInfo);
				objfriendInfo.setRoleId(readLong());
				objfriendInfo.setRoleName(readString());
				objfriendInfo.setLevel(readInteger());
				objfriendInfo.setOccupation(readInteger());
				objfriendInfo.setSendState(readBoolean());
				objfriendInfo.setGetState(readInteger());
				objfriendInfo.setIsOnLine(readBoolean());
				objfriendInfo.setYellowVipLevel(readInteger());
				objfriendInfo.setIsYearYellowVip(readBoolean());
				objfriendInfo.setIsYellowHighVip(readBoolean());
			}
						friendAssistInfo.setAssistRate(readInteger());
						friendAssistInfo.setTotalTechPoint(readInteger());
				return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(friendAssistInfo.getIndex());
			com.hifun.soul.gameserver.friend.FriendInfo friendInfo_friendAssistInfo = friendAssistInfo.getFriendInfo();
					writeLong(friendInfo_friendAssistInfo.getRoleId());
					writeString(friendInfo_friendAssistInfo.getRoleName());
					writeInteger(friendInfo_friendAssistInfo.getLevel());
					writeInteger(friendInfo_friendAssistInfo.getOccupation());
					writeBoolean(friendInfo_friendAssistInfo.getSendState());
					writeInteger(friendInfo_friendAssistInfo.getGetState());
					writeBoolean(friendInfo_friendAssistInfo.getIsOnLine());
					writeInteger(friendInfo_friendAssistInfo.getYellowVipLevel());
					writeBoolean(friendInfo_friendAssistInfo.getIsYearYellowVip());
					writeBoolean(friendInfo_friendAssistInfo.getIsYellowHighVip());
				writeInteger(friendAssistInfo.getAssistRate());
		writeInteger(friendAssistInfo.getTotalTechPoint());
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_UPDATE_MEDITATION_ASSIST_POSITION;
	}
	
	@Override
	public String getTypeName() {
		return "GC_UPDATE_MEDITATION_ASSIST_POSITION";
	}

	public com.hifun.soul.gameserver.meditation.msg.MeditationFriendAssistInfo getFriendAssistInfo(){
		return friendAssistInfo;
	}
		
	public void setFriendAssistInfo(com.hifun.soul.gameserver.meditation.msg.MeditationFriendAssistInfo friendAssistInfo){
		this.friendAssistInfo = friendAssistInfo;
	}
}