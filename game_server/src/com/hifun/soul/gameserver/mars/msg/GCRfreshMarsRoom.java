package com.hifun.soul.gameserver.mars.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.GCMessage;

/**
 * 响应刷新战神之巅房间
 *
 * @author SevenSoul
 */
@Component
public class GCRfreshMarsRoom extends GCMessage{
	
	/** 房间类型 */
	private int roomType;
	/** 房主信息  */
	private com.hifun.soul.gameserver.mars.msg.info.MarsPlayerInfo ownerInfo;

	public GCRfreshMarsRoom (){
	}
	
	public GCRfreshMarsRoom (
			int roomType,
			com.hifun.soul.gameserver.mars.msg.info.MarsPlayerInfo ownerInfo ){
			this.roomType = roomType;
			this.ownerInfo = ownerInfo;
	}

	@Override
	protected boolean readImpl() {
		roomType = readInteger();
		ownerInfo = new com.hifun.soul.gameserver.mars.msg.info.MarsPlayerInfo();
						ownerInfo.setPlayerId(readLong());
						ownerInfo.setPlayerName(readString());
						ownerInfo.setPlayerLevel(readInteger());
						ownerInfo.setOccupationType(readInteger());
						ownerInfo.setTodayKillValue(readInteger());
				return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(roomType);
		writeLong(ownerInfo.getPlayerId());
		writeString(ownerInfo.getPlayerName());
		writeInteger(ownerInfo.getPlayerLevel());
		writeInteger(ownerInfo.getOccupationType());
		writeInteger(ownerInfo.getTodayKillValue());
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_RFRESH_MARS_ROOM;
	}
	
	@Override
	public String getTypeName() {
		return "GC_RFRESH_MARS_ROOM";
	}

	public int getRoomType(){
		return roomType;
	}
		
	public void setRoomType(int roomType){
		this.roomType = roomType;
	}

	public com.hifun.soul.gameserver.mars.msg.info.MarsPlayerInfo getOwnerInfo(){
		return ownerInfo;
	}
		
	public void setOwnerInfo(com.hifun.soul.gameserver.mars.msg.info.MarsPlayerInfo ownerInfo){
		this.ownerInfo = ownerInfo;
	}
}