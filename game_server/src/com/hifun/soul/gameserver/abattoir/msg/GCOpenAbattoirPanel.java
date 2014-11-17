package com.hifun.soul.gameserver.abattoir.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.GCMessage;

/**
 * 响应打开角斗场面板
 *
 * @author SevenSoul
 */
@Component
public class GCOpenAbattoirPanel extends GCMessage{
	
	/** 个人信息  */
	private com.hifun.soul.gameserver.abattoir.msg.WrestlerInfo wrestlerInfo;
	/** 角斗场房间列表  */
	private com.hifun.soul.gameserver.abattoir.msg.AbattoirRoomInfo[] rooms;

	public GCOpenAbattoirPanel (){
	}
	
	public GCOpenAbattoirPanel (
			com.hifun.soul.gameserver.abattoir.msg.WrestlerInfo wrestlerInfo,
			com.hifun.soul.gameserver.abattoir.msg.AbattoirRoomInfo[] rooms ){
			this.wrestlerInfo = wrestlerInfo;
			this.rooms = rooms;
	}

	@Override
	protected boolean readImpl() {
		short count=0;
		wrestlerInfo = new com.hifun.soul.gameserver.abattoir.msg.WrestlerInfo();
						wrestlerInfo.setTitleId(readInteger());
						wrestlerInfo.setTitleName(readString());
						wrestlerInfo.setCurrentPrestige(readInteger());
						wrestlerInfo.setMaxPrestige(readInteger());
						wrestlerInfo.setRoomId(readInteger());
						wrestlerInfo.setRoomName(readString());
						wrestlerInfo.setRemainWrestleNum(readInteger());
						wrestlerInfo.setRemainTime(readInteger());
						wrestlerInfo.setRevenue(readInteger());
						wrestlerInfo.setTotalRevenue(readInteger());
						wrestlerInfo.setNextBuyNumCost(readInteger());
						wrestlerInfo.setRoomPageIndex(readInteger());
				count = readShort();
		count = count < 0 ? 0 : count;
		rooms = new com.hifun.soul.gameserver.abattoir.msg.AbattoirRoomInfo[count];
		for(int i=0; i<count; i++){
			com.hifun.soul.gameserver.abattoir.msg.AbattoirRoomInfo objrooms = new com.hifun.soul.gameserver.abattoir.msg.AbattoirRoomInfo();
			rooms[i] = objrooms;
					objrooms.setRoomId(readInteger());
							objrooms.setRoomName(readString());
							objrooms.setRevenue(readInteger());
							objrooms.setOwnerId(readLong());
							objrooms.setOwnerOccupationType(readInteger());
							objrooms.setOwnerType(readInteger());
							objrooms.setOwnerName(readString());
							objrooms.setOwnerLevel(readInteger());
							objrooms.setProtectTime(readInteger());
				}
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(wrestlerInfo.getTitleId());
		writeString(wrestlerInfo.getTitleName());
		writeInteger(wrestlerInfo.getCurrentPrestige());
		writeInteger(wrestlerInfo.getMaxPrestige());
		writeInteger(wrestlerInfo.getRoomId());
		writeString(wrestlerInfo.getRoomName());
		writeInteger(wrestlerInfo.getRemainWrestleNum());
		writeInteger(wrestlerInfo.getRemainTime());
		writeInteger(wrestlerInfo.getRevenue());
		writeInteger(wrestlerInfo.getTotalRevenue());
		writeInteger(wrestlerInfo.getNextBuyNumCost());
		writeInteger(wrestlerInfo.getRoomPageIndex());
	writeShort(rooms.length);
	for(int i=0; i<rooms.length; i++){
	com.hifun.soul.gameserver.abattoir.msg.AbattoirRoomInfo objrooms = rooms[i];
				writeInteger(objrooms.getRoomId());
				writeString(objrooms.getRoomName());
				writeInteger(objrooms.getRevenue());
				writeLong(objrooms.getOwnerId());
				writeInteger(objrooms.getOwnerOccupationType());
				writeInteger(objrooms.getOwnerType());
				writeString(objrooms.getOwnerName());
				writeInteger(objrooms.getOwnerLevel());
				writeInteger(objrooms.getProtectTime());
	}
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_OPEN_ABATTOIR_PANEL;
	}
	
	@Override
	public String getTypeName() {
		return "GC_OPEN_ABATTOIR_PANEL";
	}

	public com.hifun.soul.gameserver.abattoir.msg.WrestlerInfo getWrestlerInfo(){
		return wrestlerInfo;
	}
		
	public void setWrestlerInfo(com.hifun.soul.gameserver.abattoir.msg.WrestlerInfo wrestlerInfo){
		this.wrestlerInfo = wrestlerInfo;
	}

	public com.hifun.soul.gameserver.abattoir.msg.AbattoirRoomInfo[] getRooms(){
		return rooms;
	}

	public void setRooms(com.hifun.soul.gameserver.abattoir.msg.AbattoirRoomInfo[] rooms){
		this.rooms = rooms;
	}	
}