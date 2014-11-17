package com.hifun.soul.gameserver.bloodtemple.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.GCMessage;

/**
 * 响应展示嗜血神殿房间
 *
 * @author SevenSoul
 */
@Component
public class GCShowBloodTempleRoom extends GCMessage{
	
	/** 嗜血神殿房间列表  */
	private com.hifun.soul.gameserver.bloodtemple.msg.BloodTempleRoomInfo[] rooms;

	public GCShowBloodTempleRoom (){
	}
	
	public GCShowBloodTempleRoom (
			com.hifun.soul.gameserver.bloodtemple.msg.BloodTempleRoomInfo[] rooms ){
			this.rooms = rooms;
	}

	@Override
	protected boolean readImpl() {
		short count=0;
		count = readShort();
		count = count < 0 ? 0 : count;
		rooms = new com.hifun.soul.gameserver.bloodtemple.msg.BloodTempleRoomInfo[count];
		for(int i=0; i<count; i++){
			com.hifun.soul.gameserver.bloodtemple.msg.BloodTempleRoomInfo objrooms = new com.hifun.soul.gameserver.bloodtemple.msg.BloodTempleRoomInfo();
			rooms[i] = objrooms;
					objrooms.setRoomId(readInteger());
							objrooms.setRoomName(readString());
							objrooms.setRevenue(readInteger());
							objrooms.setOwnerId(readLong());
							objrooms.setOwnerOccupationType(readInteger());
							objrooms.setOwnerType(readInteger());
							objrooms.setOwnerName(readString());
							objrooms.setOwnerLevel(readInteger());
							objrooms.setOwnerTitleId(readInteger());
							objrooms.setOwnerTitleName(readString());
							objrooms.setOwnerLegionId(readLong());
							objrooms.setOwnerLegionName(readString());
							objrooms.setProtectTime(readInteger());
				}
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
	writeShort(rooms.length);
	for(int i=0; i<rooms.length; i++){
	com.hifun.soul.gameserver.bloodtemple.msg.BloodTempleRoomInfo objrooms = rooms[i];
				writeInteger(objrooms.getRoomId());
				writeString(objrooms.getRoomName());
				writeInteger(objrooms.getRevenue());
				writeLong(objrooms.getOwnerId());
				writeInteger(objrooms.getOwnerOccupationType());
				writeInteger(objrooms.getOwnerType());
				writeString(objrooms.getOwnerName());
				writeInteger(objrooms.getOwnerLevel());
				writeInteger(objrooms.getOwnerTitleId());
				writeString(objrooms.getOwnerTitleName());
				writeLong(objrooms.getOwnerLegionId());
				writeString(objrooms.getOwnerLegionName());
				writeInteger(objrooms.getProtectTime());
	}
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_SHOW_BLOOD_TEMPLE_ROOM;
	}
	
	@Override
	public String getTypeName() {
		return "GC_SHOW_BLOOD_TEMPLE_ROOM";
	}

	public com.hifun.soul.gameserver.bloodtemple.msg.BloodTempleRoomInfo[] getRooms(){
		return rooms;
	}

	public void setRooms(com.hifun.soul.gameserver.bloodtemple.msg.BloodTempleRoomInfo[] rooms){
		this.rooms = rooms;
	}	
}